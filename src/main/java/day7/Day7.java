package day7;

import utils.FileUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day7 {

    List<String> ops = List.of("*", "+");
    List<ResultDay7> results = new ArrayList<>();
    List<Map<Long, List<Integer>>> puzzles;

    public Day7(List<String> lines) {
        this.puzzles = convertLinesToPuzzleSet(lines);
    }

    public List<Map<Long, List<Integer>>> getPuzzles() {
        return puzzles;
    }

    public List<Map<Long, List<Integer>>> convertLinesToPuzzleSet(List<String> input) {
        List<Map<Long, List<Integer>>> puzzles = new ArrayList<>();
        for (String line : input) {
            String[] parts = line.split(":");
            Map<Long, List<Integer>> puzzle = new HashMap<>();
            for (int i = 0; i < parts[1].length(); i++) {
                String[] listOfInts = parts[1].trim().split(" ");
                for (int j = 0; j < listOfInts.length; j++) {
                    List<Integer> ints = Arrays.stream(listOfInts)
                            .map(e -> Integer.parseInt(e.trim()))
                            .collect(Collectors.toList());
                    puzzle.put(Long.parseLong(parts[0].trim()), ints);
                }
            }
            puzzles.add(puzzle);
        }
        return puzzles;
    }

    public Long run(List<Map<Long, List<Integer>>> puzzles) {
        for (Map<Long, List<Integer>> puzzle : puzzles) {
            Map.Entry<Long, List<Integer>> entry = puzzle.entrySet().stream().findFirst().get();

            List<String> startingOps = new ArrayList<>();
            List<String> endOps = new ArrayList<>();
            for (int i = 0; i < entry.getValue().size() - 1; i++) {
                startingOps.add("+");
                endOps.add("|");
            }

            ResultDay7 result = runCalc(entry.getKey(), entry.getValue(), startingOps, endOps);
            results.add(result);
        }

        Long sum = results.stream()
                .filter(result -> result.isPossible())
                .collect(Collectors.toList())
                .stream()
                .map(e -> e.getTargetValue())
                .mapToLong(i -> i)
                .sum();
        System.out.println("Sum of valid reports: " + sum);
        return sum;
    }

    // check if the result of combining inputs and ops results in key
    public ResultDay7 runCalc(Long target, List<Integer> inputs, List<String> currentOps, List<String> endOps) {
//        System.out.println("target: " + target + ", inputs: " + inputs + ", currentOps: " + currentOps);

        ResultDay7 result = null;
        while (!isPossible(target, currentOps, inputs) && !currentOps.equals(endOps)) {
            currentOps = nextOps(currentOps);
        }

        if (isPossible(target, currentOps, inputs)) {
            result = new ResultDay7(true, currentOps, inputs, target);
            System.out.println("SUCCESS target: " + target + ", inputs: " + inputs + ", success with: " + currentOps);
        } else if (currentOps.equals(endOps)) {
            result = new ResultDay7(false, List.of(), inputs, target);
            System.out.println("FAIL target: " + target + ", inputs: " + inputs);
        }
        return result;
    }

    public boolean isPossible(Long target, List<String> ops, List<Integer> inputs) {
        Long calcResult = 0l;

        if (inputs.size() > 1) {
            for (int i = 1; i < inputs.size(); i++) {
                switch (ops.get(i - 1)) {
                    case "*":
                        if (i == 1) {
                            calcResult = Long.valueOf(inputs.get(0)) * Long.valueOf(inputs.get(1));
                        } else {
                            calcResult = calcResult * inputs.get(i);
                        }
                        break;
                    case "+":
                        if (i == 1) {
                            calcResult = Long.valueOf(inputs.get(0)) + Long.valueOf(inputs.get(1));
                        } else {
                            calcResult = calcResult + inputs.get(i);
                        }
                        break;
                    case "|":
                        if (i == 1) {
                            calcResult = Long.valueOf(inputs.get(0).toString() + inputs.get(1).toString());
                        } else {
                            calcResult = Long.valueOf(calcResult.toString() + inputs.get(i).toString());
                        }
                        break;
                }
            }
        } else {
            calcResult = Long.valueOf(inputs.get(0));
        }
        return calcResult.equals(target);
    }

    public List<String> removeBars(List<String> ops) {
        return ops.stream().filter(e -> !e.equals("|")).collect(Collectors.toList());
    }

    // run binary count calc - 0000 -> 0001 -> 0010 -> 0011 -> 0100 -> 0101 -> 0110 -> 0111 -> 1000 - then replace 1 with * adn 0 with +
    public List<String> nextOps(List<String> operations) {
        String currentPattern = convertToBinary(String.join("", operations));
        String nextPattern = getNextPattern(currentPattern);
        String nextOps = convertToString(nextPattern);
        return Arrays.asList(nextOps.split(""));
    }

    public static String getNextPattern(String currentPattern) {
        int currentNumber = Integer.parseInt(currentPattern, 3); // base 2 (binary)
        int nextNumber = currentNumber + 1;
        String numberBase3 = Integer.toString(nextNumber,3);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < currentPattern.length() - numberBase3.length(); i++) {
            sb.append("0");
        }
        sb.append(numberBase3);
        return sb.toString();
    }

    public static String convertToBinary(String pattern) {
        // Replace '-' with '0' and '+' with '1' in the string
        StringBuilder sb = new StringBuilder();
        for (char ch : pattern.toCharArray()) {
            if (ch == '+') {
                sb.append('0');
            } else if (ch == '*') {
                sb.append('1');
            } else if (ch == '|') {
                sb.append('2');
            } else {
                throw new IllegalArgumentException("Invalid character in pattern: " + ch);
            }
        }
        return sb.toString();
    }

    /**
     * here, the binary representation will have a 1 in the place of each '*'
     * everything else should be a '+' (represented by a 0);
     */
    public static String convertToString(String binary) {
        // Replace '-' with '0' and '+' with '1' in the string
        StringBuilder operatorString = new StringBuilder();
        for (int i = 0; i < binary.length(); i++) {
            char ch = '0';
            if (i < binary.toCharArray().length) {
                ch = binary.toCharArray()[i];
            }
            if (ch == '0') {
                operatorString.append('+');
            } else if (ch == '1') {
                operatorString.append('*');
            } else if (ch == '2') {
                operatorString.append('|');
            } else {
                throw new IllegalArgumentException("Invalid character in pattern: " + ch);
            }
        }
        return operatorString.toString();
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = FileUtils.getFileAsList("src/main/resources/day7/input.txt");

        Day7 day7 = new Day7(lines);
        day7.run(day7.convertLinesToPuzzleSet(lines));

    }
}
