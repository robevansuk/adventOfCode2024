package day4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day4Test {

    private Day4 testObject;

    @BeforeEach
    void setUp() throws IOException {
        testObject = new Day4("src/test/resources/day4/test_input.txt");
    }

    @Test
    void checkGridIsLoadedProperly() {
        String[][] grid = testObject.getGrid();

        assertEquals(10, grid.length);
        assertEquals(10, grid[0].length);
    }

    @Test
    void checkSearchKeywordForwardsOnASingleLineWithOneKeywordEntryToIdentify() throws IOException {
        Day4 testForward = new Day4("src/test/resources/day4/test_input_first_line_only.txt");
        String[][] grid = testForward.getGrid();

        Integer xmas = testForward.searchForwards(grid, "XMAS");

        assertEquals(1, xmas);
    }

    @Test
    void checkSearchKeywordBackwardsOnASingleLineWithOneKeywordEntryToIdentify() throws IOException {
        Day4 testBackward = new Day4("src/test/resources/day4/test_input_second_line_only.txt");
        String[][] grid = testBackward.getGrid();

        Integer xmas = testBackward.searchBackwards(grid, "XMAS");

        assertEquals(1, xmas);
    }

    @Test
    void checkSearchKeywordDownwardsOnASingleColWithOneKeywordEntryToIdentify() throws IOException {
        Day4 testDownwards = new Day4("src/test/resources/day4/test_input_last_col_only.txt");
        String[][] grid = testDownwards.getGrid();

        Integer xmas = testDownwards.searchDownwards(grid, "XMAS");

        assertEquals(9, xmas);
    }

    @Test
    void checkSearchKeywordUpwardsOnASingleColWithOneKeywordEntryToIdentify() throws IOException {
        Day4 testUpwards = new Day4("src/test/resources/day4/test_input_last_col_only.txt");
        String[][] grid = testUpwards.getGrid();

        Integer xmas = testUpwards.searchUpwards(grid, "XMAS");

        assertEquals(2, xmas);
    }

    @Test
    void checkSearchKeywordDiagonallyBackwardsAndDown() throws IOException {
        Day4 testDiagonallyBackDown = new Day4("src/test/resources/day4/test_input_diagonally_backwards_down.txt");
        String[][] grid = testDiagonallyBackDown.getGrid();

        Integer xmas = testDiagonallyBackDown.searchDiagonallyBackwardsDown(grid, "XMAS");

        assertEquals(5, xmas);
    }

    @Test
    void checkSearchKeywordDiagonallyBackwardsAndUp() throws IOException {
        Day4 testDiagonallyBackUp = new Day4("src/test/resources/day4/test_input_diagonally_backwards_up.txt");
        String[][] grid = testDiagonallyBackUp.getGrid();

        Integer xmas = testDiagonallyBackUp.searchDiagonallyBackwardsUp(grid, "XMAS");

        assertEquals(4, xmas);
    }

    @Test
    void checkSearchKeywordDiagonallyForwardsAndDown() throws IOException {
        Day4 testDiagonallyForwardsDown = new Day4("src/test/resources/day4/test_input_diagonally_forwards_down.txt");
        String[][] grid = testDiagonallyForwardsDown.getGrid();

        Integer xmas = testDiagonallyForwardsDown.searchDiagonallyForwardsDown(grid, "XMAS");

        assertEquals(4, xmas);
    }

    @Test
    void checkSearchKeywordDiagonallyForwardsAndUp() throws IOException {
        Day4 testDiagonallyForwardsUp = new Day4("src/test/resources/day4/test_input_diagonally_forwards_up.txt");
        String[][] grid = testDiagonallyForwardsUp.getGrid();

        Integer xmas = testDiagonallyForwardsUp.searchDiagonallyForwardsUp(grid, "XMAS");

        assertEquals(4, xmas);
    }

    @Test
    void checkSearchFindsAllInstancesOfKeywordInGrid() {
        String[][] grid = testObject.getGrid();

        List<Integer> xmas = testObject.search(grid, "XMAS");
        int total = testObject.getTotal(xmas);

        assertEquals(18, total);
    }

    @Test
    void checkSearchFindsAllInstancesOfKeywordInSampleInputGrid() throws IOException {
        Day4 sampleInput = new Day4("src/test/resources/day4/sample_input.txt");
        String[][] grid = sampleInput.getGrid();

        List<Integer> xmas = sampleInput.search(grid, "XMAS");
        int total = sampleInput.getTotal(xmas);

        assertEquals(11, total);
    }


    @Test
    void testFor_X_MAS_part2_firstPattern() throws IOException {
        Day4 sampleInput = new Day4("src/test/resources/day4/masTestInput1.txt");
        String[][] grid = sampleInput.getGrid();

        Integer result = sampleInput.searchOrthogonally(grid, "MAS");

        assertEquals(1, result);
    }

    @Test
    void testFor_X_MAS_part2_secondPattern() throws IOException {
        Day4 sampleInput = new Day4("src/test/resources/day4/masTestInput2.txt");
        String[][] grid = sampleInput.getGrid();

        Integer result = sampleInput.searchOrthogonally(grid, "MAS");

        assertEquals(1, result);
    }

    @Test
    void testFor_X_MAS_part2_thirdPattern() throws IOException {
        Day4 sampleInput = new Day4("src/test/resources/day4/masTestInput3.txt");
        String[][] grid = sampleInput.getGrid();

        Integer result = sampleInput.searchOrthogonally(grid, "MAS");

        assertEquals(1, result);
    }

    @Test
    void testFor_X_MAS_part2_fourthPattern() throws IOException {
        Day4 sampleInput = new Day4("src/test/resources/day4/masTestInput4.txt");
        String[][] grid = sampleInput.getGrid();

        Integer result = sampleInput.searchOrthogonally(grid, "MAS");

        assertEquals(1, result);
    }


    @Test
    void testFor_X_MAS_part2_TestData() throws IOException {
        Day4 sampleInput = new Day4("src/test/resources/day4/masTestInput.txt");
        String[][] grid = sampleInput.getGrid();

        Integer result = sampleInput.searchOrthogonally(grid, "MAS");

        assertEquals(9, result);
    }
}