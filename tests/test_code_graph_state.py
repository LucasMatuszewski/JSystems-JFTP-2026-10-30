import importlib.util
import os
from pathlib import Path
import tempfile
import unittest
from unittest.mock import patch

ROOT = Path(__file__).resolve().parents[1]
SPEC = importlib.util.spec_from_file_location("graph_state", ROOT / "tools/code_graph.py")
GRAPH = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(GRAPH)


class GraphStateTests(unittest.TestCase):
    def test_default_retains_existing_state_location(self):
        with patch.dict(os.environ):
            os.environ.pop("JFTP_GRAPH_STATE", None)
            self.assertEqual(GRAPH.BASE, GRAPH.state_directory())

    def test_override_keeps_verified_installation_separate(self):
        local = ROOT / ".local/graph-state-tests"
        local.mkdir(parents=True, exist_ok=True)
        with tempfile.TemporaryDirectory(dir=local) as temporary:
            state = Path(temporary) / "state with spaces"
            with patch.dict(os.environ, {"JFTP_GRAPH_STATE": str(state)}):
                env = GRAPH.environment()
            self.assertEqual(str(state / "runtime"), env["CBM_RUNTIME_DIR"])
            self.assertEqual(str(state / "cache"), env["CBM_CACHE_DIR"])
            self.assertEqual(str(GRAPH.ROOT / "src"), env["CBM_ALLOWED_ROOT"])
            self.assertTrue((state / "runtime").is_dir())
            self.assertEqual(Path.home() / ".jftp-code-graph", GRAPH.BASE)

    def test_relative_override_is_rejected(self):
        with patch.dict(os.environ, {"JFTP_GRAPH_STATE": "relative/state"}):
            with self.assertRaises(ValueError):
                GRAPH.state_directory()


if __name__ == "__main__":
    unittest.main()
