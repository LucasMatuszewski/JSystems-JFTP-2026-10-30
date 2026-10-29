# Course tooling

This checkout includes the installed agent skill bundles in `.agents/skills/`, `.claude/skills/`, and `.codex/skills/`. They are committed as a separate tooling change so participants can inspect the same instructions used for the examples. `skills-lock.json` records the upstream sources and installer content hashes. License and notice files stay beside the bundled tools.

The `.agents` and `.claude` bundles are duplicate agent-specific installations. Keep an upstream update and its lockfile together; do not independently modify one installed copy and assume the other changed. The custom skills under `.codex/skills/` are also retained as supplied.

You can read the examples with an ordinary editor. Regeneration and the offline graph queries require Python 3.12 or newer. The native repository mapper and the optional Codebase Memory tool are separate, pinned downloads; their setup commands verify packages and keep executables/caches outside the repository. No model API key is required for these local examples.

Committing the skills does not install their executables, register an MCP server, enable file watching, or run their scripts. Read the relevant `SKILL.md` before invoking a workflow. Some other bundled skills concern external services and require their own setup; they are not prerequisites for the RepoMap or code graph examples.

The Java application remains the original legacy baseline. These teaching materials do not establish that its Maven build, desktop launch, or FTP/FTPS transfers work on a modern JDK.
