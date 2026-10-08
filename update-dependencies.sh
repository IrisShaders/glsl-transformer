#!/bin/sh
# Updates the pinned dependency and plugin versions in gradle/libs.versions.toml and the Gradle wrapper
# to their latest stable versions and then verifies that the project still builds and passes its tests.
set -e
cd "$(dirname "$0")"

./gradlew versionCatalogUpdate
./gradlew wrapper --gradle-version latest
# the second run lets the new Gradle version update the wrapper files themselves
./gradlew wrapper
./gradlew build
# the release tasks only run in the release workflow, so at least check that they can still be set up
./gradlew jreleaserFullRelease --dry-run
