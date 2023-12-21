package at.onlyquiz.controller;

import at.onlyquiz.controller.factories.ControllerFactory;
import at.onlyquiz.controller.factories.Controllers;
import at.onlyquiz.gameplay.DefaultMode;
import at.onlyquiz.gameplay.GameMode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class GameSessionSettingController {
    @FXML
    public Button defaultModeButton, endlessModeButton, trainingModeButton, backButton;

    public void pressDefaultModeButton() {
        startingGameSession(new DefaultMode());
    }

    public void pressEndlessModeButton() {
    }

    public void pressTrainingModeButton() {
    }

    public void pressBackButton() {
        try {
            Stage currentStage = (Stage) defaultModeButton.getScene().getWindow();
            currentStage.setScene(ControllerFactory.getScene(Controllers.MENU_VIEW));
            currentStage.sizeToScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startingGameSession(GameMode gameMode) {
        try {
        Stage currentStage = (Stage) defaultModeButton.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(ControllerFactory.class.getResource(Controllers.GAME_SESSION_VIEW.getPath()));
        Parent parent = fxmlLoader.load();
        GameSessionController gameSessionController = fxmlLoader.getController();
        gameSessionController.setCurrentGameMode(gameMode);

        currentStage.setScene(new Scene(parent));
        currentStage.sizeToScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
