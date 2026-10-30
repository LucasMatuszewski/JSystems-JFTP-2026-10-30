/*
 * Copyright 2012 jMethods, Inc. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.myjavaworld.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.swing.event.EventListenerList;

import com.myjavaworld.util.ProgressEvent;
import com.myjavaworld.util.ProgressListener;

/**
 * @author Sai Pullabhotla
 */
public class Unzip implements AutoCloseable {

	/**
	 * Zip file to unzip
	 */
	private File file = null;
	/**
	 * Directory to unzip the contents
	 */
	private File targetDirectory = null;
	/**
	 * ZipFile object to read the file.
	 */
	private ZipFile zipFile = null;
	/**
	 * Event listener list
	 */
	private EventListenerList listenerList = null;

	public Unzip(File file) {
		this.file = file;
		this.targetDirectory = file.getParentFile();
		listenerList = new EventListenerList();
	}

	public File getFile() {
		return file;
	}

	public void setTargetDirectory(File targetDirectory) {
		this.targetDirectory = targetDirectory;
	}

	public File getTargetDirectory() {
		return targetDirectory;
	}

	public void addZipListener(ZipListener l) {
		listenerList.add(ZipListener.class, l);
	}

	public void removeZipListener(ZipListener l) {
		listenerList.remove(ZipListener.class, l);
	}

	public void addProgressListener(ProgressListener l) {
		listenerList.add(ProgressListener.class, l);
	}

	public void removeProgressListener(ProgressListener l) {
		listenerList.remove(ProgressListener.class, l);
	}

