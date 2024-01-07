package at.onlyquiz.model.scoring;

import at.onlyquiz.model.question.Difficulty;

public class EndlessScoringStrategy implements ScoringStrategy {

    private static final int MAX_SCORE = 1000000; // Maximum score that can be approached
    private int totalQuestionsAnswered = 0;

    @Override
    public int calculateScore(Difficulty difficulty, int timeTaken, boolean jokerUsed) {
        totalQuestionsAnswered++;
        double score = MAX_SCORE - (MAX_SCORE / (double) totalQuestionsAnswered);
        return (int) score;
    }
}