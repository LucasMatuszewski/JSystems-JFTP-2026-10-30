#!/usr/bin/env python3
"""Reproduce and explore the JFTP course graph with Python's standard library."""

from __future__ import annotations

import argparse
import collections
import hashlib
import json
import os
from pathlib import Path, PurePosixPath
import platform
import sqlite3
import subprocess
import sys
import tarfile
import tempfile
import urllib.request
import webbrowser
import zipfile

ROOT = Path(__file__).resolve().parents[1]
OUTPUT = ROOT / "docs/code-graph"
BASE = Path.home() / ".jftp-code-graph"
VERSION = "0.11.0"
RELEASE = f"https://github.com/DeusData/codebase-memory-mcp/releases/download/v{VERSION}"
PROJECT = "jftp-src"
SNAPSHOT = OUTPUT / "jftp-code-graph.json"
PREFIX = f"{PROJECT}.main.java.com.myjavaworld.jftp."


def digest(path):
    return hashlib.sha256(Path(path).read_bytes()).hexdigest()


def write_json(path, value):
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(value, ensure_ascii=True, indent=2) + "\n", encoding="utf-8", newline="\n")


def environment():
    result = os.environ.copy()
    result.update(CBM_CACHE_DIR=str(BASE / "cache"),
                  CBM_RUNTIME_DIR=str(BASE / "runtime"),
                  CBM_ALLOWED_ROOT=str(ROOT / "src"))
    (BASE / "runtime").mkdir(parents=True, exist_ok=True)
    return result


def executable():
    name = "codebase-memory-mcp.exe" if sys.platform == "win32" else "codebase-memory-mcp"
    path = BASE / "tools" / VERSION / name
    if not path.is_file():
        raise ValueError("Run 'python tools/code_graph.py setup' first.")
    receipt = json.loads((path.parent / "course-receipt.json").read_text(encoding="utf-8"))
    if digest(path) != receipt["executable_sha256"]:
        raise ValueError("Installed graph executable no longer matches its verified receipt.")
    return path


def native(*arguments, capture=False):
    result = subprocess.run([str(executable()), *arguments], cwd=ROOT,
                            env=environment(), text=True, encoding="utf-8",
                            capture_output=capture, check=True)
    return result.stdout if capture else None


def setup():
    machine = platform.machine().lower()
    cpu = {"amd64": "amd64", "x86_64": "amd64", "arm64": "arm64", "aarch64": "arm64"}.get(machine)
    system = {"win32": "windows", "linux": "linux", "darwin": "darwin"}.get(sys.platform)
    if not system or not cpu:
        raise ValueError(f"No reviewed setup route for {sys.platform}/{machine}.")
    extension = "zip" if system == "windows" else "tar.gz"
    portable = "-portable" if system == "linux" else ""
    asset = f"codebase-memory-mcp-{system}-{cpu}{portable}.{extension}"
    destination = BASE / "tools" / VERSION
    if destination.exists():
        print(f"Verified existing executable: {executable()}")
        return
    destination.parent.mkdir(parents=True, exist_ok=True)
    with tempfile.TemporaryDirectory(prefix="jftp-graph-download-") as folder:
        staging = Path(folder)
        for name in ("checksums.txt", asset):
            request = urllib.request.Request(f"{RELEASE}/{name}", headers={"User-Agent": "jftp-course-example"})
            with urllib.request.urlopen(request, timeout=60) as response, (staging / name).open("xb") as target:
                while block := response.read(1024 * 1024):
                    target.write(block)
        entries = [line.split() for line in (staging / "checksums.txt").read_text().splitlines()]
        matches = [parts[0] for parts in entries if len(parts) == 2 and parts[1].lstrip("*") == asset]
        if len(matches) != 1 or digest(staging / asset) != matches[0]:
            raise ValueError("Release archive checksum mismatch or missing checksum entry.")
        unpacked = staging / "package"
        unpacked.mkdir()
        archive = staging / asset
        if extension == "zip":
            with zipfile.ZipFile(archive) as bundle:
                members = bundle.infolist()
                if len(members) > 100 or sum(item.file_size for item in members) > 500_000_000:
                    raise ValueError("Unexpected archive size.")
                for item in members:
                    path = PurePosixPath(item.filename)
                    if path.is_absolute() or ".." in path.parts or "\\" in item.filename or ":" in item.filename:
                        raise ValueError("Unsafe archive path.")
                    if ((item.external_attr >> 16) & 0o170000) == 0o120000:
                        raise ValueError("Archive links are not supported.")
                bundle.extractall(unpacked)
        else:
            with tarfile.open(archive) as bundle:
                members = bundle.getmembers()
                if len(members) > 100 or sum(item.size for item in members) > 500_000_000:
                    raise ValueError("Unexpected archive size.")
                if any(not (item.isfile() or item.isdir()) for item in members):
                    raise ValueError("Archive links and special files are not supported.")
                bundle.extractall(unpacked, filter="data")
        program_name = "codebase-memory-mcp.exe" if system == "windows" else "codebase-memory-mcp"
        program = unpacked / program_name
        if not program.is_file() or not (unpacked / "LICENSE").is_file():
            raise ValueError("Unexpected package layout; nothing was installed.")
        if system != "windows":
            program.chmod(0o755)
        receipt = {"version": VERSION, "archive_url": f"{RELEASE}/{asset}",
                   "archive_sha256": matches[0], "executable_sha256": digest(program)}
        write_json(unpacked / "course-receipt.json", receipt)
        # Publish only the fully verified package, without replacing an installation.
        import shutil
        shutil.copytree(unpacked, destination)
    print(native("--version", capture=True).strip())
    for key in ("auto_index", "auto_watch", "watcher_enabled", "ui_enabled"):
        native("config", "set", key, "false", capture=True)
    print(f"Installed verified tool in {destination}; no client integrations were registered.")


