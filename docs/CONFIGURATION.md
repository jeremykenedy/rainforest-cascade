# Configuration

Open Rainforest Cascade from the TV launcher and operate the settings with the remote. Choices persist locally and apply when the screensaver next starts.

| Setting | Choices | Default |
| --- | --- | --- |
| Surroundings | Rainforest, mossy stone, red canyon, random | Rainforest |
| Time of day | Daylight, night, random | Daylight |
| Waterfall width | Narrow, curtain, wide, random | Curtain |
| Water flow | Slow, natural, fast, random | Natural |
| Water mist | Off, light, heavy, random | Light |
| Sunlight shimmer | Off, on, random | On |
| Randomize all | Off, on | Off |

The app-owned settings provider is `com.jeremykenedy.rainforestcascade.settings`:

- `content://com.jeremykenedy.rainforestcascade.settings/schema` returns keys, labels, types, defaults, available values, and whether an individual random value is supported.
- `content://com.jeremykenedy.rainforestcascade.settings/settings` returns current key/value pairs.
- Update one setting through the `settings` URI with `ContentValues` fields `key` and `value`. Values outside the schema are rejected.

These contracts support host interfaces that opt to integrate them. The current Fire TV UI app's screensaver picker does not itself edit app-specific settings.
