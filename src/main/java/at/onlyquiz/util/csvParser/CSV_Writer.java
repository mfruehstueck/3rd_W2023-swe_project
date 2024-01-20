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
import java.util.HashMap;
import java.util.List;

import static at.debugtools.DebugTools.debugLine;

public class CSV_Writer {
  static public void update_line(Path csvPath, int lineIdx, String[] entry) throws IOException, CsvException {
    List<String[]> csvLines_all = CSV_Reader.get_csvLines_all(csvPath);
    String[] current_csvLine;

    try (CSVWriter writer = new CSVWriter(new FileWriter(csvPath.toString()))) {
      if(entry == null && lineIdx >= Configuration.DEFAULT_CSV_HEADEREND_POSITION && !csvLines_all.isEmpty()) csvLines_all.remove(lineIdx);
      for (int i = 0; i < csvLines_all.size(); i++) {
        current_csvLine = (i == lineIdx && entry != null) ? entry : csvLines_all.get(i);
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

  static public void append_line(Path csvPath, String[] line) throws IOException, CsvException {
    List<String[]> csvLines_all = CSV_Reader.get_csvLines_all(csvPath);
    csvLines_all.add(line);
    System.out.println(DebugTools.debugLine(new Throwable()) + Arrays.toString(line));

    try (CSVWriter writer = new CSVWriter(Files.newBufferedWriter(csvPath, StandardOpenOption.WRITE))) {
      for (String[] csvLine : csvLines_all) { writer.writeNext(csvLine); }
    }
  }
}