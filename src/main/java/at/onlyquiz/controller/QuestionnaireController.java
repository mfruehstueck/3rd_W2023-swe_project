package at.onlyquiz.controller;

import at.onlyquiz.controller.cells.QuestionEditCell;
import at.onlyquiz.controller.cells.QuestionnaireEditCell;
import at.onlyquiz.controller.cells.models.QuestionnaireEdit;
import at.onlyquiz.controller.eventHandlers.OnClickEventHandler;
import at.onlyquiz.controller.factories.View;
import at.onlyquiz.gameplay.EditMode;
import at.onlyquiz.model.question.GameQuestion;
import at.onlyquiz.util.Configuration;
import at.onlyquiz.util.FileManagement;
import at.onlyquiz.util.QuestionDictionary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class QuestionnaireController extends BaseController implements Initializable {
    @FXML
    public ListView<QuestionnaireEdit> ui_questionnaire_listView;
    @FXML
    public ListView<GameQuestion> ui_question_listView;
    @FXML
    public GridPane ui_container;
    @FXML
    public TextField ui_newQuestionnaire_textField;

    private ObservableList<QuestionnaireEdit> questionnaire_obstList;
    private ObservableList<GameQuestion> question_obstList;
    private QuestionnaireEdit questionnaire_listView_selected;
    private Integer question_listView_selectedIdx;

    //NSEC: instance members
    public QuestionnaireController() {
        super();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        questionnaire_obstList = FXCollections.observableArrayList();
        question_obstList = FXCollections.observableArrayList();

        QuestionDictionary.init();
        fill_questionnaire_obstList();

        ui_questionnaire_listView.setItems(questionnaire_obstList);
        ui_questionnaire_listView.setCellFactory(listView -> new QuestionnaireEditCell(onClick_Export, onClick_editQuestionnaire, onClick_deleteQuestionnaire));

        ui_question_listView.setItems(question_obstList);
        ui_question_listView.setCellFactory(listView -> new QuestionEditCell(onClick_editQuestion, onClick_deleteQuestion));

        ui_questionnaire_listView.getSelectionModel().selectedItemProperty().addListener((observable -> {
            questionnaire_listView_selected = ui_questionnaire_listView.getSelectionModel().getSelectedItem();
            if (questionnaire_listView_selected == null) return;
            question_obstList.setAll(QuestionDictionary.get_allQuestions(questionnaire_listView_selected.getQuestionnaireName()));
        }));

        ui_question_listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            question_listView_selectedIdx = ui_question_listView.getSelectionModel().getSelectedIndex();
        });
    }

    //NSEC: eventHandlers
    private final OnClickEventHandler<QuestionnaireEdit> onClick_Export = (item) -> {
        File destionationQuestionnaire = FileManagement.save_file("Export Questionnaire", new String[]{"csv Files", "*.csv"});

        if (destionationQuestionnaire == null) return;

        File sourceQuestionnaire = QuestionDictionary.get_QuestionnaireFiles().get(item.getQuestionnaireName()).toFile();
        FileManagement.copy_file(sourceQuestionnaire, destionationQuestionnaire);
    };

    private final OnClickEventHandler<QuestionnaireEdit> onClick_editQuestionnaire = (item) -> {
        questionnaire_listView_selected = item;
        open_editMode(0);
    };

    private final OnClickEventHandler<QuestionnaireEdit> onClick_deleteQuestionnaire = (item) -> {
        File questionnaire = QuestionDictionary.get_QuestionnaireFiles().get(item.getQuestionnaireName()).toFile();
        FileManagement.delete_file(questionnaire);
        ui_questionnaire_listView.getItems().remove(item);
        QuestionDictionary.init();
    };

    private final OnClickEventHandler<GameQuestion> onClick_editQuestion = (item) -> {
        question_listView_selectedIdx = ui_question_listView.getItems().indexOf(item);
        open_editMode(question_listView_selectedIdx);
    };

    private final OnClickEventHandler<GameQuestion> onClick_deleteQuestion = (item) -> {
        String selected_questionnaireName = questionnaire_listView_selected.getQuestionnaireName();
        question_listView_selectedIdx = ui_question_listView.getItems().indexOf(item);
        ui_question_listView.getItems().remove(item);

        QuestionDictionary.delete_gameQuestion(selected_questionnaireName, item.getLineIdx());
    };

    //NSEC: instance fields


    //NSEC: private methods
    private void fill_questionnaire_obstList() {
        questionnaire_obstList.clear();
        for (var k : QuestionDictionary.get_QuestionnaireFiles().keySet()) {
            questionnaire_obstList.add(new QuestionnaireEdit(k));
        }
    }

    private void open_editMode(int idx) {
        String selected_questionnaireName = questionnaire_listView_selected.getQuestionnaireName();
        List<GameQuestion> gameQuestions = QuestionDictionary.get_allQuestions(selected_questionnaireName);

        //if file empty because of previous edit mode deleted all, create new question
        if (gameQuestions.isEmpty()) {
            gameQuestions.add(new GameQuestion());
        }

        EditMode editMode = new EditMode(gameQuestions, idx, selected_questionnaireName);

        startingGameSession(editMode, get_stage(ui_container));
    }

    @FXML
    public void onClick_ui_back_button() {
        set_view(get_stage(ui_container), View.MENU_VIEW);
    }

    @FXML
    public void onClick_ui_editQuestionnaire_button() {
        String newQuestionnaireName = ui_newQuestionnaire_textField.getText();

        if (newQuestionnaireName.isEmpty()) return;
        if (QuestionDictionary.get_QuestionnaireFiles().containsKey(newQuestionnaireName)) return;

        QuestionDictionary.update_gameQuestion(newQuestionnaireName, new GameQuestion());

        QuestionDictionary.init();
        fill_questionnaire_obstList();
    }

    @FXML
    public void onClick_ui_importQuestionnaire_button() {
        File sourceQuestionnaire = FileManagement.open_file("Import Questionnaire", new String[]{"csv Files", "*.csv"});

        if (sourceQuestionnaire == null) return;

        File destinationQuestionnaire = FileManagement.getPath(Configuration.DEFAULT_BASEPATH_QUESTIONNARES, sourceQuestionnaire.getName()).toFile();
        FileManagement.copy_file(sourceQuestionnaire, destinationQuestionnaire);

        QuestionDictionary.init();
        fill_questionnaire_obstList();
    }
}
