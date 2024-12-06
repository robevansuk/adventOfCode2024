package day4;

import utils.FileUtils;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day4 {

    private static final String INPUT = "src/main/resources/day4/input.txt";
    private static final String KEYWORD = "SAMX";
    private String[][] grid;

    public Day4(String input) throws IOException {
        this.grid = FileUtils.getFileAsArray(input);
    }

    public String[][] getGrid() {
        return grid;
    }

    public List<Integer> search(String[][] grid, String keyword) {
        List<Integer> results = new ArrayList<>();

        results.add(searchForwards(grid, keyword));
        results.add(searchBackwards(grid, keyword));
        results.add(searchUpwards(grid, keyword));
        results.add(searchDownwards(grid, keyword));

        results.add(searchDiagonallyForwardsDown(grid, keyword));
        results.add(searchDiagonallyForwardsUp(grid, keyword));
        results.add(searchDiagonallyBackwardsDown(grid, keyword));
        results.add(searchDiagonallyBackwardsUp(grid, keyword));

        return results;
    }

    public int getTotal(List<Integer> counts) {
        return counts.stream().mapToInt(Integer::intValue).sum();
    }

    public Integer searchDiagonallyBackwardsUp(String[][] grid, String keyword) {
        int count = 0;

        int startRow = keyword.length()-1;
        int endRow = grid.length;
        System.out.println("searchDiagBackUp StartRow,EndRow: " + startRow + "," + endRow);
        int startCol = keyword.length()-1;
        int endCol = grid[0].length;
        System.out.println("searchDiagBackUp StartCol,EndCol: " + startCol + "," + endCol);

        for(int i=startRow; i<endRow; i++) { // iterate rows
            for(int j=startCol; j<endCol; j++) { // cols
//                System.out.println(i + "," + j);
                String maybeWord = getCharsDiagonallyBackwardsAndUp(grid, keyword, i, j);
//                System.out.println(maybeWord);
                if (maybeWord.equals(keyword)) {
                    count++;
                }
            }
        }
        System.out.println("Diag Back Up: " + count);
        return count;
    }

    private String getCharsDiagonallyBackwardsAndUp(String[][] grid, String keyword, int i, int j) {
        StringBuilder sb = new StringBuilder();
        for (int y=0; y<keyword.length(); y++) {
            int row = i-y;
            int col = j-y;
            sb.append(grid[row][col]);
        }
        return sb.toString();
    }

    public Integer searchDiagonallyForwardsUp(String[][] grid, String keyword) {
        int count = 0;

        int startRow = keyword.length()-1;
        int endRow = grid.length;
        System.out.println("searchDiagFwdUp StartRow,EndRow: " + startRow + "," + endRow);
        int startCol = 0;
        int endCol = grid[0].length-keyword.length()+1;
        System.out.println("searchDiagFwdUp StartCol,EndCol: " + startCol + "," + endCol);

        for(int i=startRow; i<endRow; i++) { // iterate rows
            for(int j=startCol; j<endCol; j++) { // cols
//                System.out.println(i + "," + j);
                String maybeWord = getCharsDiagonallyForwardsAndUp(grid, keyword, i, j);
//                System.out.println(maybeWord);
                if (maybeWord.equals(keyword)) {
                    count++;
                }
            }
        }
        System.out.println("Diag Forward Up: " + count);
        return count;
    }

    private String getCharsDiagonallyForwardsAndUp(String[][] grid, String keyword, int i, int j) {
        StringBuilder sb = new StringBuilder();
        for (int y=0; y<keyword.length(); y++) {
            int row = i-y;
            int col = j+y;
            sb.append(grid[row][col]);
        }
        return sb.toString();
    }

    public Integer searchDiagonallyForwardsDown(String[][] grid, String keyword) {
        int count = 0;

        int startRow = 0;
        int endRow = grid.length-keyword.length()+1;
        System.out.println("searchDiagFwdDown StartRow,EndRow: " + startRow + "," + endRow);
        int startCol = 0;
        int endCol = grid[0].length-keyword.length()+1;
        System.out.println("searchDiagFwdDown StartCol,EndCol: " + startCol + "," + endCol);

        for(int i=startRow; i<endRow; i++) { // iterate rows
            for(int j=startCol; j<endCol; j++) { // cols
//                System.out.println(i + "," + j);
                String maybeWord = getCharsDiagonallyForwardsAndDown(grid, keyword, i, j);
//                System.out.println(maybeWord);
                if (maybeWord.equals(keyword)) {
                    count++;
                }
            }
        }
        System.out.println("Diag Forward Down: " + count);
        return count;
    }

    private String reverse(String maybeWord) {
        StringBuilder sb = new StringBuilder();
        for (int i=maybeWord.length()-1; i>=0; i--) {
            sb.append(maybeWord.charAt(i));
        }
        return sb.toString();
    }

    private String getCharsDiagonallyForwardsAndDown(String[][] grid, String keyword, int i, int j) {
        StringBuilder sb = new StringBuilder();
        for (int y=0; y<keyword.length(); y++) {
            int row = i+y;
            int col = j+y;
            sb.append(grid[row][col]);
        }
        return sb.toString();
    }

    public Integer searchDiagonallyBackwardsDown(String[][] grid, String keyword) {
        int count = 0;

        int startRow = 0;
        int endRow = grid.length-keyword.length()+1;
        System.out.println("searchDiagBackDown StartRow,EndRow: " + startRow + "," + endRow);
        int startCol = keyword.length()-1;
        int endCol = grid[0].length;
        System.out.println("searchDiagBackDown StartCol,EndCol: " + startCol + "," + endCol);

        for(int i=startRow; i<endRow; i++) { // iterate rows
            for(int j=startCol; j<endCol; j++) { // cols

//        for(int i=0; i<grid.length-keyword.length()+1; i++) { // iterate rows
//            for(int j=keyword.length()-1; j<grid[0].length; j++) { // cols
//                System.out.println(i + "," + j);
                String maybeWord = getCharsDiagonallyBackwardsAndDown(grid, keyword, i, j);
//                System.out.println(maybeWord);
                if (maybeWord.equals(keyword)) {
                    count++;
                }
            }
        }
        System.out.println("Diag Back Down: " + count);
        return count;
    }

    private String getCharsDiagonallyBackwardsAndDown(String[][] grid, String keyword, int i, int j) {
        StringBuilder sb = new StringBuilder();
        for (int y=0; y<keyword.length(); y++) {
            int row = i+y;
            int col = j-y;
            sb.append(grid[row][col]);
        }
        return sb.toString();
    }

    public Integer searchDownwards(String[][] grid, String keyword) {
        int count = 0;

        int startRow = 0;
        int endRow = grid.length-keyword.length()+1;
        System.out.println("searchDownwards StartRow,EndRow: " + startRow + "," + endRow);
        int startCol = 0;
        int endCol = grid[0].length;
        System.out.println("searchDownwards StartCol,EndCol: " + startCol + "," + endCol);

        for(int i=startRow; i<endRow; i++) { // rows
            for(int j=startCol; j<endCol; j++) {// cols
//                System.out.println(i + "," + j);
                String maybeWord = getCharsTopToBottom(grid, keyword, i, j);
//                System.out.println(maybeWord);
                if (maybeWord.equals(keyword)) {
                    count++;
                }
            }
        }
        System.out.println("Downwards: " + count);
        return count;
    }

    private String getCharsTopToBottom(String[][] grid, String keyword, int i, int j) {
        StringBuilder sb = new StringBuilder();
        for (int y=0; y<keyword.length(); y++) {
            sb.append(grid[i+y][j]);
        }
        return sb.toString();
    }

    public Integer searchUpwards(String[][] grid, String keyword) {
        int count = 0;

        int startRow  = keyword.length()-1;
        int endRow =  grid.length;
        System.out.println("searchUpwards StartRow,EndRow: " + startRow + "," + endRow);
        int startCol = 0;
        int endCol = grid[0].length;
        System.out.println("searchUpwards StartCol,EndCol: " + startCol + "," + endCol);

        for(int i=startRow; i<endRow; i++) { // iterate across all rows
            for(int j=startCol; j<endCol; j++) {// iterate over all chars down the column
//                System.out.println(i + "," + j);
                String maybeWord = getCharsBottomToTop(grid, keyword, i, j);
                if (maybeWord.equals(keyword)) {
                    count++;
                }
            }
        }
        System.out.println("Upwards: " + count);
        return count;
    }

    private String getCharsBottomToTop(String[][] grid, String keyword, int i, int j) {
        StringBuilder sb = new StringBuilder();
        for (int y=0; y<keyword.length(); y++) {
            int row = i-y;
            int col = j;
            sb.append(grid[row][col]);
        }
        return sb.toString();
    }

    public Integer searchBackwards(String[][] grid, String keyword) {
        int count = 0;

        int startRow = 0;
        int endRow = grid.length;
        System.out.println("searchBackwards StartRow,EndRow: " + startRow + "," + endRow);
        int startCol = keyword.length()-1;
        int endCol = grid[0].length;
        System.out.println("searchBackwards StartCol,EndCol: " + startCol + "," + endCol);

        for(int i=startRow; i<endRow; i++) {
            for(int j=startCol; j<endCol; j++) {
//                System.out.println(i + "," + j);
                String maybeWord = getCharsRightToLeft(grid, keyword, i, j);
//                System.out.println(maybeWord);
                if (maybeWord.equals(keyword)) {
                    count++;
                }
            }
        }
        System.out.println("Backwards: " + count);
        return count;
    }

    private String getCharsRightToLeft(String[][] grid, String keyword, int i, int j) {
        StringBuilder sb = new StringBuilder();
        for (int k=0; k<keyword.length(); k++) {
            int row = i;
            int col = j-k;
//            System.out.println("row,col -> " + row + ","+ col);
            sb.append(grid[row][col]);
        }
        return sb.toString();
    }

    public Integer searchForwards(String[][] grid, String keyword) {
        int count = 0;

        int startRow = 0;
        int endRow = grid.length;
        System.out.println("searchForwards StartRow,EndRow: " + startRow + "," + endRow);
        int startCol = 0;
        int endCol = grid[0].length-keyword.length()+1;
        System.out.println("searchForwards StartCol,EndCol: " + startCol + "," + endCol);

        for(int i=startRow; i<endRow; i++) { // iterate across all rows
            for(int j=startCol; j<endCol; j++) {// iterate over all chars (up to the last 4/length of the keyword being searched for)
                String maybeWord = getCharsLeftToRight(grid[i], keyword, j);
                if (maybeWord.equals(keyword)) {
                    count++;
                }
            }
        }
        System.out.println("Forwards: " + count);
        return count;
    }

    private String getCharsLeftToRight(String[] characters, String keyword, int j) {
        StringBuilder sb = new StringBuilder();
        for (int k=0; k<keyword.length(); k++) {
          sb.append(characters[j+k]);
        }
        return sb.toString();
    }


    public static void main(String[] args) throws IOException {
        Day4 day4 = new Day4(INPUT);
        String[][] grid = day4.getGrid();

        List<Integer> xmas = day4.search(grid, KEYWORD);
        int total = day4.getTotal(xmas);
        System.out.println(KEYWORD + " = " + total);

        System.out.println("MAS in an X = " + day4.searchOrthogonally(grid, "MAS"));
    }

    public Integer searchOrthogonally(String[][] grid, String keyword) {
        int count = 0;

        // reverse between the two
        List<Point> pointsFwdDiagDown = new ArrayList<>();
        List<Point> pointsBackDiagUp = new ArrayList<>();

        // reverse between the two
        List<Point> pointsFwdDiagUp = new ArrayList<>();
        List<Point> pointsBackDiagDown = new ArrayList<>();

        int startRow = 0;
        int endRow = grid.length;
        System.out.println("RowsMAS StartRow,EndRow: " + startRow + "," + endRow);
        int startCol = 0;
        int endCol = grid[0].length;
        System.out.println("ColsMAS StartCol,EndCol: " + startCol + "," + endCol);

        for(int i=startRow; i<endRow; i++) { // iterate rows
            for(int j=startCol; j<endCol; j++) { // cols
                String maybeWord = null;
                // forwards and down:
                int startRow1 = 0;
                int endRow1 = grid.length-keyword.length()+1;
                int startCol1 =0;
                int endCol1 = grid[0].length-keyword.length()+1;

                if (i >= startRow1 && i < endRow1
                 && j>=startCol1 && j < endCol1) {
                    maybeWord = getCharsDiagonallyForwardsAndDown(grid, keyword, i, j);
                    if (maybeWord.equals(keyword)) {
                        pointsFwdDiagDown.add(new Point(i, j));
                    }
                    if (reverse(maybeWord).equals(keyword)) {
                        int xCoord = i + keyword.length()-1;
                        int yCoord = j + keyword.length()-1;
                        pointsBackDiagUp.add(new Point(xCoord, yCoord));
                    }
                }


                int startRow2 = keyword.length()-1;
                int endRow2 = grid.length;
                int startCol2 = 0;
                int endCol2 = grid[0].length-keyword.length()+1;

                // Backwards and Up:
                if (i >= startRow2 && i < endRow2
                        && j >= startCol2 && j < endCol2)  {
                    maybeWord = getCharsDiagonallyForwardsAndUp(grid, keyword, i, j);
                    if (maybeWord.equals(keyword)) {
                        pointsFwdDiagUp.add(new Point(i, j));
                    }
                    if (reverse(maybeWord).equals(keyword)) {
                        int rowCoord = i - keyword.length()+1;
                        int colCoord = j + keyword.length()-1;
                        pointsBackDiagDown.add(new Point(rowCoord, colCoord));
                    }
                }
            }
        }

        for (Point point : pointsFwdDiagDown) {
               if (pointsFwdDiagUp.contains(new Point(point.x+keyword.length()-1, point.y))
                || pointsBackDiagDown.contains(new Point(point.x, point.y+keyword.length()-1))) {
                   count++;
               }
        }

        for (Point point : pointsBackDiagUp) {
            if (pointsBackDiagDown.contains(new Point(point.x-keyword.length()+1, point.y))
                    || pointsFwdDiagUp.contains(new Point(point.x, point.y-keyword.length()+1))) {
                count++;
            }
        }

        return count;
    }
}
