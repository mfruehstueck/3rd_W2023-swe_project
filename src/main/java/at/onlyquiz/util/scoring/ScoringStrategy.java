package at.onlyquiz.util.scoring;

import at.onlyquiz.model.question.Difficulty;

public interface ScoringStrategy {
    int calculateScore(Difficulty difficulty, int timeTaken, boolean jokerUsed);
}