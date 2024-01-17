package at.onlyquiz.controller;

import at.onlyquiz.controller.factories.ControllerFactory;
import at.onlyquiz.controller.factories.Controllers;
import at.onlyquiz.util.GeneralSettings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GeneralSettingsController implements Initializable {
  @FXML
  public CheckBox colorBlindCheckBox;
  @FXML
  public Button backButton;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    colorBlindCheckBox.setSelected(GeneralSettings.isColorBlind());
  }

  public void pressColorBlindCheckbox() {
    GeneralSettings.setColorBlind(colorBlindCheckBox.isSelected());
  }

  public void pressBackButton() {
    try {
      Stage currentStage = (Stage) colorBlindCheckBox.getScene().getWindow();
      currentStage.setScene(ControllerFactory.getScene(Controllers.MENU_VIEW));
      currentStage.sizeToScene();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
