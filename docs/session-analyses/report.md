# Agent usage report

- Generated: 2026-10-29 14:05 UTC
- Mode: historical usage
- Period: Not set to Not set (half-open)
- Calendar timezone: UTC
- Share mode: off

> Costs are API-equivalent estimates from a versioned public rate card. They are not an invoice and not subscription payments.

## Summary

| Metric | Value |
| --- | ---: |
| Requests | 597 |
| Sessions | 19 |
| Input tokens (includes cache reads and writes) | 69,975,366 |
| Cache read tokens | 67,618,432 |
| Cache write tokens | 0 |
| Output tokens (includes reasoning) | 362,073 |
| Reasoning tokens (subset of output, not added again) | 139,889 |
| Total processed tokens (input + output) | 70,337,439 |
| API-equivalent cost estimate (USD) | $11.61 to $21.50 |
| Priced requests | 425 |
| Unpriced requests | 172 |
| Requests missing usage counts | 0 |

Selected source history has 1 accounting-gap observations. Excluded or unattributable activity is not zero usage; gaps may extend outside the selected period.

The cost covers priced requests only: 172 requests are unpriced.

## Insights

1. **Largest observed tool content**
   - Evidence: exec: 2782290 result characters across 294 calls.
   - Suggested action: Narrow repeated reads or bound result sizes. This measures visible text, not the tool's full causal token cost.
2. **Pricing coverage is incomplete**
   - Evidence: 172 requests have unknown models or billing dimensions.
   - Suggested action: Supply a verified rate card or retain these requests as unpriced; the displayed cost covers only priced work.
3. **Compaction affects context interpretation**
   - Evidence: 3 recorded compactions in selected sessions.
   - Suggested action: Use the latest measured request and retained-history estimates; lifetime usage is a separate total.

Recommendations are suggestions only; nothing in your configuration was changed.

## Daily usage

| Date | Requests | Input | Cache read | Cache write | Output | Reasoning (in output) | Total | API-equivalent cost |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| 2026-10-28 | 326 | 39,879,069 | 38,640,128 | 0 | 196,769 | 75,474 | 40,075,838 | $5.78 to $10.64 |
| 2026-10-29 | 271 | 30,096,297 | 28,978,304 | 0 | 165,304 | 64,415 | 30,261,601 | $5.82 to $10.86 |

## Monthly usage

| Month | Requests | Input | Cache read | Cache write | Output | Reasoning (in output) | Total | API-equivalent cost |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| 2026-10 | 597 | 69,975,366 | 67,618,432 | 0 | 362,073 | 139,889 | 70,337,439 | $11.61 to $21.50 |

## Models

| Model | Requests | Input | Cache read | Cache write | Output | Reasoning (in output) | Total | API-equivalent cost |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| gpt-6.1-sol | 419 | 54,990,163 | 53,580,928 | 0 | 342,460 | 131,986 | 55,332,623 | $11.60 to $21.49 |
| codex-auto-review | 172 | 14,820,311 | 13,902,208 | 0 | 18,246 | 7,531 | 14,838,557 | $0.00 |
| gpt-6-luna | 6 | 164,892 | 135,296 | 0 | 1,367 | 372 | 166,259 | $0.0050 to $0.0097 |

## Sessions

| Session | Agent | Model | Requests | Input | Cache read | Cache write | Output | Reasoning (in output) | Total | API-equivalent cost |
| --- | --- | --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| codex:01a182b5-2021-7b03-91a3-76483a7c9ade | codex | gpt-6.1-sol | 134 | 25,680,662 | 25,260,032 | 0 | 171,554 | 66,406 | 25,852,216 | $5.08 to $9.31 |
| codex:01a187b8-7f7f-7183-8185-b9cb82257e8d | codex | gpt-6.1-sol | 82 | 14,062,088 | 13,624,960 | 0 | 76,420 | 32,003 | 14,138,508 | $3.00 to $5.62 |
| codex:01a182bf-5214-7292-8379-eadf65591ea6 | codex | codex-auto-review | 90 | 10,540,578 | 10,081,280 | 0 | 8,968 | 3,282 | 10,549,546 | $0.00 |
| codex:01a186bf-626c-7751-b9a3-f47df6375329 | codex | gpt-6.1-sol | 71 | 9,004,731 | 8,795,136 | 0 | 54,904 | 22,445 | 9,059,635 | $1.85 to $3.42 |
| codex:01a18774-75e3-7630-a953-65baf5bb7cb4 | codex | gpt-6.1-sol | 49 | 3,555,189 | 3,463,936 | 0 | 22,731 | 5,187 | 3,577,920 | $0.76 to $1.40 |
| codex:01a187b8-85db-7023-a9e5-9b31e09804a8 | codex | codex-auto-review | 36 | 2,468,170 | 2,296,320 | 0 | 4,289 | 2,034 | 2,472,459 | $0.00 |
| codex:01a18300-7652-73b3-bff9-c2d0d9b4efb3 | codex | mixed models | 50 | 1,858,623 | 1,708,288 | 0 | 11,787 | 4,555 | 1,870,410 | $0.51 to $0.96 |
| codex:01a184ac-7c69-76d2-920d-0694623bd835 | codex | codex-auto-review | 9 | 660,191 | 630,528 | 0 | 795 | 230 | 660,986 | $0.00 |
| codex:01a18499-a438-76a2-88b7-046937093561 | codex | codex-auto-review | 12 | 468,323 | 355,200 | 0 | 1,100 | 403 | 469,423 | $0.00 |
| codex:01a18279-0fc7-77b3-afa3-6d5758439ae1 | codex | gpt-6.1-sol | 20 | 383,112 | 357,632 | 0 | 1,147 | 239 | 384,259 | $0.10 to $0.19 |
| codex:01a18774-7e32-7153-9496-3665839dda98 | codex | codex-auto-review | 12 | 360,708 | 314,368 | 0 | 1,274 | 549 | 361,982 | $0.00 |
| codex:01a18737-8343-7453-97ce-7dbfd3beadb2 | codex | gpt-6.1-sol | 9 | 291,136 | 231,296 | 0 | 3,184 | 992 | 294,320 | $0.17 to $0.33 |
| codex:01a186f4-f928-7b82-b412-ce7754d253f7 | codex | codex-auto-review | 8 | 268,668 | 188,800 | 0 | 1,053 | 574 | 269,721 | $0.00 |
| codex:01a18305-0554-77d1-a0f8-b3c8b586c808 | codex | gpt-6.1-sol | 7 | 248,899 | 217,600 | 0 | 876 | 55 | 249,775 | $0.09 to $0.18 |
| codex:01a187f8-a6ed-7103-bcd3-81df033c1959 | codex | gpt-6.1-sol | 3 | 70,615 | 57,344 | 0 | 1,224 | 476 | 71,839 | $0.04 to $0.08 |
| codex:01a18305-4834-7de2-8910-147d37213059 | codex | codex-auto-review | 3 | 28,580 | 23,424 | 0 | 425 | 240 | 29,005 | $0.00 |
| codex:01a18737-93cc-7d43-ad38-44c51d39c17a | codex | codex-auto-review | 1 | 14,992 | 6,144 | 0 | 225 | 155 | 15,217 | $0.00 |
| codex:01a182fe-a7f1-7523-908f-4f09e92f6f35 | codex | codex-auto-review | 1 | 10,101 | 6,144 | 0 | 117 | 64 | 10,218 | $0.00 |

## Task categories

| Task | Requests | Input | Cache read | Cache write | Output | Reasoning (in output) | Total | API-equivalent cost |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| unclassified | 597 | 69,975,366 | 67,618,432 | 0 | 362,073 | 139,889 | 70,337,439 | $11.61 to $21.50 |

## Current context

Context describes one session at its latest measured request. It is separate from the processed historical totals elsewhere in this report, which count every request.

### Session 01a187f8-a6ed-7103-bcd3-81df033c1959

- Agent and model: codex, gpt-6.1-sol
- Observed at: 2026-10-29 14:05 UTC
- Basis: input + output of the latest measured request
- Measured context: 27,349 of 475,000 tokens (5.8% computed occupancy)
- Free (window minus measured): 447,651 tokens
- Compactions: 0; retention: recorded-visible-history; quality: reported
- Visible content estimate: 20,467 tokens (range 13,645 to 40,935); method: visible characters / 4.0; range characters / 6 to / 2
- Unattributed residual (measured minus visible estimate): 6,882 tokens. It covers hidden system prompts, tool schemas, non-text payloads and estimation error.

