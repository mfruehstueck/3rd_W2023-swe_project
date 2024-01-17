package at.onlyquiz.util;

import at.debugtools.DebugTools;
import at.onlyquiz.OnlyQuizApplication;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Objects;

public class Configuration {
    public static final Path DEFAULT_BASEPATH_QUESTIONNARES;

    static {
        try {
            DEFAULT_BASEPATH_QUESTIONNARES = Path.of(Objects.requireNonNull(OnlyQuizApplication.class.getResource("/at/onlyquiz/questionnaires")).toURI());
            System.out.println(DebugTools.debugLine(new Throwable()) + "DEFAULT_BASEPATH_QUESTIONNARES: " + DEFAULT_BASEPATH_QUESTIONNARES);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

  public static final int DEFAULT_CSV_HEADEREND_POSITION = 1;
  public static final Character DEFAULT_CSV_SEPERATOR = ',';
  public static final int MAX_TIMESELECTED = 2;
}
