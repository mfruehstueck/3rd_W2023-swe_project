package at.onlyquiz.model.joker;

import at.onlyquiz.model.question.GameQuestion;
import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ChatJoker extends Joker {
    private static final String API_URL = "https://api.openai.com/v1/engines/davinci/completions";
    private static final String API_KEY = "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";

    @Override
    public void use(GameQuestion question) {
        // Example prompt
        String prompt = "Act like a family member, you can randomly choose one, be it elderly or very young. the scene is that I need help answering a question, the moment i send you the question we have 30 seconds, you need to stall and act confused, only help me in the last 5 seconds";
        String response = getChatGptResponse(prompt);
        System.out.println(response);
    }

    private String getChatGptResponse(String prompt) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + API_KEY);
            con.setDoOutput(true);

            String jsonInputString = "{\"prompt\": \"" + prompt + "\", \"max_tokens\": 50}";
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
