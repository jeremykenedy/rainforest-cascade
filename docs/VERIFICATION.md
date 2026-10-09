# Verification

## Fire TV

- Device: Amazon Fire TV Stick 4K Max, model AFTDEC012E
- Fire OS: 11
- Android API: 30
- Connection: ADB over the local network
- Screen: TV panel reports 3840x2160; Android logical output is validated separately and no native 4K composition claim is made
- Screensaver component before and after test: `com.androsaver/.ScreensaverService`
- Screensaver enabled before and after test: `1`

The app installed successfully and its preview and remote-operated settings screens were exercised on this device. The day and night screenshots show the running preview activity. Fire OS does not expose the Android Dream manager service or a supported shell command to start a selected DreamService (`cmd dream help` reports `Can't find service: dream`). A five-minute idle test continued to run the pre-existing Androsaver service, so the `WaterfallDreamService` lifecycle could not be verified on this Fire TV. The original screensaver component and enabled state were restored. The app service is registered in the package manager with the required bind permission.

| Platform | Device | Result |
| --- | --- | --- |
| Fire TV DreamService activation | AFTDEC012E, Fire OS 11, API 30 | Not verified. We are looking for a Fire TV owner to confirm DreamService selection and idle activation, then report the model, Fire OS/API, resolution, and results in the [issue tracker](https://github.com/jeremykenedy/rainforest-cascade/issues). Preview and settings were tested on this device. |
| Physical Android TV | Not tested for this release | We are looking for an Android TV owner to test installation, screensaver selection and activation, and remote settings, then report the model, OS/API, resolution, and results in the [issue tracker](https://github.com/jeremykenedy/rainforest-cascade/issues). |
| Physical Google TV | Not tested for this release | We are looking for a Google TV owner to run the same checks and report the model, OS/API, resolution, and results in the [issue tracker](https://github.com/jeremykenedy/rainforest-cascade/issues). |

## Runtime and coverage limits

Canvas draws continuously at a requested 30 frames per second while active and stops on the dream lifecycle stop callback. Sustained frame pacing, temperature, idle power, and long-duration burn-in have not been measured. Host coverage is 100% line and branch for the option resolver and settings validator; Android-dependent lifecycle and drawing code is excluded from host coverage.
