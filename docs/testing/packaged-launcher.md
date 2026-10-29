# Packaged launcher verification — 2026-10-29

The expected distribution contract is an application JAR at the archive root,
runtime dependencies under `lib`, and launchers that work independently of the
caller's current directory. Agent instructions do not belong in the application
distribution. This fixes packaging without changing production Java or resources.

## Red, green and native QA

Three archive checks first failed: both launchers referenced a missing `lib/jftp`
JAR, and the wildcard document selection included `AGENTS.md`. After fixing the
launcher paths and selecting product documents explicitly, all three checks pass:

```powershell
.\mvnw.cmd package
python -m unittest discover -s tests -p test_distribution.py -v
```

The actual assembled Windows distribution was copied to the ignored directory
`.local/packaged launch with spaces/app` and launched through its `jftp.bat` from
the repository root, with an isolated synthetic Java profile and English locale.
Process inspection confirmed the Java child of the recorded launcher executed
the application JAR from that directory. No user connection profiles were used.

Computer Use inspected the real window, opened a second session with Ctrl+N,
and closed it with Ctrl+Shift+C. The original synthetic `welcome.txt` remained
visible afterward. The launcher log contained only the expected
`JAVA_TOOL_OPTIONS` notice and no application errors. Compilation, tests, archive
inspection and native startup are separate checks; these session checks do not
establish native FTP/FTPS transfer acceptance.

| Observed screen | Screenshot |
|---|---|
| Initial packaged workspace | [Workspace](screenshots/2026-10-29-packaged-workspace.jpg) |
| Second session opened | [Two sessions](screenshots/2026-10-29-packaged-two-sessions.jpg) |
| Extra session closed | [After close](screenshots/2026-10-29-packaged-after-close.jpg) |

## Visual comparison and limits

Compared with `assets/homepage.png` and `docs/design-guidelines.md`, this remains
the original dense gray Swing interface, with small icons and no JSystems logo,
burgundy primary actions or brand typography. Windows shell labels still include
Polish text despite the application's English locale. These are recorded baseline
differences for the separate branding/English UI work, not visual improvements
claimed by the launcher fix. Unix execution has not been manually validated.
