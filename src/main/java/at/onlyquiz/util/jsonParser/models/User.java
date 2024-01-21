package at.onlyquiz.util.jsonParser.models;

import at.onlyquiz.util.userManagement.User_Properties;

import java.util.HashMap;
import java.util.Objects;

public class User {
  private String userName;
  private String hashedPassword;
  private HashMap<String, Boolean> selectedQuestionnaires;
  private HashMap<String, Boolean> selectedProperties;

  public User() { }
  public User(String userName) {
    this.userName = userName;
    this.hashedPassword = null;
    this.selectedQuestionnaires = null;
    this.selectedProperties = null;
  }
  public User(String userName, String password) {
    this.userName = userName;
    this.hashedPassword = hashPassword(password);
    this.selectedQuestionnaires = new HashMap<>();
    this.selectedProperties = new HashMap<>() {{ put(User_Properties.LIVEAUDIENCE.getLiveAudience(), false); }};
  }

  public boolean checkPassword(String password) { return (Objects.equals(this.hashedPassword, password)); }
  private String hashPassword(String password) { return password; }

  public String getUserName() { return userName; }
  public void setUserName(String userName) { this.userName = userName; }
  public String getHashedPassword() { return hashedPassword; }
  public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }

  public HashMap<String, Boolean> get_selectedQuestionnaires() { return selectedQuestionnaires; }
  public HashMap<String, Boolean> get_selectedProperties() { return selectedProperties; }
  public void set_selectedQuestionnaires(HashMap<String, Boolean> selectedQuestionnaires) { this.selectedQuestionnaires = selectedQuestionnaires; }
  public void set_selectedProperties(HashMap<String, Boolean> selectedProperties) { this.selectedProperties = selectedProperties; }
}
