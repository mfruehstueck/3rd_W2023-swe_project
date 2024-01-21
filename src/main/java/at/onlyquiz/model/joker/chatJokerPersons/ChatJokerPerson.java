package at.onlyquiz.model.joker.chatJokerPersons;

import at.onlyquiz.model.question.Answer;
import at.onlyquiz.model.question.GameQuestion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class ChatJokerPerson {
  List<String> greetingLines = new ArrayList<>();
  List<String> greetingBackLines = new ArrayList<>();
  List<String> tellIsInSHowLines = new ArrayList<>();
  List<String> askQuestionsLines = new ArrayList<>();
  //List<String> solutionLines = new ArrayList<>();
  Random random = new Random();

  public String greetingPerson() {
    if (!greetingLines.isEmpty()) {
      int index = random.nextInt(0, greetingLines.size());
      return greetingLines.get(index);
    }
    return "Hello!";
  }

  public String greetingBack() {
    if (!greetingBackLines.isEmpty()) {
      int index = random.nextInt(0, greetingBackLines.size());
      return greetingBackLines.get(index);
    }
    return "I'm fine. Thank you.";
  }

  public String tellIsInShow() {
    if (!tellIsInSHowLines.isEmpty()) {
      int index = random.nextInt(0, tellIsInSHowLines.size());
      return tellIsInSHowLines.get(index);
    }
    return "Wow, how exciting! Tell me your question!";
  }


  public String askQuestion() {
    if (!askQuestionsLines.isEmpty()) {
      int index = random.nextInt(0, askQuestionsLines.size());
      return askQuestionsLines.get(index);
    }
    return "Let me think about it.";
  }

  public String getSolution(GameQuestion gameQuestion) {
    int possibility = 0;
    List<Answer> tmp = new ArrayList<>();
    for (Answer a : gameQuestion.getAnswers()) {
      if (a.isCorrect()) {
        tmp.add(a);
      }
    }

    for (Answer a : gameQuestion.getAnswers()) {
      if (!a.isCorrect()) {
        tmp.add(a);
      }
    }

    switch (gameQuestion.getDifficulty()) {
      case EASY -> possibility = 1;
      case MEDIUM -> possibility = 2;
      case HARD -> possibility = 3;
    }
    //TODO Mind that after 50/50 there are only 2 answers possible / visible
    String possibleAnswer = tmp.get(random.nextInt(0, possibility)).getAnswer();
    return concatAnswerString(possibleAnswer);
  }

  public abstract String concatAnswerString(String possibleAnswer);
}
