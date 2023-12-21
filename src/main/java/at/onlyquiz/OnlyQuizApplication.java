package at.onlyquiz;

import at.onlyquiz.controller.factories.ControllerFactory;
import at.onlyquiz.controller.factories.Controllers;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OnlyQuizApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = ControllerFactory.getScene(Controllers.MENU_VIEW);
        stage.setTitle("OnlyQuiz");
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}