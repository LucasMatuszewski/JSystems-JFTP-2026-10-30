package com.myjavaworld.zip;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import junit.framework.TestCase;

/** Real controlled files only; dangerous device/UNC names are tested lexically. */
public class UnzipContainmentSecurityTest extends TestCase {
    private Path fixture;
    private Path output;
    private Path archive;
    private static final byte[] ORIGINAL = {7, 6, 5, 4, 3};
    private static final byte[] ENTRY = {0, 1, (byte) 0xff};

    protected void setUp() throws Exception {
        fixture = Files.createTempDirectory(Paths.get(System.getProperty("java.io.tmpdir")), "jftp-containment-");
        output = Files.createDirectory(fixture.resolve("output"));
        archive = fixture.resolve("input.zip");
        Files.write(output.resolve("keep.bin"), ORIGINAL);
    }

    protected void tearDown() throws Exception {
        try (Stream<Path> paths = Files.walk(fixture)) {
            for (Path path : paths.sorted(Comparator.reverseOrder()).collect(Collectors.toList())) {
                Files.deleteIfExists(path);
            }
        }
    }

    public void testParentTraversalRejectsWholeArchiveBeforeOutput() throws Exception {
        Path outside = fixture.resolve("outside.bin");
        Files.write(outside, ORIGINAL);
        createArchive("first.bin", "../outside.bin");
        assertRejected();
        assertBytes(ORIGINAL, Files.readAllBytes(outside));
        assertOnlyKeepFile();
    }

    public void testUnsafeDirectoryEntryDoesNotCreateDirectories() throws Exception {
        createArchive("first.bin", "../escape/");
        assertRejected();
        assertFalse(Files.exists(fixture.resolve("escape")));
        assertOnlyKeepFile();
    }

    public void testDuplicateFileDestinationsAreRejectedBeforeOutput() throws Exception {
        createArchive("first.bin", "dupa.bin", "dupb.bin");
        byte[] bytes = Files.readAllBytes(archive);
        byte[] from = "dupb.bin".getBytes(StandardCharsets.UTF_8);
        byte[] to = "dupa.bin".getBytes(StandardCharsets.UTF_8);
        int replacements = 0;
        for (int i = 0; i <= bytes.length - from.length; i++) {
            if (Arrays.equals(from, Arrays.copyOfRange(bytes, i, i + from.length))) {
                System.arraycopy(to, 0, bytes, i, to.length);
                replacements++;
            }
        }
        assertEquals("Both ZIP header names must be replaced", 2, replacements);
        Files.write(archive, bytes);
        assertRejected();
        assertOnlyKeepFile();
    }

    public void testFileDirectoryConflictIsRejectedBeforeOutput() throws Exception {
        createArchive("first.bin", "node", "node/child.bin");
        assertRejected();
        assertOnlyKeepFile();
    }

    public void testArchiveCannotOverwriteItsOwnInput() throws Exception {
        archive = output.resolve("input.zip");
        createArchive("first.bin", "input.zip");
        byte[] before = Files.readAllBytes(archive);
        assertRejected();
        assertBytes(before, Files.readAllBytes(archive));
        assertFalse(Files.exists(output.resolve("first.bin")));
    }

    public void testMissingExtractionFolderIsRejectedWithoutCreation() throws Exception {
        createArchive("entry.bin");
        Path absent = fixture.resolve("missing");
        try {
            extract(absent);
            fail("Missing extraction directory must be rejected");
        } catch (IOException expected) {
            assertTrue(expected.getMessage(), expected.getMessage().contains("extraction folder"));
        }
        assertFalse(Files.exists(absent));
    }

    public void testHardLinkAliasOfInputArchiveCannotBeOverwritten() throws Exception {
        createArchive("first.bin", "alias.zip");
        Path alias = output.resolve("alias.zip");
        Files.createLink(alias, archive);
        byte[] before = Files.readAllBytes(archive);
        assertRejected();
        assertBytes(before, Files.readAllBytes(archive));
        assertBytes(before, Files.readAllBytes(alias));
        assertFalse(Files.exists(output.resolve("first.bin")));
    }

