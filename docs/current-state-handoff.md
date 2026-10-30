# JFTP modernization handoff — 2026-10-30

**Latest state:** [scheduled resume handoff](scheduled-resume-handoff.md) supersedes
the counts, native-access state and ZIP implementation status below. It records
the working-tree implementation, 85-case package, startup-locale correction and
the user's 13:30 continuation schedule. Earlier milestones are retained here.

The active objective is incremental modernization of the existing Java desktop
client: keep a verified regression baseline, improve the runtime and security,
apply the supplied JSystems brand, and validate real desktop and server workflows.
The work below advances that objective. The branded desktop implementation is in
the working tree; its runtime commit awaits final native QA on an active desktop.

## Verified application baseline

- Original characterization was established before production changes, against
  application revision `14e62ceba4e371c2a0b955604b10f065f46f4f7d`. Production Java
  now has the uncommitted branded/English desktop change described below.
- The committed build-recovery change `3c0ba84` pins Maven 3.10.0 through the
  wrapper and retains Java 8 bytecode compatibility on JDK 25. Production Java,
  resources, and application dependencies remain unchanged.
- **80 scoped cases pass on the working tree:** 65 local/model/archive/desktop
  cases (48 earlier baseline, three extra original-extractor cases and 14 new
  desktop cases), ten real FTP contract cases, and five real
  explicit FTPS contract cases. These are
  separate suites, not full native-application acceptance. See
  [persistence evidence](testing/persistence.md),
  [FTP contract evidence](testing/ftp-contract.md), and
  [FTPS contract evidence](testing/ftps-contract.md).
- The FTPS cases establish encrypted component transfers with the tested server
  and configuration. They do not establish that the legacy custom trust manager
  has correct PKIX or hostname validation.

## Build and packaged desktop evidence

The launcher source fix passed three archive checks. The actual Windows package
was launched from a path containing spaces while using a different current
directory and an isolated synthetic profile. Native inspection confirmed the
workspace, opening a second session with Ctrl+N, and closing it with Ctrl+Shift+C.
Screenshots are [workspace](testing/screenshots/2026-10-29-packaged-workspace.jpg),
[two sessions](testing/screenshots/2026-10-29-packaged-two-sessions.jpg), and
[after close](testing/screenshots/2026-10-29-packaged-after-close.jpg). The run is
documented in [packaged launcher verification](testing/packaged-launcher.md).
The subsequent [native FTP run](testing/native-ftp.md) verified connection,
binary download, upload, directory browsing, recursive download, disconnect and
reconnect through the actual desktop. Three transferred files were byte-identical
to their originals; eleven screenshots and a complete real-server log are saved.
The original fixture stopped. Owned QA application instances have since been
closed for rebuilds. Native FTPS and button-driven abort/interrupted-transfer
behavior remain unverified. Three new same-thread cancellation/reconnect
component cases pass, and one controlled ineffective-abort mutation makes exactly
those three fail; the normal client then passes all ten FTP cases again.

The latest normal wrapper `clean package` command passed with 62 tests and zero
failures/errors/skips, followed by five archive checks. The separate launcher fix
is committed as `2973a21`. The pre-existing release-8/deprecation and assembly
directory-attachment diagnostics remain documented.
After adding three archive characterizations, a fresh ordinary `test` run passes
65 cases; the application package itself is unchanged. The
[extractor compatibility report](testing/archive-compatibility.md) records the
original-source check and the controlled single-read mutation that fails exactly
the new large-entry case, followed by three original-code passes.

## Active desktop gate and implementation

[Branded desktop evidence](testing/branded-desktop.md) records the earlier real
1000-pixel window, Connect cancellation, session controls, fixed-theme/English
preferences, synthetic favorite loading and real directory listing, with eleven
screenshots. It deliberately does not claim final native transfer acceptance.

The last adjustment disables FlatLaf's optional native library after observing a
new JDK warning. Windows desktop access has returned: the copied candidate creates
a responding `JSystems FTP` window on Temurin 25.0.5+8, with no native-library
warning. The current gate is different: both Computer Use tools report the native
pipe unavailable, including retry/reset. A request to restore Computer Use is
pending. Process/window metadata is not visual or flow acceptance. The owned app
was stopped. The idle fixture did not exit after STOP; its identity-verified child
was terminated and child/supervisor exit were verified. A real fixture-lifecycle
regression now passes after bounding the first idle socket wait; see
[fixture lifecycle](testing/fixture-lifecycle.md). Ten FTP and five FTPS cases
also pass again, with both fixture supervisors exiting zero. No owned app or
server remains.
Do not alter desktop/security settings as a workaround.

