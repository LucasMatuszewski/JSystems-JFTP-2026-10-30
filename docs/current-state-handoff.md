# JFTP modernization handoff — 2026-10-29

The active objective is incremental modernization of the existing Java desktop
client: keep a verified regression baseline, improve the runtime and security,
apply the supplied JSystems brand, and validate real desktop and server workflows.
The work below advances that objective; it does not complete modernization or
branding.

## Verified application baseline

- Production Java sources remain unchanged from application revision
  `14e62ceba4e371c2a0b955604b10f065f46f4f7d`. Runtime behavior changes have not
  been made.
- The committed build-recovery change `3c0ba84` pins Maven 3.10.0 through the
  wrapper and retains Java 8 bytecode compatibility on JDK 25. Production Java,
  resources, and application dependencies remain unchanged.
- **60 scoped cases pass:** 48 local/model/archive characterization cases, seven
  real FTP contract cases, and five real explicit FTPS contract cases. These are
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
This does not verify FTP/FTPS transfers through the native UI.

The fresh wrapper `package` command passed with 48 tests and zero failures,
errors or skips. The separate packaging fix is committed as `2973a21`.
The pre-existing assembly directory-attachment warning remains documented.

## Refreshed source context

- RepoMap: 191 Java files, 1,688 declarations, 1,033 selected symbols.
- Code graph: 5,968 nodes, 18,419 edges, 573 files.
- Repomix: 59 files, 66,300 tokens.

The refreshed snapshots identify their base working-tree revision metadata
explicitly. They are navigation aids; original source and callers remain the
evidence for consequential behavior.

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

1. Decide the supported current Java runtime target and address transport trust,
   archive containment, and credential-storage defects as separately specified
   changes.
2. Exercise native transfers against an authorized real FTP/FTPS server, including
   byte integrity, error and abort/reconnect outcomes.
3. Apply the approved English UI and JSystems right-side logo work, with native
   screenshots compared against the supplied design reference.

Preserve unrelated user work in `docs/session-analyses/`; do not stage it as part
of these milestones. No modernization, security remediation, or brand integration
is claimed complete here.
