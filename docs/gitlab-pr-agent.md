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

The original course example records a live GitHub/OpenRouter launcher run, but
does not claim a live GitLab run. For this adaptation, the project's GitLab CI
validator accepts all three jobs with zero errors or warnings, and a simulated
`master` pipeline validates. Shell syntax and both missing-secret failure paths
also passed locally. The Java lint and 85 unit tests passed before adding this
review job; no application code or Java job commands changed in this step.

A configured model key, bot token and real MR are still needed to complete the
end-to-end review check. Native Swing QA and real FTP/FTPS verification remain
separate application checks.
