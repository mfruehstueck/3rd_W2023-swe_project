package at.onlyquiz.util.scoreSystem;

import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.util.timeSystem.TimeConstants;

public class ScoreCalculator {

    public static int calculateDefaultModeScore(Difficulty difficulty, int timeRemaining, boolean jokerUsed) {
        int basePoints = switch (difficulty) {
            case EASY -> ScoreConstants.EASY_POINTS;
            case MEDIUM -> ScoreConstants.MEDIUM_POINTS;
            case HARD -> ScoreConstants.HARD_POINTS;
        };

        int score = basePoints - (basePoints / TimeConstants.ANSWERING_TIME_SEC) * (TimeConstants.ANSWERING_TIME_SEC - timeRemaining);
        if (jokerUsed) {
            score -= ScoreConstants.JOKER_COST;
        }

        return Math.max(score, 0);
    }

    //TODO Ibrahim we need another score system for Endless-Mode
    public static int calculateEndlessModeScore(Difficulty difficulty, int timeTaken, boolean jokerUsed, int totalQuestionsAnswered){
        double score = ScoreConstants.MAX_SCORE  * (Math.exp((double) totalQuestionsAnswered / ScoreConstants.MAX_SCORE ) - 1);
        return (int) Math.min(score, ScoreConstants.MAX_SCORE );
    }
}
