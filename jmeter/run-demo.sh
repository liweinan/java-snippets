#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

JMETER="${HOME}/.sdkman/candidates/jmeter/current/bin/jmeter"
if [[ ! -x "$JMETER" ]]; then
  echo "JMeter not found: $JMETER" >&2
  echo "See docs/jmeter-troubleshooting-guide.md" >&2
  exit 1
fi

echo "==> compile"
mvn -q compile -DskipTests

echo "==> start JmeterTargetServer"
java -cp target/classes io.weli.concurrent.JmeterTargetServer &
SERVER_PID=$!
trap 'kill "$SERVER_PID" 2>/dev/null || true' EXIT

sleep 2
curl -sf "http://127.0.0.1:18080/hello" >/dev/null
echo "    server ok (PID=$SERVER_PID)"

mkdir -p jmeter/out
rm -f jmeter/out/results.jtl jmeter/out/jmeter-run.log

echo "==> run jmeter (no proxy for localhost)"
unset http_proxy https_proxy HTTP_PROXY HTTPS_PROXY all_proxy ALL_PROXY
"$JMETER" -n \
  -t jmeter/hello-load-test.jmx \
  -l jmeter/out/results.jtl \
  -j jmeter/out/jmeter-run.log

echo "==> done"
echo "    samples : $(tail -n +2 jmeter/out/results.jtl | wc -l | tr -d ' ')"
echo "    log     : jmeter/out/jmeter-run.log"
echo "    results : jmeter/out/results.jtl"
