package com.myjavaworld.zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import junit.framework.TestCase;

/** Explicit filesystem-prerequisite suite; not run by ordinary Surefire. */
public class UnzipSymbolicLinkSecurityIT extends TestCase {

	public void testDirectorySymbolicLinkRejectsArchiveBeforeAnyOutput()
			throws Exception {
		Path temporary = createTemporaryDirectory("jftp-unzip-dir-link-");
		List<Path> links = new ArrayList<Path>();
		try {
			Path outsideDirectory = Files.createDirectory(temporary.resolve("sibling"));
			Path sentinel = outsideDirectory.resolve("entry.bin");
			byte[] sentinelBytes = new byte[] { 4, 5, 6, 7 };
			Files.write(sentinel, sentinelBytes);
			Path output = Files.createDirectory(temporary.resolve("output"));
			createSymbolicLink(output.resolve("link"), outsideDirectory, links);
			Path archive = temporary.resolve("input.zip");
			createArchive(archive.toFile(), new String[] {
					"safe-first.bin", "link/entry.bin" });

			assertRejectedBeforeFirstOutput(archive, output);
			assertBytes(sentinelBytes, Files.readAllBytes(sentinel));
			assertTrue(Files.isSymbolicLink(output.resolve("link")));
		} finally {
			try {
				deleteLinks(links);
			} finally {
				deleteTree(temporary);
			}
		}
	}

	public void testFileSymbolicLinkRejectsArchiveBeforeAnyOutput()
			throws Exception {
		Path temporary = createTemporaryDirectory("jftp-unzip-file-link-");
		List<Path> links = new ArrayList<Path>();
		try {
			Path outside = temporary.resolve("sibling.bin");
			byte[] sentinelBytes = new byte[] { 8, 9, 10, 11 };
			Files.write(outside, sentinelBytes);
			Path output = Files.createDirectory(temporary.resolve("output"));
			createSymbolicLink(output.resolve("alias.bin"), outside, links);
			Path archive = temporary.resolve("input.zip");
			createArchive(archive.toFile(), new String[] {
					"safe-first.bin", "alias.bin" });

			assertRejectedBeforeFirstOutput(archive, output);
			assertBytes(sentinelBytes, Files.readAllBytes(outside));
			assertTrue(Files.isSymbolicLink(output.resolve("alias.bin")));
		} finally {
			try {
				deleteLinks(links);
			} finally {
				deleteTree(temporary);
			}
		}
	}

	public void testDanglingFileSymbolicLinkRejectsWithoutCreatingTarget()
			throws Exception {
		Path temporary = createTemporaryDirectory("jftp-unzip-dangling-link-");
		List<Path> links = new ArrayList<Path>();
		try {
			Path target = temporary.resolve("missing-target.bin");
			Path output = Files.createDirectory(temporary.resolve("output"));
			Path danglingLink = output.resolve("missing.bin");
			createSymbolicLink(danglingLink, target, links);
			Path archive = temporary.resolve("input.zip");
			createArchive(archive.toFile(), new String[] {
					"safe-first.bin", "missing.bin" });

			assertRejectedBeforeFirstOutput(archive, output);
			assertFalse(Files.exists(target));
			assertTrue(Files.isSymbolicLink(danglingLink));
		} finally {
			try {
				deleteLinks(links);
			} finally {
				deleteTree(temporary);
			}
		}
	}

	private static void assertRejectedBeforeFirstOutput(Path archive, Path output)
			throws Exception {
		Unzip unzip = new Unzip(archive.toFile());
		unzip.setTargetDirectory(output.toFile());
		unzip.open();
		IOException rejection = null;
		try {
			unzip.unzip();
		} catch (IOException expected) {
			rejection = expected;
		} finally {
			unzip.close();
		}
		assertNotNull("Unsafe existing symbolic link should reject extraction", rejection);
		assertNotNull("Rejection should explain the unsafe ZIP entry", rejection.getMessage());
		assertTrue("Unexpected rejection message: " + rejection.getMessage(),
				rejection.getMessage().startsWith("Unsafe ZIP entry"));
		assertFalse("Safe first entry must not be written before validation",
				Files.exists(output.resolve("safe-first.bin")));
	}

	private static Path createTemporaryDirectory(String prefix) throws IOException {
		return Files.createTempDirectory(
				Paths.get(System.getProperty("java.io.tmpdir")), prefix);
	}

	private static void createSymbolicLink(Path link, Path target,
			List<Path> createdLinks) throws IOException {
		try {
			Files.createSymbolicLink(link, target);
			createdLinks.add(link);
		} catch (IOException | UnsupportedOperationException | SecurityException unavailable) {
			throw new IOException("Symbolic-link test prerequisite unavailable for "
					+ link + " -> " + target, unavailable);
		}
	}

	private static void createArchive(File archive, String[] names)
			throws IOException {
		ZipOutputStream output = new ZipOutputStream(new FileOutputStream(archive));
		try {
			for (String name : names) {
				output.putNextEntry(new ZipEntry(name));
				output.write(name.getBytes("UTF-8"));
				output.closeEntry();
			}
		} finally {
			output.close();
		}
	}

	private static void assertBytes(byte[] expected, byte[] actual) {
		assertEquals("Unexpected output length", expected.length, actual.length);
		for (int i = 0; i < expected.length; i++) {
			assertEquals("Unexpected byte at offset " + i, expected[i], actual[i]);
		}
	}

	private static void deleteLinks(List<Path> links) throws IOException {
		for (Path link : links) {
			Files.deleteIfExists(link);
		}
	}

	private static void deleteTree(Path root) throws IOException {
		if (!Files.exists(root)) {
			return;
		}
		java.util.stream.Stream<Path> paths = Files.walk(root);
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
