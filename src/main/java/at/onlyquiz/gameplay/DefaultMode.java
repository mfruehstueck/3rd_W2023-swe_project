package at.onlyquiz.gameplay;

import at.onlyquiz.model.joker.AudienceJoker;
import at.onlyquiz.model.joker.ChatJoker;
import at.onlyquiz.model.joker.FiftyFiftyJoker;
import at.onlyquiz.model.question.Difficulty;

public class DefaultMode extends GameMode {

    private static final int EASY_POINTS = 20000;
    private static final int MEDIUM_POINTS = 60000;
    private static final int HARD_POINTS = 120000;
    private static final int TIMER_SECONDS = 45;
    private static final int JOKER_COST = 10000;
    private FiftyFiftyJoker fiftyFiftyJoker;
    private ChatJoker chatJoker;
    private AudienceJoker audienceJoker;

    public DefaultMode(int achievedScore) {
        this.achievedScore = achievedScore;
    }

    public int scoreCalculator(Difficulty difficulty, int timeTaken, boolean jokerUsed) {
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
