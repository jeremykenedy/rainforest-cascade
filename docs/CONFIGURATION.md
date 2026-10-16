# Configuration

Open Rainforest Cascade from the TV launcher and operate the settings with the remote. Choices persist locally and apply when the screensaver next starts.

| Setting | Choices | Default |
| --- | --- | --- |
| Surroundings | Forest, mossy stone, canyon, sunrise flower garden (rendered), random | Forest |
| Time of day | Daylight, night, random | Daylight |
| Framing | Wide view, balanced, close view, random | Balanced |
| Water flow | Slow, natural, fast, random | Natural |
| Added mist | Off, light, heavy, random | Light |
| Sunlight shimmer | Off, on, random | On |
| Randomize all | Off, on | Off |

Forest, stone and canyon select real waterfall recordings. Garden selects the separate [original animated matte](GARDEN.md). Framing changes the camera view uniformly at 1.0x, 1.12x or 1.25x; it does not change the physical width of the water. The legacy saved key `width` keeps its existing values: `narrow` means wide view, `curtain` means balanced and `wide` means close view. Water and rocks keep their proportions. The source locations and authors are in [media provenance](MEDIA.md). Speed changes scene playback to 0.58x, 1x or 1.55x. Night is a cool dim grade of the daylight scene. Added mist and sunlight shimmer are separate effects; turning them off does not remove natural spray or sunlight already recorded in the clip. Sunlight shimmer is suppressed in night mode.

The final settings button opens bundled source credits and the full application license offline. Existing keys and accepted values are preserved.

The app-owned settings provider is `com.jeremykenedy.rainforestcascade.settings`:

- `content://com.jeremykenedy.rainforestcascade.settings/schema` returns keys, labels, types, defaults, available values, and whether an individual random value is supported.
- `content://com.jeremykenedy.rainforestcascade.settings/settings` returns current key/value pairs.
- Update one setting through the `settings` URI with `ContentValues` fields `key` and `value`. Values outside the schema are rejected.

These contracts support host interfaces that opt to integrate them. Host-specific settings editing and preview capabilities must be verified against the installed host version.
