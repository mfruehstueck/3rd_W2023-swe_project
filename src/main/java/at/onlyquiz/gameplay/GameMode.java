package at.onlyquiz.gameplay;

import at.onlyquiz.model.question.GameQuestion;

import java.util.ArrayList;
import java.util.List;

public abstract class GameMode {
    List<GameQuestion> setOfQuestions = new ArrayList<>();
    Integer totalScore, achievedScore;
    boolean scoreVisible, timerVisible;
    boolean jokersAvailable;


    public boolean confirmAnswer(){

        return false;
    };

    public boolean isScoreVisible() {
        return scoreVisible;
    }

    public boolean isTimerVisible() {
        return timerVisible;
    }

    public List<GameQuestion> getSetOfQuestions() {
        return setOfQuestions;
    }

    public Integer getAchievedScore() {
        return achievedScore;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public boolean areJokersAvailable() {
        return jokersAvailable;
    }
}
