package at.onlyquiz.gameplay;

import at.onlyquiz.model.question.GameQuestion;
import at.onlyquiz.util.JokerQueue;

import java.util.ArrayList;
import java.util.List;

public abstract class GameMode {
    List<GameQuestion> setOfQuestions = new ArrayList<>();
    GameQuestion currentQuestion;
    Integer totalScore, achievedScore;
    JokerQueue audienceJokers = new JokerQueue();
    JokerQueue fiftyFiftyJokers = new JokerQueue();
    JokerQueue chatJokers = new JokerQueue();
    boolean scoreVisible, timerVisible;
    boolean jokersAvailable;


    public Integer scoreCalculator(){
        return 0;
    }

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

    public JokerQueue getAudienceJokers() {
        return audienceJokers;
    }

    public JokerQueue getFiftyFiftyJokers() {
        return fiftyFiftyJokers;
    }

    public JokerQueue getChatJokers() {
        return chatJokers;
    }

    public GameQuestion getCurrentQuestion() {
        return currentQuestion;
    }
}
