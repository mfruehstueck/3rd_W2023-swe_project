package at.onlyquiz.model.joker;

import at.onlyquiz.model.question.GameQuestion;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public abstract class Joker {

    private boolean used;

    public Joker(){
        this.used = false;
    }

    public void use(GameQuestion gameQuestion) {
        this.used = true;
        //TODO: this needs to be handled in the gameQuestion class
        List<Button> answerButtons = getAnswerButtonsFromQuestion(gameQuestion);
        performAction(gameQuestion, answerButtons);
    }


    public boolean isUsed(){
        return used;
    }

    private List<Button> getAnswerButtonsFromQuestion(GameQuestion gameQuestion) {
        // This method should return a list of buttons associated with the answers.
        // The implementation depends on how you're linking buttons to answers.
        // This is a placeholder implementation.
        List<Button> buttons = new ArrayList<>();
        // Example: add buttons to the list based on your actual UI mapping
        return buttons;
    }

    protected abstract void performAction(GameQuestion gameQuestion, List<Button> answerButtons);

}
