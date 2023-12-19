package at.onlyquiz.model.question;

public class Answer {
    private String value;
    private boolean right;
    private boolean visible;

    public Answer(String value, boolean right){
        this.value = value;
        this.right = right;
        visible = true;
    }

    public String getValue(){
        return value;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isVisible(){
        return visible;
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }
}
