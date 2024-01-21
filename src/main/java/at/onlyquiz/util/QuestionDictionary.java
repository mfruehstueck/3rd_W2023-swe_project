package at.onlyquiz.util;

import at.debugtools.DebugTools;
import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.model.question.GameQuestion;
import at.onlyquiz.util.csvParser.CSV_Column;
import at.onlyquiz.util.csvParser.CSV_Reader;
import at.onlyquiz.util.csvParser.CSV_Writer;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

import static at.onlyquiz.util.Configuration.DEFAULT_BASEPATH_QUESTIONNARES;
import static at.onlyquiz.util.Configuration.DEFAULT_CSV_HEADEREND_POSITION;

public class QuestionDictionary {
    //N: HashMap: CsvFile, TimesSelected, Difficulty, LineIndex
    private static HashMap<Path, HashMap<Integer, HashMap<Difficulty, List<Integer>>>> dictionary;
    private static HashMap<String, Path> questionnaireFilePaths;

    //N: read csv-files on app start
    public static void init() {
        questionnaireFilePaths = get_ListOfQuestionnaireFiles();
        dictionary = read_questionnaireFiles();
    }

    public static List<GameQuestion> get_allQuestions(String questionnaireName) {
        Path csvPath = get_csvPath_from(questionnaireName);

        List<GameQuestion> out_gameQuestions = new ArrayList<>();
        List<String[]> csvLines;

        try {
            csvLines = CSV_Reader.get_csvLines_all(csvPath);
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }

        for (int i = DEFAULT_CSV_HEADEREND_POSITION; i < csvLines.size(); i++) {
            out_gameQuestions.add(new GameQuestion(i, csvLines.get(i)));
        }

        return out_gameQuestions;
    }

    public static List<GameQuestion> get_randomQuestions(List<String> questionnaireFileNames, Difficulty difficulty, int amount) {
        List<GameQuestion> out_questions = new ArrayList<>();
        List<Integer> current_listOf_lineIdx = new ArrayList<>();
        HashMap<Difficulty, List<Integer>> current_mapOf_difficulty = new HashMap<>();
        GameQuestion current_gameQuestion;
        Random random = new Random();
        int randomFileID;
        int randomLineIdx;
        Path pathOfQuestionnaireFile;
        String[] current_line;
        HashMap<Integer, HashMap<Difficulty, List<Integer>>> current_mapOf_timesSelected;

        for (int i = 0; i < amount; i++) {
            if (questionnaireFileNames.isEmpty()) return null;
            randomFileID = random.nextInt(0, questionnaireFileNames.size());
            pathOfQuestionnaireFile = questionnaireFilePaths.get(questionnaireFileNames.get(randomFileID));
            current_mapOf_timesSelected = dictionary.get(pathOfQuestionnaireFile);

            for (Integer timesSelected : current_mapOf_timesSelected.keySet()) {
                current_mapOf_difficulty = current_mapOf_timesSelected.get(timesSelected);
                if (!(current_mapOf_difficulty == null || current_mapOf_difficulty.isEmpty())) {
                    current_listOf_lineIdx = current_mapOf_difficulty.get(difficulty);
                    if (current_listOf_lineIdx == null || current_listOf_lineIdx.isEmpty()) continue;
                    break;
                }

            }
            if (current_mapOf_difficulty == null || current_mapOf_difficulty.isEmpty() || current_listOf_lineIdx == null) {
                questionnaireFileNames.remove(questionnaireFileNames.get(randomFileID));
                if (questionnaireFileNames.isEmpty())
                    System.out.println(DebugTools.debugLine(new Throwable()) + "failed questionnaireFileNames is empty");
                i--;
                continue;
            }

            randomLineIdx = random.nextInt(0, current_listOf_lineIdx.size());

            try {
                current_line = CSV_Reader.get_line_by_lineIdx(pathOfQuestionnaireFile, current_listOf_lineIdx.get(randomLineIdx));

                current_gameQuestion = new GameQuestion(current_listOf_lineIdx.get(randomLineIdx), current_line);
                current_gameQuestion.incrementTimesSelected();

                //update csv
                CSV_Writer.update_line(pathOfQuestionnaireFile, current_listOf_lineIdx.get(randomLineIdx), current_gameQuestion.getCsvLine());

                //update dictionary
                Integer lineNumberOfCurrentQuestion = current_listOf_lineIdx.get(randomLineIdx);
                current_listOf_lineIdx.remove(randomLineIdx);

                Integer next_timeSelected = current_gameQuestion.getTimesSelected();
                HashMap<Difficulty, List<Integer>> next_mapOf_difficulty = current_mapOf_timesSelected.computeIfAbsent(next_timeSelected, k -> new HashMap<>());
                List<Integer> next_listOf_lineIdx = next_mapOf_difficulty.computeIfAbsent(difficulty, k -> new ArrayList<>());

                next_mapOf_difficulty.put(difficulty, next_listOf_lineIdx);
                next_listOf_lineIdx.add(lineNumberOfCurrentQuestion);

                out_questions.add(current_gameQuestion);
            } catch (IOException | CsvException e) {
                throw new RuntimeException(e);
            }
        }

    return out_questions;
  }

//    public static void update_questions_bulk(Path csvPath, List<GameQuestion> questions) {
//        HashMap<Integer, String[]> csvLines = new HashMap<>();
//
//        for (GameQuestion q : questions) { csvLines.put(q.getLineNumber(), q.getCsvLine()); }
//
//        try {
//            CSV_Writer.update_lines_bulk(csvPath, csvLines);
//        } catch (IOException | CsvException e) {
//            throw new RuntimeException(e);
//        }
//    }

