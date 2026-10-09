# Testing

Run the installer unit tests and coverage gate:

```bash
bash test.sh
bash scripts/test-python-coverage.sh
```

Run Java settings tests and the 100% line and branch gate:

```bash
bash scripts/test-coverage.sh
```

Run formatting, documentation, and privacy checks with the commands in the README. Build the APK and inspect its permissions. On the Fire TV, test the settings activity, saved choices, provider reads/writes, start and stop of the dream, smooth visible motion, and return to the previous TV screen. Record the device's original screensaver component and enabled value before selecting the test dream, then restore both afterward.

Host JaCoCo covers the owned option resolver and settings value validation. Android framework callbacks and drawing calls are exercised on device rather than counted as host-executable branches.
