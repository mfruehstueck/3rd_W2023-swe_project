package at.onlyquiz.controller;

import at.debugtools.DebugTools;
import at.onlyquiz.controller.factories.View;
import at.onlyquiz.gameplay.GameMode;
import at.onlyquiz.model.joker.ChatJoker;
import at.onlyquiz.model.question.Answer;
import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.model.question.GameQuestion;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class GameSessionController extends BaseController{


    public CheckBox easyCheckBox, mediumCheckBox, hardCheckBox;
    @FXML
    public TextFlow chatJokerTextFlow;
    @FXML
    public Button chatInput1, chatInput2, chatInput3, chatInput4;
    public Button deleteButton, nextQuestionButton, prevQuestionButton;
    private GameMode currentGameMode;
    private Answer selectedAnswer;
    private Button selectedAnswerButton;
    @FXML
    private Label totalScoreLabel, achievableScoreLabel, timeLabel, questionCounterLabel, questionLabel;

    @FXML
    private GridPane ui_container;

    @FXML
    private TextField answerATextField, answerBTextField, answerCTextField, answerDTextField;
    @FXML
    private TextField questionTextField;

    @FXML
    private Button fiftyFiftyJokerButton, audienceJokerButton, chatJokerButton;

    @FXML
    private Button answerAButton, answerBButton, answerCButton, answerDButton;
    @FXML
    private Button commitButton, nextButton, endButton;
    @FXML
    public BarChart<String, Double> votingResultsChart;
    private Timeline timer, achievableScore;
    private boolean nextState;
    private ScaleTransition blinkTransition;

    private ChatJoker activeChatJoker;

    public GameSessionController() {
    }

    private void initialize() {
        if (GeneralSettings.isColorBlind()){
            String colorBlindPath = String.valueOf(getClass().getResource("/at/onlyquiz/styles/colorBlind/colorBlindMode.css"));
            ui_container.getStylesheets().removeAll();
            ui_container.getStylesheets().add(colorBlindPath);
        }

        setJokersAvailability(currentGameMode.areJokersAvailable());
        setScoreLabelsVisible(currentGameMode.isScoreVisible());
        setTimeVisible(currentGameMode.isTimerVisible());

        if (currentGameMode.isEditAble()) {
            startEditView();
        } else {
            startAnswerView();
        }

    }


    // Setup for answering questions

    private void startAnswerView() {
        answerATextField.setVisible(false);
        answerBTextField.setVisible(false);
        answerCTextField.setVisible(false);
        answerDTextField.setVisible(false);
        questionTextField.setVisible(false);
        commitButton.disableProperty().set(true);
        nextButton.setVisible(false);
        endButton.setVisible(false);
        easyCheckBox.setVisible(false);
        mediumCheckBox.setVisible(false);
        hardCheckBox.setVisible(false);
        deleteButton.setVisible(false);
        prevQuestionButton.setVisible(false);
        nextQuestionButton.setVisible(false);
        freshUpQuestionLabels();
    }

    public void freshUpQuestionLabels() {
        votingResultsChart.setVisible(false);
        chatJokerTextFlow.setVisible(false);
        chatInput1.setVisible(false); chatInput2.setVisible(false); chatInput3.setVisible(false); chatInput4.setVisible(false);
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
        commitButton.setVisible(true);
        commitButton.disableProperty().set(false);
        endButton.setVisible(false);
        nextButton.setVisible(false);
        votingResultsChart.setVisible(false);
        chatJokerTextFlow.setVisible(false);
        chatInput1.setVisible(false); chatInput2.setVisible(false); chatInput3.setVisible(false); chatInput4.setVisible(false);
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

        switch (currentGameMode.getCurrentQuestion().getDifficulty()) {
            case EASY -> selectEasyDifficulty();
            case MEDIUM -> selectMediumDifficulty();
            case HARD -> selectHardDifficulty();
        }

        questionCounterLabel.setText("Question number: " + (currentGameMode.getIndexInQuestionnaire()+1) + " / " + currentGameMode.getSetOfQuestions().size());
    }

    private void setUpTextField(TextField textField, int index) {
        if (!currentGameMode.getCurrentQuestion().getSpecificAnswer(index).getAnswer().isEmpty()) {
            textField.setText(currentGameMode.getCurrentQuestion().getSpecificAnswer(index).getAnswer());
        }
    }


    // UI refresh functions
    private void refreshAnswerView(){
        totalScoreLabel.setText("Total Score: " + currentGameMode.getTotalScore());
        freshUpQuestionLabels();
    }

    private void refreshJokerButtons(){
        if (currentGameMode.getFiftyFiftyJokers().isEmpty()){
            fiftyFiftyJokerButton.disableProperty().set(true);
        }
        if (currentGameMode.getAudienceJokers().isEmpty()){
            audienceJokerButton.disableProperty().set(true);
        }
        if (currentGameMode.getChatJokers().isEmpty()){
            chatJokerButton.disableProperty().set(true);
        }
    }

    //Basic functions for UI

    public void useFiftyFiftyJoker() {
        if (!currentGameMode.getFiftyFiftyJokers().isEmpty()) {
            currentGameMode.getFiftyFiftyJokers().pop().use(currentGameMode.getCurrentQuestion());
            currentGameMode.setJokerUsed(true);

            refreshAnswerView();
            refreshJokerButtons();
        }
    }


    public void useAudienceJoker() {
        if (!currentGameMode.getAudienceJokers().isEmpty()) {
            currentGameMode.getAudienceJokers().pop().use(currentGameMode.getCurrentQuestion());
            currentGameMode.setJokerUsed(true);

            XYChart.Series<String, Double> series = new XYChart.Series<>();
            for (Answer answer : currentGameMode.getCurrentQuestion().getAnswers()){
                if (answer.isVisible()){
                    series.getData().add(new XYChart.Data<>(answer.getAnswer(), answer.getVotingValue()));
                }
            }
            votingResultsChart.getData().add(series);
            votingResultsChart.setVisible(true);

            refreshJokerButtons();
        }
    }

    public void useChatJoker() {
        if (!currentGameMode.getChatJokers().isEmpty()) {
            activeChatJoker = (ChatJoker) currentGameMode.getChatJokers().pop();

            chatJokerTextFlow.setVisible(true);
            chatInput2.setVisible(true); chatInput3.setVisible(true);

            activeChatJoker.use(currentGameMode.getCurrentQuestion());

            chatJokerTextFlow.getChildren().add(new Text("\n \n"));
            chatJokerTextFlow.getChildren().add(new Text(activeChatJoker.getNextResponse()));

            refreshChatInputs();

            chatJokerTextFlow.setVisible(true);

            refreshJokerButtons();
        }
    }

    public void pressChatInput1() {
        chatJokerTextFlow.getChildren().add(new Text(activeChatJoker.setSelectedInput(chatInput1.getText())));
        refreshChatInputs();

    }
    public void pressChatInput2() {
        chatJokerTextFlow.getChildren().add(new Text(activeChatJoker.setSelectedInput(chatInput2.getText())));
        refreshChatInputs();

    }
    public void pressChatInput3() {
        chatJokerTextFlow.getChildren().add(new Text(activeChatJoker.setSelectedInput(chatInput3.getText())));
        refreshChatInputs();

    }
    public void pressChatInput4() {
        chatJokerTextFlow.getChildren().add(new Text(activeChatJoker.setSelectedInput(chatInput4.getText())));
        refreshChatInputs();
    }

    private void refreshChatInputs(){
        if (!activeChatJoker.getInputText1().isEmpty()){
            chatInput1.setText(activeChatJoker.getInputText1());
            chatInput1.setVisible(true);
        }
        else {
            chatInput1.setText("");
            chatInput1.setVisible(false);
        }

        if (!activeChatJoker.getInputText2().isEmpty()){
            chatInput2.setText(activeChatJoker.getInputText2());
            chatInput2.setVisible(true);
        }
        else {
            chatInput2.setText("");
            chatInput2.setVisible(false);
        }

        if (!activeChatJoker.getInputText3().isEmpty()){
            chatInput3.setText(activeChatJoker.getInputText3());
            chatInput3.setVisible(true);
        }
        else {
            chatInput3.setText("");
            chatInput3.setVisible(false);
        }

        if (!activeChatJoker.getInputText4().isEmpty()){
            chatInput4.setText(activeChatJoker.getInputText4());
            chatInput4.setVisible(true);
        }
        else {
            chatInput4.setText("");
            chatInput4.setVisible(false);
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
            blinkSelectedAnswerButton(selectedAnswerButton);
            commitButton.disableProperty().set(false);
        }
    }

    public void pressCommitButton() {
        // When Question is not editable the answer will be committed
        if (!currentGameMode.isEditAble()) {
            if (currentGameMode.isTimerVisible())timer.stop();
            if (currentGameMode.isScoreVisible())achievableScore.stop();
            if (selectedAnswer != null && selectedAnswerButton != null) {
                if (selectedAnswer.isCorrect()) {
                    selectedAnswerButton.getStyleClass().removeAll("answer-button-selected");
                    selectedAnswerButton.getStyleClass().add("answer-button-right");
                } else {
                    selectedAnswerButton.getStyleClass().removeAll("answer-button-selected");
                    selectedAnswerButton.getStyleClass().add("answer-button-wrong");
                }
                currentGameMode.confirmAnswer(selectedAnswer.isCorrect());
                System.out.println(DebugTools.debugLine(new Throwable()));;
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
            GameQuestion question = currentGameMode.getCurrentQuestion();
            question.setQuestion(questionTextField.getText());
            List<Answer> answers = currentGameMode.getCurrentQuestion().getAnswers();
            answers.get(0).setAnswer(answerATextField.getText());
            answers.get(1).setAnswer(answerBTextField.getText());
            answers.get(2).setAnswer(answerCTextField.getText());
            answers.get(3).setAnswer(answerDTextField.getText());

            currentGameMode.confirmAnswer(true);
            freshUpTextFields();
        }
    }

    public void pressNextButton(){
        commitButton.setVisible(true);
        nextButton.setVisible(false);
        stopBlinkingSelectedAnswerButton();
        refreshAnswerView();

        if (currentGameMode.isTimerVisible()){
            currentGameMode.resetTimer();
            startTimer();
        }

        if (currentGameMode.isScoreVisible()){
            startAchievableScoreCountDown();
        }
        nextState = false;
    }

    public void pressEndButton(){

    }

    public void pressDeleteButton() {
        questionTextField.setText("");
        answerATextField.setText("");
        answerBTextField.setText("");
        answerCTextField.setText("");
        answerDTextField.setText("");
    }

    public void pressPrevQuestionButton() {
        boolean firstQuestion = currentGameMode.previousIndex();
        freshUpTextFields();
    }

    public void pressNextQuestionButton() {
        boolean reachedEnd = currentGameMode.nextIndex();
        if (reachedEnd && currentGameMode.isEditAble()) {
            currentGameMode.setCurrentQuestion(new GameQuestion());
        }
        freshUpTextFields();
    }

    public void pressStopButton() {
        set_view(get_stage(ui_container), View.MENU_VIEW);
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
        if (hasTimer) {
            currentGameMode.resetTimer();
            startTimer();
        }
    }

    private void startTimer(){
        timer = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateTimerLabel();
                if (currentGameMode.getReadingSecondsRemaining() != 0){
                    currentGameMode.setReadingSecondsRemaining(currentGameMode.getReadingSecondsRemaining() - 1);
                } else {
                    currentGameMode.setAnswerSecondsRemaining(currentGameMode.getAnswerSecondsRemaining() - 1);
                }

                if (currentGameMode.getAnswerSecondsRemaining() <= 0) {
                    timer.stop();
                    handleTimerEnd();
                }
            }
        }));

        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void updateTimerLabel() {
        int minutes = 0;
        int seconds = 0;
        String timeLabelText;

        if (currentGameMode.getReadingSecondsRemaining() != 0){
            minutes = currentGameMode.getReadingSecondsRemaining() / 60;
            seconds = currentGameMode.getReadingSecondsRemaining() % 60;
            timeLabelText = "Time to read";
        } else {
            minutes = currentGameMode.getAnswerSecondsRemaining() / 60;
            seconds = currentGameMode.getAnswerSecondsRemaining() % 60;
            timeLabelText = "Time to answer";
        }

        timeLabel.setText(String.format(timeLabelText + ": %02d:%02d", minutes, seconds));
    }

    private void handleTimerEnd() {
        timeLabel.setText("Time over. No additional points.");
    }

    //achievable score
    private void startAchievableScoreCountDown(){
        achievableScore = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                achievableScoreLabel.setText("achievable Score: " + currentGameMode.calculateScore());
            }
        }));

        achievableScore.setCycleCount(Timeline.INDEFINITE);
        achievableScore.play();
    }

    // Setter

    public void setCurrentGameMode(GameMode currentGameMode) {
        this.currentGameMode = currentGameMode;
        initialize();
    }

    public void setJokersAvailability(boolean hasJokers) {
        audienceJokerButton.setVisible(hasJokers);
        chatJokerButton.setVisible(hasJokers);
        fiftyFiftyJokerButton.setVisible(hasJokers);
        if (hasJokers){
            refreshJokerButtons();
        }
    }

    public void setScoreLabelsVisible(boolean hasScoreLabels) {
            totalScoreLabel.visibleProperty().set(hasScoreLabels);
            achievableScoreLabel.visibleProperty().set(hasScoreLabels);
            if (hasScoreLabels) {
                startAchievableScoreCountDown();
            }
    }

    public void selectEasyDifficulty(){
        easyCheckBox.setSelected(true);
        mediumCheckBox.setSelected(false);
        hardCheckBox.setSelected(false);
        currentGameMode.getCurrentQuestion().setDifficulty(Difficulty.EASY);
    }

    public void selectMediumDifficulty(){
        easyCheckBox.setSelected(false);
        mediumCheckBox.setSelected(true);
        hardCheckBox.setSelected(false);
        currentGameMode.getCurrentQuestion().setDifficulty(Difficulty.MEDIUM);
    }

    public void selectHardDifficulty(){
        easyCheckBox.setSelected(false);
        mediumCheckBox.setSelected(false);
        hardCheckBox.setSelected(true);
        currentGameMode.getCurrentQuestion().setDifficulty(Difficulty.HARD);
    }
}
