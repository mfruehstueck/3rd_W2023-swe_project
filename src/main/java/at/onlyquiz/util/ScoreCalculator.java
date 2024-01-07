package at.onlyquiz.util;

import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.model.scoring.ScoringStrategy;

public class ScoreCalculator {

    private ScoringStrategy scoringStrategy;

    public ScoreCalculator(ScoringStrategy scoringStrategy) {
        this.scoringStrategy = scoringStrategy;
    }

    public int calculateScore(Difficulty difficulty, int timeTaken, boolean jokerUsed) {
        return scoringStrategy.calculateScore(difficulty, timeTaken, jokerUsed);
    }

    public void setScoringStrategy(ScoringStrategy scoringStrategy) {
        this.scoringStrategy = scoringStrategy;
    }
}
