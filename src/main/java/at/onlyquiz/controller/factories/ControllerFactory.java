package at.onlyquiz.controller.factories;

import at.onlyquiz.controller.GameSessionController;
import at.onlyquiz.controller.GameSessionSettingController;
import at.onlyquiz.controller.MenuController;
import at.onlyquiz.controller.QuestionnaireController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ControllerFactory {
    private static GameSessionController gameSessionController;
    private static GameSessionSettingController gameSessionSettingController;
    private static MenuController menuController;
    private static QuestionnaireController questionnaireController;

    public static <T> T getController(Controllers controller) throws IOException {
        System.out.println(ControllerFactory.class.getResource(controller.getPath()));
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerFactory.class.getResource(controller.getPath()));
        switch (controller) {
            case MENU_VIEW:
                fxmlLoader.setController(getMenuController());
                break;
            case GAME_SESSION_VIEW:
                fxmlLoader.setController(getGameSessionController());
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


    private static GameSessionController getGameSessionController() {
        if (gameSessionController == null) {
            gameSessionController = new GameSessionController();
        }
        return gameSessionController;
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
