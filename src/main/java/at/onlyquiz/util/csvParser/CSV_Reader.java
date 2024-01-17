package at.onlyquiz.util.csvParser;

import at.onlyquiz.model.question.Difficulty;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static at.onlyquiz.util.Configuration.DEFAULT_CSV_HEADEREND_POSITION;

public class CSV_Reader {

  //TODO Manuel -> the first two question in the question file get ignored. why?
  static public String[] get_line_by_lineNumber(Path csvPath, int lineNumber) throws IOException, CsvException {
    return get_csvLines_all(csvPath).get(lineNumber);
  }
  static public List<String[]> get_lines_bulk(Path csvPath, List<Integer> lineNumbers) throws IOException, CsvException {
    List<String[]> lines_all = get_csvLines_all(csvPath);
    List<String[]> lines_bulk = new ArrayList<>();

    for (int i = DEFAULT_CSV_HEADEREND_POSITION; i < lines_all.size(); i++) {
      if (lineNumbers.contains(i)) lines_bulk.add(lines_all.get(i));
    }

    return lines_bulk;
  }


  //N: HashMap<Integer, Integer>(lineNumbers_by_selectedCounter) = selectedCounter, numberOfLinesWithSelectedCounter
  static public HashMap<Difficulty, HashMap<Integer, List<Integer>>> get_lineNumbers_all_by_timesSelected(Path csvPath) throws IOException, CsvException {
    HashMap<Difficulty, HashMap<Integer, List<Integer>>> lineNumbers_all_by_timesSelected = new HashMap<>();
    HashMap<Integer, List<Integer>> current_mapOf_lineNumbers_by_timesSelected;
    List<Integer> current_listOf_current_timesSelected;
    List<String[]> lines_all = get_csvLines_all(csvPath);
    String[] current_line;
    int current_timesSelected;
    Difficulty current_difficulty;

    for (int i = DEFAULT_CSV_HEADEREND_POSITION; i < lines_all.size(); i++) {
      current_line = lines_all.get(i);
      current_difficulty = Difficulty.valueOf(current_line[CSV_Column.DIFFICULTY.ordinal()]);
      current_timesSelected = Integer.parseInt(current_line[CSV_Column.TIMES_SELECTED.ordinal()]);
      current_mapOf_lineNumbers_by_timesSelected = lineNumbers_all_by_timesSelected.computeIfAbsent(current_difficulty, k -> new HashMap<>());
      current_listOf_current_timesSelected = lineNumbers_all_by_timesSelected.get(current_difficulty).get(current_timesSelected);

      if (current_listOf_current_timesSelected == null) {
        current_listOf_current_timesSelected = new ArrayList<>();
        current_mapOf_lineNumbers_by_timesSelected.put(current_timesSelected, current_listOf_current_timesSelected);
      } else current_listOf_current_timesSelected.add(i);
    }

    return lineNumbers_all_by_timesSelected;
  }

  static public List<String[]> get_csvLines_all(Path csvPath) throws IOException, CsvException {
    try (CSVReader csvReader = new CSVReader(Files.newBufferedReader(csvPath))) { return csvReader.readAll(); }
  }
}
