# PR-Agent on GitLab

The `pr-agent-review` job in [.gitlab-ci.yml](../.gitlab-ci.yml) follows the
[course PR-Agent example](https://github.com/LucasMatuszewski/jsystems-codex-java-2026-10/tree/main/course-materials/cicd-headless/PR-Agent-Qodo).
It uses community PR-Agent 0.47.0, with the CLI image pinned by digest, and posts
an English review comment on a GitLab merge request. A Qodo subscription is not
required.

The lint and unit-test jobs run automatically. AI review is manual and advisory,
available after those checks pass for merge requests whose source branch is in
this project. Fork merge requests do not get this job. Review errors remain
visible in the job log and do not block the pipeline.

## Required secrets

In this project's **Settings → CI/CD → Variables**, add these two variables as
**Variable** type, with environment scope `*`, **Masked and hidden**, and variable
expansion disabled:

| Variable | Value |
|---|---|
| `REVIEW_API_KEY` | An OpenRouter API key with credit/access to the configured model. Model API usage is billed separately from coding subscriptions. |
| `GITLAB__PERSONAL_ACCESS_TOKEN` | A dedicated GitLab bot token with `api` scope and access to this project. A project access token with Developer role is a straightforward setup; alternatively use a dedicated bot account's personal access token and give that account Developer membership here. |

To reuse the OpenRouter key across courses on this self-managed server, an
administrator can define `REVIEW_API_KEY` under **Admin → Settings → CI/CD →
Variables** instead. The job inherits it without a code change. Remove the
project-level duplicate if the instance variable should be used, because project
variables take precedence. Instance variables are available to other projects'
pipelines too; a group variable provides narrower sharing within a course group.
Keep the GitLab bot token project-specific. See [GitLab variable scopes and
precedence](https://docs.gitlab.com/ci/variables/).

Create a project access token under **Settings → Access tokens**, with an expiry
date, `api` scope and Developer role. Store its value only in the CI variable.
`CI_JOB_TOKEN` cannot publish the review comment. The token's identity will be
the author of the comment; no administrator token is needed.

For this trusted internal course repository, leave **Protect variable** unchecked
if reviews run from ordinary unprotected feature branches. Protected variables
are not normally available to those MR pipelines. Only trusted contributors
should be allowed to change/run the secret-bearing pipeline, and pipeline
variable overrides should be restricted to maintainers. Protecting variables
requires a compatible protected-branch MR workflow instead.

## Model settings

The following optional CI variables use the course defaults when absent:

| Variable | Default |
|---|---|
| `REVIEW_API_BASE` | `https://openrouter.ai/api/v1` |
| `REVIEW_MODEL` | `openai/gpt-6-luna` |
| `REVIEW_MAX_TOKENS` | `250000` input tokens per call |

The endpoint must be trusted: it receives the model API key and review content.
PR-Agent's `openai/` adapter prefix is added by the job, so the OpenRouter default
becomes `openai/openai/gpt-6-luna` internally. Keep `REVIEW_MODEL` as shown in the
table. Large diffs may use up to four calls; findings are limited to five.
If you change models, check the job's reasoning/temperature settings as well;
the supplied settings follow the course model configuration.

The existing Docker runner needs HTTPS access to Docker Hub, this GitLab instance
and the model endpoint. The review job uses the image's `/app` directory and
overrides the Maven setup/cache inherited by the Java jobs. It fetches MR data
through the GitLab API without checking out or running application code.
Repository/global PR-Agent settings files are disabled; review settings and
JFTP-specific instructions are defined in the pipeline.

## First run

1. Commit and push the CI files to GitLab, then open a same-project merge request
   containing a small change.
2. Let `java-lint` and `unit-tests` finish, then press **Play** on
   `pr-agent-review` in that MR's pipeline.
3. Confirm the bot posts a review comment, and inspect the job log/run details
   for the configured model and errors. A successful process alone is not
   evidence that the review is useful.
4. For a pilot, introduce a small deliberate defect in a disposable branch,
   verify the review identifies it, fix it, and run review in the new pipeline.
   Confirm the persistent comment is updated rather than duplicated.

This setup runs `review` only. Comment commands such as `/review` need a separate
webhook service. The job sends code/diff context to the selected model endpoint.

## Validation status

Live GitLab verification passed on 2026-10-30 in
[MR !1](https://jsystems-gitlab.polandcentral.cloudapp.azure.com/lucas/JSystems-JFTP-2026-10-30/-/merge_requests/1),
[pipeline 12](https://jsystems-gitlab.polandcentral.cloudapp.azure.com/lucas/JSystems-JFTP-2026-10-30/-/pipelines/12),
at commit `786db7a`:

- Checkstyle passed with zero violations and uploaded its report.
- All 85 headless tests passed on the Linux Docker runner, with zero failures,
  errors or skips. GitLab received the JUnit results and report artifacts.
- The manual PR-Agent job succeeded and the project bot published a
  [review comment](https://jsystems-gitlab.polandcentral.cloudapp.azure.com/lucas/JSystems-JFTP-2026-10-30/-/merge_requests/1#note_3).
  Run details identified `openai/openai/gpt-6-luna` through OpenRouter, one AI
  call, and 5,316 input / 576 output tokens.

The first pipeline exposed a missing `unzip` package: the Unix wrapper switched
to TAR.GZ and rejected it against the pinned ZIP checksum. Installing `unzip`
fixed the fresh-download path while preserving checksum validation.

The review raised the documented secret-exposure concern: same-project pipeline
editors can access injected secrets. This is retained as a limitation of the
trusted internal course setup, not claimed as fixed by manual execution or
masking. At verification the private project listed only its owner and review
bot as members. Reassess this setup before admitting untrusted contributors.

Known nonfatal output includes legacy Java 8 compatibility/deprecation notes,
local-only runner cache notices, and PR-Agent warnings about absent optional
`.secrets.toml` files; the configured environment credentials worked. Native Swing
QA, real FTP/FTPS verification and review-quality testing with a deliberately
introduced defect remain separate checks.
