package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileUtils {
    public static List<String> getFileAsList(String input) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(input));
        if (lines.size() == 0) {
            throw new IllegalArgumentException("Input file should have more than zero lines");
        }
        return lines;
    }
}
