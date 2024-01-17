package at.onlyquiz.controller;

import at.onlyquiz.controller.factories.View;
import at.onlyquiz.util.GeneralSettings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GeneralSettingsController extends BaseController implements Initializable {
  @FXML
  public CheckBox colorBlindCheckBox;
  @FXML
  public Button backButton;
  @FXML
  public GridPane ui_container;
  public GeneralSettingsController() { }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    colorBlindCheckBox.setSelected(GeneralSettings.isColorBlind());
  }

  public void pressColorBlindCheckbox() {
    GeneralSettings.setColorBlind(colorBlindCheckBox.isSelected());
  }

  public void pressBackButton() { set_view(get_stage(ui_container), View.MENU_VIEW); }
}
