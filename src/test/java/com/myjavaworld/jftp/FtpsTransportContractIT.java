package com.myjavaworld.jftp;

import com.myjavaworld.ftp.ConnectionException;
import com.myjavaworld.ftp.DefaultFTPClient;
import com.myjavaworld.ftp.DefaultListParser;
import com.myjavaworld.ftp.FTPConstants;
import com.myjavaworld.ftp.FTPException;
import junit.framework.TestCase;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Stream;

/** Explicit FTPS with JSSE PKIX trust; does not exercise JFTP's custom trust dialog. */
public class FtpsTransportContractIT extends TestCase {
    private DefaultFTPClient client;
    private DefaultListParser parser;
    private Path serverDirectory;
    private Path localDirectory;
    private int port;
    private String remoteDirectory;
    private Path caFile;

    protected void setUp() throws Exception {
        String fixture = System.getProperty("jftp.fixture.root");
        assertNotNull("Run tools/run_ftp_integration.py --tls", fixture);
        Path root = Paths.get(fixture).toRealPath();
        assertTrue(root.startsWith(Paths.get(System.getProperty("user.dir"), ".local", "ftp-integration")
                .toAbsolutePath().normalize()));
        caFile = root.getParent().resolve("ca.pem");
        assertTrue("A fresh test CA is required", Files.isRegularFile(caFile));
        port = Integer.parseInt(System.getProperty("jftp.fixture.port", "0"));
        assertTrue(port > 0 && port <= 65535);
        String name = "tls-" + UUID.randomUUID();
        serverDirectory = Files.createDirectory(root.resolve(name));
        localDirectory = Files.createTempDirectory(root.getParent(), "tls-client-");
        remoteDirectory = "/" + name;
        parser = new DefaultListParser();
        client = new DefaultFTPClient();
        client.setListParser(parser);
        client.setTimeout(10000);
        client.setPassive(true);
        client.setSSLUsage(FTPConstants.USE_EXPLICIT_SSL);
        client.setDataChannelUnencrypted(false);
    }

    protected void tearDown() throws Exception {
        try {
            if (client != null && client.isConnected()) client.disconnect();
        } finally {
            remove(serverDirectory);
            remove(localDirectory);
        }
    }

    public void testExplicitFtpsRoundtripUsesTrustedCaAndPreservesBinaryBytes() throws Exception {
        client.setSSLContext(trustedContext());
        connectAndLogin();
        Path source = localDirectory.resolve("source.bin");
        Path destination = localDirectory.resolve("result.bin");
        Files.write(source, bytes());
        client.upload(source.toFile(), parser.createRemoteFile(remoteDirectory, "secured.bin", false),
                FTPConstants.TYPE_BINARY, false, 0L);
        client.download(parser.createRemoteFile(remoteDirectory, "secured.bin", false), destination.toFile(),
                FTPConstants.TYPE_BINARY, false);
        assertTrue(Arrays.equals(bytes(), Files.readAllBytes(destination)));
        assertTrue(Arrays.equals(bytes(), Files.readAllBytes(serverDirectory.resolve("secured.bin"))));
    }

    public void testUntrustedFreshCertificateIsRejectedByJsse() throws Exception {
        SSLContext untrusted = SSLContext.getInstance("TLS");
        KeyStore empty = KeyStore.getInstance(KeyStore.getDefaultType());
        empty.load(null, null);
        try (InputStream input = Files.newInputStream(caFile.resolveSibling("unrelated-ca.pem"))) {
            empty.setCertificateEntry("unrelated-ca", CertificateFactory.getInstance("X.509").generateCertificate(input));
        }
        TrustManagerFactory managers = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        managers.init(empty);
        untrusted.init(null, managers.getTrustManagers(), null);
        client.setSSLContext(untrusted);
        try {
            client.connect("127.0.0.1", port);
            client.login("fixture-user", "fixture-password");
            fail("An untrusted fresh CA must not establish an authenticated FTPS session");
        } catch (ConnectionException expected) {
            assertFalse(Files.exists(serverDirectory.resolve("secured.bin")));
        }
    }

