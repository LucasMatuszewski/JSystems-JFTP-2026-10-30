# Explicit FTPS contract — 2026-10-29

Five real-server FTPS tests pass with zero failures/errors/skips. The server requires
TLS for both control and data connections. Successful control handshakes negotiated
**TLS 1.3**, including the case using the original application's SSL context. This
is backend/component evidence, not a native UI acceptance pass.

## Reproduce

Install the additional test-only dependencies into the existing isolated fixture
environment, then choose a runner:

```powershell
& .local/ftp-test-server/Scripts/python.exe -m pip install --index-url https://pypi.org/simple -r tools/ftp-tls-fixture-requirements.txt
python tools/run_ftp_integration.py --tls
python tools/run_ftp_integration.py --tls --runner legacy
```

The legacy route works before Maven build recovery and first runs the 48 local
characterization cases. Both routes then execute the same five FTPS cases. The
fixture uses pyftpdlib 2.2.0, pyOpenSSL 26.4.0 and cryptography 50.0.2; the requirements
file also pins the installed compatibility/binding dependencies.

Every run creates a fresh CA, a separate unrelated CA and a server certificate
with IP/DNS SANs, issuer identifiers, key usage and bounded validity. The server
accepts TLS 1.2 or later. Standard Python and Java trust validation remains enabled;
no trust-all manager or hostname-verification bypass is used by the standard JSSE
test cases. Private fixture keys stay under the ignored run directory and are not
published. Public test certificates in the evidence are historical artifacts;
the runner always regenerates them.

## Executed cases

| Test | Observable outcome |
|---|---|
| Trusted CA, explicit FTPS round trip | Exact 4096 binary bytes uploaded/downloaded over encrypted connections |
| Original `JFTPSSLContext`, stored synthetic server leaf | Application key/trust manager configuration completes a binary download without a warning dialog |
| Unrelated trusted CA | JSSE rejects the server chain; no authenticated transfer is established |
| Plaintext login | TLS-required server rejects it with 550 |
| Unencrypted data channel | Server rejects the transfer with 550 and does not create the destination |

The application-context case first guards both certificate-store paths inside a
synthetic Java home. It adds only the fresh synthetic server certificate to that
profile's trust store. It never reads or modifies real user trust/key stores.

## What this does and does not prove

The original FTPAPI and application SSL-context configuration can negotiate modern
TLS on JDK 25 with an explicitly stored server certificate. That does **not** prove
the original custom trust manager has correct PKIX/SAN validation. Its source-based
limitations in [ADR-000](../ADR/000-main-architecture.md#tls-boundary-and-known-hazards)
remain; the unrelated-CA rejection case uses standard JSSE validation rather than
the application's interactive warning/override path.

Implicit FTPS, native connection controls, certificate-warning choices, active FTPS,
abort/reconnect and interoperability with other servers remain unverified. The
fixture's negative cases intentionally log rejected TLS/login/data operations;
those expected refusals are not successful application UI flows.

Python initially rejected a fixture certificate missing authority identifiers.
The fixture certificate was corrected with normal issuer/key-usage extensions.
Verification was not disabled. The final runs passed and shut down their fixtures
before emitting success.

[Evidence, public certificates and log hashes](evidence/ftps-contract/result.json)
record the two successful runners. Logs normalize only the repository path prefix;
original per-run logs remain under `.local/ftp-integration`.

## References

- [Publisher FTPS fixture configuration](https://pyftpdlib.readthedocs.io/en/latest/tutorial.html#ftps-ftp-over-tls-ssl-server)
- [pyOpenSSL installation and version diagnostics](https://www.pyopenssl.org/en/stable/install.html)
- [X.509 certificate construction](https://cryptography.io/en/latest/x509/tutorial/)
- [Original application trust implementation evidence](../current-state-evidence.md)
