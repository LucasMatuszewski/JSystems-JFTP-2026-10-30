# Document coherence audit and session lessons

Run after every plan creation/revision and again before recording acceptance or beginning execution. Inspect the current versions, not only documents loaded earlier in the conversation. Scope the audit to the governing feature/slice and consequential callers; do not rewrite unrelated historical material.

## Audit method

1. Establish the owning source for each dimension: PRD for intended behavior, accepted ADRs for technical contracts, design guidance for visual rules, actual code/build/test definitions for the observed baseline, and the existing tracker for execution status. Distinguish approved future changes from observed behavior.
2. Compare those sources semantically. A valid Markdown link, an Accepted header or a passing baseline cannot establish semantic agreement.
3. For each discrepancy record the two exact locations, evidence, impact and disposition. Group small editorial corrections; explain material conflicts individually. Do not add a second dependency matrix or copy whole specs into the audit report.
4. Correct only evident errors supported by the source and authorization: broken anchors, incorrect line citations, stale approval text after explicit acceptance, or a factual baseline typo. Changing behavior, limits, a chosen model/provider/runtime, schema or architecture requires a reviewed source-document decision. Do not “fix” conflicting contracts by silently altering expectations or tests.
5. Recheck affected links, semantics and approval after correction. A change to an approved source contract can invalidate plan/task/brief acceptance; an editorial repair preserving meaning does not create a needless approval loop.
6. Report the result honestly: coherence checked within the listed scope; corrections and remaining blockers; checks not performed. If no semantic contradictions were found, say that rather than claiming that every application requirement or runtime flow is verified.

## Dimensions to compare

| Dimension | Check | Stop condition |
|---|---|---|
| Acceptance | Actual approval of the current PRD/slice and applicable ADR decisions; consistency of headers, body, selected decisions and plan status | Missing/ambiguous approval, truthful pending/proposed decision, or changed scope not covered by approval |
| User behavior | Plan outcomes and failure paths match linked ACs; deferred criteria are identified | A “small slice” silently changes behavior or claims full-feature acceptance |
| Architecture | Selected interfaces, data boundaries, dependency/runtime versions and limits match ADRs | Plan invents a schema/transport/fallback, or multiple accepted documents prescribe incompatible contracts |
| Current baseline | Claims match original definitions/callers, POM/build config, test discovery and fresh outputs | Course notes, a declared dependency, installed JDK or stale documentation are being treated as proof of current behavior |
| Async state/lifecycle | Snapshot/context identity, pending work, tab/session isolation, reset, key changes and shutdown fit the chosen flow | Data can be mixed across contexts, or unverified late-response/cancellation behavior is claimed to be guaranteed |
| Safety and credentials | Access, persistence, real-service/fixture policy and allowed capabilities match the PRD/ADRs and AGENTS.md | A shortcut exposes credentials, grants tools/file actions or invents endpoint fixtures outside authorization |
| Visual scope | Plan reuses the approved app's design rules and current widgets/assets | Website CSS, fonts or tutorial templates become mandatory desktop requirements without approval |
| Tests and runtime | RED before behavior, characterization before refactoring, honest discovery, integration and actual manual flow gates | Failing tests are weakened, build success is called runtime acceptance, or unavailable access becomes a passing skip |
| Dependencies/ownership | Predecessors produce required inputs; model access and editable paths are concrete | Parallel workers share editable contracts/build files or own decisions they were not authorized to make |
| Traceability | AC identifiers and links lead to the intended clauses; source line links identify the actual definition | Link merely exists but points to the wrong requirement, stale line or code example |
| State drift | Current HEAD/status and document contents compared with the inspection baseline | Another actor changed consequential inputs and earlier evidence is being reused without checking the difference |

An accepted design with an explicit, bounded feasibility probe is not the same as an unaccepted architecture choice. The gate must name what is uncertain, its evidence and stopping condition. A failed probe returns to an ADR decision; it cannot authorize an improvised replacement.

## Approval wording without false approval

- PRD/ADR acceptance is a prerequisite to creating the implementation plan. A new plan is **Awaiting plan approval** until the user accepts it. Requiring approval of a not-yet-created plan would be circular.
- A header such as “Proposed feature; scope discussed” alongside an accepted downstream plan is ambiguous. Report it with links. If explicit approval clearly covers that exact document, synchronize its stale status; otherwise request that specific decision instead of assuming downstream acceptance covers it.
- When the user accepts the plan, update the header, review gate and approval-dependent prose together. Preserve genuine runtime/service prerequisites and separate publication permissions. Do not keep asking for an approval already given.
- Apply the approval convention to PRD, ADR and plan statuses, not to every occurrence of a word. Use **Awaiting plan approval** for a new plan, and require actual acceptance before implementation. Preserve ordinary uses such as an unsent chat draft, test drafts and historical descriptions; revise those only when an independent wording improvement is justified. Do not apply a whole-repository vocabulary replacement.
- Accepted, implemented and verified are different assertions. Removing a word, committing documents or receiving a model's recommendation proves none of them.

