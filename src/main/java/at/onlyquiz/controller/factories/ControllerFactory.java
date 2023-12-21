package at.onlyquiz.controller.factories;

import at.onlyquiz.controller.GameSessionController;
import at.onlyquiz.controller.GameSessionSettingController;
import at.onlyquiz.controller.QuestionnaireController;
import at.onlyquiz.gameplay.GameMode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class ControllerFactory {

    public static Scene getScene(Controllers controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerFactory.class.getResource(controller.getPath()));
        return new Scene(fxmlLoader.load());
    }

    public static Scene startingGameSession(GameMode gameMode) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerFactory.class.getResource(Controllers.GAME_SESSION_VIEW.getPath()));
        Parent parent = fxmlLoader.load();
        GameSessionController gameSessionController = fxmlLoader.getController();
        gameSessionController.setCurrentGameMode(gameMode);

        return new Scene(parent);
    }
}
