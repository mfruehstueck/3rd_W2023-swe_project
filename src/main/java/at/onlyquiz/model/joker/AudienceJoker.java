package at.onlyquiz.model.joker;

import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.model.question.GameQuestion;
import javafx.scene.control.Button;

import java.util.List;

public class AudienceJoker extends Joker {

    public AudienceJoker(){
        super();
    }

    @Override
    public void use(GameQuestion question) {

    }

    public String getSurveyResult(Difficulty difficulty) {
        // Implementation of survey result logic
        return "Survey Result Based on Difficulty";
    }
}
