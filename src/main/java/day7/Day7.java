package day7;

import utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day7 {

    List<String> ops = List.of("*", "+");
    List<ResultDay7> results = new ArrayList<>();

    public Day7() {

    }

    public void run(List<Map<Integer, List<Integer>>> puzzles) {
        for (Map<Integer, List<Integer>> puzzle : puzzles) {
            Map.Entry<Integer, List<Integer>> entry = puzzle.entrySet().stream().findFirst().get();
            isFirstTry = true;
            ResultDay7 result = runCalc(entry.getKey(), entry.getValue(), getOpsToTry(entry.getValue(), null));
            results.add(result);
        }
    }

    // check if the result of combining inputs and ops results in key
    public ResultDay7 runCalc(Integer key, List<Integer> inputs, String ops) {
        ResultDay7 result = new ResultDay7(isPossible(Arrays.asList(ops.split("\u0000")), inputs, key), Arrays.asList(ops.split("\u0000")), inputs, key);
        return result;
    }

    private boolean isPossible(List<String> ops, List<Integer> inputs, Integer key) {
        Long calcResult = 0l;
        for (int i=0; i<inputs.size()-1; i++) {
            switch(ops.get(i)) {
               case "*":
                   if(i==0) {
                       calcResult = Long.valueOf(inputs.get(i));
                   } else {
                       calcResult = calcResult * inputs.get(i);
                   }
                   break;
               case "+":
                   if (i == 0 ) {
                        calcResult = Long.valueOf(inputs.get(i));
                   } else {
                       calcResult = calcResult + inputs.get(i);
                   }
                   break;
           }
        }
    }

    // run binary count calc - 0000 -> 0001 -> 0010 -> 0011 -> 0100 -> 0101 -> 0110 -> 0111 -> 1000 - then replace 1 with * adn 0 with +
    public String getOpsToTry(List<Integer> value, String lastValueTried) {
        if (lastValueTried == null) {
            return "++++";
        }
        if (lastValueTried.equals("****")) {
            return "****";
        }

    }

    public static void main(String[] args) throws IOException {
        List<String> lines = FileUtils.getFileAsList("src/main/resources/day7/input.txt");
        List<Map<Integer, List<Integer>>> puzzles = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(":");
            Map<Integer, List<Integer>> puzzle = new HashMap<>();
            for (int i=0; i < parts[1].length(); i++) {
                String[] listOfInts = parts[1].split(" ");
                for (int j=0; j < listOfInts.length; j++) {
                    List<Integer> ints = Arrays.stream(listOfInts)
                            .map(e -> Integer.parseInt(e.trim()))
                            .collect(Collectors.toList());
                    puzzle.put(j, ints);
                }
            }
        }
        Day7 day7 = new Day7();
        day7.run(puzzles);

    }
}
