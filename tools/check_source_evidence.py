#!/usr/bin/env python3
"""Check source evidence in the worktree or a local Git revision, allowing recorded CRLF/LF conversion."""

import argparse
import hashlib
import json
from pathlib import Path
import subprocess
import sys

ROOT = Path(__file__).resolve().parents[1]


def check(evidence, root=ROOT, revision=None):
    cards = evidence.get("citations")
    if not isinstance(cards, list) or not cards:
        raise ValueError("Evidence must contain nonempty citations.")
    normalized_count = 0
    commit = None
    if revision is not None:
        resolved = subprocess.run(
            ["git", "-C", str(root), "rev-parse", "--verify", "--end-of-options", revision + "^{commit}"],
            capture_output=True, text=True,
        )
        if resolved.returncode:
            raise ValueError("Cannot resolve evidence revision to a local commit.")
        commit = resolved.stdout.strip()
    blobs = {}
    for card in cards:
        path = (root / card["path"]).resolve()
        if not path.is_relative_to(root.resolve()):
            raise ValueError("Evidence path is outside the repository.")
        if commit is None:
            content = path.read_bytes()
        else:
            relative = path.relative_to(root.resolve()).as_posix()
            if relative not in blobs:
                blob = subprocess.run(["git", "-C", str(root), "show", f"{commit}:{relative}"], capture_output=True)
                if blob.returncode:
                    raise ValueError(f"Source is missing from evidence revision: {relative}")
                blobs[relative] = blob.stdout
            content = blobs[relative]
        if hashlib.sha256(content).hexdigest() != card["sha256"]:
            if hashlib.sha256(content.replace(b"\r\n", b"\n")).hexdigest() != card.get("sha256_lf"):
                raise ValueError(f"Source hash mismatch: {card['path']}")
            normalized_count += 1
        lines = content.decode("utf-8").splitlines()
        start, end = card["start_line"], card["end_line"]
        if not (type(start) is int and type(end) is int and 1 <= start <= end <= len(lines)):
            raise ValueError(f"Invalid source range: {card['path']}")
        quote = card["quote"].replace("\r\n", "\n")
        if not quote.strip() or quote not in "\n".join(lines[start - 1:end]):
            raise ValueError(f"Quotation mismatch: {card['path']}")
    result = {"valid": True, "checked": len(cards), "newline_conversion_only": normalized_count}
    if commit is not None:
        result["revision"] = commit
    return result


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("evidence", type=Path)
    parser.add_argument("--revision", help="Verify frozen citations against a local Git commit instead of the working tree")
    args = parser.parse_args()
    print(json.dumps(check(json.loads(args.evidence.read_text(encoding="utf-8")), revision=args.revision)))


if __name__ == "__main__":
    try:
        main()
    except (OSError, ValueError, KeyError, TypeError) as error:
        print(f"evidence: {error}", file=sys.stderr)
        raise SystemExit(1)
