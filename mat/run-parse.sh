#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
HPROF="$(cd "$(dirname "${1:-$ROOT/mat/out/jmap-heap-demo.hprof}")" && pwd)/$(basename "${1:-$ROOT/mat/out/jmap-heap-demo.hprof}")"

MAT_PARSE="/Applications/MemoryAnalyzer.app/Contents/Eclipse/ParseHeapDump.sh"

if [[ ! -f "$HPROF" ]]; then
  echo "Heap dump not found: $HPROF" >&2
  echo "Run: ./mat/capture-heap.sh" >&2
  exit 1
fi

if [[ ! -x "$MAT_PARSE" ]]; then
  echo "MAT not found. Run: brew install --cask memoryanalyzer" >&2
  exit 1
fi

echo "==> parsing $HPROF"
"$MAT_PARSE" "$HPROF" org.eclipse.mat.api:suspects org.eclipse.mat.api:overview

OUT_DIR="$(dirname "$HPROF")"
BASE="$(basename "$HPROF" .hprof)"
echo "==> reports:"
ls -lh "$OUT_DIR/${BASE}"_*.zip 2>/dev/null || true
echo "    unzip ${BASE}_Leak_Suspects.zip && open index.html"
