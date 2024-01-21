package at.onlyquiz.controller.cells.models;

public class QuestionEdit {
    private final String questionName;

    public QuestionEdit(String questionName) {
        this.questionName = questionName;
    }

    //getter
    public String getQuestionName() {
        return questionName;
    }
}
