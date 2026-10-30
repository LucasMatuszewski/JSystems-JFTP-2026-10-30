# Java 25 readiness — 2026-10-30

**Status:** CLI compatibility confirmed on the current working tree. This is a
probe of the planned platform target, not a claim that the default compiler
setting or native application acceptance has been modernized already.

The verified Adoptium archive installed Temurin 25.0.5+8 into the private build
cache. The normal system Java remains 25.0.4.1. Maven Wrapper reported Maven
3.10.0 and Java 25.0.5 when selected through process-local `JAVA_HOME`.

## Executed checks

| Check | Observed result |
|---|---|
| Official JDK archive checksum | Matches the SHA-256 recorded in ADR-004 |
| Normal release-8 local tests on latest runtime | 62 cases, zero failures/errors/skips |
| Clean compile with release-25 override | Passed; 184 production sources, with the existing unused Apple adapter excluded |
| Actual class version | Application entry, SSL context and both integration classes are major version 69 |
| Release-25 local tests | 62 cases, zero failures/errors/skips |
| Release-25 real FTP | 10 cases, zero failures/errors/skips; fixture stopped |
| Release-25 explicit FTPS | 5 cases, zero failures/errors/skips; fixture stopped |
| Native desktop on final candidate | Pending: the RDP session disconnected, and Java reports no desktop access |

The 62 local cases include 48 legacy/model/archive/persistence cases and 14 new
desktop/English-display cases in the working tree. The new desktop implementation
and its tests remain uncommitted until their required native QA can finish.
The committed cancellation extension supplies the ten-case FTP boundary suite.

The source still contains the browser applet: release 25 reports its `JApplet`
removal warning. Legacy deprecated/raw-API notes also remain. The planned platform
step retires the obsolete entry; this report does not hide the diagnostic or
claim a warning-free platform build.

## Reproduce the compatibility probe

Use a disposable shell and select a verified JDK 25 using `JAVA_HOME`. From the repository
root, with no application running from `target`:

```powershell
.\mvnw.cmd -B '-Dmaven.compiler.release=25' clean test
$env:MAVEN_ARGS = '-Dmaven.compiler.release=25'
python tools/run_ftp_integration.py
python tools/run_ftp_integration.py --tls
```

The real-server guides describe fixture installation. Close the probe shell
when returning to the normal build; its current default
compiler target is still release 8. `clean` removes the project's generated
`target` directory. The recorded run checked its resolved workspace path and
confirmed it was not a reparse point before cleaning.

[Evidence](evidence/java25-readiness/result.json) preserves the version output,
test/compile logs, transport receipts, source fingerprints and class-version
observations. This supports [ADR-004](../ADR/004-java25-platform.md). It does not
establish native FTPS, desktop cancellation, other operating systems or completion
of the overall refactor.
