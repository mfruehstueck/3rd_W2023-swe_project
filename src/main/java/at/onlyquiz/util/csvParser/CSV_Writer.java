package at.onlyquiz.util.csvParser;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.net.URL;

public class CSV_Writer extends CSV_Parser {
    //NSEC: Singleton
    private static CSV_Writer __instance__;
    public static CSV_Writer getInstance(URL pathToFile) { return (__instance__ != null) ? __instance__ : new CSV_Writer(pathToFile); }
    private CSV_Writer(URL pathToFile) { super(pathToFile); }
    public static void close() { __instance__ = null; }

    public boolean write() throws IOException, CsvValidationException {
        return false;
    }
}
