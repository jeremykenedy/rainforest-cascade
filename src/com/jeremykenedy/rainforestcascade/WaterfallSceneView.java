package com.jeremykenedy.rainforestcascade;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.View;

import java.util.Random;

final class WaterfallSceneView extends View {
    private static final int PARTICLES = 72;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Random random = new Random(91377L);
    private final float[] mistX = new float[PARTICLES];
    private final float[] mistY = new float[PARTICLES];
    private final float[] mistSize = new float[PARTICLES];
    private final float[] mistPhase = new float[PARTICLES];
    private final float[] rockX = new float[260];
    private final float[] rockY = new float[260];
    private final float[] rockScale = new float[260];
    private final float[] rockShape = new float[260];
    private final float[] leafX = new float[34];
    private final float[] leafY = new float[34];
    private final float[] leafScale = new float[34];
    private final float[] leafAngle = new float[34];
    private final Path leftCliff = new Path();
    private final Path rightCliff = new Path();
    private final Path fallShape = new Path();
    private final Path rockFacet = new Path();
    private LinearGradient skyGradient;
    private LinearGradient poolGradient;
    private LinearGradient fallGradient;
    private RadialGradient sunGlow;
    private WaterfallOptions options;
    private long startedAt;
    private boolean running;

    WaterfallSceneView(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        for (int i = 0; i < PARTICLES; i++) {
            mistX[i] = random.nextFloat();
            mistY[i] = random.nextFloat();
            mistSize[i] = 1.2f + random.nextFloat() * 4.8f;
            mistPhase[i] = random.nextFloat() * 6.28f;
        }
        for (int i = 0; i < rockX.length; i++) {
            float sideX = random.nextFloat() * .34f;
            rockX[i] = random.nextBoolean() ? sideX : 1f - sideX;
            rockY[i] = random.nextFloat() * .78f;
            rockScale[i] = .004f + random.nextFloat() * .014f;
            rockShape[i] = .72f + random.nextFloat() * .58f;
        }
        for (int i = 0; i < leafX.length; i++) {
            float sideX = .015f + random.nextFloat() * .19f;
            leafX[i] = random.nextBoolean() ? sideX : 1f - sideX;
            leafY[i] = .11f + random.nextFloat() * .72f;
            leafScale[i] = .006f + random.nextFloat() * .019f;
            leafAngle[i] = -50f + random.nextFloat() * 100f;
        }
        loadOptions();
    }

    void start() {
        if (!running) {
            running = true;
            startedAt = SystemClock.uptimeMillis();
            postInvalidateOnAnimation();
        }
    }

