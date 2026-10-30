# Repository Guidelines

## Project

JFTP is a Java/Swing desktop FTP and FTPS client for browsing local and remote files, transferring files, and managing connection profiles. The application is in this checkout. Inspect its actual Maven descriptor, source, call sites, resources, and dependency availability before choosing build or startup commands; a declared dependency is not proof that its implementation is available.

Use English for all chat, application documentation, participant instructions, and UI text.

**Key documentation:**
- `docs/PRD.md` — current user-visible behavior and its evidence; proposed behavior must be distinguished from the observed baseline.
- `docs/ADR/` — current architecture, technical contracts, constraints, and validation strategy.
- `docs/design-guidelines.md` and `assets/homepage.png` — supplied JSystems brand references for approved visual changes.
- `docs/source-context.md` — RepoMap, code graph, and focused full-source context guides. Generated snapshots are navigation aids; verify current original definitions and callers before editing.

## Repository Layout

```
src/main/java/       Desktop client, shared Swing components, utilities, and ZIP helpers
src/test/            Java application test roots
tests/               Repository-tool checks
assets/              Supplied logo, design tokens, and visual reference
docs/                Product/architecture descriptions, evidence, and context examples
tools/               Local context-generation and verification helpers
```

`README.md` is the application introduction. Keep application documentation consistent with verified behavior and commands. Add an update-notes section or changelog only when requested.

---

## Agent Workflow

### Local code investigation and generated context

- Use [docs/source-context.md](docs/source-context.md), RepoMap, the code graph and scoped Repomix excerpts first, before file-by-file source searches. Delegate source reading to Luna with a focused question and request concise, source-cited findings; verify critical originals before editing.
- After changing captured inputs, refresh and verify affected maps, graphs, packs and related derived files before committing or handing off. Preserve explicitly frozen historical baselines.
- Investigate and correct discrepancies between code, agent instructions and generated context; record their cause. Treat generated context as navigation, not authority over valid code. Consult the human when intent remains unclear.

### Research order: CLI help first, then Context7, web search last

1. **For CLI tools, check built-in help first** (`<tool> --help` and relevant subcommand help), then use Context7 if the help is insufficient. **Search Context7 MCP first** for other project, library, framework, SDK, API and platform documentation. Use web search only as a last resort, after checking Context7.
2. When using Context7, start with `resolve-library-id`, using the library/project name and a focused documentation question, unless an exact Context7 ID was supplied. Prefer the closest authoritative, version-matching result.
3. Use `query-docs` with that ID, scoped to one concept per query. Try relevant alternate names or focused queries before concluding that the needed documentation is missing or insufficient.
4. **Only after an identified Context7 documentation gap, delegate internet research to Luna.** Give Luna the attempted IDs/versions/queries and the precise unanswered question; require official/primary sources and their URLs. Verify the findings against the repository before applying them. Do not delegate web research before the Context7 check.
5. If Context7 MCP is unavailable or fails, report that prerequisite instead of silently switching to internet search or claiming that a search found no documentation.

### When implementing new or refactoring functionality
1. Read the relevant PRD and ADR files for the affected area.
2. Define the expected behavior from the specification before writing or changing any code.
3. Confirm the actual application checkout and inspect source, build descriptors, call sites and dependencies before proposing commands or APIs. Identify build, test and startup commands from that project, using its wrapper when available; do not assume a framework, build tool or runtime version.
4. Establish missing or ambiguous requirements with the user and record agreed application scope in its PRD before implementation. Routine documentation changes do not need an application PRD or ADR.

### TDD Rules
For every feature and bug fix:
1. Start from the specification, not the existing implementation.
2. Write or extend tests **before** production code.
3. Run the new tests and confirm they fail for the expected reason.
4. Implement the minimum code needed to make them pass.
5. Run the full verification suite for the changed scope.
6. Refactor only while tests stay green.
7. **Manually validate the running application** (see Manual QA below). Automated tests — especially E2E — can produce false passes; a task is not done until the real app has been exercised by hand.

If the area has no suitable test infrastructure yet, add it as part of the task — do not silently skip tests.

Before creating or refreshing a unit/integration mock of an endpoint or database, make a fresh real request/query in an authorized local/test environment using synthetic data. Save the complete response verbatim (HTTP status, headers and body, or database result rows and type/schema information) and derive the mock from that capture, not from documentation that may be stale. Use a response containing no secrets; record its date, target, request/query and relevant service version without credentials. If a safe capture cannot be obtained, report the missing prerequisite instead of inventing a response shape.

