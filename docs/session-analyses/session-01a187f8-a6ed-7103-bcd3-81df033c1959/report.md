# Detailed inspection of this conversation

Session `01a187f8-a6ed-7103-bcd3-81df033c1959` only. Started 2026-10-29T14:03:03.498Z; captured 2026-10-29T14:51:53+00:00. Exact request counters end at 2026-10-29T14:51:42.706Z. All timestamps are UTC. Automatic-review threads and every unrelated session are excluded.

The native per-response counters record **1,135,202 processed tokens across 19 requests**: 1,118,433 input plus 16,769 output. The input includes 1,022,464 cache reads (91.42%), 0 cache writes and 95,969 ordinary input tokens. Output includes 6,904 reasoning tokens; reasoning is not added again. Repeated input across requests counts again, so processed tokens are not the amount of unique text in the chat.

All 19 request records have reported counters; 0 are missing usage, 0 are unpriced, and the cumulative native input/output endpoint matches the request sums. Reference API-equivalent cost: $0.4619–$0.8399, using bundled card 2026-10-17.2. This is not subscription spending or an invoice.

## Usage by user task

| Task | Requests | Input | Cached input | Output | Reasoning subset | Total |
| --- | --- | --- | --- | --- | --- | --- |
| Analyze all locally available Codex sessions | 7 | 264,383 | 201,984 | 2,947 | 1,432 | 267,330 |
| Save the three all-session reports in the repository | 2 | 116,075 | 115,456 | 549 | 0 | 116,624 |
| Inspect only this conversation and its current context | 10 | 737,975 | 705,024 | 13,273 | 5,472 | 751,248 |

## Exact request ledger

These are model requests, not user messages or tool calls. Input contains the cached column, and output contains the reasoning column.

| # | Completed UTC | Task # | Input | Cached input | Output | Reasoning subset | Total |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | 2026-10-29T14:04:23.065Z | 1 | 19,612 | 13,184 | 225 | 0 | 19,837 |
| 2 | 2026-10-29T14:04:48.804Z | 1 | 24,092 | 19,712 | 561 | 329 | 24,653 |
| 3 | 2026-10-29T14:05:15.654Z | 1 | 26,911 | 24,448 | 438 | 147 | 27,349 |
| 4 | 2026-10-29T14:05:37.485Z | 1 | 32,799 | 13,184 | 274 | 26 | 33,073 |
| 5 | 2026-10-29T14:05:58.189Z | 1 | 47,720 | 27,136 | 185 | 62 | 47,905 |
| 6 | 2026-10-29T14:06:35.939Z | 1 | 56,379 | 47,744 | 423 | 352 | 56,802 |
| 7 | 2026-10-29T14:10:04.098Z | 1 | 56,870 | 56,576 | 841 | 516 | 57,711 |
| 8 | 2026-10-29T14:18:54.057Z | 2 | 57,728 | 57,472 | 464 | 0 | 58,192 |
| 9 | 2026-10-29T14:19:12.819Z | 2 | 58,347 | 57,984 | 85 | 0 | 58,432 |
| 10 | 2026-10-29T14:43:39.501Z | 3 | 58,670 | 58,240 | 395 | 0 | 59,065 |
| 11 | 2026-10-29T14:44:09.076Z | 3 | 63,704 | 58,880 | 555 | 229 | 64,259 |
| 12 | 2026-10-29T14:44:45.655Z | 3 | 64,287 | 64,128 | 42 | 9 | 64,329 |
| 13 | 2026-10-29T14:44:50.680Z | 3 | 64,357 | 64,128 | 77 | 44 | 64,434 |
| 14 | 2026-10-29T14:45:01.548Z | 3 | 73,391 | 64,256 | 520 | 51 | 73,911 |
| 15 | 2026-10-29T14:45:36.272Z | 3 | 73,970 | 73,728 | 55 | 22 | 74,025 |
| 16 | 2026-10-29T14:45:41.050Z | 3 | 74,053 | 73,856 | 66 | 33 | 74,119 |
| 17 | 2026-10-29T14:46:36.336Z | 3 | 82,444 | 73,984 | 1,780 | 944 | 84,224 |
| 18 | 2026-10-29T14:47:51.130Z | 3 | 88,237 | 84,096 | 1,717 | 1,552 | 89,954 |
| 19 | 2026-10-29T14:51:42.706Z | 3 | 94,862 | 89,728 | 8,066 | 2,588 | 102,928 |

