package at.onlyquiz.controller;

import at.onlyquiz.gameplay.GameMode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class GameSessionController {

    private final GameMode currentGameMode;
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


    public GameSessionController(GameMode currentGameMode) {
        this.currentGameMode = currentGameMode;
    }

    @FXML
    private void initialize(){
        System.out.println("init function from GameSession Controller");
        System.out.println(currentGameMode);

        setScoreLabelsVisible(currentGameMode.isScoreVisible());
        setTimeVisible(currentGameMode.isTimerVisible());
        setJokersAvailability(currentGameMode.areJokersAvailable());
        //setUpQuestionLabels();
    }

    public void setUpQuestionLabels(){
        questionTextField.setText(currentGameMode.getCurrentQuestion().getQuestion());
        answerAButton.setText(currentGameMode.getCurrentQuestion().getAnswers().get(0).getValue());
        answerBButton.setText(currentGameMode.getCurrentQuestion().getAnswers().get(1).getValue());
        answerCButton.setText(currentGameMode.getCurrentQuestion().getAnswers().get(2).getValue());
        answerDButton.setText(currentGameMode.getCurrentQuestion().getAnswers().get(3).getValue());
    }
    public void setJokersAvailability(boolean availability){
        audienceJokerButton.setVisible(availability);
        chatJokerButton.setVisible(availability);
        fiftyFiftyJokerButton.setVisible(availability);
    }

    public void setScoreLabelsVisible(boolean visible){
        totalScoreLabel.visibleProperty().set(visible);
        achievableScoreLabel.visibleProperty().set(visible);
    }

    public void setTimeVisible(boolean visible){
        timeLabel.visibleProperty().set(visible);
    }

    public void useFiftyFiftyJoker(ActionEvent actionEvent){
        currentGameMode.getFiftyFiftyJokers().pop().use();
    }

    public void useAudienceJoker(ActionEvent actionEvent){
        currentGameMode.getAudienceJokers().pop().use();
    }

    public void useChatJoker(ActionEvent actionEvent){
        currentGameMode.getChatJokers().pop().use();
    }
}
