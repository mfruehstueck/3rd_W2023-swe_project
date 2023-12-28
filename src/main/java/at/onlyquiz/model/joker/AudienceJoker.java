package at.onlyquiz.model.joker;

import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.model.question.GameQuestion;

public class AudienceJoker extends Joker {

    public AudienceJoker(){
        super();
    }

    @Override
    protected void performAction() {
        // return audience survey result based on question difficulty
    }
    public String getSurveyResult(Difficulty difficulty) {
        // Implementation of survey result logic
        return "Survey Result Based on Difficulty";
    }
}
