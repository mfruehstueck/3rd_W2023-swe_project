package at.onlyquiz.gameplay;

import at.onlyquiz.model.joker.AudienceJoker;
import at.onlyquiz.model.joker.FiftyFiftyJoker;
import at.onlyquiz.model.question.Answer;
import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.model.question.GameQuestion;
import at.onlyquiz.util.QuestionDictionary;

import java.util.ArrayList;
import java.util.List;

public class DefaultMode extends GameMode {

    public DefaultMode() {
        editAble = false;
        scoreVisible = true;
        timerVisible = true;
        timeDurationInSeconds = 5;
        questionCounter = 0;
        jokersAvailable = true;

        //set up jokers;
        fiftyFiftyJokers.add(new FiftyFiftyJoker());
        audienceJokers.add(new AudienceJoker());

        List<String> testQuestionnaire = new ArrayList<>();
        testQuestionnaire.add("test_testQuestions");
        //setOfQuestions.addAll(QuestionDictionary.get_Questions(testQuestionnaire, Difficulty.EASY, 5));
        //setOfQuestions.addAll(QuestionDictionary.get_Questions(testQuestionnaire, Difficulty.MEDIUM, 5));
        setOfQuestions.addAll(QuestionDictionary.get_Questions(testQuestionnaire, Difficulty.HARD, 1));

        currentQuestion = popQuestionOutOfSet();
        currentQuestion.shuffleAnswers();
    }

    @Override
    public void confirmAnswer(boolean isCorrect) {
        if (isCorrect){
            //TODO calculate the score
            if (setOfQuestions.isEmpty()) {
                //TODO something when player finished
                finished = true;
            } else {
                currentQuestion = popQuestionOutOfSet();
                questionCounter += 1;
            }
        } else {
            finished = true;
        }
    }
}
