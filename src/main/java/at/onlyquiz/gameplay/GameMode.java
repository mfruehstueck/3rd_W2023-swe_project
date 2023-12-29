package at.onlyquiz.gameplay;

import at.onlyquiz.model.question.GameQuestion;
import at.onlyquiz.util.JokerQueue;

import java.util.ArrayList;
import java.util.List;

public abstract class GameMode {
    List<GameQuestion> setOfQuestions = new ArrayList<>();
    GameQuestion currentQuestion;
    Integer totalScore, achievedScore, questionCounter;
    JokerQueue audienceJokers = new JokerQueue();
    JokerQueue fiftyFiftyJokers = new JokerQueue();
    JokerQueue chatJokers = new JokerQueue();
    boolean scoreVisible, timerVisible;
    boolean jokersAvailable;
    boolean editAble;
    int timeDurationInSeconds;


    public abstract void confirmAnswer(boolean isCorrect);

    public GameQuestion popQuestionOutOfSet(){
        if (setOfQuestions.isEmpty()) return null;
        GameQuestion q = setOfQuestions.get(0);
        setOfQuestions.remove(0);
        return q;
    }

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

    public boolean isEditAble() {
        return editAble;
    }

    public int getTimeDurationInSeconds() {
        return timeDurationInSeconds;
    }
}
