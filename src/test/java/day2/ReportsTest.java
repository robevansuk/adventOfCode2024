package day2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportsTest {

    private Reports testObject;

    @BeforeEach
    void setUp() throws IOException {
        testObject = new Reports("src/test/resources/day2/input.txt");
    }

    @Test
    void checkFirstTestReportIsSafeAscending() {
        List<String> testReports = testObject.getReports();

        boolean isSafe = testObject.isSafeReport(testReports.get(0), true);

        assertTrue(isSafe);
    }

    @Test
    void checkSecondTestReportIsSafeAscendingAtMaxDistance() {
        List<String> testReports = testObject.getReports();

        boolean isSafe = testObject.isSafeReport(testReports.get(1), true);

        assertTrue(isSafe);
    }

    @Test
    void checkFirstTestReportIsSafeDescending() {
        List<String> testReports = testObject.getReports();

        boolean isSafe = testObject.isSafeReport(testReports.get(2), true);

        assertTrue(isSafe);
    }

    @Test
    void checkSecondTestReportIsSafeDescendingAtMaxDistance() {
        List<String> testReports = testObject.getReports();

        boolean isSafe = testObject.isSafeReport(testReports.get(3), true);

        assertTrue(isSafe);
    }

    @Test
    void checkTestReportIsUnsafeAscendingbBeyondMaxDistance() {
        List<String> testReports = testObject.getReports();

        boolean isSafe = testObject.isSafeReport(testReports.get(4), true);

        assertFalse(isSafe);
    }

    @Test
    void checkTestReportIsUnsafeDescendingbBeyondMaxDistance() {
        List<String> testReports = testObject.getReports();

        boolean isSafe = testObject.isSafeReport(testReports.get(5), true);

        assertFalse(isSafe);
    }

    @Test
    void checkTestReportIsUnsafeDueToEqualSequentialNumbersInAscReport() {
        List<String> testReports = testObject.getReports();

        boolean isSafe = testObject.isSafeReport(testReports.get(6), true);

        assertFalse(isSafe);
    }

    @Test
    void checkTestReportIsUnsafeDueToEqualSequentialNumbersInDescReport() {
        List<String> testReports = testObject.getReports();

        boolean isSafe = testObject.isSafeReport(testReports.get(7), true);

        assertFalse(isSafe);
    }

    @Test
    void testReport8To11AreSafe() {
        List<String> testReports = testObject.getReports();

        boolean isSafe1 = testObject.isSafeReport(testReports.get(8), true);
        boolean isSafe2 = testObject.isSafeReport(testReports.get(9), true);
        boolean isSafe3 = testObject.isSafeReport(testReports.get(10), true);
        boolean isSafe4 = testObject.isSafeReport(testReports.get(11), true);


        assertTrue(isSafe1);
        assertTrue(isSafe2);
        assertTrue(isSafe3);
        assertTrue(isSafe4);
    }


    /**
     * 7 6 4 2 1: Safe without removing any level.
     * 1 2 7 8 9: Unsafe regardless of which level is removed.
     * 9 7 6 2 1: Unsafe regardless of which level is removed.
     * 1 3 2 4 5: Safe by removing the second level, 3.
     * 8 6 4 4 1: Safe by removing the third level, 4.
     * 1 3 6 7 9: Safe without removing any level.
     */
    @Test
    void testReports13To17AreSafe() {
        List<String> testReports = testObject.getReports();

        boolean isSafe2 = testObject.isSafeReport(testReports.get(13), true);
        boolean isSafe3 = testObject.isSafeReport(testReports.get(14), true);
        boolean isSafe4 = testObject.isSafeReport(testReports.get(15), true);
        boolean isSafe5 = testObject.isSafeReport(testReports.get(16), true);
        boolean isSafe6 = testObject.isSafeReport(testReports.get(17), true);

        assertFalse(isSafe2);
        assertFalse(isSafe3);
        assertTrue(isSafe4);
        assertTrue(isSafe5);
        assertTrue(isSafe6);
    }
}