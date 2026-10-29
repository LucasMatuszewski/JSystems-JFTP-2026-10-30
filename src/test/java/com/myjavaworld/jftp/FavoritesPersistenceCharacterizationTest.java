package com.myjavaworld.jftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.SealedObject;

import junit.framework.TestCase;
import com.myjavaworld.ftp.FTPConstants;

public class FavoritesPersistenceCharacterizationTest extends TestCase {

    private Path dataHome;
    private Path favoritesFile;
    private byte[] previousFavoritesBytes;
    private boolean hadPreviousFavoritesFile;

    protected void setUp() throws Exception {
        Path temporaryRoot = new File(System.getProperty("java.io.tmpdir"))
                .toPath().toAbsolutePath().normalize();
        Path userHome = new File(System.getProperty("user.home"))
                .toPath().toAbsolutePath().normalize();
        assertTrue("user.home must be contained in java.io.tmpdir",
                userHome.startsWith(temporaryRoot));
        String profileName = userHome.getFileName().toString();
        assertTrue("user.home basename must identify an isolated test profile",
                "profile".equals(profileName) || "test-profile".equals(profileName));

        // Do not reference JFTP or FavoritesManager before the profile guard.
        dataHome = JFTP.DATA_HOME.toPath().toAbsolutePath().normalize();
        assertTrue("JFTP data home must remain within the isolated profile",
                dataHome.startsWith(userHome));
        Files.createDirectories(dataHome);
        favoritesFile = dataHome.resolve("favorites.ser");
        hadPreviousFavoritesFile = Files.isRegularFile(favoritesFile);
        if (hadPreviousFavoritesFile) {
            previousFavoritesBytes = Files.readAllBytes(favoritesFile);
        }
    }

    protected void tearDown() throws Exception {
        if (favoritesFile == null) {
            return;
        }
        if (hadPreviousFavoritesFile) {
            Files.write(favoritesFile, previousFavoritesBytes);
        } else {
            Files.deleteIfExists(favoritesFile);
        }
    }

    public void testMissingAndEmptyFavoritesFileReturnEmptyList() throws Exception {
        Files.deleteIfExists(favoritesFile);
        assertTrue(FavoritesManager.getFavorites().isEmpty());
        assertTrue(Files.exists(favoritesFile));
        assertEquals(0L, Files.size(favoritesFile));
        assertTrue(FavoritesManager.getFavorites().isEmpty());
    }

    public void testSaveAndReadRetainsSyntheticProfileAndSecurityFields() throws Exception {
        Favorite favorite = createFavorite("synthetic profile", "secret-placeholder");
        FavoritesManager.saveFavorites(Arrays.asList(favorite));

        List favorites = FavoritesManager.getFavorites();
        assertEquals(1, favorites.size());
        Favorite restored = (Favorite) favorites.get(0);
        assertEquals("synthetic profile", restored.getName());
        assertEquals("ftp.example.invalid", restored.getHostName());
        assertEquals(2121, restored.getPort());
        assertEquals("synthetic-user", restored.getUser());
        assertEquals("secret-placeholder", restored.getPassword());
        assertEquals("synthetic-account", restored.getAccount());
        assertFalse(restored.isPassive());
        assertEquals("/synthetic/local", restored.getInitialLocalDirectory());
        assertEquals("/synthetic/remote", restored.getInitialRemoteDirectory());
        assertTrue(Arrays.equals(new String[] { "NOOP", "SYST" }, restored.getCommands()));
        assertEquals(FTPConstants.USE_EXPLICIT_SSL, restored.getSSLUsage());
        assertTrue(restored.isDataChannelUnencrypted());
        assertEquals(2990, restored.getImplicitSSLPort());
    }

    public void testAddFavoriteRetainsExistingOrderAndAppendsNewEntry() throws Exception {
        Favorite first = createFavorite("first", "placeholder-one");
        Favorite second = createFavorite("second", "placeholder-two");
        FavoritesManager.saveFavorites(new ArrayList<Favorite>(Arrays.asList(first)));

        FavoritesManager.addFavorite(second);

        List favorites = FavoritesManager.getFavorites();
        assertEquals(2, favorites.size());
        assertEquals("first", ((Favorite) favorites.get(0)).getName());
        assertEquals("second", ((Favorite) favorites.get(1)).getName());
    }

    public void testSavingNullListProducesEmptyFavoritesList() throws Exception {
        FavoritesManager.saveFavorites(null);

        assertTrue(FavoritesManager.getFavorites().isEmpty());
    }

    public void testLegacySavedFixedSizeListCannotBeAppended() throws Exception {
        FavoritesManager.saveFavorites(Arrays.asList(createFavorite("fixed", "placeholder")));
        try {
            FavoritesManager.addFavorite(createFavorite("second", "placeholder"));
            fail("The legacy store preserves the serialized fixed-size list implementation");
        } catch (UnsupportedOperationException expected) {
            assertEquals(1, FavoritesManager.getFavorites().size());
        }
    }

    public void testLegacyPlainSerializedListIsReadAndRewrittenAsSealedObject() throws Exception {
        Favorite favorite = createFavorite("legacy synthetic", "legacy-placeholder");
        List legacy = new ArrayList();
        legacy.add(favorite);
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(favoritesFile.toFile()));
        try {
            output.writeObject(legacy);
        } finally {
            output.close();
        }

        List loaded = FavoritesManager.getFavorites();

        assertEquals(1, loaded.size());
        assertEquals("legacy synthetic", ((Favorite) loaded.get(0)).getName());
        ObjectInputStream rewritten = new ObjectInputStream(new FileInputStream(favoritesFile.toFile()));
        try {
            assertTrue(rewritten.readObject() instanceof SealedObject);
        } finally {
            rewritten.close();
        }
    }

    public void testCorruptFavoritesStreamPropagatesIOException() throws Exception {
        Files.write(favoritesFile, new byte[] { 1, 2, 3, 4 });

        try {
            FavoritesManager.getFavorites();
            fail("Expected corrupt object stream to propagate IOException");
        } catch (IOException expected) {
            // The object stream header is invalid; source propagates this IOException.
        }
    }

    private Favorite createFavorite(String name, String password) {
        Favorite favorite = new Favorite();
        favorite.setName(name);
        favorite.setHostName("ftp.example.invalid");
        favorite.setPort(2121);
        favorite.setUser("synthetic-user");
        favorite.setPassword(password);
        favorite.setAccount("synthetic-account");
        favorite.setPassive(false);
        favorite.setInitialLocalDirectory("/synthetic/local");
        favorite.setInitialRemoteDirectory("/synthetic/remote");
        favorite.setCommands(new String[] { "NOOP", "SYST" });
        favorite.setSSLUsage(FTPConstants.USE_EXPLICIT_SSL);
        favorite.setDataChannelUnencrypted(true);
        favorite.setImplicitSSLPort(2990);
        return favorite;
    }
}
