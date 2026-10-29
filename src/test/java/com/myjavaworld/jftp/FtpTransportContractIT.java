package com.myjavaworld.jftp;

import com.myjavaworld.ftp.DefaultFTPClient;
import com.myjavaworld.ftp.DefaultListParser;
import com.myjavaworld.ftp.FTPConstants;
import com.myjavaworld.ftp.FTPException;
import com.myjavaworld.ftp.RemoteFile;
import junit.framework.TestCase;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

/** Real-server contract used by FTPSession; this is not a native UI test. */
public class FtpTransportContractIT extends TestCase {
    private DefaultFTPClient client;
    private DefaultListParser parser;
    private Path serverDirectory;
    private Path localDirectory;
    private String remoteDirectory;
    private int port;

    protected void setUp() throws Exception {
        String rootProperty = System.getProperty("jftp.fixture.root");
        assertNotNull("Run tools/run_ftp_integration.py to start the real loopback fixture", rootProperty);
        Path root = Paths.get(rootProperty).toRealPath();
        Path ownedRoot = Paths.get(System.getProperty("user.dir"), ".local", "ftp-integration")
                .toAbsolutePath().normalize();
        assertTrue("Fixture must be inside the owned integration directory", root.startsWith(ownedRoot));
        port = Integer.parseInt(System.getProperty("jftp.fixture.port", "0"));
        assertTrue("Fixture port must be supplied", port > 0 && port <= 65535);
        String name = "case-" + UUID.randomUUID();
        serverDirectory = Files.createDirectory(root.resolve(name));
        localDirectory = Files.createTempDirectory(root.getParent(), "client-");
        remoteDirectory = "/" + name;
        parser = new DefaultListParser();
        client = new DefaultFTPClient();
        client.setListParser(parser);
        client.setTimeout(10000);
        client.setPassive(true);
    }

    protected void tearDown() throws Exception {
        try {
            if (client != null && client.isConnected()) client.disconnect();
        } finally {
            removeOwnedTree(serverDirectory);
            removeOwnedTree(localDirectory);
        }
    }

    private void connect() throws Exception {
        client.connect("127.0.0.1", port);
        client.login("fixture-user", "fixture-password");
        client.setWorkingDirectory(parser.createRemoteFile(remoteDirectory, true));
    }

    public void testListsRealFilesUsingTheConfiguredParser() throws Exception {
        Files.write(serverDirectory.resolve("seed.bin"), bytes());
        Files.createDirectory(serverDirectory.resolve("folder"));
        connect();
        Set<String> names = new HashSet<String>();
        for (RemoteFile entry : client.list()) names.add(entry.getName());
        assertEquals(new HashSet<String>(Arrays.asList("seed.bin", "folder")), names);
    }

    public void testDownloadReplacesExistingLocalContentWithoutAppending() throws Exception {
        Files.write(serverDirectory.resolve("seed.bin"), bytes());
        Path destination = localDirectory.resolve("download.bin");
        Files.write(destination, new byte[8192]);
        connect();
        client.download(remote("seed.bin"), destination.toFile(), FTPConstants.TYPE_BINARY, false);
        assertTrue(Arrays.equals(bytes(), Files.readAllBytes(destination)));
    }

    public void testUploadStoresFromByteZeroAndReplacesExistingRemoteContent() throws Exception {
        Path source = localDirectory.resolve("source.bin");
        Files.write(source, bytes());
        Files.write(serverDirectory.resolve("upload with spaces.bin"), new byte[8192]);
        connect();
        client.upload(source.toFile(), remote("upload with spaces.bin"), FTPConstants.TYPE_BINARY, false, 0L);
        assertTrue(Arrays.equals(bytes(), Files.readAllBytes(serverDirectory.resolve("upload with spaces.bin"))));
    }

    public void testPassiveBinaryRoundtripPreservesEveryByte() throws Exception {
        roundtrip(true);
    }

    public void testActiveBinaryRoundtripPreservesEveryByte() throws Exception {
        roundtrip(false);
    }

    public void testMissingRemoteFileReports550AndPreservesExistingLocalFile() throws Exception {
        Path destination = localDirectory.resolve("retained.bin");
        Files.write(destination, bytes());
        connect();
        try {
            client.download(remote("missing.bin"), destination.toFile(), FTPConstants.TYPE_BINARY, false);
            fail("The real server must reject a missing remote file");
        } catch (FTPException expected) {
            assertEquals("550", expected.getErrorCode());
            assertTrue(Arrays.equals(bytes(), Files.readAllBytes(destination)));
        }
    }

    public void testWrongPasswordIsRejectedByTheRealServer() throws Exception {
        client.connect("127.0.0.1", port);
        try {
            client.login("fixture-user", "incorrect-synthetic-password");
            fail("The fixture must reject the wrong password");
        } catch (FTPException expected) {
            assertEquals("530", expected.getErrorCode());
        }
    }

    private void roundtrip(boolean passive) throws Exception {
        client.setPassive(passive);
        Path source = localDirectory.resolve("source.bin");
        Path destination = localDirectory.resolve("result.bin");
        Files.write(source, bytes());
        connect();
        client.upload(source.toFile(), remote("roundtrip.bin"), FTPConstants.TYPE_BINARY, false, 0L);
        client.download(remote("roundtrip.bin"), destination.toFile(), FTPConstants.TYPE_BINARY, false);
        assertTrue(Arrays.equals(bytes(), Files.readAllBytes(destination)));
        assertTrue(Arrays.equals(bytes(), Files.readAllBytes(serverDirectory.resolve("roundtrip.bin"))));
    }

    private RemoteFile remote(String name) throws Exception {
        return parser.createRemoteFile(client.getWorkingDirectory(), name, false);
    }

    private static byte[] bytes() {
        byte[] bytes = new byte[4096];
        for (int i = 0; i < bytes.length; i++) bytes[i] = (byte) i;
        return bytes;
    }

    private static void removeOwnedTree(Path directory) throws Exception {
        if (directory == null || !Files.exists(directory)) return;
        try (Stream<Path> paths = Files.walk(directory)) {
            for (Path path : (Iterable<Path>) paths.sorted(Comparator.reverseOrder())::iterator) {
                Files.delete(path);
            }
        }
    }
}
