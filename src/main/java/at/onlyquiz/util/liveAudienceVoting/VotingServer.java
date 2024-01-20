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

        httpServer.createContext("/onlyQuizLiveVoting", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String resourcePath = "/at/onlyquiz/liveVoting/votingPage.html";
                InputStream resourceStream = getClass().getResourceAsStream(resourcePath);

                if (resourceStream != null) {
                    String htmlTemplate = readFromInputStream(resourceStream);

                    String htmlResponse = htmlTemplate
                            .replace("$BUTTON_TEXT_1$", answers.get(0).getAnswer())
                            .replace("$BUTTON_TEXT_2$", answers.get(1).getAnswer())
                            .replace("$BUTTON_TEXT_3$", answers.get(2).getAnswer())
                            .replace("$BUTTON_TEXT_4$", answers.get(3).getAnswer());

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

        httpServer.createContext("/countButtonClick", new HttpHandler() {
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

        httpServer.createContext("/thankYouPage", new HttpHandler() {
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

    private static void setUpDefaultButtonHashMap(){
        buttonClickCount = new HashMap<>();
        buttonClickCount.put(1,0);
        buttonClickCount.put(2,0);
        buttonClickCount.put(3,0);
        buttonClickCount.put(4,0);
    }
}
