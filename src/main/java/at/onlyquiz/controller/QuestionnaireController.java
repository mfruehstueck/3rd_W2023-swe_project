package at.onlyquiz.controller;

import at.onlyquiz.util.QuestionDictionary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionnaireController implements Initializable {
   @FXML
   public ListView<String> ui_questionnaire_listView;
   public ListView<String> ui_question_listView;

   //NSEC: instance members
   public QuestionnaireController() {

   }
   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
      questionnaire_list = FXCollections.observableArrayList();
      question_list = FXCollections.observableArrayList();

//      ui_questionnaire_listView.setCellFactory(ui_questionnaire_listView -> new);

      fill_questionnaire_listView();

      ui_questionnaire_listView.setItems(questionnaire_list);
   }
   //NSEC: instance fields
   private ObservableList<String> questionnaire_list;
   private ObservableList<String> question_list;

   //NSEC: private methods
   private void fill_questionnaire_listView() { questionnaire_list.setAll(QuestionDictionary.get_QuestionnaireFiles().keySet()); }
   private void fill_question_listView(){

   }
   public void onClick_ui_questionnaire_listView_listItem(MouseEvent mouseEvent) {
   }
   public void onClick_ui_question_listView_listItem(MouseEvent mouseEvent) {
   }
}
