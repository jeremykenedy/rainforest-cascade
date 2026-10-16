# Architecture

Rainforest Cascade is a native Android application with a DreamService, remote-operated settings activity, settings content provider, and an OpenGL ES 2.0 renderer. Android MediaPlayer decodes bundled H.264 footage into a SurfaceTexture. The fragment shader samples that moving surface, adds slow uniform camera drift, grades daylight footage for the night option, and applies optional mist and sunlight overlays.

Forest, stone and canyon select their licensed waterfall recordings; garden selects an original pre-rendered animated matte. All four use the same decoder and shader path. The framing control applies a uniform camera zoom rather than stretching the waterfall. Wide, balanced and close views use scales of 1.0, 1.12 and 1.25; the legacy saved key `width` and values `narrow`, `curtain` and `wide` remain compatible. The shader also preserves aspect ratio when the output surface differs from 16:9. Playback speed changes the decoder's PlaybackParams. Added atmosphere cannot remove spray or sunlight already present in a recording. The renderer releases the decoder and video surface when its activity or dream stops.

The application has no network permission or network client. Video, shaders, source attribution and the application license work offline. GitHub access is limited to the separately run installer when a user chooses to fetch a release. The settings provider at `content://com.jeremykenedy.rainforestcascade.settings` exposes versioned `schema` and `settings` cursors.

The app targets API 36 and supports API 23 or newer. Bundled video is 1920x1080 at 30 frames per second. Android determines the actual output surface size; source resolution, decoder cadence and panel resolution do not establish native 4K rendering or sustained display frame rate.
