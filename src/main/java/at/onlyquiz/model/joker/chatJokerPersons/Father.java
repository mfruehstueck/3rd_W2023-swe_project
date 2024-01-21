package at.onlyquiz.model.joker.chatJokerPersons;

public class Father extends ChatJokerPerson {

    public Father() {
        super();
        greetingLines.add("Hey kiddo, how's it going?");
        greetingLines.add("Hello there! what's going on ?");

        greetingBackLines.add("I'm good, thanks. What's new..");

        tellIsInShowLines.add("Alright, let's see if I can help you with this.");

        askQuestionsLines.add("That's a tough one, but let's work it out together.");
        askQuestionsLines.add("Hmm, let me think...");
        askQuestionsLines.add("You know, when I was your age, we often faced similar problems...");

        //concludingLines.add("You got this! Trust your instincts.");
        //concludingLines.add("Whatever happens, I'm proud of you.");
        //concludingLines.add("Take your time and make the best choice.");
    }

    @Override
    public String concatAnswerString(String possibleAnswer) {
        String returnText = "";
        switch (random.nextInt(3)) {
            case 0 -> returnText = "I believe " + possibleAnswer + " is the right answer.";
            case 1 -> returnText = "It's a bit tricky, but I'd go with " + possibleAnswer + ".";
            case 2 -> returnText = "I'm leaning towards " + possibleAnswer + ", but it's just a hunch.";
        }
        return returnText;
    }
}
