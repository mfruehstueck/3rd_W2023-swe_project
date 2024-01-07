package at.onlyquiz.model.joker;

import at.onlyquiz.model.question.GameQuestion;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public abstract class Joker {

    protected boolean used;

    public Joker(){
        this.used = false;
    }

    public void use(GameQuestion gameQuestion) {
    }


    public boolean isUsed(){
        return used;
    }
}
