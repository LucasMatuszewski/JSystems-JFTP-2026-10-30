package com.myjavaworld.jftp;

import com.myjavaworld.ftp.ConnectionException;
import com.myjavaworld.ftp.DataConnectionEvent;
import com.myjavaworld.ftp.DataConnectionListener;
import com.myjavaworld.ftp.DefaultFTPClient;
import com.myjavaworld.ftp.DefaultListParser;
import com.myjavaworld.ftp.FTPConstants;
import com.myjavaworld.ftp.FTPException;
import com.myjavaworld.ftp.RemoteFile;
import junit.framework.TestCase;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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

    // Callback cancellation runs on the transfer thread; native/UI cancellation is a separate gate.
    public void testDownloadCallbackAbortLeavesExactPartialPrefix() throws Exception {
        byte[] source = largePayload();
        String name = "cancel-download-" + UUID.randomUUID() + ".bin";
        Files.write(serverDirectory.resolve(name), source);
        connect();
        Path destination = localDirectory.resolve(name);
        AbortOnFirstProgress listener = cancelDownload(name, destination);
        assertCancelledPrefix(listener, DataConnectionEvent.RECEIVE, source,
                Files.readAllBytes(destination));
    }

    public void testUploadCallbackAbortLeavesExactPartialPrefix() throws Exception {
        byte[] source = largePayload();
        String name = "cancel-upload-" + UUID.randomUUID() + ".bin";
        Path sourceFile = localDirectory.resolve(name);
        Files.write(sourceFile, source);
        connect();
        client.setBufferSize(1024);
        AbortOnFirstProgress listener = new AbortOnFirstProgress(client);
        client.addDataConnectionListener(listener);
        try {
            client.upload(sourceFile.toFile(), remote(name), FTPConstants.TYPE_BINARY, false, 0L);
        } finally {
            client.removeDataConnectionListener(listener);
        }
        assertCancelledPrefix(listener, DataConnectionEvent.SEND, source,
                Files.readAllBytes(serverDirectory.resolve(name)));
    }

    public void testReconnectAfterCancelledDownloadCanCompleteTransfer() throws Exception {
        byte[] source = largePayload();
        String name = "cancel-reconnect-" + UUID.randomUUID() + ".bin";
        Files.write(serverDirectory.resolve(name), source);
        connect();
        Path cancelled = localDirectory.resolve("cancelled-" + name);
        AbortOnFirstProgress listener = cancelDownload(name, cancelled);
        assertCancelledPrefix(listener, DataConnectionEvent.RECEIVE, source,
                Files.readAllBytes(cancelled));

        client.disconnect();
        client = new DefaultFTPClient();
        client.setListParser(parser);
        client.setTimeout(10000);
        client.setPassive(true);
        connect();
        Path complete = localDirectory.resolve("complete-" + name);
        client.download(remote(name), complete.toFile(), FTPConstants.TYPE_BINARY, false);
        assertTrue(Arrays.equals(source, Files.readAllBytes(complete)));
    }

    private AbortOnFirstProgress cancelDownload(String name, Path destination) throws Exception {
        client.setBufferSize(1024);
        AbortOnFirstProgress listener = new AbortOnFirstProgress(client);
        client.addDataConnectionListener(listener);
        try {
            client.download(remote(name), destination.toFile(), FTPConstants.TYPE_BINARY, false);
        } catch (FTPException exception) {
            if (!"426".equals(exception.getErrorCode())) throw exception;
        } finally {
            client.removeDataConnectionListener(listener);
        }
        return listener;
    }

    private static byte[] largePayload() {
        byte[] result = new byte[1024 * 1024];
        for (int i = 0; i < result.length; i++) result[i] = (byte) (i * 31 + 7);
        return result;
    }

    private static void assertCancelledPrefix(AbortOnFirstProgress listener, int direction,
            byte[] source, byte[] partial) {
        assertNull("callback abort must not fail", listener.callbackFailure);
        assertTrue(listener.abortRequested);
        assertEquals(1, listener.startedCount);
        assertEquals(1, listener.progressCount);
        assertEquals(1, listener.abortedCount);
        assertEquals(1, listener.finishedCount);
        assertEquals(0, listener.errorCount);
        assertEquals(Arrays.asList("started", "progress", "aborted", "finished"), listener.events);
        assertEquals(direction, listener.startedId);
        assertEquals(direction, listener.progressId);
        // FTPAPI 3.0.0 also emits RECEIVE for an aborted upload.
        assertEquals(DataConnectionEvent.RECEIVE, listener.abortedId);
        assertEquals(direction, listener.finishedId);
        assertTrue("cancelled result must be a nonempty partial file", partial.length > 0);
        assertTrue("cancelled result must be shorter than source", partial.length < source.length);
        assertTrue("cancelled bytes must equal source prefix",
                Arrays.equals(Arrays.copyOf(source, partial.length), partial));
        assertEquals(partial.length, listener.abortedBytes);
        assertEquals(partial.length, listener.finishedBytes);
    }

    private static final class AbortOnFirstProgress implements DataConnectionListener {
        private final DefaultFTPClient client;
        private final List<String> events = new ArrayList<String>();
        private Exception callbackFailure;
        private boolean abortRequested;
        private int startedCount, progressCount, abortedCount, finishedCount, errorCount;
        private int startedId, progressId, abortedId, finishedId;
        private long abortedBytes, finishedBytes;

        private AbortOnFirstProgress(DefaultFTPClient client) { this.client = client; }
        public void dataTransferStarted(DataConnectionEvent e) {
            startedCount++; startedId = e.getId(); events.add("started");
        }
        public void dataTransferProgress(DataConnectionEvent e) {
            progressCount++; progressId = e.getId(); events.add("progress");
            if (!abortRequested) {
                abortRequested = true;
                try { client.abort(); }
                catch (FTPException | ConnectionException exception) { callbackFailure = exception; }
            }
        }
        public void dataTransferAborted(DataConnectionEvent e) {
            abortedCount++; abortedId = e.getId(); abortedBytes = e.getBytesTransferred(); events.add("aborted");
        }
        public void dataTransferFinished(DataConnectionEvent e) {
            finishedCount++; finishedId = e.getId(); finishedBytes = e.getBytesTransferred(); events.add("finished");
        }
        public void dataTransferError(DataConnectionEvent e) { errorCount++; events.add("error"); }
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
