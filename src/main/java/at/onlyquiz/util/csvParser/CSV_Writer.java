package at.onlyquiz.util.csvParser;

import at.debugtools.DebugTools;
import at.onlyquiz.util.Configuration;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class CSV_Writer {
    static public void update_line(Path csvPath, int lineIdx, String[] entry) throws IOException, CsvException {
        List<String[]> csvLines_all = CSV_Reader.get_csvLines_all(csvPath);
        String[] current_csvLine;

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvPath.toString()))) {
            if (entry == null && lineIdx >= Configuration.DEFAULT_CSV_HEADEREND_POSITION && !csvLines_all.isEmpty() && lineIdx < csvLines_all.size()) csvLines_all.remove(lineIdx);
            for (int i = 0; i < csvLines_all.size(); i++) {
                current_csvLine = (i == lineIdx && entry != null) ? entry : csvLines_all.get(i);
                writer.writeNext(current_csvLine);
            }
        }
    }

    static public void append_line(Path csvPath, String[] line) throws IOException, CsvException {
        List<String[]> csvLines_all = CSV_Reader.get_csvLines_all(csvPath);
        csvLines_all.add(line);
        System.out.println(DebugTools.debugLine(new Throwable()) + Arrays.toString(line));

        try (CSVWriter writer = new CSVWriter(Files.newBufferedWriter(csvPath, StandardOpenOption.WRITE))) {
            for (String[] csvLine : csvLines_all) { writer.writeNext(csvLine); }
        }
    }
}