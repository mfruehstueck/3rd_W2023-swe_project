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
    //TODO add playername
        startingGameSession(new DefaultMode("p1"), get_stage(ui_container));
    }

    public void pressEndlessModeButton() {
    //TODO add playername
        startingGameSession(new EndlessMode("p2"), get_stage(ui_container));
    }

    public void pressTrainingModeButton() {
    }

  public void pressBackButton() { set_view(get_stage(ui_container), View.MENU_VIEW); }

}
