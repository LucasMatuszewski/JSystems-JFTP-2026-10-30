package com.myjavaworld.jftp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

import junit.framework.TestCase;

public class LocalFileEnglishDisplayTest extends TestCase {

	public void testRegularFileDisplayNamePreservesExactAndUnicodeBasenames()
			throws Exception {
		Path temporary = Files.createTempDirectory("jftp-display-name-");
		try {
			Path mixedCase = Files.createFile(temporary.resolve("Mixed Case.txt"));
			Path unicode = Files.createFile(temporary.resolve("Résumé 文件.txt"));
			assertEquals("Mixed Case.txt",
					new LocalFile(mixedCase.toFile()).getDisplayName());
			assertEquals("Résumé 文件.txt",
					new LocalFile(unicode.toFile()).getDisplayName());
		} finally {
			deleteTree(temporary);
		}
	}

	public void testTxtFileTypeIsEnglish() throws Exception {
		Path temporary = Files.createTempDirectory("jftp-type-txt-");
		try {
			Path file = Files.createFile(temporary.resolve("example.TXT"));
			assertEquals("TXT File", new LocalFile(file.toFile()).getType());
		} finally {
			deleteTree(temporary);
		}
	}

	public void testExtensionlessRegularFileTypeIsEnglish() throws Exception {
		Path temporary = Files.createTempDirectory("jftp-type-file-");
		try {
			Path file = Files.createFile(temporary.resolve("README"));
			assertEquals("File", new LocalFile(file.toFile()).getType());
		} finally {
			deleteTree(temporary);
		}
	}

	public void testDirectoryTypeIsEnglish() throws Exception {
		Path temporary = Files.createTempDirectory("jftp-type-directory-");
		try {
			Path directory = Files.createDirectory(temporary.resolve("Folder"));
			assertEquals("Directory", new LocalFile(directory.toFile()).getType());
		} finally {
			deleteTree(temporary);
		}
	}

	public void testEveryFilesystemRootDisplaysItsAbsolutePath() throws Exception {
		Path temporary = Files.createTempDirectory("jftp-root-display-");
		try {
			File[] roots = File.listRoots();
			assertTrue("File.listRoots() must return at least one root",
					roots.length > 0);
			for (int i = 0; i < roots.length; i++) {
				assertEquals(roots[i].getAbsolutePath(),
						new LocalFile(roots[i]).getDisplayName());
			}
		} finally {
			deleteTree(temporary);
		}
	}

	private static void deleteTree(Path root) throws IOException {
		Stream<Path> paths = Files.walk(root);
		try {
			paths.sorted(Comparator.reverseOrder()).forEach(path -> {
				try {
					Files.deleteIfExists(path);
				} catch (IOException exception) {
					throw new RuntimeException(exception);
				}
			});
		} finally {
			paths.close();
		}
	}
}
