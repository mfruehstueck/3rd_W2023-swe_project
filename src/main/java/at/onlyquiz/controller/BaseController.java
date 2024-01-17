package at.onlyquiz.controller;

import at.onlyquiz.controller.factories.ControllerFactory;
import at.onlyquiz.controller.factories.View;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {
  protected Stage stage;

  public void set_view(Stage stage, View view) {
    try {
      if (view == View.QUIT) {
        stage.close();
      } else {
        stage.setScene(ControllerFactory.get_scene(view));
        stage.sizeToScene();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Stage get_stage(GridPane ui_container) { return stage = (Stage) ui_container.getScene().getWindow(); }
}
