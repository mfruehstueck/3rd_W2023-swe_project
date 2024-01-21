package at.onlyquiz.gameplay;

import at.onlyquiz.model.joker.AudienceJoker;
import at.onlyquiz.model.joker.ChatJoker;
import at.onlyquiz.model.joker.FiftyFiftyJoker;
import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.model.question.GameQuestion;
import at.onlyquiz.util.Configuration;
import at.onlyquiz.util.QuestionDictionary;
import at.onlyquiz.util.userManagement.UserManagement;
import at.onlyquiz.util.jsonParser.models.PersistScore;
import at.onlyquiz.util.jsonParser.models.User;
import at.onlyquiz.util.scoreSystem.ScoreCalculator;

import java.util.Collections;
import java.util.List;

public class EndlessMode extends GameMode {
    public EndlessMode(List<String> selectedQuestionnaires, User current_user, boolean hasLiveAudience) {
        super(selectedQuestionnaires, current_user);
        editAble = false;
        scoreVisible = true;
        timerVisible = false;
        answerSecondsRemaining = 0;
        totalScore = 0;
        questionCounter = 0;
        jokersAvailable = true;

        fiftyFiftyJokers.add(new FiftyFiftyJoker());
        audienceJokers.add(new AudienceJoker(hasLiveAudience));
        chatJokers.add(new ChatJoker(false));

    getNewQuestions();
    Collections.shuffle(setOfQuestions);
    currentQuestion = popQuestionOutOfSet();
    currentQuestion.shuffleAnswers();
  }

  @Override
  public void confirmAnswer(boolean isCorrect) {
    if (isCorrect) {
        questionCounter += 1;
        jokerUsed = false;
      getNewQuestions();
      currentQuestion = popQuestionOutOfSet();
      currentQuestion.shuffleAnswers();
      totalScore += calculateScore();
      achievedScore += totalScore;
    } else {
        if (achievedScore != 0 && !UserManagement.get_currentUser().getUserName().equals(Configuration.DEFAULT_GUEST)) {
            PersistScore.saveScore(this);
        }
      finished = true;
    }
  }

  @Override
  public int calculateScore() {
        return ScoreCalculator.calculateEndlessModeScore(currentQuestion.getDifficulty()); }

  private void getNewQuestions() {
    if (setOfQuestions.size() < 2) {
      for (Difficulty d : Difficulty.values()) {
        List<GameQuestion> current_gameQuestions = QuestionDictionary.get_randomQuestions(selectedQuestionnaires, d, 5);
        if (current_gameQuestions != null) setOfQuestions.addAll(current_gameQuestions);
      }
    }
  }
}
