---
name: write-implementation-plan
description: Create or revise a reviewable implementation plan for a bounded application slice using accepted PRDs, ADRs and current repository evidence. Audit document coherence and prepare orchestration gates without starting implementation or workers. Use for implementation planning, not initial product discovery or architecture authoring.
---

# Implementation Planning

Produce a plan another orchestrator can execute without rediscovering scope or making architecture decisions. Keep the plan small by linking authoritative requirements; put exact execution context in durable task briefs only when dispatch is authorized.

## Approval gates come first

The sequence is **accepted PRD and applicable ADR decisions → plan awaiting approval → accepted plan → authorized execution**. Approval of a new plan necessarily follows its creation; do not ask someone to approve a nonexistent plan. For an existing plan, check its current acceptance before resuming execution.

- Discover the application's actual documents and acceptance evidence before authoring the plan. Use explicit user approval or an accepted project record for the applicable revision/scope; a document authored with an Accepted heading, a commit, successful build, or prior discussion alone is not proof of human acceptance.
- If the governing PRD or an applicable ADR decision is unaccepted, missing, or ambiguous, **stop before writing a new implementation plan**. Name and link the specific document/section, quote its status and explain the missing approval or decision. Read-only discovery needed to establish the gap is allowed. Do not create an execution-ready matrix around the gap or invoke document-authoring workflows without scope to do so.
- Check active governing PRDs, ADRs and plans for unaccepted statuses such as Draft, Proposed or Awaiting review. Use **Awaiting plan approval** for a new plan. Require evidence-backed acceptance of the governing documents before implementation; removing or renaming a status does not establish acceptance. This rule concerns document status, not ordinary uses of “draft” such as an unsent message draft, test draft or historical description. Preserve those meanings. Once actual approval is established, update stale status labels and approval-dependent text together, within authorized scope.
- Keep approval, implementation and runtime verification as separate states. An accepted design can still be unimplemented or have an explicit feasibility gate. Only decisions governing the selected slice must be accepted; unrelated historical or superseded records do not block it.
- Plan creation does not authorize workers, implementation, worktrees, publication or merge. Plan acceptance is a prerequisite, not an automatic instruction to start work. Honor explicit combined instructions such as “approve and implement” without asking again. Record approval already given; do not leave a stale waiting gate or demand repeated confirmation.

Changing a requirement or architectural decision invalidates affected downstream acceptance until reviewed. An editorial correction that preserves meaning does not require a new approval loop.

## Ground the plan in this checkout

1. Read applicable root/nested AGENTS.md, the feature PRD, applicable ADRs and design guidance. Search the repository before asking for paths. Distinguish the application from course materials, sample scaffolds and tutorial role definitions.
2. Inspect working-tree status, HEAD, branch, entry points, actual build descriptors/dependency availability, callers, tests and existing tracker. Use prescribed source maps/graphs/packs for navigation, then verify consequential originals. Do not replace a legacy checkout with a fresh app.
3. Run scoped, non-mutating baseline checks when permitted. Save full logs in allowed artifacts and return concise counts/diagnostics, rather than flooding output and losing warnings. Separate commands executed, commands only verified from source, and commands still proposed. Record discovery counts/skips, startup, real user flow and baseline failures separately; do not infer runtime success from a build.
4. Preserve unrelated work and recheck HEAD/status before handoff. If another actor committed during inspection, identify whether runtime inputs changed; do not attribute that commit to yourself or rerun tests merely because documentation changed.
5. Ask only consequential unresolved questions after discovery, with recommendations and interactive tools when requested/available; there is no minimum question count. If an asynchronous question received no answer, it remains unanswered. Use recommendations instead of answers only when the user delegates that choice. Do not invent architecture to avoid a question: put a necessary new decision into a proposed ADR for review.

Respect the current mode: in read-only Plan mode, return the complete plan and intended destination without saving. Otherwise save to `docs/plans/<feature>-implementation-plan.md`, unless the user chose another location. Reuse an existing relevant plan rather than generating competing versions.

## Build one bounded delivery plan

Use [references/plan-template.md](references/plan-template.md) when composing the plan. Adapt its size to the slice, keeping **one authoritative dependency matrix** with exactly these columns:

| Task | Observable outcome | PRD/ADR links | Prerequisites | Owner/model | Editable files | Verification and handoff |
|---|---|---|---|---|---|---|

