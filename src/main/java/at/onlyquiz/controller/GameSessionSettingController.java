package at.onlyquiz.controller;

import at.onlyquiz.controller.factories.ControllerFactory;
import at.onlyquiz.controller.factories.View;
import at.onlyquiz.gameplay.CreationMode;
import at.onlyquiz.gameplay.DefaultMode;
import at.onlyquiz.gameplay.GameMode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameSessionSettingController extends BaseController {
    @FXML
    public Button defaultModeButton, endlessModeButton, trainingModeButton, backButton;
    @FXML
    public GridPane ui_container;

    public GameSessionSettingController() { }

    public void pressDefaultModeButton() {
        startingGameSession(new DefaultMode());
    }

    public void pressEndlessModeButton() {
        startingGameSession(new CreationMode());
    }

    public void pressTrainingModeButton() {
    }

    public void pressBackButton() { set_view(get_stage(ui_container), View.MENU_VIEW); }

    public void startingGameSession(GameMode gameMode) {
//        GameSessionController gameSessionController = ControllerFactory.get_controller(View.GAME_SESSION_VIEW);
//        gameSessionController.setCurrentGameMode(gameMode);
//        set_view(get_stage(ui_container), View.GAME_SESSION_VIEW);
        try {
            Stage currentStage = (Stage) defaultModeButton.getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(ControllerFactory.class.getResource(View.GAME_SESSION_VIEW.getPath()));
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
