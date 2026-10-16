# Verification

## Four-scene garden candidate

The separate four-scene candidate was tested on the same API 34 Google TV emulator at 1920x1080. Its signed APK SHA-256 is `88602f840deae1e9a7fa8de5b1dda03601c70d0d70399ab11262e1817e7de31f` (72,053,957 bytes). The three recorded video files, shader and renderer remain unchanged. Garden is original rendered artwork; see [garden provenance, controls and verification](GARDEN.md).

Actual native checks cover all garden framings, effects, night grading and fast playback, remote Down then Select to Preview and Back to settings, offline credits, and a bound nonpersistent test-Dream followed by Back. The native motion recording runs for 33.514 seconds after a 40-second settle. All seven prior preferences and seven system values were compared and restored exactly, including the selected Reel Atrium Dream and enabled value. The app was stopped and Home restored. Physical moving review, sustained performance, idle activation, sleep/wake and final release remain pending.

The first test incorrectly expected immediate Select to launch Preview while the ScrollView held initial focus. That assertion stopped the harness and its finally block restored state. A corrected test verified the actual Down then Select path against fresh XML showing the focused Preview button; no navigation source change was needed.

## Three-recording 1.1.0 emulator candidate

On October 16, 2026, the signed candidate was installed on the shared Google TV/Android TV emulator, API 34, serial `emulator-5580`, with a 1920x1080 output surface. The tested APK SHA-256 is `678f64b4d14b7f85de42e047eef04138818f6a8dc0c97c15e954141f6fc314b9` (50,123,900 bytes).

After a 40-second settle, native captures cover all three environments at wide, balanced and close framing, added mist and sunlight, cool night grading, slow and fast playback, readable remote settings, and offline credits scrolled into the full Apache application license. Screenshot provenance is in [`art/screenshots/provenance.json`](../art/screenshots/provenance.json). Framing preserves water and rock proportions; it changes camera crop rather than physical waterfall width.

The actual `WaterfallDreamService` was bound through the emulator's nonpersistent test-Dream route. Dream-manager output identifies the service, `isPreview=true` and `mBound=true`; Back exited it. This exercises the real service but does not prove physical TV idle activation. All seven prior preference values were restored. The selected Reel Atrium component and enabled value `1` remained unchanged, and the app was stopped before returning Home.

| Native recording | Actual duration | Decoded compositor frames | Changed adjacent frames |
| --- | --- | --- | --- |
| Forest, balanced | 20.635 s | 957 | 641 |
| Mossy stone, balanced | 20.715 s | 928 | 687 |
| Snowy canyon, balanced | 10.768 s | 388 | 328 |
| Slow playback with night/mist | 4.022 s | 147 | 87 |
| Fast daylight playback | 4.449 s | 148 | 138 |

Every decoded native frame was nonempty. Changed-frame counts use a mean channel difference above 0.02 on reduced diagnostic frames. The three source loops decode completely and their boundary changes are comparable to ordinary frame changes. These statistics support moving review and are not renderer FPS measurements. The emulator logged an unsupported `eglQueryContext` attribute during surface setup; the native images and playback continued. Physical decoder/GPU behavior and sustained performance still require hardware verification.

The final signed candidate SHA-256 is `ba2a3695bc45fe66fd8ab136e7f9a4e5fef7cb5f4d7aa9369057c31d7f35fd47` (50,947,205 bytes). Its classes, shader, three videos and application license are byte-identical to the native-tested APK. The only attribution change removes a trailing blank line. The final embedded screensaver preview is refreshed from the actual native forest capture. Its source image is separate from the continuously moving video used by the renderer.

## Historical 1.0 physical Fire TV preview

- Device: Amazon Fire TV Stick 4K Max, model AFTDEC012E
- Android API reported by the earlier record: 30
- Fire OS build: not refreshed for this candidate
- Connection: ADB over the local network
- Screen: TV panel reports 3840x2160; Android logical output is validated separately and no native 4K composition claim is made
- Screensaver component before and after test: `com.androsaver/.ScreensaverService`
- Screensaver enabled before and after test: `1`

The app installed successfully and its preview and remote-operated settings screens were exercised on this device. The day and night screenshots show the running preview activity. Fire OS does not expose the Android Dream manager service or a supported shell command to start a selected DreamService (`cmd dream help` reports `Can't find service: dream`). A five-minute idle test continued to run the pre-existing Androsaver service, so the `WaterfallDreamService` lifecycle could not be verified on this Fire TV. The original screensaver component and enabled state were restored. The app service is registered in the package manager with the required bind permission.

| Platform | Device | Result |
| --- | --- | --- |
| Fire TV DreamService activation | AFTDEC012E, historical API 30 record | Not verified. We are looking for a Fire TV owner to confirm DreamService selection and idle activation, then report the model, Fire OS/API, resolution, and results in the [issue tracker](https://github.com/jeremykenedy/rainforest-cascade/issues). Preview and settings were tested on this device. |
| Physical Android TV | Not tested for this release | We are looking for an Android TV owner to test installation, screensaver selection and activation, and remote settings, then report the model, OS/API, resolution, and results in the [issue tracker](https://github.com/jeremykenedy/rainforest-cascade/issues). |
| Physical Google TV | Not tested for this release | We are looking for a Google TV owner to run the same checks and report the model, OS/API, resolution, and results in the [issue tracker](https://github.com/jeremykenedy/rainforest-cascade/issues). |

## Current candidate limits

The 1.1.0 candidates use 1920x1080/30 fps recorded and rendered video decoded into OpenGL ES 2.0. Source frame rate and recorded emulator compositor frame counts do not prove sustained renderer or physical display frame rate. Long-duration temperature, idle power, hardware decoder behavior and burn-in protection have not been measured on a physical TV. Current physical idle activation, sleep/wake and remote return paths remain pending.

Host coverage is 100% line and branch for the option resolver and settings validator. The corrected gate enforces COVEREDRATIO 1.0 and formatting checks actual Java files from the project root. Framework, decoder, graphics and lifecycle code require native verification and are outside host coverage.
