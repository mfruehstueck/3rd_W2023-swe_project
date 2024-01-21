package at.onlyquiz.util.jsonParser.models;

import at.onlyquiz.gameplay.GameMode;
import at.onlyquiz.util.Configuration;
import at.onlyquiz.util.jsonParser.JSON_Parser;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PersistScore {
  private String gameMode;
  private String playerName;
  private Integer score;

  public PersistScore() { }
  public PersistScore(String gameMode, String playerName, Integer score) {
    this.gameMode = gameMode;
    this.playerName = playerName;
    this.score = score;
  }
  public PersistScore(GameMode gameMode) {
    String gameModeName = gameMode.getClass().getSimpleName();
    String playerName = gameMode.getPlayerName();
    int achievedScore = gameMode.getAchievedScore();

    this.gameMode = gameModeName;
    this.playerName = playerName;
    this.score = achievedScore;
  }

  public static void saveScore(GameMode gameMode) { JSON_Parser.write(Configuration.SAVEDSCORE_FILE, new PersistScore(gameMode)); }

  public static List<PersistScore> getSavedScores() { return JSON_Parser.read(Configuration.SAVEDSCORE_FILE, PersistScore.class); }

  public static List<PersistScore> getTop10fromAllGameModes() {
    List<PersistScore> output = getSavedScores();

    output.sort(Comparator.comparingInt(PersistScore::getScore));

    return output.stream().limit(10).collect(Collectors.toList());
  }

  public String getGameMode() { return gameMode; }
  public void setGameMode(String gameMode) { this.gameMode = gameMode; }
  public String getPlayerName() { return playerName; }
  public void setPlayerName(String playerName) { this.playerName = playerName; }
  public int getScore() { return score; }
  public void setScore(int score) { this.score = score; }
}

