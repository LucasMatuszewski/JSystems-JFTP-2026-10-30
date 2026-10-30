# Resume handoff — quota wait, 2026-10-30

The user requested a pause until **13:30 Europe/Warsaw today**, then recurring
continuations every **5 hours 10 minutes**. See [monitor instructions](resume-monitor.md).
This is a scheduled continuation of the full goal, not its completion. Respect
any later user pause/cancellation. Do not create a second monitor.

## Current application work

Two runtime changes remain **uncommitted** and need separate, verified commits:

1. Branded English Swing desktop: FlatLaf, right-side desktop SVG, EDT bootstrap,
   fixed theme/locale preferences and readable file descriptions. The accepted
   PRD and ADR-003 describe it. A real German-startup native run exposed German
   shortcut words despite English menus. A fresh-JVM test reproduced
   `CTRL=Strg`, `SHIFT=Umschalt`, `LOCALE=en`; moving the English default before
   AWT/EDT initialization fixes that test. The final native rerun is pending.
2. ZIP containment: `ArchiveEntryPath`, `Unzip`, `DownloadAndUnzipAction` resource
   closure and five test classes. The original extractor failed nine intended
   assertions among eleven real-filesystem cases. The working tree now preflights
   paths/collisions, rejects redirected/dangling junctions, writes temporary
   siblings, verifies size/CRC and replaces complete outputs without changing an
   outside hard-link name. A dangling-junction regression was added red then green.
   This is partial verification, not a completed security release.

Latest ordinary wrapper **package passes 85 cases, zero failures/errors/skips**.
The fresh-JVM startup case is included. Five archive checks pass. The current
assembled application JAR is SHA-256:

`a7fe3912fcc9b6c5914cdfbb272ec645bfbcff9229a2f0ced80f5a7e020c6a34`

The preceding security candidate passed ten real FTP and five real FTPS cases,
both fixtures stopped normally. Its assembled helper smoke test extracted
77,071 exact bytes and rejected a valid-first/unsafe-later ZIP without changing
the owned outside sentinel or creating the first entry. That smoke test used the
earlier JAR `92eec8…`, before the unrelated startup-locale correction.

Relevant ignored logs: `.local/final-desktop-package.log`,
`.local/final-desktop-archive-checks.log`, `.local/desktop-startup-locale-red.log`,
`.local/archive-containment-red-final.log`, `.local/archive-security-local-tests.log`,
`.local/archive-dangling-redirect-{red,green}.log`,
`.local/archive-security-{ftp,ftps}.log` and
`.local/archive-package-smoke-latest.json`. Preserve evidence before rebuilding.
The main POM still targets release 8. Java 25 adoption and obsolete applet/adapter
retirement remain subsequent work under ADR-004; do not claim adoption from the
earlier readiness probe. Use the verified cached JDK in `.local/jdk25-latest.json`.

## Open acceptance gates

Native control **returned** before this pause. Computer Use uses the available
skill and `@oai/sky` through the JS tool; fresh window selection is required.
The last owned JFTP process was stopped before rebuilding. The owned loopback
server was stopped for the quota pause. No test server should be assumed alive.

Restart a fresh fixture using `.local/native-transfer-setup.py`, then launch a
copied assembled distribution with `.local/launch-branded.py`. The helper selects
the verified JDK explicitly, seeds an isolated German/legacy-theme profile and
synthetic favorite, and copies the package to avoid Windows JAR locks. It creates
an owned outside ZIP sentinel in the run directory. Start a new fixture first:
the saved runtime JSON refers to a stopped port.

Inspect the final English shortcut labels, header/logo and untouched screen
layout against the brand reference; exercise new/close session, Connect cancel,
preferences/favorite loading, upload and binary download with byte comparisons.
Then run safe/unsafe Download and Unzip via the real UI. Use the dialog's current
local archive-location choice so those tests retain the ZIP. The fixture contains
`safe-archive.zip` and `unsafe-archive.zip`; the unsafe entry is
`../outside-native.bin` and its first entry is `unsafe-first.bin`. Verify the
outside sentinel stays unchanged, no first-entry output exists, the ZIP remains,
and error/busy/local-refresh behavior is correct. Capture each touched screen.

The untracked `2026-10-30-final-*.jpg` files are **development observations** of
the earlier candidate, including the German shortcut defect. Some immediate
post-action screenshots lagged the Swing update (the two-session label captured
an earlier frame). Do not treat them as final acceptance; observe settled state
and capture accurately labelled screenshots on the new candidate.

The explicit `UnzipSymbolicLinkSecurityIT` suite has **three prerequisite errors,
zero skips**: Windows denies actual symbolic-link creation (WinError 1314).
Hard-link and junction cases pass. WSL lists Ubuntu 26.04 but cannot start:
`HCS_E_HYPERV_NOT_INSTALLED`. No security/virtualization settings were changed.
Run those real symlink cases in an authorized capable test environment before
claiming all ZIP criteria. Do not substitute mocks or skips for this gate.

## Commit and completion discipline

The committed baseline has 51 cases. Branding adds 15, for 66 in its independent
scope; ZIP security adds 19, for 85 combined. Update branch-scoped README/counts
when committing, rather than claiming tests from another pending change.
Keep runtime commits separate and gated by actual UI acceptance. The new
`/src/**/*.java` whitespace rule in the working `.gitattributes` belongs to the
desktop change; the recurring-monitor rule is committed separately.

Refresh current RepoMap, graph and focused Repomix after verified runtime commits,
in separate commits. Historical source cards must remain checked against
revision `14e62ce`; current ZIP/action definitions now differ. PRD/ADR status and
Mermaid receipt updates for these runtime changes remain to be reconciled.
Preserve unrelated `docs/session-analyses/` work. Do not push. The full platform,
transport trust, credentials, worker/EDT, cancellation and native FTPS objectives
are still incomplete.
