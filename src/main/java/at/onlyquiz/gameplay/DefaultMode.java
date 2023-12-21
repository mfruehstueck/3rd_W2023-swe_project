package at.onlyquiz.gameplay;

import at.onlyquiz.model.question.Answer;
import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.model.question.GameQuestion;
import java.util.ArrayList;
import java.util.List;

public class DefaultMode extends GameMode {

    public DefaultMode() {
        editAble = false;
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
