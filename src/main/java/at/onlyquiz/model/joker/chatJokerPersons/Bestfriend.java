package at.onlyquiz.model.joker.chatJokerPersons;

import at.onlyquiz.model.question.Answer;
import at.onlyquiz.model.question.GameQuestion;

import java.util.ArrayList;
import java.util.List;

public class Bestfriend extends ChatJokerPerson{

    public Bestfriend(){
        greetingLines.add("Hello Buddy, whats up? How can I help you?");
        greetingLines.add("Wassaaapp?");
        greetingLines.add("Hey my BFF! How was your Date yesterday? Did you have \"the seks\"?");
        greetingLines.add("Hi there! it's been a minute, whats new with you?");

        greetingBackLines.add("I'm pretty well, thanks. How can I help you?");

        tellIsInSHowLines.add("Nice, you must tell me how you get there.. What is your Question?");


        askQuestionsLines.add("Oh. That is a really good question...");

        askQuestionsLines.add("Let me thinkt about that...");
    }

    public String concatAnswerString(String possibleAnswer){
        String returnText = "";

        switch (random.nextInt(0,3)){
            case 0 -> returnText = "I am 100% sure that " + possibleAnswer + " must be correct!";
            case 1 -> returnText = "Uff, not sure about that. Probably " + possibleAnswer +". I guess?";
            case 2 -> returnText = "I'm taking a shot in the dark and say "+ possibleAnswer +" is right";
        }


        return returnText;
    }

}
