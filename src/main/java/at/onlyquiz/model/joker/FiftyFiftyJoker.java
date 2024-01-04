package at.onlyquiz.model.joker;

import at.onlyquiz.model.question.GameQuestion;
import javafx.scene.control.Button;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FiftyFiftyJoker extends Joker {

    public FiftyFiftyJoker() {
        super();
    }



    @Override
    public void performAction(GameQuestion question, List<Button> answerButtons) {
        // Find indices of incorrect answers
        List<Integer> incorrectAnswerIndices = getIncorrectAnswerIndices(question, answerButtons);

        // Hide two incorrect answers
        hideTwoIncorrectAnswers(answerButtons, incorrectAnswerIndices);
    }

    private List<Integer> getIncorrectAnswerIndices(GameQuestion question, List<Button> answerButtons) {
        return answerButtons.stream()
                .filter(button -> !question.getAnswerByButton(button).isCorrect())
                .map(answerButtons::indexOf)
                .collect(Collectors.toList());
    }

    private void hideTwoIncorrectAnswers(List<Button> answerButtons, List<Integer> incorrectAnswerIndices) {
        Collections.shuffle(incorrectAnswerIndices);
        incorrectAnswerIndices.stream()
                .limit(2)
                .forEach(index -> answerButtons.get(index).setVisible(false));
    }
}