## What was used

Recorded provider: openai. Recorded model(s): gpt-6.1-sol. Host source: vscode; originator: codex-tui; CLI version: 0.165.0. Reasoning effort in the recorded user turns: xhigh.

| Top-level tool | Recorded invocations | Argument characters | Result characters |
| --- | --- | --- | --- |
| exec | 11 | 33,599 | 146,009 |
| wait | 4 | 220 | 62,169 |
| js | 2 | 215 | 36,694 |

`exec` and `wait` are the functions orchestration tools. `js` is the Computer Use interface. Nested tool call sites found in recorded orchestration code: `exec_command`: 17, `write_stdin`: 1. These nested calls are not additional model requests. Full per-call counts and result sizes are in JSON under `session_inspection.tool_calls`.

The two UI calls inventoried available surfaces and attempted to select Chrome. Computer Use stopped because it could not determine the current browser URL confidently. No successful report rendering was observed. Commands used local Python, PowerShell file operations, ripgrep and a Git status read. No recorded web search, provider quota API capture, explicit collaboration-agent spawn, package installation, configuration change or scheduled collection occurred in this root conversation.

Loaded skill bodies: `agent-usage-analytics`, `computer-use`. Skill invocation detections are inferred from file reads; the standard skill-size table is a combined output proxy when the same command also reads references. It is not an exact token count for the skill body.

### Explicit resource reads

This list comes from literal `Get-Content -LiteralPath` and Python script paths in recorded commands. It does not claim to capture every import, variable-based file read or filesystem access.

| Resource | Recorded read/run commands |
| --- | --- |
| C:\Users\labuser\.codex\plugins\cache\openai-bundled\computer-use\26.1021.31345\docs\confirmations.md | 1 |
| C:\Users\labuser\.codex\plugins\cache\openai-bundled\computer-use\26.1021.31345\docs\guidance.md | 1 |
| C:\Users\labuser\.codex\plugins\cache\openai-bundled\computer-use\26.1021.31345\skills\computer-use\SKILL.md | 1 |
| C:\Users\labuser\AppData\Local\Temp\codex-usage-analytics\2026-10-29-all-sessions\report.md | 1 |
| C:\Users\labuser\Dev\jftp\.agents\skills\agent-usage-analytics\SKILL.md | 2 |
| C:\Users\labuser\Dev\jftp\.agents\skills\agent-usage-analytics\assets\pricing.json | 1 |
| C:\Users\labuser\Dev\jftp\.agents\skills\agent-usage-analytics\references\collection-and-quotas.md | 1 |
| C:\Users\labuser\Dev\jftp\.agents\skills\agent-usage-analytics\references\schema-and-pricing.md | 2 |
| C:\Users\labuser\Dev\jftp\.agents\skills\agent-usage-analytics\references\sources.md | 2 |
| C:\Users\labuser\Dev\jftp\.agents\skills\agent-usage-analytics\scripts\agent_usage.py | 2 |
| C:\Users\labuser\Dev\jftp\.agents\skills\agent-usage-analytics\scripts\usage_collectors.py | 3 |
| C:\Users\labuser\Dev\jftp\.agents\skills\agent-usage-analytics\scripts\usage_common.py | 1 |
| C:\Users\labuser\Dev\jftp\.agents\skills\agent-usage-analytics\scripts\usage_render.py | 1 |
| C:\Users\labuser\Dev\jftp\AGENTS.md | 1 |
| C:\Users\labuser\Dev\jftp\.agents\skills\agent-usage-analytics\scripts\agent_usage.py (Python entrypoint) | 5 |

Other observed inputs were this session rollout, usage data from known Codex session directories during the earlier all-session scan, environment aliases for the current session, file listings, hashes and the generated report files. Python imports used the analytics collector/accounting/renderer modules and their dashboard template. Repository source maps and the code graph guide were supplied as navigation references in AGENTS.md; their contents were not read for this analytics task. JFTP application source and pom.xml were not examined.

