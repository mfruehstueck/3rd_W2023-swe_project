package at.onlyquiz.model.question;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GameQuestion {
    private  String question;
    private List<Answer> answers;
    private Difficulty difficulty;
    private boolean editable;

    public GameQuestion() {
        this.editable = true;
    }

    public GameQuestion(String question, List<Answer> answers, Difficulty difficulty){
        this.question = question;
        this.answers = answers;
        this.difficulty = difficulty;
        this.editable = false;
    }

    public void shuffleAnswers(){
        Collections.shuffle(answers);
    }

    public Answer getSpecificAnswer(int index){
        return answers.get(index);
    }

    public String getQuestion(){
        return question;
    }

    public List<Answer> getAnswers(){
        return answers;
    }

    public Difficulty getDifficulty(){
        return difficulty;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
