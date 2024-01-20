package at.onlyquiz.controller;

import at.onlyquiz.gameplay.EndlessMode;
import javafx.scene.control.TextField;
import at.onlyquiz.controller.cells.QuestionnaireSelectionCell;
import at.onlyquiz.controller.cells.models.QuestionnaireSelection;
import at.onlyquiz.controller.eventHandlers.OnClickEventHandler;
import at.onlyquiz.controller.factories.View;
import at.onlyquiz.gameplay.DefaultMode;
import at.onlyquiz.util.QuestionDictionary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameSessionSettingController extends BaseController {
  @FXML
  public Button defaultModeButton, endlessModeButton, trainingModeButton, backButton;
  @FXML
  public TextField playerNameTextField;
  @FXML
  public GridPane ui_container;
  @FXML
  public ListView<QuestionnaireSelection> ui_questionnaireSelection_listView;

  private ObservableList<QuestionnaireSelection> questionnaireSelection_obstList;
  
    public GameSessionSettingController() {

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);

    questionnaireSelection_obstList = FXCollections.observableArrayList();
    var keySet = QuestionDictionary.get_QuestionnaireFiles().keySet();
    for (var k : keySet) { questionnaireSelection_obstList.add(new QuestionnaireSelection(k, false)); }

    ui_questionnaireSelection_listView.setItems(questionnaireSelection_obstList);
    ui_questionnaireSelection_listView.setCellFactory(listView -> new QuestionnaireSelectionCell(onClick_update));
  }

  private final OnClickEventHandler<QuestionnaireSelection> onClick_update = (item) -> item.set_isSelected(!item.isSelected());

  private List<String> get_selectedQuestionnairs() {
    List<String> selectedQuestionnairs = new ArrayList<>();

    for (var item : questionnaireSelection_obstList) { if (item.isSelected()) selectedQuestionnairs.add(item.get_questionnaireName()); }

    return selectedQuestionnairs;
  }
  
    public void pressTrainingModeButton() {
  }

    public void pressDefaultModeButton() {
        startingGameSession(new DefaultMode(playerNameTextField.getText()), get_stage(ui_container));
    }

    public void pressEndlessModeButton() {
        startingGameSession(new EndlessMode(playerNameTextField.getText()), get_stage(ui_container));
    }


  public void pressBackButton() { set_view(get_stage(ui_container), View.MENU_VIEW); }
  
}
