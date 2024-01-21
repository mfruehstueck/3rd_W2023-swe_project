package at.onlyquiz.model.joker;

import at.onlyquiz.model.joker.chatJokerPersons.*;
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
    this.person = null;
  }


  @Override
  public void use(GameQuestion question) {
    if(!online){
      chatHistory = person.greetingPerson();
      currentQuestion = question;
    }
  }


  public String getNextResponse() {
    String personType = person.getClass().getSimpleName(); // Get the type of ChatJokerPerson


    switch (responseCounter) {
      case 1 -> {
        // Different options after the greeting based on the ChatJokerPerson
        switch (personType) {
          case "GrandMa" -> {
            inputText1 = "I'm doing well, Granny. How about you?";
            inputText2 = "Hi Grandma! how is it going?";
            inputText3 = "Just the usual, Grandma. how is it going for you?";
          }
          case "Father" -> {
            inputText1 = "All good, Dad. how is it going?";
            inputText2 = "Hey Dad, got a tricky quiz question here.";
            inputText3 = "Doing fine, Dad. Need some advice on a question.";
          }
          case "Bestfriend" -> {
            inputText1 = "Hey! All good. Need help with a quiz.";
            inputText2 = "What's up! Stuck on a quiz question.";
            inputText3 = "Just hanging out. Got a question for you.";
          }
          case "Aunt" -> {
            inputText1 = "Hello Auntie, I need your wisdom on something.";
            inputText2 = "Hi Aunt, got a moment to help with a quiz?";
            inputText3 = "Hey there, Auntie! Struggling with a quiz question here.";
          }
        }
        return person.greetingPerson() + "\n";
      }
      case 2 -> {
        // Responses after greeting back, personalized for each ChatJokerPerson
        switch (personType) {
          case "GrandMa" -> {
            inputText1 = "Just the usual stuff, Grandma. Need a bit of your wisdom.";
            inputText2 = "Grandma, I’m playing a quiz and I’m stuck. Can you help?";
            inputText3 = "Everything's fine. Just pondering over a quiz question.";
          }
          case "Father" -> {
            inputText1 = "All good, Dad. There's this question in a quiz...";
            inputText2 = "Hey Dad, I could use your help with a quiz question.";
            inputText3 = "Things are fine. Got a tricky question here.";
          }
          case "Bestfriend" -> {
            inputText1 = "You know me, always into something. Got a quiz question.";
            inputText2 = "Hey! Just trying to ace this quiz. Need your brain.";
            inputText3 = "All's well. Just stuck on this weird quiz question.";
          }
          case "Aunt" -> {
            inputText1 = "Auntie, I'm good. There’s this quiz I'm trying to figure out.";
            inputText2 = "Doing well, Aunt. Just puzzled by a quiz question.";
            inputText3 = "All's great. But there's a question in a quiz I can't crack.";
          }
        }
        return person.greetingBack() + "\n";
      }
      case 3 -> {
        // Tell them about playing OnlyQuiz
        inputText1 = "The question is: " + currentQuestion.getQuestion();
        inputText2 = "Here's a tough one: " + currentQuestion.getQuestion();
        inputText3 = "Need your brain for this: " + currentQuestion.getQuestion();
        return person.tellIsInShow() + "\n";
      }
      case 4 -> {
        // After asking the question, vary the options
        inputText1 = "Any idea about the right answer?";
        inputText2 = "This one's hard. What do you think?";
        inputText3 = "I'm stumped. Your thoughts?";
        return person.askQuestion() + "\n";
      }
      case 5 -> {
        // Conclude the chat
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
    this.responseCounter++;
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
