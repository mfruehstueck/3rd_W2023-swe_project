package at.onlyquiz.model.joker;

import at.onlyquiz.model.question.Answer;
import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.model.question.GameQuestion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AudienceJoker extends Joker {
  private Random random = new Random();
  public AudienceJoker() {
    super();
  }

  @Override
  public void use(GameQuestion gameQuestion) {
    List<Answer> answers = new ArrayList<>(gameQuestion.getAnswers());
    double votingSum = 100;
    double from = 0, to = 0;
    switch (gameQuestion.getDifficulty()) {
      case EASY -> { // clearly defined
        from = 60;
        to = 75;
      }
      case MEDIUM -> { // less defined
        from = 50;
        to = 60;
      }
      case HARD -> { // not clear
        from = 20;
        to = 40;
      }
    }
    for (Answer a : answers) {
      if (a.isCorrect()) {
        a.setVotingValue(random.nextDouble(from, to));
        votingSum -= a.getVotingValue();
        answers.remove(a);
        break;
      }
    }

    for (Answer a : answers) {
      a.setVotingValue(random.nextDouble(0, votingSum));
      votingSum -= a.getVotingValue();

    }
    used = true;
  }

  public String getSurveyResult(Difficulty difficulty) {
    // Implementation of survey result logic
    return "Survey Result Based on Difficulty";
  }
}
