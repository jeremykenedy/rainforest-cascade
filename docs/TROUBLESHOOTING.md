# Troubleshooting

If the screensaver does not start, select Rainforest Cascade in system screensaver settings and confirm that the device is authorized for ADB. The app does not alter the device's selection, timers, sleep behavior, or updates.

If settings changes do not appear immediately, close and restart the screensaver. Changes are loaded when the dream starts. If the installer cannot find the TV, confirm that `adb devices` lists the explicit serial as `device` rather than `unauthorized` or `offline`.

For a checksum failure, do not install the file. Download the APK and checksum again from the same GitHub release.
