package at.onlyquiz.controller.cells.models;

public class QuestionnaireSelection {
    private final String questionnaireName;
    private boolean isSelected;

    public QuestionnaireSelection(String questionnaireName, boolean isSelected) {
        this.questionnaireName = questionnaireName;
        this.isSelected = isSelected;
    }

    //getter
    public boolean isSelected() {
        return isSelected;
    }

    public String get_questionnaireName() {
        return questionnaireName;
    }

    //setter
    public void set_isSelected(boolean selected) {
        isSelected = selected;
    }
}
