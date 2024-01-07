package at.onlyquiz.controller;

import at.onlyquiz.controller.factories.ControllerFactory;
import at.onlyquiz.controller.factories.Controllers;
import at.onlyquiz.gameplay.GameMode;
import at.onlyquiz.model.question.Answer;
import at.onlyquiz.util.GeneralSettings;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;

public class GameSessionController {


    private GameMode currentGameMode;
    private Answer selectedAnswer;
    private Button selectedAnswerButton;
    @FXML
    private Label totalScoreLabel, achievableScoreLabel, timeLabel, questionCounterLabel, questionLabel;

    @FXML
    private GridPane baseContainer;

    @FXML
    private TextField questionTextField, answerATextField, answerBTextField, answerCTextField, answerDTextField;

    @FXML
    private Button fiftyFiftyJokerButton, audienceJokerButton, chatJokerButton;

    @FXML
    private Button answerAButton, answerBButton, answerCButton, answerDButton;
    @FXML
    private Button commitButton, nextButton, endButton;
    @FXML
    public BarChart<String, Integer> votingResultsChart;
    private Timeline timer;
    private int secondsRemaining;

    private boolean nextState;
    private ScaleTransition blinkTransition;

    public GameSessionController() {
    }

    private void initialize() {
        if (GeneralSettings.isColorBlind()){
            String colorBlindPath = String.valueOf(getClass().getResource("/at/onlyquiz/styles/colorBlind/colorBlindMode.css"));
            baseContainer.getStylesheets().removeAll();
            baseContainer.getStylesheets().add(colorBlindPath);
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Answer A", 20));
        series.getData().add(new XYChart.Data<>("Answer B", 30));
        series.getData().add(new XYChart.Data<>("Answer C", 40));
        series.getData().add(new XYChart.Data<>("Answer D", 10));

        votingResultsChart.getData().add(series);

        setJokersAvailability();
        setScoreLabelsVisible();
        setTimeVisible(currentGameMode.isTimerVisible());

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
        nextButton.setVisible(false);
        endButton.setVisible(false);

        freshUpQuestionLabels();
    }

    public void freshUpQuestionLabels() {
        questionCounterLabel.setText("Question number: " + currentGameMode.getQuestionCounter());
        questionLabel.setText(currentGameMode.getCurrentQuestion().getQuestion());
        setUpAnswerButtonText(answerAButton, 0);
        setUpAnswerButtonText(answerBButton, 1);
        setUpAnswerButtonText(answerCButton, 2);
        setUpAnswerButtonText(answerDButton, 3);
    }

    private void setUpAnswerButtonText(Button button, int index) {
        button.getStyleClass().removeAll("answer-button-wrong");
        button.getStyleClass().removeAll("answer-button-right");
        button.getStyleClass().add("answer-button");
        button.setText(currentGameMode.getCurrentQuestion().getSpecificAnswer(index).getAnswer());
        button.setVisible(currentGameMode.getCurrentQuestion().getSpecificAnswer(index).isVisible());
    }


    // Setup for editing questions

    private void startEditView() {
        setUpEditMode();
    }

    public void setUpEditMode() {
        commitButton.setVisible(false);
        commitButton.disableProperty().set(true);
        nextButton.setVisible(false);

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
        if (!currentGameMode.getCurrentQuestion().getSpecificAnswer(index).getAnswer().isEmpty()) {
            textField.setText(currentGameMode.getCurrentQuestion().getSpecificAnswer(index).getAnswer());
        }
    }


    //Basic functions for UI

    public void useFiftyFiftyJoker() {
        if (!currentGameMode.getFiftyFiftyJokers().isEmpty()) {
            currentGameMode.getFiftyFiftyJokers().pop().use(currentGameMode.getCurrentQuestion());
            freshUpQuestionLabels();
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
        if (!nextState) {
            if (selectedAnswerButton != null) {
                selectedAnswerButton.getStyleClass().removeAll("answer-button-selected");
                selectedAnswerButton.getStyleClass().add("answer-button");
                stopBlinkingSelectedAnswerButton();
            }

            selectedAnswer = currentGameMode.getCurrentQuestion().getSpecificAnswer(index);
            selectedAnswerButton = answerButton;
            //selectedAnswerButton.getStyleClass().add("answer-button-selected");
            blinkSelectedAnswerButton(selectedAnswerButton);
            commitButton.disableProperty().set(false);
        }
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
                currentGameMode.confirmAnswer(selectedAnswer.isCorrect());
                commitButton.setVisible(false);

                if (currentGameMode.isFinished()) {
                    endButton.setVisible(true);
                } else {
                    nextButton.setVisible(true);
                }

                nextState = true;
            }
        }
        // When Question is editable, the input text will be saved
        else {

        }
    }

    public void pressNextButton(){
        commitButton.setVisible(true);
        nextButton.setVisible(false);
        stopBlinkingSelectedAnswerButton();
        freshUpQuestionLabels();
        nextState = false;
    }

    public void pressEndButton(){

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

    private void blinkSelectedAnswerButton(Button button) {
        blinkTransition = new ScaleTransition(Duration.seconds(0.5), button);
        blinkTransition.setFromX(1);
        blinkTransition.setToX(1.2);
        blinkTransition.setFromY(1);
        blinkTransition.setToY(1.2);
        blinkTransition.setCycleCount(ScaleTransition.INDEFINITE);
        blinkTransition.setAutoReverse(true);

        blinkTransition.playFromStart();
    }

    private void stopBlinkingSelectedAnswerButton(){
        if (blinkTransition != null){
            blinkTransition.stop();
            selectedAnswerButton.setScaleX(1.0);
            selectedAnswerButton.setScaleY(1.0);
        }
    }

    // functions for timer
    public void setTimeVisible(boolean hasTimer) {
        timeLabel.visibleProperty().set(hasTimer);
        startTimer();

    }

    private void startTimer(){
        secondsRemaining = currentGameMode.getTimeDurationInSeconds();

        timer = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateTimerLabel();
                secondsRemaining--;

                if (secondsRemaining < 0) {
                    timer.stop();
                    handleTimerEnd();
                }
            }
        }));

        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void updateTimerLabel() {
        int minutes = secondsRemaining / 60;
        int seconds = secondsRemaining % 60;
        timeLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
    }

    private void handleTimerEnd() {
        timeLabel.setText("time is over!");
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


}
