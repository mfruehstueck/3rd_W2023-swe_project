package at.onlyquiz.controller;

import at.onlyquiz.controller.cells.QuestionEditCell;
import at.onlyquiz.controller.cells.QuestionnaireEditCell;
import at.onlyquiz.controller.cells.models.QuestionEdit;
import at.onlyquiz.controller.cells.models.QuestionnaireEdit;
import at.onlyquiz.controller.factories.View;
import at.onlyquiz.gameplay.EditMode;
import at.onlyquiz.model.question.GameQuestion;
import at.onlyquiz.util.QuestionDictionary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class QuestionnaireController extends BaseController implements Initializable {
  @FXML
  public ListView<QuestionnaireEdit> ui_questionnaire_listView;
  @FXML
  public ListView<QuestionEdit> ui_question_listView;
  @FXML
  public GridPane ui_container;
  @FXML
  public TextField ui_newQuestionnaire_textField;

  //NSEC: instance members
  public QuestionnaireController() { super(); }
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);

    questionnaire_obstList = FXCollections.observableArrayList();
    question_obstList = FXCollections.observableArrayList();

    fill_questionnaire_obstList();

    ui_questionnaire_listView.setItems(questionnaire_obstList);
    ui_questionnaire_listView.setCellFactory(listView -> new QuestionnaireEditCell());

    ui_question_listView.setItems(question_obstList);
    ui_question_listView.setCellFactory(listView -> new QuestionEditCell());

    ui_questionnaire_listView.getSelectionModel().selectedItemProperty().addListener((observable -> {
      questionnaire_listView_selected = ui_questionnaire_listView.getSelectionModel().getSelectedItem();
      if (questionnaire_listView_selected == null) return;
      question_obstList.clear();
      for (var item : QuestionDictionary.get_questionNames(questionnaire_listView_selected.getQuestionnaireName())) { question_obstList.add(new QuestionEdit(item)); }
    }));

    ui_question_listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      question_listView_selectedIdx = ui_question_listView.getSelectionModel().getSelectedIndex();
    });
  }
  //NSEC: instance fields
  private ObservableList<QuestionnaireEdit> questionnaire_obstList;
  private ObservableList<QuestionEdit> question_obstList;
  private QuestionnaireEdit questionnaire_listView_selected;
  private Integer question_listView_selectedIdx;

  //NSEC: private methods
  private void fill_questionnaire_obstList() {
    questionnaire_obstList.clear();
    for (var k : QuestionDictionary.get_QuestionnaireFiles().keySet()) {
      questionnaire_obstList.add(new QuestionnaireEdit(k));
    }
  }
  private void fill_question_listView() {

  }

  @FXML
  public void onClick_ui_back_button(ActionEvent actionEvent) { set_view(get_stage(ui_container), View.MENU_VIEW); }

  @FXML
  public void onClick_ui_editQuestionnaire_button(ActionEvent actionEvent) {
    String newQuestionnaireName = ui_newQuestionnaire_textField.getText();

    if (newQuestionnaireName.isEmpty()) return;
    if (QuestionDictionary.get_QuestionnaireFiles().containsKey(newQuestionnaireName)) return;

    QuestionDictionary.update_gameQuestion(newQuestionnaireName, new GameQuestion());

    fill_questionnaire_obstList();
  }

  @FXML
  public void onClick_ui_editQuestion_button(ActionEvent actionEvent) {
    if (questionnaire_listView_selected == null) return;

    EditMode editMode;
    List<GameQuestion> gameQuestions = QuestionDictionary.get_allQuestions(questionnaire_listView_selected.getQuestionnaireName());
    String selected_questionnaireName = questionnaire_listView_selected.getQuestionnaireName();

    question_listView_selectedIdx = (question_listView_selectedIdx == null) ? 0 : question_listView_selectedIdx;

    //if file empty because of previous edit mode deleted all, create new question
    if (gameQuestions.isEmpty()) {
      gameQuestions.add(new GameQuestion());
//      QuestionDictionary.update_gameQuestion(selected_questionnaireName, gameQuestions.get(0));
    }

    editMode = new EditMode(gameQuestions, question_listView_selectedIdx, selected_questionnaireName);

    startingGameSession(editMode, get_stage(ui_container));
  }
}
