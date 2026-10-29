package com.myjavaworld.zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import junit.framework.TestCase;

public class ZipCharacterizationTest extends TestCase {

	public void testLegacyUnzipExtractsJdkArchiveNestedBinaryEmptyAndUtf8Entries()
			throws Exception {
		Path temporary = Files.createTempDirectory("jftp-unzip-input-");
		try {
			Path archive = temporary.resolve("input.zip");
			byte[] binary = new byte[] { 0, 1, 2, (byte) 0xff, 0, 42 };
			byte[] text = "synthetic UTF-8 name contents".getBytes("UTF-8");
			createJdkArchive(archive.toFile(), new String[] {
					"nested/", "nested/data.bin", "nested/empty.dat",
					"nested/space ü.txt" }, new byte[][] {
					null, binary, new byte[0], text });
			Path output = temporary.resolve("output");
			Files.createDirectory(output);

			Unzip unzip = new Unzip(archive.toFile());
			unzip.setTargetDirectory(output.toFile());
			unzip.open();
			try {
				unzip.unzip();
			} finally {
				unzip.close();
			}

			assertTrue(Files.isDirectory(output.resolve("nested")));
			assertTrue(Files.exists(output.resolve("nested/empty.dat")));
			assertEquals(0L, Files.size(output.resolve("nested/empty.dat")));
			assertBytes(binary, Files.readAllBytes(output.resolve("nested/data.bin")));
			assertBytes(text, Files.readAllBytes(output.resolve("nested/space ü.txt")));
		} finally {
			deleteTree(temporary);
		}
	}

	public void testLegacyZipOutputHasRelativeNestedNamesAndExactFileBytes()
			throws Exception {
		Path temporary = Files.createTempDirectory("jftp-zip-output-");
		try {
			Path sourceRoot = temporary.resolve("source");
			Path nested = sourceRoot.resolve("nested");
			Files.createDirectories(nested);
			byte[] binary = new byte[] { 9, 0, (byte) 0x80, (byte) 0xfe, 4 };
			byte[] text = "round trip content".getBytes("UTF-8");
			Files.write(nested.resolve("data.bin"), binary);
			Files.write(nested.resolve("space ü.txt"), text);
			Files.write(nested.resolve("empty.dat"), new byte[0]);
			Path archive = temporary.resolve("result.zip");

			Zip zip = new Zip(archive.toFile());
			zip.setRelativeTo(sourceRoot.toFile());
			zip.open();
			try {
				zip.addEntry(nested.toFile());
			} finally {
				zip.close();
			}

			ZipFile standardReader = new ZipFile(archive.toFile());
			try {
				List<String> names = new ArrayList<String>();
				Enumeration<? extends ZipEntry> entries = standardReader.entries();
				while (entries.hasMoreElements()) {
					names.add(entries.nextElement().getName());
				}
				Collections.sort(names);
				List<String> expected = new ArrayList<String>();
				expected.add("nested/");
				expected.add("nested/data.bin");
				expected.add("nested/empty.dat");
				expected.add("nested/space ü.txt");
				Collections.sort(expected);
				assertEquals(expected, names);
				assertBytes(binary, readEntry(standardReader, "nested/data.bin"));
				assertBytes(text, readEntry(standardReader, "nested/space ü.txt"));
				assertBytes(new byte[0], readEntry(standardReader, "nested/empty.dat"));
			} finally {
				standardReader.close();
			}
		} finally {
			deleteTree(temporary);
		}
	}

	public void testLegacyUnzipBeforeOpenThrowsDocumentedIllegalState()
			throws Exception {
		Path temporary = Files.createTempDirectory("jftp-unzip-state-");
		try {
			Path archive = temporary.resolve("empty.zip");
			createJdkArchive(archive.toFile(), new String[0], new byte[0][]);
			Unzip unzip = new Unzip(archive.toFile());
			try {
				unzip.unzip();
				fail("unzip before open should fail");
			} catch (IllegalStateException expected) {
				assertEquals("ZipFile is not open", expected.getMessage());
			}
		} finally {
			deleteTree(temporary);
		}
	}

	public void testLegacyZipAddEntryBeforeOpenThrowsDocumentedIllegalState()
			throws Exception {
		Path temporary = Files.createTempDirectory("jftp-zip-state-");
		try {
			Path source = temporary.resolve("source.txt");
			Files.write(source, new byte[] { 1 });
			Zip zip = new Zip(temporary.resolve("output.zip").toFile());
			try {
				zip.addEntry(source.toFile());
				fail("addEntry before open should fail");
			} catch (IllegalStateException expected) {
				assertEquals("Zip File was never opened or is already closed. ",
						expected.getMessage());
			}
		} finally {
			deleteTree(temporary);
		}
	}

	public void testLegacyZipCannotAddEntryAfterClose() throws Exception {
		Path temporary = Files.createTempDirectory("jftp-zip-closed-");
		try {
			Path source = temporary.resolve("source.txt");
			Files.write(source, new byte[] { 3, 4 });
			Zip zip = new Zip(temporary.resolve("output.zip").toFile());
			zip.open();
			zip.close();
			try {
				zip.addEntry(source.toFile());
				fail("addEntry after close should fail");
			} catch (IllegalStateException expected) {
				assertEquals("Zip File was never opened or is already closed. ",
						expected.getMessage());
			}
		} finally {
			deleteTree(temporary);
		}
	}

	private static void createJdkArchive(File archive, String[] names,
			byte[][] contents) throws IOException {
		ZipOutputStream output = new ZipOutputStream(new FileOutputStream(archive));
		try {
			for (int i = 0; i < names.length; i++) {
				output.putNextEntry(new ZipEntry(names[i]));
				if (contents[i] != null) {
					output.write(contents[i]);
				}
				output.closeEntry();
			}
		} finally {
			output.close();
		}
	}

	private static byte[] readEntry(ZipFile archive, String name)
			throws IOException {
			java.io.InputStream input = archive.getInputStream(archive.getEntry(name));
		try {
			java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
			byte[] buffer = new byte[256];
			int count;
			while ((count = input.read(buffer)) != -1) {
				output.write(buffer, 0, count);
			}
			return output.toByteArray();
		} finally {
			input.close();
		}
	}

	private static void assertBytes(byte[] expected, byte[] actual) {
		assertEquals(expected.length, actual.length);
		for (int i = 0; i < expected.length; i++) {
			assertEquals("byte at offset " + i, expected[i], actual[i]);
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
