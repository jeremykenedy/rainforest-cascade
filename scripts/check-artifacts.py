#!/usr/bin/env python3
"""Verify licensed waterfall assets and the playable offline APK package."""

import hashlib
import json
from pathlib import Path
import xml.etree.ElementTree as ET
import zipfile

ROOT = Path(__file__).resolve().parent.parent
for path in list((ROOT / "res").rglob("*.xml")) + list((ROOT / "art").glob("*.svg")):
    ET.parse(path)
media = json.loads((ROOT / "assets/media.json").read_text())
expected = {"forest", "stone", "canyon"}
assert {Path(item["file"]).stem for item in media} == expected, "All three real environments must be bundled"
assert {path.stem for path in (ROOT / "res/raw").glob("*.mp4")} == expected, "Remove obsolete scene recordings"
with zipfile.ZipFile(ROOT / "build/rainforest-cascade.apk") as apk:
    for name in ("NOTICE", "LICENSE"):
        assert apk.read("assets/" + name) == (ROOT / name).read_bytes(), name
    for item in media:
        path = ROOT / item["file"]
        assert path.stat().st_size == item["bytes"], path
        assert hashlib.sha256(path.read_bytes()).hexdigest() == item["sha256"], path
        assert apk.getinfo(item["file"]).compress_type == zipfile.ZIP_STORED, path
        assert hashlib.sha256(apk.read(item["file"])).hexdigest() == item["sha256"], path
        assert item["source"].startswith("https://commons.wikimedia.org/wiki/File:")
        assert item["author"] and item["license"] and item["modifications"]
print("All real environments and framing assets, media hashes, XML and packaged offline credits are valid.")