## What is currently retained

The latest measured request used 94,862 input tokens and generated 8,066 output tokens. The native context anchor is **102,928 / 475,000 tokens (21.67%)**, using the collector's input-plus-output basis; computed free space is 372,072. It is an observation at 2026-10-29T14:51:42.706Z, not a live reading of an in-flight next prompt. Recorded compactions: 0.

The anchored categories below are estimated from visible recorded characters, not measured tokenization. Skill bodies and reference documents read through tools are included under tool results. The advertised skill catalog is a separate category; advertising does not mean loading every skill.

| Context category | Recorded characters | Estimated tokens | Estimate range |
| --- | --- | --- | --- |
| assistant_messages | 2,917 | 729 | 486–1,458 |
| environment | 921 | 230 | 154–460 |
| instructions | 27,530 | 6,882 | 4,588–13,765 |
| skill_catalog | 14,437 | 3,609 | 2,406–7,218 |
| tool_arguments | 34,034 | 8,508 | 5,672–17,017 |
| tool_results | 244,872 | 61,218 | 40,812–122,436 |
| user_messages | 7,393 | 1,848 | 1,232–3,696 |

Visible category estimates total 83,024 tokens, leaving 19,904 tokens as an unattributed residual. Hidden runtime content, tool schemas, non-text representations and estimation error cannot be split exactly using this log. It would be incorrect to assign that residual to one particular tool or document.

At capture, the retained visible inventory contains the earlier request to analyze all local sessions and its aggregate report output; the instruction to copy the three reports and its verification; the current request to inspect this session; user-supplied skill text; user-provided AGENTS.md revisions and environment context; assistant progress/final messages; recorded tool arguments and results; skill/reference/script excerpts; UI API documentation, app inventory and the UI stop result. The latest supplied AGENTS.md replaces earlier instructions semantically; earlier versions can remain as historical text. Tool output from the broad all-session Markdown read is a substantial contributor. Raw reasoning content is not exported; only its reported token count and the number of recorded reasoning items are listed.

The recorded advertised catalog contains 38 skill entries; only the loaded bodies listed above were detected. Catalog names: `imagegen`, `openai-docs`, `skill-creator`, `skill-installer`, `agent-usage-analytics`, `cloudflare-safe`, `create-design-system`, `legacy-codebase-workflows`, `repository-security-audit`, `repository-use-case-advisor`, `respond-to-code-review`, `web-performance-audit`, `write-adr`, `write-agents-md`, `write-prd`, `computer-use:computer-use`, `documents:documents`, `google-drive:google-docs`, `google-drive:google-drive`, `google-drive:google-drive-comments`, `google-drive:google-sheets`, `google-drive:google-slides`, `pages:maintain-space`, `pages:manage-schedules`, `pages:organize-space`, `pages:write-page`, `pdf:pdf`, `plugin-management:plugin-management`, `presentations:Presentations`, `sites:sites`, `sites:sites-remix`, `spreadsheets:Spreadsheets`, `spreadsheets:excel-live-control`, `template-creator:template-creator`, `visualize:visualize`, `work-pets:create-pet`, `work-pets:pets`, `work-pets:update-pet`.

### Structural inventory

| Response item type | Count |
| --- | --- |
| additional_tools | 1 |
| custom_tool_call | 11 |
| custom_tool_call_output | 10 |
| function_call | 6 |
| function_call_output | 6 |
| message | 19 |
| reasoning | 22 |

| Message role | Count |
| --- | --- |
| assistant | 9 |
| developer | 4 |
| user | 6 |

Event mirrors such as agent-message events are not counted again as new messages or requests. JSON also provides event-type counts and the retained visible inventory at capture, which can include items after the latest request anchor.

## Quota observations and measurement limits

| Bucket | Latest recorded use | Observed UTC | Resets UTC |
| --- | --- | --- | --- |
| codex:primary | 55.0% | 2026-10-29T14:48:03.428Z | 2026-10-29T16:04:09.000Z |
| codex:secondary | 25.0% | 2026-10-29T14:48:03.428Z | 2026-11-04T13:32:27.000Z |

