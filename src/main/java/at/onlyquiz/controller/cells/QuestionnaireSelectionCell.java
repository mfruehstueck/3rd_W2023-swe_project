package at.onlyquiz.controller.cells;

import at.onlyquiz.controller.cells.models.QuestionnaireSelection;
import at.onlyquiz.controller.eventHandlers.OnClickEventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class QuestionnaireSelectionCell extends BaseCell<QuestionnaireSelection> {
  private final Label ui_questionnaireName;
  private final CheckBox ui_isSelected;
  private final HBox ui_layout;

  public QuestionnaireSelectionCell(OnClickEventHandler<QuestionnaireSelection> onClick_ui_isSelected) {
    super();

    this.ui_questionnaireName = new Label();
    this.ui_isSelected = new CheckBox();
    this.ui_layout = new HBox(ui_questionnaireName, blank, ui_isSelected);

    this.ui_isSelected.setOnMouseClicked(mouseEvent -> onClick_ui_isSelected.onCLick(getItem()));
  }

  @Override
  protected void updateItem(QuestionnaireSelection item, boolean empty) {
    super.updateItem(item, empty);

    if (isEmptyItem(item, empty)) return;

    ui_questionnaireName.setText(item.get_questionnaireName());
    ui_isSelected.setSelected(item.isSelected());

    HBox.setHgrow(blank, Priority.ALWAYS);
    setGraphic(ui_layout);
  }
}
