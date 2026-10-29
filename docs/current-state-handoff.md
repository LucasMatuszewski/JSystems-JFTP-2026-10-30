# JFTP takeover progress — 2026-10-29

The active objective remains: recover and test the application, establish regression
and characterization coverage, modernize the Java desktop incrementally, apply the
supplied JSystems brand/logo, launch and exercise real flows, and retain screenshots
and evidence. This documentation milestone does not complete that objective.

## Completed baseline documentation

- The inherited workflow is scoped to JFTP in root `AGENTS.md`, with English-only work and its test/manual-QA/commit requirements retained.
- [PRD](PRD.md) records current user-visible behavior and self-answered intake questions.
- [ADR-000](ADR/000-main-architecture.md) records architecture, interfaces, data, transport/trust and packaging constraints.
- [ADR-001](ADR/001-baseline-verification.md) defines the characterization-first verification gate.
- [Evidence register](current-state-evidence.md) contains 51 checked original-source cards plus matching external FTPAPI artifact hashes.
- [UML views](uml/README.md) contain six Mermaid diagrams and offline SVGs. All six plus nine diagrams in the PRD/ADRs render successfully in Chrome with Mermaid 11.12.1; source/hash and link checks pass. A class-diagram preview was visually inspected.
- RepoMap, offline code graph and focused complete-source Repomix pack are committed with their guides and verifiers. Tool verification does not count as Java application tests.

At that documentation checkpoint, application source, resources, original README and POM matched original
revision `14e62ceba4e371c2a0b955604b10f065f46f4f7d`. No production refactor or
branding integration has begun. The untracked `docs/session-analyses/` belongs to
separate user work and must be preserved without staging it incidentally.

## Next work in order

**Current build work in progress:** the wrapper now pins Maven 3.10.0 with a
verified distribution SHA-256. The modified POM passes all 35 tests through
Surefire 3.6.1 and produces ZIP/tar/directory assemblies. Three actual-archive
checks first failed on both bad launcher JAR paths and accidental AGENTS.md
inclusion, then passed after working-tree launcher/assembly fixes. These runtime
and build edits are still uncommitted pending packaged native startup verification
and source-context refresh. Keep them separate when committing: build recovery,
launcher/distribution fixes, then evidence/context updates.

A packaged app was launched from `.local/packaged launch with spaces/app/jftp.bat`
with a synthetic profile and a different working directory. Its launcher PID and
logs are under `.local/packaged launch with spaces/`. The older baseline app may
also remain open (original baseline PID 12364). Computer Use reported a physical
Escape stop when inspection resumed after the user's resume message; no new native
window observation was obtained, so do not claim packaged startup visually passed.
Do not issue further desktop input until Computer Use is resumed by the user.
Continue independent CLI/source work meanwhile. The current frozen AS-IS source
cards can be checked using the evidence helper's `--revision 14e62ce`; the current
working-tree POM and launcher changes intentionally differ from that historical
baseline. Refresh live context packs/graphs and add current build instructions
before the build/distribution commits. Relevant logs are in `.local/baseline/`:
`maven-recovered-test.log`, `maven-recovered-package.log`, `distribution-red.log`
and `distribution-green.log`.

**Later executed milestone:** [Baseline verification](testing/README.md) now records
181 unchanged production files compiled for Windows, 35 passing characterization
tests, three detected controlled mutations and actual native startup/session
screenshots. This supplements the historical pending statements below. The original
Maven path still fails on its HTTP repository; live FTP/FTPS and packaging remain
pending. Continue with normal Maven build recovery and broader tests before runtime
refactoring or brand changes.

1. Read the PRD/ADRs and inspect current Git state. Recover build/test execution while retaining unchanged application behavior. Verify current official versions before selecting modernization targets.
2. The installed Temurin JDK 25 rejects source/target 1.5 in a standalone probe; Maven was not on PATH. A full application build has not passed. The publisher's matching FTPAPI 3.0.0 artifacts are cached at `~/.jftp-build-cache/legacy-ftpapi-3.0.0`; hashes/HTTPS URLs are in the evidence register. JavaHelp and remaining build dependencies/plugins still need resolution.
3. Establish green characterization on original application source, including filters, host defaults/normalization, transfer types, synthetic persistence and ZIP behavior. Demonstrate critical assertions fail under a controlled mutation, then revert it. Use Luna only for tightly specified simple work; the coordinator makes architecture decisions.
4. Launch the actual native desktop with a synthetic isolated profile. Capture baseline screenshots. Use an authorized real test server and verify actual file bytes/state for transfers, abort/reconnect and FTPS. No native startup or successful FTP run has been demonstrated yet.
5. Make separate specified changes for security defects and modernization. Known source findings include archive paths without a containment guard, custom server trust without delegated PKIX checking, embedded-key favorites storage, unsafe-looking worker/UI interactions and an apparent launcher/assembly path mismatch. Do not conceal changed behavior as a pure refactor.
6. Apply the supplied brand and right-side logo only after baseline protection; compare real touched-screen screenshots with `assets/homepage.png` and `docs/design-guidelines.md`. Refresh affected context snapshots and use scoped verified commits; do not push.

## Resume monitor outcome

The requested Bash monitor fired at **17:10 Warsaw**. Its first invocation failed
before any model call because two CLI flags were mutually exclusive. The command
and regression check were corrected in commit `41f41e3`: `--approve-for-me` selects
workspace-write and must not be combined with `--sandbox`. Three timer tests pass;
a real CLI missing-session probe confirms argument parsing reaches session lookup.
This session was already active afterward, so no second monitor or retry was armed.
The original local attempt marker/logs remain under ignored `.local/resume-monitor`.