Uncommitted scope: FlatLaf dependencies and packaged logo/theme, new
`DesktopBootstrap`, `DesktopAppearance`, `BrandHeader`, EDT startup, main-frame
header/title/minimum size, table row minimum, English local file descriptions,
fixed appearance/locale preferences, four test classes, archive checks,
README and Java CRLF whitespace attributes. Preserve these edits and finish
native QA before the runtime commit. The English desktop SVG omits only the
separate Polish caption and preserves every mark/wordmark path and the view box;
the original reference remains intact. Asset/provenance commit: `e9706bf`.
The current candidate JAR SHA-256 is
`85d95eec316eec3a38b8afacfab1415ebe793a935839e53104617232cc0940ec`.
The strengthened resource-loading assertion detects a missing compiled SVG
(one expected failure in five cases), then all five pass after restoration.
The full package and direct-source runner each pass 62 cases. Follow-up evidence
is in `docs/testing/evidence/branded-desktop-followup/`.

Distribution documentation is separately fixed in `f361d46`: a standalone user
README replaces repository-only links in the ZIP, with a real-archive link test.
Commit `73c2c87` keeps the direct-source runner aligned with the desktop asset.

## Java platform readiness

[ADR-004](ADR/004-java25-platform.md) selects the latest LTS baseline, Java 25.
Temurin 25.0.5+8 was downloaded from the official release, checksum-verified and
extracted into a private cache; global Java/PATH were unchanged. All 77 scoped
cases passed with release-25 application/test classes (major version 69).
[Readiness evidence](testing/java25-readiness.md) preserves that probe.

The normal POM still targets release 8, and a clean normal package was restored
after the override probe (major version 52). The current candidate JAR remains
available under `target/jftp-5.0.2-SNAPSHOT-bin/jftp-5.0.2-SNAPSHOT/`.
The local verified JDK is identified by `.local/jdk25-latest.json`.
Do not confuse compatibility proof with adoption or final native acceptance.

## Saved source context — refresh pending

- RepoMap: 191 Java files, 1,688 declarations, 1,033 selected symbols.
- Code graph: 5,968 nodes, 18,419 edges, 573 files.
- Repomix: 59 files, 66,300 tokens.

These are the pre-branding snapshot measurements. Current source and new test
changes make the saved manifest stale; refresh RepoMap, graph and focused Repomix
after the verified runtime change, in separate commits. The historical original
source cards remain checked against revision `14e62ce`.

## Resume monitor

The requested Bash monitor fired at **17:10 Warsaw**. Its first invocation failed
before any model call because two CLI flags were mutually exclusive. Commit
`41f41e3` corrected the command and regression check: `--approve-for-me` selects
workspace-write and must not be combined with `--sandbox`. Three timer tests pass,
and a real CLI missing-session probe confirms argument parsing reaches session
lookup. The session was already active afterward, so no second monitor or retry
was armed. Original local attempt markers and logs remain under ignored
`.local/resume-monitor`.

## Next coordinator work

1. Restore Computer Use's native pipe; Windows itself is already active. The
   previous owned app and fixture have stopped.
2. When native control returns, launch a copied final distribution using
   the verified JDK, complete native upload/download byte checks and final
   screenshots, then commit the scoped desktop implementation. The helper under
   `.local/launch-branded.py` now selects the verified cached JDK explicitly,
   including the launcher's process-local PATH. Use owned synthetic fixtures only.
3. Adopt release 25 and retire the applet/unused Apple adapter under ADR-004,
   retaining the actively used macOS integration until its separate migration.
4. Address transport trust, archive containment, credential storage and worker/UI
   boundaries through specified changes and tests. Extend native verification to
   FTPS, error and F12/interrupted-transfer outcomes. Refresh current docs and
   source-context artifacts after verified changes.

Preserve unrelated user work in `docs/session-analyses/`; do not stage it as part
of these milestones. Full modernization, security remediation and final brand
acceptance remain incomplete. Do not push.

The next archive-security change is specified in the
[contained extraction PRD](prd/archive-containment.md) and
[ADR-005](ADR/005-contained-archive-extraction.md). They define entry/path
validation before output, redirected-path rejection, staged complete-file
publication, resource cleanup and an explicit stable-filesystem threat boundary.
The extractor itself is still unchanged; the design is not an implemented fix.
