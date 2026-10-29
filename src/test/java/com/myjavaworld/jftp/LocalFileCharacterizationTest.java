package com.myjavaworld.jftp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import junit.framework.TestCase;

public class LocalFileCharacterizationTest extends TestCase {

    private Path root;

    protected void setUp() throws Exception {
        root = Files.createTempDirectory("jftp-local-file-");
    }

    protected void tearDown() throws Exception {
        if (root != null && Files.exists(root)) {
            Stream<Path> paths = Files.walk(root);
            try {
                paths.sorted(Comparator.reverseOrder()).forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            } finally {
                paths.close();
            }
        }
    }

    public void testExtensionIsUppercaseAndLastSuffixOnly() throws Exception {
        assertEquals("TXT", new LocalFile(createFile("archive.part.TxT", "x").toFile()).getExtension());
    }

    public void testExtensionIsEmptyWhenNameHasNoDot() throws Exception {
        assertEquals("", new LocalFile(createFile("README", "x").toFile()).getExtension());
    }

    public void testLegacyLeadingDotAndTrailingDotExtensionRules() throws Exception {
        assertEquals("HIDDENNAME", new LocalFile(createFile(".hiddenname", "x").toFile()).getExtension());
        assertEquals("", new LocalFile(createFile("trailing.", "x").toFile()).getExtension());
    }

    public void testDirectoryExtensionIsNull() throws Exception {
        Path directory = Files.createDirectory(root.resolve("folder.ext"));
        LocalFile localFile = new LocalFile(directory.toFile());
        assertTrue(localFile.isDirectory());
        assertNull(localFile.getExtension());
    }

    public void testMetadataIsSnapshotAndNewObjectObservesFileChanges() throws Exception {
        Path file = createFile("snapshot.bin", "a");
        Files.setLastModifiedTime(file, java.nio.file.attribute.FileTime.fromMillis(1000000000000L));
        LocalFile snapshot = new LocalFile(file.toFile());
        long capturedSize = snapshot.getSize();
        long capturedModified = snapshot.getLastModified();

        Files.write(file, "changed-content".getBytes("UTF-8"));
        Files.setLastModifiedTime(file, java.nio.file.attribute.FileTime.fromMillis(1200000000000L));

        assertEquals(capturedSize, snapshot.getSize());
        assertEquals(capturedModified, snapshot.getLastModified());
        LocalFile refreshed = new LocalFile(file.toFile());
        assertEquals(Files.size(file), refreshed.getSize());
        assertEquals(Files.getLastModifiedTime(file).toMillis(), refreshed.getLastModified());
        assertTrue(refreshed.getSize() != snapshot.getSize());
        assertTrue(refreshed.getLastModified() != snapshot.getLastModified());
    }

    public void testEqualsForSamePathAndDifferentRealPaths() throws Exception {
        Path first = createFile("same.txt", "a");
        Path second = createFile("other.txt", "a");
        assertEquals(new LocalFile(first.toFile()), new LocalFile(first.toFile()));
        assertFalse(new LocalFile(first.toFile()).equals(new LocalFile(second.toFile())));
    }

    public void testListReturnsActualChildSetWithoutOrderingAssumption() throws Exception {
        createFile("one.txt", "1");
        createFile("two.txt", "2");
        Files.createDirectory(root.resolve("folder"));

        LocalFile[] children = new LocalFile(root.toFile()).list();
        Set<String> names = new HashSet<String>();
        for (LocalFile child : children) {
            names.add(child.getName());
        }
        Set<String> expected = new HashSet<String>();
        expected.add("one.txt");
        expected.add("two.txt");
        expected.add("folder");
        assertEquals(expected, names);
    }

    private Path createFile(String name, String contents) throws IOException {
        Path file = root.resolve(name);
        Files.write(file, contents.getBytes("UTF-8"));
        return file;
    }
}
