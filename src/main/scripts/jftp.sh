#!/usr/bin/env sh
APP_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd) || exit 1
exec java -jar "$APP_DIR/${project.artifactId}-${project.version}.jar" "$@"
