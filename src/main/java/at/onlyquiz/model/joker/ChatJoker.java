package at.onlyquiz.model.joker;

import at.onlyquiz.model.joker.chatJokerPersons.Bestfriend;
import at.onlyquiz.model.joker.chatJokerPersons.ChatJokerPerson;
import at.onlyquiz.model.question.GameQuestion;

public class ChatJoker extends Joker {

  private String chatHistory = "";
  private String inputText1 = "", inputText2 = "", inputText3 = "", inputText4 = "";
  int responseCounter = 0;
  private String selectedInput = "";
  private ChatJokerPerson person;
  private GameQuestion currentQuestion;

  public ChatJoker(boolean isOnline) {
    super();
    this.online = isOnline;
    this.person = new Bestfriend();
  }

  @Override
  public void use(GameQuestion question) {
    if (!online) {
      chatHistory = person.greetingPerson();
      currentQuestion = question;
    }
  }


  public String getNextResponse() {
    switch (responseCounter) {
      case 0 -> {
        //Options after he greets you - you have to answer the person back how he / she is.
        inputText1 = "";
        inputText2 = "Hello, im fine - how are you?";
        inputText3 = "";
        inputText4 = "";
        return person.greetingPerson() + "\n";
      }
      case 1 -> {
        //after you greets him back
        inputText1 = "";
        inputText2 = "I am playing \"OnlyQuiz\" and I have a question I need your help with";
        inputText3 = "";
        inputText4 = "";
        return person.greetingBack() + "\n";
      }
      case 2 -> {
        //after you tell him that you playing onlyQuiz
        inputText1 = "the question is: " + currentQuestion.getQuestion();
        inputText2 = "";
        inputText3 = currentQuestion.getQuestion();
        inputText4 = "";
        return person.tellIsInShow() + "\n";
      }
      case 3 -> {
        //after you ask the Question.
        inputText1 = "What could be the right answer?";
        inputText2 = "It could be " + currentQuestion.getAnswers().get(0).getAnswer();
        inputText3 = "I am complete lost. Hopefully you can help me...";
        inputText4 = "";
        return person.askQuestion() + "\n";
      }
      case 4 -> {
        // values must be empty - cause the joker ends here
        inputText1 = "";
        inputText2 = "";
        inputText3 = "";
        inputText4 = "";
        return person.getSolution(currentQuestion);
      }
    }
    return "";
  }

  public void setPerson(ChatJokerPerson person) {
    this.person = person;
  }

  public String getChatHistory() {
    return chatHistory;
  }

  public String getInputText1() {
    return inputText1;
  }

  public void setInputText1(String inputText1) {
    this.inputText1 = inputText1;
  }

  public String getInputText2() {
    return inputText2;
  }

  public void setInputText2(String inputText2) {
    this.inputText2 = inputText2;
  }

  public String getInputText3() {
    return inputText3;
  }

  public void setInputText3(String inputText3) {
    this.inputText3 = inputText3;
  }

  public String getInputText4() {
    return inputText4;
  }

  public void setInputText4(String inputText4) {
    this.inputText4 = inputText4;
  }

  public String getSelectedInput() {
    return selectedInput;
  }

  public String setSelectedInput(String selectedInput) {
    this.selectedInput = selectedInput;
    responseCounter++;
    return getNextResponse();
  }


  public ChatJokerPerson getPerson() {
    return person;
  }
}
