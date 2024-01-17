package at.onlyquiz.controller.factories;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class ControllerFactory {

  public static Scene getScene(Controllers controller) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(ControllerFactory.class.getResource(controller.getPath()));
    return new Scene(fxmlLoader.load());
  }

}
