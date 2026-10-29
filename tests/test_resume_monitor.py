"""Observable Bash timer checks; the executor is a local script, never a model call."""

import os
from pathlib import Path
import shutil
import subprocess
import tempfile
import time
import unittest


ROOT = Path(__file__).resolve().parents[1]
BASH = shutil.which("bash") if os.name != "nt" else r"C:\Program Files\Git\bin\bash.exe"


def bash_path(path):
    value = str(Path(path).resolve()).replace("\\", "/")
    return f"/{value[0].lower()}{value[2:]}" if os.name == "nt" else value


class ResumeMonitorTests(unittest.TestCase):
    def setUp(self):
        local = ROOT / ".local" / "resume-tests"
        local.mkdir(parents=True, exist_ok=True)
        self.temp = tempfile.TemporaryDirectory(prefix="jftp-resume-test-", dir=local)
        self.addCleanup(self.temp.cleanup)
        self.base = Path(self.temp.name)
        self.calls = self.base / "calls.txt"
        self.executor = self.base / "executor.sh"
        self.executor.write_text(
            '#!/usr/bin/env bash\nprintf "%s\\n" "$*" >> "$TEST_CALLS"\ncat > "$TEST_PROMPT"\n',
            encoding="utf-8", newline="\n",
        )
        self.prompt = self.base / "prompt.txt"
        self.prompt.write_text("Continue the authorized work.\n", encoding="utf-8")
        self.env = {**os.environ, "TEST_CALLS": bash_path(self.calls),
                    "TEST_PROMPT": bash_path(self.base / "received.txt")}

    def command(self, delay=0, state="run", *extra):
        return [BASH, bash_path(ROOT / "tools/resume-after-reset.sh"),
                "--at", str(int(time.time()) + delay),
                "--thread", "01a187b8-7f7f-7183-8185-b9cb82257e8d",
                "--codex", bash_path(self.executor),
                "--state-dir", bash_path(self.base / state),
                "--prompt-file", bash_path(self.prompt),
                "--quiet-seconds", "0", "--poll-seconds", "1", *extra]

    def run_monitor(self, command):
        return subprocess.run(command, env=self.env, capture_output=True, text=True,
                              encoding="utf-8", timeout=15)

    def test_waits_until_deadline_then_resumes_exact_thread_once(self):
        command = self.command(3)
        process = subprocess.Popen(command, env=self.env, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        try:
            time.sleep(0.6)
            self.assertFalse(self.calls.exists(), "Executor must not run before the target")
            stdout, stderr = process.communicate(timeout=15)
            self.assertEqual(process.returncode, 0, (stdout, stderr))
        finally:
            if process.poll() is None:
                process.kill()
                process.communicate()
        calls = self.calls.read_text().splitlines()
        self.assertEqual(len(calls), 1)
        self.assertIn("--approve-for-me resume", calls[0])
        self.assertNotIn("--sandbox", calls[0], "--approve-for-me already selects workspace-write")
        self.assertIn("01a187b8-7f7f-7183-8185-b9cb82257e8d -", calls[0])
        self.assertEqual((self.base / "received.txt").read_text(), self.prompt.read_text())
        second = self.run_monitor(command)
        self.assertNotEqual(second.returncode, 0)
        self.assertEqual(self.calls.read_text().splitlines(), calls)

    def test_cancel_file_prevents_a_model_call(self):
        state = self.base / "cancelled"
        state.mkdir()
        (state / "CANCEL").touch()
        result = self.run_monitor(self.command(0, "cancelled"))
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertFalse(self.calls.exists())

    def test_recent_session_activity_defers_execution(self):
        activity = self.base / "activity.jsonl"
        activity.touch()
        command = self.command(0, "busy", "--activity-file", bash_path(activity),
                               "--quiet-seconds", "3")
        process = subprocess.Popen(command, env=self.env, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        try:
            time.sleep(0.6)
            self.assertFalse(self.calls.exists())
            stdout, stderr = process.communicate(timeout=15)
            self.assertEqual(process.returncode, 0, (stdout, stderr))
        finally:
            if process.poll() is None:
                process.kill()
                process.communicate()
        self.assertEqual(len(self.calls.read_text().splitlines()), 1)


if __name__ == "__main__":
    unittest.main()
