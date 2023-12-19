package at.onlyquiz.model.joker;

public abstract class Joker {

    boolean used;

    public Joker(){
        used = false;
    }

    protected void use() { }

    public boolean isUsed(){
        return used;
    }
}
