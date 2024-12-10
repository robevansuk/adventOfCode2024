package day6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileUtils;

import java.awt.Point;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day6Test {

    private Day6 testObject;

    @BeforeEach
    void setUp() throws IOException {
        String[][] lines = FileUtils.getFileAsArray("src/test/resources/day6/input.txt");
        testObject = new Day6(lines);
    }

    @Test
    void getPositionOfGuard() {
        Point result = testObject.findGuard();
        assertEquals(new Point(4, 6), result);
    }

    @Test
    void onceGuardIsFoundStoreItsLocation() {
        Point guardLocation = testObject.findGuard();
        testObject.setGuardLocation(guardLocation);

        assertEquals(new Point(4, 6), testObject.guardLocation());
    }

    @Test
    void ifStepForwardIsFreeOfObstableUpdateGuardLocation() {
        Point guardLocation = testObject.guardLocation();
        Point newLocation = testObject.moveGuardForwards();

        assertEquals(new Point(4, 5), newLocation);
    }

    @Test
    void whenSteppingForwardUpdateMapRepresentationWithGuardInNewPosition() {
        Point newLocation = testObject.moveGuardForwards();

        assertEquals("^", testObject.getMap()[newLocation.y][newLocation.x]);
    }

    @Test
    void whenSteppingForwardLeaveTrailForGuardsPathWithX() {
        Point guardLocation = testObject.guardLocation();
        Point newLocation = testObject.moveGuardForwards();

        assertEquals("X", testObject.getMap()[guardLocation.y][guardLocation.x]);
    }

    @Test
    void whenSteppingForwardTurnRightIfTheresAnObstacleIntheWayAndTurnRight() {
        //take 6 steps on test map
        testObject.moveGuardForwards();
        testObject.moveGuardForwards();
        testObject.moveGuardForwards();
        testObject.moveGuardForwards();
        testObject.moveGuardForwards();
        testObject.moveGuardForwards();

        assertEquals(Direction.RIGHT, testObject.getDirection());
    }

    @Test
    void whenSteppingForwardTurnAndCheckEachDirection() {
        assertEquals(Direction.UP, testObject.getDirection());
        //take 6 steps on test map
        for (int i = 0; i < 6; i++) {
            testObject.moveGuardForwards();
        }
        System.out.println("-----------------");
        assertEquals(Direction.RIGHT, testObject.getDirection());
        for (int i = 0; i < 4; i++) {
            testObject.moveGuardForwards();
        }
        System.out.println("-----------------");
        assertEquals(Direction.DOWN, testObject.getDirection());
        for (int i = 0; i < 5; i++) {
            testObject.moveGuardForwards();
        }
        System.out.println("-----------------");
        assertEquals(Direction.LEFT, testObject.getDirection());
        for (int i = 0; i < 6; i++) {
            testObject.moveGuardForwards();
        }
        assertEquals(Direction.UP, testObject.getDirection());
    }

    @Test
    void endPatrolWhenGuardStepsOffMap() {
        testObject.runPatrol();

        assertEquals(new Point(7, 9), testObject.guardLocation());

    }

    @Test
    void testMovePositionOfGuardOneStepForwards() {
        assertEquals(new Point(4, 6), testObject.guardLocation());
        Point result = testObject.moveGuardForwards();
        assertEquals(new Point(4, 5), result);
    }

    @Test
    void testCountTotalVisitedPositionsOnMap() {
        testObject.runPatrol();
        int result = testObject.countVisitedPointsOnMap();
        assertEquals(41, result);
    }

    @Test
    void movingUpOverASpaceLeavesAPipeCharBehind() {
        testObject.moveGuardForwards();
        testObject.moveGuardForwards();
        assertEquals("|", testObject.getPathMap()[6][4]);
        assertEquals("|", testObject.getPathMap()[5][4]);
    }

    @Test
    void testMovingAroundResultsInTheRightSymbols() {
        assertEquals(Direction.UP, testObject.getDirection());
        //take 6 steps on test map
        testObject.moveGuardForwards();
        testObject.moveGuardForwards();
        testObject.moveGuardForwards();
        testObject.moveGuardForwards();
        testObject.moveGuardForwards();
        testObject.moveGuardForwards();

        assertEquals(Direction.RIGHT, testObject.getDirection());
        assertEquals("|", testObject.getPathMap()[6][4]);
        assertEquals("|", testObject.getPathMap()[5][4]);
        assertEquals("|", testObject.getPathMap()[4][4]);
        assertEquals("|", testObject.getPathMap()[3][4]);
        assertEquals("|", testObject.getPathMap()[2][4]);

        testObject.moveGuardForwards();
        testObject.moveGuardForwards();
        testObject.moveGuardForwards();
        testObject.moveGuardForwards();
        testObject.moveGuardForwards();

        System.out.println("---------------------------------------------------");

        assertEquals(Direction.DOWN, testObject.getDirection());
        testObject.moveGuardForwards();
        assertEquals("+", testObject.getPathMap()[1][4]);
        testObject.moveGuardForwards();
        assertEquals("-", testObject.getPathMap()[1][5]);
        testObject.moveGuardForwards();
        assertEquals("-", testObject.getPathMap()[1][6]);
        testObject.moveGuardForwards();
        assertEquals("-", testObject.getPathMap()[1][7]);
        testObject.moveGuardForwards();
        assertEquals("+", testObject.getPathMap()[1][8]);
        testObject.moveGuardForwards();
        assertEquals("|", testObject.getPathMap()[2][8]);
        testObject.moveGuardForwards();
        System.out.println("---------------------------------------------------");

        for (int i = 0; i < 4; i++) {
            testObject.moveGuardForwards();
        }
        assertEquals(Direction.LEFT, testObject.getDirection());
        assertEquals("|", testObject.getPathMap()[3][8]);
        assertEquals("|", testObject.getPathMap()[4][8]);
        assertEquals("|", testObject.getPathMap()[5][8]);
        assertEquals("+", testObject.getPathMap()[6][8]);
        assertEquals("-", testObject.getPathMap()[6][7]);

        System.out.println("---------------------------------------------------");
        for (int i = 0; i < 6; i++) {
            testObject.moveGuardForwards();
        }
        assertEquals(Direction.UP, testObject.getDirection());
        assertEquals("-", testObject.getPathMap()[6][6]);
        assertEquals("-", testObject.getPathMap()[6][5]);
        assertEquals("+", testObject.getPathMap()[6][4]);
        assertEquals("-", testObject.getPathMap()[6][3]);
        assertEquals("+", testObject.getPathMap()[6][2]);
        assertEquals("|", testObject.getPathMap()[5][2]);
    }


    @Test
    void testLoopPossibilitisOneInTestFile1() throws IOException {
        String[][] lines = FileUtils.getFileAsArray("src/test/resources/day6/test1.txt");
        Day6 testFile1 = new Day6(lines);
        testFile1.runPatrol();
        int result = testFile1.getLoopPossibilitiesCount();
        List<Point> obstacles = testFile1.getObstaclePoints();
        assertEquals(1, result);
    }

    @Test
    void testLoopPossibilitisOneInTestFile2() throws IOException {
        String[][] lines = FileUtils.getFileAsArray("src/test/resources/day6/test2.txt");
        Day6 rightAndDownCreatesALoop = new Day6(lines);
        rightAndDownCreatesALoop.runPatrol();
        int result = rightAndDownCreatesALoop.getLoopPossibilitiesCount();
        List<Point> obstacles = rightAndDownCreatesALoop.getObstaclePoints();
        assertEquals(1, result);
    }

    @Test
    void testLoopPossibilitisOneInTestFile3() throws IOException {
        String[][] lines = FileUtils.getFileAsArray("src/test/resources/day6/test3.txt");
        Day6 downAndLeftCreatesALoop = new Day6(lines);
        downAndLeftCreatesALoop.runPatrol();
        int result = downAndLeftCreatesALoop.getLoopPossibilitiesCount();
        List<Point> obstacles = downAndLeftCreatesALoop.getObstaclePoints();
        assertEquals(1, result);
    }

    @Test
    void testLoopPossibilitisOneInTestFile4() throws IOException {
        String[][] lines = FileUtils.getFileAsArray("src/test/resources/day6/test4.txt");
        Day6 leftAndUpCreatesALoop = new Day6(lines);
        leftAndUpCreatesALoop.runPatrol();
        int result = leftAndUpCreatesALoop.getLoopPossibilitiesCount();
        List<Point> obstacles = leftAndUpCreatesALoop.getObstaclePoints();
        assertEquals(2, result);
    }

    @Test
    void testNoLoopPossibilitiesInTestFile1() throws IOException {
        String[][] lines = FileUtils.getFileAsArray("src/test/resources/day6/NoLoops1.txt");
        Day6 noLoops = new Day6(lines);
        noLoops.runPatrol();
        int result = noLoops.getLoopPossibilitiesCount();
        List<Point> obstacles = noLoops.getObstaclePoints();
        assertEquals(0, result);
    }

    @Test
    void testNoLoopPossibilitiesInTestFile2() throws IOException {
        String[][] lines = FileUtils.getFileAsArray("src/test/resources/day6/NoLoops2.txt");
        Day6 noLoops = new Day6(lines);
        noLoops.runPatrol();
        int result = noLoops.getLoopPossibilitiesCount();
        List<Point> obstacles = noLoops.getObstaclePoints();
        assertEquals(0, result);
    }

    @Test
    void testNoLoopPossibilitiesInTestFile3() throws IOException {
        String[][] lines = FileUtils.getFileAsArray("src/test/resources/day6/NoLoops3.txt");
        Day6 noLoops = new Day6(lines);
        noLoops.runPatrol();
        int result = noLoops.getLoopPossibilitiesCount();
        List<Point> obstacles = noLoops.getObstaclePoints();
        assertEquals(1, result);
    }

    @Test
    void testNoLoopPossibilitiesInTestFile4() throws IOException {
        String[][] lines = FileUtils.getFileAsArray("src/test/resources/day6/NoLoops4.txt");
        Day6 noLoops = new Day6(lines);
        noLoops.runPatrol();
        int result = noLoops.getLoopPossibilitiesCount();
        List<Point> obstacles = noLoops.getObstaclePoints();
        assertEquals(2, result);
    }


    @Test
    void testLoopPossibilitiesAreIdentifiedInSampleDataCorrectly() {
        testObject.runPatrol();
        int result = testObject.getLoopPossibilitiesCount();
        List<Point> obstacles = testObject.getObstaclePoints();
        assertEquals(6, result);
    }
}
