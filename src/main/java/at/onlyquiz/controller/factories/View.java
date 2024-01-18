package at.onlyquiz.controller.factories;

public enum View {
  //    at/onlyquiz/views/menu-view.fxml
  MENU_VIEW("/at/onlyquiz/views/menu-view.fxml"),
  GENERAL_SETTINGS_VIEW("/at/onlyquiz/views/generalSettings-view.fxml"),
  GAME_SESSION_VIEW("/at/onlyquiz/views/gameSession-view.fxml"),
  GAME_SESSION_SETTINGS("/at/onlyquiz/views/gameSessionSettings-view.fxml"),
  QUESTIONNAIRE_VIEW("/at/onlyquiz/views/questionnaire-view.fxml"),
  QUIT("no path necessary");

  private final String path;

  View(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }
}
