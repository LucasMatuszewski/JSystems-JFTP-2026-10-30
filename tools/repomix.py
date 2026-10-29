#!/usr/bin/env python3
"""Build, verify, and read the focused Repomix course pack."""

from __future__ import annotations

import argparse
import hashlib
import json
import os
from pathlib import Path
import shutil
import subprocess
import sys
import tempfile
import time
import xml.etree.ElementTree as ET

ROOT = Path(__file__).resolve().parents[1]
OUTPUT = ROOT / "docs/repomix"
ARTIFACTS = OUTPUT / "artifacts"
CONFIG = ARTIFACTS / "repomix.config.json"
PACK = OUTPUT / "connection-transfer.full.xml"
PROVENANCE = OUTPUT / "connection-transfer.provenance.json"
TOOL_ROOT = Path.home() / ".jftp-repomix"
VERSION = "1.18.1"


def digest(content):
    return hashlib.sha256(content).hexdigest()


def read_json(path):
    return json.loads(path.read_text(encoding="utf-8"))


def config_paths():
    config = read_json(CONFIG)
    if any(config["output"].get(key) for key in ("compress", "removeComments", "removeEmptyLines", "showLineNumbers", "truncateBase64")):
        raise ValueError("This adapter verifies full source and comments, without structural/content transforms.")
    paths = config["include"]
    if not paths or len(paths) != len(set(paths)):
        raise ValueError("Use a nonempty, unique explicit include list.")
    for name in paths:
        path = (ROOT / name).resolve()
        if not path.is_relative_to(ROOT) or not path.is_file():
            raise ValueError(f"Selected source path is missing or outside the checkout: {name}")
    return paths


def source_info(paths):
    result = {}
    for name in paths:
        content = (ROOT / name).read_bytes()
        text = content.decode("utf-8").replace("\r\n", "\n")
        result[name] = {"sha256": digest(content), "sha256_lf": digest(text.encode("utf-8")),
                        "packed_content_sha256": digest(text.strip().encode("utf-8"))}
    return result


def packed_files(path=PACK):
    root = ET.parse(path).getroot()
    entries = root.findall(".//files/file")
    if not entries:
        raise ValueError("The XML pack contains no file entries.")
    result = {}
    for entry in entries:
        name = entry.attrib["path"]
        if name in result or list(entry):
            raise ValueError("Duplicate file or unescaped markup in XML pack.")
        result[name] = (entry.text or "").strip()
    return result


def compare_pack(path, expected):
    files = packed_files(path)
    if set(files) != set(expected):
        raise ValueError(f"Packed-file coverage differs from config: missing={sorted(set(expected)-set(files))}; extra={sorted(set(files)-set(expected))}")
    for name, text in files.items():
        if digest(text.encode("utf-8")) != expected[name]["packed_content_sha256"]:
            raise ValueError(f"Full source/comment comparison failed: {name}")
    return files


def setup():
    npm = shutil.which("npm.cmd" if os.name == "nt" else "npm")
    if not npm or not shutil.which("node"):
        raise ValueError("Install Node.js 22 or newer with npm before setup.")
    TOOL_ROOT.mkdir(parents=True, exist_ok=True)
    for name in ("package.json", "package-lock.json"):
        shutil.copyfile(ARTIFACTS / name, TOOL_ROOT / name)
    subprocess.run([npm, "ci", "--prefix", str(TOOL_ROOT), "--ignore-scripts", "--no-audit", "--no-fund"], check=True)
    print(f"Installed lockfile-pinned Repomix outside the repository: {TOOL_ROOT}")


