"""Exercise the real pinned synthetic FTP fixture's STOP-file lifecycle."""
import json
import os
from pathlib import Path
import subprocess
import tempfile
import time
import unittest

ROOT = Path(__file__).resolve().parents[1]
FIXTURE = ROOT / "tools" / "ftp_test_server.py"
VENV_PYTHON = ROOT / ".local" / "ftp-test-server" / (
    "Scripts/python.exe" if os.name == "nt" else "bin/python"
)
RUNS = ROOT / ".local" / "ftp-integration"


def stop_verified_windows_child(ready, process, root):
    """Stop only the ready.json child whose OS parent and command identify this run."""
    child_pid = ready.get("pid") if ready else None
    parent_pid = ready.get("parent_pid") if ready else None
    if (os.name != "nt" or not isinstance(child_pid, int) or child_pid == process.pid
            or parent_pid != process.pid):
        return
    env = os.environ.copy()
    env["JFTP_FIXTURE_CHILD_PID"] = str(child_pid)
    env["JFTP_FIXTURE_PARENT_PID"] = str(process.pid)
    env["JFTP_FIXTURE_SCRIPT"] = str(FIXTURE.resolve())
    env["JFTP_FIXTURE_ROOT"] = str(root.resolve())
    command = (
        "$ErrorActionPreference = 'Stop'; "
        "$p = Get-CimInstance Win32_Process -Filter ('ProcessId = ' + $env:JFTP_FIXTURE_CHILD_PID); "
        "if ($null -ne $p) { if (-not ([int]$p.ParentProcessId -eq [int]$env:JFTP_FIXTURE_PARENT_PID "
        "-and $p.CommandLine.Contains($env:JFTP_FIXTURE_SCRIPT) "
        "-and $p.CommandLine.Contains($env:JFTP_FIXTURE_ROOT))) { throw 'Fixture child identity mismatch' }; "
        "$owned = Get-Process -Id ([int]$env:JFTP_FIXTURE_CHILD_PID); "
        "Stop-Process -Id $owned.Id; "
        "if (-not $owned.WaitForExit(2000)) { throw 'Owned fixture child did not exit' }; 'stopped' }"
    )
    result = subprocess.run(
        ["powershell.exe", "-NoProfile", "-NonInteractive", "-Command", command],
        env=env, capture_output=True, text=True, timeout=5,
    )
    if result.returncode != 0:
        raise RuntimeError(f"Could not verify/stop owned fixture child {child_pid}: {result.stderr}")


class FixtureLifecycleTests(unittest.TestCase):
    def test_stop_file_exits_real_fixture_successfully(self):
        if not VENV_PYTHON.is_file():
            raise RuntimeError(f"Pinned FTP fixture Python is required: {VENV_PYTHON}")
        RUNS.mkdir(parents=True, exist_ok=True)
        run_dir = Path(tempfile.mkdtemp(prefix="fixture-lifecycle-", dir=RUNS))
        root = run_dir / "root"
        root.mkdir()
        log_path = run_dir / "fixture.log"
        log = log_path.open("wb")
        process = None
        ready = None
        try:
            process = subprocess.Popen(
                [str(VENV_PYTHON), str(FIXTURE), "--root", str(root)],
                cwd=ROOT,
                stdout=log,
                stderr=subprocess.STDOUT,
            )
            ready_path = run_dir / "ready.json"
            deadline = time.monotonic() + 15
            ready = None
            while time.monotonic() < deadline:
                if process.poll() is not None:
                    break
                try:
                    ready = json.loads(ready_path.read_text(encoding="utf-8"))
                    break
                except (FileNotFoundError, json.JSONDecodeError):
                    time.sleep(0.05)
            if ready is None:
                log.flush()
                self.fail(f"Fixture did not publish ready.json; log: {log_path}; "
                          f"exit={process.poll()}; output={log_path.read_text(encoding='utf-8', errors='replace')}")
            if os.name == "nt" and ready.get("pid") != process.pid:
                self.assertEqual(process.pid, ready.get("parent_pid"),
                                 "ready.json is not a child of the launched Windows interpreter")
            elif os.name != "nt":
                self.assertEqual(process.pid, ready.get("pid"), "ready.json process identity mismatch")
            self.assertEqual("127.0.0.1", ready.get("host"), "fixture must bind loopback only")
            self.assertEqual(str(root.resolve()), ready.get("root"), "fixture served an unexpected root")

            (run_dir / "STOP").touch()
            try:
                exit_code = process.wait(timeout=5)
            except subprocess.TimeoutExpired:
                log.flush()
                self.fail(f"Fixture failed to exit within 5 seconds after STOP; pid={process.pid}; "
                          f"log: {log_path}; output={log_path.read_text(encoding='utf-8', errors='replace')}")
            self.assertEqual(0, exit_code, f"Fixture exited unsuccessfully; log: {log_path}")
        finally:
            try:
                if process is not None and process.poll() is None:
                    try:
                        stop_verified_windows_child(ready, process, root)
                    finally:
                        if process.poll() is None:
                            process.terminate()
                            try:
                                process.wait(timeout=2)
                            except subprocess.TimeoutExpired:
                                process.kill()
                                process.wait(timeout=2)
            finally:
                log.close()


if __name__ == "__main__":
    unittest.main()
