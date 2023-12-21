package at.onlyquiz.controller;

import at.onlyquiz.controller.factories.ControllerFactory;
import at.onlyquiz.controller.factories.Controllers;
import at.onlyquiz.gameplay.DefaultMode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    public Button playButton, questionnaireButton, settingsButton, quitButton;

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

    private void changeScene(Controllers controller){
        Stage currentStage = (Stage) playButton.getScene().getWindow();
        if (controller == Controllers.QUIT){
            currentStage.close();
        }

        try {
            currentStage.setScene(ControllerFactory.getScene(controller));
            currentStage.sizeToScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}