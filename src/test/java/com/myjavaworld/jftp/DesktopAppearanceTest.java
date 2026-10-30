package com.myjavaworld.jftp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import com.myjavaworld.gui.MTable;

import junit.framework.TestCase;

public class DesktopAppearanceTest extends TestCase {

    private static final String[] DEFAULT_KEYS = {
            "Component.focusColor",
            "Table.selectionBackground",
            "Table.selectionForeground",
            "Table.rowHeight",
            "defaultFont"
    };

    private Locale previousLocale;
    private Locale previousComponentLocale;
    private Locale previousDefaultsLocale;
    private String previousNativeLibrarySetting;
    private LookAndFeel previousLookAndFeel;
    private final Map<String, Object> previousDefaults = new HashMap<String, Object>();
    private final Map<String, Boolean> hadDefault = new HashMap<String, Boolean>();

    protected void setUp() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                previousLocale = Locale.getDefault();
                previousComponentLocale = JComponent.getDefaultLocale();
                previousDefaultsLocale = UIManager.getDefaults().getDefaultLocale();
                previousLookAndFeel = UIManager.getLookAndFeel();
                previousNativeLibrarySetting = System.getProperty("flatlaf.useNativeLibrary");
                UIDefaults defaults = UIManager.getDefaults();
                for (String key : DEFAULT_KEYS) {
                    hadDefault.put(key, Boolean.valueOf(defaults.containsKey(key)));
                    previousDefaults.put(key, defaults.get(key));
                }
            }
        });
    }

    protected void tearDown() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                try {
                    if (previousLookAndFeel != null) {
                        UIManager.setLookAndFeel(previousLookAndFeel);
                    }
                } catch (Exception e) {
                    throw new AssertionError("Could not restore prior look and feel", e);
                } finally {
                    Locale.setDefault(previousLocale);
                    if (previousNativeLibrarySetting == null) {
                        System.clearProperty("flatlaf.useNativeLibrary");
                    } else {
                        System.setProperty("flatlaf.useNativeLibrary", previousNativeLibrarySetting);
                    }
                    JComponent.setDefaultLocale(previousComponentLocale);
                    UIDefaults defaults = UIManager.getDefaults();
                    defaults.setDefaultLocale(previousDefaultsLocale);
                    for (String key : DEFAULT_KEYS) {
                        if (hadDefault.get(key).booleanValue()) {
                            defaults.put(key, previousDefaults.get(key));
                        } else {
                            defaults.remove(key);
                        }
                    }
                }
            }
        });
    }

    public void testInstallRequiresEdt() {
        assertFalse(SwingUtilities.isEventDispatchThread());
        try {
            DesktopAppearance.install();
            fail("Appearance installation must require the EDT");
        } catch (IllegalStateException expected) {
            // Expected contract for off-EDT installation.
        }
    }

    public void testFixedAppearanceSettingsPreserveLegacyPreferences() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                JFTPPreferences saved = JFTP.prefs;
                JFTPPreferences legacy = new JFTPPreferences();
                legacy.setLookAndFeelClassName("legacy.saved.Appearance");
                legacy.setLocale(Locale.GERMAN);
                try {
                    JFTP.prefs = legacy;
                    UIPrefsPanel panel = new UIPrefsPanel();
                    panel.populateScreen(legacy);
                    assertNotNull("Fixed theme must be identified truthfully",
                            findByText(panel, "JSystems Light"));
                    assertFalse("Do not offer ignored appearance choices", containsComboBox(panel));
                    panel.saveChanges();
                    assertEquals("legacy.saved.Appearance", legacy.getLookAndFeelClassName());
                    assertEquals(Locale.GERMAN, legacy.getLocale());
                } finally {
                    JFTP.prefs = saved;
                }
            }
        });
    }

    private static boolean containsComboBox(Container root) {
        for (Component component : root.getComponents()) {
            if (component instanceof JComboBox || component instanceof Container
                    && containsComboBox((Container) component)) return true;
        }
        return false;
    }

    public void testInstallSetsBrandedAppearanceAndEnglishDefaults() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                Locale.setDefault(Locale.GERMAN);
                JComponent.setDefaultLocale(Locale.GERMAN);
                DesktopAppearance.install();
                assertEquals("Desktop startup must use the pure Java FlatLaf path",
                        "false", System.getProperty("flatlaf.useNativeLibrary"));
                assertTrue(UIManager.getLookAndFeel().getClass().getName()
                        .startsWith("com.formdev.flatlaf"));
                assertEquals(Locale.ENGLISH, Locale.getDefault());
                assertEquals(Locale.ENGLISH, JComponent.getDefaultLocale());
                Color burgundy = new Color(0x800000);
                assertEquals(burgundy, UIManager.getColor("Component.focusColor"));
                assertEquals(burgundy, UIManager.getColor("Table.selectionBackground"));
                assertEquals(Color.WHITE, UIManager.getColor("Table.selectionForeground"));
                assertTrue(((Number) UIManager.get("Table.rowHeight")).intValue() >= 26);
                assertTrue("Visible table rows must honor the branded minimum",
                        new MTable().getRowHeight() >= 26);
                Font defaultFont = UIManager.getFont("defaultFont");
                assertNotNull(defaultFont);
                assertTrue(defaultFont.getSize() >= 14);
            }
        });
    }

    public void testHeaderContainsAccessiblePaintableLogoAndTitle() throws Exception {
        final BrandHeader[] header = new BrandHeader[1];
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                header[0] = new BrandHeader();
            }
        });
        final JLabel[] logoLabel = new JLabel[1];
        final JLabel[] titleLabel = new JLabel[1];
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                logoLabel[0] = findByAccessibleName(header[0], "JSystems logo");
                titleLabel[0] = findByText(header[0], "JSystems FTP");
            }
        });
        assertNotNull("Accessible logo label is required", logoLabel[0]);
        assertNotNull("Application title is required", titleLabel[0]);
        assertNotNull("Logo label must have an icon", logoLabel[0].getIcon());

        javax.swing.Icon icon = logoLabel[0].getIcon();
        assertTrue("Logo must use the SVG renderer", icon instanceof com.formdev.flatlaf.extras.FlatSVGIcon);
        assertTrue("A missing-resource placeholder is not a loaded logo",
                ((com.formdev.flatlaf.extras.FlatSVGIcon) icon).hasFound());
        assertTrue("Logo dimensions should preserve the supplied SVG ratio",
                Math.abs(((double) icon.getIconWidth() / icon.getIconHeight())
                        - (230.0 / 72.0)) <= 0.05);
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        try {
            icon.paintIcon(logoLabel[0], graphics, 0, 0);
        } finally {
            graphics.dispose();
        }
        assertTrue("Painted SVG should contain visible nonwhite pixels",
                countOpaqueNonwhitePixels(image) > 100);
    }

    public void testHeaderPlacesLogoToRightOfTitleAtDesktopWidthWithoutOverlap()
            throws Exception {
        final BrandHeader[] header = new BrandHeader[1];
        final JLabel[] logoLabel = new JLabel[1];
        final JLabel[] titleLabel = new JLabel[1];
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                header[0] = new BrandHeader();
                DimensionSize size = preferredSize(header[0]);
                header[0].setSize(1000, size.height);
                layoutRecursively(header[0]);
                logoLabel[0] = findByAccessibleName(header[0], "JSystems logo");
                titleLabel[0] = findByText(header[0], "JSystems FTP");
            }
        });
        assertNotNull(logoLabel[0]);
        assertNotNull(titleLabel[0]);
        Rectangle logo = SwingUtilities.convertRectangle(logoLabel[0].getParent(),
                logoLabel[0].getBounds(), header[0]);
        Rectangle title = SwingUtilities.convertRectangle(titleLabel[0].getParent(),
                titleLabel[0].getBounds(), header[0]);
        assertTrue("Logo must lie fully within the header", header[0].getBounds().contains(logo));
        assertTrue("Logo should be on the right half of the header", logo.getCenterX() > 500.0);
        assertFalse("Logo must not overlap the application title", logo.intersects(title));
    }

    private static JLabel findByAccessibleName(Container root, String name) {
        for (Component component : root.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                if (label.getAccessibleContext() != null
                        && name.equals(label.getAccessibleContext().getAccessibleName())) {
                    return label;
                }
            }
            if (component instanceof Container) {
                JLabel found = findByAccessibleName((Container) component, name);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    private static JLabel findByText(Container root, String text) {
        for (Component component : root.getComponents()) {
            if (component instanceof JLabel && text.equals(((JLabel) component).getText())) {
                return (JLabel) component;
            }
            if (component instanceof Container) {
                JLabel found = findByText((Container) component, text);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    private static int countOpaqueNonwhitePixels(BufferedImage image) {
        int count = 0;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int argb = image.getRGB(x, y);
                int alpha = (argb >>> 24) & 0xff;
                int red = (argb >>> 16) & 0xff;
                int green = (argb >>> 8) & 0xff;
                int blue = argb & 0xff;
                if (alpha > 0 && (red < 250 || green < 250 || blue < 250)) {
                    count++;
                }
            }
        }
        return count;
    }

    private static void layoutRecursively(Container container) {
        container.doLayout();
        for (Component component : container.getComponents()) {
            if (component instanceof Container) {
                layoutRecursively((Container) component);
            }
        }
    }

    private static DimensionSize preferredSize(JComponent component) {
        java.awt.Dimension dimension = component.getPreferredSize();
        return new DimensionSize(dimension.width, dimension.height);
    }

    private static final class DimensionSize {
        private final int width;
        private final int height;

        private DimensionSize(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
}
