package com.myjavaworld.zip;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import junit.framework.TestCase;

public class UnzipResourceLifecycleTest extends TestCase {
    public void testScopedExtractionFailureReleasesArchiveForRename() throws Exception {
        Path run = Files.createTempDirectory(Paths.get(System.getProperty("java.io.tmpdir")), "jftp-zip-close-");
        try {
            Path archive = run.resolve("input.zip");
            try (ZipOutputStream zip = new ZipOutputStream(Files.newOutputStream(archive))) {
                zip.putNextEntry(new ZipEntry("entry.bin"));
                zip.write(new byte[] {1, 2, 3});
                zip.closeEntry();
            }
            Path invalidFolder = Files.write(run.resolve("not-a-folder"), new byte[] {9});
            Unzip unzip = new Unzip(archive.toFile());
            unzip.setTargetDirectory(invalidFolder.toFile());
            unzip.open();
            boolean failed = false;
            try (Unzip opened = unzip) {
                opened.unzip();
            } catch (IOException expected) {
                failed = true;
            }
            assertTrue("Invalid extraction folder must fail", failed);
            Path moved = run.resolve("retained.zip");
            Files.move(archive, moved);
            assertTrue("Closed input must be available for inspection", Files.isRegularFile(moved));
        } finally {
            try (Stream<Path> paths = Files.walk(run)) {
                for (Path path : paths.sorted(Comparator.reverseOrder()).collect(Collectors.toList())) {
                    Files.deleteIfExists(path);
                }
            }
        }
    }
}