For behavior-preserving legacy refactoring, write characterization tests against the original code and get them green before changing it. Assert observable inputs, outputs, state changes and errors rather than private methods or the current class layout. Demonstrate that critical assertions detect a controlled behavioral mutation, then revert that mutation. Existing tests may protect a pure refactor; a new behavior or bug fix still requires Red -> Green -> Refactor.

Investigate production code and fixtures before changing a failing assertion. Do not weaken, disable or delete tests to conceal a defect. Change expected behavior only when the specification supports it, and explain why.

### Manual QA (required after every task that affects the running app)

Use the application's actual runtime and verified commands. For JFTP's Windows Swing UI, use **Computer Use through `@oai/sky` in `node_repl`**, or human verification on the real application. **Screenshot-based coordinate clicks plus keyboard shortcuts worked best in the completed click-throughs**; Swing's accessibility tree often exposed only the window frame, making element-index actions less reliable. See the [native FTP verification](docs/testing/native-ftp.md).

- Select the actual application window after each launch. Observe, perform one action, then refresh state. Swing can update after the first capture; reobserve an unchanged/uncertain result before the next input or evidence capture.
- For modal dialogs and menus, target the returned screenshot region containing the control (the dialog/menu's own region when provided), using its current screenshot ID and region-relative coordinates. Do not reuse stale coordinates, IDs or accessibility indexes.
- Use CLI tools for build/startup, synthetic server setup, logs and byte/hash comparisons. Use **the Playwright CLI** for browser surfaces and rendered documentation; neither CLI/component tests nor browser automation replaces clicking through JFTP's native UI.

1. Start JFTP with its verified project command and inspect its real desktop window. Record successful startup separately from compilation and test execution.
2. Take a screenshot of every screen you touched, after the intended state is visible. Save evidence in `docs/testing/screenshots/`.
3. Exercise the affected real flow end-to-end by hand: connect to an authorized test server, browse, transfer files, cancel, or reconnect as appropriate to the changed scope. Use synthetic data and real services, without mocks.
4. Verify the flow actually works: correct navigation, correct data displayed, no application/log errors, English UI text. Check actual transferred bytes and destination state, not just a success message.
5. **Compare your screenshots against the brand reference** (`assets/homepage.png` + `docs/design-guidelines.md` tokens): colors, typography, spacing, button styles and logo placement must match the application's agreed brand reference.
6. Report what you validated (steps + screenshots) in your task summary. If anything looks or behaves wrong, fix it before committing.
7. If desktop access, native tooling or a required service is unavailable, state which flows remain unverified. Process/window metadata and passing headless tests do not complete required native QA.

### Verification (required before every commit)

Verify only the scope relevant to your change. If the change affects runtime behavior, confirm the app starts correctly.

**Test Strategy:**
| Type | Mocks | Who |
|---|---|---|
| Unit | All deps | be/fe-dev |
| Integration | Only external LLM API | be-dev |
| E2E | NOTHING (real stack) | qa-engineer |

**Verification:** For runtime changes, start the real application and finish with Manual QA. Record compilation/build, test execution and startup as separate checks, including discovery counts and skips. A successful build or E2E run alone does not establish that the app works. Documentation-only changes use diff, link and consistency checks and do not require application startup. Fix errors and warnings introduced by the change; report pre-existing failures without hiding them.

**Env Vars:** JFTP does not require an LLM API key. Inspect the actual application configuration and authorized synthetic test setup; never read secrets or assume an example environment file exists.

### Commit Rules
- Commit only after verification passes and the changed scope is in a working state.
- Keep commits focused: one logical change per commit.
- Format: `Area: short summary` (e.g. `Backend:`, `Frontend:`, `Docs:`)
- Do **not** push to remote unless the user explicitly asks.

### Git Worktrees
- Use this checkout by default. Create a worktree only when requested or needed for parallel agent work; ask first when not explicitly requested.
- Remove worktrees with `git worktree remove <path>` and run `git worktree prune` when done.

### Completion Criteria
A task is complete only when:
- Implementation matches the relevant PRD, ADR, and design guidance
- Tests were written first and pass honestly
- Verification for the changed scope passed with no errors or warnings
- Runtime changes passed Manual QA on the actual UI, CLI or API, with screenshots or command/request evidence and the application's own visual reference where applicable; documentation-only work passed its scoped checks
- The commit message is focused and the repository is in a consistent, reviewable state
