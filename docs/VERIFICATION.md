# Verification

## Production 1.1.0 artifact

The exact signed APK is 72,053,957 bytes with SHA-256 `88602f840deae1e9a7fa8de5b1dda03601c70d0d70399ab11262e1817e7de31f`. Its production signing certificate remains `4818ad1fb9c91392b47d28fb84113640dfdc386d642890d980dd632637161400`. All runtime source, resources, bundled assets, manifest, NOTICE, license and build inputs remain identical to the garden source used for this tested binary; subsequent changes document the evidence and add gallery images.

The four scenes are three licensed recordings and a separate original animated sunrise garden. Garden uses a 1672x941 original plate rendered with independently moving water, foam, pond, mist and local foliage to a 1920x1080/30 fps loop. It is not recorded footage or native 4K detail. See [media provenance](MEDIA.md) and [garden](GARDEN.md).

## Physical Fire TV

| Field | Actual result |
| --- | --- |
| Device | Physical AFTDEC012E, API 30; Fire OS build not refreshed |
| Capture | 1920x1080; native 4K composition not claimed |
| Update | Same-package, same-signer update retained all seven prior preferences |
| Remote route | Settings, Down then Select on Preview, then Back to settings |
| Playback | Garden daylight after 40-second settle, 33.588-second recording |
| Dream | Actual bound WaterfallDreamService, mCurrentDreamIsTest=true and mBound=true, night garden; Back cleared it |
| Moving review | Normal QuickTime playback 0 to 34 seconds and 13 to 34 seconds observed changing water, foam, pond and mist, without an abrupt jump |
| Restoration | All seven app preferences and five system values exact; prior asleep state restored |

Physical screenshot hashes and capture timestamps are in [physical provenance](../art/screenshots/physical-provenance.json). The [daylight image](../art/screenshots/fire-tv-garden-day.png) shows the actual running Preview. The [night image](../art/screenshots/fire-tv-garden-night-dream.png) shows the actual test-Dream.

The five restored system values cover selected DreamService, enabled state, sleep timeout, screen-off timeout and auto-wake setting. The test did not persistently select Rainforest Cascade as the user's default Dream. A short SurfaceFlinger sample had 125 valid buffer presents at approximately 43.26 buffer presents per second, with a median 16.703-millisecond interval. This measures compositor buffer presents, not 30 fps source video decoding, sustained renderer FPS, heat or power.

A harness initially compared the Preview button text with different letter case from the native uppercase label. The case-insensitive check verified the actual remote route; no application navigation change was needed.

## API 34 Google TV emulator

The exact four-scene APK was tested at 1920x1080 on emulator-5580. Native captures cover all garden framings, added effects, night grading, fast playback, remote Down then Select to Preview and Back, credits scrolled into the full Apache license, and a bound nonpersistent test-Dream with Back. A 33.514-second recording crosses two 16-second source loop boundaries after a 40-second settle. All seven app preferences and seven sampled system values were restored, including the previously selected Reel Atrium service and enabled state. The app was stopped and Home restored.

Three-recording checks used APK `678f64b4d14b7f85de42e047eef04138818f6a8dc0c97c15e954141f6fc314b9`. Those videos, shader and renderer are byte-identical in the four-scene release. All three environments were captured at wide, balanced and close framing, with separate mist, sunlight, night and playback-speed checks. Per-image build hashes are preserved in [native provenance](../art/screenshots/provenance.json).

| Native recording | Duration | Decoded compositor frames | Changed adjacent frames |
| --- | --- | --- | --- |
| Forest, balanced |20.635s |957 |641 |
| Mossy stone, balanced |20.715s |928 |687 |
| Snowy canyon, balanced |10.768s |388 |328 |
| Slow playback with night/mist |4.022s |147 |87 |
| Fast daylight |4.449s |148 |138 |
| Garden, wide |33.514s |1495 |1064 |

Every decoded frame was nonempty. Changed-frame counts are reduced-frame diagnostics, not renderer FPS. All source loops decode completely. The emulator logged an unsupported eglQueryContext attribute during surface setup while playback continued; physical final playback is separately established above.

## Browser and host checks

The final public 21-image release gallery passed actual desktop 1280 and mobile 320 inspection, with every image loaded at 1920x1080 and no horizontal overflow. Its first row shows the final physical daylight Preview and night Dream captures alongside the native wide garden view. Automatic light/dark desktop/mobile motion and reduced-motion banner cases passed; manual GitHub single-theme account settings and a continuously observed full banner loop remain unverified.

Java settings logic and the installer each enforce 100 percent line and branch coverage. The corrected Java gate uses COVEREDRATIO 1.0 and formats actual source files from the project root. Android framework, decoder, graphics and lifecycle are explicitly outside host coverage and require native checks.

## Limits and other devices

Explicit test-Dream and Preview do not establish automatic idle activation, reboot persistence or sleep/wake behavior across vendor settings. Sustained temperature, idle power, long-duration memory and all scene/control combinations on physical hardware remain unverified. Full-scene drift does not guarantee prevention of image retention.

Physical Android TV and Google TV devices were unavailable. Owners of those devices can test installation, Dream selection/idle activation and remote navigation, then report model, OS/API, resolution, settings and results through the [issue tracker](https://github.com/jeremykenedy/rainforest-cascade/issues). The historical 1.0 physical preview was a different Canvas renderer and does not establish current performance.
