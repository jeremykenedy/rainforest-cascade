# Installation

## Install or update from GitHub

Connect ADB to the TV, then run:

```bash
python3 install.py --serial TV_IP:5555
```

The guided installer downloads the latest stable release APK and checksum from this repository, validates the URL and SHA-256, and installs the package. It contacts GitHub only for this explicit download and sends no TV or usage data.

## Install a local build

```bash
python3 install.py --serial TV_IP:5555 --apk build/rainforest-cascade.apk
```

## Remove

```bash
python3 install.py --serial TV_IP:5555 --uninstall
```

Non-interactive removal needs `--uninstall --yes --force`. Removal clears the app and its local settings. Install, update, and removal do not change the selected screensaver, timers, sleep behavior, or system update settings. Choose Rainforest Cascade in the device's screensaver or ambient display settings after installation. Menu names vary by vendor.
