#!/bin/sh
cd "$( dirname "$0" )"

# This script will configure the JVM memory constraints and fire up the SBT instance.
# If no arguments were provided to the script, SBT will start in shell mode.

# -XX:MaxPermSize JVM option is not required if you are running on JVM 8+

exec java \
  -XX:MaxPermSize=256m \
  -Xss2m -Xms2g -Xmx2g \
  -XX:+TieredCompilation -XX:+CMSClassUnloadingEnabled \
  -XX:+UseNUMA -XX:+UseParallelGC \
  -jar sbt-launch-0.13.6.jar \
  "$@"
