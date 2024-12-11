package day7;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.FileUtils;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Day7Test {

    private Day7 testObject;

    @BeforeEach
    void setUp() throws IOException {
        List<String> lines = FileUtils.getFileAsList("src/test/resources/day7/input.txt");
        testObject = new Day7(lines);

    }

    @Test
    void testResultOfTestDataIsGood() {
        Long result = testObject.run(testObject.getPuzzles());
        assertEquals(3749, result);
    }

    @Test
    void getNextOpsShouldReturnStarForPlus() {
        List<String> ops = List.of("+");
        List<String> nextOps = testObject.nextOps(ops);
        assertEquals(List.of("*"), nextOps);
    }

    @Disabled("Part 2 introduced bars - this no longer works as originally intended")
    void testNextOpsForPlusPlus() {
        List<String> ops = List.of("+", "+");
        List<String> nextOps = testObject.nextOps(ops);
        assertEquals(List.of("+", "*"), nextOps);

        List<String> nextOps1 = testObject.nextOps(nextOps);
        assertEquals(List.of("*", "+"), nextOps1);

        List<String> nextOps2 = testObject.nextOps(nextOps1);
        assertEquals(List.of("*", "*"), nextOps2);
    }

    @Test
    void canFindSolutionFromExampleInputs() {
        List<String> ops = List.of("+", "+", "+");
        List<Integer> ints = List.of(1, 2, 3, 4);
        ResultDay7 preprocessed = testObject.runCalc(492l, ints, ops, List.of("|", "|", "|"));

        // 1 | 2 | 3 + 4 = 123 * 4 = 492
        assertTrue(preprocessed.isPossible());
    }

    @Test
    void canFindAnotherSolutionFromExampleInputs() {
        List<String> ops = List.of("+", "+", "+");
        List<Integer> ints = List.of(1, 2, 3, 4);
        ResultDay7 preprocessed = testObject.runCalc(127l, ints, ops, List.of("|", "|", "|"));

        // 1 | 2 | 3 + 4 = 123 + 4 = 127
        assertTrue(preprocessed.isPossible());
    }

    @Test
    void runExamplesFromAOC() throws IOException {
        List<String> lines2 = FileUtils.getFileAsList("src/test/resources/day7/input2.txt");
        Day7 testObject2 = new Day7(lines2);
        testObject2.run(testObject2.getPuzzles());

    }
}

