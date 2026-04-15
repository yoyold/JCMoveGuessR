#!/bin/sh
# Maven Wrapper script
set -e

# Find the project base dir (the directory containing .mvn)
MAVEN_PROJECTBASEDIR="${MAVEN_BASEDIR:-}"
if [ -z "$MAVEN_PROJECTBASEDIR" ]; then
  MAVEN_PROJECTBASEDIR="$(cd "$(dirname "$0")" && pwd)"
fi

MAVEN_WRAPPER_JAR="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar"
MAVEN_WRAPPER_PROPERTIES="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.properties"

# Read distributionUrl
DISTRIBUTION_URL=$(grep distributionUrl "$MAVEN_WRAPPER_PROPERTIES" | sed 's/distributionUrl=//')

# Derive local Maven home from URL
MAVEN_ZIP_FILENAME=$(basename "$DISTRIBUTION_URL")
MAVEN_DIR_NAME="${MAVEN_ZIP_FILENAME%-bin.zip}"
MAVEN_USER_HOME="${MAVEN_USER_HOME:-$HOME/.m2/wrapper}"
MAVEN_HOME="$MAVEN_USER_HOME/dists/$MAVEN_DIR_NAME"
MAVEN_EXE="$MAVEN_HOME/bin/mvn"

# Download and unpack Maven if not present
if [ ! -x "$MAVEN_EXE" ]; then
  echo "Downloading Maven from $DISTRIBUTION_URL ..."
  mkdir -p "$MAVEN_USER_HOME/dists"
  TMP_ZIP="$MAVEN_USER_HOME/$MAVEN_ZIP_FILENAME"
  if command -v curl >/dev/null 2>&1; then
    curl -fsSL "$DISTRIBUTION_URL" -o "$TMP_ZIP"
  elif command -v wget >/dev/null 2>&1; then
    wget -q "$DISTRIBUTION_URL" -O "$TMP_ZIP"
  else
    echo "Error: curl or wget required to download Maven." >&2
    exit 1
  fi
  unzip -q "$TMP_ZIP" -d "$MAVEN_USER_HOME/dists"
  rm "$TMP_ZIP"
  echo "Maven installed to $MAVEN_HOME"
fi

exec "$MAVEN_EXE" "$@"
