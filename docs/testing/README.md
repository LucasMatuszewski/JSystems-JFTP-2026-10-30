# JFTP baseline verification

This is the first executed application baseline, recorded on 2026-10-29 after the
source-derived [PRD](../PRD.md) and [ADRs](../ADR/000-main-architecture.md).
At this first milestone, production Java/resources and `pom.xml` matched application revision
`14e62ceba4e371c2a0b955604b10f065f46f4f7d`. No runtime modernization or branding
change is included in this milestone.

Later milestones expanded the local suite to [48 cases](persistence.md), added
[seven real FTP](ftp-contract.md) and [five real FTPS](ftps-contract.md) cases,
recovered the [Maven build](../build.md), and verified the
[packaged Windows launcher](packaged-launcher.md). The historical results below
remain the original baseline, rather than the current total.
The later [native FTP workflow](native-ftp.md) also passed real desktop upload,
download, recursive download, browsing, disconnect and reconnect checks.

## Reproduce the characterization run

Use Windows, PowerShell 7 and JDK 25 with `java` and `javac` on PATH:

```powershell
pwsh -NoProfile -File tools/legacy-baseline.ps1
```

The script verifies three pinned dependency hashes, downloads missing artifacts
over HTTPS into `~/.jftp-build-cache`, compiles the current checkout with
`--release 8`, copies the original resources, and runs every `*Test.java` class
through the original JUnit 3.8.1 runner. It fails on compile/test errors, zero
discovered classes or missing JUnit success counts. Every invocation uses a fresh
ignored `.local/baseline-runs/<id>` directory and isolated Java home/temp paths.
Logs and `result.json` contain per-class counts. No real server, user profile or
stored credentials are required for these tests.

This is a temporary legacy build route for the pre-modernization checkout. It is
not a claim that Java 8 is the final target, that the historical Maven build works,
or that these tests establish transfer/security compatibility.

## Results and limits

| Check | Executed result |
|---|---|
| Compile all original 182 Java sources | Failed: the obsolete `OSXAdapterOld` imports unavailable Apple APIs |
| Windows baseline compile | Passed for 181 unchanged production sources, excluding only `OSXAdapterOld.java` |
| Original Maven compile | Failed: Maven 3.10.0 blocks the original HTTP dependency repository |
| Characterization | **35 tests in five classes; zero failures, errors or skips** |
| Controlled mutations | All three detected; original production files were never edited |
| Real native startup | Passed, synthetic profile, JDK 25.0.4.1; captured workspace |
| Native session controls | Open/cancel Connect; create second tab; close selected tab; confirmed visually |
| Native application stderr | Empty during this scoped run |
| FTP/FTPS/server behavior | Not exercised yet |
| Distribution packaging/launchers | Not verified yet |

The active `OSXAdapter` is called by `JFTPApplication` and uses reflection.
A search of `src/main/java` found references to `OSXAdapterOld` only inside its own
file. Excluding that file establishes a Windows baseline, not macOS support.
Compilation emits three Java 8 compatibility warnings and legacy deprecated/raw
API notes; these are retained in the evidence rather than reported as warning-free.

## What the tests protect

| Class | Count | Observable behavior |
|---|---:|---|
| `RemoteHostCharacterizationTest` | 14 | Port normalization, host trimming, null/whitespace credentials, defaults, command formatting and favorite identity |
| `LocalFileCharacterizationTest` | 7 | Extension rules, directory handling, cached metadata, path equality and real directory listing |
| `LocalFileFilterCharacterizationTest` | 7 | Regex/date include/exclude sets, directory pass-through and real hidden-file behavior |
| `ModelMappingCharacterizationTest` | 2 | Null download mapping and non-null local/remote edit mapping using the actual dependency parser |
| `ZipCharacterizationTest` | 5 | Nested binary/empty/UTF-8 ZIP extraction, ZIP creation verified with JDK reader, unopened/closed errors |

