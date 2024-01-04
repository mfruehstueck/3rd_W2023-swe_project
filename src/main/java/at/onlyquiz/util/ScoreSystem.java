package at.onlyquiz.util;

import at.onlyquiz.model.question.Difficulty;

public class ScoreSystem {

    private static final int EASY_POINTS = 20000;
    private static final int MEDIUM_POINTS = 60000;
    private static final int HARD_POINTS = 120000;
    private static final int TIMER_SECONDS = 45;
    private static final int JOKER_COST = 10000;

    public static int calculateScore(Difficulty difficulty, int timeTaken, boolean jokerUsed) {
        int basePoints;
        switch (difficulty) {
            case EASY:
                basePoints = EASY_POINTS;
                break;
            case MEDIUM:
                basePoints = MEDIUM_POINTS;
                break;
            case HARD:
                basePoints = HARD_POINTS;
                break;
            default:
                throw new IllegalArgumentException("Unknown difficulty level");
        }

        int score = basePoints - (basePoints / TIMER_SECONDS) * timeTaken;
        if (jokerUsed) {
            score -= JOKER_COST;
        }

        return Math.max(score, 0); // Ensure that score doesn't go below 0
    }
}
