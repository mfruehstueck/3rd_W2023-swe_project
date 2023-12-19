module at.onlyquiz.onlyquiz {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.onlyquiz.onlyquiz to javafx.fxml;
    exports at.onlyquiz.onlyquiz;
}