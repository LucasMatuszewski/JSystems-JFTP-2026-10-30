# JFTP: tool assessment and incremental modernization plan

Keep the Java desktop application and restore a reproducible development loop first. The shortest useful toolchain is direct search, the native repository map, original-source inspection, citation checking, and then a working Java build with behavior tests. Add source packs or graph navigation when a specific task benefits; defer semantic retrieval until measured search failures justify it.

## Scope and provenance

- Examined on 2026-10-29 at revision `14e62ceba4e371c2a0b955604b10f065f46f4f7d`.
- User direction: retain the Java desktop app and modernize incrementally.
- This is an investigation and proposed work sequence. Application source and `pom.xml` were not changed. No application build, launch, or transfer test has been claimed.
- Initial working tree had untracked `.agents/`, `.claude/`, `.codex/`, `assets/`, `docs/`, and `skills-lock.json`; tracked application files had no reported modifications. Existing design assets and documentation were preserved.
- Counts from tracked paths: 576 files; 182 Java files; 29,330 physical Java source lines, including blanks and comments. Package groups contain 113 Java files under `jftp`, 50 under `gui`, 15 under `util`, and 4 under `zip`.
- The tracked test directories contain only `src/test/java/.gitkeep` and `src/test/resources/.gitkeep`. The tracked-file search found no Maven wrapper or `.github` workflow. These are scoped repository observations, not statements about tests or CI elsewhere.
- Available on PATH: Python 3.14.9 and Temurin JDK 25.0.4.1, including `javac`, `jdeps`, `jdeprscan`, and `jpackage`. Maven and Repomix were not found on PATH; a full build was not attempted.
- A narrow compiler probe of the dependency-free original `Encoder.java`, with annotation processing disabled and the POM's `-source 1.5 -target 1.5`, exited 2 on this JDK: `Source option 5 is no longer supported. Use 8 or later.` and the corresponding target error. This proves that configuration cannot compile with this compiler; it is not a Maven build result. Command and output are saved in `artifacts/jftp-compiler-probe.json`.

## What the repository contains

| Area | Observed source and implication |
| --- | --- |
| Desktop startup | `JFTPApplication.main` constructs `JFTPApplication`; its constructor creates and shows `JFTP`, then opens a session. Inspect startup threading before changing it. [E03] |
| UI and orchestration | Swing components, actions, and dialogs call `FTPSession`. `FTPSession.java` has 1,667 lines and combines client setup, transfers, status, and UI coordination. This is a useful refactoring boundary, not evidence that the whole class should be rewritten. [E04, E05, E06, E16] |
| FTP implementation | Maven declares `com.myjavaworld:ftpapi:3.0.0`. `FTPSession` reflectively constructs the configured client and parser. The default class names point into `com.myjavaworld.ftp`; that implementation is outside this checkout's tracked Java sources. Its availability and behavior remain unresolved. [E01, E04, E07] |
| Dependency coupling | 29 local Java files import `com.myjavaworld.ftp` types. Replacing a dependency in the POM alone would not be a sufficient migration. Preserve behavior through a small adapter and migrate consumers in steps. [S02] |
| Persistence | Favorites and preferences use Java object serialization. Favorites use a source-embedded AES key and contain an unencrypted-write fallback. Plan migration with backups and legacy fixtures. [E08, E09, E17] |
| TLS and certificates | A custom SSL context and trust manager are part of the client setup. The hostname method compares certificate Common Name to hostname; the server trust method uses its own validation path and user override. Negotiated protocol behavior still depends on the external library, runtime, and server. [E04, E10, E11] |
| Archives | Download-and-unzip calls the local `Unzip` utility. Extraction combines the destination with entry names and writes them without a destination-containment check in the examined methods. This warrants an early path-traversal regression test and fix. It was not dynamically exploited in this investigation. [E12, E13] |
| Resources and integration | The POM includes English, German, Traditional Chinese, image, and JavaHelp resource roots. Preserve help and localization when changing build or UI packaging. [E02, E15] |
| Obsolete launch path | `JFTPApplet` extends `JApplet`. Removing or isolating this path is a separate compatibility change. JDK 26 removes the Applet API; the installed JDK 25 still contains it. [E14; Oracle reference below] |

Verified connection entry callers are `ConnectAction.actionPerformed`, `FavoritesDlg.connectButtonPressed`, and `FTPSession.reconnect`. The session entry sets up a worker; the worker separately invokes the external library's `connect` and `login`. Keeping these levels distinct prevents incorrect change-impact conclusions. [E04, E05, E06]

