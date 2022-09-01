package four;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Cheker {

    public static Cell getFirstFreeCell(List<Cell> cells) {
        Cell res = null;
        for (int i  = cells.size() - 1; i >= 0; i--) {
            if (" ".equals(cells.get(i).getText())) {
                res = cells.get(i);
                break;
            }
        }
        return res;
    }

    public static boolean isWin(Cell[][] matrix, Color color) {
        return checkColWin(matrix, color) || checkRowWin(matrix, color) || checkDiagonalWin(matrix, color);
    }

    private static boolean checkColWin(Cell[][] matrix, Color color) {

        for (int j = 0; j < matrix[0].length; ++j) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < matrix.length; ++i) {
                Cell currentCell = matrix[i][j];
                String currentCellText = currentCell.getText();
                stringBuilder.append(currentCellText);

            }
            if (findMatches(matrix,color, stringBuilder.toString(), j, "col")) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkRowWin(Cell[][] matrix, Color color) {
        for (int i = 0; i < matrix.length; ++i) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < matrix[i].length; ++j) {
                Cell currentCell = matrix[i][j];
                String currentCellText = currentCell.getText();
                stringBuilder.append(currentCellText);

            }
            if (findMatches(matrix,color, stringBuilder.toString(), i, "row")) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkDiagonalWin(Cell[][] matrix, Color color) {
        List<List<Cell>> diagonalsPositive = findDiagonalsPositive(matrix);
        List<List<Cell>> diagonalsNegative = findDiagonalsNegative(matrix);

        return checkDiagonals(diagonalsPositive, color) || checkDiagonals(diagonalsNegative, color);
    }

    private static boolean checkDiagonals(List<List<Cell>> diagonals, Color color) {
        for (int i = 0; i < diagonals.size(); ++i) {
            List<Cell> c = new ArrayList<>();
            int sizeDiagonal = diagonals.get(i).size();
            if (sizeDiagonal >= 4) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < sizeDiagonal; ++j) {
                    Cell currentCell = diagonals.get(i).get(j);
                    c.add(currentCell);
                    String currentCellText = currentCell.getText();
                    stringBuilder.append(currentCellText);
                }

                int indexX = stringBuilder.indexOf("XXXX");
                int indexO = stringBuilder.indexOf("OOOO");
                int k = -1;

                if (indexO != -1 ) {
                    k = indexO;
                }

                if (indexX != -1) {
                    k = indexX;
                }
                List<Cell> winCell = new ArrayList<>(4);
                if (k != -1) {

                    int index = k;
                    while (k < index + 4) {
                        winCell.add(c.get(k));
                        k++;
                    }
                }

                if (!winCell.isEmpty()) {
                    printWin(winCell, color);
                    return true;
                }

            }

        }
        return false;
    }



    private static void printWin(List<Cell> cellArrayList, Color color) {
        for (Cell cell : cellArrayList) {
            cell.setBackground(color);
        }
    }

    private static boolean findMatches(Cell[][] matrix, Color color, String str, int i, String direction) {
        List<Cell> cellArrayList = new ArrayList<>(4);

        int indexX = str.indexOf("XXXX");
        int indexO = str.indexOf("OOOO");
        int k = -1;

        if (indexO != -1 ) {
            k = indexO;
        }

        if (indexX != -1) {
            k = indexX;
        }

        if (k != -1) {
            int index = k;
            while (k < index + 4) {
                if ("col".equals(direction)) {
                    cellArrayList.add(matrix[k][i]);
                } else if ("row".equals(direction)) {
                    cellArrayList.add(matrix[i][k]);
                } else if ("diag".equals(direction)) {
                    cellArrayList.add(matrix[i++][k++]);
                }

                k++;
            }
        }

        if (!cellArrayList.isEmpty()) {
            printWin(cellArrayList, color);
            return true;
        } else {
            cellArrayList.clear();
        }
        return false;
    }



    private static List<List<Cell>> findDiagonalsNegative(Cell[][] matrix) {
        final List<List<Cell>> diags = new ArrayList<>();
        for (int row = matrix.length - 1; row > 1; --row) {
            diags.add(getDiagonal(matrix, 0, row));
        }
        for (int col = 0; col < matrix[0].length; ++col) {
            diags.add(getDiagonal(matrix,col, 0));
        }
        return diags;
    }

    private static List<Cell> getDiagonal(Cell[][] matrix, int x, int y) {
        final List<Cell> diag = new ArrayList<>();
        while (x < matrix.length && y < matrix[0].length) {
            diag.add(matrix[x++][y++]);
        }
        return diag;
    }


    private static List<List<Cell>> findDiagonalsPositive(Cell[][] matrix) {
        final List<List<Cell>> diags = new ArrayList<>();
        List<Cell> diag = new ArrayList<>();


        if (matrix == null || matrix.length == 0) {
            return null;
        }

        int M = matrix.length;
        int N = matrix[0].length;

        // print `/` diagonal for the upper-left half of the matrix
        for (int r = 0; r < M; r++)
        {
            // start from each cell of the first column
            for (int i = r, j = 0; j < N && i >= 0; i--, j++) {
                diag.add(matrix[i][j]);
            }
            diags.add(diag);
            diag = new ArrayList<>();
        }

        // print `/` diagonal for the lower-right half of the matrix
        for (int c = 1; c < N; c++)
        {
            // start from each cell of the last row
            for (int i = M - 1, j = c; j < N && i >= 0; i--, j++) {
                diag.add(matrix[i][j]);
            }

            diags.add(diag);
            diag = new ArrayList<>();
        }
        return diags;
    }

}

