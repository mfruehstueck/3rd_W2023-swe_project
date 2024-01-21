package at.onlyquiz.util.userManagement;

public enum User_Properties {
  LIVEAUDIENCE("liveAudience");
  private final String liveAudience;
  User_Properties(String liveAudience) { this.liveAudience = liveAudience; }
  public String getLiveAudience() { return liveAudience; }
}