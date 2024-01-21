package at.onlyquiz.util;


public class GeneralSettings {

    private static boolean colorBlind = false;

    public static boolean isColorBlind() {
        return colorBlind;
    }

    public static void setColorBlind(boolean colorBlind) {
        GeneralSettings.colorBlind = colorBlind;
    }
}
