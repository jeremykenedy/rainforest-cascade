<p align="center">
  <a href="https://github.com/jeremykenedy/rainforest-cascade#gh-light-mode-only"><picture><source media="(max-width: 600px) and (prefers-reduced-motion: reduce)" srcset="art/banner-mobile-still-light.67fbb8f08d94.svg"><source media="(prefers-reduced-motion: reduce)" srcset="art/banner-still-light.6a7db98249f1.svg"><source media="(max-width: 600px)" srcset="art/banner-mobile-light.27f8b3a886d6.svg"><img src="art/banner-light.a87203b87841.svg#gh-light-mode-only" alt="Rainforest Cascade" width="800"></picture></a>
  <a href="https://github.com/jeremykenedy/rainforest-cascade#gh-dark-mode-only"><picture><source media="(max-width: 600px) and (prefers-reduced-motion: reduce)" srcset="art/banner-mobile-still-dark.d5171576048d.svg"><source media="(prefers-reduced-motion: reduce)" srcset="art/banner-still-dark.254bae11ffa8.svg"><source media="(max-width: 600px)" srcset="art/banner-mobile-dark.442a0ba1f7b1.svg"><img src="art/banner-dark.06a203cdc11a.svg#gh-dark-mode-only" alt="Rainforest Cascade" width="800"></picture></a>
</p>

<p align="center">Three recorded waterfalls and an original sunrise flower garden, with quiet atmospheric controls.</p>

<p align="center">
    <a href="https://github.com/jeremykenedy/rainforest-cascade/releases"><img src="https://img.shields.io/github/downloads/jeremykenedy/rainforest-cascade/total" alt="GitHub release downloads"></a>
    <a href="https://github.com/jeremykenedy/rainforest-cascade/releases"><img src="https://img.shields.io/github/v/release/jeremykenedy/rainforest-cascade?label=latest%20release" alt="Latest release"></a>
    <a href="https://github.com/jeremykenedy/rainforest-cascade/actions/workflows/ci.yml"><img src="https://github.com/jeremykenedy/rainforest-cascade/actions/workflows/ci.yml/badge.svg" alt="Build, tests, and coverage"></a>
    <a href="https://github.com/jeremykenedy/rainforest-cascade/actions/workflows/style.yml"><img src="https://github.com/jeremykenedy/rainforest-cascade/actions/workflows/style.yml/badge.svg" alt="Code style"></a>
    <a href="https://github.com/jeremykenedy/rainforest-cascade/actions/workflows/docs.yml"><img src="https://github.com/jeremykenedy/rainforest-cascade/actions/workflows/docs.yml/badge.svg" alt="Documentation checks"></a>
    <a href="https://github.com/jeremykenedy/rainforest-cascade/actions/workflows/security.yml"><img src="https://github.com/jeremykenedy/rainforest-cascade/actions/workflows/security.yml/badge.svg" alt="Privacy checks"></a>
    <a href="https://img.shields.io/github/license/jeremykenedy/rainforest-cascade"><img src="https://img.shields.io/github/license/jeremykenedy/rainforest-cascade?label=license" alt="Apache 2.0 license"></a>
</p>

<p align="center">
    <a href="https://github.com/jeremykenedy"><img src="https://img.shields.io/github/followers/jeremykenedy?label=Follow&style=social" alt="Follow jeremykenedy on GitHub"></a>
    <a href="https://github.com/jeremykenedy/rainforest-cascade/stargazers"><img src="https://img.shields.io/badge/Star-this%20repo-yellow?logo=github&style=social" alt="Star this repository"></a>
    <a href="https://github.com/sponsors/jeremykenedy"><img src="https://img.shields.io/badge/Sponsor-jeremykenedy-ea4aaa?logo=githubsponsors&logoColor=white" alt="Sponsor"></a>
</p>

## Table of Contents

