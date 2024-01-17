package at.onlyquiz.controller;

import at.onlyquiz.controller.factories.View;
import at.onlyquiz.util.QuestionDictionary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionnaireController extends BaseController implements Initializable {
  @FXML
  public ListView<String> ui_questionnaire_listView;
  @FXML
  public ListView<String> ui_question_listView;
  @FXML
  public GridPane ui_container;

  //NSEC: instance members
  public QuestionnaireController() { }
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

  //NSEC: event handlers
  @FXML
  public void onClick_ui_questionnaire_listView_listItem(MouseEvent mouseEvent) {
    ui_questionnaire_listView_selected = ui_questionnaire_listView.getSelectionModel().getSelectedItem();
    if (ui_questionnaire_listView_selected == null) return;
    var a = QuestionDictionary.get_QuestionnaireFiles().get(ui_questionnaire_listView_selected);
    question_list.setAll(QuestionDictionary.get_questions(a));
  }
  @FXML
  public void onClick_ui_question_listView_listItem(MouseEvent mouseEvent) {
  }

  @FXML
  public void onClick_ui_back_button(ActionEvent actionEvent) { set_view(get_stage(ui_container), View.MENU_VIEW); }
  public void onClick_ui_editQuestionnaire_button(ActionEvent actionEvent) { }
  public void onClick_ui_editQuestion_button(ActionEvent actionEvent) { }
}
