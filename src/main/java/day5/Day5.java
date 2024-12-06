package day5;

import org.apache.commons.lang3.StringUtils;
import utils.FileUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Day5 {

    private Map<Integer, List<Integer>> rules;
    private List<List<Integer>> updates;

    public Day5(List<String> rules, List<String> updates) {
        this.rules = extractRules(rules);
        this.updates = extractUpdates(updates);
    }

    private List<List<Integer>> extractUpdates(List<String> updates) {
        return updates.stream().map(update -> Arrays.asList(update.split(","))
                        .stream()
                        .filter(StringUtils::isNotBlank)
                        .map(Integer::valueOf)
                        .collect(toList()))
                .collect(Collectors.toList());
    }

    private Map<Integer, List<Integer>> extractRules(List<String> rules) {
        Map<Integer, List<Integer>> result = new HashMap<>();
        for (String rule : rules) {
            String[] split = rule.split("\\|");
            int key = Integer.parseInt(split[0]);
            int value = Integer.parseInt(split[1]);
            if (result.containsKey(key)) {
                result.get(key).add(value);
            } else {
                result.put(key, new ArrayList<>(List.of(value)));
            }
        }
        return result;
    }

    public Map<Integer, List<Integer>> getRules() {
        return rules;
    }

    public List<List<Integer>> getUpdates() {
        return updates;
    }

    public int totalCentreValues(List<List<Integer>> updates) {
        int total = 0;
        for (List<Integer> update : updates) {
            int middleValue = ((update.size()-1)/2);
            total += update.get(middleValue);
        }
        return total;
    }

    public List<List<Integer>> getValidUpdates(Map<Integer, List<Integer>> rules, List<List<Integer>> updates) {
        List<List<Integer>> validUpdates = new ArrayList<>();

        for(List<Integer> update : updates) {
            if (isValidUpdate(rules, update)) {
                validUpdates.add(update);
            }
        }
        return validUpdates;
    }

    public List<List<Integer>> getInvalidUpdates(Map<Integer, List<Integer>> rules, List<List<Integer>> updates) {
        List<List<Integer>> invalidUpdates = new ArrayList<>();

        for(List<Integer> update : updates) {
            if (!isValidUpdate(rules, update)) {
                invalidUpdates.add(update);
            }
        }
        return invalidUpdates;
    }

    private boolean isValidUpdate(Map<Integer, List<Integer>> rules, List<Integer> update) {
        for (int i = 0; i < update.size()-1; i++) {
            boolean isValidRight = checkRulesForRightNumbers(rules, i, update);
            if (!isValidRight) {
                return false;
            }
        }
        return true;
    }

    public boolean checkRulesForRightNumbers(Map<Integer, List<Integer>> rules, int i, List<Integer> update) {
        Integer elementToLookupRulesFor = update.get(i);
        List<Integer> theRules = rules.get(elementToLookupRulesFor);
        for (int m = i+1; m < update.size(); m++) {
            Integer nextValue = update.get(m);
            if (theRules==null || !theRules.contains(nextValue)) {
                return false;
            }
        }

        return true;
    }



    private static int readInRules(List<String> lines, List<String> rules, int i) {
        while (!StringUtils.isBlank(lines.get(i))) {
            rules.add(lines.get(i));
            i++;
        }
        return i++;
    }

    private static int readInUpdates(List<String> lines, List<String> updates, int i) {
        while (i < lines.size()) {
            if (isNotBlank(lines.get(i))) {
                updates.add(lines.get(i));
            }
            i++;
        }
        return i;
    }


    public static void main(String[] args) throws IOException {
        List<String> lines = FileUtils.getFileAsList("src/main/resources/day5/input.txt");
        List<String> rules = new ArrayList<>();
        List<String> updates = new ArrayList<>();

        int i=0;
        i = readInRules(lines, rules, i);
        readInUpdates(lines, updates, i);

        Day5 day5 = new Day5(rules, updates);

        List<List<Integer>> validUpdates = day5.getValidUpdates(day5.getRules(), day5.getUpdates());

        int result = day5.totalCentreValues(validUpdates);
        System.out.println("Total Of Middle Values: " + result);

        List<List<Integer>> invalidUpdates = day5.getInvalidUpdates(day5.getRules(), day5.getUpdates());
        List<List<Integer>> correctedUpdates = day5.getCorrectedUpdates(day5.getRules(), invalidUpdates);
        int correctedReportsValue = day5.totalCentreValues(correctedUpdates);

        System.out.println("Total Of Middle Values For Corrected Reports: " + correctedReportsValue);
    }

    public List<List<Integer>> getCorrectedUpdates(Map<Integer, List<Integer>> rules, List<List<Integer>> updates) {
        for (int x=0; x< updates.size(); x++) {
            List<Integer> update = updates.get(x);
            // check each number in the update
            for (int i = 0; i < update.size()-1; i++) {
                boolean result = checkRulesForRightNumbers(rules, i, update);
                int tempStore = i;
                int next = 0;
                while (!result) {
                    next++;
                    swap(tempStore, tempStore+next, update);
                    result = checkRulesForRightNumbers(rules, i, update);
                    if (!result) {
                        // swap back and try swapping to next location on next iteration
                        swap(tempStore, tempStore+next, update);
                    }
                }

            }
        }
        return updates;
    }

    private List<Integer> swap(int i, int j, List<Integer> update) {
        int iPlusOneElem = update.get(j);
        int ithElem = update.get(i);
        update.set(i, iPlusOneElem);
        update.set(j, ithElem);
        return update;
    }

}
