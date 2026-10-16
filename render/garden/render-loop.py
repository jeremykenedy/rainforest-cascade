"""Render the original garden matte with moving water, pond waves, mist and foliage.

Requires offline render dependencies NumPy, OpenCV and ffmpeg.
The Android app plays the bundled result without those dependencies.
"""
from pathlib import Path
import subprocess

import cv2
import numpy as np

root = Path(__file__).resolve().parents[2]
source = root / 'art/materials/sunrise-garden-original.png'
output = root / 'build/garden-render'
output.mkdir(parents=True, exist_ok=True)
w, h = (1920, 1080)
original = (
    cv2.resize(cv2.imread(str(source)), (w, h), interpolation=cv2.INTER_AREA)
    .astype(np.float32) / 255
)
xx, yy = np.meshgrid(np.arange(w, dtype=np.float32), np.arange(h, dtype=np.float32))
x = xx / w
y = yy / h
poly = np.zeros((h, w), np.uint8)
points = np.array([
    [0.406, 0.244], [0.471, 0.255], [0.501, 0.257], [0.559, 0.247],
    [0.597, 0.272], [0.623, 0.42], [0.633, 0.567], [0.393, 0.574],
    [0.349, 0.414], [0.379, 0.27],
]) * np.array([w, h])
cv2.fillPoly(poly, [points.astype(np.int32)], 255)
lum = cv2.cvtColor(original, cv2.COLOR_BGR2GRAY)
mask = poly / 255 * np.clip((lum - 0.18) / 0.34, 0, 1)
mask = cv2.GaussianBlur(mask.astype(np.float32), (0, 0), 1.4)
mask *= np.minimum(np.clip((y - 0.235) / 0.035, 0, 1), np.clip((0.58 - y) / 0.04, 0, 1))
watermask = mask[:, :, None]
pond = np.zeros((h, w), np.uint8)
pondpoly = np.array([
    [0.15, 0.585], [0.85, 0.585], [0.86, 0.77], [0.72, 0.84],
    [0.61, 0.94], [0.41, 0.89], [0.25, 0.84], [0.12, 0.74],
]) * np.array([w, h])
cv2.fillPoly(pond, [pondpoly.astype(np.int32)], 255)
pond = cv2.GaussianBlur(pond.astype(np.float32) / 255, (0, 0), 8)
writer = subprocess.Popen([
    'ffmpeg', '-hide_banner', '-loglevel', 'error',
    '-f', 'rawvideo', '-pix_fmt', 'bgr24', '-s', f'{w}x{h}',
    '-r', '30', '-i', '-', '-an', '-c:v', 'libx264',
    '-preset', 'veryfast', '-qp', '18', '-x264-params',
    'psy=0:aq-mode=0:mbtree=0:ipratio=1:pbratio=1:keyint=30:min-keyint=30:scenecut=0',
    '-pix_fmt', 'yuv420p', '-movflags', '+faststart', '-y',
    str(output / 'garden-periodic-loop.mp4'),
], stdin=subprocess.PIPE)

for frame in range(480):
    t = frame / 30
    a = 2 * np.pi * t / 16
    flow = np.zeros_like(original)
    # Fade each advected phase to zero at its reset, preserving the rock boundary.
    for phase in (0.0, 0.5):
        f = (t + phase) % 1
        sampley = yy - f * 27
        samplex = xx + 1.9 * np.sin(y * 37 - a * 13) + 0.8 * np.sin(y * 71 + a * 18 + x * 19)
        layer = cv2.remap(original, samplex.astype(np.float32), sampley.astype(np.float32), cv2.INTER_LINEAR, borderMode=cv2.BORDER_REFLECT)
        weight = np.sin(np.pi * f) ** 2
        flow += layer * weight
    image = original * (1 - watermask * 0.88) + flow * watermask * 0.88
    foam = np.sin(x * 403 + np.sin(x * 71) * 3) * np.sin(y * 261 - a * 74 + x * 13)
    image += watermask * (foam[:, :, None] * 0.026)
    ripple = np.sin(y * 240 - a * 8 + np.sin(x * 27 + a * 2) * 1.4)
    dx = (np.sin(y * 170 - a * 6) * 1.6 + np.sin(y * 391 + a * 4) * 0.55) * pond
    dy = ripple * 0.75 * pond
    moving = cv2.remap(image, (xx + dx).astype(np.float32), (yy + dy).astype(np.float32), cv2.INTER_LINEAR, borderMode=cv2.BORDER_REFLECT)
    fog = np.exp(-((y - 0.575) / 0.026) ** 2) * np.exp(-((x - 0.5) / 0.25) ** 4)
    fog *= 0.5 + 0.5 * np.sin(x * 19 + a * 2 + np.sin(x * 31 - a))
    moving = moving * (1 - fog[:, :, None] * 0.06) + np.array([0.76, 0.83, 0.87], np.float32) * fog[:, :, None] * 0.06
    foliage = np.clip((np.abs(x - 0.5) - 0.25) / 0.16, 0, 1) * np.clip((y - 0.5) / 0.3, 0, 1)
    sway = (np.sin(a + x * 8) + 0.35 * np.sin(a * 2 + y * 11)) * foliage * 1.4
    moving = cv2.remap(moving, (xx + sway).astype(np.float32), (yy + sway * 0.16).astype(np.float32), cv2.INTER_LINEAR, borderMode=cv2.BORDER_REFLECT)
    encoded = np.clip(moving * 255, 0, 255).astype(np.uint8)
    if frame in (0, 45, 120, 210):
        cv2.imwrite(str(output / f'proof-{frame:04}.jpg'), encoded)
    writer.stdin.write(encoded.tobytes())
writer.stdin.close()
assert writer.wait() == 0
cv2.imwrite(str(output / 'water-mask.jpg'), np.uint8(mask * 255))
print(output / 'garden-periodic-loop.mp4')