- Choose one demonstrable end-to-end flow, including its failure/recovery path and preserved existing behavior. Link exact AC identifiers and ADR/design sections. An anchor must lead to the cited requirement, not simply an existing file. A subset is explicitly partial delivery, not completion of deferred feature criteria.
- Do not copy requirement lists, model schemas, API contracts or technical constants from PRD/ADRs into the plan. Reference them. Put planning facts here: sequencing, dependencies, file ownership, evidence, gates and commit boundaries. Missing architecture is not a plan assumption.
- Give each row one bounded deliverable, actual prerequisites, a selected available model, editable-path allowlist, acceptance evidence and commit boundary. Plan an observed failing behavioral test before new production behavior, GREEN characterization plus a reverted controlled mutation before legacy refactoring, integration checks after dependent components, actual runtime QA, independent review, and a PR for the finished slice under repository publication rules.
- Name the critical path, shared-file hotspots, failure/rework and rollback handling, and a smaller fallback. A partial or offline fallback must not be presented as successful delivery of the real flow.
- Use the existing tracker for execution status, not a second status table in the plan. If no tracker can be identified, explain that gap and establish the status destination with the user rather than silently creating a competing tracker. Keep review status and execution progress distinct.

## Orchestration and just-in-time briefs

Select models from the active harness, not a tutorial. Default to **Luna for bounded implementation, tests and QA when available**; the strong primary orchestrator owns decisions and independently checks the result. Name a separate fresh reviewer/model. A worker cannot accept its own work or alter scope/contracts. Do not invent model IDs or claim access without checking the harness. If delegation is unavailable, preserve the same task boundaries and execute sequentially with the actual primary model after authorization.

Prefer ad hoc subagents for one-off work; create short custom definitions only for reusable roles or stable restrictions. Planning itself launches no workers. Parallelize only independent inputs with disjoint editable files, and tell both workers what is concurrent. Serialize contracts, build/lock files, migrations, integration and tracker edits. Use one checkout; follow repository approval/cleanup rules if a worktree becomes necessary.

After plan acceptance and execution authorization, immediately before each task the orchestrator writes a durable, self-contained brief such as `.agent-briefs/<feature>/<task-id>.md`. Do not write every brief during planning. Each brief contains only:

- The bounded outcome, selected model, exact editable files, concurrent ownership and forbidden changes.
- The applicable requirement/ADR clauses and chosen contracts copied as necessary exact context, with authoritative source links for traceability. Unlike the compact plan, the brief must contain enough substance for a small worker to act without rediscovering the architecture.
- Current source excerpts/call sites, accepted predecessor outputs and exact interfaces needed by this task, not full repository packs or an inherited full conversation.
- Ordered steps, actual commands, synthetic inputs, expected RED and GREEN outcomes, relevant integration/runtime checks, stop conditions and result-report requirements. Resolve task-local implementation choices before dispatch; route consequential new architecture to an ADR first.

The orchestrator reads the diff and reproduces relevant checks before integration or commit. Component handoff is provisional where native acceptance is not yet possible; mark that gap, do not close the task or commit an unverified running flow. Follow actual per-task QA/commit policy rather than turning the plan into a blanket exemption.

## Mandatory coherence audit and handoff

**After every new or revised plan, and again when recording acceptance or preparing execution, audit the plan against the current PRD, ADRs, design guidance and code evidence.** Read [references/coherence-audit.md](references/coherence-audit.md), including the session-derived failure patterns. This is a semantic audit, not just a link/keyword check.

Correct evident editorial/source-citation errors when authorized and supported by the owning source; report what changed. Explain material contradictions with exact document links and their effect on planning/execution. Do not silently choose one conflicting contract, relabel an unapproved document, or copy a workaround into the plan. Reaudit affected relationships after a correction. Do not hand off a contradictory plan as ready for approval or execution.

For a new plan set **Awaiting plan approval** unless the user explicitly accepted that exact current scope/content. Upon acceptance record who/when/scope from actual evidence and update all stale waiting text. Do not use “draft” as a maturity label. Acceptance never establishes that tests ran or implementation exists.

Finish with the saved link (or full content/destination in read-only mode), audit result and unresolved decisions/prerequisites, the first bounded task and the review/execution gate. Say what was actually checked. Never imply that source inspection, validator success or plan approval proves runtime behavior.
