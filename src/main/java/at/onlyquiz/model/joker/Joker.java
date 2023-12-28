package at.onlyquiz.model.joker;

import at.onlyquiz.model.question.GameQuestion;

public abstract class Joker {

    private boolean used;

    public Joker(){
        this.used = false;
    }

    public void use(GameQuestion gameQuestion) {
        this.used = true;
        performAction();
    }

    protected abstract void performAction();


    public boolean isUsed(){
        return used;
    }
}
