package at.onlyquiz.util;

import at.onlyquiz.model.joker.Joker;

import java.util.List;

public class JokerQueue {
    List<Joker> queue;

    public JokerQueue() {
    }

    public void add(Joker joker){
        queue.add(joker);
    }

    public Joker pop(){
        if (!queue.isEmpty()){
            Joker joker = queue.get(0);
            queue.remove(0);
            return joker;
        }
        return null;
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public int getLength(){
        return queue.size();
    }
}
