package com.myjavaworld.zip;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import junit.framework.TestCase;

public class ArchiveEntryPathTest extends TestCase {

	private final Path root = Paths.get(System.getProperty("java.io.tmpdir"))
			.toAbsolutePath().normalize().resolve("jftp-policy-test");

	public void testOrdinaryNestedUnicodeAndDotfilePathsAreAccepted()
			throws Exception {
		assertEquals(root.resolve("nested/space ü.txt"),
				ArchiveEntryPath.resolve(root, "nested/space ü.txt", false));
		assertEquals(root.resolve(".config"),
				ArchiveEntryPath.resolve(root, ".config", false));
	}

	public void testNestedDirectoryEntryResolvesWithoutTrailingSlash()
			throws Exception {
		assertEquals(root.resolve("nested/path"),
				ArchiveEntryPath.resolve(root, "nested/path/", true));
	}

	public void testUnsafePathFormsAreRejectedWithEnglishReason() throws Exception {
		String[] names = new String[] {
				"", ".", "..", "../outside", "nested/../outside", "/absolute",
				"C:/drive", "C:relative", "\\rooted", "\\\\host\\share",
				"//host/share", "nested\\file", "a//b", "a/./b", "a/../b",
				"trailing.", "trailing ", "a:b", "a?b", "a*b", "<html>",
				"quote\"", "pipe|", "line\nbreak", "nul" + (char) 0 + "byte"
		};
		for (String name : names) {
			assertUnsafe(name);
		}
	}

	public void testWindowsReservedDeviceBasenamesAreRejected() throws Exception {
		String[] names = new String[] {
				"CON", "PRN", "AUX", "NUL", "COM1", "COM9", "LPT1", "LPT9",
				"cOn", "pRn", "AuX", "nUl", "cOm1", "cOm9", "lPt1", "lPt9",
				"con.txt", "prn.log", "aux.dat", "nul.bin", "com1.txt",
				"com9.log", "lpt1.dat", "lpt9.bin", "COM¹", "LPT²",
				"com¹.txt", "lpt².log", "CONIN$", "CONOUT$", "CLOCK$"
		};
		for (String name : names) {
			assertUnsafe(name);
		}
	}

	private void assertUnsafe(String name) {
		try {
			ArchiveEntryPath.resolve(root, name, false);
			fail("Expected unsafe ZIP entry to be rejected: " + printable(name));
		} catch (IOException expected) {
			String message = expected.getMessage();
			assertNotNull("Unsafe entry error should have an English message", message);
			assertTrue("Unexpected unsafe-entry message: " + message,
					message.startsWith("Unsafe ZIP entry"));
			assertTrue("Unsafe-entry message should include a reason: " + message,
					message.length() > "Unsafe ZIP entry".length());
		}
	}

	public void testRejectionMessageEscapesUntrustedMarkupAndControls() throws Exception {
		try {
			ArchiveEntryPath.resolve(root, "<html>\n&'\"", false);
			fail("Unsafe markup must be rejected");
		} catch (IOException expected) {
			String message = expected.getMessage();
			assertTrue(message.startsWith("Unsafe ZIP entry"));
			assertTrue(message.contains("\\u003c"));
			assertTrue(message.contains("\\u000a"));
			assertFalse(message.contains("<"));
			assertFalse(message.contains("\n"));
			assertFalse(message.contains("&"));
		}
	}

	private static String printable(String value) {
		return value.replace("\n", "\\n").replace("\r", "\\r");
	}
}
