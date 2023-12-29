package at.onlyquiz.gameplay;

import at.onlyquiz.model.question.Answer;
import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.model.question.GameQuestion;
import java.util.ArrayList;
import java.util.List;

public class DefaultMode extends GameMode {

    public DefaultMode() {
        editAble = false;
        scoreVisible = false;
        timerVisible = true;
        timeDurationInSeconds = 5;

        createTestQuestions();
        currentQuestion = popQuestionOutOfSet();
        currentQuestion.shuffleAnswers();
    }

    @Override
    public void confirmAnswer(boolean isCorrect) {
        if (isCorrect){
            //TODO calculate the score
            if (setOfQuestions.isEmpty()) {
                //TODO something when player finished
            } else {
                currentQuestion = popQuestionOutOfSet();
            }
        }
    }


    private void createTestQuestions(){
        List<Answer> answers1 = new ArrayList<>();
        answers1.add(new Answer("A", true));
        answers1.add(new Answer("B", false));
        answers1.add(new Answer("C", false));
        answers1.add(new Answer("D", false));

        List<Answer> answers2 = new ArrayList<>();
        answers2.add(new Answer("1912", true));
        answers2.add(new Answer("1910", false));
        answers2.add(new Answer("1913", false));
        answers2.add(new Answer("1911", false));

        List<Answer> answers3 = new ArrayList<>();
        answers3.add(new Answer("George Orwell", true));
        answers3.add(new Answer("Aldous Huxley", false));
        answers3.add(new Answer("Ray Bradbury", false));
        answers3.add(new Answer("Thomas More", false));

        setOfQuestions.add(new GameQuestion("A, B, C oder D?", answers1, Difficulty.EASY));
        setOfQuestions.add(new GameQuestion("In which year did the Titanic sink?", answers2, Difficulty.MEDIUM));
        setOfQuestions.add(new GameQuestion("Who is the author of '1984'?", answers3, Difficulty.HARD));
    }
}
