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

The committed baseline test scope contains **51 local/model/archive cases**:
48 established cases and three later characterizations of the unchanged ZIP
extractor. The current working tree also has 14 desktop-feature cases, bringing
its observed ordinary run to 65; that feature still awaits native acceptance.
See [archive evidence](testing/archive-compatibility.md) and
[desktop verification](testing/branded-desktop.md). Tests run with
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

## GitLab CI checks

The root [.gitlab-ci.yml](../.gitlab-ci.yml) runs two parallel jobs on a Linux
Docker runner with JDK 25, using the pinned Maven Wrapper:

The Java jobs install `unzip` so the Unix wrapper uses the pinned ZIP archive.
Without it, the wrapper downloads TAR.GZ, which does not match the ZIP checksum.

- `java-lint` runs Checkstyle 3.6.0 against production Java sources. The small
  [ruleset](../ci/checkstyle.xml) rejects empty statements, internal `sun.*`
  imports, mismatched outer type filenames, and lowercase `l` long suffixes.
- `unit-tests` runs `clean test` with the headless settings from `pom.xml`.
  Surefire XML reports appear in GitLab's test results and remain available,
  alongside the lint report, as job artifacts for three days, including failures.

Pipelines run for branch/tag pushes and merge requests. An open merge request
uses its MR pipeline instead of creating a duplicate branch push pipeline.
Maven downloads are cached within the project. The Java checks require no CI
secrets. The separate manual `pr-agent-review` job adds an advisory AI review to
same-project merge requests; see [PR-Agent setup](gitlab-pr-agent.md) for the
two required secrets, model settings and first-run steps.

Run the equivalent checks locally in PowerShell:

```powershell
.\mvnw.cmd -B -ntp org.apache.maven.plugins:maven-checkstyle-plugin:3.6.0:check "-Dcheckstyle.config.location=ci/checkstyle.xml" "-Dcheckstyle.includeResources=false"
.\mvnw.cmd -B -ntp clean test
```

The GitLab runner needs outbound HTTPS access to Maven Central and the FTPAPI
publisher repository. These checks exclude the separate `*IT` suites and do not
launch the desktop application or establish native UI acceptance; real FTP/FTPS
and manual desktop QA still use their existing verification guides.