	protected void fireBeginFileEvent(File file) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ZipListener.class) {
				((ZipListener) listeners[i + 1]).beginFile(new ZipEvent(this,
						ZipEvent.UNZIP, file.getName()));
			}
		}
	}

	protected void fireEndFileEvent(File file) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ZipListener.class) {
				((ZipListener) listeners[i + 1]).endFile(new ZipEvent(this,
						ZipEvent.UNZIP, file.getName()));
			}
		}
	}

	protected void fireProgressEvent(int progress) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ProgressListener.class) {
				((ProgressListener) listeners[i + 1])
						.progressChanged(new ProgressEvent(this, progress));
			}
		}
	}

	public void open() throws ZipException, IOException {
		if (zipFile != null) {
			throw new IllegalStateException("ZipFile is already open");
		}
		zipFile = new ZipFile(file);
	}

	@Override
	public void close() throws IOException {
		if (zipFile == null) {
			throw new IllegalStateException(
					"ZipFile is not open or is already closed");
		}
		zipFile.close();
	}

	public void unzip() throws IOException {
		if (zipFile == null) {
			throw new IllegalStateException("ZipFile is not open");
		}
		Path root;
		try {
			if (targetDirectory == null) throw new IOException("No folder selected");
			root = targetDirectory.toPath().toRealPath();
			if (!Files.readAttributes(root, BasicFileAttributes.class,
					LinkOption.NOFOLLOW_LINKS).isDirectory()) {
				throw new IOException("Not a directory");
			}
		} catch (IOException error) {
			throw new IOException("Invalid extraction folder: an existing directory is required", error);
		}
		Path input = file.toPath().toRealPath();
		List<PlannedEntry> plan = planEntries(root, input);
		for (PlannedEntry planned : plan) {
			ZipEntry entry = planned.entry;
			if (entry.isDirectory()) {
				boolean created = ensureDirectories(root, planned.target, entry.getName());
				if (created && entry.getTime() >= 0) {
					checkExistingPath(root, planned.target, entry.getName(), true);
					Files.setLastModifiedTime(planned.target, FileTime.fromMillis(entry.getTime()));
				}
			} else {
				unzipFile(root, input, planned);
			}
		}
	}

	private List<PlannedEntry> planEntries(Path root, Path input) throws IOException {
		List<PlannedEntry> plan = new ArrayList<PlannedEntry>();
		Map<Path, Boolean> kinds = new HashMap<Path, Boolean>();
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			String name = entry.getName();
			Path target = ArchiveEntryPath.resolve(root, name, entry.isDirectory());
			checkExistingPath(root, target, name, entry.isDirectory());
			checkInputArchive(input, target, name);
			if (!entry.isDirectory() && (entry.getSize() < 0 || entry.getCrc() < 0)) {
				throw ArchiveEntryPath.unsafe(name, "file size and checksum are required");
			}
			Boolean previous = kinds.get(target);
			if (previous != null && (!entry.isDirectory() || !previous.booleanValue())) {
				throw ArchiveEntryPath.unsafe(name, "duplicate file destination or file/directory conflict");
			}
			for (Path parent = target.getParent(); !parent.equals(root); parent = parent.getParent()) {
				if (Boolean.FALSE.equals(kinds.get(parent))) {
					throw ArchiveEntryPath.unsafe(name, "a planned parent is a file");
				}
				kinds.put(parent, Boolean.TRUE);
			}
			kinds.put(target, Boolean.valueOf(entry.isDirectory()));
			if (previous == null) plan.add(new PlannedEntry(entry, target));
		}
		return plan;
	}

	/** Checks a stable destination tree; this is not a concurrent-writer sandbox. */
	private void checkExistingPath(Path root, Path target, String name, boolean directory)
			throws IOException {
		Path current = root;
		if (!checkPhysicalPath(current, name, true)) {
			throw ArchiveEntryPath.unsafe(name, "the extraction folder no longer exists");
		}
		for (Path part : root.relativize(target)) {
			current = current.resolve(part);
			if (!checkPhysicalPath(current, name, !current.equals(target) || directory)) return;
		}
	}

	private boolean checkPhysicalPath(Path path, String name, boolean directory) throws IOException {
		BasicFileAttributes attributes;
		try {
			attributes = Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
		} catch (NoSuchFileException absent) {
			return false;
		} catch (IOException error) {
			throw pathFailure(name, "an existing destination cannot be inspected", error);
		}
		if (attributes.isSymbolicLink()) {
			throw ArchiveEntryPath.unsafe(name, "an existing path is a symbolic link");
		}
		Path physical;
		try {
			physical = path.toRealPath();
		} catch (IOException error) {
			// An existing dangling redirect is not a missing output directory.
			throw pathFailure(name, "an existing destination cannot be resolved", error);
		}
		if (!physical.equals(path)) {
			throw ArchiveEntryPath.unsafe(name, "an existing path redirects to another location");
		}
		if (directory ? !attributes.isDirectory() : !attributes.isRegularFile()) {
			throw ArchiveEntryPath.unsafe(name, directory
					? "a parent or directory destination is not a directory"
					: "the file destination is not an ordinary file");
		}
		return true;
	}

	private IOException pathFailure(String name, String reason, IOException cause) {
		IOException failure = ArchiveEntryPath.unsafe(name, reason);
		failure.initCause(cause);
		return failure;
	}

	private void checkInputArchive(Path input, Path target, String name) throws IOException {
		if (target.equals(input)) throw ArchiveEntryPath.unsafe(name, "destination is the input archive");
		try {
			Files.readAttributes(target, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
		} catch (NoSuchFileException absent) {
			return;
		}
		if (Files.isSameFile(input, target)) {
			throw ArchiveEntryPath.unsafe(name, "destination is an alias of the input archive");
		}
	}

	private boolean ensureDirectories(Path root, Path directory, String name) throws IOException {
		checkExistingPath(root, directory, name, true);
		Path current = root;
		boolean created = false;
		for (Path part : root.relativize(directory)) {
			current = current.resolve(part);
			created = false;
			try {
				Files.createDirectory(current);
				created = true;
			} catch (FileAlreadyExistsException existing) {
				// Revalidate both type and physical destination, including junctions.
			}
			checkExistingPath(root, current, name, true);
		}
		return created;
	}

	private void unzipFile(Path root, Path input, PlannedEntry planned) throws IOException {
		ZipEntry entry = planned.entry;
		Path target = planned.target;
		String name = entry.getName();
		ensureDirectories(root, target.getParent(), name);
		checkExistingPath(root, target, name, false);
		checkInputArchive(input, target, name);
		fireBeginFileEvent(target.toFile());
		Path pending = null;
		Throwable failure = null;
		try {
			pending = Files.createTempFile(target.getParent(), ".jftp-unzip-", ".tmp");
			CRC32 checksum = new CRC32();
			long total = 0;
			final int bufferSize = 16 * 1024;
			try (BufferedInputStream bin = new BufferedInputStream(zipFile.getInputStream(entry), bufferSize);
					BufferedOutputStream bout = new BufferedOutputStream(Files.newOutputStream(pending,
							StandardOpenOption.WRITE, LinkOption.NOFOLLOW_LINKS), bufferSize)) {
				byte[] buffer = new byte[bufferSize];
				int read;
				while ((read = bin.read(buffer)) != -1) {
					if (read > entry.getSize() - total) throw corrupt(name, "size exceeds declaration");
					bout.write(buffer, 0, read);
					checksum.update(buffer, 0, read);
					total += read;
					int progress = entry.getSize() == 0 ? 0
							: (int) Math.min(100.0, total * 100.0 / entry.getSize());
					fireProgressEvent(progress);
				}
			}
			if (total != entry.getSize() || checksum.getValue() != entry.getCrc()) {
				throw corrupt(name, "size or checksum mismatch");
			}
			checkExistingPath(root, target, name, false);
			checkInputArchive(input, target, name);
			Files.move(pending, target, StandardCopyOption.REPLACE_EXISTING);
			pending = null;
		} catch (IOException | RuntimeException | Error error) {
			failure = error;
			throw error;
		} finally {
			finishFile(pending, failure);
		}
	}

	private IOException corrupt(String name, String reason) {
		return new IOException("Corrupt ZIP entry '" + ArchiveEntryPath.printable(name) + "': " + reason);
	}

	private void finishFile(Path pending, Throwable primary) throws IOException {
		Throwable cleanup = null;
		try {
			if (pending != null) Files.deleteIfExists(pending);
		} catch (IOException | RuntimeException | Error error) {
			cleanup = error;
		}
		try {
			// Preserve the existing listener contract; events are not success receipts.
			fireEndFileEvent(file);
		} catch (RuntimeException | Error error) {
			if (cleanup == null) cleanup = error;
			else cleanup.addSuppressed(error);
		}
		if (cleanup != null) {
			if (primary != null) primary.addSuppressed(cleanup);
			else if (cleanup instanceof IOException) throw (IOException) cleanup;
			else if (cleanup instanceof RuntimeException) throw (RuntimeException) cleanup;
			else throw (Error) cleanup;
		}
	}

	private static final class PlannedEntry {
		final ZipEntry entry;
		final Path target;
		PlannedEntry(ZipEntry entry, Path target) {
			this.entry = entry;
			this.target = target;
		}
	}
}
