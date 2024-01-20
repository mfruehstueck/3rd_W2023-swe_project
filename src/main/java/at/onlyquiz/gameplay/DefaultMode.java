package at.onlyquiz.gameplay;

import at.onlyquiz.model.joker.AudienceJoker;
import at.onlyquiz.model.joker.ChatJoker;
import at.onlyquiz.model.joker.FiftyFiftyJoker;
import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.util.QuestionDictionary;
import at.onlyquiz.util.scoreSystem.ScoreCalculator;
import at.onlyquiz.util.scoreSystem.savedScoresJSON.PersistScore;

import java.util.ArrayList;
import java.util.List;

public class DefaultMode extends GameMode {

  public DefaultMode(List<String> selectedQuestionnaires, String playername) {
    super(selectedQuestionnaires, playername);
    editAble = false;
    scoreVisible = true;
    timerVisible = true;
    answerSecondsRemaining = 5;
    totalScore = 0;
    questionCounter = 0;
    jokersAvailable = true;

    //set up jokers;
    fiftyFiftyJokers.add(new FiftyFiftyJoker());
    audienceJokers.add(new AudienceJoker(true));
    chatJokers.add(new ChatJoker(false));

    setOfQuestions.addAll(QuestionDictionary.get_randomQuestions(selectedQuestionnaires, Difficulty.EASY, 5));
    setOfQuestions.addAll(QuestionDictionary.get_randomQuestions(selectedQuestionnaires, Difficulty.MEDIUM, 5));
    setOfQuestions.addAll(QuestionDictionary.get_randomQuestions(selectedQuestionnaires, Difficulty.HARD, 5));

    currentQuestion = popQuestionOutOfSet();
    currentQuestion.shuffleAnswers();
  }

  @Override
  public void confirmAnswer(boolean isCorrect) {
    if (isCorrect) {
        questionCounter += 1;
        totalScore += calculateScore();
      if (questionCounter != 0 && questionCounter % 5 == 0){
          achievedScore = totalScore;
      }
      jokerUsed = false;
      if (setOfQuestions.isEmpty()) {
        //TODO something when player Wins!
        finished = true;
      } else {
        currentQuestion = popQuestionOutOfSet();
        currentQuestion.shuffleAnswers();
      }
    } else {
        PersistScore.saveScore(this, playername, achievedScore);
      finished = true;
    }
  }

  @Override
  public int calculateScore() {
    return ScoreCalculator.calculateDefaultModeScore(currentQuestion.getDifficulty(), answerSecondsRemaining, jokerUsed);
  }
}
