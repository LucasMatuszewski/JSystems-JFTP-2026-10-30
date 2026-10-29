package com.myjavaworld.jftp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import junit.framework.TestCase;

import com.myjavaworld.util.DateFilter;
import com.myjavaworld.util.RegexFilter;

public class LocalFileFilterCharacterizationTest extends TestCase {

    private Path root;

    protected void setUp() throws Exception {
        root = Files.createTempDirectory("jftp-local-filter-");
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

    public void testRegexInclusionAcceptsMatchingFilesOnly() throws Exception {
        createFile("keep.txt", "x");
        createFile("skip.bin", "x");
        LocalFileFilter filter = new LocalFileFilter(new RegexFilter(".*\\.txt", 0), null);
        assertEquals(set("keep.txt"), names(new LocalFile(root.toFile()).list(filter)));
    }

    public void testRegexExclusionRejectsMatchingFiles() throws Exception {
        createFile("keep.txt", "x");
        createFile("skip.bin", "x");
        LocalFileFilter filter = new LocalFileFilter(new RegexFilter(".*\\.txt", 0), null, true, true);
        assertEquals(set("skip.bin"), names(new LocalFile(root.toFile()).list(filter)));
    }

    public void testDirectoriesPassNameFilterInInclusionMode() throws Exception {
        createFile("keep.txt", "x");
        Files.createDirectory(root.resolve("folder"));
        LocalFileFilter filter = new LocalFileFilter(new RegexFilter(".*\\.txt", 0), null);
        assertEquals(set("keep.txt", "folder"), names(new LocalFile(root.toFile()).list(filter)));
    }

    public void testDirectoriesPassNameFilterInExclusionMode() throws Exception {
        createFile("keep.txt", "x");
        Files.createDirectory(root.resolve("folder"));
        LocalFileFilter filter = new LocalFileFilter(new RegexFilter(".*\\.txt", 0), null, true, true);
        assertEquals(set("folder"), names(new LocalFile(root.toFile()).list(filter)));
    }

    public void testDateInclusionAcceptsFileWithinInclusiveRangeAndDirectories() throws Exception {
        Path file = createFile("dated.txt", "x");
        long modified = 1100000000000L;
        Files.setLastModifiedTime(file, java.nio.file.attribute.FileTime.fromMillis(modified));
        Files.createDirectory(root.resolve("folder"));
        DateFilter dates = new DateFilter(new java.util.Date(modified), new java.util.Date(modified));
        LocalFileFilter filter = new LocalFileFilter(null, dates);
        assertEquals(set("dated.txt", "folder"), names(new LocalFile(root.toFile()).list(filter)));
    }

    public void testDateExclusionRejectsMatchingFileButPassesDirectories() throws Exception {
        Path file = createFile("dated.txt", "x");
        long modified = 1100000000000L;
        Files.setLastModifiedTime(file, java.nio.file.attribute.FileTime.fromMillis(modified));
        Files.createDirectory(root.resolve("folder"));
        DateFilter dates = new DateFilter(new java.util.Date(modified), new java.util.Date(modified));
        LocalFileFilter filter = new LocalFileFilter(null, dates, true, true);
        assertEquals(set("folder"), names(new LocalFile(root.toFile()).list(filter)));
    }

    public void testLegacyHiddenFileExclusionInvertsTheHiddenGate() throws Exception {
        Path file = createFile(".hidden.txt", "x");
        if (Files.getFileStore(file).supportsFileAttributeView("dos")) {
            Files.setAttribute(file, "dos:hidden", Boolean.TRUE);
        }
        assertTrue("A real hidden file is required; this test must not silently skip", Files.isHidden(file));
        LocalFile hidden = new LocalFile(file.toFile());
        assertFalse(new LocalFileFilter(null, null, false, false).accept(hidden));
        assertTrue(new LocalFileFilter(null, null, false, true).accept(hidden));
        assertTrue(new LocalFileFilter(null, null, true, false).accept(hidden));
    }

    private Path createFile(String name, String contents) throws IOException {
        Path file = root.resolve(name);
        Files.write(file, contents.getBytes("UTF-8"));
        return file;
    }

    private Set<String> names(LocalFile[] files) {
        Set<String> names = new HashSet<String>();
        for (LocalFile file : files) {
            names.add(file.getName());
        }
        return names;
    }

    private Set<String> set(String... names) {
        Set<String> result = new HashSet<String>();
        for (String name : names) {
            result.add(name);
        }
        return result;
    }
}
