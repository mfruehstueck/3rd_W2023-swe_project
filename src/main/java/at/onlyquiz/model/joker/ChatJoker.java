package at.onlyquiz.model.joker;

import at.onlyquiz.model.question.GameQuestion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ChatJoker extends Joker {

  @Override
  public void use(GameQuestion question) {
    if(!online){

    }
  }

  public ChatJoker(boolean isOnline) {
    super();
    this.online = isOnline;
  }

}
