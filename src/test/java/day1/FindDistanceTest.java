package day1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FindDistanceTest {

    private static final String TEST_INPUT = "src/test/resources/day1/input.txt";

    private FindDistance testObject;

    @BeforeEach
    void setUp() throws IOException {
        testObject = new FindDistance(TEST_INPUT);
    }

    @Test
    void testExtractList0() {
        List<Integer> list1 = List.of(64256, 46941, 47111);

        assertEquals(list1, testObject.getList(0));
    }


    @Test
    void testExtractList1() {
        List<Integer> list1 = List.of(78813, 56838, 50531);

        assertEquals(list1, testObject.getList(1));
    }

    @Test
    void testSortDesc() {
        List<Integer> list = List.of(78813, 56838, 50531);
        List<Integer> result = List.of(50531, 56838, 78813);

        assertEquals(result, testObject.sortDesc(list));
    }

    @Test
    void testCalculateDistances() {
        List<Integer> list1 = testObject.sortDesc(List.of(64256, 46941, 47111));
        List<Integer> list2 = testObject.sortDesc(List.of(78813, 56838, 50531));

        Long result = Long.valueOf(
                Math.abs(46941 - 50531) +
                Math.abs(47111 - 56838) +
                Math.abs(64256 - 78813));

//        27874

        assertEquals(result, testObject.calculateTotalDistance(list1, list2));
    }

    @Test
    void calculateSimilarityScore() {
        List<Integer> list1 = List.of(1, 2, 3, 4, 5, 6);
        List<Integer> list2 =List.of(3, 3, 4, 2, 1, 1);

        Long result = Long.valueOf((1 * 2) + (2 * 1) + (3 * 2) + (4 * 1) + (5 * 0) + (6 * 0));

        assertEquals(result, testObject.getSimilarityScore(list1, list2));
    }
}