package at.onlyquiz.controller;

import at.onlyquiz.controller.cells.QuestionnaireSelectionCell;
import at.onlyquiz.controller.cells.models.QuestionnaireSelection;
import at.onlyquiz.controller.eventHandlers.OnClickEventHandler;
import at.onlyquiz.controller.factories.View;
import at.onlyquiz.gameplay.DefaultMode;
import at.onlyquiz.gameplay.EndlessMode;
import at.onlyquiz.util.Configuration;
import at.onlyquiz.util.QuestionDictionary;
import at.onlyquiz.util.UserManagement;
import at.onlyquiz.util.jsonParser.JSON_Parser;
import at.onlyquiz.util.jsonParser.models.User;
import at.onlyquiz.util.userManagement.User_Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
  public CheckBox liveAudienceCheckBox;

  private ObservableList<QuestionnaireSelection> questionnaireSelection_obstList;
  private User current_user;

  public GameSessionSettingController() {

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);

    current_user = UserManagement.get_currentUser();

    playerNameTextField.setText(current_user.getUserName());

    questionnaireSelection_obstList = FXCollections.observableArrayList();
    var keySet = QuestionDictionary.get_QuestionnaireFiles().keySet();
    for (var k : keySet) { questionnaireSelection_obstList.add(new QuestionnaireSelection(k, false)); }

    ui_questionnaireSelection_listView.setItems(questionnaireSelection_obstList);
    ui_questionnaireSelection_listView.setCellFactory(listView -> new QuestionnaireSelectionCell(onClick_update));

    liveAudienceCheckBox.setOnAction(actionEvent -> {
      if (current_user.getHashedPassword() != null) {
        current_user.get_selectedProperties().put(User_Properties.LIVEAUDIENCE.getLiveAudience(), liveAudienceCheckBox.isSelected());
        JSON_Parser.update(Configuration.USER_FILE, current_user);
      }
    });

    if (current_user.getHashedPassword() != null) {
      if (current_user.get_selectedQuestionnaires().isEmpty() && current_user.get_selectedProperties().isEmpty()) return;
      for (QuestionnaireSelection qs : questionnaireSelection_obstList) {
        if (current_user.get_selectedQuestionnaires().containsKey(qs.get_questionnaireName())) {
          qs.set_isSelected(current_user.get_selectedQuestionnaires().get(qs.get_questionnaireName()));
        }
      }
      liveAudienceCheckBox.setSelected(current_user.get_selectedProperties().get(User_Properties.LIVEAUDIENCE.getLiveAudience()));
    }
  }

  private final OnClickEventHandler<QuestionnaireSelection> onClick_update = (item) -> {
    item.set_isSelected(!item.isSelected());
    if (current_user.getHashedPassword() != null) {
      current_user.get_selectedQuestionnaires().put(item.get_questionnaireName(), item.isSelected());
      JSON_Parser.update(Configuration.USER_FILE, current_user);
    }
  };

  private List<String> get_selectedQuestionnairs() {
    List<String> selectedQuestionnairs = new ArrayList<>();

    for (var item : questionnaireSelection_obstList) { if (item.isSelected()) selectedQuestionnairs.add(item.get_questionnaireName()); }

    return selectedQuestionnairs;
  }

  public void pressDefaultModeButton() {
    if (get_selectedQuestionnairs().isEmpty()) return;
    startingGameSession(new DefaultMode(get_selectedQuestionnairs(), current_user, liveAudienceCheckBox.isSelected()), get_stage(ui_container));
  }

  public void pressEndlessModeButton() {
    if (get_selectedQuestionnairs().isEmpty()) return;
    startingGameSession(new EndlessMode(get_selectedQuestionnairs(), current_user, liveAudienceCheckBox.isSelected()), get_stage(ui_container));
  }

  public void pressTrainingModeButton() {
  }

  public void pressBackButton() { set_view(get_stage(ui_container), View.MENU_VIEW); }


}