## Tools in the skill: what to use here

| Tool or workflow | Best JFTP use | Recommendation and limits |
| --- | --- | --- |
| `rg`, Git inventory, original files | Locate exact classes, configuration, resource keys, and concrete callers | Use on every task. Fastest starting point for a known symbol. Record the searched paths for absence claims. |
| `setup_native.py` and `legacy-repo-map` | Establish package ownership and declaration locations without restoring the build | Used and verified here. The mapper needs no Rust compiler or LLM endpoint after setup. It is experimental syntax-based navigation, not a Java type-resolved call graph. |
| `export_repo_map.py` | Save a readable, reusable overview with scope and coverage | Used here. Primary map: [jftp-overview.rust.md](jftp-overview.rust.md). Sidecars are under `artifacts/jftp-overview.rust/`; only `artifacts/` is ignored by Git. |
| `check_citations.py` | Verify that saved source paths, hashes, ranges, and quotations are current | Use for consequential findings, API cards, and documentation. It detects stale/fabricated citations but cannot establish that reasoning follows from a quote. |
| Full-code Repomix | Gather a connection, persistence, or transfer slice across several files for implementation | Use when starting that change. Recommended first scope: session entry and worker, three callers, `RemoteHost`, preferences, TLS classes, relevant properties, and `pom.xml`. Preserve bodies and original comments initially; decide comment filtering separately. Compression removes implementation and is unsuitable as the only behavior evidence. Not installed or generated in this investigation. |
| IDE references / Serena with Eclipse JDT LS | Rename methods safely and find typed callers after Maven import succeeds | Preferred for Java impact/refactoring once the actual dependencies and classpath resolve. Serena is optional agent access to this navigation; no server was registered here. Reflection, listeners, and resources still need source checks. |
| Codebase-memory-mcp (CBM) | Repeated caller or impact questions while the build remains unavailable | Optional bounded pilot, not a prerequisite. The skill's tested baseline is 0.11.0 on Linux, not a Windows certification or a current-version recommendation. Use an isolated external cache, verify release hashes and extraction coverage, and check every consequential edge in source. Do not enable watchers or client registration as a side effect. |
| Graphify code-only | Explore a specific architectural relationship if direct search/maps are insufficient | Secondary alternative to CBM. The skill's baseline is 0.9.91. The recorded JFTP trial missed a constructor edge and produced incorrect receiver relationships; graph absence must not drive deletion. No graph was run here. |
| `context7_backend.py` lexical retrieval | Search repeated prose questions over verified notes/API cards plus source | Defer. SQLite FTS5 is the simplest pilot if real questions repeatedly defeat direct search. Keep the database external and reindex changed source/revisions. The local service is not a shared production service. |
| Semantic/hybrid retrieval and learned reranking | A later, measured prose-discovery problem | Lowest priority now. These require optional inference dependencies/models. In the skill's earlier JFTP development comparison, raw-code retrieval located expected ranges for only 1/6 positive questions; learned reranking reached 0/6. Verified API cards helped, but these were not held-out benchmarks and were not rerun here. More infrastructure does not establish better answers. |

The graph guidance and retrieval guidance already contain trials against JFTP, including the exact examined repository revision in the graph reference. Reuse their failure cases as tool acceptance questions: find the three actual connection-entry callers, resolve startup to the real `JFTP` receiver, distinguish library calls from session calls, and reject apparent dead-code findings when constructors/resources still reference the class. See the installed [graph guidance](../../.agents/skills/legacy-codebase-workflows/references/code-graphs.md) and [retrieval guidance](../../.agents/skills/legacy-codebase-workflows/references/private-retrieval.md).

## Native map generated in this investigation

