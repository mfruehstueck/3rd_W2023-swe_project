package at.onlyquiz.controller.factories;

import at.onlyquiz.controller.GameSessionController;
import at.onlyquiz.controller.GameSessionSettingController;
import at.onlyquiz.controller.MenuController;
import at.onlyquiz.controller.QuestionnaireController;
import at.onlyquiz.gameplay.GameMode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class ControllerFactory {
    private static GameSessionSettingController gameSessionSettingController;
    private static MenuController menuController;
    private static QuestionnaireController questionnaireController;

    public static <T> T getController(Controllers controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerFactory.class.getResource(controller.getPath()));
        switch (controller) {
            case MENU_VIEW:
                fxmlLoader.setController(getMenuController());
                break;

            case QUESTIONNAIRE_VIEW:
                fxmlLoader.setController(getQuestionnaireController());
                break;
            case GAME_SESSION_SETTINGS:
                fxmlLoader.setController(getGameSettingController());
                break;

        }

        return fxmlLoader.load();
    }

    public static Parent startingGameSession(GameMode gameMode) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerFactory.class.getResource(Controllers.GAME_SESSION_VIEW.getPath()));
        Parent scene = fxmlLoader.load();
        GameSessionController gameSessionController = fxmlLoader.getController();
        gameSessionController.setCurrentGameMode(gameMode);

        return scene;
    }

    private static GameSessionSettingController getGameSettingController() {
        if (gameSessionSettingController == null) {
            gameSessionSettingController = new GameSessionSettingController();
        }
        return gameSessionSettingController;
    }

    private static MenuController getMenuController() {
        if (menuController == null) {
            menuController = new MenuController();
        }
        return menuController;
    }

    private static QuestionnaireController getQuestionnaireController() {
        if (questionnaireController == null) {
            questionnaireController = new QuestionnaireController();
        }
        return questionnaireController;
    }
}
