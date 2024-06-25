import java.util.ArrayList;
import java.util.List;

public class Board {
    private static final int EMPTY = 0;
    private int[][] grid;

    public Board(int width, int height) {
        grid = new int[height][width];
        reset();
    }

    public void reset() {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                grid[r][c] = EMPTY;
            }
        }
    }

    public boolean isCellEmpty(int row, int col) {
        return grid[row][col] == EMPTY;
    }

    public boolean canPlacePiece(Tetromino piece, int row, int col) {
        int[][] shape = piece.getShape();
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] != 0) {
                    int gridRow = row + r;
                    int gridCol = col + c;
                    if (gridRow < 0 || gridRow >= grid.length || gridCol < 0 || gridCol >= grid[0].length
                            || !isCellEmpty(gridRow, gridCol)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void placePiece(Tetromino piece, int row, int col) {
        int[][] shape = piece.getShape();
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] != 0) {
                    grid[row + r][col + c] = piece.getColorValue();
                }
            }
        }
    }

    public List<Integer> clearFullLines() {
        List<Integer> linesCleared = new ArrayList<>();
        for (int r = 0; r < grid.length; r++) {
            boolean isFull = true;
            for (int c = 0; c < grid[r].length; c++) {
                if (grid[r][c] == EMPTY) {
                    isFull = false;
                    break;
                }
            }
            if (isFull) {
                linesCleared.add(r);
            }
        }
        for (int line : linesCleared) {
            for (int r = line - 1; r >= 0; r--) {
                System.arraycopy(grid[r], 0, grid[r + 1], 0, grid[r].length);
            }
            for (int c = 0; c < grid[0].length; c++) {
                grid[0][c] = EMPTY;
            }
        }
        return linesCleared;
    }

    public int[][] getGrid() {
        return grid;
    }
}
