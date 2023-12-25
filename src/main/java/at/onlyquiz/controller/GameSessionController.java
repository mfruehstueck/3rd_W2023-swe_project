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
    private Answer selectedAnswer;
    private Button selectedAnswerButton;
    @FXML
    private Label totalScoreLabel, achievableScoreLabel, timeLabel, questionCounterLabel, questionLabel;

    @FXML
    private HBox questionBox;

    @FXML
    private TextField questionTextField, answerATextField, answerBTextField, answerCTextField, answerDTextField;

    @FXML
    private Button fiftyFiftyJokerButton, audienceJokerButton, chatJokerButton;

    @FXML
    private Button answerAButton, answerBButton, answerCButton, answerDButton, commitButton;

    public GameSessionController() {
    }

    private void initialize() {
        setJokersAvailability();
        setScoreLabelsVisible();
        setTimeVisible();

        if (currentGameMode.isEditAble()) {
            startEditView();
        } else {
            startAnswerView();
        }

    }


    // Setup for answering questions

    private void startAnswerView() {
        setUpQuestionLabels();
    }

    private void setUpQuestionLabels() {
        answerATextField.setVisible(false);
        answerBTextField.setVisible(false);
        answerCTextField.setVisible(false);
        answerDTextField.setVisible(false);
        questionTextField.setVisible(false);
        commitButton.disableProperty().set(true);

        freshUpQuestionLabels();
    }

    public void freshUpQuestionLabels() {
        questionLabel.setText(currentGameMode.getCurrentQuestion().getQuestion());
        setUpAnswerButtonText(answerAButton, 0);
        setUpAnswerButtonText(answerBButton, 1);
        setUpAnswerButtonText(answerCButton, 2);
        setUpAnswerButtonText(answerDButton, 3);
    }

    private void setUpAnswerButtonText(Button button, int index) {
        button.getStyleClass().removeAll("answer-button-wrong");
        button.getStyleClass().removeAll("answer-button.right");
        button.getStyleClass().add("answer-button");
        button.setText(currentGameMode.getCurrentQuestion().getSpecificAnswer(index).getValue());
    }


    // Setup for editing questions

    private void startEditView() {
        setUpEditMode();
    }

    public void setUpEditMode() {
        commitButton.setVisible(false);
        commitButton.disableProperty().set(true);

        answerAButton.setVisible(false);
        answerBButton.setVisible(false);
        answerCButton.setVisible(false);
        answerDButton.setVisible(false);
        questionLabel.setVisible(false);

        freshUpTextFields();
    }

    private void freshUpTextFields() {
        if (!currentGameMode.getCurrentQuestion().getQuestion().isEmpty()) {
            questionTextField.setText(currentGameMode.getCurrentQuestion().getQuestion());
        }
        setUpTextField(answerATextField, 0);
        setUpTextField(answerBTextField, 1);
        setUpTextField(answerCTextField, 2);
        setUpTextField(answerDTextField, 3);
    }

    private void setUpTextField(TextField textField, int index) {
        if (!currentGameMode.getCurrentQuestion().getSpecificAnswer(index).getValue().isEmpty()) {
            textField.setText(currentGameMode.getCurrentQuestion().getSpecificAnswer(index).getValue());
        }
    }


    //Basic functions for UI

    public void useFiftyFiftyJoker() {
        if (!currentGameMode.getFiftyFiftyJokers().isEmpty()) {
            currentGameMode.getFiftyFiftyJokers().pop().use(currentGameMode.getCurrentQuestion());
        }
    }

    public void useAudienceJoker() {
        if (!currentGameMode.getAudienceJokers().isEmpty()) {
            currentGameMode.getAudienceJokers().pop().use(currentGameMode.getCurrentQuestion());
        }
    }

    public void useChatJoker() {
        if (!currentGameMode.getChatJokers().isEmpty()) {
            currentGameMode.getChatJokers().pop().use(currentGameMode.getCurrentQuestion());
        }
    }

    public void selectAnswerA() {
        selectSpecificAnswer(0, answerAButton);
    }

    public void selectAnswerB() {
        selectSpecificAnswer(1, answerBButton);
    }

    public void selectAnswerC() {
        selectSpecificAnswer(2, answerCButton);
    }

    public void selectAnswerD() {
        selectSpecificAnswer(3, answerDButton);
    }

    private void selectSpecificAnswer(int index, Button answerButton) {
        if (selectedAnswerButton != null) {
            selectedAnswerButton.getStyleClass().removeAll("answer-button-selected");
            selectedAnswerButton.getStyleClass().add("answer-button");
        }

        selectedAnswer = currentGameMode.getCurrentQuestion().getSpecificAnswer(index);
        selectedAnswerButton = answerButton;
        selectedAnswerButton.getStyleClass().add("answer-button-selected");
        commitButton.disableProperty().set(false);
    }

    public void pressCommitButton() {
        // When Question is not editable the answer will be committed
        if (!currentGameMode.isEditAble()) {
            if (selectedAnswer != null && selectedAnswerButton != null) {
                if (selectedAnswer.isCorrect()) {
                    selectedAnswerButton.getStyleClass().removeAll("answer-button-selected");
                    selectedAnswerButton.getStyleClass().add("answer-button-right");
                } else {
                    selectedAnswerButton.getStyleClass().removeAll("answer-button-selected");
                    selectedAnswerButton.getStyleClass().add("answer-button-wrong");
                }
            }
        }
        // When Question is editable, the input text will be saved
        else {

        }
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


    // Setter

    public void setCurrentGameMode(GameMode currentGameMode) {
        this.currentGameMode = currentGameMode;
        initialize();
    }

    public void setJokersAvailability() {
        audienceJokerButton.setVisible(currentGameMode.areJokersAvailable());
        chatJokerButton.setVisible(currentGameMode.areJokersAvailable());
        fiftyFiftyJokerButton.setVisible(currentGameMode.areJokersAvailable());
    }

    public void setScoreLabelsVisible() {
        totalScoreLabel.visibleProperty().set(currentGameMode.isScoreVisible());
        achievableScoreLabel.visibleProperty().set(currentGameMode.isScoreVisible());
    }

    public void setTimeVisible() {
        timeLabel.visibleProperty().set(currentGameMode.isTimerVisible());
    }

}
