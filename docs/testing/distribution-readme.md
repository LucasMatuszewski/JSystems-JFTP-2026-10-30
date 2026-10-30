# Distribution README verification — 2026-10-30

The application archive now contains a standalone user README from
`src/main/distribution/README.md`. The repository README can retain developer
links without shipping references to documentation absent from the package.
The license and notice still accompany the application; agent instructions do
not enter the distribution.

Before changing the assembly descriptor, the new actual-ZIP link check failed
against the existing package: ten README links referred to missing repository
documentation (including one repeated architecture-directory link). After the
assembly change, a clean wrapper package on Temurin 25.0.5+8 passed all 62 local
cases, with zero failures, errors or skips. The actual archive then passed all
five packaging checks, including the new relative-link check. The packaged user
README's two license/notice links both resolve inside the ZIP.

Reproduce after building:

```powershell
python -m unittest tests.test_distribution tests.test_distribution_docs -v
```

This documentation/assembly selection change does not alter application runtime
behavior. The current branded application remains a separate working-tree
change with native acceptance pending. Existing compiler/deprecation diagnostics
and the assembly plugin's directory-attachment warning remain visible in build
output. The user README describes the supported Java 25 graphical environment;
it does not certify Unix or macOS startup.
