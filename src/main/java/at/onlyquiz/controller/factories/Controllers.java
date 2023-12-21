package at.onlyquiz.controller.factories;

import java.net.URL;

public enum Controllers {
    MENU_VIEW("/at/onlyquiz/views/menu-view.fxml"),
    GENERAL_SETTINGS_VIEW("/at/onlyquiz/views/generalSettings-view.fxml"),
    GAME_SESSION_VIEW("/at/onlyquiz/views/gameSession-view.fxml"),
    GAME_SESSION_SETTINGS("/at/onlyquiz/views/gameSessionSettings-view.fxml"),
    QUESTIONNAIRE_VIEW("/at/onlyquiz/views/questionnaire-view.fxml"),
    QUIT("no path necessary");

    private String path;

    Controllers(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public URL getRecource(){
        return getClass().getResource(path);
    }
}
