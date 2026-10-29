import hashlib
import importlib.util
import subprocess
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
HELPER_PATH = ROOT / "tools" / "check_source_evidence.py"
SPEC = importlib.util.spec_from_file_location("check_source_evidence", HELPER_PATH)
CHECKER = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(CHECKER)


class SourceEvidenceRevisionTests(unittest.TestCase):
    def setUp(self):
        self.test_area = ROOT / ".local" / "source-evidence-tests"
        self.test_area.mkdir(parents=True, exist_ok=True)
        self.temp = tempfile.TemporaryDirectory(dir=str(self.test_area))
        self.repo = Path(self.temp.name) / "repo"
        self.repo.mkdir()
        self.hooks = Path(self.temp.name) / "empty-hooks"
        self.hooks.mkdir()
        self._git("init", "--quiet")
        self.source = self.repo / "fixture.txt"
        self.committed_bytes = b"alpha\nbeta\n"
        self.source.write_bytes(self.committed_bytes)
        self._git("add", "fixture.txt")
        self._git("commit", "--quiet", "-m", "fixture")
        self.revision = self._git("rev-parse", "HEAD").stdout.strip()

    def tearDown(self):
        self.temp.cleanup()

    def _git(self, *args):
        return subprocess.run(
            [
                "git",
                "-c",
                "user.name=Fixture",
                "-c",
                "user.email=fixture@example.invalid",
                "-c",
                "core.hooksPath=" + str(self.hooks),
                *args,
            ],
            cwd=str(self.repo),
            check=True,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            text=True,
        )

    def _evidence(self, path="fixture.txt"):
        return {
            "citations": [
                {
                    "path": path,
                    "sha256": hashlib.sha256(self.committed_bytes).hexdigest(),
                    "sha256_lf": hashlib.sha256(self.committed_bytes).hexdigest(),
                    "start_line": 1,
                    "end_line": 2,
                    "quote": "alpha\nbeta",
                }
            ]
        }

    def test_default_check_rejects_changed_worktree(self):
        self.source.write_bytes(b"changed\nbeta\n")

        with self.assertRaisesRegex(ValueError, "hash mismatch"):
            CHECKER.check(self._evidence(), root=self.repo)

    def test_revision_check_uses_committed_blob_and_returns_resolved_revision(self):
        self.source.write_bytes(b"changed\nbeta\n")

        result = CHECKER.check(self._evidence(), root=self.repo, revision=self.revision)

        self.assertTrue(result["valid"])
        self.assertEqual(self.revision, result["revision"])

    def test_revision_does_not_bypass_path_outside_root_protection(self):
        outside = Path(self.temp.name) / "outside.txt"
        outside.write_bytes(self.committed_bytes)

        with self.assertRaisesRegex(ValueError, "outside the repository"):
            CHECKER.check(self._evidence("../outside.txt"), root=self.repo, revision=self.revision)

    def test_option_like_invalid_revision_fails_as_value_error(self):
        with self.assertRaises(ValueError):
            CHECKER.check(self._evidence(), root=self.repo, revision="--help")


if __name__ == "__main__":
    unittest.main()
