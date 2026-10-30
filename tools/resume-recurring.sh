#!/usr/bin/env bash
# Clock-based continuation of one existing authenticated Codex session.
set -euo pipefail

usage() {
  echo 'Usage: bash tools/resume-recurring.sh --at ISO_WITH_OFFSET_OR_EPOCH --thread UUID --codex PATH --state-dir DIR --prompt-file FILE [--activity-file FILE] [--interval-seconds 18600] [--quiet-seconds 300] [--poll-seconds 30] [--max-runs 0]'
}
target='' thread='' codex='' state='' prompt='' activity=''
interval=18600 quiet=300 poll=30 max_runs=0
while (($#)); do
  case "$1" in
    --at|--thread|--codex|--state-dir|--prompt-file|--activity-file|--interval-seconds|--quiet-seconds|--poll-seconds|--max-runs)
      (($# >= 2)) || { usage >&2; exit 2; }
      case "$1" in
        --at) target=$2 ;; --thread) thread=$2 ;; --codex) codex=$2 ;;
        --state-dir) state=$2 ;; --prompt-file) prompt=$2 ;; --activity-file) activity=$2 ;;
        --interval-seconds) interval=$2 ;; --quiet-seconds) quiet=$2 ;;
        --poll-seconds) poll=$2 ;; --max-runs) max_runs=$2 ;;
      esac
      shift 2 ;;
    --help|-h) usage; exit 0 ;;
    *) usage >&2; exit 2 ;;
  esac
done
[[ -n $target && -n $thread && -x $codex && -n $state && -f $prompt ]] || { usage >&2; exit 2; }
[[ $thread =~ ^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$ ]] || exit 2
[[ $interval =~ ^[1-9][0-9]*$ && $quiet =~ ^(0|[1-9][0-9]*)$ && $poll =~ ^[1-9][0-9]*$ && $poll -le 60 ]] || exit 2
[[ $max_runs =~ ^(0|[1-9][0-9]*)$ && ( -z $activity || -f $activity ) ]] || exit 2
if [[ $target =~ ^[0-9]+$ ]]; then first=$target; else first=$(date -d "$target" +%s); fi
repo=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)
cd -- "$repo"
mkdir -p -- "$state"
state=$(cd -- "$state" && pwd)
mkdir -- "$state/schedule.lock" 2>/dev/null || { echo 'Another monitor owns this schedule' >&2; exit 3; }
child=''
cleanup() {
  # Retain the lock on an abnormal monitor exit if its child could still run.
  if [[ -n $child ]] && kill -0 "$child" 2>/dev/null; then return; fi
  rmdir -- "$state/schedule.lock" 2>/dev/null || true
}
trap cleanup EXIT
trap 'touch "$state/CANCEL"; exit 130' INT TERM
printf '%s\n' "$$" > "$state/monitor.pid"
log() { printf '%s %s\n' "$(date -u +%FT%TZ)" "$*" | tee -a "$state/monitor.log"; }
binding=$(printf '%s\n' "$thread" "$first" "$interval" "$codex" "$repo")
if [[ -f $state/schedule.conf ]]; then
  [[ $(cat -- "$state/schedule.conf") == "$binding" ]] || { log 'CONFIGURATION MISMATCH; use a separate state directory'; exit 2; }
else
  printf '%s\n' "$binding" > "$state/schedule.conf"
fi
next=$first
if [[ -f $state/next-at ]]; then
  next=$(cat -- "$state/next-at")
  [[ $next =~ ^[0-9]+$ ]] || { log 'INVALID next-at state'; exit 2; }
fi
log "ARMED first=$(date -u -d "@$first" +%FT%TZ) interval_seconds=$interval thread=$thread"
completed=0
while :; do
  if [[ -e $state/CANCEL ]]; then log 'CANCELLED; no future resume'; exit 0; fi
  printf '%s\n' "$next" > "$state/next-at"
  cycle="$state/attempts/$next"
  mkdir -p -- "$cycle"
  if [[ ! -e $cycle/attempted ]]; then
    printf '%s\n' "$next" > "$state/active-cycle"
    command=("$BASH" "$repo/tools/resume-after-reset.sh" --at "$next" --thread "$thread"
      --codex "$codex" --state-dir "$cycle" --prompt-file "$prompt"
      --quiet-seconds "$quiet" --poll-seconds "$poll" --cancel-file "$state/CANCEL")
    if [[ -n $activity ]]; then command+=(--activity-file "$activity"); fi
    log "WAITING target=$(date -u -d "@$next" +%FT%TZ)"
    "${command[@]}" > "$cycle/timer.stdout.log" 2> "$cycle/timer.stderr.log" &
    child=$!
    printf '%s\n' "$child" > "$state/active-child.pid"
    while kill -0 "$child" 2>/dev/null; do
      # The one-shot checks the shared CANCEL before invoking Codex.
      # An in-flight Codex run is allowed to finish; future cycles are cancelled.
      sleep "$poll"
    done
    set +e
    wait "$child"
    result=$?
    set -e
    child=''
    log "CYCLE FINISHED target=$next exit_code=$result"
    completed=$((completed + 1))
  else
    log "ALREADY ATTEMPTED target=$next; skipping duplicate"
  fi
  if [[ -e $state/CANCEL ]]; then log 'CANCELLED; no future resume'; exit 0; fi
  next=$((next + interval))
  now=$(date +%s)
  if ((next <= now)); then
    skipped=$(((now - next) / interval + 1))
    next=$((next + skipped * interval))
    log "SKIPPED missed_slots=$skipped; no overlapping or catch-up runs"
  fi
  printf '%s\n' "$next" > "$state/next-at"
  if ((max_runs > 0 && completed >= max_runs)); then log 'COMPLETE; requested run count reached'; exit 0; fi
done
