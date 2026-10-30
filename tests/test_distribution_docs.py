"""Check relative Markdown links in the README shipped in the actual ZIP."""
from pathlib import Path
import posixpath
import re
import unittest
import urllib.parse
import zipfile

ROOT = Path(__file__).resolve().parents[1]


class DistributionReadmeLinksTests(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        packages = list((ROOT / "target").glob("jftp-*-bin.zip"))
        if len(packages) != 1:
            raise RuntimeError("Expected one existing binary distribution ZIP")
        cls.archive = zipfile.ZipFile(packages[0])
        cls.addClassCleanup(cls.archive.close)

    def test_relative_markdown_links_resolve_inside_distribution(self):
        entries = set(self.archive.namelist())
        readmes = [name for name in entries if name.endswith("/README.md")]
        self.assertEqual(1, len(readmes), "Expected one packaged README.md")
        readme = readmes[0]
        contents = self.archive.read(readme).decode("utf-8")
        targets = re.findall(r"\[[^\]]*\]\((?:<([^>]+)>|([^\s)]+))(?:\s+[^)]*)?\)", contents)
        links = [angle or plain for angle, plain in targets]
        missing = []
        for target in links:
            target = urllib.parse.unquote(target)
            parsed = urllib.parse.urlsplit(target)
            if parsed.scheme or target.startswith("//") or not parsed.path:
                continue
            resolved = posixpath.normpath(posixpath.join(posixpath.dirname(readme), parsed.path))
            if resolved not in entries:
                missing.append(f"{target} -> {resolved}")
        self.assertTrue(links, "Packaged README has no Markdown links to check")
        self.assertEqual([], missing, "Relative README links missing from ZIP: " + "; ".join(missing))


if __name__ == "__main__":
    unittest.main()
