package at.onlyquiz.model.question;

import java.util.List;

public class GameQuestion {
    private String question;
    private List<Answer> answers;
    private Difficulty difficulty;

    public GameQuestion(String question, List<Answer> answers, Difficulty difficulty){
        this.question = question;
        this.answers = answers;
        this.difficulty = difficulty;
    }


    public Answer getSpecificAnswer(int index){
        return answers.get(index);
    }

    public String getQuestion(){
        return question;
    }

    public List<Answer> getAnswers(){
        return answers;
    }

    public Difficulty getDifficulty(){
        return difficulty;
    }
}
