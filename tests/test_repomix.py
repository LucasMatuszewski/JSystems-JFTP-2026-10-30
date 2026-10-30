import json
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
CONFIG_PATH = Path("docs/repomix/artifacts/repomix.config.json")
PACK_PATH = Path("docs/repomix/connection-transfer.full.xml")
PROVENANCE_PATH = Path("docs/repomix/connection-transfer.provenance.json")
ARTIFACT_PATHS = (
    Path("docs/repomix/artifacts/repomix.config.json"),
    Path("docs/repomix/artifacts/package.json"),
    Path("docs/repomix/artifacts/package-lock.json"),
)
SESSION_PATH = Path("src/main/java/com/myjavaworld/jftp/FTPSession.java")


class RepomixCliTests(unittest.TestCase):
    def make_fixture(self):
        temporary = tempfile.TemporaryDirectory()
        fixture = Path(temporary.name)

        def copy_file(relative_path, normalize_crlf=False):
            source = ROOT / relative_path
            target = fixture / relative_path
            target.parent.mkdir(parents=True, exist_ok=True)
            content = source.read_bytes()
            if normalize_crlf:
                content = content.replace(b"\r\n", b"\n")
            target.write_bytes(content)

        copy_file(Path("tools/repomix.py"))
        copy_file(PACK_PATH)
        copy_file(PROVENANCE_PATH)
        for path in ARTIFACT_PATHS:
            copy_file(path)

        config = json.loads((fixture / CONFIG_PATH).read_text(encoding="utf-8"))
        for name in config["include"]:
            copy_file(Path(name), normalize_crlf=True)
        return temporary, fixture, config

    def run_cli(self, fixture, *arguments):
        return subprocess.run(
            [sys.executable, str(fixture / "tools/repomix.py"), *arguments],
            cwd=fixture,
            text=True,
            encoding="utf-8",
            capture_output=True,
        )

    def test_verify_list_and_extract_saved_pack(self):
        temporary, fixture, config = self.make_fixture()
        try:
            verified = self.run_cli(fixture, "verify")
            self.assertEqual(verified.returncode, 0, verified.stderr)
            self.assertIn("Verified 60 full files", verified.stdout)

            listed = self.run_cli(fixture, "list")
            self.assertEqual(listed.returncode, 0, listed.stderr)
            listed_paths = listed.stdout.splitlines()
            self.assertEqual(set(listed_paths), set(config["include"]))
            self.assertEqual(len(listed_paths), 60)
            self.assertIn("src/main/java/com/myjavaworld/zip/ArchiveEntryPath.java", listed_paths)

            extracted = self.run_cli(
                fixture, "extract", SESSION_PATH.as_posix(), "--start", "593", "--end", "598"
            )
            self.assertEqual(extracted.returncode, 0, extracted.stderr)
            original_lines = (ROOT / SESSION_PATH).read_bytes().decode("utf-8").replace(
                "\r\n", "\n"
            ).splitlines()
            expected = [f"{number}: {original_lines[number - 1]}" for number in range(593, 599)]
            self.assertEqual(extracted.stdout.splitlines(), expected)
        finally:
            temporary.cleanup()

    def test_verify_rejects_changed_selected_source(self):
        temporary, fixture, _ = self.make_fixture()
        try:
            source = fixture / SESSION_PATH
            text = source.read_text(encoding="utf-8")
            self.assertIn("class FTPSession", text)
            source.write_text(text.replace("class FTPSession", "class FTPSessionChanged", 1), encoding="utf-8")

            result = self.run_cli(fixture, "verify")
            self.assertNotEqual(result.returncode, 0)
            self.assertIn("Selected source changed", result.stderr)
        finally:
            temporary.cleanup()

    def test_verify_rejects_changed_packed_source(self):
        temporary, fixture, _ = self.make_fixture()
        try:
            pack = fixture / PACK_PATH
            content = pack.read_text(encoding="utf-8")
            self.assertIn("class FTPSession", content)
            pack.write_text(content.replace("class FTPSession", "class FTPSessionChanged", 1), encoding="utf-8")

            result = self.run_cli(fixture, "verify")
            self.assertNotEqual(result.returncode, 0)
            self.assertIn("Pack hash differs", result.stderr)
        finally:
            temporary.cleanup()


if __name__ == "__main__":
    unittest.main()
