"""Real loopback-only FTP fixture; serves only an owned synthetic directory."""
import argparse
import importlib.metadata
import json
import logging
import os
from pathlib import Path

from pyftpdlib.authorizers import DummyAuthorizer
from pyftpdlib.handlers import FTPHandler
from pyftpdlib.servers import FTPServer

ROOT = Path(__file__).resolve().parents[1]


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--root", required=True, type=Path)
    args = parser.parse_args()
    root = args.root.resolve(strict=True)
    if not root.is_dir() or not root.is_relative_to(ROOT / ".local" / "ftp-integration"):
        parser.error("Serve only a synthetic directory under .local/ftp-integration")
    version = importlib.metadata.version("pyftpdlib")
    if version != "2.2.0":
        parser.error("Install the pinned tools/ftp-fixture-requirements.txt")
    logging.basicConfig(level=logging.DEBUG, format="%(asctime)s %(levelname)s %(message)s")
    authorizer = DummyAuthorizer()
    authorizer.add_user("fixture-user", "fixture-password", str(root), perm="elradfmwMT")

    class Handler(FTPHandler):
        pass

    Handler.authorizer = authorizer
    Handler.banner = "JFTP synthetic loopback integration fixture"
    server = FTPServer(("127.0.0.1", 0), Handler)
    server.max_cons = 16
    server.max_cons_per_ip = 16
    ready = {"pid": os.getpid(), "parent_pid": os.getppid(), "host": "127.0.0.1", "port": server.socket.getsockname()[1],
             "pyftpdlib": version, "root": str(root)}
    (root.parent / "ready.json").write_text(json.dumps(ready, indent=2) + "\n", encoding="utf-8")
    def stop_when_requested():
        if (root.parent / "STOP").exists():
            server.close_all()
    server.ioloop.call_every(0.1, stop_when_requested)
    try:
        server.serve_forever()
    finally:
        server.close_all()


if __name__ == "__main__":
    main()
