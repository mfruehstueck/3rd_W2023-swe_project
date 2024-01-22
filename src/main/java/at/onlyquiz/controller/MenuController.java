package at.onlyquiz.controller;

import at.onlyquiz.controller.factories.View;
import at.onlyquiz.util.jsonParser.models.PersistScore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController extends BaseController implements Initializable {
    @FXML
    public Button playButton, questionnaireButton, settingsButton, quitButton;
    @FXML
    public GridPane ui_container;

    @FXML
    public TableView<PersistScore> top10Table;
    @FXML
    public Button logoutButton;
    @FXML
    private TableColumn<PersistScore, String> playerNameColumn, scoreColumn, gameModeColumn;

    public MenuController() { }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        List<PersistScore> allScores = PersistScore.getTop10fromAllGameModes();
        ObservableList<PersistScore> sortedScores = FXCollections.observableArrayList(allScores);

        top10Table.setItems(sortedScores);
        gameModeColumn.setCellValueFactory(new PropertyValueFactory<>("gameMode"));
        playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        top10Table.sort();
    }

    public void pressPlayButton() {
        set_view(get_stage(ui_container), View.GAME_SESSION_SETTINGS);
    }
    public void pressQuestionnairesButton() {
        set_view(get_stage(ui_container), View.QUESTIONNAIRE_VIEW);
    }
    public void pressSettingsButton() {
        set_view(get_stage(ui_container), View.SCORE_HISTORY_VIEW);
    }
    public void pressQuitButton() {
        set_view(get_stage(ui_container), View.QUIT);
    }
    public void pressLogoutButton() { set_view(get_stage(ui_container), View.LOGIN_VIEW); }
}