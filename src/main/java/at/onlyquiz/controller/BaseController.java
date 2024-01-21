package at.onlyquiz.controller;

import at.onlyquiz.controller.factories.ControllerFactory;
import at.onlyquiz.controller.factories.View;
import at.onlyquiz.gameplay.GameMode;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class BaseController implements Initializable {
    protected Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

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

    public Stage get_stage(GridPane ui_container) {
        return stage = (Stage) ui_container.getScene().getWindow();
    }

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

    public void showResultScreen(GameMode gameMode, Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ControllerFactory.class.getResource(View.RESULT_SCREEN_VIES.getPath()));
            Parent parent = fxmlLoader.load();
            ResultScreenController resultScreenController = fxmlLoader.getController();
            resultScreenController.setCurrentGameMode(gameMode);
            stage.setScene(new Scene(parent));
            stage.sizeToScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<ButtonType> showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        if (alertType == Alert.AlertType.CONFIRMATION) {
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        } else if (alertType == Alert.AlertType.INFORMATION) {
            alert.getButtonTypes().setAll(ButtonType.OK);
        }

        return alert.showAndWait();
    }
}
