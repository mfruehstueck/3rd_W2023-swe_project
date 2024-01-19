package at.onlyquiz.gameplay;

import at.onlyquiz.model.question.GameQuestion;
import at.onlyquiz.util.QuestionDictionary;

import java.util.List;

public class EditMode extends GameMode {

  private final String nameOfQuestionnaire;

  public EditMode(List<GameQuestion> setOfQuestions, int indexInQuestionnaire, String questionnairePath) {
      super(null);
    editAble = true;
    timerVisible = false;
    scoreVisible = false;
    jokersAvailable = false;


    this.nameOfQuestionnaire = questionnairePath;
    this.setOfQuestions = setOfQuestions;
    this.indexInQuestionnaire = indexInQuestionnaire;
    currentQuestion = setOfQuestions.get(indexInQuestionnaire);
  }

  @Override
  public void confirmAnswer(boolean isCorrect) {
    //check if delete or createNew operation
    if (currentQuestion.getQuestion().isEmpty() && currentQuestion.getAnswers().get(0).getAnswer().isEmpty() &&
            currentQuestion.getAnswers().get(1).getAnswer().isEmpty() && currentQuestion.getAnswers().get(2).getAnswer().isEmpty() &&
            currentQuestion.getAnswers().get(3).getAnswer().isEmpty()){
            QuestionDictionary.delete_gameQuestion(nameOfQuestionnaire, currentQuestion.getLineIdx());
            setOfQuestions = QuestionDictionary.get_allQuestions(nameOfQuestionnaire);
            previousIndex();
    }
    else {
            if (!setOfQuestions.contains(currentQuestion)) setOfQuestions.add(currentQuestion);
            QuestionDictionary.update_gameQuestion(nameOfQuestionnaire, currentQuestion);
            boolean reachedEnd = nextIndex();
            if (reachedEnd) currentQuestion = new GameQuestion();
    }
  }

  @Override
  public int calculateScore() {
    return 0;
  }

}


