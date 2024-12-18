package day11;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Test {

    @Test
    void runFirstTestAgainstSampleData() {
        List<String> strings = List.of("0 1 10 99 999");
        Day11 day11 = new Day11(strings);

        Map<Long, Integer> expectedResult = Map.of(1l, 2,
                2024l, 1,
                0l, 1,
                9l, 2,
                2021976l, 1);
        assertEquals(expectedResult, day11.applyRulesToEachStone(day11.getPuzzles()));
    }

    @Test
    void run1BlinkTest() {
        List<String> strings = List.of("125 17");
        Day11 day11 = new Day11(strings);

//        assertEquals("253000 1 7", day11.recursiveCounter(day11.getPuzzles(), 1));
//        assertEquals("253 0 2024 14168", day11.recursiveCounter(day11.getPuzzles(), 2));
//        assertEquals("512072 1 20 24 28676032", day11.recursiveCounter(day11.getPuzzles(), 3));
//        assertEquals("512 72 2024 2 0 2 4 2867 6032", day11.recursiveCounter(day11.getPuzzles(), 4));
//        assertEquals("1036288 7 2 20 24 4048 1 4048 8096 28 67 60 32", day11.recursiveCounter(day11.getPuzzles(), 5));
//        assertEquals("2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2", day11.recursiveCounter(day11.getPuzzles(), 6));

        assertEquals(3l, day11.applyRulesNTimes(day11.getPuzzles(), 1));
    }

    @Test
    void run2BlinkTest() {
        List<String> strings = List.of("125 17");
        Day11 day11 = new Day11(strings);

        assertEquals(4l, day11.applyRulesNTimes(day11.getPuzzles(),2));
    }

    @Test
    void run3BlinkTest() {
        List<String> strings = List.of("125 17");
        Day11 day11 = new Day11(strings);

        assertEquals(5l, day11.applyRulesNTimes(day11.getPuzzles(),3));
    }

    @Test
    void run4BlinkTest() {
        List<String> strings = List.of("125 17");
        Day11 day11 = new Day11(strings);

        assertEquals(9l, day11.applyRulesNTimes(day11.getPuzzles(),4));
    }

    @Test
    void run5BlinkTest() {
        List<String> strings = List.of("125 17");
        Day11 day11 = new Day11(strings);

        assertEquals(13l, day11.applyRulesNTimes(day11.getPuzzles(),5));

    }

    @Test
    void run6BlinkTest() {
        List<String> strings = List.of("125 17");
        Day11 day11 = new Day11(strings);

        assertEquals(22l, day11.applyRulesNTimes(day11.getPuzzles(),6));

    }
}
