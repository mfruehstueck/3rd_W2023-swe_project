package at.onlyquiz.util;

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

public class QuestionDictionary {
   //N: HashMap: CsvFile, TimesSelected, Difficulty, LineIndex
   private static HashMap<Path, HashMap<Integer, HashMap<Difficulty, List<Integer>>>> dictionary;
   private static HashMap<String, Path> questionnaireFiles;

   //N: read csv-files on app start
   public static void init() {
      questionnaireFiles = get_ListOfQuestionnaireFiles();
      dictionary = read_questionnaireFiles();
   }
   public static List<String> get_questions(Path csvPath) {
      List<String> out_questions = new ArrayList<>();
      try {
         List<String[]> csvLines = CSV_Reader.get_csvLines_all(csvPath);
         for (String[] line : csvLines) { out_questions.add(line[CSV_Column.QUESTION.ordinal()]); }
      } catch (IOException | CsvException e) {
         throw new RuntimeException(e);
      }

      return out_questions;
   }
   public static List<GameQuestion> get_randomQuestions(List<String> questionnaireFileNames, Difficulty difficulty, int amount) {
      List<GameQuestion> out_questions = new ArrayList<>();
      List<Integer> current_listOf_lineIdx;
      List<Integer> selectedRandomLineNumbers = new ArrayList<>();
      HashMap<Difficulty, List<Integer>> current_mapOf_lineIdx_byDifficulty;
      GameQuestion current_gameQuestion;
      Random random = new Random();
      int randomFileID;
      int randomLineNumber;
      int current_timesSelected;
      Path pathOfQuestionnaireFile;
      String[] current_line;

      for (int i = 0; i < amount; i++) {
         current_timesSelected = 0;
         randomFileID = random.nextInt(0, questionnaireFileNames.size());
         pathOfQuestionnaireFile = questionnaireFiles.get(questionnaireFileNames.get(randomFileID));
         current_mapOf_lineIdx_byDifficulty = dictionary.get(pathOfQuestionnaireFile).get(current_timesSelected);

         if (current_mapOf_lineIdx_byDifficulty == null) {
            i--;
            continue;
         }
         do {
            current_listOf_lineIdx = current_mapOf_lineIdx_byDifficulty.get(difficulty);
            current_timesSelected++;
         } while (current_listOf_lineIdx == null || current_listOf_lineIdx.isEmpty());
         //TODO Manuel fix this. There can be 0 question of one difficulty in a questionnaire

         randomLineNumber = random.nextInt(0, current_listOf_lineIdx.size());

         try {
            current_line = CSV_Reader.get_line_by_lineIdx(pathOfQuestionnaireFile, current_listOf_lineIdx.get(randomLineNumber));

            current_gameQuestion = new GameQuestion(current_listOf_lineIdx.get(randomLineNumber), current_line);
            current_gameQuestion.incrementTimesSelected();

            CSV_Writer.update_line(pathOfQuestionnaireFile, current_listOf_lineIdx.get(randomLineNumber), current_gameQuestion.getCsvLine());

            out_questions.add(current_gameQuestion);
         } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
         }
         //TODO remove this init and find a solution to not get the same questions twice
         init();
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
   public static HashMap<String, Path> get_QuestionnaireFiles() { return questionnaireFiles; }
   private static Path get_pathOfQuestionnaireFile_byID(int position) { return questionnaireFiles.get(position); }
   public static HashMap<Path, HashMap<Integer, HashMap<Difficulty, List<Integer>>>> get_dictionary() { return dictionary; }

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

   private static HashMap<Path, HashMap<Integer, HashMap<Difficulty, List<Integer>>>> read_questionnaireFiles() {
      HashMap<Path, HashMap<Integer, HashMap<Difficulty, List<Integer>>>> out_dictionary = new HashMap<>();
      HashMap<Integer, HashMap<Difficulty, List<Integer>>> current_mapOf_lineNumbers_byTimesSelected;

      for (Path csvPath : questionnaireFiles.values()) {
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
