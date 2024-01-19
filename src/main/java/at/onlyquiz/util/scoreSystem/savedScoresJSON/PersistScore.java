package at.onlyquiz.util.scoreSystem.savedScoresJSON;

import at.onlyquiz.gameplay.GameMode;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersistScore {
    private static final String savedScorePath = PersistScore.class.getResource("/at/onlyquiz/scores/savedScores.json").getPath();
    private static ObjectMapper mapper = new ObjectMapper();
    private String gameMode;
    private String playerName;
    private Integer score;

    static {
        File file = new File(savedScorePath);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date date;

    public PersistScore(String gameMode, String playerName, Integer score, Date date) {
        this.gameMode = gameMode;
        this.playerName = playerName;
        this.score = score;
        this.date = date;
    }

    public static void saveScore(GameMode gameMode){
        if (gameMode.getAchievedScore() != null || gameMode.getAchievedScore() != 0) {
            PersistScore newScore = new PersistScore(
                    gameMode.getClass().getName(),
                    gameMode.getPlayername(),
                    gameMode.getAchievedScore(),
                    new Date());

            try {
                List<PersistScore> existingScores = mapper.readValue(
                        new File(savedScorePath),
                        mapper.getTypeFactory().constructCollectionType(List.class, PersistScore.class)
                );

                existingScores.add(newScore);

                mapper.writeValue(new File(savedScorePath), existingScores);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<PersistScore> getSavedScores(){
        try {
            return mapper.readValue(
                    new File(savedScorePath),
                    mapper.getTypeFactory().constructCollectionType(List.class, PersistScore.class)
            );
        } catch (IOException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
