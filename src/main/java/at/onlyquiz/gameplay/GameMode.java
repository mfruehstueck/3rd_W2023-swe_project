package at.onlyquiz.gameplay;

import at.onlyquiz.model.question.GameQuestion;
import at.onlyquiz.util.JokerQueue;
import at.onlyquiz.util.timeSystem.TimeConstants;

import java.util.ArrayList;
import java.util.List;

public abstract class GameMode {
  List<GameQuestion> setOfQuestions = new ArrayList<>();
  GameQuestion currentQuestion;
  Integer totalScore, achievableScore, questionCounter;
  JokerQueue audienceJokers = new JokerQueue();
  JokerQueue fiftyFiftyJokers = new JokerQueue();
  JokerQueue chatJokers = new JokerQueue();
  boolean scoreVisible, timerVisible;
  boolean jokersAvailable;
  boolean editAble;
  boolean finished = false;
  boolean jokerUsed = false;
  int answerSecondsRemaining, readingSecondsRemaining;
  int indexInQuestionnaire = 0;
  protected List<String> selectedQuestionnaires;

  public GameMode() { super(); }

  public GameMode(List<String> selectedQuestionnaires) { this.selectedQuestionnaires = selectedQuestionnaires; }

  public abstract void confirmAnswer(boolean isCorrect);
  public abstract int calculateScore();

  public GameQuestion popQuestionOutOfSet() {
    if (setOfQuestions.isEmpty()) return null;
    GameQuestion q = setOfQuestions.get(0);
    setOfQuestions.remove(0);
    return q;
  }

  public void resetTimer() {
    resetReadingSecondsRemaining();
    resetAnswerSecondsRemaining();
  }

  private void resetReadingSecondsRemaining() {
    switch (currentQuestion.getDifficulty()) {
      case EASY -> readingSecondsRemaining = TimeConstants.EASY_TIME_TO_READ_SEC;
      case MEDIUM -> readingSecondsRemaining = TimeConstants.MEDIUM_TIME_TO_READ_SEC;
      case HARD -> readingSecondsRemaining = TimeConstants.HARD_TIME_TO_READ_SEC;
    }
  }

  private void resetAnswerSecondsRemaining() {
    answerSecondsRemaining = TimeConstants.ANSWERING_TIME_SEC;
  }

  public boolean nextIndex() {
    if (indexInQuestionnaire + 1 < setOfQuestions.size()) {
      indexInQuestionnaire++;
      currentQuestion = setOfQuestions.get(indexInQuestionnaire);
      return false;
    }
    if (indexInQuestionnaire + 1 == setOfQuestions.size()) {
      indexInQuestionnaire++;
    }
    return true;
  }

  public boolean previousIndex() {
    if (indexInQuestionnaire - 1 >= 0) {
      indexInQuestionnaire--;
      currentQuestion = setOfQuestions.get(indexInQuestionnaire);
      return false;
    }
    return true;
  }

  public int getIndexInQuestionnaire() {
    return indexInQuestionnaire;
  }

  public void setIndexInQuestionnaire(int indexInQuestionnaire) {
    this.indexInQuestionnaire = indexInQuestionnaire;
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

  public Integer getAchievableScore() {
    return achievableScore;
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

  public int getAnswerSecondsRemaining() {
    return answerSecondsRemaining;
  }

  public void setAnswerSecondsRemaining(int answerSecondsRemaining) {
    this.answerSecondsRemaining = answerSecondsRemaining;
  }

  public int getReadingSecondsRemaining() {
    return readingSecondsRemaining;
  }

  public void setReadingSecondsRemaining(int readingSecondsRemaining) {
    this.readingSecondsRemaining = readingSecondsRemaining;
  }

  public boolean isFinished() {
    return finished;
  }

  public Integer getQuestionCounter() {
    return questionCounter;
  }

  public boolean isJokerUsed() {
    return jokerUsed;
  }

  public void setJokerUsed(boolean jokerUsed) {
    this.jokerUsed = jokerUsed;
  }

  public void setCurrentQuestion(GameQuestion currentQuestion) {
    this.currentQuestion = currentQuestion;
  }
}