Quota percentages describe provider/account windows, not just this conversation. They are retained observations, not a fresh account query, and cannot be converted into a fixed token allowance. No complete device/cloud coverage was declared, so calibration stays unavailable.

Exact per-element token costs are unavailable: native counters measure whole model requests. Character counts and category estimates describe visible content; they do not prove the causal cost of a specific tool, file, skill or message. Hidden platform instructions and internal reasoning are not reproduced in this report.

This is a frozen snapshot of an active conversation. The capture tool may be listed without a result. Later report-writing results, future requests and the final reply are not included in the request cutoff. Generating an analysis also consumes tokens, shown in the third task phase.

## Supported improvements

1. Read selected report.json fields instead of printing the full cross-session Markdown report; the earlier broad read produced a large recorded tool result.
2. Reuse already loaded skill/reference context where possible; repeated reference reads add retained tool text.
3. For comparisons, retain a timestamped snapshot and compare request-counter deltas; do not compare lifetime totals with context occupancy.

## Reproduce this scope

Pass the exact root rollout as the source to avoid the CLI default that includes child threads:

```powershell
python "C:\Users\labuser\Dev\jftp\.agents\skills\agent-usage-analytics\scripts\agent_usage.py" session --source "codex=C:\Users\labuser\.codex\sessions\2026\10\29\rollout-2026-10-29T15-03-03-01a187f8-a6ed-7103-bcd3-81df033c1959.jsonl" --session "01a187f8-a6ed-7103-bcd3-81df033c1959" --out "C:\Users\labuser\Dev\jftp\docs\session-analyses\session-01a187f8-a6ed-7103-bcd3-81df033c1959"
```

HTML visual verification is unavailable after the earlier Computer Use URL-confidence stop. Local validation checks one-session scope, request sums, embedded JSON consistency, offline dependencies and absence of exported raw transcript content.

---

# Session usage report

- Generated: 2026-10-29 14:51 UTC
- Mode: single session
- Period: Not set to Not set (half-open)
- Calendar timezone: UTC
- Session: 01a187f8-a6ed-7103-bcd3-81df033c1959
- Share mode: off

> Costs are API-equivalent estimates from a versioned public rate card. They are not an invoice and not subscription payments.

## Current context

Context describes one session at its latest measured request. It is separate from the processed historical totals elsewhere in this report, which count every request.

### Session 01a187f8-a6ed-7103-bcd3-81df033c1959

- Agent and model: codex, gpt-6.1-sol
- Observed at: 2026-10-29 14:51 UTC
- Basis: input + output of the latest measured request
- Measured context: 102,928 of 475,000 tokens (21.7% computed occupancy)
- Free (window minus measured): 372,072 tokens
- Compactions: 0; retention: recorded-visible-history; quality: reported
- Visible content estimate: 83,024 tokens (range 55,350 to 166,050); method: visible characters / 4.0; range characters / 6 to / 2
- Unattributed residual (measured minus visible estimate): 19,904 tokens. It covers hidden system prompts, tool schemas, non-text payloads and estimation error.

| Category (estimated) | Characters | Estimated tokens | Range |
| --- | ---: | ---: | ---: |
| tool\_results | 244,872 | 61,218 | 40,812 to 122,436 |
| tool\_arguments | 34,034 | 8,508 | 5,672 to 17,017 |
| instructions | 27,530 | 6,882 | 4,588 to 13,765 |
| skill\_catalog | 14,437 | 3,609 | 2,406 to 7,218 |
| user\_messages | 7,393 | 1,848 | 1,232 to 3,696 |
| assistant\_messages | 2,917 | 729 | 486 to 1,458 |
| environment | 921 | 230 | 154 to 460 |

## Lifetime usage of this session

| Metric | Value |
| --- | ---: |
| Requests | 19 |
| Sessions | 1 |
| Input tokens (includes cache reads and writes) | 1,118,433 |
| Cache read tokens | 1,022,464 |
| Cache write tokens | 0 |
| Output tokens (includes reasoning) | 16,769 |
| Reasoning tokens (subset of output, not added again) | 6,904 |
| Total processed tokens (input + output) | 1,135,202 |
| API-equivalent cost estimate (USD) | $0.46 to $0.84 |
| Priced requests | 19 |
| Unpriced requests | 0 |
| Requests missing usage counts | 0 |

