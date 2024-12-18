package day11;

import utils.FileUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day11Part2 {

    private final List<String> puzzles;

    private long count = 0;
    private List<Long> stones;

    private Map<Long, Long> memory;


    public Day11Part2(List<String> lines) {
        this.puzzles = List.of(lines.get(0).split(" "));
        this.stones = List.of(lines.get(0).split(" "))
                .stream()
                .map(e -> Long.parseLong(e))
                .collect(Collectors.toList());
        memory = new HashMap<>();
    }

    public List<Long> getPuzzles() {
        return stones;
    }

    public Map<Long, Long> getMemoryCountsForEachStone(Map<Long, Long> originStones) {
        Map<Long, Long> counts = new HashMap<>();
        for (Long oStone : originStones.keySet()) {

            List<Long> resultantStones = applyRuleToStone(oStone);

            for (Long rStone : resultantStones) {
                if (counts.containsKey(rStone)) {
                    counts.put(rStone, counts.get(rStone) + 1);
                } else {
                    counts.put(rStone, 1l);
                }
            }
        }

        return counts;
    }

    public Long applyRulesToEachStoneUsingMemory(Map<Long, Long> stoneCounts, int memorySize, int blinks) {

        Map<Long, Long> counts = new HashMap<>();
        counts = getCounMemorisedCounts(stoneCounts, memorySize, counts);

        Map<Long, Long> nonMemorisedStones = stoneCounts;
        nonMemorisedStones = getCountsForNonMemorisedStones(memorySize, blinks, counts, nonMemorisedStones);

        for (Map.Entry<Long, Long> nmStones : nonMemorisedStones.entrySet()) {
            if (counts.containsKey(nmStones.getKey())) {
                System.out.println("Stone: " + nmStones.getKey() + ", Count: " + nmStones.getValue() + ", Total: " + counts.get(nmStones.getKey()) + nmStones.getValue());
                counts.put(nmStones.getKey(), counts.get(nmStones.getKey()) + nmStones.getValue());
            } else {
                counts.put(nmStones.getKey(), nmStones.getValue());
            }
        }

        // for the remaining iterations, use brute force to calculate
        // the number of runes for each stone we don't have a cached count for
        for (Long stone : stoneCounts.keySet()) {

            // use cached counts if available
            if (memory.containsKey(stone)) {
                if (counts.containsKey(stone)) {
                    counts.put(stone, counts.get(stone) + sumCountsFor(stoneCounts, stone));
                } else {
                    counts.put(stone, sumCounts(memory));
                }
            } else {
                Map<Long, Long> finalStones = stoneCounts;
                for (int i = 0; i < blinks - memorySize; i++) {
                    System.out.println("Loop: " + i + ", Stone count: " + Long.valueOf(counts.entrySet().stream().map(e -> e.getValue()).mapToLong(e -> e).sum()));
                    nonMemorisedStones = getMemoryCountsForEachStone(nonMemorisedStones);
                }
            }
        }

        return sumCounts(counts);
    }

    private Map<Long, Long> getCountsForNonMemorisedStones(int memorySize, int blinks, Map<Long, Long> counts, Map<Long, Long> newStones) {
        for (int i = 0; i < blinks - memorySize; i++) {
            System.out.println("Loop: " + i + ", Stone count: " + Long.valueOf(counts.entrySet().stream().map(e -> e.getValue()).mapToLong(e -> e).sum()));
            newStones = getMemoryCountsForEachStone(newStones);
        }
        printMetrics();
        return newStones;
    }

    private Map<Long, Long> getCounMemorisedCounts(Map<Long, Long> stoneCounts, int memorySize, Map<Long, Long> counts) {
        for (Long stone : stoneCounts.keySet()) {
            counts = stoneCounts;
            for (int i = 0; i < memorySize; i++) {
                System.out.println("Loop To build memory: " + i  + ", for Stone: " + stone + ", counts: " + sumCounts(counts));
                counts = getMemoryCountsForEachStone(counts);
            }
            printMetrics();
            memory.put(stone, sumCounts(counts));
        }
        counts.clear();
        return counts;
    }

    private void printMetrics() {
        System.out.println("Heap Size: " + (Runtime.getRuntime().totalMemory() / (1024 * 1024 * 1024)) + "G, "
                + "heapMaxSize: " + (Runtime.getRuntime().maxMemory() / (1024 * 1024 * 1024)) + "G, "
                + "heapFreeSize: " + (Runtime.getRuntime().freeMemory() / (1024 * 1024 * 1024)) + "G");
    }

    private Long sumCounts(Map<Long, Long> counts) {
        return counts.entrySet().stream().map(e -> e.getValue()).mapToLong(e -> e).sum();
    }

    private Long sumCountsFor(Map<Long, Long> counts, Long stoneValue) {
        return counts.entrySet().stream().filter(e -> e.getKey().equals(stoneValue)).map(e -> e.getValue()).mapToLong(e -> e).sum();
    }

    private Map<Long, Long> getStonesAsMapCount(List<Long> replace) {
        Map<Long, Long> mapResults = new HashMap<>();
        for (int i = 0; i < replace.size(); i++) {
            if (mapResults.containsKey(replace.get(i))) {
                mapResults.put(replace.get(i), mapResults.get(replace.get(i)) + 1);
            } else {
                mapResults.put(replace.get(i), 1l);
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
        Day11Part2 day11 = new Day11Part2(lines);

        System.out.println(day11.applyRulesToEachStoneUsingMemory(day11.getStonesAsMapCount(day11.getPuzzles()),
                35,
                75));
        System.out.println("Execution time is " + (System.currentTimeMillis() - startTime) + " ms");
    }
}
