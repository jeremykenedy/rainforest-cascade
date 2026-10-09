package com.jeremykenedy.rainforestcascade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.junit.Test;

public final class WaterfallOptionsTest {
    @Test
    public void resolvesExplicitControls() {
        WaterfallOptions options = WaterfallOptions.resolve(
                "canyon", "night", "wide", "fast", "heavy", "on", false, new Random(4));
        assertEquals(2, options.environment);
        assertTrue(options.night);
        assertEquals(.78f, options.width, 0.001f);
        assertEquals(1.55f, options.speed, 0.001f);
        assertEquals(72, options.mist);
        assertTrue(options.sunlight);
    }

    @Test
    public void resolvesFallbacksAndEveryOptionValue() {
        assertEquals(0, WaterfallOptions.environmentIndex("forest"));
        assertEquals(1, WaterfallOptions.environmentIndex("stone"));
        assertEquals(2, WaterfallOptions.environmentIndex("canyon"));
        assertEquals(0, WaterfallOptions.environmentIndex("unsupported"));
        assertEquals(.42f, WaterfallOptions.widthValue("narrow"), 0.001f);
        assertEquals(.60f, WaterfallOptions.widthValue("curtain"), 0.001f);
        assertEquals(.78f, WaterfallOptions.widthValue("wide"), 0.001f);
        assertEquals(.60f, WaterfallOptions.widthValue("unsupported"), 0.001f);
        assertEquals(.58f, WaterfallOptions.speedValue("slow"), 0.001f);
        assertEquals(1f, WaterfallOptions.speedValue("natural"), 0.001f);
        assertEquals(1.55f, WaterfallOptions.speedValue("fast"), 0.001f);
        assertEquals(1f, WaterfallOptions.speedValue("unsupported"), 0.001f);
        assertEquals(0, WaterfallOptions.mistCount("off"));
        assertEquals(30, WaterfallOptions.mistCount("light"));
        assertEquals(72, WaterfallOptions.mistCount("heavy"));
        assertEquals(30, WaterfallOptions.mistCount("unsupported"));
        WaterfallOptions defaults = WaterfallOptions.resolve(
                "unsupported", "unsupported", "unsupported", "unsupported", "unsupported", "unsupported", false,
                new Random(2));
        assertEquals(0, defaults.environment);
        assertFalse(defaults.night);
        assertEquals(.60f, defaults.width, 0.001f);
        assertEquals(1f, defaults.speed, 0.001f);
        assertEquals(30, defaults.mist);
        assertTrue(defaults.sunlight);
    }

    @Test
    public void randomAndMasterRandomizationUseSupportedChoices() {
        Set<Integer> environments = new HashSet<>();
        Set<Float> widths = new HashSet<>();
        Set<Float> speeds = new HashSet<>();
        Set<Integer> mists = new HashSet<>();
        for (int seed = 0; seed < 120; seed++) {
            WaterfallOptions perSetting = WaterfallOptions.resolve(
                    "random", "random", "random", "random", "random", "random", false, new Random(seed));
            assertTrue(perSetting.environment >= 0 && perSetting.environment <= 2);
            assertTrue(Arrays.asList(.42f, .60f, .78f).contains(perSetting.width));
            assertTrue(Arrays.asList(.58f, 1f, 1.55f).contains(perSetting.speed));
            assertTrue(Arrays.asList(0, 30, 72).contains(perSetting.mist));
            environments.add(perSetting.environment);
            widths.add(perSetting.width);
            speeds.add(perSetting.speed);
            mists.add(perSetting.mist);
            WaterfallOptions all = WaterfallOptions.resolve(
                    "forest", "day", "narrow", "slow", "off", "off", true, new Random(seed));
            assertTrue(all.environment >= 0 && all.environment <= 2);
            assertTrue(Arrays.asList(.42f, .60f, .78f).contains(all.width));
            assertTrue(Arrays.asList(.58f, 1f, 1.55f).contains(all.speed));
            assertTrue(Arrays.asList(0, 30, 72).contains(all.mist));
        }
        assertEquals(3, environments.size());
        assertEquals(3, widths.size());
        assertEquals(3, speeds.size());
        assertEquals(3, mists.size());
    }

    @Test
    public void validatesProviderValuesAndRejectsUnsupportedInput() {
        assertTrue(SettingsValues.isSupported("environment", "forest"));
        assertTrue(SettingsValues.isSupported("environment", "stone"));
        assertTrue(SettingsValues.isSupported("environment", "canyon"));
        assertTrue(SettingsValues.isSupported("environment", "random"));
        assertTrue(SettingsValues.isSupported("lighting", "day"));
        assertTrue(SettingsValues.isSupported("lighting", "night"));
        assertTrue(SettingsValues.isSupported("lighting", "random"));
        assertTrue(SettingsValues.isSupported("width", "narrow"));
        assertTrue(SettingsValues.isSupported("width", "curtain"));
        assertTrue(SettingsValues.isSupported("width", "wide"));
        assertTrue(SettingsValues.isSupported("width", "random"));
        assertTrue(SettingsValues.isSupported("speed", "slow"));
        assertTrue(SettingsValues.isSupported("speed", "natural"));
        assertTrue(SettingsValues.isSupported("speed", "fast"));
        assertTrue(SettingsValues.isSupported("speed", "random"));
        assertTrue(SettingsValues.isSupported("mist", "off"));
        assertTrue(SettingsValues.isSupported("mist", "light"));
        assertTrue(SettingsValues.isSupported("mist", "heavy"));
        assertTrue(SettingsValues.isSupported("mist", "random"));
        assertTrue(SettingsValues.isSupported("sunlight", "off"));
        assertTrue(SettingsValues.isSupported("sunlight", "on"));
        assertTrue(SettingsValues.isSupported("sunlight", "random"));
        assertTrue(SettingsValues.isSupported("randomize_all", "true"));
        assertTrue(SettingsValues.isSupported("randomize_all", "false"));
        assertFalse(SettingsValues.isSupported(null, "forest"));
        assertFalse(SettingsValues.isSupported("environment", null));
        assertFalse(SettingsValues.isSupported("environment", "volcano"));
        assertFalse(SettingsValues.isSupported("lighting", "twilight"));
        assertFalse(SettingsValues.isSupported("width", "infinite"));
        assertFalse(SettingsValues.isSupported("speed", "turbo"));
        assertFalse(SettingsValues.isSupported("mist", "extreme"));
        assertFalse(SettingsValues.isSupported("sunlight", "always"));
        assertFalse(SettingsValues.isSupported("randomize_all", "yes"));
        assertFalse(SettingsValues.isSupported("other", "value"));
    }
}
