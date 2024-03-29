package at.onlyquiz.model.joker;

import at.onlyquiz.model.question.Answer;
import at.onlyquiz.model.question.GameQuestion;
import at.onlyquiz.util.liveAudienceVoting.VotingServer;
import at.onlyquiz.util.timeSystem.TimeConstants;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;

public class AudienceJoker extends Joker {
    private Random random = new Random();
    private int secondsRemaining = TimeConstants.LIVE_AUDIENCE_TIMER;

    public AudienceJoker(boolean isOnline) {
        super();
        this.online = isOnline;
    }

    @Override
    public void use(GameQuestion gameQuestion) {
        if (online) {
            try {
                VotingServer.startServers(gameQuestion.getAnswers());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            List<Answer> answers = new ArrayList<>(gameQuestion.getAnswers());
            double votingSum = 100;
            double from = 0, to = 0;
            switch (gameQuestion.getDifficulty()) {
                case EASY -> { // clearly defined
                    from = 60;
                    to = 75;
                }
                case MEDIUM -> { // less defined
                    from = 50;
                    to = 60;
                }
                case HARD -> { // not clear
                    from = 20;
                    to = 40;
                }
            }
            for (Answer a : answers) {
                if (a.isCorrect()) {
                    a.setVotingValue(random.nextDouble(from, to));
                    votingSum -= a.getVotingValue();
                    answers.remove(a);
                    break;
                }
            }

            for (Answer a : answers) {
                a.setVotingValue(random.nextDouble(0, votingSum));
                votingSum -= a.getVotingValue();

            }
            used = true;

        }
    }

    public String generateQrURL() {
        return "http://" + getLocalIPAddress() + ":8080/vote";
    }

    public int getSecondsRemaining() {
        return secondsRemaining;
    }

    public void setSecondsRemaining(int secondsRemaining) {
        this.secondsRemaining = secondsRemaining;
    }

    public void resetSecondsRemaining() {
        this.secondsRemaining = 0;
    }

    public String getLocalIPAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();

                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();

                    // Filtere IPv4-Adressen und lokale Adressen
                    if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "127.0.0.1"; // Fallback auf localhost
    }

    public void stopVoting(List<Answer> answers) {
        Map<Integer, Double> resultMap = VotingServer.getResults(VotingServer.stopServer());
        for (int key : resultMap.keySet()) {
            answers.get(key - 1).setVotingValue(resultMap.get(key));
        }
    }

}
