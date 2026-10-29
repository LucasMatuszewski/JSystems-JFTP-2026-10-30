# Current-state evidence register

**Examined:** 2026-10-29. **Application baseline:** `14e62ceba4e371c2a0b955604b10f065f46f4f7d`.

This register supports the [AS-IS PRD](PRD.md) and [architecture records](ADR/000-main-architecture.md).
At initial capture, application files and `pom.xml` matched that baseline. All CS entries are
**source-derived**, not native UI or server observations. Related lettered entries
belong to the same CS number. Read the complete cited ranges, callers and resources
before editing; a matching quotation/hash checks provenance, not semantic correctness.

## Repository sources

The [machine-readable register](current-state-evidence.json) records full-file raw
and LF-normalized SHA-256 hashes, line ranges and exact source excerpts. Verify it
against the frozen baseline with
`python tools/check_source_evidence.py docs/current-state-evidence.json --revision 14e62ce`.
Omit `--revision` to check the current working tree instead; intentional subsequent
changes should fail that check rather than silently rewrite this historical record.
Only recorded CRLF/LF conversion is tolerated.

| Evidence | Observation area | Original source |
|---|---|---|
| CS-01 | Desktop startup | [JFTPApplication.java](../src/main/java/com/myjavaworld/jftp/JFTPApplication.java#L32), lines 32–66 |
| CS-02 | Frame, registries and sessions | [JFTP.java](../src/main/java/com/myjavaworld/jftp/JFTP.java#L66), lines 66–162 |
| CS-03a | Session initial local directory | [FTPSession.java](../src/main/java/com/myjavaworld/jftp/FTPSession.java#L110), lines 110–151 |
| CS-03b | Session log and pane layout | [FTPSession.java](../src/main/java/com/myjavaworld/jftp/FTPSession.java#L289), lines 289–312 |
| CS-04 | Session close and application exit | [JFTP.java](../src/main/java/com/myjavaworld/jftp/JFTP.java#L188), lines 188–216 |
| CS-05 | Connection defaults, security fields and validation | [ConnectionDlg.java](../src/main/java/com/myjavaworld/jftp/ConnectionDlg.java#L565), lines 565–713 |
| CS-06a | Approved versus cancelled dialog result | [ConnectionDlg.java](../src/main/java/com/myjavaworld/jftp/ConnectionDlg.java#L102), lines 102–127 |
| CS-06b | Connect action dispatch | [ConnectAction.java](../src/main/java/com/myjavaworld/jftp/actions/ConnectAction.java#L50), lines 50–76 |
| CS-07 | Connection worker and completion | [FTPSession.java](../src/main/java/com/myjavaworld/jftp/FTPSession.java#L593), lines 593–699 |
| CS-08a | Retained reconnect host | [FTPSession.java](../src/main/java/com/myjavaworld/jftp/FTPSession.java#L701), lines 701–705 |
| CS-08b | Disconnect worker and UI cleanup | [FTPSession.java](../src/main/java/com/myjavaworld/jftp/FTPSession.java#L1495), lines 1495–1538 |
| CS-09 | Download selection, recursion and file request | [FTPSession.java](../src/main/java/com/myjavaworld/jftp/FTPSession.java#L707), lines 707–833 |
| CS-10 | Upload selection, recursion and file request | [FTPSession.java](../src/main/java/com/myjavaworld/jftp/FTPSession.java#L835), lines 835–971 |
| CS-11 | Transfer type and progress | [FTPSession.java](../src/main/java/com/myjavaworld/jftp/FTPSession.java#L208), lines 208–247 |
| CS-12 | Download and unzip action | [DownloadAndUnzipAction.java](../src/main/java/com/myjavaworld/jftp/actions/DownloadAndUnzipAction.java#L58), lines 58–115 |
| CS-13a | Zip and upload action | [ZipAndUploadAction.java](../src/main/java/com/myjavaworld/jftp/actions/ZipAndUploadAction.java#L59), lines 59–124 |
| CS-13b | Download with alternate name | [DownloadAsAction.java](../src/main/java/com/myjavaworld/jftp/actions/DownloadAsAction.java#L50), lines 50–89 |
| CS-13c | Upload with alternate name | [UploadAsAction.java](../src/main/java/com/myjavaworld/jftp/actions/UploadAsAction.java#L50), lines 50–89 |
| CS-14a | Local filter acceptance including hidden exclusion | [LocalFileFilter.java](../src/main/java/com/myjavaworld/jftp/LocalFileFilter.java#L115), lines 115–142 |
| CS-14b | Filters trigger pane refresh | [FTPSession.java](../src/main/java/com/myjavaworld/jftp/FTPSession.java#L258), lines 258–275 |
| CS-15 | Remote file operations, permissions and deletion | [FTPSession.java](../src/main/java/com/myjavaworld/jftp/FTPSession.java#L973), lines 973–1278 |
| CS-16 | Local file operations and deletion | [FTPSession.java](../src/main/java/com/myjavaworld/jftp/FTPSession.java#L1280), lines 1280–1492 |
| CS-17a | Remote edit dispatch | [EditRemoteFileAction.java](../src/main/java/com/myjavaworld/jftp/actions/EditRemoteFileAction.java#L54), lines 54–90 |
| CS-17b | Remote open dispatch | [OpenRemoteFileAction.java](../src/main/java/com/myjavaworld/jftp/actions/OpenRemoteFileAction.java#L54), lines 54–96 |
| CS-18a | Changed-file confirmation and upload | [FTPSession.java](../src/main/java/com/myjavaworld/jftp/FTPSession.java#L153), lines 153–197 |
| CS-18b | Temporary downloads and monitoring | [FTPSession.java](../src/main/java/com/myjavaworld/jftp/FTPSession.java#L1558), lines 1558–1601 |
| CS-19a | Favorites storage, compatibility and embedded cipher key | [FavoritesManager.java](../src/main/java/com/myjavaworld/jftp/FavoritesManager.java#L35), lines 35–187 |
| CS-19b | Connect selected favorite | [FavoritesDlg.java](../src/main/java/com/myjavaworld/jftp/FavoritesDlg.java#L232), lines 232–250 |
| CS-19c | Create favorite from connected profile | [JFTP.java](../src/main/java/com/myjavaworld/jftp/JFTP.java#L411), lines 411–448 |
| CS-20 | Preferences fields and constructor defaults | [JFTPPreferences.java](../src/main/java/com/myjavaworld/jftp/JFTPPreferences.java#L31), lines 31–151 |
| CS-21 | Preferences panels and save behavior | [PreferencesDlg.java](../src/main/java/com/myjavaworld/jftp/PreferencesDlg.java#L55), lines 55–330 |
| CS-22 | Update information request and retries | [AutoUpdater.java](../src/main/java/com/myjavaworld/jftp/AutoUpdater.java#L31), lines 31–93 |
| CS-23 | Server certificate trust and override | [JFTPTrustManager.java](../src/main/java/com/myjavaworld/jftp/ssl/JFTPTrustManager.java#L57), lines 57–162 |
| CS-24 | Declared build, dependencies and resources | [pom.xml](../pom.xml#L1), lines 1–197 |
| CS-25a | Menu order | [JFTP.java](../src/main/java/com/myjavaworld/jftp/JFTP.java#L458), lines 458–473 |
| CS-25b | Local pane structure | [LocalPane.java](../src/main/java/com/myjavaworld/jftp/LocalPane.java#L330), lines 330–410 |
| CS-25c | Remote pane structure | [RemotePane.java](../src/main/java/com/myjavaworld/jftp/RemotePane.java#L319), lines 319–379 |
| CS-26 | Host model and port normalization | [RemoteHost.java](../src/main/java/com/myjavaworld/jftp/RemoteHost.java#L33), lines 33–224 |
| CS-27 | Custom worker thread and EDT completion | [SwingWorker.java](../src/main/java/com/myjavaworld/gui/SwingWorker.java#L30), lines 30–156 |
| CS-28 | Preference object streams and finally return | [JFTP.java](../src/main/java/com/myjavaworld/jftp/JFTP.java#L512), lines 512–567 |
| CS-29 | JVM-wide SOCKS and authenticator settings | [JFTPUtil.java](../src/main/java/com/myjavaworld/jftp/JFTPUtil.java#L60), lines 60–96 |
| CS-30a | SSL context creation | [JFTPSSLContext.java](../src/main/java/com/myjavaworld/jftp/ssl/JFTPSSLContext.java#L35), lines 35–45 |
| CS-30b | Certificate stores | [KeyStoreManager.java](../src/main/java/com/myjavaworld/jftp/ssl/KeyStoreManager.java#L34), lines 34–100 |
| CS-31 | Archive output paths | [Unzip.java](../src/main/java/com/myjavaworld/zip/Unzip.java#L134), lines 134–187 |
| CS-32 | Displayed product version | [JFTPConstants.java](../src/main/java/com/myjavaworld/jftp/JFTPConstants.java#L24), lines 24–34 |
| CS-33a | Assembly distribution layout | [binary.xml](../src/main/assembly/binary.xml#L1), lines 1–44 |
| CS-33b | Windows launcher path | [jftp.bat](../src/main/scripts/jftp.bat#L1), lines 1–1 |
| CS-33c | Shell launcher path | [jftp.sh](../src/main/scripts/jftp.sh#L1), lines 1–1 |

| CS-34 | Favorite inheritance and name identity | [Favorite.java](../src/main/java/com/myjavaworld/jftp/Favorite.java#L25), lines 25–55 |
| CS-35 | Temporary transfer mapping | [TransferObject.java](../src/main/java/com/myjavaworld/jftp/TransferObject.java#L23), lines 23–55 |
| CS-36 | Local file model | [LocalFile.java](../src/main/java/com/myjavaworld/jftp/LocalFile.java#L35), lines 35–283 |

## D-01 — Matching external FTPAPI implementation

On 2026-10-29 the publisher's [download page](https://www.jmethods.com/ftpapi/download.html)
linked version 3.0.0. The equivalent HTTPS Maven location served these artifacts
with HTTP 200. They were cached outside the repository; no dependency binary is
vendored in this documentation commit.

| Artifact | Bytes | SHA-256 |
|---|---:|---|
| [POM](https://www.jmethods.com/mvn-repo/com/myjavaworld/ftpapi/3.0.0/ftpapi-3.0.0.pom) | 4153 | `8f0fc4453198537be6eb2946a707a410f25edeb7dfbc052e3de622fdc6d28d77` |
| [Binary](https://www.jmethods.com/mvn-repo/com/myjavaworld/ftpapi/3.0.0/ftpapi-3.0.0.jar) | 58936 | `496782add046e211b5f7a686c483522cca8879048f12286249aa50c8f86b505c` |
| [Sources](https://www.jmethods.com/mvn-repo/com/myjavaworld/ftpapi/3.0.0/ftpapi-3.0.0-sources.jar) | 66091 | `41b34998036524f55d8de1843d145d68ded85e9f79303a154ec189c84084a863` |

In that source archive, `FTPClient.java` lines 702–740 define append/skip arguments;
`DefaultFTPClient.java` lines 644–740 route transfers; `DataConnection.java` lines
301–343 open download output using the append flag, and lines 357–372 apply upload
skip only when positive. Thus JFTP's ordinary non-append download replaces an
existing destination when opening succeeds; upload uses STOR and skips no source
bytes. Server refusal/replacement results remain runtime-dependent. The inspected
`DataConnection.java` archive entry hashes to
`154f3856e8a5db4a16c829740385ffed035caa957664c49e1770930192fa74c4`.

## D-02 — External remote filter

In the same source archive, `RemoteFileFilter.java` lines 106–122 admit directories,
apply optional regular-expression/date conditions to files and invert matching
for exclusion mode. `DefaultFTPClient.java` lines 578–595 apply the supplied filter
to listing results. `RemoteFileFilter.java` hashes to
`4b3e2f15e0a20a7b1e59a8c9cd53686f284b678ad6e4bc6eec2df352ae6c1040`.
The class belongs to the external dependency, not JFTP's checked-in Java source.

## Local environment observations

- Windows 11, PowerShell, Temurin OpenJDK `25.0.4.1+1-LTS`.
- Maven was not found on PATH during inventory. No complete application build/test/start command has passed yet.
- A standalone `CompilerProbe` invoked with `javac -source 1.5 -target 1.5` returned exit code 2: source and target 5 are unsupported. This observation does not claim compilation of the full application.
- The original `src/test` tree contains no Java test files. Repository tooling tests are separate.
- No native JFTP screenshots, successful FTP transfers or negotiated TLS details exist in this baseline evidence set.

These observations are dated. Future runtime evidence should identify a new
revision and record commands, discovery counts, skips, results and screenshots
separately; it must not rewrite a historical failure into an unqualified success.
