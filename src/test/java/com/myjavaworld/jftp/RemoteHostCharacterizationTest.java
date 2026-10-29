package com.myjavaworld.jftp;

import junit.framework.TestCase;

import com.myjavaworld.ftp.FTPConstants;

public class RemoteHostCharacterizationTest extends TestCase {

	public void testLegacyOrdinaryPortAtOrBelowZeroUsesDefault() {
		RemoteHost host = new RemoteHost();
		host.setPort(0);
		assertEquals(FTPConstants.DEFAULT_PORT, host.getPort());
		host.setPort(-1);
		assertEquals(FTPConstants.DEFAULT_PORT, host.getPort());
	}

	public void testPositiveOrdinaryPortIsRetained() {
		RemoteHost host = new RemoteHost();
		host.setPort(2121);
		assertEquals(2121, host.getPort());
	}

	public void testLegacyImplicitSslPortRetainsZero() {
		RemoteHost host = new RemoteHost();
		host.setImplicitSSLPort(0);
		assertEquals(0, host.getImplicitSSLPort());
	}

	public void testHostnameIsTrimmed() {
		RemoteHost host = new RemoteHost();
		host.setHostName("  ftp.example.test  ");
		assertEquals("ftp.example.test", host.getHostName());
	}

	public void testNullHostnameBecomesEmpty() {
		RemoteHost host = new RemoteHost();
		host.setHostName(null);
		assertEquals("", host.getHostName());
	}

	public void testUsernameWhitespaceIsPreserved() {
		RemoteHost host = new RemoteHost();
		host.setUser("  operator  ");
		assertEquals("  operator  ", host.getUser());
	}

	public void testNullUsernameBecomesEmpty() {
		RemoteHost host = new RemoteHost();
		host.setUser(null);
		assertEquals("", host.getUser());
	}

	public void testPasswordWhitespaceIsPreserved() {
		RemoteHost host = new RemoteHost();
		host.setPassword("  synthetic secret  ");
		assertEquals("  synthetic secret  ", host.getPassword());
	}

	public void testNullPasswordBecomesEmpty() {
		RemoteHost host = new RemoteHost();
		host.setPassword(null);
		assertEquals("", host.getPassword());
	}

	public void testFreshHostUsesPassivePlainFtpDefaults() {
		RemoteHost host = new RemoteHost();
		assertTrue(host.isPassive());
		assertEquals(FTPConstants.USE_NO_SSL, host.getSSLUsage());
	}

	public void testLegacyCommandParsingSkipsBlankLinesAndRetainsTokenWhitespace() {
		RemoteHost host = new RemoteHost();
		host.setCommands("  SITE one  \r\n\r\n\tSITE two\t\n   \nSITE three  ");
		String[] expected = { "  SITE one  ", "\tSITE two\t", "SITE three  " };
		assertEquals(expected.length, host.getCommands().length);
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], host.getCommands()[i]);
		}
	}

	public void testLegacyCommandFormattingAddsTrailingNewlinePerCommand() {
		RemoteHost host = new RemoteHost();
		host.setCommands("FIRST\nSECOND");
		assertEquals("FIRST\nSECOND\n", host.getCommandsAsString());
	}

	public void testFavoriteIdentityIgnoresNameCase() {
		Favorite first = new Favorite();
		first.setName("Work server");
		Favorite second = new Favorite();
		second.setName("WORK SERVER");
		assertTrue(first.equals(second));
		assertTrue(second.equals(first));
	}

	public void testFavoriteIdentityRejectsDifferentName() {
		Favorite first = new Favorite();
		first.setName("Work server");
		Favorite second = new Favorite();
		second.setName("Backup server");
		assertFalse(first.equals(second));
	}
}
