module at.onlyquiz.onlyquiz {
  requires javafx.controls;
  requires javafx.fxml;
  requires com.opencsv;
  requires com.fasterxml.jackson.annotation;
  requires com.fasterxml.jackson.databind;
  requires com.fasterxml.jackson.datatype.jsr310;
  requires jdk.httpserver;


  opens at.onlyquiz to javafx.fxml;
  opens at.onlyquiz.controller to javafx.fxml;
  opens at.onlyquiz.util.scoreSystem.savedScoresJSON to com.fasterxml.jackson.annotation;
  exports at.onlyquiz;
  exports at.onlyquiz.util.scoreSystem.savedScoresJSON;
}