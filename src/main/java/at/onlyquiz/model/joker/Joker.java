package at.onlyquiz.model.joker;

import at.onlyquiz.model.question.GameQuestion;

public abstract class Joker {

    boolean used;

    public Joker(){
        used = false;
    }

    public void use(GameQuestion gameQuestion) { }

    public boolean isUsed(){
        return used;
    }
}