| Category (estimated) | Characters | Estimated tokens | Range |
| --- | ---: | ---: | ---: |
| tool\_results | 30,319 | 7,580 | 5,053 to 15,160 |
| instructions | 26,666 | 6,666 | 4,444 to 13,333 |
| skill\_catalog | 14,437 | 3,609 | 2,406 to 7,218 |
| user\_messages | 7,102 | 1,776 | 1,184 to 3,551 |
| tool\_arguments | 2,274 | 568 | 379 to 1,137 |
| environment | 921 | 230 | 154 to 460 |
| assistant\_messages | 152 | 38 | 25 to 76 |

### Session 01a187b8-85db-7023-a9e5-9b31e09804a8

- Agent and model: codex, codex-auto-review
- Observed at: 2026-10-29 14:03 UTC
- Basis: input + output of the latest measured request
- Measured context: 102,725 of 258,400 tokens (39.8% computed occupancy)
- Free (window minus measured): 155,675 tokens
- Compactions: 0; retention: recorded-visible-history; quality: reported
- Visible content estimate: 94,302 tokens (range 62,868 to 188,604); method: visible characters / 4.0; range characters / 6 to / 2
- Unattributed residual (measured minus visible estimate): 8,423 tokens. It covers hidden system prompts, tool schemas, non-text payloads and estimation error.

| Category (estimated) | Characters | Estimated tokens | Range |
| --- | ---: | ---: | ---: |
| user\_messages | 344,778 | 86,194 | 57,463 to 172,389 |
| instructions | 22,360 | 5,590 | 3,727 to 11,180 |
| assistant\_messages | 9,151 | 2,288 | 1,525 to 4,576 |
| environment | 918 | 230 | 153 to 459 |

### Session 01a187b8-7f7f-7183-8185-b9cb82257e8d

- Agent and model: codex, gpt-6.1-sol
- Observed at: 2026-10-29 14:03 UTC
- Basis: input + output of the latest measured request
- Measured context: 269,400 of 475,000 tokens (56.7% computed occupancy)
- Free (window minus measured): 205,600 tokens
- Compactions: 0; retention: recorded-visible-history; quality: reported
- Visible content estimate: 216,346 tokens (range 144,231 to 432,691); method: visible characters / 4.0; range characters / 6 to / 2
- Unattributed residual (measured minus visible estimate): 53,054 tokens. It covers hidden system prompts, tool schemas, non-text payloads and estimation error.

| Category (estimated) | Characters | Estimated tokens | Range |
| --- | ---: | ---: | ---: |
| tool\_results | 639,925 | 159,981 | 106,654 to 319,962 |
| tool\_arguments | 158,907 | 39,727 | 26,484 to 79,454 |
| instructions | 26,129 | 6,532 | 4,355 to 13,064 |
| user\_messages | 15,190 | 3,798 | 2,532 to 7,595 |
| skill\_catalog | 14,437 | 3,609 | 2,406 to 7,218 |
| assistant\_messages | 9,875 | 2,469 | 1,646 to 4,938 |
| environment | 921 | 230 | 154 to 460 |

### Session 01a1878b-5975-7472-a73a-7176f2885057

- Agent and model: codex, unknown
- Observed at: 2026-10-29 12:03 UTC
- Basis: input + output of the latest measured request
- Measured context: Unknown of Unknown tokens (Unknown computed occupancy)
- Free space: Unknown
- Compactions: 0; retention: recorded-visible-history; quality: unavailable
- Current context: Unknown, because no measured request was recorded for this context.
- Visible content estimate: 8,584 tokens (range 5,723 to 17,169); method: visible characters / 4.0; range characters / 6 to / 2

| Category (estimated) | Characters | Estimated tokens | Range |
| --- | ---: | ---: | ---: |
| instructions | 21,546 | 5,386 | 3,591 to 10,773 |
| user\_messages | 12,791 | 3,198 | 2,132 to 6,396 |

### Session 01a18774-75e3-7630-a953-65baf5bb7cb4

- Agent and model: codex, gpt-6.1-sol
- Observed at: 2026-10-29 12:02 UTC
- Basis: input + output of the latest measured request
- Measured context: 120,429 of 475,000 tokens (25.4% computed occupancy)
- Free (window minus measured): 354,571 tokens
- Compactions: 0; retention: recorded-visible-history; quality: reported
- Visible content estimate: 85,828 tokens (range 57,219 to 171,657); method: visible characters / 4.0; range characters / 6 to / 2
- Unattributed residual (measured minus visible estimate): 34,601 tokens. It covers hidden system prompts, tool schemas, non-text payloads and estimation error.

| Category (estimated) | Characters | Estimated tokens | Range |
| --- | ---: | ---: | ---: |
| tool\_results | 219,435 | 54,859 | 36,572 to 109,718 |
| tool\_arguments | 58,855 | 14,714 | 9,809 to 29,428 |
| instructions | 26,129 | 6,532 | 4,355 to 13,064 |
| user\_messages | 21,353 | 5,338 | 3,559 to 10,676 |
| skill\_catalog | 14,437 | 3,609 | 2,406 to 7,218 |
| assistant\_messages | 2,186 | 546 | 364 to 1,093 |
| environment | 921 | 230 | 154 to 460 |

### Session 01a18774-7e32-7153-9496-3665839dda98

- Agent and model: codex, codex-auto-review
- Observed at: 2026-10-29 12:01 UTC
- Basis: input + output of the latest measured request
- Measured context: 50,725 of 258,400 tokens (19.6% computed occupancy)
- Free (window minus measured): 207,675 tokens
- Compactions: 0; retention: recorded-visible-history; quality: reported
- Visible content estimate: 43,228 tokens (range 28,818 to 86,455); method: visible characters / 4.0; range characters / 6 to / 2
- Unattributed residual (measured minus visible estimate): 7,497 tokens. It covers hidden system prompts, tool schemas, non-text payloads and estimation error.

| Category (estimated) | Characters | Estimated tokens | Range |
| --- | ---: | ---: | ---: |
| user\_messages | 150,253 | 37,563 | 25,042 to 75,126 |
| instructions | 18,779 | 4,695 | 3,130 to 9,390 |
| assistant\_messages | 2,960 | 740 | 493 to 1,480 |
| environment | 918 | 230 | 153 to 459 |

### Session 01a18737-8343-7453-97ce-7dbfd3beadb2

- Agent and model: codex, unknown
- Observed at: 2026-10-29 11:13 UTC
- Basis: input + output of the latest measured request
- Measured context: Unknown of Unknown tokens (Unknown computed occupancy)
- Free space: Unknown
- Compactions: 0; retention: recorded-visible-history; quality: unavailable
- Current context: Unknown, because no measured request was recorded for this context.
- Visible content estimate: 5,386 tokens (range 3,591 to 10,773); method: visible characters / 4.0; range characters / 6 to / 2

| Category (estimated) | Characters | Estimated tokens | Range |
| --- | ---: | ---: | ---: |
| instructions | 21,546 | 5,386 | 3,591 to 10,773 |

### Session 01a18737-93cc-7d43-ad38-44c51d39c17a

- Agent and model: codex, codex-auto-review
- Observed at: 2026-10-29 11:06 UTC
- Basis: input + output of the latest measured request
- Measured context: 15,217 of 258,400 tokens (5.9% computed occupancy)
- Free (window minus measured): 243,183 tokens
- Compactions: 0; retention: recorded-visible-history; quality: reported
- Visible content estimate: 12,593 tokens (range 8,394 to 25,186); method: visible characters / 4.0; range characters / 6 to / 2
- Unattributed residual (measured minus visible estimate): 2,624 tokens. It covers hidden system prompts, tool schemas, non-text payloads and estimation error.

| Category (estimated) | Characters | Estimated tokens | Range |
| --- | ---: | ---: | ---: |
| user\_messages | 30,698 | 7,674 | 5,116 to 15,349 |
| instructions | 18,500 | 4,625 | 3,083 to 9,250 |
| environment | 918 | 230 | 153 to 459 |
| assistant\_messages | 255 | 64 | 42 to 128 |

