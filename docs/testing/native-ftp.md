# Native FTP workflow verification — 2026-10-29

The assembled Windows application was exercised through Computer Use on JDK 25,
using the unchanged legacy production Java and the corrected packaged launcher.
This supplements the [transport contract tests](ftp-contract.md) with actual
Swing actions, asynchronous workers, pane refreshes and observed connection state.

## Controlled setup

The real pyftpdlib 2.2.0 fixture from `tools/ftp_test_server.py` listened only on
`127.0.0.1` at an ephemeral port and served an owned synthetic directory under
ignored `.local/ftp-integration/`. It used the documented disposable fixture
account. The original `FavoritesManager` API seeded that connection into the
application's isolated synthetic profile; no operator profile or credentials
were accessed, and no authentication dialog was automated. The native favorites
manager selected this synthetic entry.

The fixture contained a directory with one child and a 35-byte file containing
CRLF, a NUL and non-ASCII bytes. The local workspace contained a 31-byte welcome
file. Binary/Image mode was selected explicitly to test byte preservation rather
than ASCII newline conversion. No external server was contacted.

## Observed steps

| Action | Verified outcome | Screenshot |
|---|---|---|
| Open Manage Favorites | Synthetic loopback entry visible | [Favorites](screenshots/2026-10-29-native-ftp-favorites.jpg) |
| Connect using that entry | Session label changes, remote root lists directory and fixture file | [Connected](screenshots/2026-10-29-native-ftp-connected.jpg) |
| Open Transfer Mode | Auto Detect, ASCII and Binary/Image choices visible; Binary/Image selected | [Transfer mode](screenshots/2026-10-29-native-ftp-transfer-mode.jpg) |
| Select remote file and open FTP menu | Download enabled for selection | [Actions](screenshots/2026-10-29-native-ftp-actions.jpg) |
| Choose Download | Local pane gains the 35-byte file; real server records TYPE I and RETR | [Downloaded](screenshots/2026-10-29-native-ftp-downloaded.jpg) |
| Select local welcome file and press F9 | Remote pane gains the file; real server records STOR | [Uploaded](screenshots/2026-10-29-native-ftp-uploaded.jpg) |
| Double-click remote directory | Path becomes `/nested`, child is listed | [Nested directory](screenshots/2026-10-29-native-ftp-nested.jpg) |
| Press F4 | Tab reads Not Connected, remote path/list clears, server receives QUIT | [Disconnected](screenshots/2026-10-29-native-ftp-disconnected.jpg) |
| Press Ctrl+F3 | New connection succeeds and root directory is listed again | [Reconnected](screenshots/2026-10-29-native-ftp-reconnected.jpg) |
| Select remote directory and press F7 | Local directory and child created by recursive download | [Recursive download](screenshots/2026-10-29-native-ftp-recursive-download.jpg) |
| Disconnect and stop owned fixture | JFTP remains open/disconnected, server closes with zero open sockets | [Final state](screenshots/2026-10-29-native-ftp-final.jpg) |

Three independent filesystem comparisons confirmed identical bytes and SHA-256
hashes for the binary download, upload and recursively downloaded child. Sizes
were 35, 31 and 26 bytes respectively. Full hashes, screenshot hashes and fixture
identity are in [native-ftp-result.json](evidence/native-ftp-result.json). The
[complete server log](evidence/native-ftp-server.log) records real commands and
replies; its password field is masked by pyftpdlib. The application launcher log
contained only the expected synthetic-profile `JAVA_TOOL_OPTIONS` notice.

## Visual comparison and limits

Compared with `assets/homepage.png` and `docs/design-guidelines.md`, this is still
the legacy gray Swing layout with small controls, no right-side JSystems logo,
and no burgundy brand treatment. Application menus/status are English; native
Windows file type and shell labels still include Polish text. These are known
baseline differences, not a brand or English-only acceptance pass.

This was a successful small-file, passive, plain-FTP manual run. It does not
establish native FTPS, active-mode UI transfers, abort or interrupted-write
behavior, overwrite/resume dialogs, recursive upload, large-file progress,
server compatibility beyond this fixture, or race-free UI threading. The separate
[FTPS component suite](ftps-contract.md) remains component evidence. There were no
production Java changes in this verification step.
