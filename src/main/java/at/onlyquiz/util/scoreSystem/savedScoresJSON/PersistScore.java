package at.onlyquiz.util.scoreSystem.savedScoresJSON;

import at.onlyquiz.gameplay.GameMode;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PersistScore {
    private static final String savedScorePath = PersistScore.class.getResource("/at/onlyquiz/scores/savedScores.json").getPath();
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
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

    public PersistScore(String gameMode, String playerName, Integer score) {
        this.gameMode = gameMode;
        this.playerName = playerName;
        this.score = score;
    }

    public PersistScore() {
    }

    public static void saveScore(GameMode gameMode){
        String gameModeName = gameMode.getClass().getSimpleName();
        String playerName = gameMode.getPlayername();
        int achievedScore = gameMode.getAchievedScore();

        if (achievedScore != 0 && !playerName.isEmpty()) {
            PersistScore newScore = new PersistScore(
                    gameModeName,
                    playerName,
                    achievedScore
            );

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

    public static List<PersistScore> getTop10fromAllGameModes(){

        List<PersistScore> allScores;
        try {
            allScores = mapper.readValue(
                    new File(savedScorePath),
                    mapper.getTypeFactory().constructCollectionType(List.class, PersistScore.class));

            allScores.sort((score1, score2) -> Integer.compare(score2.getScore(), score1.getScore()));

            return allScores.stream().limit(10).collect(Collectors.toList());
        } catch (IOException e) {
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
}