### Session 01a186bf-626c-7751-b9a3-f47df6375329

- Agent and model: codex, gpt-6.1-sol
- Observed at: 2026-10-29 10:51 UTC
- Basis: input + output of the latest measured request
- Measured context: 267,857 of 475,000 tokens (56.4% computed occupancy)
- Free (window minus measured): 207,143 tokens
- Compactions: 0; retention: recorded-visible-history; quality: reported
- Visible content estimate: 217,824 tokens (range 145,218 to 435,650); method: visible characters / 4.0; range characters / 6 to / 2
- Unattributed residual (measured minus visible estimate): 50,033 tokens. It covers hidden system prompts, tool schemas, non-text payloads and estimation error.

| Category (estimated) | Characters | Estimated tokens | Range |
| --- | ---: | ---: | ---: |
| tool\_results | 712,528 | 178,132 | 118,755 to 356,264 |
| tool\_arguments | 103,330 | 25,832 | 17,222 to 51,665 |
| instructions | 31,348 | 7,837 | 5,225 to 15,674 |
| skill\_catalog | 10,279 | 2,570 | 1,713 to 5,140 |
| assistant\_messages | 9,609 | 2,402 | 1,602 to 4,804 |
| user\_messages | 3,347 | 837 | 558 to 1,674 |
| environment | 858 | 214 | 143 to 429 |

### Session 01a186f4-f928-7b82-b412-ce7754d253f7

- Agent and model: codex, codex-auto-review
- Observed at: 2026-10-29 10:49 UTC
- Basis: input + output of the latest measured request
- Measured context: 56,515 of 258,400 tokens (21.9% computed occupancy)
- Free (window minus measured): 201,885 tokens
- Compactions: 0; retention: recorded-visible-history; quality: reported
- Visible content estimate: 50,283 tokens (range 33,522 to 100,568); method: visible characters / 4.0; range characters / 6 to / 2
- Unattributed residual (measured minus visible estimate): 6,232 tokens. It covers hidden system prompts, tool schemas, non-text payloads and estimation error.

| Category (estimated) | Characters | Estimated tokens | Range |
| --- | ---: | ---: | ---: |
| user\_messages | 179,618 | 44,904 | 29,936 to 89,809 |
| instructions | 18,779 | 4,695 | 3,130 to 9,390 |
| assistant\_messages | 1,882 | 470 | 314 to 941 |
| environment | 855 | 214 | 142 to 428 |

Showing the 10 most recent of 19 context observations; the JSON report has all.

## Tools and skills

Ranked by visible content size and invocation counts. These are content proxies, not exact causal charges: a model request is billed for its whole prompt, not per tool.

### Tools

| Name | Agent | Calls | Argument characters | Result characters | Estimated visible tokens |
| --- | --- | ---: | ---: | ---: | ---: |
| exec | codex | 294 | 677,420 | 2,782,290 | 864,928 |
| wait | codex | 84 | 4,572 | 235,720 | 60,073 |
| js | codex | 4 | 513 | 42,090 | 10,651 |
| request\_user\_input\_async | codex | 7 | 2,324 | 119 | 611 |

### Skills

| Name | Agent | Calls | Argument characters | Result characters | Estimated visible tokens |
| --- | --- | ---: | ---: | ---: | ---: |
| openai-docs | codex | 5 | Unknown | 68,081 | 17,020 |
| legacy-codebase-workflows | codex | 2 | Unknown | 40,146 | 10,036 |
| skill-installer | codex | 1 | Unknown | 32,101 | 8,025 |
| create-design-system | codex | 1 | Unknown | 30,696 | 7,674 |
| agent-usage-analytics | codex | 1 | Unknown | 20,443 | 5,111 |
| computer-use | codex | 3 | Unknown | 16,285 | 4,071 |
| write-agents-md | codex | 1 | Unknown | 8,953 | 2,238 |
| repository-security-audit | codex | 2 | Unknown | Unknown | Unknown |
| visualize | codex | 2 | Unknown | Unknown | Unknown |

## Provider and model attribution

Harness labels can hide GLM or other proxy routes. Reported models are unverified reference comparisons unless their route is recorded or explicitly confirmed.

| Agent | Provider | Reported label | Effective model | Basis | Requests | API-equivalent cost |
| --- | --- | --- | --- | --- | ---: | ---: |
| codex | openai | codex-auto-review | codex-auto-review | recorded | 172 | $0.00 |
| codex | openai | gpt-6-luna | gpt-6-luna | recorded | 6 | $0.0050 to $0.0097 |
| codex | openai | gpt-6.1-sol | gpt-6.1-sol | recorded | 419 | $11.60 to $21.49 |

## Quotas

Provider quota windows, percentages and resets are shown as observed. They are separate from local token totals and are not converted into tokens.

