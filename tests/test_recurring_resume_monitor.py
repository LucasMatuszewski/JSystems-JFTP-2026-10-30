"""Real Bash processes with a local fake executor; never invoke a model."""
from datetime import datetime, timedelta, timezone
import subprocess
import time

from tests.test_resume_monitor import BASH, ROOT, MonitorFixture, bash_path


class RecurringResumeMonitorTests(MonitorFixture):
    def command(self, delay=0, state="recurring", interval=3, runs=2, *extra):
        return [BASH, bash_path(ROOT / "tools/resume-recurring.sh"),
                "--at", str(int(time.time()) + delay),
                "--thread", "01a187b8-7f7f-7183-8185-b9cb82257e8d",
                "--codex", bash_path(self.executor),
                "--state-dir", bash_path(self.base / state),
                "--prompt-file", bash_path(self.prompt),
                "--interval-seconds", str(interval), "--max-runs", str(runs),
                "--quiet-seconds", "0", "--poll-seconds", "1", *extra]

    def run_monitor(self, command):
        return subprocess.run(command, env=self.env, capture_output=True, text=True,
                              encoding="utf-8", timeout=30)

    def wait_for_file(self, path, process, content=None):
        deadline = time.monotonic() + 10
        while time.monotonic() < deadline:
            if path.exists() and (content is None or content in path.read_text()):
                return
            if process.poll() is not None:
                break
            time.sleep(0.05)
        self.fail(f"Monitor did not reach expected state: {path}; exit={process.poll()}")

    def test_offset_deadline_and_recurring_calls_keep_valid_resume_arguments(self):
        command = self.command(3, "state with spaces")
        target = int(command[command.index("--at") + 1])
        command[command.index("--at") + 1] = datetime.fromtimestamp(
            target, timezone(timedelta(hours=1))).isoformat()
        self.env["TEST_EVENTS"] = bash_path(self.base / "events.txt")
        process = subprocess.Popen(command, env=self.env, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        try:
            time.sleep(0.5)
            self.assertFalse(self.calls.exists(), "No call before the requested deadline")
            stdout, stderr = process.communicate(timeout=30)
            self.assertEqual(0, process.returncode, (stdout, stderr))
        finally:
            if process.poll() is None:
                process.kill()
                process.communicate()
        calls = self.calls.read_text().splitlines()
        self.assertEqual(2, len(calls))
        for call in calls:
            self.assertIn("exec --approve-for-me resume", call)
            self.assertNotIn("--sandbox", call)
            self.assertIn("01a187b8-7f7f-7183-8185-b9cb82257e8d -", call)
        starts = [int(line.split()[1]) for line in (self.base / "events.txt").read_text().splitlines()
                  if line.startswith("START")]
        self.assertGreaterEqual(starts[0], target)
        self.assertGreaterEqual(starts[1], target + 3)
        self.assertEqual(self.prompt.read_text(), (self.base / "received.txt").read_text())

    def test_long_execution_never_overlaps_the_next_cycle(self):
        self.env["TEST_SLEEP"] = "3"
        self.env["TEST_EVENTS"] = bash_path(self.base / "events.txt")
        result = self.run_monitor(self.command(interval=1))
        self.assertEqual(0, result.returncode, result.stderr)
        events = (self.base / "events.txt").read_text().splitlines()
        self.assertEqual(["START", "END", "START", "END"], [line.split()[0] for line in events])
        self.assertGreaterEqual(int(events[2].split()[1]), int(events[1].split()[1]))

    def test_failed_attempt_is_logged_and_next_scheduled_cycle_still_runs(self):
        self.env["TEST_EXIT_CODE"] = "7"
        result = self.run_monitor(self.command(interval=2))
        self.assertEqual(0, result.returncode, result.stderr)
        self.assertEqual(2, len(self.calls.read_text().splitlines()))
        codes = list((self.base / "recurring" / "attempts").glob("*/exit-code"))
        self.assertEqual(2, len(codes))
        self.assertEqual(["7", "7"], sorted(path.read_text().strip() for path in codes))

    def test_cancel_before_deadline_prevents_execution(self):
        state = self.base / "cancelled"
        state.mkdir()
        (state / "CANCEL").touch()
        result = self.run_monitor(self.command(10, "cancelled"))
        self.assertEqual(0, result.returncode, result.stderr)
        self.assertFalse(self.calls.exists())

    def test_second_monitor_cannot_claim_the_same_schedule(self):
        command = self.command(20, "locked")
        process = subprocess.Popen(command, env=self.env, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        try:
            self.wait_for_file(self.base / "locked/monitor.pid", process)
            duplicate = self.run_monitor(command)
            self.assertEqual(3, duplicate.returncode, duplicate.stderr)
            (self.base / "locked/CANCEL").touch()
            stdout, stderr = process.communicate(timeout=15)
            self.assertEqual(0, process.returncode, (stdout, stderr))
        finally:
            if process.poll() is None:
                process.kill()
                process.communicate()
        self.assertFalse(self.calls.exists())

    def test_cancel_propagates_while_session_activity_defers_execution(self):
        activity = self.base / "activity.jsonl"
        activity.touch()
        command = self.command(0, "busy", 3, 2, "--activity-file", bash_path(activity),
                               "--quiet-seconds", "60")
        process = subprocess.Popen(command, env=self.env, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        try:
            self.wait_for_file(self.base / "busy/active-cycle", process)
            cycle = int((self.base / "busy/active-cycle").read_text().strip())
            self.wait_for_file(self.base / f"busy/attempts/{cycle}/monitor.log", process, "WAITING")
            self.assertFalse(self.calls.exists())
            (self.base / "busy/CANCEL").touch()
            stdout, stderr = process.communicate(timeout=15)
            self.assertEqual(0, process.returncode, (stdout, stderr))
        finally:
            if process.poll() is None:
                process.kill()
                process.communicate()
        self.assertFalse(self.calls.exists())
