package at.onlyquiz.util;

import at.debugtools.DebugTools;
import at.onlyquiz.OnlyQuizApplication;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class Configuration {
  public static final Path DEFAULT_BASEPATH_QUESTIONNARES;
  public static final Path DEFAULT_BASEPATH_CONFIGS;
  public static final File SAVEDSCORE_FILE;
  public static final File USER_FILE;

  static {
    try {
      DEFAULT_BASEPATH_QUESTIONNARES = Path.of(OnlyQuizApplication.class.getResource("/at/onlyquiz/questionnaires").toURI());
      DEFAULT_BASEPATH_CONFIGS = Path.of(OnlyQuizApplication.class.getResource("/at/onlyquiz/scores").toURI());
      System.out.println(DebugTools.debugLine(new Throwable()) + "DEFAULT_BASEPATH_QUESTIONNARES: " + DEFAULT_BASEPATH_QUESTIONNARES);

      SAVEDSCORE_FILE = FileManagement.getPath(Configuration.DEFAULT_BASEPATH_CONFIGS, "savedScores.json").toFile();
      USER_FILE = FileManagement.getPath(Configuration.DEFAULT_BASEPATH_CONFIGS, "users.json").toFile();

      FileManagement.getOrCreateFile(SAVEDSCORE_FILE);
      FileManagement.getOrCreateFile(USER_FILE);
    } catch (URISyntaxException | NullPointerException e) {
      throw new RuntimeException(e);
    }
  }

  public static final int DEFAULT_CSV_HEADEREND_POSITION = 1;
  public static final Character DEFAULT_CSV_SEPERATOR = ',';
  public static final int MAX_TIMESELECTED = 2;
}
