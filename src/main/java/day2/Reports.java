package day2;

import utils.FileUtils;

import java.io.IOException;
import java.util.List;

public class Reports {

    private static final String INPUT = "src/main/resources/day2/input.txt";
    private final List<String> reports;

    public Reports(String input) throws IOException {
        reports = FileUtils.getFileAsList(input);
    }

    private int countSafeReports(List<String> reports) {
        int safeReports = 0;
        for (String report : reports) {
            boolean isSafeReport = isSafeReport(report, true);
            if (isSafeReport) {
                safeReports++;
                System.out.println("SAFE ----------------------: " + report);
            } else {
                System.out.println("Unsafe: " + report);
            }
        }
        return safeReports;
    }

    public boolean isSafeReport(String report, boolean isFirstTime) {
        String[] reportParts = report.split(" ");
        boolean isSafeReport = true;

        boolean isAscending = isDecreasing(getInt(reportParts[0]), getInt(reportParts[1]));
        boolean isDescending = isIncreasing(getInt(reportParts[0]), getInt(reportParts[1]));

        for (int i = 0;  i < reportParts.length-1; i++) {
            int int1 = getInt(reportParts[i]);
            int int2 = getInt(reportParts[i + 1]);

            if (int1 == int2) {
                isSafeReport = false;
                break;
            } else if (isAscending) {

                if (isIncreasing(int1, int2) || Math.abs(int1 - int2) > 3) {
                    isSafeReport = false;
                    break;
                }
            } else if (isDescending) {
                if (isDecreasing(int1 - int2, 0) || Math.abs(int1 - int2) > 3) {
                    isSafeReport = false;
                    break;
                }
            }
        }

        // Problem Dampener
        if (!isSafeReport && isFirstTime) {
            for (int i = 0; i < reportParts.length; i++) {
                // ignore part i of list
                String subList = getListWithoutIthEntry(reportParts, i);
                isSafeReport = isSafeReport(subList, false);
                if (isSafeReport) {
                    break;
                }
            }
        }

        return isSafeReport;
    }

    private String getListWithoutIthEntry(String[] reportParts, int i) {
        String sublist = "";
        for (int j = 0; j < reportParts.length; j++) {
            if (j != i) {
                sublist = sublist + " " + reportParts[j];
            }
        }
        return sublist.trim();
    }

    private boolean isDecreasing(int int1, int int2) {
        return int1 > int2;
    }

    private boolean isIncreasing(int int1, int int2) {
        return int1 < int2;
    }

    private int getInt(String intVal) {
        return Integer.parseInt(intVal);
    }

    public static void main(String[] args) {
        try {
            Reports reports = new Reports(INPUT);
            int safeReports = reports.countSafeReports(reports.getReports());
            int unsafeReports = reports.getReports().size() - safeReports;
            System.out.println("Total Safe reports: " + safeReports);
            System.out.println("Total unsafe reports: " + unsafeReports);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getReports() {
        return reports;
    }
}
