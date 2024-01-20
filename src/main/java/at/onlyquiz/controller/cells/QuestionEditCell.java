package at.onlyquiz.controller.cells;

import at.onlyquiz.controller.cells.models.QuestionEdit;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class QuestionEditCell extends BaseCell<QuestionEdit> {
  private final Label ui_questionName;
  private final HBox ui_layout;

  public QuestionEditCell() {
    super();

    this.ui_questionName = new Label();
    this.ui_layout = new HBox(ui_questionName);
  }

  @Override
  protected void updateItem(QuestionEdit item, boolean empty) {
    super.updateItem(item, empty);

    if (isEmptyItem(item, empty)) return;

    ui_questionName.setText(item.getQuestionName());

    HBox.setHgrow(blank, Priority.ALWAYS);
    setGraphic(ui_layout);
  }
}
