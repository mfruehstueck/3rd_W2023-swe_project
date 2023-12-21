package at.onlyquiz.model.question;

public class Answer {
    private String value;
    private boolean correct;
    private boolean visible;

    public Answer(String value, boolean correct){
        this.value = value;
        this.correct = correct;
        visible = true;
    }

    public String getValue(){
        return value;
    }

    public boolean isCorrect() {
        return correct;
    }

    public boolean isVisible(){
        return visible;
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }
}
