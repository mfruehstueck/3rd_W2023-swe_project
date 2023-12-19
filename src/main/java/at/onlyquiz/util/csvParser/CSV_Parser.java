package at.onlyquiz.util.csvParser;

import java.net.URL;

public class CSV_Parser {
    private final URL pathToCSV;

    public CSV_Parser(URL pathToFile) {
        this.pathToCSV = pathToFile;
    }

    public URL get_pathToCSV() {
        return this.pathToCSV;
    }
}
