package at.onlyquiz.util;

import at.debugtools.DebugTools;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileManagement {
    public static void getOrCreateFile(File file) {
        try {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        } catch (IOException e) {
            System.out.println(DebugTools.debugLine(new Throwable()) + "failed to create " + file);
        }
    }

    public static Path getPath(Path path, String file) {
        return Path.of(path.toString(), file);
    }

  public static File save_file(String title, String[] filter) {
    FileChooser ofd = new FileChooser();
    ofd.setTitle(title);

    // Set the initial directory (optional)
    // fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

    // Add file filters (optional)
    if (filter != null && filter.length == 2) {
      ofd.getExtensionFilters().add(new FileChooser.ExtensionFilter(filter[0], filter[1]));
    }

        File selectedFile = ofd.showSaveDialog(null);

        if (selectedFile == null) return null;
        System.out.println(DebugTools.debugLine(new Throwable()) + "saved " + selectedFile);

        return selectedFile;
    }

    public static File open_file(String title, String[] filter) {
        FileChooser ofd = new FileChooser();
        ofd.setTitle(title);

        // Add file filters (optional)
        if (filter != null && filter.length == 2) {
            ofd.getExtensionFilters().add(new FileChooser.ExtensionFilter(filter[0], filter[1]));
        }

        File selectedFile = ofd.showOpenDialog(null);

        if (selectedFile == null) return null;
        System.out.println(DebugTools.debugLine(new Throwable()) + "saved " + selectedFile);

        return selectedFile;
    }

    public static void copy_file(File source, File destination) {
        try {
            Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(DebugTools.debugLine(new Throwable()) + "failed to copy_file");
        }
    }

    public static void delete_file(File file) {
        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            System.out.println(DebugTools.debugLine(new Throwable()) + "failed to delete_file");
        }
    }
}
