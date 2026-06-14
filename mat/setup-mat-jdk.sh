#!/usr/bin/env bash
# 一次性配置：让 MAT 使用 sdkman JDK 17+（MAT 1.16 要求 Java 17+）
set -euo pipefail

JAVA_BIN="${JAVA_BIN:-$HOME/.sdkman/candidates/java/current/bin/java}"
INI="/Applications/MemoryAnalyzer.app/Contents/Eclipse/MemoryAnalyzer.ini"

if [[ ! -x "$JAVA_BIN" ]]; then
  echo "Java not found: $JAVA_BIN" >&2
  echo "Set JAVA_BIN or install JDK via sdkman." >&2
  exit 1
fi

if [[ ! -f "$INI" ]]; then
  echo "MAT not installed. Run: brew install --cask memoryanalyzer" >&2
  exit 1
fi

if grep -q '^-vm$' "$INI" 2>/dev/null; then
  echo "MemoryAnalyzer.ini already has -vm, skip."
  exit 0
fi

cp "$INI" "$INI.bak"
awk -v java="$JAVA_BIN" '
  /^-vmargs/ && !done {
    print "-vm"
    print java
    done=1
  }
  { print }
' "$INI.bak" > "$INI"

echo "Patched $INI"
echo "  -vm $JAVA_BIN"
echo "Backup: $INI.bak"