    public void testRedirectedParentsInsideAndOutsideRootAreRejected() throws Exception {
        for (boolean withinRoot : new boolean[] {false, true}) {
            Path target = Files.createDirectory((withinRoot ? output : fixture).resolve("physical-" + withinRoot));
            Files.write(target.resolve("entry.bin"), ORIGINAL);
            Path link = output.resolve("redirect");
            createDirectoryRedirect(link, target);
            try {
                createArchive("first.bin", "redirect/entry.bin");
                assertRejected();
                assertBytes(ORIGINAL, Files.readAllBytes(target.resolve("entry.bin")));
                assertFalse(Files.exists(output.resolve("first.bin")));
            } finally {
                // Remove the redirect itself before walking our owned tree.
                Files.deleteIfExists(link);
            }
        }
    }

    public void testOperatorSelectedRedirectRootUsesItsPhysicalDirectory() throws Exception {
        Path physical = Files.createDirectory(fixture.resolve("selected-physical"));
        Path selected = fixture.resolve("selected");
        createDirectoryRedirect(selected, physical);
        try {
            createArchive("nested/data.bin");
            extract(selected);
            assertBytes(ENTRY, Files.readAllBytes(physical.resolve("nested/data.bin")));
        } finally {
            Files.deleteIfExists(selected);
        }
    }

    public void testDanglingDirectoryRedirectIsRejectedBeforeOutput() throws Exception {
        Path vanished = Files.createDirectory(fixture.resolve("vanished"));
        Path redirect = output.resolve("dangling");
        createDirectoryRedirect(redirect, vanished);
        Files.delete(vanished);
        try {
            createArchive("first.bin", "dangling/entry.bin");
            assertRejected();
            assertFalse("Validation must happen before any output", Files.exists(output.resolve("first.bin")));
        } finally {
            Files.deleteIfExists(redirect);
        }
    }

    private void createArchive(String... names) throws IOException {
        try (ZipOutputStream zip = new ZipOutputStream(Files.newOutputStream(archive))) {
            for (String name : names) {
                zip.putNextEntry(new ZipEntry(name));
                if (!name.endsWith("/")) zip.write(ENTRY);
                zip.closeEntry();
            }
        }
    }

    private void assertRejected() throws Exception {
        try {
            extract(output);
            fail("Unsafe archive must be rejected before output");
        } catch (IOException expected) {
            assertNotNull("Rejection must explain the entry", expected.getMessage());
            assertTrue("Rejection must explain the unsafe entry: " + expected,
                    expected.getMessage().startsWith("Unsafe ZIP entry"));
        }
        assertTrue("Rejected archive must remain", Files.isRegularFile(archive));
    }

    private void extract(Path destination) throws IOException {
        Unzip unzip = new Unzip(archive.toFile());
        unzip.setTargetDirectory(destination.toFile());
        unzip.open();
        try {
            unzip.unzip();
        } finally {
            unzip.close();
        }
    }

    private void assertOnlyKeepFile() throws IOException {
        try (Stream<Path> paths = Files.list(output)) {
            List<String> names = paths.map(p -> p.getFileName().toString()).sorted().collect(Collectors.toList());
            assertEquals(Arrays.asList("keep.bin"), names);
        }
        assertBytes(ORIGINAL, Files.readAllBytes(output.resolve("keep.bin")));
    }

    private static void createDirectoryRedirect(Path link, Path target) throws Exception {
        if (!System.getProperty("os.name").startsWith("Windows")) {
            Files.createSymbolicLink(link, target);
            return;
        }
        String command = "$ErrorActionPreference='Stop'; New-Item -ItemType Junction -Path $env:JFTP_LINK_PATH -Target $env:JFTP_LINK_TARGET | Out-Null";
        ProcessBuilder builder = new ProcessBuilder("powershell.exe", "-NoProfile", "-NonInteractive", "-Command", command);
        builder.environment().put("JFTP_LINK_PATH", link.toAbsolutePath().toString());
        builder.environment().put("JFTP_LINK_TARGET", target.toAbsolutePath().toString());
        Path log = link.getParent().resolve("junction-setup.log");
        builder.redirectErrorStream(true).redirectOutput(log.toFile());
        Process process = builder.start();
        if (!process.waitFor(15, java.util.concurrent.TimeUnit.SECONDS)) {
            process.destroyForcibly();
            throw new IOException("Junction fixture setup timed out: " + log);
        }
        if (process.exitValue() != 0) throw new IOException("Junction fixture unavailable: " + new String(Files.readAllBytes(log), StandardCharsets.UTF_8));
        Files.delete(log);
    }

    private static void assertBytes(byte[] expected, byte[] actual) {
        assertTrue("File bytes differ", Arrays.equals(expected, actual));
    }
}