- Tool: pinned `legacy-tools-v0.3.1` / `legacy-repo-map` 0.3.1, Windows x86-64, verified release source `5c4eb2ab355d0eba1fb84d0d43e3bd72a49af976`.
- Scope: `src`; focus symbols: `JFTPApplication`, `FTPSession`, `FavoritesManager`; budget: 16,384 estimated tokens. `pom.xml` was read separately because it is outside `src`.
- Result: 425 selected text/configuration files, all 182 Java files parsed, zero parser failures, 1,586 captured definitions, 1,044 selected, 542 omitted. Captured-definition selection is 65.826%; this is not an accuracy score.
- Raw map: 16,379 estimated tokens; measured single-run generation: 13.7524 seconds. This is a subprocess measurement on this machine, not a comparative benchmark or a tokenizer/billing measurement.
- Map status is complete but budget-truncated. Inspect omitted declarations with direct source search. There are no clipped declaration snippets. Skips comprise 98 binary files and 41 non-UTF-8 `.properties` files, all under `src/main/resources_de`. Those 41 German resource files were not mapped or decoded during this investigation; their actual encodings must be established and their original bytes checked during resource/build recovery. All Java files were parsed. Images were not visually assessed.
- The first setup attempt failed with Windows socket error 10013 for the pinned GitHub release index. The network-permitted installer succeeded. The sandbox could not subsequently read the temporary tool cache; mapping/export succeeded with the same permitted execution boundary. No Python mapper fallback was used.

## Work sequence with acceptance gates

### 1. Restore a reproducible build and launch

Make the first implementation change a small build-recovery PR. Locate and verify the `ftpapi` artifact and its transitive requirements; record provenance and reproducible dependency coordinates. Confirm JavaHelp resolution. The original HTTP repository is a likely modern Maven blocker: Maven blocks external HTTP repositories by default. Resolve artifacts through verified HTTPS locations or a controlled mirror rather than bypassing that protection.

Add a pinned Maven wrapper; update compiler/build plugins enough to support the chosen JDK; document the working build and desktop launch commands. Aim for the installed JDK 25 baseline, but treat compatibility as unproven until the dependencies, compiler, packaging, and desktop launch pass. If an older runtime is needed to characterize the original, use an isolated toolchain and document why. Preserve resource directories and legacy profile compatibility. Reconcile the POM's UTF-8 setting with the 41 non-UTF-8 property files before resource filtering/conversion; validate representative localized strings against the originals. Changes to obsolete release/deploy configuration can follow in a separate, bounded build cleanup.

Acceptance: a fresh checkout can build/package using the wrapper and documented JDK; the packaged desktop app opens a local session with resources intact; CI runs that same build. An initial screen launch is a smoke check, not proof that FTP/FTPS transfers work. Dependency availability is the main unknown, so calendar estimates before this gate would be unreliable.

### 2. Add a small behavior safety net

Use a maintained JUnit setup and a disposable loopback FTP/FTPS fixture. Start with connection/login success and failure, directory listing, byte-for-byte upload/download, cancellation and reconnect, and preservation of favorites/preferences during upgrade. Exercise active/passive mode, explicit/implicit FTPS, ASCII/binary policy, or proxy settings only where current supported workflows require them. Test representative legacy data with secrets replaced by synthetic values.

Capture intended behavior at the observable boundary. Add an adapter seam around the FTP client only as needed to make these tests practical. Tests that encode unsafe archive/certificate behavior should assert the intended corrected behavior rather than preserve the defect. Basic UI smoke checks should cover connect, transfer completion/error, and cancellation without freezing the event thread.

Acceptance: the chosen supported workflows pass against a known fixture, failure cases have clear outcomes, and CI can repeat the checks. If the original baseline cannot run, state that and separate compatibility repairs from behavior changes.

### 3. Address the specific security-sensitive findings

Prioritize archive extraction containment, standards-based certificate/hostname validation, and credential persistence. Add negative tests for `../` archive entries and other platform path escapes; ensure every output remains within the chosen destination. Cover trusted/untrusted, expired, SAN-hostname match, mismatch, and explicit user trust decisions for FTPS. Verify actual protocol negotiation before claiming TLS versions are supported or disabled.

Replace source-embedded-key credential protection with an explicit secure storage design suitable for supported desktop platforms. Preserve a backed-up import path for existing favorites, avoid silently writing secrets unencrypted, and test both successful migration and rollback/failure. Preferences and favorites serialization require compatibility decisions before changing their classes or formats.

Acceptance: negative cases pass, ordinary transfers still work, and legacy profiles can migrate without data loss. This investigation identifies code patterns; it is not a complete security assessment or an exploitability determination.

### 4. Extract responsibilities gradually

Start with a connection/client factory or adapter around the reflective FTP dependency, then a transfer service and a persistence boundary. Keep existing Swing actions as consumers while moving one workflow at a time. Since 29 files directly import FTP types, a dependency replacement needs an explicit compatibility plan for remote-file models, listing parsers, exceptions, callbacks, and configuration. Evaluate whether to retain or replace `ftpapi` only after its availability and required behaviors are known.

