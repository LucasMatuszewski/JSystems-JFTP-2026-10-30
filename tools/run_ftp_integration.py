"""Run the actual FTPAPI boundary against a real isolated loopback FTP server."""
import argparse
import contextlib
import ftplib
import hashlib
import json
import os
from pathlib import Path
import subprocess
import re
import ssl
import time
import uuid
import xml.etree.ElementTree as ET

ROOT = Path(__file__).resolve().parents[1]


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--python", type=Path, default=ROOT / ".local" / "ftp-test-server" /
                        ("Scripts/python.exe" if os.name == "nt" else "bin/python"))
    parser.add_argument("--runner", choices=("maven", "legacy"), default="maven",
                        help="Use the normal wrapper, or reproduce the source-only pre-build-recovery baseline")
    parser.add_argument("--tls", action="store_true", help="Run the explicit FTPS contract with a fresh test CA")
    args = parser.parse_args()
    if not args.python.is_file():
        parser.error("Create .local/ftp-test-server venv and install tools/ftp-fixture-requirements.txt")
    run = ROOT / ".local" / "ftp-integration" / uuid.uuid4().hex
    server_root = run / "server"
    server_root.mkdir(parents=True)
    started = time.time()
    settings = run / "maven-settings.xml"
    settings.write_text('<settings xmlns="http://maven.apache.org/SETTINGS/1.2.0"/>\n', encoding="utf-8")
    with (run / "server.log").open("w", encoding="utf-8") as log:
        server = subprocess.Popen([str(args.python), str(ROOT / "tools/ftp_test_server.py"),
                                   "--root", str(server_root), *(["--tls"] if args.tls else [])], stdout=log, stderr=log,
                                  cwd=ROOT, env={**os.environ, "PYTHONUNBUFFERED": "1"})
        try:
            deadline = time.monotonic() + 20
            while not (run / "ready.json").exists():
                if server.poll() is not None:
                    raise RuntimeError(f"Fixture exited; inspect {run / 'server.log'}")
                if time.monotonic() >= deadline:
                    raise RuntimeError(f"Fixture did not become ready; inspect {run / 'server.log'}")
                time.sleep(0.05)
            ready = json.loads((run / "ready.json").read_text())
            # Windows venv launchers supervise a child interpreter. Accept only
            # that direct child, never an unrelated process publishing readiness.
            owned = ready["pid"] == server.pid or (os.name == "nt" and ready["parent_pid"] == server.pid)
            if not owned or ready["host"] != "127.0.0.1":
                raise RuntimeError("Unexpected fixture identity")
            with (run / "probe.log").open("w", encoding="utf-8") as probe:
                with contextlib.redirect_stdout(probe):
                    client = (ftplib.FTP_TLS(context=ssl.create_default_context(cafile=str(run / "ca.pem")))
                              if args.tls else ftplib.FTP())
                    client.set_debuglevel(2)
                    client.connect(ready["host"], ready["port"], timeout=10)
                    client.login("fixture-user", "fixture-password")
                    if args.tls:
                        client.prot_p()
                        print("Probe negotiated:", client.sock.version())
                    client.pwd()
                    client.quit()
            test_class = "FtpsTransportContractIT" if args.tls else "FtpTransportContractIT"
            expected_tests = 5 if args.tls else 10
            command = [str(ROOT / ("mvnw.cmd" if os.name == "nt" else "mvnw")), "-B", "-ntp",
                       "-s", str(settings), "-Dmaven.repo.local=" + str(ROOT / ".local/m2"),
                       "-Dtest=" + test_class, "-Djftp.fixture.root=" + str(server_root),
                       "-Djftp.fixture.port=" + str(ready["port"]), "test"]
            if args.runner == "maven":
                with (run / "maven.log").open("w", encoding="utf-8") as output:
                    result = subprocess.run(command, cwd=ROOT, stdout=output, stderr=subprocess.STDOUT)
                if result.returncode:
                    raise RuntimeError(f"Integration failed; inspect {run / 'maven.log'}")
                report = ROOT / ("target/surefire-reports/TEST-com.myjavaworld.jftp." + test_class + ".xml")
                if not report.exists() or report.stat().st_mtime < started:
                    raise RuntimeError("Missing fresh Surefire integration report")
                suite = ET.parse(report).getroot()
                counts = {key: int(suite.attrib[key]) for key in ("tests", "failures", "errors", "skipped")}
                runtime_version = next((item.get("value") for item in suite.findall("properties/property")
                                        if item.get("name") == "java.runtime.version"), None)
            else:
                runtime_version = None
                result = subprocess.run(["pwsh", "-NoProfile", "-File", str(ROOT / "tools/legacy-baseline.ps1")],
                                        cwd=ROOT, capture_output=True, text=True, encoding="utf-8", errors="replace")
                (run / "legacy-build.log").write_text(result.stdout + result.stderr, encoding="utf-8")
                if result.returncode:
                    raise RuntimeError(f"Legacy build failed; inspect {run / 'legacy-build.log'}")
                candidates = [p for p in (ROOT / ".local/baseline-runs").glob("*/result.json")
                              if p.stat().st_mtime >= started]
                if len(candidates) != 1:
                    raise RuntimeError("Expected exactly one fresh legacy build receipt")
                classes = candidates[0].parent / "classes"
                cache = Path.home() / ".jftp-build-cache"
                classpath = os.pathsep.join(str(p) for p in (classes,
                    cache / "legacy-ftpapi-3.0.0/ftpapi-3.0.0.jar", cache / "javahelp-2.0.05.jar",
                    cache / "junit-3.8.1.jar"))
                with (run / "legacy-transport.log").open("w", encoding="utf-8") as output:
                    compiled = subprocess.run(["javac", "--release", "8", "-encoding", "UTF-8", "-cp", classpath,
                        "-d", str(classes), str(ROOT / ("src/test/java/com/myjavaworld/jftp/" + test_class + ".java"))],
                        cwd=ROOT, stdout=output, stderr=subprocess.STDOUT)
                    if compiled.returncode:
                        raise RuntimeError("Legacy integration compilation failed")
                    result = subprocess.run(["java", "-Djava.awt.headless=true", "-Duser.language=en",
                        "-Djava.io.tmpdir=" + str(run), "-Duser.home=" + str(run / "profile"),
                        "-Djftp.fixture.root=" + str(server_root), "-Djftp.fixture.port=" + str(ready["port"]),
                        "-cp", classpath, "junit.textui.TestRunner", "com.myjavaworld.jftp." + test_class],
                        cwd=ROOT, stdout=output, stderr=subprocess.STDOUT)
                text = (run / "legacy-transport.log").read_text(encoding="utf-8")
                if result.returncode or not re.search(r"OK \(" + str(expected_tests) + r" tests\)", text):
                    raise RuntimeError(f"Legacy integration failed; inspect {run / 'legacy-transport.log'}")
                counts = {"tests": expected_tests, "failures": 0, "errors": 0, "skipped": 0}
            if counts != {"tests": expected_tests, "failures": 0, "errors": 0, "skipped": 0}:
                raise RuntimeError(f"Unexpected integration discovery/results: {counts}")
            receipt = {"fixture": ready, "counts": counts, "elapsed_seconds": round(time.time() - started, 3),
                       "application_ui_tested": False, "transport": "FTPAPI 3.0.0", "tls_tested": args.tls,
                       "runner": args.runner, "java_runtime_version": runtime_version,
                       "test_source_sha256_lf": hashlib.sha256((ROOT / ("src/test/java/com/myjavaworld/jftp/" +
                           test_class + ".java")).read_bytes().replace(b"\r\n", b"\n")).hexdigest()}
        finally:
            if server.poll() is None:
                (run / "STOP").touch()
                try:
                    server.wait(timeout=10)
                except subprocess.TimeoutExpired:
                    server.terminate()
                    server.wait(timeout=10)
        receipt["fixture_supervisor_exit_code"] = server.returncode
        receipt["fixture_stopped"] = True
        (run / "result.json").write_text(json.dumps(receipt, indent=2) + "\n", encoding="utf-8")
        print(json.dumps({"passed": True, **counts, "fixture_stopped": True, "evidence": str(run)}))


if __name__ == "__main__":
    try:
        main()
    except (OSError, RuntimeError, ValueError, subprocess.SubprocessError) as error:
        raise SystemExit(str(error))
