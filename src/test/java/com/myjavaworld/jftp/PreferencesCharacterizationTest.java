package com.myjavaworld.jftp;

import java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

import com.myjavaworld.ftp.FTPConstants;

public class PreferencesCharacterizationTest extends TestCase {

	private Path preferencesFile;
	private byte[] originalPreferences;
	private boolean preferencesExisted;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		Path temporaryDirectory = Paths.get(System.getProperty("java.io.tmpdir"))
				.toAbsolutePath().normalize();
		Path userHome = Paths.get(System.getProperty("user.home"))
				.toAbsolutePath().normalize();
		String profileName = userHome.getFileName() == null ? ""
				: userHome.getFileName().toString();
		if (!userHome.startsWith(temporaryDirectory)
				|| !("profile".equals(profileName) || "test-profile".equals(profileName))) {
			fail("Preferences tests require an isolated profile named profile or test-profile under java.io.tmpdir");
		}

		Path dataDirectory = userHome.resolve(".jftp").resolve("data");
		Files.createDirectories(dataDirectory);
		preferencesFile = dataDirectory.resolve("preferences.ser");
		preferencesExisted = Files.exists(preferencesFile);
		originalPreferences = preferencesExisted
				? Files.readAllBytes(preferencesFile) : null;

		// First JFTP reference occurs after confirming the home is a synthetic profile.
		JFTP.loadPreferences();
	}

	@Override
	protected void tearDown() throws Exception {
		try {
			if (preferencesFile != null) {
				if (preferencesExisted) {
					Files.write(preferencesFile, originalPreferences);
				} else {
					Files.deleteIfExists(preferencesFile);
				}
			}
		} finally {
			super.tearDown();
		}
	}

	public void testLegacyDefaultsArePassivePlainFtpAndBinary() {
		JFTPPreferences preferences = new JFTPPreferences();
		assertTrue(preferences.isPassive());
		assertEquals(FTPConstants.USE_NO_SSL, preferences.getSSLUsage());
		assertEquals(FTPConstants.TYPE_BINARY,
				preferences.getDefaultTransferType());
	}

	public void testLegacyTxtExtensionDefaultsToAscii() {
		JFTPPreferences preferences = new JFTPPreferences();
		Map transferTypes = preferences.getTransferTypes();
		assertEquals(Integer.valueOf(FTPConstants.TYPE_ASCII), transferTypes.get("TXT"));
	}

	public void testConfiguredSettingsSurviveJavaSerializationRoundtrip()
			throws Exception {
		JFTPPreferences original = configuredPreferences();
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		ObjectOutputStream output = new ObjectOutputStream(bytes);
		output.writeObject(original);
		output.close();

		ObjectInputStream input = new ObjectInputStream(
				new ByteArrayInputStream(bytes.toByteArray()));
		JFTPPreferences restored;
		try {
			restored = (JFTPPreferences) input.readObject();
		} finally {
			input.close();
		}
		assertConfiguredPreferences(restored);
	}

	public void testJftpSaveAndLoadRetainDirectoryEmailBoundsAndTransferTypes()
			throws Exception {
		JFTPPreferences original = configuredPreferences();
		JFTP.savePreferences(original);

		JFTPPreferences restored = JFTP.loadPreferences();
		assertConfiguredPreferences(restored);
		assertTrue(Files.isRegularFile(preferencesFile));
	}

	public void testMissingPreferencesFileCreatesAndLoadsDefaults()
			throws Exception {
		Files.deleteIfExists(preferencesFile);

		JFTPPreferences loaded = JFTP.loadPreferences();
		assertTrue(Files.isRegularFile(preferencesFile));
		assertTrue(loaded.isPassive());
		assertEquals(FTPConstants.USE_NO_SSL, loaded.getSSLUsage());
		assertEquals(FTPConstants.TYPE_BINARY,
				loaded.getDefaultTransferType());
		assertEquals("you@yourcompany.com", loaded.getEmail());
	}

	public void testLegacyCorruptPreferencesStreamReturnsDefaultsAndLeavesFile()
			throws Exception {
		byte[] corrupt = new byte[] { 0x13, 0x37, 0x00, 0x01, 0x02 };
		Files.write(preferencesFile, corrupt);

		JFTPPreferences loaded = JFTP.loadPreferences();
		assertTrue(loaded.isPassive());
		assertEquals(FTPConstants.TYPE_BINARY,
				loaded.getDefaultTransferType());
		assertEquals("you@yourcompany.com", loaded.getEmail());
		assertBytes(corrupt, Files.readAllBytes(preferencesFile));
	}

	private static JFTPPreferences configuredPreferences() {
		JFTPPreferences preferences = new JFTPPreferences();
		preferences.setLocalDirectory(new File("synthetic-local-dir").getPath());
		preferences.setEmail("synthetic@example.test");
		preferences.setWindowBounds(new Rectangle(11, 22, 640, 480));
		preferences.setDefaultTransferType(FTPConstants.TYPE_ASCII);
		Map<String, Integer> transferTypes = new TreeMap<String, Integer>();
		transferTypes.put("CUSTOM", Integer.valueOf(FTPConstants.TYPE_ASCII));
		transferTypes.put("DATA", Integer.valueOf(FTPConstants.TYPE_BINARY));
		preferences.setTransferTypes(transferTypes);
		return preferences;
	}

	private static void assertConfiguredPreferences(JFTPPreferences preferences) {
		assertEquals(new File("synthetic-local-dir").getPath(),
				preferences.getLocalDirectory());
		assertEquals("synthetic@example.test", preferences.getEmail());
		assertEquals(new Rectangle(11, 22, 640, 480),
				preferences.getWindowBounds());
		assertEquals(FTPConstants.TYPE_ASCII,
				preferences.getDefaultTransferType());
		Map transferTypes = preferences.getTransferTypes();
		assertEquals(Integer.valueOf(FTPConstants.TYPE_ASCII),
				transferTypes.get("CUSTOM"));
		assertEquals(Integer.valueOf(FTPConstants.TYPE_BINARY),
				transferTypes.get("DATA"));
	}

	private static void assertBytes(byte[] expected, byte[] actual) {
		assertEquals(expected.length, actual.length);
		for (int i = 0; i < expected.length; i++) {
			assertEquals("byte at offset " + i, expected[i], actual[i]);
		}
	}
}
