# Releasing

1. Set the version name and code in `build.sh` release environment values.
2. Run the full local test, style, documentation, privacy, build, and coverage suite.
3. Verify package ID, DreamService metadata, permissions, and the signed APK.
4. Install on the Fire TV and confirm continuous animation and remote settings. Save and restore the pre-existing screensaver component and enabled state around the test.
5. Capture final screenshots from the running release candidate and update verification notes.
6. Commit and push all source, docs, assets, and CI changes. Wait for every required workflow to pass on the exact release candidate. Do not make changes after this gate without rerunning checks and CI.
7. Publish a SemVer release with the signed APK and its `.sha256` file. Verify both downloaded assets and their checksum.

Retain the same signing key across updates. Document changes, compatibility, and upgrade path in the release notes.
