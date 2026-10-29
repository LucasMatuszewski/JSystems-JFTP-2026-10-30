#!/usr/bin/env bash
# One attempt after a deadline; uses an existing authenticated Codex CLI session.
set -euo pipefail

usage() {
  echo 'Usage: bash tools/resume-after-reset.sh --at ISO_WITH_OFFSET_OR_EPOCH --thread UUID --codex PATH --state-dir DIR --prompt-file FILE [--activity-file FILE] [--quiet-seconds 300] [--poll-seconds 30]'
}

target='' thread='' codex='' state='' prompt='' activity=''
quiet=300 poll=30
while (($#)); do
  case "$1" in
    --at|--thread|--codex|--state-dir|--prompt-file|--activity-file|--quiet-seconds|--poll-seconds)
      (($# >= 2)) || { usage >&2; exit 2; }
      case "$1" in
        --at) target=$2 ;; --thread) thread=$2 ;; --codex) codex=$2 ;;
        --state-dir) state=$2 ;; --prompt-file) prompt=$2 ;; --activity-file) activity=$2 ;;
        --quiet-seconds) quiet=$2 ;; --poll-seconds) poll=$2 ;;
      esac
      shift 2 ;;
    --help|-h) usage; exit 0 ;;
    *) usage >&2; exit 2 ;;
  esac
done
[[ -n $target && -n $thread && -n $codex && -n $state && -f $prompt ]] || { usage >&2; exit 2; }
[[ $quiet =~ ^[0-9]+$ && $poll =~ ^[0-9]+$ && $poll -gt 0 && $poll -le 60 ]] || exit 2
[[ $thread =~ ^[0-9a-fA-F-]{36}$ ]] || { echo 'Expected a session UUID' >&2; exit 2; }
[[ -x $codex ]] || { echo 'Codex executable is missing or not executable' >&2; exit 2; }
[[ -z $activity || -f $activity ]] || { echo 'Session activity file is missing' >&2; exit 2; }
if [[ $target =~ ^[0-9]+$ ]]; then
  target_epoch=$target
else
  target_epoch=$(date -d "$target" +%s)
fi
repo=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)
cd -- "$repo"
mkdir -p -- "$state"
state=$(cd -- "$state" && pwd)
[[ ! -e $state/attempted ]] || { echo 'A resume was already attempted in this state directory' >&2; exit 3; }
mkdir -- "$state/monitor.lock" 2>/dev/null || { echo 'Another monitor owns this state directory' >&2; exit 3; }
trap 'rmdir -- "$state/monitor.lock" 2>/dev/null || true' EXIT
printf '%s\n' "$$" > "$state/monitor.pid"
log() { printf '%s %s\n' "$(date -u +%FT%TZ)" "$*" | tee -a "$state/monitor.log"; }
log "ARMED target=$(date -u -d "@$target_epoch" +%FT%TZ) thread=$thread quiet_seconds=$quiet"
last_reason=''
while :; do
  if [[ -e $state/CANCEL ]]; then log 'CANCELLED; no resume requested'; exit 0; fi
  now=$(date +%s)
  if ((now < target_epoch)); then
    remaining=$((target_epoch - now))
    ((remaining > poll)) && remaining=$poll
    sleep "$remaining"
    continue
  fi
  # A sleeping machine may wake late, but never resume this old instruction the next day.
  if ((now > target_epoch + 43200)); then log 'EXPIRED; target is over 12 hours old'; exit 4; fi
  if [[ -n $activity ]]; then
    modified=$(stat -c %Y -- "$activity")
    if ((now - modified < quiet)); then
      if [[ $last_reason != busy ]]; then log 'WAITING; recent session activity'; last_reason=busy; fi
      sleep "$poll"
      continue
    fi
  fi
  break
done
[[ ! -e $state/CANCEL ]] || { log 'CANCELLED; no resume requested'; exit 0; }
# Claim the attempt before launch, so even a crash cannot cause an automatic second call.
printf '%s\n' "$(date -u +%FT%TZ)" > "$state/attempted"
log 'STARTING; one Codex resume attempt'
set +e
"$codex" exec --sandbox workspace-write --approve-for-me resume "$thread" - \
  < "$prompt" > "$state/resume.stdout.log" 2> "$state/resume.stderr.log"
result=$?
set -e
printf '%s\n' "$result" > "$state/exit-code"
log "FINISHED exit_code=$result; no automatic retries"
exit "$result"
