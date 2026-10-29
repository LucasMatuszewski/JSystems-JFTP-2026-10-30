#!/usr/bin/env python3
"""Check course source evidence, allowing only recorded CRLF/LF conversion."""

import argparse
import hashlib
import json
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]


def check(evidence, root=ROOT):
    cards = evidence.get("citations")
    if not isinstance(cards, list) or not cards:
        raise ValueError("Evidence must contain nonempty citations.")
    normalized_count = 0
    for card in cards:
        path = (root / card["path"]).resolve()
        if not path.is_relative_to(root.resolve()):
            raise ValueError("Evidence path is outside the repository.")
        content = path.read_bytes()
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
    return {"valid": True, "checked": len(cards), "newline_conversion_only": normalized_count}


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("evidence", type=Path)
    args = parser.parse_args()
    print(json.dumps(check(json.loads(args.evidence.read_text(encoding="utf-8")))))


if __name__ == "__main__":
    try:
        main()
    except (OSError, ValueError, KeyError, TypeError) as error:
        print(f"evidence: {error}", file=sys.stderr)
        raise SystemExit(1)