def build(replace):
    if not replace and (PACK.exists() or PROVENANCE.exists()):
        raise ValueError("A saved pack exists; use build --replace to refresh it.")
    paths = config_paths()
    before = source_info(paths)
    package = TOOL_ROOT / "node_modules/repomix"
    if not (package / "package.json").is_file():
        raise ValueError("Run 'python tools/repomix.py setup' first.")
    installed_lock = read_json(TOOL_ROOT / "package-lock.json")
    lock = read_json(ARTIFACTS / "package-lock.json")
    if installed_lock != lock:
        raise ValueError("Installed dependency lock differs from course lock; rerun setup.")
    with tempfile.TemporaryDirectory(prefix="jftp-repomix-") as directory:
        staging = Path(directory)
        pack = staging / PACK.name
        metrics = staging / "metrics.json"
        started = time.perf_counter()
        result = subprocess.run(["node", str(ROOT / "tools/repomix-runner.mjs"), str(package), str(ROOT),
                                 str(CONFIG), str(pack), str(metrics)], cwd=ROOT, text=True,
                                encoding="utf-8", capture_output=True)
        elapsed = time.perf_counter() - started
        ARTIFACTS.mkdir(parents=True, exist_ok=True)
        (ARTIFACTS / "last-build.log").write_text(result.stdout + result.stderr, encoding="utf-8", newline="\n")
        if result.returncode:
            raise ValueError("Repomix failed; inspect docs/repomix/artifacts/last-build.log.")
        stats = read_json(metrics)
        if stats["suspiciousFilesResults"] or stats["skippedFiles"]:
            raise ValueError("Repomix reported security exclusions or skipped inputs; inspect the build before publishing.")
        if before != source_info(paths):
            raise ValueError("Source changed during packing; no output was published.")
        compare_pack(pack, before)
        if stats["totalFiles"] != len(paths):
            raise ValueError("Repomix file count disagrees with verified coverage.")
        raw = pack.read_bytes()
        provenance = {
            "schema_version": 1,
            "source_revision": subprocess.check_output(["git", "rev-parse", "HEAD"], cwd=ROOT, text=True).strip(),
            "profile": "focused connection/transfer workflow; full code with original comments",
            "tool": {"name": "repomix", "version": VERSION, "node_version": stats["nodeVersion"],
                     "package_integrity": lock["packages"]["node_modules/repomix"]["integrity"],
                     "tokenizer_version": lock["packages"]["node_modules/gpt-tokenizer"]["version"]},
            "config_sha256": digest(CONFIG.read_bytes()),
            "dependency_lock_sha256": digest((ARTIFACTS / "package-lock.json").read_bytes()),
            "pack_sha256": digest(raw),
            "normalization": "Repomix trims leading/trailing file whitespace. XML escapes are decoded and CRLF/LF is normalized for full-text comparison. Internal code, comments and blank lines are retained.",
            "file_count": len(paths), "java_file_count": sum(name.endswith(".java") for name in paths),
            "utf8_bytes": len(raw), "repomix_total_characters": stats["totalCharacters"],
            "tokens": {"encoding": "o200k_base", "count": stats["totalTokens"]},
            "generation_elapsed_seconds": elapsed, "security_findings": 0, "skipped_files": [],
            "files": {name: {**before[name], "tokens": stats["fileTokenCounts"].get(name)} for name in paths},
        }
        OUTPUT.mkdir(parents=True, exist_ok=True)
        PROVENANCE.write_text(json.dumps(provenance, indent=2) + "\n", encoding="utf-8", newline="\n")
        PACK.write_bytes(raw)
        print(f"Published {len(paths)} full files / {stats['totalTokens']:,} o200k_base tokens to {PACK}")


def verify():
    provenance = read_json(PROVENANCE)
    paths = config_paths()
    if digest(PACK.read_bytes()) != provenance["pack_sha256"]:
        raise ValueError("Pack hash differs from saved provenance.")
    if digest(CONFIG.read_bytes()) != provenance["config_sha256"]:
        raise ValueError("Packing configuration changed; rebuild the snapshot.")
    if digest((ARTIFACTS / "package-lock.json").read_bytes()) != provenance["dependency_lock_sha256"]:
        raise ValueError("Dependency lock changed; rebuild the snapshot.")
    current = source_info(paths)
    if set(current) != set(provenance["files"]):
        raise ValueError("Selected source coverage changed.")
    for name, info in current.items():
        if info["sha256_lf"] != provenance["files"][name]["sha256_lf"]:
            raise ValueError(f"Selected source changed: {name}")
    compare_pack(PACK, current)
    print(f"Verified {len(paths)} full files and original comments, XML parseability, source/config/lock hashes, and pack hash.")


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    sub = parser.add_subparsers(dest="action", required=True)
    sub.add_parser("setup")
    command = sub.add_parser("build")
    command.add_argument("--replace", action="store_true")
    sub.add_parser("verify")
    sub.add_parser("list")
    command = sub.add_parser("extract")
    command.add_argument("path")
    command.add_argument("--start", type=int, default=1)
    command.add_argument("--end", type=int)
    args = parser.parse_args()
    if args.action == "setup":
        setup()
    elif args.action == "build":
        build(args.replace)
    elif args.action == "verify":
        verify()
    elif args.action == "list":
        print("\n".join(packed_files()))
    else:
        content = packed_files().get(args.path)
        if content is None:
            raise ValueError("Path is outside the saved pack; use list to see the scope.")
        lines = content.splitlines()
        end = len(lines) if args.end is None else args.end
        if not 1 <= args.start <= end <= len(lines):
            raise ValueError("Invalid packed-file line range.")
        print("\n".join(f"{number}: {lines[number-1]}" for number in range(args.start, end + 1)))


if __name__ == "__main__":
    try:
        main()
    except (OSError, ValueError, KeyError, ET.ParseError, subprocess.CalledProcessError) as error:
        print(f"repomix: {error}", file=sys.stderr)
        raise SystemExit(1)
