package at.onlyquiz.gameplay;

import at.onlyquiz.model.joker.AudienceJoker;
import at.onlyquiz.model.joker.ChatJoker;
import at.onlyquiz.model.joker.FiftyFiftyJoker;
import at.onlyquiz.model.question.Answer;
import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.model.question.GameQuestion;

import java.util.ArrayList;
import java.util.List;

public class DefaultMode extends GameMode {
    private FiftyFiftyJoker fiftyFiftyJoker;
    private ChatJoker chatJoker;
    private AudienceJoker audienceJoker;

    public DefaultMode() {
        scoreVisible = false;
        timerVisible = false;
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("A", true));
        answers.add(new Answer("B", false));
        answers.add(new Answer("C", false));
        answers.add(new Answer("D", false));

        currentQuestion = new GameQuestion("A, B, C oder D?", answers, Difficulty.EASY);
        currentQuestion.shuffleAnswers();
    }

}
