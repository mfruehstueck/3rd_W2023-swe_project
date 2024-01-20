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
    public EndlessMode(List<String> selectedQuestionnaires, String playername) {
        super(selectedQuestionnaires, playername);
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
            setOfQuestions.addAll(QuestionDictionary.get_randomQuestions(selectedQuestionnaires, Difficulty.EASY, 5));
            setOfQuestions.addAll(QuestionDictionary.get_randomQuestions(selectedQuestionnaires, Difficulty.MEDIUM, 5));
            setOfQuestions.addAll(QuestionDictionary.get_randomQuestions(selectedQuestionnaires, Difficulty.HARD, 5));
        }
    }
}
