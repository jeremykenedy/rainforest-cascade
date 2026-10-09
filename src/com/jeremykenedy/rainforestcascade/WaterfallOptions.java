package com.jeremykenedy.rainforestcascade;

import java.util.Random;

public final class WaterfallOptions {
    public final int environment;
    public final boolean night;
    public final float width;
    public final float speed;
    public final int mist;
    public final boolean sunlight;

    private WaterfallOptions(int environment, boolean night, float width, float speed, int mist,
            boolean sunlight) {
        this.environment = environment;
        this.night = night;
        this.width = width;
        this.speed = speed;
        this.mist = mist;
        this.sunlight = sunlight;
    }

    public static WaterfallOptions resolve(String environment, String lighting, String width, String speed,
            String mist, String sunlight, boolean randomizeAll, Random random) {
        String selectedEnvironment = choose(environment, randomizeAll, random,
                "forest", "stone", "canyon");
        String selectedLighting = choose(lighting, randomizeAll, random, "day", "night");
        String selectedWidth = choose(width, randomizeAll, random, "curtain", "narrow", "wide");
        String selectedSpeed = choose(speed, randomizeAll, random, "natural", "slow", "fast");
        String selectedMist = choose(mist, randomizeAll, random, "light", "off", "heavy");
        String selectedSunlight = choose(sunlight, randomizeAll, random, "on", "off");
        return new WaterfallOptions(environmentIndex(selectedEnvironment), "night".equals(selectedLighting),
                widthValue(selectedWidth), speedValue(selectedSpeed), mistCount(selectedMist),
                "on".equals(selectedSunlight));
    }

    static String choose(String selected, boolean randomizeAll, Random random, String... values) {
        if (randomizeAll || "random".equals(selected)) return values[random.nextInt(values.length)];
        for (String value : values) if (value.equals(selected)) return value;
        return values[0];
    }

    static int environmentIndex(String value) {
        if ("stone".equals(value)) return 1;
        if ("canyon".equals(value)) return 2;
        return 0;
    }

    static float widthValue(String value) {
        if ("narrow".equals(value)) return 0.42f;
        if ("wide".equals(value)) return 0.78f;
        return 0.60f;
    }

    static float speedValue(String value) {
        if ("slow".equals(value)) return 0.58f;
        if ("fast".equals(value)) return 1.55f;
        return 1.0f;
    }

    static int mistCount(String value) {
        if ("off".equals(value)) return 0;
        if ("heavy".equals(value)) return 72;
        return 30;
    }
}