    void stop() {
        running = false;
        removeCallbacks(invalidator);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() <= 0 || getHeight() <= 0) return;
        float time = (SystemClock.uptimeMillis() - startedAt) / 1000f;
        drawBackdrop(canvas, time);
        drawCliffs(canvas, time);
        drawPool(canvas, time);
        drawWaterfall(canvas, time);
        drawMist(canvas, time);
        if (running) postDelayed(invalidator, 33L);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        if (width <= 0 || height <= 0) return;
        float scale = width / 1920f;
        int skyTop = color(0xff91c3bc, 0xff142327);
        int skyBottom = color(0xffccddbd, 0xff182c31);
        skyGradient = new LinearGradient(0, 0, 0, height, skyTop, skyBottom, Shader.TileMode.CLAMP);
        int waterTop = color(0xff397f86, 0xff173d48);
        int waterBottom = color(0xff89c3b9, 0xff24515a);
        poolGradient = new LinearGradient(0, height * .69f, 0, height, waterTop, waterBottom, Shader.TileMode.CLAMP);
        int fallTop = color(0xfff1f5e9, 0xffc6e4e0);
        int fallBottom = color(0xff62b4b7, 0xff347e8b);
        fallGradient = new LinearGradient(0, height * .18f, 0, height * .79f,
                new int[] {fallTop, color(0xffbce7dc, 0xff8bd4d0), fallBottom},
                new float[] {0f, .42f, 1f}, Shader.TileMode.CLAMP);
        sunGlow = new RadialGradient(width * .54f, height * .16f, width * .43f,
                new int[] {color(0x43fff4c7, 0x1c92e8df), 0x00fff4c7}, null, Shader.TileMode.CLAMP);
        buildCliffShapes(width, height, scale);
    }

    private final Runnable invalidator = new Runnable() {
        @Override public void run() { if (running) invalidate(); }
    };

    private void loadOptions() {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(getContext());
        options = WaterfallOptions.resolve(p.getString("environment", "forest"), p.getString("lighting", "day"),
                p.getString("width", "curtain"), p.getString("speed", "natural"), p.getString("mist", "light"),
                p.getString("sunlight", "on"), p.getBoolean("randomize_all", false), new Random(System.currentTimeMillis()));
    }

    private int color(int day, int night) { return options.night ? night : day; }

    private void drawBackdrop(Canvas canvas, float time) {
        paint.setAlpha(255);
        paint.setShader(skyGradient);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        paint.setShader(null);
        if (options.sunlight && !options.night) {
            paint.setShader(sunGlow);
            canvas.drawRect(0, 0, getWidth(), getHeight() * .8f, paint);
            paint.setShader(null);
            paint.setColor(0x18fff5c8);
            for (int i = 0; i < 5; i++) {
                float center = getWidth() * (.34f + i * .085f) + (float) Math.sin(time * .09f + i) * 18f;
                paint.setStrokeWidth(getWidth() * .012f);
                canvas.drawLine(center, 0, center + getWidth() * .07f, getHeight() * .77f, paint);
            }
        }
    }

    private void buildCliffShapes(int width, int height, float scale) {
        float top = height * .12f;
        float bottom = height * .83f;
        float opening = width * options.width;
        float center = width * .5f;
        float leftEdge = center - opening * .5f;
        float rightEdge = center + opening * .5f;
        leftCliff.reset();
        leftCliff.moveTo(0, 0);
        leftCliff.lineTo(width * .36f, 0);
        leftCliff.cubicTo(width * .32f, top * .45f, leftEdge - 20 * scale, top * .68f, leftEdge, top);
        leftCliff.cubicTo(leftEdge - 8 * scale, height * .37f, leftEdge + 18 * scale, height * .57f,
                leftEdge - 15 * scale, bottom);
        leftCliff.lineTo(0, height);
        leftCliff.close();
        rightCliff.reset();
        rightCliff.moveTo(width, 0);
        rightCliff.lineTo(width * .64f, 0);
        rightCliff.cubicTo(width * .68f, top * .45f, rightEdge + 20 * scale, top * .68f, rightEdge, top);
        rightCliff.cubicTo(rightEdge + 8 * scale, height * .37f, rightEdge - 18 * scale, height * .57f,
                rightEdge + 15 * scale, bottom);
        rightCliff.lineTo(width, height);
        rightCliff.close();
        fallShape.reset();
        fallShape.moveTo(leftEdge, top);
        fallShape.cubicTo(leftEdge + 22 * scale, height * .32f, leftEdge - 14 * scale, height * .55f, center - opening * .35f, height * .72f);
        fallShape.quadTo(center, height * .77f, center + opening * .35f, height * .72f);
        fallShape.cubicTo(rightEdge + 14 * scale, height * .55f, rightEdge - 22 * scale, height * .32f, rightEdge, top);
        fallShape.close();
    }

    private void drawCliffs(Canvas canvas, float time) {
        int rock = options.environment == 2 ? color(0xff8b6046, 0xff483b39)
                : options.environment == 1 ? color(0xff526f68, 0xff35484a) : color(0xff395f4c, 0xff253d3a);
        paint.setColor(rock);
        canvas.drawPath(leftCliff, paint);
        canvas.drawPath(rightCliff, paint);
        drawRockBands(canvas, time);
        drawFoliage(canvas, time);
    }

    private void drawRockBands(Canvas canvas, float time) {
        for (int i = 0; i < rockX.length; i++) {
            float x = getWidth() * rockX[i];
            float y = getHeight() * rockY[i] + (float) Math.sin(time * .05f + i) * 1.5f;
            float size = getWidth() * rockScale[i];
            int base = options.environment == 2 ? color(0xffa77754, 0xff574642)
                    : options.environment == 1 ? color(0xff638478, 0xff3c5452)
                    : color(0xff52765c, 0xff304b43);
            int variation = (i % 3 == 0) ? color(0x34484631, 0x263c5c58)
                    : (i % 3 == 1) ? color(0x2251a085, 0x22558b85) : 0x18000000;
            float halfHeight = size * rockShape[i] * .54f;
            float skew = size * .35f;
            rockFacet.reset();
            rockFacet.moveTo(x - size, y - halfHeight * .25f);
            rockFacet.lineTo(x - size * .42f, y - halfHeight);
            rockFacet.lineTo(x + size * .52f, y - halfHeight * .72f);
            rockFacet.lineTo(x + size, y + halfHeight * .08f);
            rockFacet.lineTo(x + skew, y + halfHeight);
            rockFacet.lineTo(x - size * .76f, y + halfHeight * .62f);
            rockFacet.close();
            paint.setColor(base);
            canvas.drawPath(rockFacet, paint);
            paint.setColor(variation);
            canvas.drawLine(x - size * .55f, y - halfHeight * .55f, x + size * .44f, y - halfHeight * .5f, paint);
        }
    }

    private void drawFoliage(Canvas canvas, float time) {
        if (options.environment == 1) return;
        for (int i = 0; i < leafX.length; i++) {
            float x = getWidth() * leafX[i];
            float y = getHeight() * leafY[i];
            float size = getWidth() * leafScale[i];
            float sway = (float) Math.sin(time * .32f + i * 1.7f) * 4f;
            paint.setColor(options.environment == 2 ? color(0xff617249, 0xff40503d)
                    : color((i % 2 == 0) ? 0xff5c8b54 : 0xff3e714d, 0xff345449));
            canvas.save();
            canvas.rotate(leafAngle[i], x + sway, y);
            canvas.drawOval(x - size + sway, y - size * .2f, x + size + sway, y + size * .2f, paint);
            paint.setColor(color(0x8869a775, 0x77518b78));
            canvas.drawLine(x - size * .65f + sway, y, x + size * .7f + sway, y, paint);
            canvas.restore();
        }
    }

    private void drawPool(Canvas canvas, float time) {
        paint.setAlpha(255);
        paint.setShader(poolGradient);
        canvas.drawRect(0, getHeight() * .70f, getWidth(), getHeight(), paint);
        paint.setShader(null);
        float centerX = getWidth() * .5f;
        float centerY = getHeight() * .79f;
        for (int i = 0; i < 10; i++) {
            float phase = (time * options.speed * .22f + i / 10f) % 1f;
            float rx = getWidth() * (.045f + phase * .22f);
            float ry = getHeight() * (.006f + phase * .035f);
            paint.setColor(color(0x3889ded2, 0x3377e2db));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(Math.max(1f, getWidth() * .0014f));
            canvas.drawOval(centerX - rx, centerY - ry, centerX + rx, centerY + ry, paint);
        }
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < 5; i++) {
            float x = getWidth() * (.33f + i * .085f) + (float) Math.sin(time * .7f + i) * 7f;
            float y = getHeight() * (.715f + (i % 2) * .025f);
            paint.setColor(color(0x74e3f4e3, 0x86bbf4e6));
            canvas.drawCircle(x, y, getWidth() * (.006f + (i % 3) * .002f), paint);
        }
    }

    private void drawWaterfall(Canvas canvas, float time) {
        paint.setAlpha(255);
        paint.setShader(fallGradient);
        canvas.drawPath(fallShape, paint);
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(getWidth() * .004f);
        paint.setColor(color(0x4798d6d0, 0x554bb4ba));
        canvas.drawPath(fallShape, paint);
        paint.setStyle(Paint.Style.FILL);
        float width = getWidth() * options.width;
        float left = getWidth() * .5f - width * .5f;
        float top = getHeight() * .14f;
        float fallHeight = getHeight() * .59f;
        for (int i = 0; i < 29; i++) {
            float u = i / 28f;
            float x = left + u * width;
            float phase = (time * options.speed * (.62f + (i % 5) * .08f) + i * .17f) % 1f;
            float y = top + phase * fallHeight;
            float wave = (float) Math.sin(time * .9f * options.speed + i * 1.41f) * width * .012f;
            float alpha = 36 + (i % 4) * 16;
            paint.setColor(color(((int) alpha << 24) | 0xeffff3, ((int) alpha << 24) | 0xc4fff8));
            paint.setStrokeWidth(getWidth() * (.0014f + (i % 3) * .00035f));
            canvas.drawLine(x + wave, y, x + wave * .6f, Math.min(top + fallHeight, y + getHeight() * (.10f + (i % 4) * .025f)), paint);
        }
        paint.setColor(color(0x9efffff4, 0x9edbfffa));
        paint.setStrokeWidth(getWidth() * .003f);
        for (int i = 0; i < 11; i++) {
            float x = left + width * (i / 10f);
            float sway = (float) Math.sin(time * .8f * options.speed + i) * getWidth() * .006f;
            canvas.drawLine(x, top, x + sway, top + fallHeight * .87f, paint);
        }
        drawFoam(canvas, time, left, width, top + fallHeight);
    }

    private void drawFoam(Canvas canvas, float time, float left, float width, float y) {
        for (int i = 0; i < 24; i++) {
            float phase = (time * options.speed * .5f + i * .041f) % 1f;
            float x = left + width * (.15f + .7f * ((i * 37 % 23) / 22f))
                    + (float) Math.sin(time * 1.7f + i) * width * .035f;
            float rise = (1f - phase) * getHeight() * .055f;
            paint.setColor(color(0x82f8fff5, 0x96d5fff9));
            canvas.drawCircle(x, y - rise, getWidth() * (.0025f + (i % 3) * .001f), paint);
        }
    }

    private void drawMist(Canvas canvas, float time) {
        paint.setAlpha(255);
        for (int i = 0; i < options.mist; i++) {
            float drift = (float) Math.sin(time * .35f + mistPhase[i]) * getWidth() * .022f;
            float rise = (time * options.speed * (8f + mistSize[i] * 1.8f) + mistY[i] * getHeight()) % (getHeight() * .24f);
            float x = getWidth() * (.5f + (mistX[i] - .5f) * .46f) + drift;
            float y = getHeight() * .68f - rise;
            int alpha = (int) (18 + 30 * (.5f + .5f * Math.sin(time * .7f + mistPhase[i])));
            paint.setColor(color((alpha << 24) | 0xe7fff7, (alpha << 24) | 0xc5fff8));
            canvas.drawCircle(x, y, mistSize[i] * (getWidth() / 1920f), paint);
        }
    }
}