Review Swing event-thread ownership and cancellation semantics before replacing the custom `com.myjavaworld.gui.SwingWorker` with standard Java concurrency. The startup code directly constructs UI from `main`; the custom worker schedules `finished()` on the event thread, while individual worker bodies still need review. These facts do not prove every UI access is safe or unsafe.

Use IDE refactorings first. `jdeps`/`jdeprscan` become useful once bytecode and dependencies exist; they cannot recover a missing library implementation from source. OpenRewrite can help with repetitive mechanical migrations after a build/test baseline exists, but pin the chosen recipe/tool version and review a dry-run patch. It is optional, not supplied by this skill, and current distribution/access requirements must be checked before adoption.

Acceptance: one workflow moves behind a small interface with existing behavior checks passing; avoid combining protocol replacement, persistence migration, and UI redesign in the same change.

### 5. Refresh the Swing UI and package the app

Evaluate FlatLaf as a small UI pilot after the baseline is stable; it supports Swing themes and HiDPI and can be tested without replacing the UI framework. Check custom `M*` components, table renderers, dialogs, icons, keyboard navigation, and localization. Existing `docs/design-guidelines.md` and `assets/` are potential design inputs only if that branding is intended; their presence does not establish a JFTP UI requirement.

Remove or isolate the applet launch path and review old macOS hooks/help integration as bounded compatibility changes. Use `jpackage` for a self-contained desktop distribution after the packaged app works; produce and test the required platform packages on appropriate platform builders.

Acceptance: representative screens work at common display scales, existing desktop workflows remain usable, profiles survive an upgrade, and a clean machine can install and launch the package.

## Recommended daily loop

1. Start from the current map or an exact `rg` query, then read current definitions, callers, and configuration.
2. For a substantial edit, gather a narrow full-code pack if it reduces repeated reads. Include resources and dependency boundaries explicitly.
3. Use resolved Java references for refactoring when the project imports correctly. Use bounded graph results only to propose source locations.
4. Make one behavior-preserving change or one explicit corrected-behavior change, run the relevant tests, and inspect the diff.
5. Refresh affected evidence/maps after source changes; run the citation checker before reusing saved claims. Keep caches/indexes and generated supporting artifacts out of shared history.

## Evidence ledger

All E-cards refer to original source at the revision above. They are observed code facts; proposed priorities and designs are recommendations. Exact file SHA-256 values, inclusive line ranges, and quotations are committed in `source-evidence.json`; the original working copy is retained in ignored `artifacts/jftp-modernization-evidence.json`. All 26 cards passed `check_citations.py`; the result is saved in `artifacts/jftp-citation-check.json`. The checked quotation supports the stated code observation; it does not prove runtime compatibility or the completeness of a caller search.