def source_manifest(normalize_lines=False):
    text_extensions = {".java", ".properties", ".xml", ".dtd", ".html", ".sh", ".bat"}
    result = {}
    for path in sorted((ROOT / "src").rglob("*")):
        if path.is_file():
            content = path.read_bytes()
            if normalize_lines and path.suffix.lower() in text_extensions:
                content = content.replace(b"\r\n", b"\n")
            result[path.relative_to(ROOT).as_posix()] = hashlib.sha256(content).hexdigest()
    return result


def evidence_card(path, quote, claim):
    source = ROOT / path
    lines = source.read_text(encoding="utf-8").splitlines()
    matches = [index + 1 for index, line in enumerate(lines) if quote in line]
    if len(matches) != 1:
        raise ValueError(f"Expected a unique evidence quotation in {path}.")
    normalized = hashlib.sha256(source.read_bytes().replace(b"\r\n", b"\n")).hexdigest()
    return {"path": path, "sha256": digest(source), "sha256_lf": normalized, "start_line": matches[0],
            "end_line": matches[0], "quote": quote, "status": "observed", "claim": claim}


def export(database, replace):
    if not replace and any((OUTPUT / name).exists() for name in
                           (SNAPSHOT.name, "jftp-code-graph.html", "verification.json")):
        raise ValueError("Snapshot already exists; use export --replace to refresh it.")
    with sqlite3.connect(database.resolve().as_uri() + "?mode=ro", uri=True) as db:
        db.row_factory = sqlite3.Row
        db.execute("BEGIN")
        indexed = db.execute("SELECT indexed_at, root_path FROM projects WHERE name=?", (PROJECT,)).fetchone()
        if not indexed or Path(indexed["root_path"]).resolve() != (ROOT / "src").resolve():
            raise ValueError("The selected graph is not an index of this checkout's src tree.")
        contexts = [json.loads(row[0] or "{}") for row in db.execute(
            "SELECT properties FROM edges WHERE project=? AND type='HAS_BRANCH'", (PROJECT,))]
        indexed_revision = next((context["head_sha"] for context in contexts if context.get("head_sha")), None)
        if not indexed_revision:
            raise ValueError("The graph has no recorded source revision; inspect it before publishing.")
        input_rows = list(db.execute("SELECT rel_path, sha256 FROM file_hashes WHERE project=?", (PROJECT,)))
        virtual_inputs = {f".codebase-memory/.semantic-input/{name}-v1" for name in
                          ("git-context", "global-extension-config", "project-extension-config")}
        stale = [row["rel_path"] for row in input_rows if row["rel_path"] not in virtual_inputs
            if not (ROOT / "src" / row["rel_path"]).is_file()
            or digest(ROOT / "src" / row["rel_path"]) != row["sha256"]]
        if stale:
            raise ValueError(f"Index is stale; reindex before exporting: {stale[:5]}")
        nodes = []
        for row in db.execute("SELECT id,label,name,qualified_name,file_path,start_line,end_line FROM nodes WHERE project=? ORDER BY id", (PROJECT,)):
            node = dict(row)
            path = node.pop("file_path")
            node["path"] = "src/" + path if path and path != "{}" else ""
            node["start"] = node.pop("start_line")
            node["end"] = node.pop("end_line")
            nodes.append(node)
        edges = []
        for row in db.execute("SELECT id,source_id,target_id,type,properties FROM edges WHERE project=? ORDER BY id", (PROJECT,)):
            props = json.loads(row["properties"] or "{}")
            edge = {"id": row["id"], "source": row["source_id"], "target": row["target_id"], "type": row["type"]}
            edge.update({key: props[key] for key in ("line", "callee", "strategy", "confidence") if key in props})
            edges.append(edge)
    lookup = {node["qualified_name"]: node for node in nodes}
    target = lookup[PREFIX + "FTPSession.connect"]
    specifications = [
        (PREFIX + "actions.ConnectAction.actionPerformed", "src/main/java/com/myjavaworld/jftp/actions/ConnectAction.java", "session.connect(remoteHost);"),
        (PREFIX + "FavoritesDlg.connectButtonPressed", "src/main/java/com/myjavaworld/jftp/FavoritesDlg.java", "session.connect(selectedHost);"),
        (PREFIX + "FTPSession.reconnect", "src/main/java/com/myjavaworld/jftp/FTPSession.java", "connect(this.remoteHost);"),
    ]
    citations, checks = [], []
    for symbol, path, quote in specifications:
        caller = lookup[symbol]
        matches = [edge for edge in edges if edge["source"] == caller["id"]
                   and edge["target"] == target["id"] and edge["type"] == "CALLS"]
        card = evidence_card(path, quote, f"{caller['name']} calls FTPSession.connect.")
        citations.append(card)
        checks.append({"source": caller["id"], "target": target["id"], "line": card["start_line"],
                       "status": "confirmed" if matches else "missing", "edge_ids": [edge["id"] for edge in matches]})
    rejected = [edge["id"] for edge in edges if edge["target"] == target["id"] and edge["type"] == "CALLS"
                and next(node for node in nodes if node["id"] == edge["source"])["label"] == "File"]
    citations.append(evidence_card("src/main/java/com/myjavaworld/jftp/FTPSession.java",
                                  "client.connect(remoteHost.getHostName(), port);",
                                  "This is a lower-level external FTP client call, not a call to the session entry."))
    export_revision = subprocess.check_output(["git", "rev-parse", "HEAD"], cwd=ROOT, text=True).strip()
    data = {"schema_version": 1, "project": PROJECT, "tool": {"name": "codebase-memory-mcp", "version": VERSION},
            "source_revision": indexed_revision, "export_revision": export_revision,
            "scope": "src", "indexed_at": indexed["indexed_at"],
            "source_hashes": source_manifest(), "source_hashes_lf": source_manifest(normalize_lines=True),
            "virtual_input_fingerprints": {row["rel_path"]: row["sha256"] for row in input_rows if row["rel_path"] in virtual_inputs},
            "node_count": len(nodes), "edge_count": len(edges),
            "node_labels": dict(collections.Counter(node["label"] for node in nodes)),
            "edge_types": dict(collections.Counter(edge["type"] for edge in edges)),
            "retained": "All indexed nodes and edges; source locations and available call resolver metadata. Other properties and source bodies are omitted.",
            "nodes": nodes, "edges": edges, "verification": {"target": target["id"], "callers": checks, "rejected_file_edges": rejected}}
    write_json(SNAPSHOT, data)
    write_json(OUTPUT / "verification.json", {"source_revision": indexed_revision, "citations": citations,
                                               "checks": data["verification"], "snapshot_sha256": digest(SNAPSHOT)})
    template = (ROOT / "tools/code-graph-viewer.html").read_text(encoding="utf-8")
    payload = json.dumps(data, ensure_ascii=True, separators=(",", ":")).replace("<", "\\u003c")
    (OUTPUT / "jftp-code-graph.html").write_text(template.replace("__JFTP_GRAPH_DATA__", payload), encoding="utf-8", newline="\n")
    print(f"Exported {len(nodes)} nodes / {len(edges)} edges to {OUTPUT}")


