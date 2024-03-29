package at.onlyquiz.util.scoreSystem;

import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.util.timeSystem.TimeConstants;

import static at.onlyquiz.util.scoreSystem.ScoreConstants.MAX_SCORE;
import static at.onlyquiz.util.scoreSystem.ScoreConstants.SCALE_FACTOR;

public class ScoreCalculator {

  public static int calculateDefaultModeScore(Difficulty difficulty, int timeRemaining, boolean jokerUsed) {
    int basePoints = switch (difficulty) {
      case EASY -> ScoreConstants.EASY_POINTS;
      case MEDIUM -> ScoreConstants.MEDIUM_POINTS;
      case HARD -> ScoreConstants.HARD_POINTS;
    };
    int timeToAnswerMax = switch (difficulty) {
      case EASY -> TimeConstants.EASY_TIME_TO_ANSWER;
      case MEDIUM -> TimeConstants.MEDIUM_TIME_TO_ANSWER;
      case HARD -> TimeConstants.HARD_TIME_TO_ANSWER;
    };

    int score = basePoints - (basePoints / timeToAnswerMax) * (timeToAnswerMax - timeRemaining);
    if (jokerUsed) {
      score -= ScoreConstants.JOKER_COST;
    }

    return Math.max(score, 0);
  }
  public static int calculateEndlessModeScore(int totalScore) {
    return (int) (MAX_SCORE * (1 - Math.exp((-1 / Math.sqrt(MAX_SCORE * SCALE_FACTOR)) * Math.sqrt(totalScore)))) + 1;
  }
}
