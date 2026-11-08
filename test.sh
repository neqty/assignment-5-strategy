#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
mkdir -p target/test-classes
JAR=target/junit-platform-console-standalone-1.11.4.jar
if [ ! -f "$JAR" ]; then
  curl -fL https://repo.maven.apache.org/maven2/org/junit/platform/junit-platform-console-standalone/1.11.4/junit-platform-console-standalone-1.11.4.jar -o "$JAR.tmp"
  mv "$JAR.tmp" "$JAR"
fi
find src/main/java src/test/java -name '*.java' > target/test-sources.txt
javac --release 17 -cp "$JAR" -d target/test-classes @target/test-sources.txt
java -jar "$JAR" execute --class-path target/test-classes --scan-class-path --disable-ansi-colors
