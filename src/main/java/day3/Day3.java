package day3;

import utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    private static final String INPUT = "src/main/resources/day3/input.txt";
    private final List<String> data;

    public Day3(String input) throws IOException {
        data = FileUtils.getFileAsList(input);
    }


    public static void main(String[] args) {
        try {
            Day3 day3 = new Day3(INPUT);
            List<String> validInstructions = day3.getValidInstructions(day3.getData());
            long result = day3.compute(validInstructions);
            System.out.println("Result: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getData() {
        return data;
    }

    public List<String> getValidInstructions(List<String> data) {
        List<String> validInstructions = new ArrayList<>();
        Pattern pattern = Pattern.compile("mul\\([0-9]+,[0-9]+\\)|do", Pattern.CASE_INSENSITIVE);
        boolean addNext = true;

        for (int i=0; i < data.size(); i++) {
            Matcher matcher = pattern.matcher(data.get(i));

            while (matcher.find()) {
                String result = extractNextInstruction(data, matcher, validInstructions, i);

                if (result.startsWith("do()")) {
                    addNext = true;
                } else if (result.startsWith("don't()")) {
                    addNext = false;
                } else if (result.split(",").length == 2 && addNext) {
                    validInstructions.add(result);
                }

            }
        }
        return validInstructions;
    }

    private static String extractNextInstruction(List<String> data, Matcher matcher, List<String> validInstructions, int i) {
        matcher.group();
        int start = matcher.start();
        int end = matcher.end();

        if (data.get(i).substring(start, end).equals("do")) {
            if (end + 5 <= data.get(i).length()) {
                if (data.get(i).substring(start, end+5).equals("don't()")) {
                    return "don't()";
                } else if (data.get(i).substring(start, end+2).equals("do()")) {
                    return "do()";
                }
            }
        }
        if (data.get(i).substring(start, end).startsWith("mul")) {
            String result =  data.get(i).substring(start + "mul(".length(), end - 1);
            return result;
        }
        return "";
    }

    public long compute(List<String> validInstructions) {
        long total = 0;
        for (int i=0; i<validInstructions.size(); i++) {
            String[] values = validInstructions.get(i).split(",");
            long x = Long.parseLong(values[0]);
            long y = Long.parseLong(values[1]);
            total += x * y;
        }
        return total;
    }
}
