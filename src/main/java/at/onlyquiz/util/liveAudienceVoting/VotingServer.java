package at.onlyquiz.util.liveAudienceVoting;

import at.onlyquiz.model.question.Answer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VotingServer {
    private static HttpServer httpServer;
    private static Map<Integer, Integer> buttonClickCount;

    static {
        setUpDefaultButtonHashMap();
    }


    public static void startServers(List<Answer> answers) throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(8080), 0);

        httpServer.createContext("/vote", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String resourcePath = "/at/onlyquiz/liveVoting/votingPage.html";
                InputStream resourceStream = getClass().getResourceAsStream(resourcePath);

                if (resourceStream != null) {
                    String htmlTemplate = readFromInputStream(resourceStream);

                    String htmlResponse = htmlTemplate.replace(">$BUTTON_TEXT_1$", answers.get(0).isVisible() ? ">" + answers.get(0).getAnswer() : "style=\"display: none\"" + ">").replace(">$BUTTON_TEXT_2$", answers.get(1).isVisible() ? ">" + answers.get(1).getAnswer() : "style=\"display: none\"" + ">").replace(">$BUTTON_TEXT_3$", answers.get(2).isVisible() ? ">" + answers.get(2).getAnswer() : "style=\"display: none\"" + ">").replace(">$BUTTON_TEXT_4$", answers.get(3).isVisible() ? ">" + answers.get(3).getAnswer() : "style=\"display: none\"" + ">");

                    exchange.sendResponseHeaders(200, htmlResponse.getBytes(StandardCharsets.UTF_8).length);

                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(htmlResponse.getBytes(StandardCharsets.UTF_8));
                    } finally {
                        resourceStream.close();
                    }
                } else {
                    String response = "Resource not found";
                    exchange.sendResponseHeaders(404, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            }
        });

        httpServer.createContext("/click", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                InputStream requestBody = exchange.getRequestBody();
                String jsonInput = new String(requestBody.readAllBytes(), StandardCharsets.UTF_8);
                int buttonId = extractButtonIdFromJson(jsonInput);

                //incresed the button clicked value by 1
                buttonClickCount.put(buttonId, buttonClickCount.getOrDefault(buttonId, 0) + 1);
                System.out.println(buttonClickCount.get(buttonId));
                exchange.sendResponseHeaders(200, -1); // 302 steht für Redirect

                exchange.getResponseBody().close();
            }

            private int extractButtonIdFromJson(String jsonInput) {
                return Integer.parseInt(jsonInput.split(":")[1].replaceAll("\\D", ""));
            }
        });

        httpServer.createContext("/thanks", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String resourcePath = "/at/onlyquiz/liveVoting/thankYouPage.html";
                InputStream resourceStream = getClass().getResourceAsStream(resourcePath);

                if (resourceStream != null) {
                    byte[] responseBytes = readFromInputStream(resourceStream).getBytes(StandardCharsets.UTF_8);
                    exchange.sendResponseHeaders(200, responseBytes.length);

                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(responseBytes);
                    } finally {
                        resourceStream.close();
                    }
                } else {
                    String response = "Resource not found";
                    exchange.sendResponseHeaders(404, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            }
        });

        httpServer.setExecutor(null);
        httpServer.start();
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (java.util.Scanner scanner = new java.util.Scanner(inputStream, StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                resultStringBuilder.append(scanner.nextLine()).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    public static Map<Integer, Integer> stopServer() {
        if (httpServer != null) {
            httpServer.stop(0);
        }
        Map<Integer, Integer> tmp = buttonClickCount;
        setUpDefaultButtonHashMap();
        return tmp;
    }

    public static Map<Integer, Double> getResults(Map<Integer, Integer> votingValues) {
        Map<Integer, Integer> filledMap = new HashMap<>();
        for (int i = 1; i <= 4; i++) {
            filledMap.put(i, votingValues.getOrDefault(i, 0));
        }

        int sum = 0;
        for (int value : filledMap.values()) {
            sum += value;
        }

        Map<Integer, Double> percentagesMap = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : filledMap.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            double percentage = (value * 100.0) / sum;
            percentagesMap.put(key, percentage);
        }

        return percentagesMap;
    }

    private static void setUpDefaultButtonHashMap() {
        buttonClickCount = new HashMap<>();
        buttonClickCount.put(1, 0);
        buttonClickCount.put(2, 0);
        buttonClickCount.put(3, 0);
        buttonClickCount.put(4, 0);
    }
}
