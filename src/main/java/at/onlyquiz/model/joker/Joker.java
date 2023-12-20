package at.onlyquiz.model.joker;

public abstract class Joker {

    boolean used;

    public Joker(){
        used = false;
    }

    public void use() { }

    public boolean isUsed(){
        return used;
    }
}
