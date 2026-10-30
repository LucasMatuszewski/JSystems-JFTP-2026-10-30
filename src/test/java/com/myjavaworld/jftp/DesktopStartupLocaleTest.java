package com.myjavaworld.jftp;

import java.awt.event.InputEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import junit.framework.TestCase;

public class DesktopStartupLocaleTest extends TestCase {

	public void testFreshGermanJvmInstallsEnglishLocaleBeforeShortcutText()
			throws Exception {
		Path run = Files.createTempDirectory(
				Paths.get(System.getProperty("java.io.tmpdir")), "jftp-startup-locale-");
		Path log = run.resolve("probe.log");
		String javaName = System.getProperty("os.name", "").toLowerCase(Locale.ROOT)
				.contains("win") ? "java.exe" : "java";
		Path java = Paths.get(System.getProperty("java.home"), "bin", javaName);
		String classPath = System.getProperty("surefire.test.class.path",
				System.getProperty("java.class.path"));
		Process process = null;
		try {
			ProcessBuilder builder = new ProcessBuilder(
					java.toString(),
					"-Djava.awt.headless=true",
					"-Duser.language=de",
					"-Duser.country=DE",
					"-Duser.home=" + run,
					"-Djava.io.tmpdir=" + run,
					"-cp", classPath,
					DesktopStartupLocaleTest.Probe.class.getName());
			builder.redirectErrorStream(true);
			builder.redirectOutput(log.toFile());
			process = builder.start();
			boolean finished = process.waitFor(20, TimeUnit.SECONDS);
			if (!finished) {
				process.destroyForcibly();
				process.waitFor(5, TimeUnit.SECONDS);
			}
			String output = new String(Files.readAllBytes(log), StandardCharsets.UTF_8);
			assertTrue("Fresh-JVM startup probe timed out. Log:\n" + output, finished);
			assertEquals("Fresh-JVM startup probe failed. Log:\n" + output,
					0, process.exitValue());
			assertTrue("Expected English CTRL shortcut text. Log:\n" + output,
				output.contains("CTRL=Ctrl"));
			assertTrue("Expected English SHIFT shortcut text. Log:\n" + output,
				output.contains("SHIFT=Shift"));
			assertTrue("Expected English default locale. Log:\n" + output,
				output.contains("LOCALE=en"));
		} finally {
			if (process != null && process.isAlive()) {
				process.destroyForcibly();
				process.waitFor(5, TimeUnit.SECONDS);
			}
			deleteTree(run);
		}
	}

	public static class Probe {
		public static void main(String[] args) throws Exception {
			final CountDownLatch completed = new CountDownLatch(1);
			final AtomicReference<Throwable> failure = new AtomicReference<Throwable>();
			DesktopBootstrap.runOnEventThread(new Runnable() {
				public void run() {
					try {
						DesktopAppearance.install();
						System.out.println("CTRL=" + InputEvent.getModifiersExText(
								InputEvent.CTRL_DOWN_MASK));
						System.out.println("SHIFT=" + InputEvent.getModifiersExText(
								InputEvent.SHIFT_DOWN_MASK));
						System.out.println("LOCALE=" + Locale.getDefault().getLanguage());
					} catch (Throwable throwable) {
						failure.set(throwable);
					} finally {
						completed.countDown();
					}
				}
			});
			if (!completed.await(10, TimeUnit.SECONDS)) {
				System.err.println("Startup callback did not complete within 10 seconds");
				System.exit(2);
			}
			if (failure.get() != null) {
				failure.get().printStackTrace(System.err);
				System.exit(3);
			}
			System.exit(0);
		}
	}

	private static void deleteTree(Path root) throws IOException {
		if (!Files.exists(root)) {
			return;
		}
		java.util.stream.Stream<Path> paths = Files.walk(root);
		try {
			paths.sorted(Comparator.reverseOrder()).forEach(path -> {
				try {
					Files.deleteIfExists(path);
				} catch (IOException exception) {
					throw new RuntimeException(exception);
				}
			});
		} finally {
			paths.close();
		}
	}
}
