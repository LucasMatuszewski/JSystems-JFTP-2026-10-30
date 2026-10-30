package com.myjavaworld.jftp;

import java.awt.Font;
import java.util.Locale;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatSystemProperties;

/** The fixed internal desktop appearance; stored legacy profiles remain readable. */
public final class DesktopAppearance {
    private DesktopAppearance() { }

    public static void install() {
        if (!SwingUtilities.isEventDispatchThread()) {
            throw new IllegalStateException("Install desktop appearance on the event dispatch thread");
        }
        Locale.setDefault(Locale.ENGLISH);
        JComponent.setDefaultLocale(Locale.ENGLISH);
        System.setProperty(FlatSystemProperties.USE_NATIVE_LIBRARY, "false");
        FlatLaf.registerCustomDefaultsSource("com.myjavaworld.jftp.branding");
        if (!FlatLightLaf.setup()) {
            throw new IllegalStateException("Could not initialize the JSystems desktop appearance");
        }
        UIManager.getDefaults().setDefaultLocale(Locale.ENGLISH);
        UIManager.put("defaultFont", new Font("Arial", Font.PLAIN, 14));
    }
}
