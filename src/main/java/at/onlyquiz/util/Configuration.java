package at.onlyquiz.util;

import at.onlyquiz.MainApplication;

import java.net.URL;

public class Configuration {
    public static URL DEFAULT_URL_QUESTIONNARES = MainApplication.class.getResource("at.onlyquiz/questionnaires");
    public static Character DEFAULT_CSV_SEPERATOR = ';';
}