## Insights

1. **Largest observed tool content**
   - Evidence: exec: 146009 result characters across 11 calls.
   - Suggested action: Narrow repeated reads or bound result sizes. This measures visible text, not the tool's full causal token cost.

Recommendations are suggestions only; nothing in your configuration was changed.

## Tools and skills

Ranked by visible content size and invocation counts. These are content proxies, not exact causal charges: a model request is billed for its whole prompt, not per tool.

### Tools

| Name | Agent | Calls | Argument characters | Result characters | Estimated visible tokens |
| --- | --- | ---: | ---: | ---: | ---: |
| exec | codex | 11 | 33,599 | 146,009 | 44,902 |
| wait | codex | 4 | 220 | 62,169 | 15,597 |
| js | codex | 2 | 215 | 36,694 | 9,227 |

### Skills

| Name | Agent | Calls | Argument characters | Result characters | Estimated visible tokens |
| --- | --- | ---: | ---: | ---: | ---: |
| agent-usage-analytics | codex | 2 | Unknown | 41,360 | 10,340 |
| computer-use | codex | 1 | Unknown | 9,876 | 2,469 |

## Daily usage

| Date | Requests | Input | Cache read | Cache write | Output | Reasoning (in output) | Total | API-equivalent cost |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| 2026-10-29 | 19 | 1,118,433 | 1,022,464 | 0 | 16,769 | 6,904 | 1,135,202 | $0.46 to $0.84 |

## Monthly usage

| Month | Requests | Input | Cache read | Cache write | Output | Reasoning (in output) | Total | API-equivalent cost |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| 2026-10 | 19 | 1,118,433 | 1,022,464 | 0 | 16,769 | 6,904 | 1,135,202 | $0.46 to $0.84 |

## Models

| Model | Requests | Input | Cache read | Cache write | Output | Reasoning (in output) | Total | API-equivalent cost |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| gpt-6.1-sol | 19 | 1,118,433 | 1,022,464 | 0 | 16,769 | 6,904 | 1,135,202 | $0.46 to $0.84 |

## Sessions

| Session | Agent | Model | Requests | Input | Cache read | Cache write | Output | Reasoning (in output) | Total | API-equivalent cost |
| --- | --- | --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| codex:01a187f8-a6ed-7103-bcd3-81df033c1959 | codex | gpt-6.1-sol | 19 | 1,118,433 | 1,022,464 | 0 | 16,769 | 6,904 | 1,135,202 | $0.46 to $0.84 |

## Task categories

| Task | Requests | Input | Cache read | Cache write | Output | Reasoning (in output) | Total | API-equivalent cost |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| unclassified | 19 | 1,118,433 | 1,022,464 | 0 | 16,769 | 6,904 | 1,135,202 | $0.46 to $0.84 |

## Provider and model attribution

Harness labels can hide GLM or other proxy routes. Reported models are unverified reference comparisons unless their route is recorded or explicitly confirmed.

| Agent | Provider | Reported label | Effective model | Basis | Requests | API-equivalent cost |
| --- | --- | --- | --- | --- | ---: | ---: |
| codex | openai | gpt-6.1-sol | gpt-6.1-sol | recorded | 19 | $0.46 to $0.84 |

## Quotas

Provider quota windows, percentages and resets are shown as observed. They are separate from local token totals and are not converted into tokens.

