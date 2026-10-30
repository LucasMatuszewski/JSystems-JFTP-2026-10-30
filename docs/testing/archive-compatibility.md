# Original extractor compatibility — 2026-10-30

Three additional characterizations pass against `Unzip.java` unchanged from
original application revision `14e62ce`:

- An existing file longer than the entry is replaced with exactly the shorter
  entry bytes, without an old suffix or append.
- A file entry with no explicit directory records creates its nested parents.
- A 65,543-byte binary entry survives several read buffers and a partial last
  chunk with exact length and byte equality.

Each case uses real JDK ZIP output, actual files in an owned temporary directory,
and the public extractor API. No transport or filesystem responses are mocked.
The five original archive cases remain green. The complete local suite now
passes **65 cases, zero failures/errors/skips** on Temurin 25.0.5+8: 48 earlier
baseline cases, these three archive cases, and 14 pending desktop-feature cases.
This does not claim that the entire application working tree is still original.

A controlled mutation compiled an isolated copy of the extractor with its read
loop changed to one read. Exactly the large-entry case failed among the three
new cases (16,384 output bytes instead of 65,543); the other two passed. A fresh
JVM using the normal classes then passed all three. The real source and packaged
JAR were never changed by that mutation.

[Evidence](evidence/archive-compatibility/result.json) preserves the full local
test output, mutation compiler/test output, original-code rerun, source hashes
and baseline revision. Run the cases through the normal wrapper:

```powershell
.\mvnw.cmd '-Dtest=UnzipCompatibilityCharacterizationTest,ZipCharacterizationTest' test
```

These characterizations protect compatible extraction while implementing the
[containment PRD](../prd/archive-containment.md) and
[ADR-005](../ADR/005-contained-archive-extraction.md). They do not test or fix
malicious paths, links, integrity failure, or the native Download and Unzip flow.
Those new-behavior tests must fail against the old implementation before the
planned production fix. Desktop acceptance remains a separate gate.
