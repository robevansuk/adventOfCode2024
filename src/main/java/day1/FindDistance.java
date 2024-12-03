package day1;

import utils.FileUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FindDistance {

    private static final String INPUT = "src/main/resources/day1/input.txt";

    List<Integer> list1;
    List<Integer> list2;

    Long distances;
    Long similarityScore;

    public FindDistance(String input) throws IOException {
        List<String> lines = FileUtils.getFileAsList(input);

        list1 = sortDesc(extractList(lines, 0));
        list2 = sortDesc(extractList(lines, 1));

        distances = calculateTotalDistance(list1, list2);
        similarityScore = getSimilarityScore(list1, list2);
    }

    public Long getSimilarityScore(List<Integer> list1, List<Integer> list2) {

        similarityScore = 0L;

        Map<Integer, Integer> counts = new HashMap<>();
        for (int i = 0; i < list2.size(); i++) {
            int currentValue = list2.get(i);
            if (counts.get(currentValue) == null) {
                counts.put(currentValue, 1);
            } else {
                counts.put(currentValue, counts.get(currentValue) + 1);
            }
        }

        for (int i = 0; i < list1.size(); i++) {
            int currentValue = list1.get(i);
            if (counts.get(currentValue) != null) {
                similarityScore += currentValue * counts.get(currentValue);
            }
        }

        return similarityScore;

    }

    public Long calculateTotalDistance(List<Integer> list1, List<Integer> list2) {
        List<Integer> distances = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            distances.add(Math.abs(list1.get(i) - list2.get(i)));
        }

        long total = 0;
        for (int i = 0; i < distances.size(); i++) {
            total += distances.get(i);
        }
        return total;
    }


    private List<Integer> extractList(List<String> lines, int col) {
        List<Integer> list = lines.stream()
                .map(line -> line.split("   ")[col])
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        return list;
    }

    public List<Integer> getList(int colId) {
        if (colId == 0) {
            return list1;
        } else if (colId == 1) {
            return list2;
        }
        throw new RuntimeException("List does not exist");
    }

    public List<Integer> sortDesc(List<Integer> list) {
        return list.stream()
                .sorted(Comparator.comparingInt(a -> a))
                .collect(Collectors.toList());
    }


    public static void main(String[] args) {
        try {
            FindDistance findDistance = new FindDistance(INPUT);
            List<Integer> list1 = findDistance.getList(0);
            List<Integer> list2 = findDistance.getList(1);
            Long totalDistance = findDistance.calculateTotalDistance(list1, list2);
            System.out.println("Total distance: " + totalDistance);
            System.out.println("Similarity score: " + findDistance.getSimilarityScore(list1, list2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
