package com.myjavaworld.zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import junit.framework.TestCase;

public class UnzipCompatibilityCharacterizationTest extends TestCase {

	public void testExistingLongerFileIsOverwrittenWithExactEntryBytes()
			throws Exception {
		Path temporary = createTemporaryDirectory("jftp-unzip-overwrite-");
		try {
			Path archive = temporary.resolve("input.zip");
			byte[] entryBytes = new byte[] { 4, 0, (byte) 0xff, 9 };
			createArchive(archive.toFile(), "result.bin", entryBytes);
			Path output = Files.createDirectory(temporary.resolve("output"));
			byte[] existingBytes = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 };
			Files.write(output.resolve("result.bin"), existingBytes);

			Unzip unzip = new Unzip(archive.toFile());
			unzip.setTargetDirectory(output.toFile());
			unzip.open();
			try {
				unzip.unzip();
			} finally {
				unzip.close();
			}

			assertBytes(entryBytes, Files.readAllBytes(output.resolve("result.bin")));
		} finally {
			deleteTree(temporary);
		}
	}

	public void testFileEntryWithoutDirectoryEntriesCreatesNestedParents()
			throws Exception {
		Path temporary = createTemporaryDirectory("jftp-unzip-nested-");
		try {
			Path archive = temporary.resolve("input.zip");
			byte[] entryBytes = new byte[] { 0, 5, (byte) 0x80, 11, 12 };
			createArchive(archive.toFile(), "nested/path/data.bin", entryBytes);
			Path output = Files.createDirectory(temporary.resolve("output"));

			Unzip unzip = new Unzip(archive.toFile());
			unzip.setTargetDirectory(output.toFile());
			unzip.open();
			try {
				unzip.unzip();
			} finally {
				unzip.close();
			}

			assertTrue(Files.isDirectory(output.resolve("nested/path")));
			assertBytes(entryBytes, Files.readAllBytes(output.resolve("nested/path/data.bin")));
		} finally {
			deleteTree(temporary);
		}
	}

	public void testLargeNonAlignedBinaryEntryIsExtractedExactly()
			throws Exception {
		Path temporary = createTemporaryDirectory("jftp-unzip-large-");
		try {
			Path archive = temporary.resolve("input.zip");
			byte[] entryBytes = new byte[65543];
			for (int i = 0; i < entryBytes.length; i++) {
				entryBytes[i] = (byte) (i * 31 + 7);
			}
			createArchive(archive.toFile(), "large.bin", entryBytes);
			Path output = Files.createDirectory(temporary.resolve("output"));

			Unzip unzip = new Unzip(archive.toFile());
			unzip.setTargetDirectory(output.toFile());
			unzip.open();
			try {
				unzip.unzip();
			} finally {
				unzip.close();
			}

			assertBytes(entryBytes, Files.readAllBytes(output.resolve("large.bin")));
		} finally {
			deleteTree(temporary);
		}
	}

	private static Path createTemporaryDirectory(String prefix) throws IOException {
		return Files.createTempDirectory(
				Paths.get(System.getProperty("java.io.tmpdir")), prefix);
	}

	private static void createArchive(File archive, String name, byte[] contents)
			throws IOException {
		ZipOutputStream output = new ZipOutputStream(new FileOutputStream(archive));
		try {
			output.putNextEntry(new ZipEntry(name));
			output.write(contents);
			output.closeEntry();
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
