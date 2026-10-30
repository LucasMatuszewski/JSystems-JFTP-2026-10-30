# Scheduled local Codex continuation

`tools/resume-recurring.sh` resumes one existing session at an explicit first
deadline, then on a fixed **18,600-second (5 hour 10 minute)** cadence. It uses
the tested one-shot helper for each attempt and runs children serially. A long
run causes missed slots to be skipped; no overlapping or catch-up calls are
started. A failed attempt is logged, and the next scheduled slot remains armed.

Use Git Bash on Windows, not the Windows WSL launcher. Supply the verified Codex
CLI executable rather than an app-server daemon from an old version directory.
For this launch the application bin link resolves to, and pins, the standalone
0.166.0 executable; it does not select the old app-server-daemon component.
The installed CLI 0.166.0 was checked with help, authenticated login status and
a deliberately absent-session probe. The probe reached thread lookup without
making a model call. The execution command is:

```bash
codex exec --approve-for-me resume SESSION_UUID - < prompt.txt
```

Do not add `--sandbox`: it conflicts with `--approve-for-me`. See the
[official command reference](https://learn.chatgpt.com/docs/developer-commands?surface=cli)
and [non-interactive resume guide](https://learn.chatgpt.com/docs/non-interactive-mode).
The installed executable's capabilities were checked independently of the docs.

Example from Git Bash; replace the first deadline, session and CLI path:

```bash
bash tools/resume-recurring.sh \
  --at '2026-10-30T13:30:00+01:00' \
  --thread 'SESSION_UUID' \
  --codex '/absolute/path/to/verified/codex' \
  --state-dir '.local/resume-recurring' \
  --prompt-file 'tools/resume-work-prompt.txt' \
  --activity-file '/absolute/path/to/the/session/rollout.jsonl'
```

The default activity guard waits for five quiet minutes before invoking Codex.
This uses the rollout file's modification time, not a quota API. The machine
must be running and awake; this Bash monitor neither wakes nor reboots Windows.
`--max-runs N` limits test/demo runs; zero, the default, keeps scheduling until
cancelled. Each attempt is claimed before launch, with its own logs and exit
code. A schedule lock prevents a second monitor in the same state directory.
Do not clear an existing lock while its monitor or child may still be alive.

The live instance for this request uses the ignored state directory
`.local/resume-recurring-2026-10-30/`. Its first deadline is **13:30 Warsaw on
2026-10-30**, followed by **18:40, 23:50, 05:00** and the same elapsed cadence.
`armed.json` records the exact executable, deadline and launch receipt;
`next-at`, `monitor.log` and `attempts/` expose the schedule and outcomes.
Per-machine launchers, session paths and execution logs remain outside Git.

Cancel future attempts with:

```powershell
New-Item -ItemType File -Path .local/resume-recurring-2026-10-30/CANCEL -Force
```

Cancellation is checked before launching Codex and while waiting for activity.
It does not forcibly terminate a Codex run already in progress. The continuation
prompt also requires later user pauses/cancellation to be honored.

Ten observable tests pass using real Bash processes and a local fake executor:
deadline/time-offset behavior, exact resume arguments with no conflicting flag,
single attempts, activity deferral, shared cancellation, recurring failure
recovery, no overlap and duplicate-monitor exclusion. No model is invoked by
these tests:

The [saved verification receipt](testing/evidence/resume-recurring/result.json)
includes the red/green runs and real CLI parser probe.

```powershell
python -m unittest tests.test_resume_monitor tests.test_recurring_resume_monitor -v
```