| Agent | Provider | Account | Bucket | Window | Resets at | Used | Observed at |
| --- | --- | --- | --- | --- | --- | ---: | --- |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 0.0% | 2026-10-28 13:32 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 0.0% | 2026-10-28 13:36 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 0.0% | 2026-10-28 13:36 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 0.0% | 2026-10-28 13:36 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 0.0% | 2026-10-28 13:36 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 1.0% | 2026-10-28 13:38 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 1.0% | 2026-10-28 13:38 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 1.0% | 2026-10-28 13:38 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 1.0% | 2026-10-28 13:38 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 1.0% | 2026-10-28 13:40 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 1.0% | 2026-10-28 13:41 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 1.0% | 2026-10-28 13:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 1.0% | 2026-10-28 13:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 2.0% | 2026-10-28 13:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 2.0% | 2026-10-28 13:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 2.0% | 2026-10-28 13:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 2.0% | 2026-10-28 13:43 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 2.0% | 2026-10-28 13:44 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 3.0% | 2026-10-28 14:49 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 3.0% | 2026-10-28 14:49 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 3.0% | 2026-10-28 14:49 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 3.0% | 2026-10-28 14:49 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 3.0% | 2026-10-28 14:49 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 3.0% | 2026-10-28 14:50 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 3.0% | 2026-10-28 14:50 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 3.0% | 2026-10-28 14:50 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 3.0% | 2026-10-28 14:50 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 3.0% | 2026-10-28 14:50 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 3.0% | 2026-10-28 14:51 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 3.0% | 2026-10-28 14:51 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 3.0% | 2026-10-28 14:51 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 3.0% | 2026-10-28 14:52 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 3.0% | 2026-10-28 14:52 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 3.0% | 2026-10-28 14:52 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 3.0% | 2026-10-28 14:52 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 3.0% | 2026-10-28 14:52 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 3.0% | 2026-10-28 14:52 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 3.0% | 2026-10-28 14:52 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:52 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:54 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:54 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:54 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:54 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:54 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:54 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:54 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:54 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:55 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:55 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:55 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:55 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:55 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:56 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:56 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:57 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:57 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:57 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:57 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:57 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:57 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:58 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:58 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:58 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:58 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:58 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:58 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:58 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:58 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:59 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 4.0% | 2026-10-28 14:59 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 5.0% | 2026-10-28 14:59 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 5.0% | 2026-10-28 14:59 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 5.0% | 2026-10-28 14:59 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 5.0% | 2026-10-28 14:59 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 5.0% | 2026-10-28 14:59 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 5.0% | 2026-10-28 14:59 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 5.0% | 2026-10-28 15:12 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 5.0% | 2026-10-28 15:12 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 5.0% | 2026-10-28 15:21 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 5.0% | 2026-10-28 15:21 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 5.0% | 2026-10-28 15:21 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 5.0% | 2026-10-28 15:21 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 5.0% | 2026-10-28 15:23 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 6.0% | 2026-10-28 15:24 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 6.0% | 2026-10-28 15:24 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 6.0% | 2026-10-28 15:29 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 6.0% | 2026-10-28 15:29 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 6.0% | 2026-10-28 15:29 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 6.0% | 2026-10-28 15:30 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 7.0% | 2026-10-28 16:30 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 7.0% | 2026-10-28 16:30 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 7.0% | 2026-10-28 16:30 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 7.0% | 2026-10-28 16:30 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 7.0% | 2026-10-28 16:31 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 8.0% | 2026-10-28 16:31 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 8.0% | 2026-10-28 16:31 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 8.0% | 2026-10-28 16:32 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 9.0% | 2026-10-28 16:33 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 9.0% | 2026-10-28 16:34 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 9.0% | 2026-10-28 16:35 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 10.0% | 2026-10-28 17:19 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 10.0% | 2026-10-28 17:20 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 10.0% | 2026-10-28 17:20 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 10.0% | 2026-10-28 17:20 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 10.0% | 2026-10-28 17:20 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 10.0% | 2026-10-28 17:20 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 11.0% | 2026-10-28 17:21 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 10.0% | 2026-10-28 17:21 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 12.0% | 2026-10-28 17:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 12.0% | 2026-10-28 17:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 11.0% | 2026-10-28 17:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 12.0% | 2026-10-28 17:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 12.0% | 2026-10-28 17:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 12.0% | 2026-10-28 17:23 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 12.0% | 2026-10-28 17:23 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 13.0% | 2026-10-28 17:24 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 13.0% | 2026-10-28 17:24 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 12.0% | 2026-10-28 17:24 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 13.0% | 2026-10-28 17:25 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 13.0% | 2026-10-28 17:25 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 14.0% | 2026-10-28 17:25 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 13.0% | 2026-10-28 17:25 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 14.0% | 2026-10-28 17:26 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 14.0% | 2026-10-28 17:26 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 15.0% | 2026-10-28 17:27 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 14.0% | 2026-10-28 17:27 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 15.0% | 2026-10-28 17:27 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 15.0% | 2026-10-28 17:27 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 15.0% | 2026-10-28 17:27 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 15.0% | 2026-10-28 17:28 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 15.0% | 2026-10-28 17:28 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 15.0% | 2026-10-28 17:28 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 16.0% | 2026-10-28 17:29 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 16.0% | 2026-10-28 17:30 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 16.0% | 2026-10-28 17:30 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 17.0% | 2026-10-28 17:30 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 16.0% | 2026-10-28 17:30 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 17.0% | 2026-10-28 17:31 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 17.0% | 2026-10-28 17:31 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 17.0% | 2026-10-28 17:32 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 17.0% | 2026-10-28 17:32 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 17.0% | 2026-10-28 17:33 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 18.0% | 2026-10-28 17:34 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 18.0% | 2026-10-28 17:34 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 18.0% | 2026-10-28 17:34 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 18.0% | 2026-10-28 17:34 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 19.0% | 2026-10-28 17:35 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 19.0% | 2026-10-28 17:35 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 20.0% | 2026-10-28 17:35 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 19.0% | 2026-10-28 17:36 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 20.0% | 2026-10-28 17:36 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 21.0% | 2026-10-28 17:38 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 20.0% | 2026-10-28 17:38 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 21.0% | 2026-10-28 17:39 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 21.0% | 2026-10-28 17:39 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 18:32 UTC | 21.0% | 2026-10-28 17:40 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 0.0% | 2026-10-28 18:59 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 0.0% | 2026-10-28 18:59 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 1.0% | 2026-10-28 19:00 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 0.0% | 2026-10-28 19:00 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 1.0% | 2026-10-28 19:00 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 2.0% | 2026-10-28 19:01 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 2.0% | 2026-10-28 19:01 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 2.0% | 2026-10-28 19:01 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 1.0% | 2026-10-28 19:02 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 2.0% | 2026-10-28 19:02 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 2.0% | 2026-10-28 19:02 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 3.0% | 2026-10-28 19:03 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 2.0% | 2026-10-28 19:03 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 3.0% | 2026-10-28 19:04 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 3.0% | 2026-10-28 19:04 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 3.0% | 2026-10-28 19:04 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 3.0% | 2026-10-28 19:05 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 4.0% | 2026-10-28 19:06 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 4.0% | 2026-10-28 19:06 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 4.0% | 2026-10-28 19:06 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 5.0% | 2026-10-28 19:07 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 4.0% | 2026-10-28 19:07 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 6.0% | 2026-10-28 19:09 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 6.0% | 2026-10-28 19:09 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 5.0% | 2026-10-28 19:09 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 6.0% | 2026-10-28 19:10 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 7.0% | 2026-10-28 19:21 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 6.0% | 2026-10-28 19:21 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 7.0% | 2026-10-28 19:21 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 7.0% | 2026-10-28 19:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 7.0% | 2026-10-28 19:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 7.0% | 2026-10-28 19:23 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 8.0% | 2026-10-28 19:23 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 8.0% | 2026-10-28 19:23 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 8.0% | 2026-10-28 19:23 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 8.0% | 2026-10-28 19:23 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 8.0% | 2026-10-28 19:23 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 8.0% | 2026-10-28 19:24 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 9.0% | 2026-10-28 19:25 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 9.0% | 2026-10-28 19:25 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 10.0% | 2026-10-28 19:26 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 10.0% | 2026-10-28 19:26 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 9.0% | 2026-10-28 19:26 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 10.0% | 2026-10-28 19:27 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 10.0% | 2026-10-28 19:27 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 10.0% | 2026-10-28 19:27 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 10.0% | 2026-10-28 19:27 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 11.0% | 2026-10-28 19:28 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 10.0% | 2026-10-28 19:28 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 11.0% | 2026-10-28 19:28 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 11.0% | 2026-10-28 19:28 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 11.0% | 2026-10-28 19:28 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 11.0% | 2026-10-28 19:28 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 12.0% | 2026-10-28 19:29 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 12.0% | 2026-10-28 19:30 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 12.0% | 2026-10-28 19:30 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 11.0% | 2026-10-28 19:30 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 13.0% | 2026-10-28 19:31 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 13.0% | 2026-10-28 19:31 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 12.0% | 2026-10-28 19:31 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 13.0% | 2026-10-28 19:31 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 13.0% | 2026-10-28 19:32 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 13.0% | 2026-10-28 19:32 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 13.0% | 2026-10-28 19:32 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 13.0% | 2026-10-28 19:32 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 13.0% | 2026-10-28 19:32 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 14.0% | 2026-10-28 19:32 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 14.0% | 2026-10-28 19:32 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 15.0% | 2026-10-28 19:33 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 15.0% | 2026-10-28 19:33 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 14.0% | 2026-10-28 19:33 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 15.0% | 2026-10-28 19:34 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 15.0% | 2026-10-28 19:34 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 17.0% | 2026-10-28 19:38 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 17.0% | 2026-10-28 19:38 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 17.0% | 2026-10-28 19:38 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 15.0% | 2026-10-28 19:38 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 17.0% | 2026-10-28 19:39 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 17.0% | 2026-10-28 19:39 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 18.0% | 2026-10-28 19:41 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 17.0% | 2026-10-28 19:41 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 18.0% | 2026-10-28 19:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 19.0% | 2026-10-28 19:53 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 18.0% | 2026-10-28 19:53 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 19.0% | 2026-10-28 19:54 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 20.0% | 2026-10-28 20:17 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 20.0% | 2026-10-28 20:17 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 20.0% | 2026-10-28 20:17 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 21.0% | 2026-10-28 20:17 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 21.0% | 2026-10-28 20:17 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 21.0% | 2026-10-28 20:18 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 22.0% | 2026-10-28 20:18 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 21.0% | 2026-10-28 20:18 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 23.0% | 2026-10-28 20:20 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 22.0% | 2026-10-28 20:20 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 24.0% | 2026-10-28 20:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 23.0% | 2026-10-28 20:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 24.0% | 2026-10-28 20:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 24.0% | 2026-10-28 20:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 24.0% | 2026-10-28 20:23 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 26.0% | 2026-10-28 20:24 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 26.0% | 2026-10-28 20:24 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 25.0% | 2026-10-28 20:24 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 27.0% | 2026-10-28 20:25 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 26.0% | 2026-10-28 20:25 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 27.0% | 2026-10-28 20:26 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 28.0% | 2026-10-28 21:05 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 27.0% | 2026-10-28 21:05 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 29.0% | 2026-10-28 21:07 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 28.0% | 2026-10-28 21:07 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 29.0% | 2026-10-28 21:07 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 30.0% | 2026-10-28 21:08 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 29.0% | 2026-10-28 21:08 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 30.0% | 2026-10-28 21:08 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 31.0% | 2026-10-28 21:09 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 31.0% | 2026-10-28 21:10 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 33.0% | 2026-10-28 21:10 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 33.0% | 2026-10-28 21:10 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 32.0% | 2026-10-28 21:10 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 33.0% | 2026-10-28 21:12 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 33.0% | 2026-10-28 21:12 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 34.0% | 2026-10-28 21:13 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 34.0% | 2026-10-28 21:13 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 33.0% | 2026-10-28 21:14 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 34.0% | 2026-10-28 21:14 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 37.0% | 2026-10-28 22:20 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 36.0% | 2026-10-28 22:20 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 37.0% | 2026-10-28 22:20 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 37.0% | 2026-10-28 22:20 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 37.0% | 2026-10-28 22:21 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 37.0% | 2026-10-28 22:21 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 37.0% | 2026-10-28 22:21 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 38.0% | 2026-10-28 22:21 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 39.0% | 2026-10-28 22:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 38.0% | 2026-10-28 22:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 39.0% | 2026-10-28 22:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 39.0% | 2026-10-28 22:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 39.0% | 2026-10-28 22:23 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 41.0% | 2026-10-28 22:26 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 40.0% | 2026-10-28 22:26 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 41.0% | 2026-10-28 22:27 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 41.0% | 2026-10-28 22:27 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 42.0% | 2026-10-28 22:28 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 41.0% | 2026-10-28 22:28 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 42.0% | 2026-10-28 22:29 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 42.0% | 2026-10-28 22:29 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 42.0% | 2026-10-28 22:30 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 43.0% | 2026-10-28 22:36 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 42.0% | 2026-10-28 22:36 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 44.0% | 2026-10-28 22:39 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 43.0% | 2026-10-28 22:39 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 44.0% | 2026-10-28 22:40 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 45.0% | 2026-10-28 22:40 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 45.0% | 2026-10-28 22:41 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 45.0% | 2026-10-28 22:41 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 45.0% | 2026-10-28 22:41 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 45.0% | 2026-10-28 22:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 45.0% | 2026-10-28 22:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 45.0% | 2026-10-28 22:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 45.0% | 2026-10-28 22:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 46.0% | 2026-10-28 22:51 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 46.0% | 2026-10-28 22:51 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 46.0% | 2026-10-28 22:52 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 47.0% | 2026-10-28 22:53 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 46.0% | 2026-10-28 22:53 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 48.0% | 2026-10-28 22:56 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 47.0% | 2026-10-28 22:56 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 49.0% | 2026-10-28 22:56 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 48.0% | 2026-10-28 22:57 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 49.0% | 2026-10-28 22:57 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 49.0% | 2026-10-28 22:57 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 49.0% | 2026-10-28 22:57 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-28 23:59 UTC | 49.0% | 2026-10-28 22:58 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 9.0% | 2026-10-29 08:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 9.0% | 2026-10-29 08:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 9.0% | 2026-10-29 08:23 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 9.0% | 2026-10-29 08:23 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 9.0% | 2026-10-29 08:23 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 9.0% | 2026-10-29 08:24 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 9.0% | 2026-10-29 08:24 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 9.0% | 2026-10-29 08:24 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 9.0% | 2026-10-29 08:26 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 10.0% | 2026-10-29 08:27 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 10.0% | 2026-10-29 08:27 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 10.0% | 2026-10-29 08:27 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 10.0% | 2026-10-29 08:50 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 10.0% | 2026-10-29 08:51 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 10.0% | 2026-10-29 08:51 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 10.0% | 2026-10-29 09:03 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 11.0% | 2026-10-29 09:03 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 11.0% | 2026-10-29 09:03 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 11.0% | 2026-10-29 09:04 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 11.0% | 2026-10-29 09:13 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 12.0% | 2026-10-29 09:13 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 12.0% | 2026-10-29 09:14 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 12.0% | 2026-10-29 09:14 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 12.0% | 2026-10-29 09:14 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 13.0% | 2026-10-29 09:17 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 13.0% | 2026-10-29 09:17 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 13.0% | 2026-10-29 09:19 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 14.0% | 2026-10-29 09:19 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 14.0% | 2026-10-29 09:19 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 14.0% | 2026-10-29 09:19 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 14.0% | 2026-10-29 09:20 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 15.0% | 2026-10-29 09:21 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 15.0% | 2026-10-29 09:21 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 15.0% | 2026-10-29 09:21 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 15.0% | 2026-10-29 09:21 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 16.0% | 2026-10-29 09:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 15.0% | 2026-10-29 09:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 16.0% | 2026-10-29 09:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 16.0% | 2026-10-29 09:22 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 16.0% | 2026-10-29 09:23 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 16.0% | 2026-10-29 09:23 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 16.0% | 2026-10-29 09:23 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 16.0% | 2026-10-29 09:37 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 17.0% | 2026-10-29 09:38 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 17.0% | 2026-10-29 09:39 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 18.0% | 2026-10-29 09:40 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 19.0% | 2026-10-29 09:40 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 19.0% | 2026-10-29 09:40 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 19.0% | 2026-10-29 09:41 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 19.0% | 2026-10-29 09:41 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 19.0% | 2026-10-29 09:41 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 19.0% | 2026-10-29 09:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 20.0% | 2026-10-29 09:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 20.0% | 2026-10-29 09:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 20.0% | 2026-10-29 09:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 20.0% | 2026-10-29 09:43 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 21.0% | 2026-10-29 09:43 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 20.0% | 2026-10-29 09:43 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 21.0% | 2026-10-29 09:43 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 21.0% | 2026-10-29 09:43 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 21.0% | 2026-10-29 09:55 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 22.0% | 2026-10-29 09:55 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 22.0% | 2026-10-29 09:56 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 22.0% | 2026-10-29 09:56 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 22.0% | 2026-10-29 10:39 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 23.0% | 2026-10-29 10:40 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 23.0% | 2026-10-29 10:40 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 24.0% | 2026-10-29 10:40 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 23.0% | 2026-10-29 10:40 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 24.0% | 2026-10-29 10:41 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 24.0% | 2026-10-29 10:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 25.0% | 2026-10-29 10:43 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 26.0% | 2026-10-29 10:46 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 27.0% | 2026-10-29 10:48 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 28.0% | 2026-10-29 10:49 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 29.0% | 2026-10-29 10:49 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 28.0% | 2026-10-29 10:50 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 29.0% | 2026-10-29 10:50 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 29.0% | 2026-10-29 10:51 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 11:01 UTC | 30.0% | 2026-10-29 10:51 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 0.0% | 2026-10-29 11:04 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 0.0% | 2026-10-29 11:04 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 1.0% | 2026-10-29 11:05 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 1.0% | 2026-10-29 11:05 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 1.0% | 2026-10-29 11:05 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 2.0% | 2026-10-29 11:06 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 2.0% | 2026-10-29 11:06 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 2.0% | 2026-10-29 11:06 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 2.0% | 2026-10-29 11:07 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 2.0% | 2026-10-29 11:07 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 2.0% | 2026-10-29 11:12 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 3.0% | 2026-10-29 11:41 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 3.0% | 2026-10-29 11:41 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 3.0% | 2026-10-29 11:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 3.0% | 2026-10-29 11:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 3.0% | 2026-10-29 11:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 4.0% | 2026-10-29 11:43 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 4.0% | 2026-10-29 11:43 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 4.0% | 2026-10-29 11:43 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 4.0% | 2026-10-29 11:44 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 4.0% | 2026-10-29 11:44 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 4.0% | 2026-10-29 11:44 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 4.0% | 2026-10-29 11:45 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 4.0% | 2026-10-29 11:45 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 4.0% | 2026-10-29 11:45 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 4.0% | 2026-10-29 11:45 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 4.0% | 2026-10-29 11:46 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 4.0% | 2026-10-29 11:46 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 4.0% | 2026-10-29 11:46 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 5.0% | 2026-10-29 11:47 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 5.0% | 2026-10-29 11:47 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 5.0% | 2026-10-29 11:48 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 5.0% | 2026-10-29 11:48 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 5.0% | 2026-10-29 11:48 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 5.0% | 2026-10-29 11:48 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 5.0% | 2026-10-29 11:48 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 5.0% | 2026-10-29 11:48 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 5.0% | 2026-10-29 11:49 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 5.0% | 2026-10-29 11:49 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 5.0% | 2026-10-29 11:49 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 6.0% | 2026-10-29 11:49 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 6.0% | 2026-10-29 11:49 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 6.0% | 2026-10-29 11:50 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 6.0% | 2026-10-29 11:50 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 7.0% | 2026-10-29 11:50 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 6.0% | 2026-10-29 11:50 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 7.0% | 2026-10-29 11:51 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 7.0% | 2026-10-29 11:51 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 7.0% | 2026-10-29 11:52 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 7.0% | 2026-10-29 11:52 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 7.0% | 2026-10-29 11:53 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 8.0% | 2026-10-29 11:53 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 8.0% | 2026-10-29 11:54 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 8.0% | 2026-10-29 11:54 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 9.0% | 2026-10-29 11:55 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 8.0% | 2026-10-29 11:55 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 9.0% | 2026-10-29 11:56 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 9.0% | 2026-10-29 11:57 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 10.0% | 2026-10-29 11:59 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 10.0% | 2026-10-29 12:00 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 11.0% | 2026-10-29 12:00 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 11.0% | 2026-10-29 12:00 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 11.0% | 2026-10-29 12:00 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 11.0% | 2026-10-29 12:00 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 11.0% | 2026-10-29 12:00 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 11.0% | 2026-10-29 12:01 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 11.0% | 2026-10-29 12:01 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 11.0% | 2026-10-29 12:01 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 11.0% | 2026-10-29 12:01 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 11.0% | 2026-10-29 12:01 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 11.0% | 2026-10-29 12:02 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 11.0% | 2026-10-29 12:02 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 11.0% | 2026-10-29 12:59 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 12.0% | 2026-10-29 13:00 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 12.0% | 2026-10-29 13:00 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 13.0% | 2026-10-29 13:00 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 13.0% | 2026-10-29 13:01 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 13.0% | 2026-10-29 13:01 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 13.0% | 2026-10-29 13:02 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 13.0% | 2026-10-29 13:02 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 14.0% | 2026-10-29 13:02 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 14.0% | 2026-10-29 13:02 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 14.0% | 2026-10-29 13:03 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 14.0% | 2026-10-29 13:03 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 14.0% | 2026-10-29 13:03 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 15.0% | 2026-10-29 13:03 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 15.0% | 2026-10-29 13:03 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 15.0% | 2026-10-29 13:04 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 15.0% | 2026-10-29 13:04 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 15.0% | 2026-10-29 13:05 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 16.0% | 2026-10-29 13:05 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 16.0% | 2026-10-29 13:08 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 18.0% | 2026-10-29 13:09 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 19.0% | 2026-10-29 13:10 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 19.0% | 2026-10-29 13:11 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 19.0% | 2026-10-29 13:11 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 20.0% | 2026-10-29 13:11 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 20.0% | 2026-10-29 13:12 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 20.0% | 2026-10-29 13:12 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 21.0% | 2026-10-29 13:12 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 21.0% | 2026-10-29 13:12 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 21.0% | 2026-10-29 13:13 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 21.0% | 2026-10-29 13:13 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 21.0% | 2026-10-29 13:14 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 22.0% | 2026-10-29 13:14 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 22.0% | 2026-10-29 13:25 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 22.0% | 2026-10-29 13:26 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 23.0% | 2026-10-29 13:26 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 23.0% | 2026-10-29 13:27 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 24.0% | 2026-10-29 13:27 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 23.0% | 2026-10-29 13:27 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 24.0% | 2026-10-29 13:28 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 24.0% | 2026-10-29 13:28 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 24.0% | 2026-10-29 13:28 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 25.0% | 2026-10-29 13:28 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 24.0% | 2026-10-29 13:28 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 25.0% | 2026-10-29 13:29 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 25.0% | 2026-10-29 13:29 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 25.0% | 2026-10-29 13:29 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 25.0% | 2026-10-29 13:29 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 26.0% | 2026-10-29 13:29 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 25.0% | 2026-10-29 13:30 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 26.0% | 2026-10-29 13:30 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 26.0% | 2026-10-29 13:30 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 26.0% | 2026-10-29 13:31 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 27.0% | 2026-10-29 13:31 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 27.0% | 2026-10-29 13:31 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 27.0% | 2026-10-29 13:31 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 27.0% | 2026-10-29 13:32 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 28.0% | 2026-10-29 13:32 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 27.0% | 2026-10-29 13:32 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 28.0% | 2026-10-29 13:32 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 28.0% | 2026-10-29 13:32 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 28.0% | 2026-10-29 13:32 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 29.0% | 2026-10-29 13:33 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 28.0% | 2026-10-29 13:33 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 29.0% | 2026-10-29 13:33 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 29.0% | 2026-10-29 13:33 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 29.0% | 2026-10-29 13:34 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 29.0% | 2026-10-29 13:34 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 29.0% | 2026-10-29 13:35 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 30.0% | 2026-10-29 13:35 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 29.0% | 2026-10-29 13:35 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 30.0% | 2026-10-29 13:35 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 30.0% | 2026-10-29 13:36 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 30.0% | 2026-10-29 13:36 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 30.0% | 2026-10-29 13:40 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 31.0% | 2026-10-29 13:41 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 32.0% | 2026-10-29 13:41 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 31.0% | 2026-10-29 13:41 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 32.0% | 2026-10-29 13:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 32.0% | 2026-10-29 13:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 32.0% | 2026-10-29 13:42 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 33.0% | 2026-10-29 13:47 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 35.0% | 2026-10-29 13:47 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 35.0% | 2026-10-29 13:48 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 35.0% | 2026-10-29 13:48 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 36.0% | 2026-10-29 13:49 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 35.0% | 2026-10-29 13:49 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 36.0% | 2026-10-29 13:49 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 36.0% | 2026-10-29 13:52 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 37.0% | 2026-10-29 13:52 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 37.0% | 2026-10-29 13:52 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 37.0% | 2026-10-29 13:53 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 37.0% | 2026-10-29 13:53 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 37.0% | 2026-10-29 13:54 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 38.0% | 2026-10-29 13:54 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 39.0% | 2026-10-29 13:55 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 39.0% | 2026-10-29 13:56 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 38.0% | 2026-10-29 13:56 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 39.0% | 2026-10-29 13:56 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 39.0% | 2026-10-29 13:57 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 39.0% | 2026-10-29 13:57 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 39.0% | 2026-10-29 13:57 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 40.0% | 2026-10-29 13:58 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 40.0% | 2026-10-29 13:58 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 40.0% | 2026-10-29 13:58 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 40.0% | 2026-10-29 13:59 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 41.0% | 2026-10-29 13:59 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 42.0% | 2026-10-29 14:00 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 42.0% | 2026-10-29 14:00 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 41.0% | 2026-10-29 14:00 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 42.0% | 2026-10-29 14:01 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 42.0% | 2026-10-29 14:01 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 42.0% | 2026-10-29 14:01 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 43.0% | 2026-10-29 14:02 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 43.0% | 2026-10-29 14:02 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 42.0% | 2026-10-29 14:02 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 43.0% | 2026-10-29 14:02 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 44.0% | 2026-10-29 14:03 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 43.0% | 2026-10-29 14:04 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 44.0% | 2026-10-29 14:04 UTC |
| codex | Unknown | local | codex:primary | 5 h | 2026-10-29 16:04 UTC | 44.0% | 2026-10-29 14:05 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 13:32 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 13:36 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 13:36 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 13:36 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 13:36 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 13:38 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 13:38 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 13:38 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 13:38 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 13:40 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 13:41 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 13:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 13:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 13:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 13:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 13:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 13:43 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 13:44 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 14:49 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 14:49 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 14:49 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 14:49 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 14:49 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 14:50 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 14:50 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 14:50 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 14:50 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 0.0% | 2026-10-28 14:50 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:51 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:51 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:51 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:52 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:52 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:52 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:52 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:52 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:52 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:52 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:52 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:54 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:54 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:54 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:54 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:54 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:54 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:54 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:54 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:55 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:55 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:55 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:55 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:55 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:56 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:56 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:57 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:57 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:57 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:57 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:57 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:57 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:58 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:58 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:58 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:58 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:58 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:58 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:58 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:58 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:59 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:59 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:59 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:59 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:59 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:59 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:59 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 14:59 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 15:12 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 15:12 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 15:21 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 15:21 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 15:21 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 15:21 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 15:23 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 15:24 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 15:24 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 15:29 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 15:29 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 15:29 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 15:30 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 16:30 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 16:30 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 16:30 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 16:30 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 16:31 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 16:31 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 16:31 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 16:32 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 16:33 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 16:34 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 1.0% | 2026-10-28 16:35 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:19 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:20 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:20 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:20 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:20 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:20 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:21 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:21 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:23 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:23 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:24 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:24 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:24 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:25 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:25 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:25 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:25 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:26 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:26 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:27 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:27 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:27 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:27 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:27 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:28 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:28 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:28 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 2.0% | 2026-10-28 17:29 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:30 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:30 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:30 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:30 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:31 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:31 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:32 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:32 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:33 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:34 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:34 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:34 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:34 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:35 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:35 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:35 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:36 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:36 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:38 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:38 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:39 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:39 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 17:40 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 18:59 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 18:59 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:00 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 3.0% | 2026-10-28 19:00 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:00 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:01 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:01 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:01 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:02 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:02 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:02 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:03 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:03 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:04 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:04 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:04 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:05 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:06 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:06 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:06 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:07 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:07 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:09 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:09 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:09 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:10 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:21 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:21 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 4.0% | 2026-10-28 19:21 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:23 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:23 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:23 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:23 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:23 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:23 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:24 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:25 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:25 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:26 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:26 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:26 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:27 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:27 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:27 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:27 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:28 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:28 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:28 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:28 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:28 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:28 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:29 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:30 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:30 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:30 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:31 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:31 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:31 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:31 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:32 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:32 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:32 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:32 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 5.0% | 2026-10-28 19:32 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 6.0% | 2026-10-28 19:32 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 6.0% | 2026-10-28 19:32 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 6.0% | 2026-10-28 19:33 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 6.0% | 2026-10-28 19:33 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 6.0% | 2026-10-28 19:33 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 6.0% | 2026-10-28 19:34 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 6.0% | 2026-10-28 19:34 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 6.0% | 2026-10-28 19:38 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 6.0% | 2026-10-28 19:38 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 6.0% | 2026-10-28 19:38 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 6.0% | 2026-10-28 19:38 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 6.0% | 2026-10-28 19:39 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 6.0% | 2026-10-28 19:39 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 6.0% | 2026-10-28 19:41 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 6.0% | 2026-10-28 19:41 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 6.0% | 2026-10-28 19:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 6.0% | 2026-10-28 19:53 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 6.0% | 2026-10-28 19:53 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 6.0% | 2026-10-28 19:54 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 7.0% | 2026-10-28 20:17 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 6.0% | 2026-10-28 20:17 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 7.0% | 2026-10-28 20:17 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 7.0% | 2026-10-28 20:17 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 7.0% | 2026-10-28 20:17 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 7.0% | 2026-10-28 20:18 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 7.0% | 2026-10-28 20:18 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 7.0% | 2026-10-28 20:18 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 7.0% | 2026-10-28 20:20 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 7.0% | 2026-10-28 20:20 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 7.0% | 2026-10-28 20:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 7.0% | 2026-10-28 20:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 7.0% | 2026-10-28 20:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 7.0% | 2026-10-28 20:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 7.0% | 2026-10-28 20:23 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 7.0% | 2026-10-28 20:24 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 7.0% | 2026-10-28 20:24 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 7.0% | 2026-10-28 20:24 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 8.0% | 2026-10-28 20:25 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 7.0% | 2026-10-28 20:25 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 8.0% | 2026-10-28 20:26 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 8.0% | 2026-10-28 21:05 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 8.0% | 2026-10-28 21:05 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 8.0% | 2026-10-28 21:07 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 8.0% | 2026-10-28 21:07 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 8.0% | 2026-10-28 21:07 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 8.0% | 2026-10-28 21:08 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 8.0% | 2026-10-28 21:08 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 8.0% | 2026-10-28 21:08 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 8.0% | 2026-10-28 21:09 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 8.0% | 2026-10-28 21:10 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 8.0% | 2026-10-28 21:10 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 8.0% | 2026-10-28 21:10 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 8.0% | 2026-10-28 21:10 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 9.0% | 2026-10-28 21:12 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 8.0% | 2026-10-28 21:12 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 9.0% | 2026-10-28 21:13 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 9.0% | 2026-10-28 21:13 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 9.0% | 2026-10-28 21:14 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 9.0% | 2026-10-28 21:14 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 9.0% | 2026-10-28 22:20 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 9.0% | 2026-10-28 22:20 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 9.0% | 2026-10-28 22:20 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 9.0% | 2026-10-28 22:20 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 9.0% | 2026-10-28 22:21 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 9.0% | 2026-10-28 22:21 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 9.0% | 2026-10-28 22:21 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 9.0% | 2026-10-28 22:21 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 9.0% | 2026-10-28 22:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 9.0% | 2026-10-28 22:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 9.0% | 2026-10-28 22:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:23 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:26 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:26 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:27 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:27 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:28 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:28 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:29 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:29 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:30 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:36 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:36 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:39 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:39 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:40 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:40 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:41 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:41 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:41 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 10.0% | 2026-10-28 22:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 11.0% | 2026-10-28 22:51 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 11.0% | 2026-10-28 22:51 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 11.0% | 2026-10-28 22:52 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 11.0% | 2026-10-28 22:53 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 11.0% | 2026-10-28 22:53 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 11.0% | 2026-10-28 22:56 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 11.0% | 2026-10-28 22:56 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 11.0% | 2026-10-28 22:56 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 11.0% | 2026-10-28 22:57 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 11.0% | 2026-10-28 22:57 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 11.0% | 2026-10-28 22:57 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 11.0% | 2026-10-28 22:57 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 11.0% | 2026-10-28 22:58 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 08:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 08:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 08:23 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 08:23 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 08:23 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 08:24 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 08:24 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 08:24 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 08:26 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 08:27 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 08:27 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 08:27 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 08:50 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 08:51 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 08:51 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 09:03 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 09:03 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 09:03 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 09:04 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 09:13 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 09:13 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 09:14 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 09:14 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 09:14 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 09:17 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 09:17 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 09:19 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 09:19 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 09:19 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 09:19 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 09:20 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 14.0% | 2026-10-29 09:21 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:21 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:21 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:21 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:22 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:23 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:23 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:23 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:37 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:38 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:39 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:40 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:40 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:40 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:41 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:41 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:41 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:43 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:43 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:43 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:43 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 15.0% | 2026-10-29 09:43 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 16.0% | 2026-10-29 09:55 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 16.0% | 2026-10-29 09:55 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 16.0% | 2026-10-29 09:56 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 16.0% | 2026-10-29 09:56 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 16.0% | 2026-10-29 10:39 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 16.0% | 2026-10-29 10:40 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 16.0% | 2026-10-29 10:40 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 16.0% | 2026-10-29 10:40 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 16.0% | 2026-10-29 10:40 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 16.0% | 2026-10-29 10:41 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 16.0% | 2026-10-29 10:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 16.0% | 2026-10-29 10:43 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 16.0% | 2026-10-29 10:46 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 16.0% | 2026-10-29 10:48 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 10:49 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 10:49 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 10:50 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 10:50 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 10:51 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 10:51 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 11:04 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 11:04 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 11:05 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 11:05 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 11:05 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 11:06 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 11:06 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 11:06 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 11:07 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 11:07 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 11:12 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 11:41 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 11:41 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 11:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 11:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 11:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 11:43 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 11:43 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:43 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 17.0% | 2026-10-29 11:44 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:44 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:44 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:45 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:45 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:45 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:45 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:46 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:46 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:46 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:47 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:47 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:48 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:48 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:48 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:48 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:48 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:48 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:49 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:49 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:49 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:49 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:49 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:50 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:50 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:50 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:50 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:51 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:51 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:52 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:52 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:53 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:53 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:54 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:54 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:55 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:55 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:56 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:57 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 18.0% | 2026-10-29 11:59 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 12:00 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 12:00 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 12:00 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 12:00 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 12:00 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 12:00 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 12:01 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 12:01 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 12:01 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 12:01 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 12:01 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 12:02 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 12:02 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 12:59 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 13:00 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 13:00 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 13:00 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 13:01 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 13:01 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 13:02 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 13:02 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 13:02 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 13:02 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 13:03 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 13:03 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 13:03 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 13:03 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 13:03 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 13:04 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 13:04 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 13:05 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 13:05 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 19.0% | 2026-10-29 13:08 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 20.0% | 2026-10-29 13:09 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 20.0% | 2026-10-29 13:10 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 20.0% | 2026-10-29 13:11 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 20.0% | 2026-10-29 13:11 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 20.0% | 2026-10-29 13:11 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 20.0% | 2026-10-29 13:12 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 20.0% | 2026-10-29 13:12 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 20.0% | 2026-10-29 13:12 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 20.0% | 2026-10-29 13:12 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 20.0% | 2026-10-29 13:13 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 20.0% | 2026-10-29 13:13 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 20.0% | 2026-10-29 13:14 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 20.0% | 2026-10-29 13:14 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 20.0% | 2026-10-29 13:25 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 20.0% | 2026-10-29 13:26 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:26 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 20.0% | 2026-10-29 13:27 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:27 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:27 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:28 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:28 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:28 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:28 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:28 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:29 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:29 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:29 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:29 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:29 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:30 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:30 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:30 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:31 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:31 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:31 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:31 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:32 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:32 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:32 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:32 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:32 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:32 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:33 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:33 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:33 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:33 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:34 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:34 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:35 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 22.0% | 2026-10-29 13:35 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 21.0% | 2026-10-29 13:35 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 22.0% | 2026-10-29 13:35 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 22.0% | 2026-10-29 13:36 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 22.0% | 2026-10-29 13:36 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 22.0% | 2026-10-29 13:40 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 22.0% | 2026-10-29 13:41 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 22.0% | 2026-10-29 13:41 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 22.0% | 2026-10-29 13:41 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 22.0% | 2026-10-29 13:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 22.0% | 2026-10-29 13:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 22.0% | 2026-10-29 13:42 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 22.0% | 2026-10-29 13:47 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 22.0% | 2026-10-29 13:47 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 22.0% | 2026-10-29 13:48 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 22.0% | 2026-10-29 13:48 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 22.0% | 2026-10-29 13:49 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 22.0% | 2026-10-29 13:49 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 22.0% | 2026-10-29 13:49 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 13:52 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 13:52 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 13:52 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 13:53 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 13:53 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 13:54 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 13:54 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 13:55 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 13:56 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 13:56 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 13:56 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 13:57 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 13:57 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 13:57 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 13:58 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 13:58 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 13:58 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 13:59 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 13:59 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 14:00 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 14:00 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 14:00 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 14:01 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 24.0% | 2026-10-29 14:01 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 23.0% | 2026-10-29 14:01 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 24.0% | 2026-10-29 14:02 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 24.0% | 2026-10-29 14:02 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 24.0% | 2026-10-29 14:02 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 24.0% | 2026-10-29 14:02 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 24.0% | 2026-10-29 14:03 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 24.0% | 2026-10-29 14:04 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 24.0% | 2026-10-29 14:04 UTC |
| codex | Unknown | local | codex:secondary | 7 d | 2026-11-04 13:32 UTC | 24.0% | 2026-10-29 14:05 UTC |

