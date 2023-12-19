module at.onlyquiz.onlyquiz {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;


    opens at.onlyquiz to javafx.fxml;
    exports at.onlyquiz;
}