package at.onlyquiz.controller.cells;

import at.onlyquiz.controller.cells.models.QuestionnaireEdit;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class QuestionnaireEditCell extends BaseCell<QuestionnaireEdit> {
  private final Label ui_questionnaireName;
  private final Button ui_rename_button;
  private final HBox ui_layout;


  public QuestionnaireEditCell() {
    super();

    ui_questionnaireName = new Label();
    ui_rename_button = new Button();

    ui_layout = new HBox(ui_questionnaireName, blank);
  }

  @Override
  protected void updateItem(QuestionnaireEdit item, boolean empty) {
    super.updateItem(item, empty);

    if (isEmptyItem(item, empty)) return;

    ui_questionnaireName.setText(item.getQuestionnaireName());

    HBox.setHgrow(blank, Priority.ALWAYS);
    setGraphic(ui_layout);
  }
}
