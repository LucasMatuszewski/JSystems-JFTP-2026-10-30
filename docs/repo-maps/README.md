# RepoMap course example

Start with [the source map](jftp-overview.rust.md), then read the [investigation and modernization plan](jftp-modernization-plan.md). The snapshot examines `src` at revision `14e62ceba4e371c2a0b955604b10f065f46f4f7d`; it is not a live index.

## What is committed

- `jftp-overview.rust.md`: the original generated overview, with selected declarations and source line numbers.
- `jftp-overview.rust.provenance.json`: scope, captured-definition coverage, limits, omitted/skipped entries, raw-map hash, and measured generation time.
- `source-evidence.json`: 26 original-source citation cards used by the modernization investigation.
- `jftp-modernization-plan.md`: findings, tool tradeoffs, unresolved questions, and proposed implementation gates. It records the environment of the investigation, not participant setup requirements.

The map parsed all 182 Java files and selected 1,044 of 1,586 captured definitions within a 16,384 estimated-token budget. Its 542 omitted declarations are a size limit, not missing application code. The 41 non-UTF-8 German property files and 98 binary files were not parsed as text. A declaration map cannot prove behavior, runtime wiring, or call-graph accuracy.

Citation cards retain their original byte hashes and an LF-normalized hash. The course checker accepts only CRLF/LF conversion in otherwise identical source and still checks exact quotations and line ranges. The bundled skill checker remains available for strict byte-identical checkouts.

Full inventories, raw maps, execution logs, and exporter sidecars live in ignored `artifacts/` after regeneration. Paths to those sidecars in the historical map describe the original run; the committed provenance and evidence above are the portable course record. Keep the report's original bytes when comparing its recorded hashes.

## Reproduce

From the repository root with Python 3.12 or newer:

```sh
# Verify/download the pinned native tool, then replace the example snapshot.
python tools/repo_map.py --setup --replace

# Reuse an already verified native installation.
python tools/repo_map.py --replace

# Check the saved original-source evidence.
python tools/check_source_evidence.py docs/repo-maps/source-evidence.json
```

Setup needs network access to the skill's pinned release. Mapping is local, uses temporary staging outside the checkout, and exports its outputs into this directory. No Rust compiler or Python parser packages are required. A native setup failure is reported directly; the helper does not silently switch implementations. An invalid existing native cache requires inspection and the skill's explicit replacement workflow.

Regeneration changes the examined revision and timing. It does not rewrite the historical modernization findings or automatically refresh their evidence. Refresh those claims only after rereading the relevant original definitions and callers. Documentation-only commits can change `HEAD` while original source hashes remain valid.

## Exercise

1. Find `FTPSession.connect` in the map and open its original body.
2. Find its callers using direct search. Inspect `ConnectAction`, `FavoritesDlg`, and `FTPSession.reconnect`.
3. Identify the boundary between the Swing session entry and the external FTP client's `connect/login` calls.
4. Explain which claims needed method bodies or configuration rather than signatures.
