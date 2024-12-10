package day6;

import utils.FileUtils;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static day6.Direction.*;

public class Day6 {

    private String[][] map;
    private String[][] pathMap;
    private Point guardLocation;
    private Direction direction = UP;
    private List<Point> loopCounters = new ArrayList<>();
    private int loopPossibilityCount = 0;
    private List<Point> loopPossibilityObstacleLocations;
    private Point guardStartLocation;

    public Day6(String[][] map) {
        this.map = map;
        this.pathMap = new String[map.length][map[0].length];
        this.loopPossibilityObstacleLocations = new ArrayList<>();
        guardStartLocation = findGuard();
        setGuardLocation(guardStartLocation);
        duplicateMapAsPathMap(map);
    }

    private void duplicateMapAsPathMap(String[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                pathMap[j][i] = map[j][i];
            }
        }
    }

    public void setGuardLocation(Point guardLocation) {
        this.guardLocation = guardLocation;
    }

    public Point guardLocation() {
        return guardLocation;
    }

    public String[][] getMap() {
        return map;
    }

    public Point findGuard() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j].equals("^")) {
                    return new Point(j, i);
                }
            }
        }
        return null;
    }

    public Point moveGuardForwards() {
        map[guardLocation.y][guardLocation.x] = "X";

        if (isNextStepOffTheMap()) {
//            printMaps();
            return exitGuard();
        }

        boolean isCornerTurn;
        while (isCornerTurn = isNextStepForwardsABarrier()) {
            direction = getNextDirection(direction);
        }

        Point newLocation;
        switch (direction) {
            case UP:
                guardLocation = moveGuardUp(isCornerTurn);
                break;
            case DOWN:
                guardLocation = moveGuardDown(isCornerTurn);
                break;
            case LEFT:
                guardLocation = moveGuardLeft(isCornerTurn);
                break;
            case RIGHT:
                guardLocation = moveGuardRight(isCornerTurn);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }

        map[guardLocation.y][guardLocation.x] = "^";

//        printMaps();
        return guardLocation;
    }

    private void printMaps() {
        for (int i=0; i<map.length; i++) {
            for (int j=0; j<map[i].length; j++) {
                System.out.print(map[i][j]);
            }

            System.out.print("    ");
            for (int j = 0; j< pathMap[i].length; j++) {

                System.out.print(pathMap[i][j]);
            }

            System.out.println();
        }
        System.out.println();
    }

    private Point exitGuard() {
        return guardLocation;
    }

    private boolean isNextStepOffTheMap() {
        switch(direction) {
            case UP:
                return guardLocation.y == 0;
            case DOWN:
                return guardLocation.y == map.length-1;
            case LEFT:
                return guardLocation.x == 0;
            case RIGHT:
                return guardLocation.x == map[0].length-1;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    public boolean isNextStepForwardsABarrier() {
        switch(direction) {
            case UP:
                return map[guardLocation.y-1][guardLocation.x].equals("#");
            case DOWN:
                return map[guardLocation.y+1][guardLocation.x].equals("#");
            case LEFT:
                return map[guardLocation.y][guardLocation.x-1].equals("#");
            case RIGHT:
                return map[guardLocation.y][guardLocation.x+1].equals("#");
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    public Direction getNextDirection(Direction direction) {
        switch (direction) {
            case UP:
                return Direction.RIGHT;
            case DOWN:
                return LEFT;
            case LEFT:
                return UP;
            case RIGHT:
                return DOWN;
        }
        return null;
    }

    private Point moveGuardRight(boolean isCornerTurn) {
        updateOldGuardLocationLeftRight(isCornerTurn);
        return new Point(guardLocation.x+1, guardLocation.y);
    }

    private Point moveGuardLeft(boolean isCornerTurn) {
        updateOldGuardLocationLeftRight(isCornerTurn);
        return new Point(guardLocation.x-1, guardLocation.y);
    }

    private Point moveGuardDown(boolean isCornerTurn) {
        updateLastGuardLocationForUpDown(isCornerTurn);
        return new Point(guardLocation.x, guardLocation.y+1);
    }

    private Point moveGuardUp(boolean isCornerTurn) {
        updateLastGuardLocationForUpDown(isCornerTurn);
        return new Point(guardLocation.x, guardLocation.y-1);
    }

    private boolean checkIfBarrierAheadCausesLoop(Point guardLocation, Direction currentDirection) {
        int startRow = guardLocation.y;
        int startCol = guardLocation.x;

        Direction nextDirection = getNextDirection(currentDirection);

        Point obstaclePoint = null;
        switch (nextDirection) {
            case UP : // moving left, check if up and right results in loop
                obstaclePoint = new Point(startCol-1, startRow);
                if (obstaclePointIsValidAndNotABarrier(obstaclePoint)) {
                    return isPathUpAndRightALoop();
                }
                return false;
            case DOWN: // moving right, check if down and left results in loop
                obstaclePoint = new Point(startCol+1, startRow);
                if (obstaclePointIsValidAndNotABarrier(obstaclePoint)) {
                    return isPathDownAndLeftALoop();
                }
                return false;
            case LEFT:
                obstaclePoint = new Point(startCol, startRow+1);
                if (obstaclePointIsValidAndNotABarrier(obstaclePoint)) {
                    return isPathLeftandUpALoop();
                }
                return false;
            case RIGHT:
                obstaclePoint = new Point(startCol, startRow-1);
                if (obstaclePointIsValidAndNotABarrier(obstaclePoint)) {
                    return isPathRightAndDownALoop();
                }
                return false;

        }
        return false;
    }

    private boolean obstaclePointIsValidAndNotABarrier(Point obstaclePoint) {
        return coordsAreWithinMapBounds(obstaclePoint)
                && obstaclePointIsNotABarrier(obstaclePoint.y, obstaclePoint.x);
    }

    private boolean obstaclePointIsNotABarrier(int row, int col) {
        return !pathMap[row][col].equals("#");
    }

    private boolean coordsAreWithinMapBounds(Point possibleObstacleLocation) {
        return possibleObstacleLocation.x >= 0 && possibleObstacleLocation.x < map.length
                && possibleObstacleLocation.y >= 0 && possibleObstacleLocation.y < map[0].length;
    }

    public int getLoopPossibilitiesCount() {
        return loopPossibilityCount;
    }

    private boolean isPathRightAndDownALoop() {
        int startCol = guardLocation.x;
        int startRow = guardLocation.y;
        if (isNextToWall(startCol, startRow, UP)) {
            return false;
        }
        // Moving right, check if down and left results in loop
        for (int i = startCol+1; i < map[0].length; i++) { // move right across Cols
            if (i == startCol+1) {
                // Check cell to right is not a wall FIRST if so this cannot be a loop
                if (isNextToWall(startCol, startRow, RIGHT)) {
                    return false;
                }
            }
            if (pathMap[startRow][i].equals("#")) { // move right until barrier found
                Point turnPoint = new Point(i-1, startRow);
                if (pathMap[startRow+1][i-1].equals("#")) {
                    return false;
                }
                Point nextToCheck = new Point(i-1, startRow+1);
//                while ( coordsAreWithinMapBounds(nextToCheck) &&
//                        newMap[nextToCheck.y][nextToCheck.x].equals("#") ) {
                    if (pathMap[turnPoint.y + 1][turnPoint.x].equals("|") // up == "|" | "+"
                            || pathMap[turnPoint.y + 1][turnPoint.x].equals("+")) {
                        Point point = new Point(guardLocation.x, guardLocation.y-1);
                        loopPossibilityObstacleLocations.add(point);
//                        printMapWithObstacle(newMap, point);
                        return true;
                    }
//                }
                break; // exit loop - no further checking necessary
            }
        }
        return false;
    }

    private boolean isPathLeftandUpALoop() {
        int startCol = guardLocation.x;
        int startRow = guardLocation.y;
        if (isNextToWall(startCol, startRow, DOWN)) {
            return false;
        }
        // Moving left, check if left and up results in loop
        for (int i = startCol-1; i >= 0; i--) { // move up the Rows
            if (i == startCol-1) {
                // Check cell to right is not a wall FIRST if so this cannot be a loop
                if (isNextToWall(startCol, startRow, LEFT)) {
                    return false;
                }
            }
            if (pathMap[startRow][i].equals("#")) { // move left until barrier found
                Point turnPoint = new Point(i+1, startRow);
                // exit if a turn point is directly next to another barrier
                if (isNextToWall(turnPoint.x, turnPoint.y, UP)) {
                    return false;// back right the grid one col
                }
                if (pathMap[turnPoint.y-1][turnPoint.x].equals("|") // up == "|" | "+"
                        || pathMap[turnPoint.y-1][turnPoint.x].equals("+")) {
                    Point point = new Point(guardLocation.x, guardLocation.y + 1);
                    loopPossibilityObstacleLocations.add(point);
//                    printMapWithObstacle(newMap, point);
                    return true;
                }
                break; // exit loop - no further checking necessary
            }
        }
        return false;
    }

    private boolean isPathDownAndLeftALoop() {
        int startCol = guardLocation.x;
        int startRow = guardLocation.y;


        if (isNextToWall(startCol, startRow, RIGHT)) {
            return false;
        }
        // check if down and left results in loop
        for (int i = startRow+1; i < map.length; i++) { // move down the Rows
            if (i == startRow+1) {
                // Check first cell encountered moving down is not a wall. If so this cannot be a loop
                if (isNextToWall(startCol, startRow, DOWN)) {
                    return false;
                }
            }
            if (pathMap[i][startCol].equals("#")) {
                Point turnPoint = new Point(startCol, i-1);
                //newMap[i-1][startCol].equals("#")
                if (isNextToWall(turnPoint.x, turnPoint.y, LEFT)) {
                    return false;// back right the grid one col
                }
                if (pathMap[turnPoint.y][turnPoint.x-1].equals("-") // left == "-" | "+"
                        || pathMap[turnPoint.y][turnPoint.x-1].equals("+")) {
                    Point point = new Point(guardLocation.x + 1, guardLocation.y);
                    loopPossibilityObstacleLocations.add(point);
//                    printMapWithObstacle(newMap, point);
                    return true;
                }
                break; // exit loop - no further checking necessary
            }
        }
        return false;
    }

    private boolean isPathUpAndRightALoop() {
        int startCol = guardLocation.x;
        int startRow = guardLocation.y;
        if (isNextToWall(startCol, startRow, LEFT)) {
            return false;
        }

        printMapWithObstacle(pathMap, guardLocation);

        // Move Up until barrier encountered.
        for (int i = startRow-1; i >= 0; i--) {
            if (i == startRow-1) {
                // Check cell to right is not a wall FIRST if so this cannot be a loop
                if (isNextToWall(startCol, startRow, UP)) {
                    return false;
                }
            }
            if (pathMap[i][startCol].equals("#")) {
                Point turnPoint = new Point(startCol, i+1); // back down the grid 1 row
                if (isNextToWall(turnPoint.x, turnPoint.y, RIGHT)) {
                    return false;// back right the grid one col
                }
                if (pathMap[turnPoint.y][turnPoint.x+1].equals("-")
                    || pathMap[turnPoint.y][turnPoint.x+1].equals("+")) {
                    Point point = new Point(startCol - 1, startRow);
                    loopPossibilityObstacleLocations.add(point);
//                    printMapWithObstacle(newMap, point);
                    return true;
                }
                break; // exit loop
            }
        }
        return false;
    }

    private boolean isNextToWall(int turnPointX, int turnPointY, Direction direction) {
        switch(direction) {
            case UP:
                if (coordsAreWithinMapBounds(new Point(turnPointX, turnPointY-1)) && pathMap[turnPointY-1][turnPointX].equals("#")) {
                    return true;
                }
                break;
            case DOWN:
                if (coordsAreWithinMapBounds(new Point(turnPointX, turnPointY+1)) && pathMap[turnPointY+1][turnPointX].equals("#")) {
                    return true;
                }
                break;
            case LEFT:
                if (coordsAreWithinMapBounds(new Point(turnPointX-1, turnPointY)) && pathMap[turnPointY][turnPointX-1].equals("#")) {
                    return true;
                }
                break;
            case RIGHT:
                if (coordsAreWithinMapBounds(new Point(turnPointX+1, turnPointY)) && pathMap[turnPointY][turnPointX+1].equals("#")) {
                    return true;
                }
                break;
        }
        return false;
    }

    private void printMapWithObstacle(String[][] newMap, Point point) {
        for (int i=0; i<newMap.length; i++) {
            for (int j=0; j<newMap[0].length; j++) {
                Point p = new Point(j, i);
                if (loopPossibilityObstacleLocations.contains(p)) {
                    System.out.print("O");
                } else if (i == guardLocation.y && j == guardLocation.x) {
                    System.out.print("^");
                } else if (newMap[i][j].equals(".")) {
                    System.out.print(" ");
                } else {
                    System.out.print(newMap[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("-------------------------------------------");
        System.out.println();
    }

    private void updateLastGuardLocationForUpDown(boolean isCornerTurn) {
        switch (pathMap[guardLocation.y][guardLocation.x]) {
            case ".":
                if (isCornerTurn) {
                    pathMap[guardLocation.y][guardLocation.x] = "+";
                } else {
                    pathMap[guardLocation.y][guardLocation.x] = "|";
                }
                break;
            case "-" : // Up/down over a left/right marker results in +
                pathMap[guardLocation.y][guardLocation.x] = "+";
                break;
            default:
                if (isCornerTurn) {
                    pathMap[guardLocation.y][guardLocation.x] = "+";
                } else {
                    pathMap[guardLocation.y][guardLocation.x] = "|";
                }
                break;
        }
    }

    private void updateOldGuardLocationLeftRight(boolean isCornerTurn) {
        switch (pathMap[guardLocation.y][guardLocation.x]) {
            case ".":
                if (isCornerTurn) {
                    pathMap[guardLocation.y][guardLocation.x] = "+";
                } else {
                    pathMap[guardLocation.y][guardLocation.x] = "-";
                }
                break;
            case "|" :
                pathMap[guardLocation.y][guardLocation.x] = "+";
                break;
            default:
                if (isCornerTurn) {
                    pathMap[guardLocation.y][guardLocation.x] = "+";
                } else {
                    pathMap[guardLocation.y][guardLocation.x] = "-";
                }
                break;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void runPatrol() {
        while (guardLocation.y >=0 && guardLocation.y < map.length
                && guardLocation.x >=0 && guardLocation.x < map[0].length){
            Point currentGuardLocation = guardLocation;
            Point newGuardLocation = moveGuardForwards();
            if (currentGuardLocation.equals(newGuardLocation)) {
                break; // end of patrol - guard exited the map.
            }
        }
    }

    public int countVisitedPointsOnMap() {
        int count = 0;
        for (int i=0; i<map.length; i++) {
            for (int j=0; j<map[i].length; j++) {
                if (map[i][j].equals("X")) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Day 6");
        String[][] mapLines = FileUtils.getFileAsArray("src/main/resources/day6/input.txt");
        Day6 day6 = new Day6(mapLines);
        day6.runPatrol();
        int visitedPointsOnMap = day6.countVisitedPointsOnMap();

        int loopCount = day6.checkForPossibleLoops();

        System.out.println("Visited points on map: " + visitedPointsOnMap);
        System.out.println("Infinite Loops Due to Obstacles: " + loopCount);
    }

    public String[][] getPathMap() {
        return pathMap;
    }

    public List getObstaclePoints() {
        return loopPossibilityObstacleLocations;
    }

    public int checkForPossibleLoops() {
        loopPossibilityObstacleLocations.clear();
        loopPossibilityCount = 0;
        direction= Direction.UP;
        guardLocation = guardStartLocation;

        while (guardLocation.y >= 0 && guardLocation.y < map.length
                && guardLocation.x >=0 && guardLocation.x < map[0].length){
            Point currentGuardLocation = guardLocation;
            Point newGuardLocation = moveGuardForwards();

            if (currentGuardLocation.equals(newGuardLocation)) {
                break; // end of patrol - guard exited the map.
            }

            if (checkIfBarrierAheadCausesLoop(guardLocation, direction)) {
                loopPossibilityCount++;
            }
        }
        return loopPossibilityCount;
    }
}
