package at.onlyquiz.controller;

import at.onlyquiz.controller.factories.View;
import at.onlyquiz.gameplay.DefaultMode;
import at.onlyquiz.gameplay.EndlessMode;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class GameSessionSettingController extends BaseController {
  @FXML
  public Button defaultModeButton, endlessModeButton, trainingModeButton, backButton;
  @FXML
  public GridPane ui_container;

  public GameSessionSettingController() { }

    public void pressDefaultModeButton() {
        startingGameSession(new DefaultMode(), get_stage(ui_container));
    }

    public void pressEndlessModeButton() {
      startingGameSession(new EndlessMode(), get_stage(ui_container));
    }

    public void pressTrainingModeButton() {
    }

  public void pressBackButton() { set_view(get_stage(ui_container), View.MENU_VIEW); }

}