| Agent | Provider | Account | Bucket | Window | Resets at | Used | Observed at |
| --- | --- | --- | --- | --- | --- | ---: | --- |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 44.0% | 2026-10-29 14:04 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 44.0% | 2026-10-29 14:05 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 44.0% | 2026-10-29 14:05 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 44.0% | 2026-10-29 14:05 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 45.0% | 2026-10-29 14:06 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 46.0% | 2026-10-29 14:09 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 49.0% | 2026-10-29 14:10 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 52.0% | 2026-10-29 14:19 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 52.0% | 2026-10-29 14:19 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 53.0% | 2026-10-29 14:43 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 53.0% | 2026-10-29 14:44 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 54.0% | 2026-10-29 14:44 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 54.0% | 2026-10-29 14:44 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 54.0% | 2026-10-29 14:45 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 54.0% | 2026-10-29 14:45 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 54.0% | 2026-10-29 14:45 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 54.0% | 2026-10-29 14:46 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 55.0% | 2026-10-29 14:48 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 24.0% | 2026-10-29 14:04 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 24.0% | 2026-10-29 14:05 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 24.0% | 2026-10-29 14:05 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 24.0% | 2026-10-29 14:05 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 24.0% | 2026-10-29 14:06 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 24.0% | 2026-10-29 14:09 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 25.0% | 2026-10-29 14:10 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 25.0% | 2026-10-29 14:19 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 25.0% | 2026-10-29 14:19 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 25.0% | 2026-10-29 14:43 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 25.0% | 2026-10-29 14:44 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 25.0% | 2026-10-29 14:44 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 25.0% | 2026-10-29 14:44 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 25.0% | 2026-10-29 14:45 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 25.0% | 2026-10-29 14:45 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 25.0% | 2026-10-29 14:45 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 25.0% | 2026-10-29 14:46 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 25.0% | 2026-10-29 14:48 UTC |

### Descriptive quota estimates

| Agent | Bucket | Status | Reason | Samples | Tokens per point | API-equivalent cost per point |
| --- | --- | --- | --- | ---: | ---: | ---: |
| codex | codex:primary | unavailable | Declare complete device/cloud coverage to attempt empirical calibration. | 18 | Unknown | Unknown |
| codex | codex:secondary | unavailable | Declare complete device/cloud coverage to attempt empirical calibration. | 14 | Unknown | Unknown |
| codex | codex:secondary | unavailable | Declare complete device/cloud coverage to attempt empirical calibration. | 4 | Unknown | Unknown |

Estimates describe compatible observations only. The provider's quota formula is unknown; they are not an allowance or a forecast.

### Local activity windows

Local activity measured as of (2026-10-29 14:51 UTC). These rolling windows are not provider quota periods, and the 7-day window can start before the report period.

| Window | Requests | Total tokens | Requests missing usage |
| --- | ---: | ---: | ---: |
| 5 hours | 19 | 1,135,202 | 0 |
| 7 days | 19 | 1,135,202 | 0 |

## Coverage

| Source | Agent | Device | Origin | Contract | Files seen | Files parsed | Rows seen | Malformed rows |
| --- | --- | --- | --- | --- | ---: | ---: | ---: | ---: |
| 39bf2d530052 | codex | local | local | observed-local | 1 | 1 | 178 | 0 |

Data from other devices, provider-hosted tasks and deleted histories may be absent. Local totals are not the complete account history.

### Warnings

No warnings.

## Pricing

> Costs are API-equivalent estimates from a versioned public rate card. They are not an invoice and not subscription payments.

- Rate card version: 2026-10-17.2
- Verified at: 2026-10-17
- Source: openai: https://developers.openai.com/api/docs/pricing; anthropic: https://platform.claude.com/docs/en/about-claude/pricing; zai: https://docs.z.ai/guides/overview/pricing

Requests are priced individually before aggregation. A range appears when a billing dimension such as cache-write duration or prompt tier is unknown. Unknown models stay unpriced.

## Methodology

- Input tokens include cache reads and cache writes; ordinary input is input minus both.
- Output tokens include reasoning. Reasoning is shown as a subset and never added again.
- Total processed tokens are input plus output, summed over every request in scope. They are historical work, not the current context size.
- Missing values are shown as Unknown, never as zero. Totals are known lower bounds when some requests lack counts.
- Repeated snapshots and overlapping sources are reconciled before aggregation, so the same activity is counted once.
- Context is anchored to the latest measured request. Category sizes are character-based estimates of recorded visible content, not provider tokenization. The unattributed residual is kept separate and categories are never rescaled to fit the measurement.
- Tool and skill figures are visible content sizes and invocation counts, not causal charges.
- Quota figures are provider observations with their own windows and resets.
- Context estimation method: visible characters / 4.0; range characters / 6 to / 2.
