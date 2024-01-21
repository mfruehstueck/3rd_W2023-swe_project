package at.onlyquiz.controller.cells;

import at.onlyquiz.controller.cells.models.QuestionnaireEdit;
import at.onlyquiz.controller.eventHandlers.OnClickEventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class QuestionnaireEditCell extends BaseCell<QuestionnaireEdit> {
    private final Label ui_questionnaireName;
    private final Button ui_rename_button;
    private final Button ui_export_button;
    private final HBox ui_layout;
    private final Button editButton;
    private final Button ui_delete_button;


    public QuestionnaireEditCell(OnClickEventHandler<QuestionnaireEdit> onClick_ui_export_button,
                                 OnClickEventHandler<QuestionnaireEdit> onClick_editButton,
                                 OnClickEventHandler<QuestionnaireEdit> onClick_delete_button) {
        super();

        ui_questionnaireName = new Label();
        ui_rename_button = new Button();


        editButton = new Button();
        editButton.setText("Edit");
        editButton.getStyleClass().add("edit-button");
        editButton.setOnAction(actionEvent -> onClick_editButton.onCLick(getItem()));

        ui_export_button = new Button();
        ui_export_button.setText("Export");
        ui_export_button.getStyleClass().add("export-button");
        ui_export_button.setOnAction(actionEvent -> onClick_ui_export_button.onCLick(getItem()));

        ui_delete_button = new Button();
        ui_delete_button.setText("X");
        ui_delete_button.getStyleClass().add("delete-button");
        ui_delete_button.setOnAction(actionEvent -> onClick_delete_button.onCLick(getItem()));

        ui_layout = new HBox(ui_questionnaireName, blank, editButton, ui_export_button, ui_delete_button);
        ui_layout.setSpacing(10);
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
