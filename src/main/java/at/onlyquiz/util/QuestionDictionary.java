package at.onlyquiz.util;

import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.model.question.GameQuestion;

import java.util.HashMap;
import java.util.List;

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

    private static GameQuestion getGameQuestionFromFile(){
        return null;
    }
}
