package at.onlyquiz.controller;

import at.onlyquiz.controller.factories.ControllerFactory;
import at.onlyquiz.controller.factories.Controllers;
import at.onlyquiz.gameplay.GameMode;
import at.onlyquiz.model.question.Answer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class GameSessionController {

    private GameMode currentGameMode;
    @FXML
    private Label totalScoreLabel, achievableScoreLabel, timeLabel, questionCounterLabel;

    @FXML
    private HBox questionBox;

    @FXML
    private TextField questionTextField;

    @FXML
    private Button fiftyFiftyJokerButton, audienceJokerButton, chatJokerButton;

    @FXML
    private Button answerAButton, answerBButton, answerCButton, answerDButton;

    public GameSessionController() {
    }

    private void initialize() {
        System.out.println("init function from GameSession Controller");
        System.out.println(currentGameMode);

        setScoreLabelsVisible(currentGameMode.isScoreVisible());
        setTimeVisible(currentGameMode.isTimerVisible());
        setJokersAvailability(currentGameMode.areJokersAvailable());
        setUpQuestionLabels();
    }

    public void setTimeVisible(boolean visible) {
        timeLabel.visibleProperty().set(visible);
    }

    public void useFiftyFiftyJoker(ActionEvent actionEvent) {
        if (!currentGameMode.getFiftyFiftyJokers().isEmpty()) {
            currentGameMode.getFiftyFiftyJokers().pop().use(currentGameMode.getCurrentQuestion());
        }
    }

    public void useAudienceJoker(ActionEvent actionEvent) {
        if (!currentGameMode.getAudienceJokers().isEmpty()) {
            currentGameMode.getAudienceJokers().pop().use(currentGameMode.getCurrentQuestion());
        }
    }

    public void useChatJoker(ActionEvent actionEvent) {
        if (!currentGameMode.getChatJokers().isEmpty()) {
            currentGameMode.getChatJokers().pop().use(currentGameMode.getCurrentQuestion());
        }
    }


    public void commitAnswer(Answer answer, Button answerButton) {
        if (answer.isCorrect()) {
            answerButton.getStyleClass().add("answer-button-right");
        } else {
            answerButton.getStyleClass().add("answer-button-wrong");
        }
    }

    public void selectAnswerA(ActionEvent actionEvent) {
        commitAnswer(currentGameMode.getCurrentQuestion().getSpecificAnswer(0), answerAButton);
    }

    public void selectAnswerB(ActionEvent actionEvent) {
        commitAnswer(currentGameMode.getCurrentQuestion().getSpecificAnswer(1), answerBButton);
    }

    public void selectAnswerC(ActionEvent actionEvent) {
        commitAnswer(currentGameMode.getCurrentQuestion().getSpecificAnswer(2), answerCButton);
    }

    public void selectAnswerD(ActionEvent actionEvent) {
        commitAnswer(currentGameMode.getCurrentQuestion().getSpecificAnswer(3), answerDButton);
    }

    public void setUpQuestionLabels() {
        questionTextField.setText(currentGameMode.getCurrentQuestion().getQuestion());
        answerAButton.setText(currentGameMode.getCurrentQuestion().getSpecificAnswer(0).getValue());
        answerBButton.setText(currentGameMode.getCurrentQuestion().getSpecificAnswer(1).getValue());
        answerCButton.setText(currentGameMode.getCurrentQuestion().getSpecificAnswer(2).getValue());
        answerDButton.setText(currentGameMode.getCurrentQuestion().getSpecificAnswer(3).getValue());
    }

    public void setJokersAvailability(boolean availability) {
        audienceJokerButton.setVisible(availability);
        chatJokerButton.setVisible(availability);
        fiftyFiftyJokerButton.setVisible(availability);
    }

    public void setScoreLabelsVisible(boolean visible) {
        totalScoreLabel.visibleProperty().set(visible);
        achievableScoreLabel.visibleProperty().set(visible);
    }

    public void setCurrentGameMode(GameMode currentGameMode) {
        this.currentGameMode = currentGameMode;
        initialize();
    }

    public void pressStopButton() {
        try {
            Stage currentStage = (Stage) answerAButton.getScene().getWindow();
            currentStage.setScene(ControllerFactory.getScene(Controllers.MENU_VIEW));
            currentStage.sizeToScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
