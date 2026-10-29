# Build and test JFTP

The recovered build uses JDK 25 and Maven Wrapper 3.3.4, pinning Maven 3.10.0 and its
distribution SHA-256. No global Maven installation is required. First execution
needs HTTPS access to Maven Central and the publisher's FTPAPI repository.

From PowerShell:

```powershell
.\mvnw.cmd test
.\mvnw.cmd package
```

On Unix-like systems the equivalent entry point is `./mvnw`; this repository's
recovered build route was exercised on Windows. The compiler temporarily retains
release 8 compatibility and excludes the unused Apple-specific `OSXAdapterOld`;
neither choice is the final runtime modernization target.

The ordinary test scope contains **48 local/model/archive tests**. They run with
an isolated Java home/temp directory and English locale. Real FTP/FTPS tests run
separately through [their fixture guides](testing/ftp-contract.md). The original
[source-only baseline runner](testing/README.md) remains available for comparing
behavior before Maven recovery.

`package` produces the application JAR, a source JAR, and ZIP/tar/directory
assemblies under `target`. The application JAR belongs at the distribution root,
with runtime dependencies under `lib`. The [packaging checks](../tests/test_distribution.py)
inspect the actual archive rather than infer success from compilation.

Extract the distribution and run `jftp.bat` on Windows or `jftp.sh` on Unix-like
systems. The launchers locate the application JAR beside themselves and forward
arguments. Windows startup from a directory containing spaces, with a different
working directory, passed [packaged desktop QA](testing/packaged-launcher.md).
Unix launcher execution remains untested. Original native baseline startup is
recorded separately in [the baseline report](testing/README.md).

Known legacy build diagnostics include Java 8 compatibility/deprecation warnings
on JDK 25 and an assembly warning that a directory-format output cannot be attached
as a regular artifact. The ZIP and tar outputs are produced successfully. These
diagnostics remain explicit follow-up work, not a warning-free build claim.

Do not invoke release, deploy or tagging goals as part of local verification.
Legacy publishing configuration remains in the POM and was not exercised.
