# Implementation plan structure

Use for one demonstrable slice. This is a shape, not an instruction to leave placeholder rows or reproduce full specifications. Replace placeholders from actual repository evidence before handoff. Omit irrelevant sections rather than creating empty scaffolding.

## Header and scope

- Title: `Implementation plan — <observable slice>`.
- Status: `Awaiting plan approval`, or an acceptance statement supported by the user's actual approval. Do not call it accepted merely because PRD/ADRs are accepted.
- Inspected checkout, branch, initial/final revision where relevant, and intended destination.
- One short paragraph describing the demo and recovery path. Identify deferred criteria and what existing behavior must remain usable, using links rather than copying acceptance text.
- Link governing PRD AC identifiers, accepted ADR sections and applicable design references. Explain any status ambiguity before planning; do not put an unaccepted architecture choice in an assumption list.

## Repository evidence and baseline

Describe what exists now: entry point, affected data/state boundaries, current tests and dependency/runtime facts. Identify unrelated working-tree changes to preserve and the existing execution tracker. Teaching examples are navigation, not requirements.

A compact baseline command/result table is useful; it is not a second task/status matrix. Distinguish:

- Executed checks, exit status, fresh discovery counts/skips and known warnings/failures.
- Startup commands checked against source/artifacts but not launched.
- Native flow, real service or tooling prerequisites not yet exercised.

Prefer a few verified commands and source pointers over a chronological investigation transcript. Logs belong in allowed artifact paths; secrets never belong in the plan.

## Authoritative dependency matrix

| Task | Observable outcome | PRD/ADR links | Prerequisites | Owner/model | Editable files | Verification and handoff |
|---|---|---|---|---|---|---|

Build only the necessary rows. Typical dependency shape: prerequisite/feasibility gate → shared contracts → disjoint implementation tasks → integrated flow → runtime acceptance → derived-context refresh if required → independent review → commit/PR handoff. Do not turn that example into a mandatory ten-task process.

For every row include:

- One bounded outcome and the requirement/decision identifiers it satisfies. Use direct links or a small link-reference legend; do not paste the underlying requirements.
- Explicit predecessor IDs and an actual selected available model. Select the strong orchestrator for decisions and default to Luna for bounded work when available.
- An exact writable area, narrowed to concrete files in the authorized task brief. Define path aliases unambiguously if needed.
- RED/GREEN or characterization evidence appropriate to the task, dependent integration checks, actual runtime QA where applicable, and handoff artifacts.
- A focused intended commit boundary or a clearly named contribution to a later verified slice commit. A deliberately failing RED state is evidence, not a commit that bypasses repository green/native gates.

State the critical path and only genuinely parallel work. Freeze shared contracts before parallel work, declare concurrent file ownership, and serialize hotspots. Identify which inputs a later task needs from each predecessor.

## Execution context and verification

State who makes decisions, writes just-in-time durable briefs, accepts handoffs and maintains the existing tracker. Plan only the brief path and required contents; do not generate implementation briefs or launch workers at planning time. A small worker's later brief embeds the necessary exact clauses/source/contracts instead of requiring architecture rediscovery.

Give actual project commands for scoped tests, integration, build and startup. Record placeholders for future test names only as a planning convention; the orchestrator must replace them with exact commands before dispatch. Do not assume a wrapper/build framework, test-discovery phase or native automation route that the project lacks.

Link the native/manual acceptance requirements and existing visual reference. Specify evidence at meaningful boundaries, including actual data/destination state where relevant. A green build, window PID or browser screenshot cannot stand in for a native desktop flow. Refresh affected generated context before commit under local rules; preserve frozen historical baselines.

Include a fresh independent review followed by orchestrator verification and bounded fixes/rechecks. Finish with a prepared PR and actual PR creation when explicitly authorized under repository rules; no implied push, publication or merge authorization.

## Failure, fallback and handoff

Link unresolved architectural decisions to proposed ADRs and stop dependent tasks until accepted. Describe prerequisite gates such as real-service credentials or native access without treating them as architecture assumptions or silently skipping them.

Explain rework and rollback within owned files/commits; preserve unrelated work. State a smaller useful fallback and exactly which original flow remains undelivered. Model/tool exhaustion changes scheduling, not the honesty of acceptance.

Close with the coherence-audit result: areas compared, evident corrections, any unresolved contradiction and its effect on readiness. Keep findings concise; do not create a duplicate requirement catalog or status tracker.

For a newly written plan, end at the user's review gate. For an accepted plan, retain the approval evidence and say which first bounded task can begin when execution is requested. Material revisions return affected scope for review; do not reuse obsolete acceptance.
