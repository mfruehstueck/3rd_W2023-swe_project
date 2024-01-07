package at.onlyquiz.util;

import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.model.question.GameQuestion;
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

public class QuestionDictionary {
    //N: HashMap: Difficulty, CsvFile, TimesSelected, LineNumber
    private static HashMap<Difficulty, HashMap<Path, HashMap<Integer, List<Integer>>>> dictionary;
    private static HashMap<String, Path> questionnaireFiles;

    //N: read csv-files on app start
    public static void init() {
        questionnaireFiles = get_ListOfQuestionnaireFiles();
        dictionary = readQuestionnaireFiles();
    }

    public static List<GameQuestion> get_Questions(List<String> questionnaireFileNames, Difficulty difficulty, int amount) {
        List<GameQuestion> questions = new ArrayList<>();
        List<Integer> current_lineNumbers;
        List<Integer> selectedRandomLineNumbers = new ArrayList<>();
        HashMap<Integer, List<Integer>> current_lineNumbersByTimesSelected;
        GameQuestion currentGameQuestion;
        Random random = new Random();
        int randomFileID;
        int randomLineNumber;
        int current_timesSelected;
        Path pathOfQuestionnaireFile;
        String[] current_line;

        for (int i = 0; i < amount; i++) {
            randomFileID = random.nextInt(0, questionnaireFileNames.size());
            pathOfQuestionnaireFile = questionnaireFiles.get(questionnaireFileNames.get(randomFileID));
            current_lineNumbersByTimesSelected = dictionary.get(difficulty).get(pathOfQuestionnaireFile);
            current_timesSelected = 0;

            if (current_lineNumbersByTimesSelected == null) {
                i--;
                continue;
            }
            do {
                current_lineNumbers = current_lineNumbersByTimesSelected.get(current_timesSelected);
                current_timesSelected++;
            } while (current_lineNumbers == null || current_lineNumbers.isEmpty());
            //TODO Manuel fix this. There can be 0 question of one difficulty in a questionnaire

            randomLineNumber = random.nextInt(0, current_lineNumbers.size());

            try {
                current_line = CSV_Reader.get_line_by_lineNumber(pathOfQuestionnaireFile, current_lineNumbers.get(randomLineNumber));

                currentGameQuestion = new GameQuestion(current_lineNumbers.get(randomLineNumber), current_line);
                currentGameQuestion.incrementTimesSelected();

                CSV_Writer.update_line(pathOfQuestionnaireFile, current_lineNumbers.get(randomLineNumber), currentGameQuestion.getCsvLine());

                questions.add(currentGameQuestion);
            } catch (IOException | CsvException e) {
                throw new RuntimeException(e);
            }
            //TODO remove this init and find a solution to not get the same questions twice
            init();
        }

        return questions;
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

    public static HashMap<String, Path> get_QuestionnaireFiles() { return questionnaireFiles; }
    private static Path get_pathOfQuestionnaireFile_byID(int position) { return questionnaireFiles.get(position); }

    private static HashMap<String, Path> get_ListOfQuestionnaireFiles() {
        HashMap<String, Path> csvPaths = new HashMap<>();
        List<Path> pathList;

        try {
            File[] csvFiles_all_array = Objects.requireNonNull(new File(DEFAULT_BASEPATH_QUESTIONNARES.toString()).listFiles());
            pathList = Stream.of(csvFiles_all_array)
                    .filter(file -> !file.isDirectory())
                    .map(File::getPath)
                    .map(Path::of)
                    .toList();
            for (Path p : pathList) { csvPaths.put(p.getFileName().toString().split("\\.")[0], p); }
            return csvPaths;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void create_questionnaire(String nameOfQuestionnaire, List<GameQuestion> gameQuestions) {
        Path csvPath = Path.of(DEFAULT_BASEPATH_QUESTIONNARES + "\\" + nameOfQuestionnaire + ".csv");
        List<String[]> csvLines = new ArrayList<>();

        if (!Files.exists(csvPath)) {
            try {
                Files.createFile(csvPath);
                questionnaireFiles.put(nameOfQuestionnaire, csvPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        for (GameQuestion gameQuestion : gameQuestions) { csvLines.add(gameQuestion.getCsvLine()); }

        try {
            CSV_Writer.append_lines(csvPath, csvLines);
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        } finally {
            init();
        }
    }

    private static HashMap<Difficulty, HashMap<Path, HashMap<Integer, List<Integer>>>> readQuestionnaireFiles() {
        HashMap<Difficulty, HashMap<Path, HashMap<Integer, List<Integer>>>> dictionary = new HashMap<>();
        HashMap<Path, HashMap<Integer, List<Integer>>> lineNumbers_by_csvPath;
        HashMap<Difficulty, HashMap<Integer, List<Integer>>> lineNumbers_by_difficulty;

        for (Path csvPath : questionnaireFiles.values()) {
            try {
                lineNumbers_by_difficulty = CSV_Reader.get_lineNumbers_all_by_timesSelected(csvPath);
                for (Difficulty d : lineNumbers_by_difficulty.keySet()) {
                    lineNumbers_by_csvPath = dictionary.get(d);
                    if (lineNumbers_by_csvPath == null) {
                        lineNumbers_by_csvPath = new HashMap<>();
                        lineNumbers_by_csvPath.put(csvPath, lineNumbers_by_difficulty.get(d));
                        dictionary.put(d, lineNumbers_by_csvPath);
                    }
                }
            } catch (IOException | CsvException e) {
                throw new RuntimeException(e);
            }
        }

        return dictionary;
    }
}
