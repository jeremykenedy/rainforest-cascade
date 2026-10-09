package com.jeremykenedy.rainforestcascade;

public final class SettingsValues {
    private SettingsValues() {}

    public static boolean isSupported(String key, String value) {
        if (key == null || value == null) return false;
        if ("environment".equals(key)) return oneOf(value, "forest", "stone", "canyon", "random");
        if ("lighting".equals(key)) return oneOf(value, "day", "night", "random");
        if ("width".equals(key)) return oneOf(value, "narrow", "curtain", "wide", "random");
        if ("speed".equals(key)) return oneOf(value, "slow", "natural", "fast", "random");
        if ("mist".equals(key)) return oneOf(value, "off", "light", "heavy", "random");
        if ("sunlight".equals(key)) return oneOf(value, "off", "on", "random");
        if ("randomize_all".equals(key)) return oneOf(value, "true", "false");
        return false;
    }

    private static boolean oneOf(String value, String... allowed) {
        for (String option : allowed) if (option.equals(value)) return true;
        return false;
    }
}