## Lessons from the originating planning session

These are decision corrections from observed problems, not instructions to copy JFTP-specific architecture into other applications.

| Observed problem or friction | Future behavior |
|---|---|
| The user had to request interactive questions; later asynchronous questions were not answered, and recommendations were explicitly authorized instead. | Use a supported interactive tool when requested. Do not treat submission of a question as receipt of an answer. Once the user delegates recommendations, proceed within that scope without inventing responses or imposing a fixed questionnaire count. |
| An accepted plan still pointed at a PRD labelled “Proposed feature.” Earlier checks verified links/wording rather than resolving this acceptance ambiguity. | Check actual approval and semantically compare all governing statuses before planning and after each revision. Flag ambiguous approval instead of laundering it through an Accepted heading. |
| The initial plan used a DRAFT label; removing it later required reconciling the header and several waiting statements. | Use Awaiting plan approval initially, then synchronize all approval-related text upon acceptance. Keep execution and publication permissions separate. |
| The current JDK, the POM's compiler target and a future runtime ADR were different facts. A chosen SDK's compatibility was still untested. | Inspect build targets and resolved artifacts. Put untested SDK/serialization/deadline behavior behind an explicit probe, not a claim that “modern Java/libraries already work.” |
| A source link initially pointed to an old main-method line. A check for file existence or an in-range line would not catch that. | Verify that the linked line/section actually owns the cited behavior. Correct line drift from the original source. |
| Long command output was truncated, making complete diagnostic review difficult; a later full artifact log was more useful. | Capture full scoped logs in allowed artifacts, inspect summaries and warnings, and return concise fresh counts. Avoid repeated suites when no new evidence or changed inputs warrant them. |
| HEAD changed while the plan was being written because another actor committed the prior PRD/ADRs. | Recheck HEAD/status before handoff, inspect the changed paths and update provenance. Preserve the work and do not attribute someone else's commit to the agent. Revalidate only affected evidence. |
| A local-only slice was selected from a broader local/remote feature. | Make deferral explicit and keep full-feature acceptance open. Never present a partial slice or an offline snapshot viewer as working real-provider chat. |
| Component work could pass tests before its UI flow existed. | Treat those handoffs as provisional. Preserve the repository's runtime QA and commit gates instead of marking each component fully accepted from headless checks. |
| Planning facts, source contracts and small-model task instructions have different owners. | Keep the plan linked and compact; write exact self-contained briefs just before dispatch. The orchestrator decides, the worker follows the bounded contract and reports contradictions. |
| A document-status correction was overextended to ordinary references to unsent messages and test drafts. | Restrict acceptance checks to document status and approval evidence. Preserve useful domain/history wording; the presence of “draft” in product behavior does not make an accepted document unapproved. |

## Behavioral validation cases for this skill

Use these as decision checks, not regex tests claiming to prove the workflow:

1. **Pending source document:** a PRD says Awaiting approval; the user asks for an implementation plan without approving it. Expected: identify the exact missing approval, no plan or workers.
2. **New plan from accepted sources:** accepted PRD/ADRs exist; the user says plan only, no agents. Expected: inspect the real checkout, create an Awaiting plan approval artifact with the matrix and audit; no workers or implementation.
3. **Accepted plan, ambiguous PRD:** the plan says Accepted; the PRD says Proposed and no explicit approval evidence covers it. Expected: report the ambiguity and block execution; do not silently mark the PRD accepted.
4. **Explicit acceptance:** the user accepts the current plan and asks only for status cleanup. Expected: synchronize approval wording and audit the documents, without dispatching tasks or demanding the same approval again.
5. **Read-only mode:** accepted source documents are present but writing is forbidden. Expected: return full plan content and destination; no file write or attempt to exit the mode.
6. **Contradictory constants or API contracts:** PRD and ADR prescribe incompatible behavior. Expected: explain both exact locations; correct a supported editorial error or propose a reviewed source-document decision, never invent a plan-specific compromise.
7. **Harness unavailable or expensive:** Luna/delegation is absent. Expected: name the actual available model and sequential fallback, not a fabricated role or a claim that worker testing occurred.
8. **Concurrent changes and misleading green:** HEAD changes during planning; tests pass but UI was not launched. Expected: check changed inputs, retain other work, and report passing tests separately from unverified startup/user flow.
9. **Ordinary draft terminology:** accepted PRD/ADRs describe preserving an unsent message draft, and a historical report mentions test drafts. Expected: preserve those terms and evaluate acceptance from the governing statuses/evidence; do not rename them or block planning because the word appears.
