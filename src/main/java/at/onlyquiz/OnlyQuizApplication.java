package at.onlyquiz;

import at.onlyquiz.controller.factories.ControllerFactory;
import at.onlyquiz.controller.factories.Controllers;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OnlyQuizApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(ControllerFactory.getController(Controllers.GAME_SESSION_VIEW), 1280, 720);
        stage.setTitle("OnlyQuiz");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}