#!/bin/bash

if [ -f "gradle/libs.versions.toml" ]; then
  FILE="gradle/libs.versions.toml"
elif [ -f "build.gradle" ]; then
  FILE="build.gradle"
else
  echo "Neither gradle/libs.versions.toml nor build.gradle was found"
  exit 1
fi

get_current_version_number() {
  local content="$1"
  if [[ "$content" =~ paparazzi\ *=\ *\"([0-9]+\.[0-9]+\.[0-9]+)\" ]]; then
    echo "${BASH_REMATCH[1]}"
    return 0
  else
    return 1
  fi
}

compare_versions() {
  local local_version=$1
  local remote_version=$2

  IFS="." read -r local_major local_minor local_patch <<< "$local_version"
  IFS="." read -r remote_major remote_minor remote_patch <<< "$remote_version"

  if [ "$local_major" -gt "$remote_major" ] || [ "$local_minor" -gt "$remote_minor" ] || [ "$local_patch" -gt "$remote_patch" ]; then
    return 1
  else
    return 0
  fi
}

bump_remote_version() {
  local remote_version=$1
  IFS='.' read -r major minor patch <<< "$remote_version"
  echo "$major.$minor.$((patch + 1))"
}

push_screenshots_to_git() {
  local commit_message="auto bump version from $local_version to $new_local_version"

  echo "$commit_message"

  if [ -z "$(git config --get user.name)" ]; then
    git config user.name "renovate[bot]"
  fi

  if [ -z "$(git config --get user.email)" ]; then
    git config user.email "29139614+renovate[bot]@users.noreply.github.com"
  fi

  git add "**/test/snapshots/images/*"
  if ! git commit -m "$commit_message"; then
    echo "Error when committing the file $FILE"
    exit 1
  fi
  git push
}

check_paparazzi_version() {
  if ! local_file_content=$(cat "$FILE" 2>/dev/null); then
    echo "Local file $FILE could not be found"
    return 1
  fi

  if ! remote_file_content=$(git show origin/develop:"$FILE" 2>/dev/null); then
    echo "Remote file $FILE could not be found"
    return 1
  fi

  if ! local_version=$(get_current_version_number "$local_file_content"); then
    echo "Local version could not be found in the $FILE file"
    return 1
  fi

  if ! remote_version=$(get_current_version_number "$remote_file_content"); then
    echo "Remote version could not be found in the $FILE file"
    return 1
  fi

  if compare_versions "$local_version" "$remote_version" -eq 0; then
    ./gradlew recordPaparazziDebug
    push_screenshots_to_git
  else
    echo "local paparazzi version: $local_version is already greater than remote version: $remote_version"
  fi
}

check_paparazzi_version