package at.onlyquiz.controller;

import at.onlyquiz.controller.factories.View;
import at.onlyquiz.gameplay.GameMode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultScreenController extends BaseController {

    @FXML
    public Label playerNameLabel, achievedScoreLabel, statusMassage;
    public GridPane ui_container;
    private GameMode currentGameMode;

    public void initialize() {
        if (currentGameMode.getPlayername() != null){
            playerNameLabel.setText("Player: " + currentGameMode.getPlayername());
        }
        if (currentGameMode.getAchievedScore() != null){
            achievedScoreLabel.setText("Achieved Score: " + currentGameMode.getAchievedScore());
        }

        if (currentGameMode.hasWon()){
            statusMassage.setText("Congratulations, you won!");
        } else {
            statusMassage.setText("Sorry, you lost..");
        }
    }

    public void pressMenuButton() {
        set_view(get_stage(ui_container), View.MENU_VIEW);
    }

    public GameMode getCurrentGameMode() {
        return currentGameMode;
    }

    public void setCurrentGameMode(GameMode currentGameMode) {
        this.currentGameMode = currentGameMode;
        initialize();
    }
}
