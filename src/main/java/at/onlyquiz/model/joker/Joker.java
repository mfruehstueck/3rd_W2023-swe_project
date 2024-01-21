package at.onlyquiz.model.joker;

import at.onlyquiz.model.question.GameQuestion;

public abstract class Joker {

    protected boolean used;
    protected boolean online;

    public Joker() {
        this.used = false;
    }

    public void use(GameQuestion gameQuestion) {
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isUsed() {
        return used;
    }
}
