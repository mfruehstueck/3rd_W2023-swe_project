package at.onlyquiz.gameplay;

import at.onlyquiz.model.question.GameQuestion;

import java.util.ArrayList;
import java.util.List;

public abstract class GameMode {
    List<GameQuestion> setOfQuestions = new ArrayList<>();
    Integer achievedScore = 0;

    public Integer scoreCalculator(){
        return 0;
    }

    public boolean confirmAnswer(){

        return false;
    };
}
