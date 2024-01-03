package at.onlyquiz.util.csvParser;

import at.onlyquiz.model.question.Difficulty;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static at.onlyquiz.util.Configuration.DEFAULT_CSV_HEADEREND_POSITION;
import static at.onlyquiz.util.Configuration.DEFAULT_CSV_SEPERATOR;

public class CSV_Reader {
    static public String[] get_line_by_lineNumber(Path csvPath, int lineNumber) throws IOException, CsvException { return get_lines_all(csvPath).get(lineNumber); }
    static public List<String[]> get_lines_bulk(Path csvPath, List<Integer> lineNumbers) throws IOException, CsvException {
        List<String[]> lines_all = get_lines_all(csvPath);
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
        List<String[]> lines_all = get_lines_all(csvPath);
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

    static private List<String[]> get_lines_all(Path csvPath) throws IOException, CsvException {
        List<String[]> lines_all = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(csvPath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                List<String[]> csvLines_all = csvReader.readAll();
                for (int i = 1; i < csvLines_all.size(); i++) lines_all.add(csvLines_all.get(i)[0].split(String.valueOf(DEFAULT_CSV_SEPERATOR)));
            }
        }

        return lines_all;
    }
}
