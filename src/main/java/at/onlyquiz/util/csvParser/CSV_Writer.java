package at.onlyquiz.util.csvParser;

import at.debugtools.DebugTools;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static at.debugtools.DebugTools.debugLine;

public class CSV_Writer {
    static public void update_line(Path csvPath, int lineNumber, String[] entry) throws IOException, CsvException {
        List<String[]> csvLines_all = CSV_Reader.get_csvLines_all(csvPath);
        String[] current_csvLine;

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvPath.toString()))) {
            for (int i = 0; i < csvLines_all.size(); i++) {
                current_csvLine = (i == lineNumber) ? entry : csvLines_all.get(i);
                writer.writeNext(current_csvLine);
            }
        }
    }

    static public void update_lines_bulk(Path csvPath, HashMap<Integer, String[]> lines) throws IOException, CsvException {
        List<String[]> csvLines_all = CSV_Reader.get_csvLines_all(csvPath);
        String[] current_csvLine;


        try (CSVWriter writer = new CSVWriter(Files.newBufferedWriter(csvPath, StandardOpenOption.WRITE))) {
            System.out.println(debugLine(new Throwable()) + "update csv = " + csvPath);
            for (int i = 0; i < csvLines_all.size(); i++) {
                current_csvLine = (lines.containsKey(i)) ? lines.get(i) : csvLines_all.get(i);
                System.out.println(i + " - " + Arrays.toString(current_csvLine));
                writer.writeNext(current_csvLine);
            }
        }
    }

    //TODO: try making writing generic
//    static private void write_line(Path csvPath, FunctionalInterface asdf) throws IOException, CsvException {
//        List<String[]> csvLines_all = CSV_Reader.get_lines_all(csvPath);
//        String[] current_csvLine;
//
//        try (CSVWriter writer = new CSVWriter(new FileWriter(csvPath.toString()))) {
//            for (int i = 0; i < csvLines_all.size(); i++) {
//                current_csvLine =
//                writer.writeNext(current_csvLine);
//            }
//        }
//    }

    static public void append_lines(Path csvPath, List<String[]> lines) throws IOException, CsvException {
        List<String[]> csvLines_all = CSV_Reader.get_csvLines_all(csvPath);
        csvLines_all.addAll(lines);
        System.out.println(DebugTools.debugLine(new Throwable()));

        try (CSVWriter writer = new CSVWriter(Files.newBufferedWriter(csvPath, StandardOpenOption.WRITE))) {
            for (String[] csvLine : csvLines_all) { writer.writeNext(csvLine); }
        }
    }
}