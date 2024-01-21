package at.onlyquiz.model.joker.chatJokerPersons;


public class Aunt extends ChatJokerPerson {

    public Aunt() {
        super();
        greetingLines.add("Hello dear, how are you today?");
        greetingLines.add("Hey sweetie, how's it going?");

        greetingBackLines.add("I'm great, thanks! What's up?");

        tellIsInShowLines.add("Let's see if your auntie can lend a hand.");

        askQuestionsLines.add("Interesting question, let me ponder for a moment.");
        askQuestionsLines.add("Let's think this through...");
        askQuestionsLines.add("I remember discussing something like this at book club...");
    }

    @Override
    public String concatAnswerString(String possibleAnswer) {
        String returnText = "";
        switch (random.nextInt(3)) {
            case 0 -> returnText = "I have a feeling that " + possibleAnswer + " is the correct choice.";
            case 1 -> returnText = "It's hard to be certain, but I'd say " + possibleAnswer + ".";
            case 2 -> returnText = "If I had to guess, I'd pick " + possibleAnswer + ".";
        }
        return returnText;
    }
}
