package com.myjavaworld.jftp;

import junit.framework.TestCase;
import java.nio.file.Files;
import java.nio.file.Path;
import com.myjavaworld.ftp.DefaultListParser;
import com.myjavaworld.ftp.RemoteFile;

public class ModelMappingCharacterizationTest extends TestCase {

	public void testLegacyDownloadTransferRetainsDirectionAndNullMappings() {
		TransferObject transfer = new TransferObject(TransferObject.DOWNLOAD,
				null, null);
		assertEquals(TransferObject.DOWNLOAD, transfer.getDirection());
		assertNull(transfer.getLocalFile());
		assertNull(transfer.getRemoteFile());
	}

	public void testUploadRetainsTheActualLocalAndRemoteEditMapping() throws Exception {
		Path temporary = Files.createTempFile("jftp-mapping-", ".txt");
		try {
		LocalFile local = new LocalFile(temporary.toFile());
		RemoteFile remote = new DefaultListParser().createRemoteFile("/incoming/edit.txt", false);
		TransferObject transfer = new TransferObject(TransferObject.UPLOAD, local, remote);
		assertEquals(TransferObject.UPLOAD, transfer.getDirection());
		assertSame(local, transfer.getLocalFile());
		assertSame(remote, transfer.getRemoteFile());
		} finally {
			Files.deleteIfExists(temporary);
		}
	}
}
