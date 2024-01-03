package at.onlyquiz.util;

import at.onlyquiz.model.question.Answer;
import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.model.question.GameQuestion;
import at.onlyquiz.util.csvParser.CSV_Column;
import at.onlyquiz.util.csvParser.CSV_Reader;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

import static at.onlyquiz.util.Configuration.DEFAULT_PATH_QUESTIONNARES;

public class QuestionDictionary {
    //N: HashMap: Difficulty, CsvFile, TimesSelected, LineNumber
    private static final HashMap<Difficulty, HashMap<Path, HashMap<Integer, List<Integer>>>> dictionary;
    private static final List<Path> questionnaireFiles;

    //N: read csv-files on app start
    static {
        questionnaireFiles = get_ListOfQuestionnaireFiles();
        dictionary = readQuestionnaireFiles();
//        System.out.println("questionnaireFiles = " + questionnaireFiles);
//        System.out.println(get_Questions(Difficulty.EASY, 5));
        System.out.println(dictionary);
    }

    public static List<GameQuestion> get_Questions(Difficulty difficulty, int amount) {
        List<GameQuestion> questions = new ArrayList<>();
        List<Answer> answers;
        String[] current_line;
        Random random = new Random();
        int randomFileID;
        int randomLineID;

        for (int i = 0; i < amount; i++) {
            randomFileID = random.nextInt(0, questionnaireFiles.size());
            randomLineID = random.nextInt(0, dictionary.get(difficulty).get(get_pathOfQuestionnaireFile_byID(randomFileID)).get(0).size());

            try {
                answers = new ArrayList<>();

                current_line = CSV_Reader.get_line_by_lineNumber(get_pathOfQuestionnaireFile_byID(randomFileID), randomLineID);
                answers.add(new Answer(current_line[CSV_Column.CORRECT_ANSWER.ordinal()], true));
                answers.add(new Answer(current_line[CSV_Column.WRONG_ANSWER_1.ordinal()], false));
                answers.add(new Answer(current_line[CSV_Column.WRONG_ANSWER_2.ordinal()], false));
                answers.add(new Answer(current_line[CSV_Column.WRONG_ANSWER_3.ordinal()], false));

                questions.add(new GameQuestion(current_line[CSV_Column.QUESTION.ordinal()], answers, Difficulty.valueOf(current_line[CSV_Column.DIFFICULTY.ordinal()])));
            } catch (IOException | CsvException e) {
                throw new RuntimeException(e);
            }
        }

        return questions;
    }

    public static List<Path> get_QuestionnaireFiles() { return questionnaireFiles; }
    public static Path get_pathOfQuestionnaireFile_byID(int position) { return questionnaireFiles.get(position); }

    private static List<Path> get_ListOfQuestionnaireFiles() {
        try {
            File[] csvFiles_all_array = Objects.requireNonNull(new File(DEFAULT_PATH_QUESTIONNARES.toString()).listFiles());
            return Stream.of(csvFiles_all_array)
                    .filter(file -> !file.isDirectory())
                    .map(File::getPath)
                    .map(Path::of)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Path get_pathOfQuestionnaireFile(String csvPath) { return null; }

    private static HashMap<Difficulty, HashMap<Path, HashMap<Integer, List<Integer>>>> readQuestionnaireFiles() {
        HashMap<Difficulty, HashMap<Path, HashMap<Integer, List<Integer>>>> dictionary = new HashMap<>();
        HashMap<Path, HashMap<Integer, List<Integer>>> lineNumbers_by_csvPath;
        HashMap<Difficulty, HashMap<Integer, List<Integer>>> lineNumbers_by_difficulty;

        for (Path csvPath : questionnaireFiles) {
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

    public static GameQuestion read_line_in_csv() {
        return null;
    }

    private static GameQuestion getGameQuestionFromFile() {
        return null;
    }
}
