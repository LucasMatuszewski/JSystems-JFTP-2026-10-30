package com.myjavaworld.jftp;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.Locale;

import javax.swing.SwingUtilities;

import junit.framework.TestCase;

public class DesktopBootstrapTest extends TestCase {

    private Locale previousLocale;

    protected void setUp() {
        previousLocale = Locale.getDefault();
    }

    protected void tearDown() {
        Locale.setDefault(previousLocale);
    }

    public void testBackgroundCallerDispatchesCallbackToEdtExactlyOnce() throws Exception {
        assertFalse(SwingUtilities.isEventDispatchThread());
        final CountDownLatch completed = new CountDownLatch(1);
        final AtomicInteger calls = new AtomicInteger();
        final AtomicReference<Boolean> onEdt = new AtomicReference<Boolean>(Boolean.FALSE);

        DesktopBootstrap.runOnEventThread(new Runnable() {
            public void run() {
                onEdt.set(Boolean.valueOf(SwingUtilities.isEventDispatchThread()));
                calls.incrementAndGet();
                completed.countDown();
            }
        });

        assertTrue("EDT callback did not complete within five seconds",
                completed.await(5, TimeUnit.SECONDS));
        assertTrue("Callback must execute on the EDT", onEdt.get().booleanValue());
        assertEquals("Callback must run exactly once", 1, calls.get());
    }

    public void testEdtCallerRunsCallbackInlineBeforeReturning() throws Exception {
        final AtomicReference<Throwable> failure = new AtomicReference<Throwable>();
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                try {
                    final boolean[] returned = new boolean[] { false };
                    final boolean[] callbackRanInline = new boolean[] { false };
                    DesktopBootstrap.runOnEventThread(new Runnable() {
                        public void run() {
                            callbackRanInline[0] = SwingUtilities.isEventDispatchThread()
                                    && !returned[0];
                        }
                    });
                    returned[0] = true;
                    assertTrue("EDT callback must execute inline", callbackRanInline[0]);
                } catch (Throwable throwable) {
                    failure.set(throwable);
                }
            }
        });
        if (failure.get() != null) {
            throw new AssertionError("EDT inline dispatch assertion failed", failure.get());
        }
    }
}