| ID | Original source lines | Supporting excerpt / observed fact |
| --- | --- | --- |
| E01 | `pom.xml:64-80` | `<artifactId>ftpapi</artifactId>`, version `3.0.0`; JUnit `3.8.1`; JavaHelp `2.0.05`. |
| E02 | `pom.xml:41-46,84-109,118-122` | HTTP dependency repository, resource roots, Java `1.5` source/target, desktop main class. |
| E03 | `src/main/java/com/myjavaworld/jftp/JFTPApplication.java:55-66` | `jftp = new JFTP();`, `jftp.setVisible(true);`, `jftp.newSession();`. |
| E04 | `src/main/java/com/myjavaworld/jftp/FTPSession.java:617-652` | Worker reflectively constructs client/parser, configures SSL, calls `client.connect` and `client.login`. |
| E05 | `src/main/java/com/myjavaworld/jftp/actions/ConnectAction.java:51-74` | `session.connect(remoteHost);`. |
| E06 | `src/main/java/com/myjavaworld/jftp/FavoritesDlg.java:237-249` and `FTPSession.java:701-704` | Favorite selection and reconnect invoke the session entry. |
| E07 | `src/main/java/com/myjavaworld/jftp/RemoteHost.java:33-45` | Default class names are `com.myjavaworld.ftp.DefaultFTPClient` and `DefaultListParser`. |
| E08 | `src/main/java/com/myjavaworld/jftp/FavoritesManager.java:70-82,120-129` | Object deserialization and an unencrypted-write fallback. |
| E09 | `src/main/java/com/myjavaworld/jftp/FavoritesManager.java:170-177` | `final byte[] encodedKey = ...` and `Cipher.getInstance("AES")`. |
| E10 | `src/main/java/com/myjavaworld/jftp/ssl/JFTPTrustManager.java:76-98,130-138` | Custom checks/user override; `parser.getParameter("CN")`; hostname equality. |
| E11 | `src/main/java/com/myjavaworld/jftp/ssl/JFTPSSLContext.java:37-44` | `SSLContext.getInstance("SSL")` with custom key/trust managers; this string alone does not establish negotiated protocols. |
| E12 | `src/main/java/com/myjavaworld/zip/Unzip.java:134-175` | `new File(targetDirectory, entry.getName())` then `new FileOutputStream(target)`; no containment check in these complete examined methods. |
| E13 | `src/main/java/com/myjavaworld/jftp/actions/DownloadAndUnzipAction.java:88-97` | Downloaded file is passed to `Unzip`, which is configured with the chosen extraction directory. |
| E14 | `src/main/java/com/myjavaworld/jftp/JFTPApplet.java:27-37` | `public class JFTPApplet extends JApplet`. |
| E15 | `src/main/java/com/myjavaworld/jftp/JFTPHelp2.java:22-46` | JavaHelp classes and `helpset/` resource lookup. |
| E16 | `src/main/java/com/myjavaworld/gui/SwingWorker.java:124-146` | `SwingUtilities.invokeLater(doFinished)` and a dedicated constructing thread. |
| E17 | `src/main/java/com/myjavaworld/jftp/JFTP.java:514-517,538-555` | Preferences object serialization and deserialization. |

Recorded searches: S01 = `git ls-files`, including test/wrapper/workflow and dependency-source paths; S02 = `rg -l '^import com.myjavaworld.ftp' src/main/java` (29 files); S03 = source search for session `.connect(...)` callers, followed by the original bodies listed above. Counts describe tracked/local paths in their stated scope, not external libraries or consumers.

## Tool and platform references checked

- [Maven HTTP repository blocking](https://maven.apache.org/docs/3.8.1/release-notes.html): external HTTP repositories are blocked by default.
- [Repomix compression](https://repomix.com/guide/code-compress): structural extraction removes implementation details; comments are a separate option.
- [Serena's Eclipse JDT LS integration source](https://github.com/oraios/serena/blob/main/src/solidlsp/language_servers/eclipse_jdtls.py): optional Java language-server integration.
- [CBM releases](https://github.com/DeusData/codebase-memory-mcp/releases): upstream has releases beyond the skill's tested 0.11.0 baseline; newer behavior was not validated here.
- [JDK 26 release notes](https://www.oracle.com/java/technologies/javase/26-relnote-issues.html) and [OpenJDK applet-removal explanation](https://inside.java/2025/12/03/applet-removal/): Applet API removal is a compatibility consideration beyond JDK 25.
- [JDK dependency analyzer](https://docs.oracle.com/en/java/javase/25/docs/specs/man/jdeps.html): analyzes class files/JARs, including JDK-internal dependencies.
- [OpenRewrite Maven goals](https://docs.openrewrite.org/reference/rewrite-maven-plugin) and [current setup](https://docs.openrewrite.org/running-recipes/getting-started): dry-run patches and version/distribution setup.
- [FlatLaf](https://www.formdev.com/flatlaf/): Swing themes and HiDPI support.
- [JDK packaging guide](https://docs.oracle.com/en/java/javase/25/jpackage/): self-contained application bundles.

## Repository-instruction pointer

The user approved adding a concise source-context pointer to the root `AGENTS.md`. That file now links this plan and the map, states that declarations are selected, and requires original-source verification and snapshot refresh. No application, testing, or commit policy was added. The fuller suggested wording retained below documents the snapshot's scope and limits:

> For JFTP architecture, system design, application workflows, overall logic, and symbol navigation, start with `docs/repo-maps/jftp-modernization-plan.md` and `docs/repo-maps/jftp-overview.rust.md`. The map covers `src`, contains selected declarations, and omits 542 captured definitions at this revision; it has no implementation bodies or resolved runtime call graph. Read current original definitions, callers, `pom.xml`, and resources before making changes. Use a scoped full-code pack when an implementation task needs cross-file context. Refresh snapshots and verify citations after relevant source or dependency changes. Supporting evidence is under `docs/repo-maps/artifacts/`.
