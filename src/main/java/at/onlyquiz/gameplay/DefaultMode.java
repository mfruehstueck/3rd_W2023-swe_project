package at.onlyquiz.gameplay;

import at.onlyquiz.model.joker.AudienceJoker;
import at.onlyquiz.model.joker.ChatJoker;
import at.onlyquiz.model.joker.FiftyFiftyJoker;

public class DefaultMode extends GameMode {
    private FiftyFiftyJoker fiftyFiftyJoker;
    private ChatJoker chatJoker;
    private AudienceJoker audienceJoker;

    public DefaultMode(int achievedScore) {
        this.achievedScore = achievedScore;
    }
}
