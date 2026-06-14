#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

OUT_DIR="mat/out"
HPROF="$OUT_DIR/jmap-heap-demo.hprof"

echo "==> compile"
mvn -q compile -DskipTests

echo "==> start JmapHeapDemo (-Xmx256m)"
java -Xmx256m -cp target/classes io.weli.concurrent.JmapHeapDemo &
DEMO_PID=$!
trap 'kill "$DEMO_PID" 2>/dev/null || true' EXIT

echo "    waiting for leak (10 rounds)..."
sleep 6

mkdir -p "$OUT_DIR"
echo "==> jmap dump"
jmap -dump:live,format=b,file="$HPROF" "$DEMO_PID"
ls -lh "$HPROF"
echo "==> done: $HPROF"
