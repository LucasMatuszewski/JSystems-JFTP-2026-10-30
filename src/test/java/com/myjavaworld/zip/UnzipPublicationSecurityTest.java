package com.myjavaworld.zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import junit.framework.TestCase;

public class UnzipPublicationSecurityTest extends TestCase {

	public void testReplacingHardLinkedDestinationDoesNotChangeOutsideFile()
			throws Exception {
		Path temporary = createTemporaryDirectory("jftp-unzip-hardlink-");
		try {
			Path outside = temporary.resolve("outside.bin");
			byte[] originalBytes = new byte[] { 3, 4, 5, 6, 7 };
			Files.write(outside, originalBytes);
			Path output = Files.createDirectory(temporary.resolve("output"));
			Path destination = output.resolve("file.bin");
			Files.createLink(destination, outside);
			Path archive = temporary.resolve("input.zip");
			byte[] entryBytes = new byte[] { 9, 0, (byte) 0xfe };
			createDeflatedArchive(archive.toFile(), "file.bin", entryBytes);

			Unzip unzip = new Unzip(archive.toFile());
			unzip.setTargetDirectory(output.toFile());
			unzip.open();
			try {
				unzip.unzip();
			} finally {
				unzip.close();
			}

			assertBytes(entryBytes, Files.readAllBytes(destination));
			assertBytes(originalBytes, Files.readAllBytes(outside));
			assertFalse("Published output must no longer share the old file",
					Files.isSameFile(destination, outside));
		} finally {
			deleteTree(temporary);
		}
	}

	public void testCorruptStoredEntryPreservesOldFileAndCleansUnpublishedOutput()
			throws Exception {
		Path temporary = createTemporaryDirectory("jftp-unzip-corrupt-");
		try {
			Path archive = temporary.resolve("input.zip");
			byte[] entryBytes = new byte[] { 1, 3, 5, 7, 9, 11, 13, 15 };
			createStoredArchive(archive.toFile(), "file.bin", entryBytes);
			byte[] corruptedArchive = corruptFirstEntryDataByte(archive);
			assertStoredArchiveHasCrcFailure(archive);

			Path output = Files.createDirectory(temporary.resolve("output"));
			Path destination = output.resolve("file.bin");
			byte[] previousBytes = new byte[] { 22, 21, 20, 19, 18, 17 };
			Files.write(destination, previousBytes);

			Unzip unzip = new Unzip(archive.toFile());
			unzip.setTargetDirectory(output.toFile());
			unzip.open();
			IOException extractionFailure = null;
			try {
				unzip.unzip();
			} catch (IOException expected) {
				extractionFailure = expected;
			} finally {
				unzip.close();
			}

			assertNotNull("Corrupt entry should fail extraction", extractionFailure);
			assertBytes(previousBytes, Files.readAllBytes(destination));
			assertEquals(Collections.singletonList("file.bin"), listNames(output));
			assertBytes(corruptedArchive, Files.readAllBytes(archive));
			Path renamedArchive = temporary.resolve("renamed.zip");
			Files.move(archive, renamedArchive);
			assertTrue(Files.isRegularFile(renamedArchive));
			assertBytes(corruptedArchive, Files.readAllBytes(renamedArchive));
		} finally {
			deleteTree(temporary);
		}
	}

	public void testEmptyArchiveLeavesExistingOutputContentsUnchanged()
			throws Exception {
		Path temporary = createTemporaryDirectory("jftp-unzip-empty-");
		try {
			Path archive = temporary.resolve("empty.zip");
			createEmptyArchive(archive.toFile());
			Path output = Files.createDirectory(temporary.resolve("output"));
			Path keep = output.resolve("keep.bin");
			byte[] keepBytes = new byte[] { 0, 1, 2, (byte) 0xff, 42 };
			Files.write(keep, keepBytes);

			Unzip unzip = new Unzip(archive.toFile());
			unzip.setTargetDirectory(output.toFile());
			unzip.open();
			try {
				unzip.unzip();
			} finally {
				unzip.close();
			}

			assertBytes(keepBytes, Files.readAllBytes(keep));
			assertEquals(Collections.singletonList("keep.bin"), listNames(output));
		} finally {
			deleteTree(temporary);
		}
	}

	private static Path createTemporaryDirectory(String prefix) throws IOException {
		return Files.createTempDirectory(
				Paths.get(System.getProperty("java.io.tmpdir")), prefix);
	}

	private static void createDeflatedArchive(File archive, String name,
			byte[] contents) throws IOException {
		ZipOutputStream output = new ZipOutputStream(new FileOutputStream(archive));
		try {
			output.putNextEntry(new ZipEntry(name));
			output.write(contents);
			output.closeEntry();
		} finally {
			output.close();
		}
	}

	private static void createStoredArchive(File archive, String name,
			byte[] contents) throws IOException {
		CRC32 crc = new CRC32();
		crc.update(contents);
		ZipEntry entry = new ZipEntry(name);
		entry.setMethod(ZipEntry.STORED);
		entry.setSize(contents.length);
		entry.setCompressedSize(contents.length);
		entry.setCrc(crc.getValue());
		ZipOutputStream output = new ZipOutputStream(new FileOutputStream(archive));
		try {
			output.putNextEntry(entry);
			output.write(contents);
			output.closeEntry();
		} finally {
			output.close();
		}
	}

	private static void createEmptyArchive(File archive) throws IOException {
		ZipOutputStream output = new ZipOutputStream(new FileOutputStream(archive));
		try {
			// An empty archive has no entries.
		} finally {
			output.close();
		}
	}

	private static byte[] corruptFirstEntryDataByte(Path archive)
			throws IOException {
		byte[] bytes = Files.readAllBytes(archive);
		assertEquals("Expected ZIP local-file header", 0x50, bytes[0] & 0xff);
		assertEquals("Expected ZIP local-file header", 0x4b, bytes[1] & 0xff);
		int nameLength = littleEndianUnsignedShort(bytes, 26);
		int extraLength = littleEndianUnsignedShort(bytes, 28);
		int dataOffset = 30 + nameLength + extraLength;
		if (dataOffset >= bytes.length) {
			throw new IOException("Stored ZIP entry has no data byte to corrupt");
		}
		bytes[dataOffset] = (byte) (bytes[dataOffset] ^ 0x01);
		Files.write(archive, bytes);
		return bytes;
	}

	private static int littleEndianUnsignedShort(byte[] bytes, int offset) {
		return (bytes[offset] & 0xff) | ((bytes[offset + 1] & 0xff) << 8);
	}

	private static void assertStoredArchiveHasCrcFailure(Path archive)
			throws IOException {
		ZipInputStream input = new ZipInputStream(Files.newInputStream(archive));
		try {
			assertNotNull("Expected one stored ZIP entry", input.getNextEntry());
			byte[] buffer = new byte[128];
			boolean crcFailure = false;
			try {
				while (input.read(buffer) != -1) {
					// Consume the entry fully so ZipInputStream verifies its CRC.
				}
			} catch (ZipException expected) {
				crcFailure = true;
			}
			assertTrue("JDK ZipInputStream did not detect the corrupted CRC", crcFailure);
		} finally {
			input.close();
		}
	}

	private static List<String> listNames(Path directory) throws IOException {
		java.util.stream.Stream<Path> children = Files.list(directory);
		try {
			List<String> names = new ArrayList<String>();
			java.util.Iterator<Path> iterator = children.iterator();
			while (iterator.hasNext()) {
				names.add(iterator.next().getFileName().toString());
			}
			Collections.sort(names);
			return names;
		} finally {
			children.close();
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
