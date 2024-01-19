package at.onlyquiz.gameplay;

import at.onlyquiz.model.joker.AudienceJoker;
import at.onlyquiz.model.joker.ChatJoker;
import at.onlyquiz.model.joker.FiftyFiftyJoker;
import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.util.QuestionDictionary;
import at.onlyquiz.util.scoreSystem.ScoreCalculator;

import java.util.ArrayList;
import java.util.List;

public class DefaultMode extends GameMode {

    public DefaultMode() {
        editAble = false;
        scoreVisible = true;
        timerVisible = true;
        answerSecondsRemaining = 5;
        totalScore = 0;
        questionCounter = 0;
        jokersAvailable = true;

    //set up jokers;
    fiftyFiftyJokers.add(new FiftyFiftyJoker());
    audienceJokers.add(new AudienceJoker(false));
    chatJokers.add(new ChatJoker(false));

    List<String> testQuestionnaire = new ArrayList<>() {{
      add("test_testQuestions");
      add("testQuestions");
    }};
    setOfQuestions.addAll(QuestionDictionary.get_randomQuestions(testQuestionnaire, Difficulty.EASY, 5));
    setOfQuestions.addAll(QuestionDictionary.get_randomQuestions(testQuestionnaire, Difficulty.MEDIUM, 5));
    setOfQuestions.addAll(QuestionDictionary.get_randomQuestions(testQuestionnaire, Difficulty.HARD, 5));

    currentQuestion = popQuestionOutOfSet();
    currentQuestion.shuffleAnswers();
  }

  @Override
  public void confirmAnswer(boolean isCorrect) {
    if (isCorrect) {
      totalScore += calculateScore();
      jokerUsed = false;
      if (setOfQuestions.isEmpty()) {
        //TODO something when player Wins!
        finished = true;
      } else {
        currentQuestion = popQuestionOutOfSet();
        currentQuestion.shuffleAnswers();
        questionCounter += 1;
      }
    } else {
      finished = true;
    }
  }

  @Override
  public int calculateScore() {
    return ScoreCalculator.calculateDefaultModeScore(currentQuestion.getDifficulty(), answerSecondsRemaining, jokerUsed);
  }
}
