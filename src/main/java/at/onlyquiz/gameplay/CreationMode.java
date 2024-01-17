package at.onlyquiz.gameplay;

import at.onlyquiz.model.question.Answer;
import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.model.question.GameQuestion;

import java.util.ArrayList;
import java.util.List;

public class CreationMode extends GameMode {

  public CreationMode() {
    editAble = true;
    scoreVisible = false;
    timerVisible = false;

    List<Answer> answers = new ArrayList<>();
    answers.add(new Answer("", true));
    answers.add(new Answer("", false));
    answers.add(new Answer("", false));
    answers.add(new Answer("", false));

    currentQuestion = new GameQuestion("", answers, Difficulty.EASY);
  }

  @Override
  public void confirmAnswer(boolean isCorrect) {

  }

  @Override
  public int calculateScore() {
    return 0;
  }
}
