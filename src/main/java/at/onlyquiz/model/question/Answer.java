package at.onlyquiz.model.question;

public class Answer {
    private final String answer;
    private final boolean correct;
    private boolean visible;

    public Answer(String answer, boolean correct){
        this.answer = answer;
        this.correct = correct;
        visible = true;
    }

    public String getAnswer(){
        return answer;
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
