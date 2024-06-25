public class Tetromino {
    private int[][] shape;
    private Game game;
    private Color color;
    private int row;
    private int col;

    public Tetromino(int[][] shape, Color color, Game game) {
        this.shape = shape;
        this.color = color;
        this.game = game;
        this.row = 0;
        this.col = 0;
    }

    public Tetromino(Tetromino tetromino) {
        this.shape = tetromino.shape;
        this.color = tetromino.color;
        this.game = tetromino.game;
        this.row = tetromino.row;
        this.col = tetromino.col;
    }

    public int[][] getShape() {
        return shape;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Color getColor() {
        return color;
    }

    public int getColorValue() {
        return color.ordinal() + 1; // Assuming color ordinal corresponds to the values used in Board
    }

    public void rotate() {
        int[][] rotated = new int[shape[0].length][shape.length];
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                rotated[c][shape.length - 1 - r] = shape[r][c];
            }
        }
        if (canMove(rotated)) {
            shape = rotated;
        }
    }

    public boolean canMove(int[][] moved) {
        if (game == null) {
            return false;
        }

        int gridHeight = Game.getGridHeight();
        int gridWidth = Game.getGridWidth();

        for (int r = 0; r < moved.length; r++) {
            for (int c = 0; c < moved[0].length; c++) {
                if (moved[r][c] != 0) {
                    int newR = row + r;
                    int newC = col + c;
                    if (newR < 0 || newR >= gridHeight || newC < 0 || newC >= gridWidth
                            || game.getGrid()[newR][newC] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean canMoveDown(int[][] grid) {
        int[][] moved = new int[shape.length][shape[0].length];
        for (int r = 0; r < shape.length; r++) {
            System.arraycopy(shape[r], 0, moved[r], 0, shape[r].length);
        }
        for (int r = shape.length - 1; r >= 0; r--) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] != 0) {
                    int newR = row + r + 1;
                    if (newR >= Game.getGridHeight() || grid[newR][col + c] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void moveDown() {
        row++;
    }

    public boolean canMoveLeft(int[][] grid) {
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] != 0) {
                    int newC = col + c - 1;
                    if (newC < 0 || grid[row + r][newC] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void moveLeft() {
        col--;
    }

    public boolean canMoveRight(int[][] grid) {
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] != 0) {
                    int newC = col + c + 1;
                    if (newC >= Game.getGridWidth() || grid[row + r][newC] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void moveRight() {
        col++;
    }
}
