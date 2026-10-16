# Testing

Run installer tests and their coverage gate:

```bash
bash test.sh
bash scripts/test-python-coverage.sh
```

Run actual Java formatting, settings tests and the 100% line and branch gate:

```bash
bash scripts/test-coverage.sh
```

Build the APK and verify media hashes, all three recordings and the original rendered garden loop, uncompressed playable video entries, XML, and the packaged offline NOTICE and LICENSE:

```bash
bash build.sh
python3 scripts/check-artifacts.py
python3 scripts/check-privacy.py
```

Host JaCoCo covers the option resolver and settings validation. Android lifecycle, decoder and graphics callbacks require native checks and are outside that percentage. The previous COVEREDCOUNT threshold did not enforce full coverage; the current gate uses COVEREDRATIO 1.0. Formatting runs from the source root and checks actual Java files.

Before a native test, record the selected screensaver, enabled state and all seven preferences. Test every environment and framing, both lighting modes, each speed, mist and sunlight, settings persistence, provider reads and writes, credits navigation, actual DreamService behavior, and Back/Home return paths. Capture the running renderer after it settles, inspect the complete moving loop and boundary, and restore the prior state. A directly launched preview does not prove idle activation on a physical TV.

Review each complete source recording for people, words, watermarks, camera movement and internal cuts before selecting a continuous shot. Inspect the encoded loop for empty frames, camera jumps and visible crossfade ghosts. Automated frame differences support visual review; they do not replace it or establish decoder performance.