- [Privacy](#privacy)
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Configuration](#configuration)
- [Fire TV Toolkit](#fire-tv-toolkit)
- [Screenshots](#screenshots)
- [Building and testing](#building-and-testing)
- [Documentation](#documentation)
- [Changelog](#changelog)
- [License](#license)

## Privacy

The app has no ads, analytics, telemetry, diagnostics, crash reporting, or tracking. It requests no network permission and makes no network requests. Settings remain on the device. The optional command-line installer contacts GitHub only when you explicitly ask it to download a release; it sends no TV or usage data.

## Features

The 1.1.0 build replaces the original painted waterfall. Native checks cover all four scenes on API 34; the exact signed production build also passed remote Preview, moving garden playback and an actual night test-Dream with Back on a physical AFTDEC012E Fire TV. See [verification](docs/VERIFICATION.md) for the artifact and limits.

- Three licensed real moving environments: a wooded cascade, mossy stone falls and a snowy canyon waterfall.
- A separate original sunrise flower garden with animated falling water, pond ripples, mist and foreground foliage. This is rendered artwork, not recorded footage.
- Aspect-preserving 1080p scenes with wide, balanced and close framing. Camera framing changes the view without stretching water or rocks.
- Actual slow, natural or fast video playback.
- Daylight or cool night grading, plus separate added mist and sunlight shimmer.
- Very slow full-scene drift, per-setting random choices and a master random mode.
- Existing remote settings and stable host provider, with offline source credits and full application license.

## Requirements

Android API 23 or newer, OpenGL ES 2.0 and H.264 decoding are required. The project targets API 36. The replacement uses 1920x1080 recorded and rendered scenes; a 4K panel or higher-resolution original source does not establish native 4K composition.

Current native and physical results are distinguished in [device verification](docs/VERIFICATION.md). Automatic idle activation across vendor configurations and sustained thermal/power behavior remain unverified.

## Installation

Connect the TV to ADB and run the guided installer. It downloads the latest stable release, verifies the published SHA-256 checksum, and installs or updates the app.

```bash
python3 install.py --serial TV_IP:5555
```

Install a locally built APK with `python3 install.py --serial TV_IP:5555 --apk build/rainforest-cascade.apk`. To remove the app and its settings, run `python3 install.py --serial TV_IP:5555 --uninstall`. Unattended removal requires `--uninstall --yes --force`. The installer does not change the TV's screensaver selection, timers, sleep behavior, or system update settings. Select Rainforest Cascade in the device's screensaver settings after installation.

## Configuration

Open Rainforest Cascade from the TV launcher to update settings. Changes apply the next time the screensaver starts. For supported options, defaults, and the host settings-provider schema, see [configuration](docs/CONFIGURATION.md).

## Fire TV Toolkit

[Fire TV Toolkit](https://github.com/jeremykenedy/fire-tv-toolkit) provides guided installation and TV setup:

```bash
git clone https://github.com/jeremykenedy/fire-tv-toolkit.git
cd fire-tv-toolkit
node setup.js
firetv-screensavers --install=rainforest-cascade
screensaver --set=rainforest-cascade
firetv-screensavers --uninstall=rainforest-cascade --force
```

Repeat the install command to update the existing package. This downloads the latest stable published release. Toolkit also manages supported launcher choices, sleep/screensaver timers and settings safeguards. Visual choices live in this app. Fire TV UI discovers installed dreams and provides its documented preview and settings integration.

## Screenshots

The first row includes the exact final production APK on a physical AFTDEC012E Fire TV in daylight Preview and a bound night DreamService, followed by its wide emulator garden view. Remaining native captures show framing, effects and remote controls on API 34. All captures are 1920x1080; scene screenshots retain the corresponding [media licenses](docs/MEDIA.md).

<table>
  <tr>
    <td width="33%"><img src="art/screenshots/fire-tv-garden-day.png" alt="Original sunrise flower garden running on physical Fire TV" width="100%"></td>
    <td width="33%"><img src="art/screenshots/fire-tv-garden-night-dream.png" alt="Cool night garden through actual bound DreamService on physical Fire TV" width="100%"></td>
    <td width="33%"><img src="art/screenshots/garden-wide-view.png" alt="Wide sunrise garden through the API34 native preview" width="100%"></td>
  </tr>
  <tr>
    <td width="33%"><img src="art/screenshots/forest-wide-view.png" alt="Wooded cascade, wide view, actual native Android TV preview" width="100%"></td>
    <td width="33%"><img src="art/screenshots/forest-balanced.png" alt="Wooded cascade, balanced framing, actual native Android TV preview" width="100%"></td>
    <td width="33%"><img src="art/screenshots/forest-close-view.png" alt="Wooded cascade, close view, actual native Android TV preview" width="100%"></td>
  </tr>
  <tr>
    <td width="33%"><img src="art/screenshots/stone-wide-view.png" alt="Mossy stone falls, wide view, actual native Android TV preview" width="100%"></td>
    <td width="33%"><img src="art/screenshots/stone-balanced.png" alt="Mossy stone falls, balanced framing, actual native Android TV preview" width="100%"></td>
    <td width="33%"><img src="art/screenshots/stone-close-view.png" alt="Mossy stone falls, close view, actual native Android TV preview" width="100%"></td>
  </tr>
  <tr>
    <td width="33%"><img src="art/screenshots/canyon-wide-view.png" alt="Snowy canyon waterfall, wide view, actual native Android TV preview" width="100%"></td>
    <td width="33%"><img src="art/screenshots/canyon-balanced.png" alt="Snowy canyon waterfall, balanced framing, actual native Android TV preview" width="100%"></td>
    <td width="33%"><img src="art/screenshots/canyon-close-view.png" alt="Snowy canyon waterfall, close view, actual native Android TV preview" width="100%"></td>
  </tr>
  <tr>
    <td width="33%"><img src="art/screenshots/forest-heavy-mist.png" alt="Added heavy mist, actual native Android TV capture" width="100%"></td>
    <td width="33%"><img src="art/screenshots/forest-sunlight.png" alt="Added sunlight shimmer, actual native Android TV capture" width="100%"></td>
    <td width="33%"><img src="art/screenshots/forest-night-heavy-slow.png" alt="Cool night grade with mist and slow playback, actual native Android TV capture" width="100%"></td>
  </tr>
  <tr>
    <td width="33%"><img src="art/screenshots/settings-garden.png" alt="Remote-operated native settings, actual native Android TV capture" width="100%"></td>
    <td width="33%"><img src="art/screenshots/credits-garden.png" alt="Bundled source credits, actual native Android TV capture" width="100%"></td>
    <td width="33%"><img src="art/screenshots/credits-garden-scrolled.png" alt="Full application license scrolled with the remote, actual native Android TV capture" width="100%"></td>
  </tr>

  <tr>
    <td width="33%"><img src="art/screenshots/garden-wide-view.png" alt="Original rendered sunrise garden at wide framing" width="100%"></td>
    <td width="33%"><img src="art/screenshots/garden-balanced.png" alt="Original garden at balanced framing" width="100%"></td>
    <td width="33%"><img src="art/screenshots/garden-close-heavy-fast.png" alt="Close garden framing with added mist and fast playback" width="100%"></td>
  </tr>
</table>

The garden also has actual native [night grading](art/screenshots/garden-night-heavy-slow.png) and [DreamService](art/screenshots/dream-garden.png) captures. Recorded-scene captures retain their original per-image APK provenance because their videos, shader and renderer are unchanged. Garden and refreshed settings/credits captures use the four-scene production build. See [garden artwork and verification](docs/GARDEN.md).

## Building and testing

Use Android SDK platform 36 and build-tools 36.0.0. The first build creates a unique signing key in `~/.android/`; keep it secure and back it up for future signed updates.

```bash
bash build.sh
bash test.sh
bash scripts/test-python-coverage.sh
bash scripts/test-coverage.sh
python3 scripts/check-docs.py
python3 scripts/check-privacy.py
bash scripts/check-style.sh
```

The Java coverage gate is 100% line and branch coverage for the project-owned settings and option-resolution logic. Android framework, decoder, graphics and lifecycle code require native verification and are outside host JaCoCo coverage. The Python installer is gated at 100% line and branch coverage.

## Documentation

- [Original sunrise garden, rendering and limits](docs/GARDEN.md)
- [Architecture](docs/ARCHITECTURE.md)
- [Building](docs/BUILDING.md)
- [CI](docs/CI.md)
- [Configuration](docs/CONFIGURATION.md)
- [Media provenance and licenses](docs/MEDIA.md)
- [Quality correction and verification boundaries](docs/QUALITY-AUDIT.md)
- [Installation](docs/INSTALLATION.md)
- [Developer integration contract](docs/INTEGRATION.md)
- [Releasing](docs/RELEASING.md)
- [Testing](docs/TESTING.md)
- [Troubleshooting](docs/TROUBLESHOOTING.md)
- [Verification and device results](docs/VERIFICATION.md)
- [Version 1.0.0 release notes](docs/releases/v1.0.0.md)

See [1.1.0 upgrade and release details](docs/releases/v1.1.0.md).

## Changelog

See the [changelog](CHANGELOG.md) for the release history.

## License

Rainforest Cascade is licensed under the [Apache License, Version 2.0](LICENSE). Recorded clips have their own Creative Commons licenses. See [NOTICE](NOTICE) and [media provenance](docs/MEDIA.md) for authors, licenses and modifications. Credits and the full application license are also available offline from settings.
