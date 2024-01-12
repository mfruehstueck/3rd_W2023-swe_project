package at.onlyquiz.controller;

import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.util.QuestionDictionary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class QuestionnaireController implements Initializable {
   @FXML
   public ListView<String> ui_questionnaire_listView;
   @FXML
   public ListView<String> ui_question_listView;

   //NSEC: instance members
   public QuestionnaireController() {

   }
   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
      questionnaire_list = FXCollections.observableArrayList();
      question_list = FXCollections.observableArrayList();

//      ui_questionnaire_listView.setCellFactory(ui_questionnaire_listView -> new);

      fill_questionnaire_list();

      ui_questionnaire_listView.setItems(questionnaire_list);
      ui_question_listView.setItems(question_list);
   }
   //NSEC: instance fields
   private ObservableList<String> questionnaire_list;
   private ObservableList<String> question_list;
   private String ui_questionnaire_listView_selected;
   private String ui_question_listView_selected;

   //NSEC: private methods
   private void fill_questionnaire_list() { questionnaire_list.setAll(QuestionDictionary.get_QuestionnaireFiles().keySet()); }
   private void fill_question_listView() {

   }
   @FXML
   public void onClick_ui_questionnaire_listView_listItem(MouseEvent mouseEvent) {
      System.out.println(mouseEvent);
      ui_questionnaire_listView_selected = ui_questionnaire_listView.getSelectionModel().getSelectedItem();
      if(ui_questionnaire_listView_selected == null) return;
      var dic = QuestionDictionary.get_dictionary();
      var a = QuestionDictionary.get_QuestionnaireFiles().get(ui_questionnaire_listView_selected);
      var csv = dic.get(a);
      var tsl = csv.get(0);
      var dif = tsl.get(Difficulty.HARD);
//      List<Integer> current_listOf_lineIdxs = QuestionDictionary.get_dictionary()
//              .get(QuestionDictionary.get_QuestionnaireFiles().get(ui_question_listView_selected))
//              .get(0).get(Difficulty.HARD);
      question_list.setAll(QuestionDictionary.get_questions(a));
   }
   @FXML
   public void onClick_ui_question_listView_listItem(MouseEvent mouseEvent) {
   }
}
