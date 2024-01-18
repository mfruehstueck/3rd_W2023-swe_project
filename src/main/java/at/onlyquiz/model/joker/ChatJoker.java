package at.onlyquiz.model.joker;

import at.onlyquiz.model.question.GameQuestion;

public class ChatJoker extends Joker {

  @Override
  public void use(GameQuestion question) {
    if(!online){

    }
  }

  public ChatJoker(boolean isOnline) {
    super();
    this.online = isOnline;
  }

}
