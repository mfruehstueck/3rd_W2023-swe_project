package at.onlyquiz.model.joker;

import at.onlyquiz.model.question.Answer;
import at.onlyquiz.model.question.GameQuestion;
import javafx.scene.control.Button;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FiftyFiftyJoker extends Joker {

    public FiftyFiftyJoker() {
        super();
    }

    @Override
    public void use(GameQuestion gameQuestion) {
        List<Answer> answers = gameQuestion.getAnswers();
        Integer counter = 0;
        while (counter<2){
            Integer random_number = new Random().nextInt(0,4);
            if(answers.get(random_number).isCorrect() == false && answers.get(random_number).isVisible()){
                answers.get(random_number).setVisible(false);
                counter++;
            }
        }
        used = true;
    }
}
