module at.onlyquiz.onlyquiz {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires jdk.httpserver;
    requires com.google.zxing;
    requires qrgen;


    opens at.onlyquiz to javafx.fxml;
    opens at.onlyquiz.controller to javafx.fxml;
    opens at.onlyquiz.util.jsonParser to com.fasterxml.jackson.annotation;
    exports at.onlyquiz;
    exports at.onlyquiz.util.jsonParser;
    exports at.onlyquiz.gameplay;
    exports at.onlyquiz.util.jsonParser.models;
    exports at.onlyquiz.util;
    exports at.onlyquiz.util.userManagement;
    exports at.onlyquiz.model.question;
}