package day3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3Test {

    private Day3 testObject;

    @BeforeEach
    void setUp() throws IOException {
        testObject = new Day3("src/test/resources/day3/input.txt");
    }

    @Test
    void testWeCanExctractMulXYFrom1LineOfText() {
        List<String> data = testObject.getData();

        List<String> validInstructions = testObject.getValidInstructions(data);

        assertEquals(validInstructions.size(), 4);
        assertEquals(validInstructions.get(0), "2,4");
        assertEquals(validInstructions.get(1), "5,5");
        assertEquals(validInstructions.get(2), "11,8");
        assertEquals(validInstructions.get(3), "8,5");
    }

    @Test
    void testMultiplyingValuesInResultsProducesCorrectResult() {
        List<String> data = testObject.getData();
        List<String> validInstructions = testObject.getValidInstructions(data);

        long result = testObject.compute(validInstructions);

        assertEquals(161, result);
    }

    @Test
    void testWithDoDont() throws IOException {
        Day3 part2TestObject = new Day3("src/test/resources/day3/input_do_dont.txt");
        List<String> data = part2TestObject.getData();
        List<String> validInstructions = part2TestObject.getValidInstructions(data);

        long result = part2TestObject.compute(validInstructions);

        assertEquals(48, result);
    }

    @Test
    void testWithDoOnlyFromFirstLineOverMultipleLines() throws IOException {
        Day3 part2TestObject = new Day3("src/test/resources/day3/realData.txt");
        List<String> data = part2TestObject.getData();
        List<String> validInstructions = part2TestObject.getValidInstructions(data);

        long result = part2TestObject.compute(validInstructions);

        assertEquals(5768506, result);
    }

    @Test
    void testWithDosAndDontsOnlyFromFirstLineOverOneLine() throws IOException {
        Day3 part2TestObject = new Day3("src/test/resources/day3/realDataWithDosAndDonts.txt");
        List<String> data = part2TestObject.getData();
        List<String> validInstructions = part2TestObject.getValidInstructions(data);

        long result = part2TestObject.compute(validInstructions);

        assertEquals(7092082, result);
    }
}