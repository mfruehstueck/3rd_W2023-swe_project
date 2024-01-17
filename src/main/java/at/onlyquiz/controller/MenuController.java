package at.onlyquiz.controller;

import at.onlyquiz.controller.factories.ControllerFactory;
import at.onlyquiz.controller.factories.Controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
  @FXML
  public Button playButton, questionnaireButton, settingsButton, quitButton;
  public VBox container;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
        if (GeneralSettings.isColorBlind()){
            container.getStylesheets().add(String.valueOf(getClass().getResource("/at/onlyquiz/styles/gameSession.css")));
            container.getStylesheets().removeAll(String.valueOf(getClass().getResource("/at/onlyquiz/styles/general.css")));
        }
        */
  }

  public void pressPlayButton() {
    changeScene(Controllers.GAME_SESSION_SETTINGS);
  }

  public void pressQuestionnairesButton() {
    changeScene(Controllers.QUESTIONNAIRE_VIEW);
  }

  public void pressSettingsButton() {
    changeScene(Controllers.GENERAL_SETTINGS_VIEW);
  }

  public void pressQuitButton() {
    changeScene(Controllers.QUIT);
  }

  private void changeScene(Controllers controller) {
    try {
      Stage currentStage = (Stage) playButton.getScene().getWindow();
      if (controller == Controllers.QUIT) {
        currentStage.close();
      } else {
        currentStage.setScene(ControllerFactory.getScene(controller));
        currentStage.sizeToScene();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}