Luna wrote bounded test drafts; the coordinator reviewed original code/dependency
APIs and ran the tests. An initial dotfile expectation was wrong: original
`LocalFile` treats `.hiddenname` as extension `HIDDENNAME`. The failing result and
source inspection led to correcting that characterization expectation, without
changing production behavior. The hidden-file test was also changed to require a
real hidden file rather than silently return on an unsupported attribute. No test
is recorded as passing through an early skip.

These are initial local/model/archive characterization tests. They do not yet
cover session orchestration, persistence, certificate validation, actual transfer
integrity, abort/reconnect races or remote editing end to end.

## Mutation evidence

The coordinator compiled modified **copies** of individual classes into isolated
directories and placed each before original classes on the test classpath. No
repository production file was changed. The final normal run used the original
classes and passed all 35 tests.

| Temporary change | Expected detection | Observed |
|---|---|---|
| Remove ordinary port normalization | Zero must map to FTP default | 1 failure in 14 tests |
| Bypass regex filtering | Inclusion/exclusion must select the correct real files | 2 failures in 7 tests |
| Remove extension uppercasing | Mixed-case suffix and leading-dot suffix change | 2 failures in 7 tests |

The logs and hashes are in [baseline-result.json](evidence/baseline-result.json).
Compiler/Maven logs replace only the machine-specific repository/home prefixes
with `<repo>`/`<user-home>`; test and mutation result logs retain their output.
This is evidence of those assertions detecting those changes, not a mutation score
for the entire application.

## Native UI observations and screenshots

The running baseline used a separate `.local/baseline/profile`, a synthetic
workspace containing `welcome.txt`, English Java locale flags, and original
preferences APIs to disable the background update check before startup. A small
temporary profile-setup class changed only that synthetic profile. It did not
patch the application or load the operator's stored favorites/credentials.

1. [Workspace](screenshots/2026-10-29-baseline-workspace.jpg): visible JFTP window, one disconnected tab, local synthetic file and empty remote pane.
2. [FTP menu](screenshots/2026-10-29-baseline-ftp-menu.jpg): Connect available; disconnected transfer actions disabled.
3. [Connect dialog](screenshots/2026-10-29-baseline-connect.jpg): blank host, anonymous default, masked default password and port 21; Escape cancelled back to the disconnected workspace. No login was submitted.
4. [Two sessions](screenshots/2026-10-29-baseline-two-sessions.jpg): Ctrl+N added a second selectable disconnected tab; the local listing populated after background work completed.
5. [After closing a session](screenshots/2026-10-29-baseline-after-close.jpg): Ctrl+Shift+C removed the selected tab, leaving the first session visible.

The rendered menu labels are **Local File**, **Remote File**, and **Transfer Mode**;
the original architectural class names use System/TransferMode terminology. Some
OS-supplied labels remain Polish (`Pulpit` and file type text) despite the English
application locale. This is a verified baseline limitation to fix deliberately,
not an English-only UI acceptance pass.

## Comparison with supplied brand reference

The coordinator visually inspected the native workspace and
[homepage reference](../../assets/homepage.png), and read the
[design guidelines](../design-guidelines.md).

| Reference | Current native baseline | Work still required |
|---|---|---|
| Burgundy primary `#800000` and controlled accents | Native gray/white controls and historical multicolor icons | Apply agreed brand tokens to the desktop UI |
| Supplied JSystems wordmark | Old FTP window icon, no JSystems wordmark in workspace | Add the requested right-side logo |
| Larger typography and clearer spacing | Small default text and a dense toolbar/table layout | Define desktop typography/spacing without sacrificing file-table usability |
| English project UI instruction | English resource strings plus Polish OS labels | Remove unintended OS-localized display labels in the relevant UI paths |

This is a comparison, not a claim of a visual match. Website promotional sections
are brand evidence, not features to copy into an FTP workspace.

## Gates identified at this baseline

The next gates at this checkpoint were to recover a normal pinned Maven build without changing production behavior; verify
packaging/startup; expand characterization to synthetic persistence and session
behavior; then add real authorized FTP/FTPS integration and native workflow checks.
Security fixes and branding need their own failing tests/change criteria and
before/after screenshots. See the [current handoff](../current-state-handoff.md)
for completed gates and remaining work. The overall modernization objective remains active.
