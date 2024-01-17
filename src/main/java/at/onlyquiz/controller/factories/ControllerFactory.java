package at.onlyquiz.controller.factories;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class ControllerFactory {

    public static Scene get_scene(View view) throws IOException { return new Scene(get_fxmlLoader(view).load()); }
    public static <T> T get_controller(View view) { return get_fxmlLoader(view).getController(); }
    private static FXMLLoader get_fxmlLoader(View view) { return new FXMLLoader(ControllerFactory.class.getResource(view.getPath())); }
}
