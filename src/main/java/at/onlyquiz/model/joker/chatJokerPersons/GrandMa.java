package at.onlyquiz.model.joker.chatJokerPersons;

public class GrandMa extends ChatJokerPerson {

    public GrandMa() {
        super();
        greetingLines.add("Hi sweetie! How're you doing?");
        greetingLines.add("Hello pumpkin, How are you?");
        greetingLines.add("Hi cutie, how was the soup yesterday, feeling better?");
        greetingLines.add("Hi baby, how are you? Have you eaten today?");

        greetingBackLines.add("I am fine, are you visiting today? Should I fix you something to eat?");

        tellIsInShowLines.add("Ooh sweetie, I don't know if I am the right person, but I will try to help.");

        askQuestionsLines.add("Oh, that is a really good question...");
        askQuestionsLines.add("Hmm... let me think about that.");
        askQuestionsLines.add("Well, back in my day, we used to say...");
    }

    @Override
    public String concatAnswerString(String possibleAnswer) {
        String returnText = "";
        switch (random.nextInt(3)) {
            case 0 -> returnText = "I am 100% sure that " + possibleAnswer + " must be correct!";
            case 1 -> returnText = "Uff sweetie, I'm not sure. Probably " + possibleAnswer + ". I guess?";
            case 2 -> returnText = "I'll take a leap of faith and say " + possibleAnswer + " is right.";
        }
        return returnText;
    }
}
