# One-time local Codex resume monitor

`tools/resume-after-reset.sh` waits until an explicit deadline, then makes one
`codex exec --sandbox workspace-write --approve-for-me resume SESSION_ID -` call.
The installed CLI's `exec` and `exec resume --help` were checked on 2026-10-29
(version 0.165.0). This follows the official [non-interactive session guide](https://developers.openai.com/codex/noninteractive).
It reuses existing local authentication; no credentials are copied into the repository.

For this session the requested target is **2026-10-29 17:10 Europe/Warsaw
(16:10 UTC)**, after the user's reported 17:04 reset. The script does not query
or guarantee quota availability. If Codex still fails, the attempt is logged and
is not retried automatically.

Run from Git Bash, substituting your own executable, thread, prompt and activity
paths. Keep private state under the ignored `.local/` directory:

```bash
bash tools/resume-after-reset.sh \
  --at '2026-10-29T17:10:00+01:00' \
  --thread 'SESSION_UUID' \
  --codex '/c/path/to/codex.exe' \
  --state-dir "$PWD/.local/resume-monitor" \
  --prompt-file "$PWD/.local/resume-monitor/prompt.txt" \
  --activity-file '/c/path/to/the/session/rollout.jsonl'
```

The machine must remain running. After sleep, execution waits until the machine
wakes; targets more than 12 hours old expire. The script checks every 30 seconds
and defers until the session file has been quiet for five minutes. File activity
is a heuristic, not an authoritative Codex busy status: a silent long-running
operation may look idle. Avoid leaving such an operation running at the target.

To cancel, create `.local/resume-monitor/CANCEL`. Inspect `monitor.log` for timer
and attempt status, `resume.stdout.log` / `resume.stderr.log` for the resumed CLI,
and `exit-code` for its result. Logs can contain project context; keep them local.
An atomic directory lock prevents simultaneous monitors using the same state
directory. The `attempted` marker prevents repeat calls after restart. Use a
new directory only when deliberately scheduling another attempt.

Verification (no model calls):

```bash
bash -n tools/resume-after-reset.sh
python -m unittest discover -s tests -p test_resume_monitor.py -v
```

The tests use a local recording shell script to verify the deadline, exact thread
and prompt, one-attempt behavior, cancellation, and deferral during recent activity.
