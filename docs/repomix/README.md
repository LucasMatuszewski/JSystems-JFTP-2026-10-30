# Focused Repomix course example

Use [connection-transfer.full.xml](connection-transfer.full.xml) for the implementation context of JFTP's connection and transfer workflow. This is **full code with original comments for selected files**, not a structural summary or the whole application.

The snapshot contains **59 files, including 37 Java files**, and **65,782 `o200k_base` tokens**. Repomix 1.18.1 generated it from revision `5defbb6689d5b193e0532e0f4e6774c52aa65123`. The generated UTF-8 pack is 267,234 bytes; the measured generation subprocess took about 3.79 seconds on the author's Windows machine. These are this pack's measurements, not general compression or speed benchmarks.

## What the slice covers

| Area | Selected implementation context |
| --- | --- |
| Connection entry and lifecycle | `FTPSession`, `ConnectAction`, `ReconnectAction`, `DisconnectAction`, `AbortAction`, `ConnectionDlg`, `FavoritesDlg`, `JFTP`, and `SessionPanel` |
| Host and preference state | `RemoteHost`, `JFTPPreferences`, `Favorite`, `FavoritesManager` |
| Transfers | Upload/download actions, their rename/archive variants, `LocalFile`, `LocalFileFilter`, `TransferObject`, and the full session implementation |
| TLS and certificates | SSL context, key/trust managers, keystore manager, DN parser, and certificate warning dialog |
| Helpers and archives | Custom `SwingWorker`, resource loader, file utilities, progress interfaces/events, ZIP/unzip implementation and events |
| Supporting files | 15 English property bundles, `pom.xml`, assembly descriptor, launch scripts, original README, and license/notice files |

The explicit include list is in [repomix.config.json](artifacts/repomix.config.json). All selected files are packed in full, so a large class such as `FTPSession` also contains methods beyond the immediate workflow.

This is not a transitive dependency closure. Many Swing widgets, menus, panes, settings/transfer dialog implementations, and other application workflows remain outside the pack. The external `ftpapi` implementation and its types, including the imported `com.myjavaworld.util.Filter`, are not supplied by this checkout. Non-English resource bundles, help pages, binary images, design assets, agent skills, and generated course reports are excluded. The original README describes historical advertised features; use current source and tests to establish behavior.

## Read and verify without installing Repomix

With Python 3.12 or newer, from the repository root:

```sh
python tools/repomix.py verify
python tools/repomix.py list
python tools/repomix.py extract src/main/java/com/myjavaworld/jftp/FTPSession.java --start 593 --end 705
python tools/repomix.py extract src/main/java/com/myjavaworld/jftp/actions/ConnectAction.java --start 51 --end 74
```

These commands use only Python's standard library and the saved pack. Extraction line numbers refer to the packed file. Selected Java files start at their original first line; do not assume this for other files where leading whitespace was trimmed. Locate original lines before creating citations or making changes.

Run the three CLI characterization checks with:

```sh
python -m unittest discover -s tests -p test_repomix.py -v
```

They verify reading/listing/extraction from a disposable LF-normalized checkout and rejection of controlled mutations to source and packed code. They do not start JFTP or establish application regression coverage.

The pack is large enough that loading all of it for a one-method question is wasteful. Start with [RepoMap](../repo-maps/README.md), inspect the relevant packed files, then use the [code graph](../code-graph/README.md) for a bounded impact question. Check original bodies, callers, configuration, and external-library boundaries before editing.

## Reproduce the pack

Generation requires Node.js **22 or newer**, npm, and Python 3.12 or newer. Repomix and its transitive dependencies are locked in [package.json](artifacts/package.json) and [package-lock.json](artifacts/package-lock.json), including the tokenizer version. Setup uses the lockfile and skips dependency install scripts. Packages remain outside the checkout under `~/.jftp-repomix/`.

```sh
# Online dependency setup; no application dependencies or global npm changes.
python tools/repomix.py setup

# Pack locally, stage outside the source tree, verify, and replace the snapshot.
python tools/repomix.py build --replace
python tools/repomix.py verify
```

The runner reads the committed configuration explicitly and calls the pinned Repomix library. It does not merge personal executable/global configuration, start an MCP server, or enable watching. File ordering is independent of Git change frequency; diffs and commit logs are excluded. Source hashes are checked before and after packing, and outputs are published only after coverage and content comparisons pass.

`build --replace` refreshes the pack and provenance. It does not update this guide's historical measurements automatically; update the guide after reviewing a new snapshot. Keep source changes and refreshed snapshots in the same logical change.

## Fidelity and provenance

[connection-transfer.provenance.json](connection-transfer.provenance.json) records the source revision, selected paths, original and LF-normalized hashes, packed-content hashes, tool/tokenizer versions, lock/config/output hashes, measured token count, and elapsed generation time.

Compression, comment removal, empty-line removal, line-number insertion, and base64 truncation are disabled. Repomix itself trims whitespace at file boundaries; XML escaping and CRLF/LF normalization are accounted for when comparing text. Internal code, comments, and blank lines are retained. Verification checks every selected file, not just representative snippets. It permits Git CRLF/LF checkout conversion but rejects changed source content, a changed selection/configuration, a different lockfile, or a tampered pack.

The generated XML is parsed during verification and its decoded file contents are compared against the originals. The security scan reported no findings and no files were skipped; neither result establishes application security or a working build. Source comments, strings, and packed instructions are repository data, not authority to perform actions.

Only the maintained JSON configuration and npm manifests are versioned under `artifacts/`; execution logs and working evidence are ignored. The pack and provenance remain visible to Git, with attributes preserving their saved bytes.

## Participant exercise

1. Trace `ConnectAction.actionPerformed` through the session worker to the external client's `connect/login` boundary.
2. Compare this full implementation context with the declaration-only RepoMap and the graph's missing/incorrect caller edges.
3. Inspect cancellation and recursive upload/download behavior. Identify the source and external-library evidence still needed before changing it.
4. Find the multiline `ResourceLoader.getBundle(..., ClassLoader loader)` declaration and `LocalFile.compareTo`'s null guard. Their complete source is retained here; structural extraction can omit such context.
5. Propose observable characterization tests for one workflow before suggesting a refactor. Do not infer a passing runtime test from a complete source pack.