    public void testOriginalJftpSslContextTransfersWithStoredSyntheticServerCertificate() throws Exception {
        Path temporary = Paths.get(System.getProperty("java.io.tmpdir")).toAbsolutePath().normalize();
        Path home = Paths.get(System.getProperty("user.home")).toAbsolutePath().normalize();
        assertTrue("Application trust tests require an isolated profile", home.startsWith(temporary));
        assertTrue(home.getFileName().toString().equals("profile") || home.getFileName().toString().equals("test-profile"));
        assertTrue(Paths.get(JFTP.prefs.getServerCertificateStore()).toAbsolutePath().normalize().startsWith(home));
        assertTrue(Paths.get(JFTP.prefs.getClientCertificateStore()).toAbsolutePath().normalize().startsWith(home));
        java.security.cert.Certificate certificate;
        try (InputStream input = Files.newInputStream(caFile.resolveSibling("server.pem"))) {
            certificate = CertificateFactory.getInstance("X.509").generateCertificate(input);
        }
        com.myjavaworld.jftp.ssl.KeyStoreManager.addServerCertificate(
                new java.security.cert.Certificate[] { certificate });
        client.setSSLContext(com.myjavaworld.jftp.ssl.JFTPSSLContext.getSSLContext(null, "127.0.0.1"));
        Files.write(serverDirectory.resolve("pinned.bin"), bytes());
        connectAndLogin();
        Path destination = localDirectory.resolve("pinned.bin");
        client.download(parser.createRemoteFile(remoteDirectory, "pinned.bin", false), destination.toFile(),
                FTPConstants.TYPE_BINARY, false);
        assertTrue(Arrays.equals(bytes(), Files.readAllBytes(destination)));
    }

    public void testTlsRequiredServerRejectsPlaintextLogin() throws Exception {
        client.setSSLUsage(FTPConstants.USE_NO_SSL);
        client.connect("127.0.0.1", port);
        try {
            client.login("fixture-user", "fixture-password");
            fail("The fixture requires an encrypted control connection");
        } catch (FTPException expected) {
            assertEquals("550", expected.getErrorCode());
        }
    }

    public void testTlsRequiredServerRejectsUnencryptedDataChannel() throws Exception {
        client.setSSLContext(trustedContext());
        client.setDataChannelUnencrypted(true);
        connectAndLogin();
        Path source = localDirectory.resolve("source.bin");
        Files.write(source, bytes());
        try {
            client.upload(source.toFile(), parser.createRemoteFile(remoteDirectory, "rejected.bin", false),
                    FTPConstants.TYPE_BINARY, false, 0L);
            fail("The fixture requires encrypted data connections");
        } catch (FTPException expected) {
            assertEquals("550", expected.getErrorCode());
            assertFalse(Files.exists(serverDirectory.resolve("rejected.bin")));
        }
    }

    private void connectAndLogin() throws Exception {
        client.connect("127.0.0.1", port);
        client.login("fixture-user", "fixture-password");
        client.setWorkingDirectory(parser.createRemoteFile(remoteDirectory, true));
    }

    private SSLContext trustedContext() throws Exception {
        KeyStore trust = KeyStore.getInstance(KeyStore.getDefaultType());
        trust.load(null, null);
        try (InputStream input = Files.newInputStream(caFile)) {
            trust.setCertificateEntry("synthetic-ca", CertificateFactory.getInstance("X.509").generateCertificate(input));
        }
        TrustManagerFactory managers = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        managers.init(trust);
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, managers.getTrustManagers(), null);
        return context;
    }

    private static byte[] bytes() {
        byte[] value = new byte[4096];
        for (int i = 0; i < value.length; i++) value[i] = (byte) i;
        return value;
    }

    private static void remove(Path root) throws Exception {
        if (root == null || !Files.exists(root)) return;
        try (Stream<Path> paths = Files.walk(root)) {
            for (Path path : (Iterable<Path>) paths.sorted(Comparator.reverseOrder())::iterator) Files.delete(path);
        }
    }
}
