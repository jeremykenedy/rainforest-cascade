# Developer integration contract

| Field | Value |
| --- | --- |
| Product | Rainforest Cascade |
| Repository and saver ID | `jeremykenedy/rainforest-cascade`, `rainforest-cascade` |
| Package | `com.jeremykenedy.rainforestcascade` |
| Dream component | `com.jeremykenedy.rainforestcascade/.WaterfallDreamService` |
| Settings | `com.jeremykenedy.rainforestcascade/.SettingsActivity` |
| Preview activity | `com.jeremykenedy.rainforestcascade/.PreviewActivity` |
| Minimum SDK | 23 |
| Release version | 1.1.0, code 2 |
| APK and checksum filenames | `rainforest-cascade.apk`, `rainforest-cascade.apk.sha256` |
| Release tag | `v1.1.0` |
| APK SHA-256 | `88602f840deae1e9a7fa8de5b1dda03601c70d0d70399ab11262e1817e7de31f` |
| APK bytes | 72,053,957 |
| APK URL | `https://github.com/jeremykenedy/rainforest-cascade/releases/download/v1.1.0/rainforest-cascade.apk` |
| Checksum URL | Same release path, `rainforest-cascade.apk.sha256` |
| Settings schema | `content://com.jeremykenedy.rainforestcascade.settings/schema` |
| Current settings | `content://com.jeremykenedy.rainforestcascade.settings/settings` |

The seven preference keys remain `environment`, `lighting`, `width`, `speed`, `mist`,
`sunlight` and `randomize_all`. Accepted values and defaults are preserved. The
legacy `width` key now selects honestly labeled camera framing: `narrow` is wide
view, `curtain` is balanced and `wide` is close view. It does not stretch the water
or change physical waterfall width. Read [configuration](CONFIGURATION.md) and the
live schema rather than inventing additional options.

The production certificate SHA-256 remains
`4818ad1fb9c91392b47d28fb84113640dfdc386d642890d980dd632637161400`.
The exact signed candidate and native-capture builds are distinguished in
[verification](VERIFICATION.md). The native preview source is
`art/screenshots/forest-balanced.png`, 1920x1080, with its JPEG derivative bundled
as `res/drawable-nodpi/screensaver_preview.jpg`. Native screenshot hashes and
capture dates are in `art/screenshots/provenance.json`.

Use the final published APK and checksum from the verified release before
refreshing an ecosystem pin. The release must be downloaded and its checksum verified before updating a host pin.
Verify package and signer continuity, DreamService registration and bind
permission, preview provenance, provider validation and the target host's native
selection, settings and Back behavior. Toolkit registration belongs in its
existing reviewed registry. UI previews use that project's normalized component
hash and capture manifest. Host features require verification against the actual
host build; app metadata alone does not implement a generic settings editor or
repository installer.

Application source is Apache-2.0. Each recorded clip and its modified video retain
their individual CC BY 4.0 or CC BY-SA 4.0 license. Preserve [NOTICE](../NOTICE),
[media provenance](MEDIA.md), adaptation terms and bundled offline credits.

The compatible garden option adds `garden` to the existing `environment` choices. It uses the same package, components, seven preference keys and provider endpoints. The three original recorded environments remain unchanged; garden is separately labeled rendered artwork.