def load_snapshot():
    return json.loads(SNAPSHOT.read_text(encoding="utf-8"))


def resolve(data, symbol):
    exact = [node for node in data["nodes"] if str(node["id"]) == symbol or node["qualified_name"] == symbol]
    found = exact or [node for node in data["nodes"] if node["qualified_name"].endswith("." + symbol)]
    if len(found) != 1:
        raise ValueError("Choose one exact qualified name: " + ", ".join(node["qualified_name"] for node in found[:10]))
    return found[0]


def query_snapshot(args):
    data = load_snapshot()
    if args.action == "search":
        found = [node for node in data["nodes"] if args.symbol.casefold() in node["qualified_name"].casefold()]
        if args.label:
            found = [node for node in found if node["label"] == args.label]
        print(json.dumps({"total": len(found), "returned": min(len(found), args.limit), "nodes": found[:args.limit]}, indent=2))
        return
    selected = resolve(data, args.symbol)
    lookup = {node["id"]: node for node in data["nodes"]}
    inbound = args.action == "callers"
    found = [edge for edge in data["edges"] if edge["type"] == "CALLS" and
             edge["target" if inbound else "source"] == selected["id"]]
    confirmed = {edge_id for check in data["verification"]["callers"] for edge_id in check["edge_ids"]}
    rows = [{"node": lookup[edge["source" if inbound else "target"]], "edge": edge,
             "source_check": "rejected synthetic file caller" if edge["id"] in data["verification"]["rejected_file_edges"] else
             "confirmed in source" if edge["id"] in confirmed else "not independently verified"}
            for edge in found[:args.limit]]
    print(json.dumps({"symbol": selected["qualified_name"], "total": len(found), "relationships": rows,
                      "known_source_checks": data["verification"] if selected["id"] == data["verification"]["target"] else None}, indent=2))


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    sub = parser.add_subparsers(dest="action", required=True)
    for action in ("setup", "index", "verify", "native-view", "stop"):
        sub.add_parser(action)
    command = sub.add_parser("export")
    command.add_argument("--database", type=Path, default=BASE / "cache" / f"{PROJECT}.db")
    command.add_argument("--replace", action="store_true")
    command = sub.add_parser("view")
    command.add_argument("--no-open", action="store_true")
    for action in ("search", "callers", "callees"):
        command = sub.add_parser(action)
        command.add_argument("symbol")
        command.add_argument("--limit", type=int, default=20)
        command.add_argument("--label", choices=("Method", "Class", "File", "Interface"))
    command = sub.add_parser("live")
    command.add_argument("arguments", nargs=argparse.REMAINDER)
    args = parser.parse_args()
    if args.action == "setup":
        setup()
    elif args.action == "index":
        native("cli", "--progress", "index_repository", "--repo-path", str(ROOT / "src"), "--name", PROJECT, "--mode", "full")
    elif args.action == "export":
        export(args.database, args.replace)
    elif args.action == "verify":
        data = load_snapshot()
        exact = data["source_hashes"] == source_manifest()
        if not exact and data.get("source_hashes_lf") != source_manifest(normalize_lines=True):
            raise ValueError("Source files changed, appeared or disappeared; refresh the graph snapshot.")
        if digest(SNAPSHOT) != json.loads((OUTPUT / "verification.json").read_text())["snapshot_sha256"]:
            raise ValueError("Snapshot hash does not match verification provenance.")
        ids = {node["id"] for node in data["nodes"]}
        if len(ids) != data["node_count"] or len(data["edges"]) != data["edge_count"] or any(
                edge["source"] not in ids or edge["target"] not in ids for edge in data["edges"]):
            raise ValueError("Snapshot contains inconsistent counts, IDs or edge endpoints.")
        html = (OUTPUT / "jftp-code-graph.html").read_text(encoding="utf-8")
        embedded = html.split('<script id="graph-data" type="application/json">', 1)[1].split("</script>", 1)[0]
        if json.loads(embedded) != data:
            raise ValueError("Viewer and snapshot disagree.")
        mode = "exact bytes" if exact else "CRLF/LF conversion only in declared text extensions"
        print(f"Verified {len(data['source_hashes'])} source files ({mode}), snapshot hash, graph endpoints, and embedded viewer data.")
    elif args.action == "view":
        path = OUTPUT / "jftp-code-graph.html"
        if not path.is_file():
            raise ValueError("Export the snapshot first.")
        print(path)
        if not args.no_open:
            webbrowser.open(path.as_uri())
    elif args.action == "native-view":
        native("config", "set", "ui_enabled", "true")
        native("daemon", "start")
        print("Native viewer: http://127.0.0.1:9749 ; stop with: python tools/code_graph.py stop")
    elif args.action == "stop":
        native("daemon", "stop")
    elif args.action == "live":
        if not args.arguments:
            raise ValueError("Provide a CBM tool and arguments, for example: live list_projects")
        native("cli", *args.arguments)
    else:
        if not 1 <= args.limit <= 100:
            raise ValueError("Use a result limit between 1 and 100.")
        query_snapshot(args)


if __name__ == "__main__":
    try:
        main()
    except (ValueError, OSError, sqlite3.Error, subprocess.CalledProcessError) as error:
        print(f"code-graph: {error}", file=sys.stderr)
        raise SystemExit(1)
