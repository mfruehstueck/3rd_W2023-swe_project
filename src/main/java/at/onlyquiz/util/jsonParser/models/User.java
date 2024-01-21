package at.onlyquiz.util.jsonParser.models;

public class User {
  private String userName;
  private String hashedPassword;

  public User() { }
  public User(String userName, String hashedPassword) {
    this.userName = userName;
    this.hashedPassword = hashedPassword;
  }

  public boolean checkPassword(String password) { return false; }
  private String hashPassword(String password) { return null; }

  public String getUserName() { return userName; }
  public void setUserName(String userName) { this.userName = userName; }
  public String getHashedPassword() { return hashedPassword; }
  public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }
}
