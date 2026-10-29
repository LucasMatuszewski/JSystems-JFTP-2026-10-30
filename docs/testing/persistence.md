# Persistence characterization — 2026-10-29

The expanded baseline passes **48 local/model/archive tests in seven classes**,
including 13 new persistence cases. Production Java still matches application
revision `14e62ceba4e371c2a0b955604b10f065f46f4f7d`; only tests were added for this
step. Both the committed legacy runner and the recovered Maven working-tree build
ran the expanded suite successfully, with zero failures, errors or skips.

## Isolation and reproducibility

```powershell
pwsh -NoProfile -File tools/legacy-baseline.ps1
```

Before their first reference to JFTP, both new classes require a synthetic
`user.home` named `profile` or `test-profile` beneath `java.io.tmpdir`. They back up
and restore only the synthetic profile's files. They never load the operator's
stored favorites, passwords, proxy settings or certificate keys. This is real
object-stream/filesystem IO, without mocked persistence.

| Class | Tests | Observations protected |
|---|---:|---|
| `PreferencesCharacterizationTest` | 6 | Passive/plain/binary defaults; TXT mapping; direct serialization; application save/load; missing-file initialization; corrupt-file behavior |
| `FavoritesPersistenceCharacterizationTest` | 7 | Missing/empty file; complete synthetic connection/security fields; append order; null-list handling; legacy List migration; corruption errors; fixed-size-list behavior |

Luna drafted these bounded tests. The coordinator reviewed original definitions,
actual callers and dependency constants, corrected fixture assumptions, strengthened
literal output assertions, and ran the checks.

## Verified legacy behavior

- Corrupt preferences return defaults and leave the corrupt bytes in place. The
  `finally` return in the original loader suppresses the object-stream exception.
- Corrupt favorites propagate `IOException` instead of returning an empty list.
- A plain serialized favorites List is loaded and rewritten through the available
  AES/SealedObject save path. This is compatibility evidence, not an encryption
  strength claim.
- Serialization retains the concrete List implementation. An initial append test
  used `Arrays.asList`, whose fixed size prevents append after reloading. Source
  review confirmed the UI uses a mutable ArrayList. The ordinary append fixture was
  corrected to match that caller; a separate test records the fixed-size-list
  exception rather than changing production behavior or hiding the constraint.

These observations describe the baseline. Silent corruption recovery, embedded-key
storage and fixed-size-list failures are not automatically requirements for the
modernized application; changing them requires explicit criteria and regression tests.

## Controlled mutation checks

The coordinator compiled modified copies into separate directories and prepended
only those copies to each test classpath. Original production files were never
edited or replaced.

| Temporary change | Result |
|---|---|
| Persist fresh defaults instead of the supplied preferences | Configured save/load assertion failed: 1 failure in 6 tests |
| Drop a favorite's password before saving | Literal retained-password assertion failed: 1 failure in 7 tests |
| Return to the original classes | All 13 persistence cases passed again |

[Evidence and hashes](evidence/persistence/result.json) retain the successful
normal runs and failing mutations. The original 35-test report remains a historical
milestone; this document supplements it. No desktop interaction or product UI
change was made by these tests.
