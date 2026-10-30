package com.myjavaworld.jftp;

import java.awt.Component;
import java.text.DateFormat;
import java.util.Locale;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import junit.framework.TestCase;

public class LocalePrefsEnglishTest extends TestCase {
    public void testLanguageChoiceTruthfullyShowsFixedEnglish() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            LocalePrefsPanel panel = new LocalePrefsPanel();
            JComboBox language = null;
            for (Component component : panel.getComponents()) {
                if (component instanceof JComboBox) language = (JComboBox) component;
            }
            assertNotNull(language);
            assertEquals(1, language.getItemCount());
            assertEquals(Locale.ENGLISH, language.getSelectedItem());
            assertFalse("Fixed English must not offer ignored choices", language.isEnabled());
        });
    }

    public void testSavingDateStylesPreservesLegacyLocaleField() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JFTPPreferences previous = JFTP.prefs;
            JFTPPreferences legacy = new JFTPPreferences();
            legacy.setLocale(Locale.GERMAN);
            legacy.setDateFormat(DateFormat.SHORT);
            legacy.setTimeFormat(DateFormat.MEDIUM);
            try {
                JFTP.prefs = legacy;
                LocalePrefsPanel panel = new LocalePrefsPanel();
                panel.populateScreen(legacy);
                panel.saveChanges();
                assertEquals(Locale.GERMAN, legacy.getLocale());
                assertEquals(DateFormat.SHORT, legacy.getDateFormat());
                assertEquals(DateFormat.MEDIUM, legacy.getTimeFormat());
            } finally {
                JFTP.prefs = previous;
            }
        });
    }
}
