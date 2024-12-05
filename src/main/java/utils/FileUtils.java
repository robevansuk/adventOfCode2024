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

    public static String[][] getFileAsArray(String input) throws IOException {
        List<String> fileAsList = getFileAsList(input);

        String[][] array = new String[fileAsList.size()][fileAsList.get(0).length()];
        for (int i = 0; i < fileAsList.size(); i++) {
            String line = fileAsList.get(i);
            for (int j = 0; j < line.length(); j++) {
                array[i][j] = String.valueOf(line.charAt(j));
            }
        }
        return array;
    }
}
