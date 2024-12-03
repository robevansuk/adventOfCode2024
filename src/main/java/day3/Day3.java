package day3;

import utils.FileUtils;

import java.io.IOException;
import java.util.List;

public class Day3 {

    private static final String INPUT = "src/main/resources/day3/input.txt";
    private final List<String> reports;

    public Day3(String input) throws IOException {
        reports = FileUtils.getFileAsList(input);
    }


    public static void main(String[] args) {
        try {
            Day3 day3 = new Day3(INPUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
