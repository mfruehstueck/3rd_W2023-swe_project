package at.onlyquiz.controller;

import at.onlyquiz.controller.factories.ControllerFactory;
import at.onlyquiz.controller.factories.View;
import at.onlyquiz.gameplay.GameMode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

  public void startingGameSession(GameMode gameMode, Stage stage) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(ControllerFactory.class.getResource(View.GAME_SESSION_VIEW.getPath()));
      Parent parent = fxmlLoader.load();
      GameSessionController gameSessionController = fxmlLoader.getController();
      gameSessionController.setCurrentGameMode(gameMode);

      stage.setScene(new Scene(parent));
      stage.sizeToScene();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