### Descriptive quota estimates

| Agent | Bucket | Status | Reason | Samples | Tokens per point | API-equivalent cost per point |
| --- | --- | --- | --- | ---: | ---: | ---: |
| codex | codex:primary | unavailable | Declare complete device/cloud coverage to attempt empirical calibration. | 110 | Unknown | Unknown |
| codex | codex:secondary | unavailable | Declare complete device/cloud coverage to attempt empirical calibration. | 454 | Unknown | Unknown |
| codex | codex:primary | unavailable | Declare complete device/cloud coverage to attempt empirical calibration. | 46 | Unknown | Unknown |
| codex | codex:secondary | unavailable | Declare complete device/cloud coverage to attempt empirical calibration. | 148 | Unknown | Unknown |
| codex | codex:primary | unavailable | Declare complete device/cloud coverage to attempt empirical calibration. | 1 | Unknown | Unknown |
| codex | codex:primary | unavailable | Declare complete device/cloud coverage to attempt empirical calibration. | 172 | Unknown | Unknown |
| codex | codex:primary | unavailable | Declare complete device/cloud coverage to attempt empirical calibration. | 73 | Unknown | Unknown |
| codex | codex:primary | unavailable | Declare complete device/cloud coverage to attempt empirical calibration. | 7 | Unknown | Unknown |
| codex | codex:primary | unavailable | Declare complete device/cloud coverage to attempt empirical calibration. | 1 | Unknown | Unknown |
| codex | codex:primary | unavailable | Declare complete device/cloud coverage to attempt empirical calibration. | 192 | Unknown | Unknown |

