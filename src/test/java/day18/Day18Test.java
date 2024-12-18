package day18;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day18Test {

    private Day18 testObject;

    @BeforeEach
    void setUp() throws IOException {
        List<String> testInput = FileUtils.getFileAsList("src/test/resources/day18/input.txt");
        List<List<Integer>> puzzleInput = new ArrayList<>();
        for (int i = 0; i < testInput.size(); i++) {
            puzzleInput.add(List.of(Integer.parseInt(testInput.get(i).split(",")[0]), Integer.parseInt(testInput.get(i).split(",")[1])));
        }
        testObject = new Day18(puzzleInput);
    }

    @Test
    void testPathToEnd() {

    }
}
