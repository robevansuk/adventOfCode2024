package day5;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day5Test {

            private static final List<String> ORDER_RULES = List.of("47|53",
            "97|13",
            "97|61",
            "97|47",
            "75|29",
            "61|13",
            "75|53",
            "29|13",
            "97|29",
            "53|29",
            "61|53",
            "97|53",
            "61|29",
            "47|13",
            "75|47",
            "97|75",
            "47|61",
            "75|61",
            "47|29",
            "75|13",
            "53|13");

            private static final List<String> UPDATES = List.of("75,47,61,53,29",
            "97,61,53,29,13",
            "75,29,13",
            "75,97,47,61,53",
            "61,13,29",
            "97,13,75,29,47");


    @Test
    void testFirstUpdateIsInvalidAgainstTheSampleRules() {
        Day5 day5 = new Day5(ORDER_RULES, UPDATES);

        List<Integer> update = day5.getUpdates().get(0);
        boolean result1 = day5.checkRulesForRightNumbers(day5.getRules(), 0, update);
        boolean result2 = day5.checkRulesForRightNumbers(day5.getRules(), 1, update);
        boolean result3 = day5.checkRulesForRightNumbers(day5.getRules(), 2, update);
        boolean result4 = day5.checkRulesForRightNumbers(day5.getRules(), 3, update);

        assertEquals(true, result1);
        assertEquals(true, result2);
        assertEquals(true, result3);
        assertEquals(true, result4);
    }

    @Test
    void testSecondUpdateIsValidAgainstTheSampleRules() {
        Day5 day5 = new Day5(ORDER_RULES, UPDATES);

        List<Integer> update = day5.getUpdates().get(1);
        boolean result1 = day5.checkRulesForRightNumbers(day5.getRules(), 0, update);
        boolean result2 = day5.checkRulesForRightNumbers(day5.getRules(), 1, update);
        boolean result3 = day5.checkRulesForRightNumbers(day5.getRules(), 2, update);
        boolean result4 = day5.checkRulesForRightNumbers(day5.getRules(), 3, update);

        assertEquals(true, result1);
        assertEquals(true, result2);
        assertEquals(true, result3);
        assertEquals(true, result4);
    }

    @Test
    void testThirdUpdateIsValidAgainstTheSampleRules() {
        Day5 day5 = new Day5(ORDER_RULES, UPDATES);

        List<Integer> update = day5.getUpdates().get(2);
        boolean result1 = day5.checkRulesForRightNumbers(day5.getRules(), 0, update);
        boolean result2 = day5.checkRulesForRightNumbers(day5.getRules(), 1, update);
        boolean result3 = day5.checkRulesForRightNumbers(day5.getRules(), 2, update);

        assertEquals(true, result1);
        assertEquals(true, result2);
        assertEquals(true, result3);
    }

    @Test
    void testFourthUpdateIsInvalidAgainstTheSampleRules() {
        Day5 day5 = new Day5(ORDER_RULES, UPDATES);

        List<Integer> update = day5.getUpdates().get(3);
        boolean result1 = day5.checkRulesForRightNumbers(day5.getRules(), 0, update);

        assertEquals(false, result1);
    }

    @Test
    void testFifthUpdateIsInvalidAgainstTheSampleRules() {
        Day5 day5 = new Day5(ORDER_RULES, UPDATES);

        List<Integer> update = day5.getUpdates().get(4);
        boolean result1 = day5.checkRulesForRightNumbers(day5.getRules(), 0, update);
        boolean result2 = day5.checkRulesForRightNumbers(day5.getRules(), 1, update);

        assertEquals(true, result1);
        assertEquals(false, result2);
    }

    @Test
    void testValidInputsAreExtractedFromSampleDataUsingSampleRules() {
        Day5 day5 = new Day5(ORDER_RULES, UPDATES);

        List<List<Integer>> isValidUpdates = day5.getValidUpdates(day5.getRules(), day5.getUpdates());

        assertEquals(3, isValidUpdates.size());
    }

    @Test
    void testApplyingRules() {
        Day5 day5 = new Day5(ORDER_RULES, UPDATES);

        List<List<Integer>> isValidUpdates = day5.getValidUpdates(day5.getRules(), day5.getUpdates());
        int result = day5.totalCentreValues(isValidUpdates);


        assertEquals(143, result);
    }


    @Test
    void testGettingInvalidInputsCorrectionForSampleInput2() {
        Day5 day5 = new Day5(ORDER_RULES, UPDATES);

        List<List<Integer>> invalidUpdates = day5.getInvalidUpdates(day5.getRules(), day5.getUpdates());
        List<List<Integer>> correctedUpdates = day5.getCorrectedUpdates(day5.getRules(), List.of(invalidUpdates.get(1)));

        assertEquals(List.of(List.of(61, 29, 13)), correctedUpdates);
    }

    @Test
    void testGettingInvalidInputsCorrectionWorksForSampleInput3() {
        Day5 day5 = new Day5(ORDER_RULES, UPDATES);

        List<List<Integer>> invalidUpdates = day5.getInvalidUpdates(day5.getRules(), day5.getUpdates());
        List<List<Integer>> correctedUpdates = day5.getCorrectedUpdates(day5.getRules(), List.of(invalidUpdates.get(2)));

        assertEquals(List.of(List.of(97, 75, 47, 29, 13)), correctedUpdates);
    }

    @Test
    void testGettingInvalidInputsWorks() {
        Day5 day5 = new Day5(ORDER_RULES, UPDATES);

        List<List<Integer>> invalidUpdates = day5.getInvalidUpdates(day5.getRules(), day5.getUpdates());
        List<List<Integer>> correctedUpdates = day5.getCorrectedUpdates(day5.getRules(), invalidUpdates);
        int result = day5.totalCentreValues(correctedUpdates);


        assertEquals(123, result);
    }
}
