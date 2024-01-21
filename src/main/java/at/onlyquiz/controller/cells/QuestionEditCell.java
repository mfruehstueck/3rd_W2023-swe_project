package at.onlyquiz.controller.cells;

import at.onlyquiz.controller.cells.models.QuestionEdit;
import at.onlyquiz.controller.cells.models.QuestionnaireEdit;
import at.onlyquiz.controller.eventHandlers.OnClickEventHandler;
import at.onlyquiz.model.question.GameQuestion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class QuestionEditCell extends BaseCell<GameQuestion> {
  private final Label ui_questionName;
  private final HBox ui_layout;
  private final Button editButton;
  private final Button ui_delete_button;

  public QuestionEditCell(OnClickEventHandler<GameQuestion> onClick_editButton, OnClickEventHandler<GameQuestion> onClick_delete_button) {
    super();

    this.editButton = new Button();
    editButton.setText("Edit");
    editButton.getStyleClass().add("edit-button");
    editButton.setOnAction(actionEvent -> onClick_editButton.onCLick(getItem()));

    ui_delete_button = new Button();
    ui_delete_button.setText("X");
    ui_delete_button.getStyleClass().add("delete-button");
    ui_delete_button.setOnAction(actionEvent -> onClick_delete_button.onCLick(getItem()));

    this.ui_questionName = new Label();
    this.ui_layout = new HBox(ui_questionName, blank, editButton, ui_delete_button);
    this.ui_layout.setSpacing(10);
  }

  @Override
  protected void updateItem(GameQuestion item, boolean empty) {
    super.updateItem(item, empty);

    if (isEmptyItem(item, empty)) return;

    ui_questionName.setText(item.getQuestion());

    HBox.setHgrow(blank, Priority.ALWAYS);
    setGraphic(ui_layout);
  }
}
