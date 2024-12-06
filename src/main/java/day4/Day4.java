package day4;

import utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day4 {

    private static final String INPUT = "src/main/resources/day4/input.txt";
    private static final String KEYWORD = "XMAS";
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
        for(int i=keyword.length()-1; i<grid.length; i++) { // iterate rows
            for(int j=keyword.length()-1; j<grid[0].length; j++) { // cols
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
        for(int i=keyword.length()-1; i<grid.length; i++) { // iterate rows
            for(int j=0; j<grid[0].length-keyword.length()+1; j++) { // cols
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

        int startCol = grid.length;
        int endCol = grid.length-keyword.length()+1;
        System.out.println("searchDownwards StartRow,EndRow: " + startCol + "," + endCol);
        int startRow = 0;
        int endRow = grid.length;
        System.out.println("searchDownwards StartCol,EndCol: " + startRow + "," + endRow);

        for(int i=0; i<grid.length-keyword.length()+1; i++) { // iterate rows
            for(int j=0; j<grid[0].length-keyword.length()+1; j++) { // cols
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
        for (int i=maybeWord.length(); i>0; i--) {
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
        for(int i=0; i<grid.length-keyword.length()+1; i++) { // iterate rows
            for(int j=keyword.length()-1; j<grid[0].length; j++) { // cols
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
                    // small optimisation
                    // subtract 1 here because the loop will also add 1 - so we need to negate that effect
                    j += keyword.length()-1;
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

        int startRow  = 0+keyword.length()-1;
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

        for(int i=0; i<endRow; i++) { // iterate across all rows
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

        List<Integer> xmas = day4.search(grid, "XMAS");
        int total = day4.getTotal(xmas);
        System.out.println(total);
    }
}
