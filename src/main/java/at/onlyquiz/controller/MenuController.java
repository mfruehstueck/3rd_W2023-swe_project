package at.onlyquiz.controller;

import at.onlyquiz.controller.factories.View;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController extends BaseController implements Initializable {
  @FXML
  public Button playButton, questionnaireButton, settingsButton, quitButton;
  @FXML
  public GridPane ui_container;

  public MenuController() { }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
        if (GeneralSettings.isColorBlind()){
            container.getStylesheets().add(String.valueOf(getClass().getResource("/at/onlyquiz/styles/gameSession.css")));
            container.getStylesheets().removeAll(String.valueOf(getClass().getResource("/at/onlyquiz/styles/general.css")));
        }
        */
  }

  public void pressPlayButton() { set_view(get_stage(ui_container), View.GAME_SESSION_SETTINGS); }
  public void pressQuestionnairesButton() { set_view(get_stage(ui_container), View.QUESTIONNAIRE_VIEW); }
  public void pressSettingsButton() { set_view(get_stage(ui_container), View.GENERAL_SETTINGS_VIEW); }
  public void pressQuitButton() { set_view(get_stage(ui_container), View.QUIT); }
}