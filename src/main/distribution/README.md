# JSystems FTP

JSystems FTP (JFTP) is a graphical Java desktop application for browsing and
transferring files over FTP and FTPS. Use Java 25 and an active graphical desktop.

## Install and start

Extract all files from this distribution and keep the application JAR, `lib`
directory, and launcher together. On Windows, run `jftp.bat`. On Unix-like
systems, run `sh ./jftp.sh` from the extracted directory. Unix launcher behavior
is not certified.

## Connect and transfer files

1. Open the connection dialog and enter the server address, port, username, and
   password. Choose FTP or the appropriate explicit or implicit FTPS option.
2. Connect to open a session. The workspace shows local and remote files in
   paired panes; navigate directories in either pane.
3. Select files or directories and use the upload or download controls to
   transfer them between panes. Transfers can include directories recursively.
4. Save a connection as a favorite to reuse its profile later.

## Known limitations

Legacy FTPS trust handling does not provide full certificate-chain and hostname
validation. Saved favorites use an embedded encryption key, so their stored
credentials do not have strong protection. Do not treat these features as
security guarantees.

See [LICENSE.txt](LICENSE.txt) and [NOTICE.txt](NOTICE.txt) for license and
attribution information.
