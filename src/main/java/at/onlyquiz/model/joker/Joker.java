package at.onlyquiz.model.joker;

import at.onlyquiz.model.question.GameQuestion;

public abstract class Joker {

  protected boolean used;

  public Joker() {
    this.used = false;
  }

  public void use(GameQuestion gameQuestion) {
  }


  public boolean isUsed() {
    return used;
  }
}
