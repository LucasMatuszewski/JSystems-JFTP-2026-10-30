# JFTP source context

Before changing the application, read its [current-state PRD](PRD.md), [architecture record](ADR/000-main-architecture.md), [baseline verification gate](ADR/001-baseline-verification.md), and [evidence register](current-state-evidence.md). They distinguish source-derived behavior from pending runtime confirmation.

The [Mermaid UML views](uml/README.md) add class relationships, connection sequencing, transfer activity, abstract session states and deployment boundaries, with offline SVG copies.

For JFTP architecture, system design, application workflows, overall logic, and symbol navigation, start with the [modernization plan](repo-maps/jftp-modernization-plan.md) and [source map](repo-maps/jftp-overview.rust.md).

The map covers `src` and contains selected declarations. Verify current original definitions, callers, resources, and `pom.xml` before edits; the map does not resolve runtime behavior. Refresh snapshots after relevant source or dependency changes.

For the portable graph viewer, saved caller checks, and regeneration commands, use the [code graph course guide](code-graph/README.md). Treat extracted edges as navigation and verify consequential relationships in the original source.

For connection and transfer implementation context, use the [Repomix course guide](repomix/README.md) and its selected full-code pack. Original comments and bodies are retained within that documented scope; inspect current originals and omitted dependencies before edits, and refresh the pack after relevant changes.
