package at.onlyquiz.model.question;

public class Answer {
  private String answer;
  private final boolean correct;
  private boolean visible;
  private double votingValue;

  public Answer(String answer, boolean correct) {
    this.answer = answer;
    this.correct = correct;
    visible = true;
    this.votingValue = 0;
  }

  public double getVotingValue() {
    return votingValue;
  }

  public void setVotingValue(double votingValue) {
    this.votingValue = votingValue;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public boolean isCorrect() {
    return correct;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }
}
