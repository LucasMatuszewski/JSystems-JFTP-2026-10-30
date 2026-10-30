# Synthetic FTP fixture lifecycle — 2026-10-30

The local test server must stop successfully within five seconds after a STOP
file is created, including when no FTP client has connected. This is a
repository-tool requirement; it does not change JFTP's application behavior.

Native-QA cleanup exposed an idle fixture that remained alive despite its STOP
file. The new test starts the real pinned server, verifies its loopback/root and
process identity, creates STOP without connecting, and waits for actual exit.
It first failed against the unchanged fixture after five seconds. The test's
cleanup handles Windows venv supervisor/child processes with fresh OS identity
checks and never terminates an unrelated process.

Inspection of the installed pyftpdlib 2.2.0 source explains the failure:
`FTPServer.serve_forever()` passes its default `None` timeout to `IOLoop.loop()`;
the loop polls the socket before polling scheduled callbacks. On an idle first
iteration, the socket wait can therefore prevent the STOP timer from running.
The fixture now supplies a 0.1-second socket-poll timeout. The existing timer
performs shutdown; no extra network request is needed to wake the server.

The lifecycle test then passed in 1.157 seconds including process startup, with
one case and zero failures/errors/skips. Both real transport suites were rerun
on Temurin 25.0.5+8: ten FTP cases and five explicit FTPS cases passed, with zero
failures/errors/skips. Both fixture supervisors exited with code zero. The
[evidence receipt](evidence/fixture-lifecycle/result.json) links the red/green
output, real server logs, transport results and source fingerprints.

With the [pinned fixture environment](ftp-contract.md) installed, reproduce:

```powershell
python -m unittest tests.test_ftp_fixture_lifecycle -v
python tools/run_ftp_integration.py
python tools/run_ftp_integration.py --tls
```

These are observable CLI/socket checks. They do not replace native desktop QA,
screenshots or the outstanding application modernization work.