Estimates describe compatible observations only. The provider's quota formula is unknown; they are not an allowance or a forecast.

### Local activity windows

Local activity measured as of (2026-10-29 14:05 UTC). These rolling windows are not provider quota periods, and the 7-day window can start before the report period.

| Window | Requests | Total tokens | Requests missing usage |
| --- | ---: | ---: | ---: |
| 5 hours | 251 | 29,555,268 | 0 |
| 7 days | 597 | 70,337,439 | 0 |

## Coverage

| Source | Agent | Device | Origin | Contract | Files seen | Files parsed | Rows seen | Malformed rows |
| --- | --- | --- | --- | --- | ---: | ---: | ---: | ---: |
| 36070293179a | codex | local | local | observed-local | 1 | 1 | 136 | 0 |
| f08ec33a4f66 | codex | local | local | observed-local | 1 | 1 | 1,242 | 0 |
| d2a16ea7e629 | codex | local | local | observed-local | 1 | 1 | 1,057 | 0 |
| 0fe3e848cace | codex | local | local | observed-local | 1 | 1 | 20 | 0 |
| 6fcc4e8f5ae0 | codex | local | local | observed-local | 1 | 1 | 378 | 0 |
| a15d87e81775 | codex | local | local | observed-local | 1 | 1 | 58 | 0 |
| 344e70f95628 | codex | local | local | observed-local | 1 | 1 | 41 | 0 |
| fdb191e91bde | codex | local | local | observed-local | 1 | 1 | 149 | 0 |
| 4be7f1d81943 | codex | local | local | observed-local | 1 | 1 | 109 | 0 |
| 30db190e39b8 | codex | local | local | observed-local | 1 | 1 | 604 | 0 |
| 0874fc90f828 | codex | local | local | observed-local | 1 | 1 | 93 | 0 |
| b9edf5e4f41a | codex | local | local | observed-local | 1 | 1 | 86 | 0 |
| 34b05f93fef6 | codex | local | local | observed-local | 1 | 1 | 17 | 0 |
| ce112e4f589f | codex | local | local | observed-local | 1 | 1 | 2 | 0 |
| 20c372585ab5 | codex | local | local | observed-local | 1 | 1 | 363 | 0 |
| 80ad90d93f7e | codex | local | local | observed-local | 1 | 1 | 144 | 0 |
| 40d1f4c5b654 | codex | local | local | observed-local | 1 | 1 | 5 | 0 |
| d0e8310d9f3f | codex | local | local | observed-local | 1 | 1 | 694 | 0 |
| f6e4009c0cc2 | codex | local | local | observed-local | 1 | 1 | 430 | 0 |
| 39bf2d530052 | codex | local | local | observed-local | 1 | 1 | 34 | 0 |

Data from other devices, provider-hosted tasks and deleted histories may be absent. Local totals are not the complete account history.

### Warnings

| Code | Occurrences | Sources |
| --- | ---: | ---: |
| fork\_baseline\_unknown | 1 | 1 |

| Code | Source | Line |
| --- | --- | ---: |
| fork\_baseline\_unknown | 4be7f1d81943 | 5 |

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
