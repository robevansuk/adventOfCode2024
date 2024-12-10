package day7;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileUtils;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void testNextOpsForPlusPlus() {
        List<String> ops = List.of("+", "+");
        List<String> nextOps = testObject.nextOps(ops);
        assertEquals(List.of("+", "*"), nextOps);

        List<String> nextOps1 = testObject.nextOps(nextOps);
        assertEquals(List.of("*" , "+"), nextOps1);

        List<String> nextOps2 = testObject.nextOps(nextOps1);
        assertEquals(List.of("*" , "*"), nextOps2);
    }
}
