package at.onlyquiz.gameplay;

import at.onlyquiz.model.joker.AudienceJoker;
import at.onlyquiz.model.joker.ChatJoker;
import at.onlyquiz.model.joker.FiftyFiftyJoker;
import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.model.question.GameQuestion;
import at.onlyquiz.util.QuestionDictionary;
import at.onlyquiz.util.UserManagement;
import at.onlyquiz.util.jsonParser.models.PersistScore;
import at.onlyquiz.util.jsonParser.models.User;
import at.onlyquiz.util.scoreSystem.ScoreCalculator;

import java.util.List;

public class DefaultMode extends GameMode {

  public DefaultMode(List<String> selectedQuestionnaires, User current_user, boolean hasLiveAudience) {
    super(selectedQuestionnaires, current_user);
    editAble = false;
    scoreVisible = true;
    timerVisible = true;
    answerSecondsRemaining = 5;
    totalScore = 0;
    questionCounter = 0;
    jokersAvailable = true;

    //set up jokers;
    fiftyFiftyJokers.add(new FiftyFiftyJoker());
    audienceJokers.add(new AudienceJoker(hasLiveAudience));
    chatJokers.add(new ChatJoker(false));

    for (Difficulty d : Difficulty.values()) {
      List<GameQuestion> current_gameQuestions = QuestionDictionary.get_randomQuestions(selectedQuestionnaires, d, 5);
      if (current_gameQuestions != null) setOfQuestions.addAll(current_gameQuestions);
    }

    currentQuestion = popQuestionOutOfSet();
    currentQuestion.shuffleAnswers();
  }

  @Override
  public void confirmAnswer(boolean isCorrect) {
    if (isCorrect) {
      questionCounter += 1;
      totalScore += calculateScore();
      if (questionCounter != 0 && questionCounter % 5 == 0) {
        achievedScore = totalScore;
      }
      jokerUsed = false;
      if (setOfQuestions.isEmpty()) {
        //TODO something when player Wins!
        finished = true;
        won = true;
      } else {
        currentQuestion = popQuestionOutOfSet();
        currentQuestion.shuffleAnswers();
      }
    } else {
      if (achievedScore != 0 && UserManagement.get_currentUser() != null) {
        PersistScore.saveScore(this);
      }
      finished = true;
    }
  }

  @Override
  public int calculateScore() {
    return ScoreCalculator.calculateDefaultModeScore(currentQuestion.getDifficulty(), answerSecondsRemaining, jokerUsed);
  }
}
