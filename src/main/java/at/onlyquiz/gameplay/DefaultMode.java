package at.onlyquiz.gameplay;

import at.debugtools.DebugTools;
import at.onlyquiz.model.joker.AudienceJoker;
import at.onlyquiz.model.joker.ChatJoker;
import at.onlyquiz.model.joker.FiftyFiftyJoker;
import at.onlyquiz.model.question.Answer;
import at.onlyquiz.model.question.Difficulty;
import at.onlyquiz.model.question.GameQuestion;
import at.onlyquiz.util.QuestionDictionary;

import java.util.ArrayList;
import java.util.List;

public class DefaultMode extends GameMode {
    private FiftyFiftyJoker fiftyFiftyJoker;
    private ChatJoker chatJoker;
    private AudienceJoker audienceJoker;

    public DefaultMode() {
        scoreVisible = false;
        timerVisible = false;
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("A", true));
        answers.add(new Answer("B", false));
        answers.add(new Answer("C", false));
        answers.add(new Answer("D", false));

//        try {
        System.out.println(DebugTools.get(new Throwable()) + " path: " + QuestionDictionary.get_pathOfQuestionnaireFile_byID(0));
        List<GameQuestion> test = QuestionDictionary.get_Questions(Difficulty.EASY, 5);
        System.out.println(test);
//            HashMap<Difficulty, HashMap<Integer, List<Integer>>> test = CSV_Reader.get_lineNumbers_all_by_timesSelected(QuestionDictionary.get_QuestionnaireFile(0));
//            System.out.println();
//        } catch (IOException | CsvException e) {
//            throw new RuntimeException(e);
//        }

        currentQuestion = new GameQuestion("A, B, C oder D?", answers, Difficulty.EASY);
        currentQuestion.shuffleAnswers();
    }
}
