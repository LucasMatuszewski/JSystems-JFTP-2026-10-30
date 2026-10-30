# Real FTP transport contract — 2026-10-29

The initial seven integration tests passed against a real loopback FTP server, using the same
FTPAPI 3.0.0 client/parser operations called by `FTPSession`. Both the recovered
Maven route and the source-only legacy route were exercised. There are no mocked
FTP replies or filesystem writes. This protects the transport boundary; it is not
proof of native UI transfer behavior or of every session orchestration path.
Three callback-cancellation/reconnect characterizations were added on 2026-10-30;
the current runner requires ten passing cases with zero skips.

## Run it

On the tested Windows/PowerShell 7/Python 3.14/JDK 25 environment:

```powershell
python -m venv .local/ftp-test-server
& .local/ftp-test-server/Scripts/python.exe -m pip install --index-url https://pypi.org/simple -r tools/ftp-fixture-requirements.txt

# Normal build route after Maven build recovery:
python tools/run_ftp_integration.py

# Optional direct-source comparison route:
python tools/run_ftp_integration.py --runner legacy
```

The legacy route first runs the complete local characterization suite, then
compiles/runs the current ten transport cases through JUnit 3.8.1. The Maven route selects
`FtpTransportContractIT` explicitly through the wrapper. Its `IT` filename keeps it
out of the ordinary local test scope. Explicitly selecting it without a fixture
fails with a prerequisite error; missing infrastructure is never a passing skip.

The pinned fixture uses pyftpdlib 2.2.0 with pyasyncore/pyasynchat 1.0.5. Its
virtual account authorizes actual sockets and file operations; it is not a mock
server. It binds **127.0.0.1 only**, obtains an ephemeral port, and serves only a
fresh directory beneath `.local/ftp-integration/`. All credentials and bytes are
synthetic. Neither real user profiles nor external FTP sites are used.

Before Java tests, a real Python FTP client validates the running fixture and
captures its exchange. The runner handles the Windows virtual-environment
supervisor/child relationship, accepts readiness only from its own process or
direct child, then requests shutdown through an owned stop file. A success result
is emitted after fixture cleanup. Logs and the per-run receipt remain in the
ignored run directory; a captured successful run is retained below.

## Executed scenarios

| Case | Observable check |
|---|---|
| Directory listing | Actual server file and directory names parsed by `DefaultListParser` |
| Download replacement | Existing longer destination replaced, exact 4096 binary bytes retained |
| Upload replacement | STOR from offset zero replaces existing remote content, including a spaced filename |
| Passive round trip | Upload and download preserve every byte, including zero and high-bit values |
| Active round trip | Same byte checks using a real active data connection |
| Missing file | Server 550 becomes `FTPException`; existing local bytes remain unchanged |
| Wrong password | Real server authentication rejects it with 530 |
| Download callback cancellation | Abort on the first progress callback leaves a nonempty exact prefix of the 1 MiB source and emits aborted then finished callbacks |
| Upload callback cancellation | Same prefix/count checks against actual server file bytes |
| Reconnect after callback cancellation | A fresh connection downloads the complete source byte-for-byte |

[Captured evidence](evidence/ftp-contract/result.json) identifies versions, counts,
hashes and scope. Server/probe logs contain real protocol exchanges; synthetic
passwords are masked by the libraries. Published logs normalize only the repository
path prefix. Original per-run logs remain local.

Not covered by the plain FTP suite: FTPS, custom application certificate decisions, recursive batch
transfers, abort/reconnect races, remote editing, or native controls. Passing this
suite must not be presented as full end-to-end application acceptance. A separate
[explicit FTPS suite](ftps-contract.md) now covers five encrypted-transport cases.

## Cancellation characterization — 2026-10-30

Cancellation is requested from the transfer's first progress callback, on that
same thread, with a 1024-byte data buffer. The tests require one start, progress,
aborted and finished callback in that order, exact terminal byte totals, and an
actual partial file equal to the source prefix. This deterministic component
case does not establish cross-thread visibility, interruption of a blocked read,
or native F12 behavior. The original UI requests cancellation on another worker.

Source review and the real run expose two legacy details: an aborted transfer
also emits its ordinary finished callback, and an aborted upload uses a RECEIVE
event ID despite its start/progress/finish events using SEND. A cancelled download
may return the real server's 426 reply; other FTP errors still fail the test.
These observations define the characterized baseline rather than a recommended
new cancellation API.

A controlled mutation compiled a copy of FTPAPI's `DataConnection` with an
ineffective abort-flag assignment, then put only that class ahead of the original
JAR in a fresh test process. All three new cases failed; the original seven still
passed. Repository production sources and dependency JARs were unchanged. The
normal unmodified client was run again afterward. The
[cancellation evidence](evidence/ftp-cancellation/result.json) records counts,
runtime version, test-source hash, complete logs and fixture shutdown.

## References

- [pyftpdlib tutorial and real server configuration](https://pyftpdlib.readthedocs.io/en/latest/tutorial.html)
- [Published pyftpdlib package](https://pypi.org/project/pyftpdlib/)
- [Matching FTPAPI dependency provenance](../current-state-evidence.md#d-01--matching-external-ftpapi-implementation)

Python's standalone urllib request to PyPI initially failed local certificate-chain
validation. Metadata retrieval through the Windows trust store and pip's validated
HTTPS installation succeeded. No TLS verification bypass or Windows trust-setting
change was used.
