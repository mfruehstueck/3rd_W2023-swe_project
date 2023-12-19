package at.onlyquiz.util;

import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.model.question.GameQuestion;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuestionDictionary {
    private static HashMap<Difficulty, HashMap<Integer, String>> dictionary;
    private static List<String> questionnaireFiles;

    //N: read csv-files on app start
    static {
        readFiles();
    }

    public static List<GameQuestion> createSetOfQuestions(int amount){
        return null;
    }

    private static void readFiles(){

    }

    public static GameQuestion read_line_in_csv(){
        return null;
    }

    private static GameQuestion getGameQuestionFromFile(){
        return null;
    }
}
