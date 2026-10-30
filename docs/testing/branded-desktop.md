# Branded desktop verification — 2026-10-30

**Status: incomplete native acceptance.** The implementation and its 14 new
local tests are in the working tree. The runtime commit is held until the final
candidate passes the repository's required native QA.

## Verified implementation and CLI scope

The candidate adds a JSystems header with the supplied right-side SVG, FlatLaf
3.7.2, readable desktop controls/rows, English application defaults and local file
descriptions, and EDT-dispatched startup. Appearance preferences name the fixed
theme; regional preferences show fixed English while retaining date/time style
choices. Existing serialized locale/theme fields remain readable.

Tests were written before the corresponding production changes. New desktop
APIs initially failed compilation, four English file-display expectations failed
against Windows shell labels, and focused tests exposed ignored appearance/locale
choices. A further regression first failed without the selected pure-Java FlatLaf
configuration. Original source/profile characterization tests remain unchanged.

- The normal wrapper package passes **62 local cases**, zero failures/errors/skips.
- Four archive checks pass, including exact supplied-logo bytes, theme resources,
  main entry, runtime classpath and product-document selection.
- The direct-source runner independently passes the same 62 cases.
- [Java 25 readiness](java25-readiness.md) records 62 local, ten real FTP and five
  explicit FTPS cases on Temurin 25.0.5 with release-25 application/test classes.
  The default POM target was restored to release 8 after that compatibility probe.

These counts do not establish final native transfer acceptance for the branded UI.

## Native observations before the last startup adjustment

The successful native candidate used an isolated synthetic profile containing
German locale and a legacy Windows look-and-feel choice. It opened with English
application labels, `Desktop` and `BIN File` descriptions, and the branded light
appearance. The configured outer window was 1000 by 700 pixels. The header and
session controls remained visible, with the logo on the right.

| Observed step | Screenshot |
|---|---|
| Workspace and right-side wordmark | [Workspace](screenshots/2026-10-30-branded-workspace.jpg) |
| Create another session | [Two sessions](screenshots/2026-10-30-branded-two-sessions.jpg) |
| Close selected session | [After close](screenshots/2026-10-30-branded-after-close.jpg) |
| Open Connect, then cancel without connecting | [Connect](screenshots/2026-10-30-branded-connect.jpg) |
| Open preferences | [General](screenshots/2026-10-30-branded-preferences-general.jpg) |
| Inspect fixed theme and corrected label spacing | [Appearance](screenshots/2026-10-30-branded-appearance.jpg) |
| Inspect fixed English and date/time styles | [Regional](screenshots/2026-10-30-branded-regional.jpg) |
| Read the synthetic favorite | [Favorites](screenshots/2026-10-30-branded-favorites.jpg) |
| Connect and load a real remote directory | [Connected](screenshots/2026-10-30-branded-connected-initial.jpg) |

The initial appearance iteration is retained as
[before spacing correction](screenshots/2026-10-30-branded-appearance-initial.jpg),
along with the [Tools menu](screenshots/2026-10-30-branded-tools-menu.jpg).
These are development observations, not screenshots of the final candidate.

## Visual comparison and remaining native gates

Compared with `assets/homepage.png` and `docs/design-guidelines.md`, the candidate
now uses the supplied wordmark, white surfaces, burgundy emphasis/focus/selection,
larger text and clearer row spacing. Arial is the documented desktop fallback;
Poppins is not bundled. Legacy toolbar icons and the historical version banner
remain follow-up work. The supplied logo's small Polish caption also needs an
English-desktop asset decision before claiming an entirely English presentation.

The earlier successful run exposed a JDK 25 warning from FlatLaf's optional
native library. The implementation now selects its documented pure-Java path;
the configuration regression passes. Its final real-window check remains pending.
The [recorded evidence](evidence/branded-desktop/result.json) preserves that warning,
the final package/test results, screenshot hashes and the later startup diagnostic.

During the final check Windows reported the RDP session disconnected, and an
independent Java probe reported `headless detected=true`. The final app could not
create a window. No desktop/security settings were changed to work around this.
Owned QA application instances and FTP fixtures have been closed.

When desktop access returns: launch the copied final distribution on the selected
JDK, check startup logs/window frames and touched dialogs, repeat Connect cancel
and tab controls, and complete real binary upload/download with byte comparisons.
Capture final screenshots and resolve the remaining logo-language detail before
the runtime commit. Native FTPS and F12/interrupted-transfer tests remain separate
overall-modernization gates. The [older unbranded FTP run](native-ftp.md) is useful
baseline evidence, not a substitute for these checks.
