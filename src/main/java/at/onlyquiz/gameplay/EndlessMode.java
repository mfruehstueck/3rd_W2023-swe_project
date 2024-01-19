package at.onlyquiz.gameplay;

import at.onlyquiz.model.joker.AudienceJoker;
import at.onlyquiz.model.joker.ChatJoker;
import at.onlyquiz.model.joker.FiftyFiftyJoker;
import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.util.QuestionDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EndlessMode extends GameMode {
    public EndlessMode() {
        editAble = false;
        scoreVisible = false;
        timerVisible = false;
        answerSecondsRemaining = 0;
        totalScore = 0;
        questionCounter = 0;
        jokersAvailable = true;

        fiftyFiftyJokers.add(new FiftyFiftyJoker());
        audienceJokers.add(new AudienceJoker(false));
        chatJokers.add(new ChatJoker(false));


        //TODO REMOVE the following list - do it with selected from GameSession Settings (manuel did this).
        List<String> testQuestionnaire = new ArrayList<>() {{
            add("test_testQuestions");
        }};
        getNewQuestions();
        Collections.shuffle(setOfQuestions);
        currentQuestion = popQuestionOutOfSet();
        currentQuestion.shuffleAnswers();
    }

    @Override
    public void confirmAnswer(boolean isCorrect) {
        if (isCorrect) {
            jokerUsed = false;
            getNewQuestions();
            currentQuestion = popQuestionOutOfSet();
            currentQuestion.shuffleAnswers();
            questionCounter += 1;
        } else {
            finished = true;
        }
    }

    @Override
    public int calculateScore() {
        return 0;
    }

    private void getNewQuestions() {
        if (setOfQuestions.size() < 2) {

            //TODO REMOVE the following list - do it with selected from GameSession Settings (manuel did this).
            List<String> testQuestionnaire = new ArrayList<>() {{
                add("test_testQuestions");
            }};

            setOfQuestions.addAll(QuestionDictionary.get_randomQuestions(testQuestionnaire, Difficulty.EASY, 5));
            setOfQuestions.addAll(QuestionDictionary.get_randomQuestions(testQuestionnaire, Difficulty.MEDIUM, 5));
            setOfQuestions.addAll(QuestionDictionary.get_randomQuestions(testQuestionnaire, Difficulty.HARD, 5));
        }
    }
}
