package at.onlyquiz.gameplay;

import at.onlyquiz.model.question.Answer;
import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.model.question.GameQuestion;

import java.util.ArrayList;
import java.util.List;

public class EditMode extends GameMode {

  public EditMode(List<GameQuestion> setOfQuestions, int indexInQuestionnaire) {
    this.setOfQuestions = setOfQuestions;
    this.indexInQuestionnaire = indexInQuestionnaire;
    currentQuestion = setOfQuestions.get(indexInQuestionnaire);
  }

  @Override
  public void confirmAnswer(boolean isCorrect) {

  }

  @Override
  public int calculateScore() {
    return 0;
  }

}


