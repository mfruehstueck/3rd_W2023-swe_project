package at.onlyquiz.util.jsonParser.models;

import java.util.HashMap;
import java.util.Objects;

public class User {
  private String userName;
  private String hashedPassword;
  private HashMap<String, Boolean> selectedQuestionnaires;

  public User() { }
  public User(String userName) {
    this.userName = userName;
    this.hashedPassword = null;
  }
  public User(String userName, String password) {
    this.userName = userName;
    this.hashedPassword = hashPassword(password);
    this.selectedQuestionnaires = new HashMap<>();
  }

  public boolean checkPassword(String password) { return (Objects.equals(this.hashedPassword, password)); }
  private String hashPassword(String password) { return password; }

  public String getUserName() { return userName; }
  public void setUserName(String userName) { this.userName = userName; }
  public String getHashedPassword() { return hashedPassword; }
  public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }

  public HashMap<String, Boolean> getSelectedQuestionnaires() { return selectedQuestionnaires; }
  public void setSelectedQuestionnaires(HashMap<String, Boolean> selectedQuestionnaires) { this.selectedQuestionnaires = selectedQuestionnaires; }
}
