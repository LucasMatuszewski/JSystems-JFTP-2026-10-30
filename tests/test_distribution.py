"""Inspect the actual assembled archive; run after mvnw package."""
from pathlib import Path
import io
import re
import unittest
import zipfile

ROOT = Path(__file__).resolve().parents[1]


class DistributionTests(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        packages = list((ROOT / "target").glob("jftp-*-bin.zip"))
        if len(packages) != 1:
            raise RuntimeError("Build one distribution with mvnw package first")
        cls.archive = zipfile.ZipFile(packages[0])
        cls.addClassCleanup(cls.archive.close)
        cls.entries = set(cls.archive.namelist())
        cls.launchers = [name for name in cls.entries if name.endswith(("/jftp.bat", "/jftp.sh"))]
        if len(cls.launchers) != 2:
            raise RuntimeError("Expected both packaged platform launchers")

    def test_each_launcher_references_an_existing_packaged_application_jar(self):
        for name in self.launchers:
            with self.subTest(launcher=name):
                script = self.archive.read(name).decode("utf-8")
                match = re.search(r'\bjava\s+-jar\s+(?:"([^"]+)"|(\S+))', script)
                self.assertIsNotNone(match, "Launcher has no java -jar command")
                jar = (match.group(1) or match.group(2)).replace("\\", "/")
                jar = jar.replace("%~dp0", "").replace("$APP_DIR/", "")
                full = name.rsplit("/", 1)[0] + "/" + jar
                self.assertIn(full, self.entries, f"Launcher references missing JAR: {jar}")

    def test_manifest_main_class_and_dependency_classpath_exist(self):
        jars = [name for name in self.entries if re.search(r'/jftp-[^/]+\.jar$', name)]
        self.assertEqual(1, len(jars))
        with zipfile.ZipFile(io.BytesIO(self.archive.read(jars[0]))) as app:
            manifest = app.read("META-INF/MANIFEST.MF").decode("utf-8").replace("\r\n ", "")
            self.assertIn("Main-Class: com.myjavaworld.jftp.JFTPApplication", manifest)
            self.assertIn("com/myjavaworld/jftp/JFTPApplication.class", app.namelist())
            classpath = re.search(r'^Class-Path: (.+)$', manifest, re.MULTILINE)
            self.assertIsNotNone(classpath)
            prefix = jars[0].rsplit("/", 1)[0] + "/"
            for dependency in classpath.group(1).strip().split():
                self.assertIn(prefix + dependency, self.entries)

    def test_distribution_includes_product_documents_without_agent_instructions(self):
        prefix = self.launchers[0].rsplit("/", 1)[0] + "/"
        for name in ("README.md", "LICENSE.txt", "NOTICE.txt"):
            self.assertIn(prefix + name, self.entries)
        self.assertNotIn(prefix + "AGENTS.md", self.entries)


if __name__ == "__main__":
    unittest.main()
