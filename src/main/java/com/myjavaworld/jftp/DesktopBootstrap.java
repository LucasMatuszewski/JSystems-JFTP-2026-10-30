package com.myjavaworld.jftp;

import java.util.Objects;
import java.util.Locale;
import javax.swing.SwingUtilities;

/** Dispatches desktop creation before any application widgets are constructed. */
public final class DesktopBootstrap {
    private DesktopBootstrap() { }

    public static void runOnEventThread(Runnable startup) {
        Objects.requireNonNull(startup, "startup");
        // Toolkit modifier labels are cached before the EDT callback runs.
        Locale.setDefault(Locale.ENGLISH);
        if (SwingUtilities.isEventDispatchThread()) {
            startup.run();
        } else {
            SwingUtilities.invokeLater(startup);
        }
    }
}
