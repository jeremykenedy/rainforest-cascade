# Building

Install Android SDK platform 36 and build-tools 36.0.0, plus a JDK and standard shell tools. Build with:

```bash
bash build.sh
```

The APK and checksum are written under `build/`. The first local build creates a unique signing key and password under `~/.android/`. Back up both securely and use the same key for future releases. Do not commit either file. `RAINFOREST_CASCADE_KEYSTORE` and `RAINFOREST_CASCADE_KEYPASS` can point to an existing key and password. CI uses a disposable key; its output is not a production release.
