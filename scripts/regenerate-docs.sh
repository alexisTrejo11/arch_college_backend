#!/usr/bin/env bash
# Regenerate docs/generated/ from docs/source/ for every domain service.
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
PYTHON="${ROOT}/.venv/bin/python3"

if [[ ! -x "$PYTHON" ]]; then
  echo "Repo venv not found at .venv — using python3 from PATH (requires PyYAML)." >&2
  PYTHON="python3"
fi

SERVICES=(
  account-service
  curriculum-service
  enrollment-service
  grade-service
  student-service
  teacher-service
  schedule-service
)

for svc in "${SERVICES[@]}"; do
  echo "=== $svc ==="
  (cd "$ROOT/$svc" && "$PYTHON" docs/yaml_to_markdown.py)
done

echo ""
echo "=== docs/project (merge service sources) ==="
"$PYTHON" "$ROOT/docs/project/merge_service_sources.py"

echo ""
echo "Done — regenerated per-service docs and merged project source."
