package at.onlyquiz.model.question;

import at.onlyquiz.util.csvParser.CSV_Column;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameQuestion {
    private final Integer lineIdx;
    private Integer timesSelected;
    private String question;
    private List<Answer> answers;
    private Difficulty difficulty;

    public GameQuestion() {
        this.lineIdx = -1;
        this.timesSelected = 0;
        question = "New Question - write me!";
        answers = new ArrayList<>();
        answers.add(new Answer("right", true));
        for (int i = 0; i < 3; i++) {
            answers.add(new Answer("wrong", false));
        }
        difficulty = Difficulty.EASY;
    }

    public GameQuestion(String question, List<Answer> answers, Difficulty difficulty) {
        this.lineIdx = -1;
        this.timesSelected = 0;
        this.question = question;
        this.answers = answers;
        this.difficulty = difficulty;
    }

    public GameQuestion(Integer lineIdx, String[] csvLine) {
        this.lineIdx = lineIdx;
        this.timesSelected = Integer.parseInt(csvLine[CSV_Column.TIMES_SELECTED.ordinal()]);
        this.question = csvLine[CSV_Column.QUESTION.ordinal()];
        this.answers = new ArrayList<>() {{
            add(new Answer(csvLine[CSV_Column.CORRECT_ANSWER.ordinal()], true));
            add(new Answer(csvLine[CSV_Column.WRONG_ANSWER_1.ordinal()], false));
            add(new Answer(csvLine[CSV_Column.WRONG_ANSWER_2.ordinal()], false));
            add(new Answer(csvLine[CSV_Column.WRONG_ANSWER_3.ordinal()], false));
        }};
        this.difficulty = Difficulty.valueOf(csvLine[CSV_Column.DIFFICULTY.ordinal()]);
    }

    @SuppressWarnings("ALL")
    public String[] getMemberNames() {
        Field[] members = this.getClass().getDeclaredFields();
        List<String> out = new ArrayList<>();

        for (Field f : members) {
            out.add(f.getName());
        }

        return out.toArray(String[]::new);
    }

    public void shuffleAnswers() {
        Collections.shuffle(answers);
    }


    //NSEC: getter
    public Integer getLineIdx() {
        return lineIdx;
    }

    public Integer getTimesSelected() {
        return timesSelected;
    }

    public String getQuestion() {
        return question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public Answer getSpecificAnswer(int index) {
        return answers.get(index);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String[] getCsvLine() {
        return new String[]{Integer.toString(timesSelected), question, difficulty.toString(), answers.get(0).getAnswer(), answers.get(1).getAnswer(), answers.get(2).getAnswer(), answers.get(3).getAnswer()};
    }

    //NSEC: setter
    public void setTimesSelected(Integer timesSelected) {
        this.timesSelected = timesSelected;
    }

    public void incrementTimesSelected() {
        this.timesSelected++;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
