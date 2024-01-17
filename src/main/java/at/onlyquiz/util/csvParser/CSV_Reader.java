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
  static public String[] get_line_by_lineIdx(Path csvPath, int lineIdx) throws IOException, CsvException {
    return get_csvLines_all(csvPath).get(lineIdx);
  }
  static public List<String[]> get_lines_bulk(Path csvPath, List<Integer> listOf_lineIdxs) throws IOException, CsvException {
    List<String[]> lines_all = get_csvLines_all(csvPath);
    List<String[]> lines_bulk = new ArrayList<>();

    for (int i = DEFAULT_CSV_HEADEREND_POSITION; i < lines_all.size(); i++) {
      if (listOf_lineIdxs.contains(i)) lines_bulk.add(lines_all.get(i));
    }

    return lines_bulk;
  }

  //N: HashMap<Integer, Integer>(lineNumbers_by_selectedCounter) = selectedCounter, numberOfLinesWithSelectedCounter
  static public HashMap<Integer, HashMap<Difficulty, List<Integer>>> get_mapOf_lineIdxs_byTimesSelected(Path csvPath) throws IOException, CsvException {
    HashMap<Integer, HashMap<Difficulty, List<Integer>>> out_lineIdxs_all_byTimesSelected = new HashMap<>();
    HashMap<Difficulty, List<Integer>> current_mapOf_lineIdxs_byDifficulty;
    int current_timesSelected;
    List<Integer> current_listOf_current_timesSelected;
    List<String[]> lines_all = get_csvLines_all(csvPath);
    String[] current_line;
    Difficulty current_difficulty;

    for (int i = DEFAULT_CSV_HEADEREND_POSITION; i < lines_all.size(); i++) {
      current_line = lines_all.get(i);
      current_difficulty = Difficulty.valueOf(current_line[CSV_Column.DIFFICULTY.ordinal()]);
      current_timesSelected = Integer.parseInt(current_line[CSV_Column.TIMES_SELECTED.ordinal()]);

      current_mapOf_lineIdxs_byDifficulty = out_lineIdxs_all_byTimesSelected.computeIfAbsent(current_timesSelected, k -> new HashMap<>());
      current_listOf_current_timesSelected = current_mapOf_lineIdxs_byDifficulty.computeIfAbsent(current_difficulty, k -> new ArrayList<>());

      current_listOf_current_timesSelected.add(i);
    }

    return out_lineIdxs_all_byTimesSelected;
  }

  static public List<String[]> get_csvLines_all(Path csvPath) throws IOException, CsvException {
    try (CSVReader csvReader = new CSVReader(Files.newBufferedReader(csvPath))) { return csvReader.readAll(); }
  }
}
