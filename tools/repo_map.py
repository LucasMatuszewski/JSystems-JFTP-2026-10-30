#!/usr/bin/env python3
"""Generate the course RepoMap with the verified native skill tool."""

import argparse
import json
from pathlib import Path
import subprocess
import sys
import tempfile
import time

ROOT = Path(__file__).resolve().parents[1]
SKILL = ROOT / ".agents/skills/legacy-codebase-workflows"
REPORT = ROOT / "docs/repo-maps/jftp-overview.rust.md"


def run_json(arguments):
    result = subprocess.run(arguments, cwd=ROOT, text=True, encoding="utf-8", capture_output=True)
    value = json.loads(result.stdout)
    return result.returncode, value


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--setup", action="store_true", help="Download/verify the skill's pinned native release if needed.")
    parser.add_argument("--replace", action="store_true", help="Replace the saved course map and provenance.")
    parser.add_argument("--budget", type=int, default=16384)
    args = parser.parse_args()
    if REPORT.exists() and not args.replace:
        parser.error("A course map already exists; pass --replace to regenerate it.")
    command = [sys.executable, str(SKILL / "scripts/setup_native.py")]
    _, status = run_json([*command, "status"])
    if status.get("state") == "needs-setup" and args.setup:
        code, status = run_json([*command, "install"])
        if code:
            raise RuntimeError(json.dumps(status))
    if status.get("state") != "ready":
        raise RuntimeError("Native mapper unavailable; no Python fallback was selected. " + json.dumps(status))
    with tempfile.TemporaryDirectory(prefix="jftp-repo-map-") as folder:
        started = time.perf_counter()
        subprocess.run([status["program"], str(ROOT), "--output-dir", folder, "--subtree", "src",
                        "--focus-symbol", "JFTPApplication", "--focus-symbol", "FTPSession",
                        "--focus-symbol", "FavoritesManager", "--budget", str(args.budget),
                        "--exclude", "docs/repo-maps/**"], check=True)
        elapsed = time.perf_counter() - started
        export = [sys.executable, str(SKILL / "scripts/export_repo_map.py"), str(ROOT),
                  "--artifact-dir", folder, "--implementation", "rust", "--output-file", str(REPORT),
                  "--elapsed-seconds", str(elapsed)]
        if args.replace:
            export.append("--force")
        subprocess.run(export, check=True)
        meta = json.loads((Path(folder) / "map.meta.json").read_text(encoding="utf-8"))
        provenance = {key: meta[key] for key in ("revision", "version", "tool", "coverage", "limits", "selection",
                                                   "rendering", "map_sha256", "parse_failures", "skipped", "truncated")}
        provenance.update(scope="src", focus_symbols=["JFTPApplication", "FTPSession", "FavoritesManager"],
                          generation_elapsed_seconds=elapsed, working_copy_fingerprint=meta["working_copy_fingerprint"],
                          report_note="Raw inventory and full metadata are regenerated into ignored artifacts/.")
        (REPORT.parent / "jftp-overview.rust.provenance.json").write_text(
            json.dumps(provenance, indent=2) + "\n", encoding="utf-8", newline="\n")


if __name__ == "__main__":
    try:
        main()
    except (RuntimeError, OSError, ValueError, subprocess.CalledProcessError) as error:
        print(f"repo-map: {error}", file=sys.stderr)
        raise SystemExit(1)
