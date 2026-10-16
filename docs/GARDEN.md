# Sunrise flower garden

This source candidate adds a fourth `environment=garden` option. The original forest, stone and canyon recordings remain byte-identical. The existing seven setting keys and values remain supported; the environment picker and provider add `garden` as a compatible choice.

The garden is original rendered artwork. It combines a generated 1672x941 environment matte with independently advected falling-water texture, aerated foam, traveling pond ripples, localized impact mist and restrained foreground foliage movement. It is not photographed moving footage. The bundled H.264 clip is rendered to 1920x1080 at 30 fps for 16 seconds; rendering at that output size does not create additional source detail. The frame does not pan to simulate running water.

`art/materials/sunrise-garden.json` records the exact plate hash and prompt. `assets/media.json` records the final video hash, dimensions and license. The plate, rendering code and resulting original video use Apache-2.0. The three recorded environments retain their separately documented Creative Commons licenses.

## Rendering

The offline render requires Python, NumPy, OpenCV and ffmpeg. None are Android runtime dependencies. From the repository root:

```sh
python3 render/garden/render-loop.py
```

The script writes `build/garden-render/garden-periodic-loop.mp4`. Inspect moving playback and the boundary before replacing `res/raw/garden.mp4`, then update its bytes and hash in `assets/media.json`. The Android renderer uses the same offline MediaPlayer and GLES path as the three recorded scenes.

## Controls and limits

Wide, balanced and close framing uniformly change the field of view. Playback speed changes the complete rendered loop, including water and foliage. Added mist and sunlight shimmer remain separate shader layers. Night cools and dims the sunrise plate; it does not move the sun or create a separately rendered night environment. Randomization includes the garden.

## Verification status

Host option tests, true line and branch coverage, Java formatting, signed build and packaged media checks pass on the source candidate. On API 34 Google TV emulator 5580, actual 1920x1080 captures cover all three framings, added effects, night and fast playback. A 33.514-second native recording crosses two source-loop boundaries after a 40-second settle. The actual DreamService was bound with isPreview=true and mBound=true; Back exited. Remote Down then Select launched Preview and Back returned to settings, verified with fresh focus XML and resumed-activity state. Offline credits opened and scrolled into the full Apache license. All seven app preferences and seven system values were restored exactly before stopping the app and returning Home. Independent native moving review observed changing waterfall, pond and mist through the 16-second cycle without an abrupt jump. Physical moving review remains pending. This scene is not released and has not been installed on the physical TV.
