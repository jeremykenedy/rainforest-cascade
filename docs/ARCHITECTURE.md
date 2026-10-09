# Architecture

Rainforest Cascade is a native Android application with a DreamService, remote-operated settings activity, settings content provider, and a hardware-accelerated Canvas renderer. The renderer draws layered canyon or forest walls, a flowing water curtain, moving highlights, impact foam, pool ripples, and optional mist. Motion stops when the dream is not visible.

The application has no network permission or network client. GitHub access is limited to the separately run installer when a user chooses to fetch a release. The host-facing settings provider is at `com.jeremykenedy.rainforestcascade.settings` and exposes versioned `schema` and `settings` cursors.

The app targets API 36 and supports API 23 or newer. Composition uses the logical display size reported by Android. The project makes no native 4K composition claim based only on the TV panel resolution.