  //NSEC: instance getter
  public static HashMap<String, Path> get_QuestionnaireFiles() { return questionnaireFilePaths; }
  public static Path get_csvPath_from(String questionnaireName) {
    return Path.of(DEFAULT_BASEPATH_QUESTIONNARES + File.separator + questionnaireName + ".csv");
  }
  public static HashMap<Path, HashMap<Integer, HashMap<Difficulty, List<Integer>>>> get_dictionary() { return dictionary; }

    private static HashMap<String, Path> get_ListOfQuestionnaireFiles() {
        HashMap<String, Path> csvPaths = new HashMap<>();
        List<Path> pathList;

        try {
            File[] csvFiles_all_array = Objects.requireNonNull(new File(DEFAULT_BASEPATH_QUESTIONNARES.toString()).listFiles());
            pathList = Stream.of(csvFiles_all_array).filter(file -> !file.isDirectory()).map(File::getPath).map(Path::of).toList();
            for (Path p : pathList) {
                csvPaths.put(p.getFileName().toString().split("\\.")[0], p);
            }
            return csvPaths;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void delete_gameQuestion(String questionnaireName, int lineIdx) {
        Path csvPath = get_csvPath_from(questionnaireName);

        if (lineIdx < 0) return;

        try {
            CSV_Writer.update_line(csvPath, lineIdx, null);
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        } finally {
            init();
        }
    }

    public static void update_gameQuestion(String nameOfQuestionnaire, GameQuestion gameQuestion) {
        Path csvPath = get_csvPath_from(nameOfQuestionnaire);

        if (!Files.exists(csvPath)) {
            try {
                Files.createFile(csvPath);
                questionnaireFilePaths.put(nameOfQuestionnaire, csvPath);

                CSV_Column[] values = CSV_Column.values();
                String[] csvHeader = new String[values.length];
                Arrays.stream(values).forEach(value -> csvHeader[value.ordinal()] = value.name());

                CSV_Writer.append_line(csvPath, csvHeader);
            } catch (IOException | CsvException e) {
                throw new RuntimeException(e);
            }
        }


        try {
            if (gameQuestion.getLineIdx() < 0) {
                System.out.println(Arrays.toString(gameQuestion.getCsvLine()));
                CSV_Writer.append_line(csvPath, gameQuestion.getCsvLine());
            } else {
                CSV_Writer.update_line(csvPath, gameQuestion.getLineIdx(), gameQuestion.getCsvLine());
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        } finally {
            init();
        }
    }

    public static HashMap<Path, HashMap<Integer, HashMap<Difficulty, List<Integer>>>> read_questionnaireFiles() {
        HashMap<Path, HashMap<Integer, HashMap<Difficulty, List<Integer>>>> out_dictionary = new HashMap<>();
        HashMap<Integer, HashMap<Difficulty, List<Integer>>> current_mapOf_lineNumbers_byTimesSelected;

        for (Path csvPath : questionnaireFilePaths.values()) {
            try {
                current_mapOf_lineNumbers_byTimesSelected = CSV_Reader.get_mapOf_lineIdxs_byTimesSelected(csvPath);
                out_dictionary.put(csvPath, current_mapOf_lineNumbers_byTimesSelected);
            } catch (IOException | CsvException e) {
                throw new RuntimeException(e);
            }
        }

        return out_dictionary;
    }
}
