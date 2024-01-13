package at.onlyquiz.model.joker;

import at.onlyquiz.model.question.Answer;
import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.model.question.GameQuestion;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AudienceJoker extends Joker {
    private Random random = new Random();

    public AudienceJoker() {
        super();
    }

    @Override
    public void use(GameQuestion gameQuestion) {
        List<Answer> answers = new ArrayList<>(gameQuestion.getAnswers());
        double total = 100;
        double from = 0, to = 0, buffer = 10;
        switch (gameQuestion.getDifficulty()) {
            case EASY -> { // clearly defined
                from = 55;
                to = 65;
            }
            case MEDIUM -> { // less defined
                from = 50;
                to = 60;
            }
            case HARD -> { // not clear
                from = 30;
                to = 45;
            }
        }

        double right = nextDoubleInRange(from, to);
        double wrong1 = nextDoubleInRange(buffer, from - buffer);
        double wrong2 = nextDoubleInRange(buffer, from);
        double wrong3 = total - right - wrong1 - wrong2;
        List<Double> votes = List.of(wrong1, wrong2, wrong3);

        // Assign values to answers in a functional way
        IntStream.range(0, answers.size())
                .forEach(i -> {
                    Answer answer = answers.get(i);
                    double voteValue = answer.isCorrect() ? right : votes.get(i % 3);
                    answer.setVotingValue(voteValue);
                });

        used = true;
    }

    private double nextDoubleInRange(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    public String getSurveyResult(Difficulty difficulty) {
        // Implementation of survey result logic
        return "Survey Result Based on Difficulty";
    }
}
