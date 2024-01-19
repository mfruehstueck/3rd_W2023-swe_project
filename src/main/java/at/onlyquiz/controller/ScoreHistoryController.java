package at.onlyquiz.controller;

import at.onlyquiz.controller.factories.View;
import at.onlyquiz.util.scoreSystem.savedScoresJSON.PersistScore;
import com.fasterxml.jackson.databind.deser.impl.PropertyValue;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ScoreHistoryController extends BaseController implements Initializable {

    @FXML
    public GridPane ui_container;
    @FXML
    public TableView<PersistScore> gameHistoryTable;
    @FXML
    private TableColumn<PersistScore, String> playerNameColumn, scoreColumn, gameModeColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<PersistScore> allScores = PersistScore.getSavedScores();
        ObservableList<PersistScore> sortedScores = FXCollections.observableArrayList(allScores);

        gameHistoryTable.setItems(sortedScores);
        gameModeColumn.setCellValueFactory(new PropertyValueFactory<>("gameMode"));
        playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        gameHistoryTable.getSortOrder().addAll(gameModeColumn, scoreColumn, playerNameColumn);
        gameHistoryTable.sort();

    }


    public void pressBackButton() {
        set_view(get_stage(ui_container), View.MENU_VIEW);
    }
}
