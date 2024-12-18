package day11;

import utils.FileUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day11 {

    private final List<String> puzzles;

    private long count = 0;
    private List<Long> stones;


    public Day11(List<String> lines) {
        this.puzzles = List.of(lines.get(0).split(" "));
        this.stones = List.of(lines.get(0).split(" "))
                .stream()
                .map(e -> Long.parseLong(e))
                .collect(Collectors.toList());
    }

    public List<Long> getPuzzles() {
        return stones;
    }

    public Map<Long, Integer> applyRulesToEachStone(List<Long> replace) {
        Map<Long, Integer> counts = new HashMap<>();
        for (int i = 0; i < replace.size(); i++) {
            List<Long> newStones = applyRuleToStone(replace.get(i));
            for (Long stone : newStones) {
                if (counts.containsKey(stone)) {
                    counts.put(stone, counts.get(stone) + 1);
                } else {
                    counts.put(stone, 1);
                }
            }
        }
        return counts;
    }

    public Map<Long, Integer> applyRulesToEachStone(Map<Long, Integer> replace) {
        Map<Long, Integer> counts = new HashMap<>();
        for (Long stone : replace.keySet()) {

            for (int i = 0; i < replace.get(stone); i++) {
                List<Long> newStones = applyRuleToStone(stone);

                for (Long s : newStones) {
                    if (counts.containsKey(s)) {
                        counts.put(s, counts.get(s) + 1);
                    } else {
                        counts.put(s, 1);
                    }
                }
            }
        }


        return counts;
    }

    public Long applyRulesNTimes(List<Long> replace, int n) {
        Map<Long, Integer> mapResults = getStonesAsMapCount(replace);
        Map<Long, Integer> subtotals = getStonesAsMapCount(replace);
        for (int i = 0; i < n; i++) {


            System.out.println("Loop: " + i + ", Stone count: " + Long.valueOf(subtotals.entrySet().stream().map(e -> e.getValue()).mapToLong(e -> e).sum()));
            System.out.println("Heap Size: " + (((Runtime.getRuntime().totalMemory() / 1024) / 1024) / 1024) + "G, "
                    + "heapMaxSize: " + (((Runtime.getRuntime().maxMemory() / 1024) / 1024)) / 1024 + "G, "
                    + "heapFreeSize: " + (((Runtime.getRuntime().freeMemory() / 1024) / 1024) / 1024) + "G");

            subtotals = applyRulesToEachStone(subtotals);
        }

        return Long.valueOf(subtotals.entrySet().stream().map(e -> e.getValue()).mapToLong(e -> e).sum());
    }

    private Map<Long, Integer> getStonesAsMapCount(List<Long> replace) {
        Map<Long, Integer> mapResults = new HashMap<>();
        for (int i = 0; i < replace.size(); i++) {
            if (mapResults.containsKey(replace.get(i))) {
                mapResults.put(replace.get(i), mapResults.get(replace.get(i)) + 1);
            } else {
                mapResults.put(replace.get(i), 1);
            }
        }
        return mapResults;
    }

    private List<Long> applyRuleToStone(Long s) {
        if (s.equals(0l)) {
            return List.of(1l);
        } else if (String.valueOf(s).length() % 2 == 0) {
            String s1 = String.valueOf(s);
            Long left = Long.parseLong(s1.substring(0, s1.length() / 2));
            Long right = Long.parseLong(s1.substring(s1.length() / 2));
            return List.of(left, right);
        } else {
            return List.of(s * 2024);
        }
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        List<String> lines = FileUtils.getFileAsList("src/main/resources/day11/input.txt");
        Day11 day11 = new Day11(lines);
        System.out.println(day11.applyRulesNTimes(List.of(25l), 75));
//        System.out.println(day11.applyRulesNTimes(day11.getPuzzles(), 75));
        System.out.println("Execution time is " + (System.currentTimeMillis() - startTime) + " ms");
    }
}
