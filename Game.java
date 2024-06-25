import java.util.Random;

public class Game {
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 20;
    private static final int PIECE_COUNT = 7;
    private static final Tetromino[] PIECES = new Tetromino[PIECE_COUNT];

    private Board board;
    private Tetromino currentPiece;
    private boolean isRunning;
    private Score score; // Use Score class for managing score

    // ScoreDisplay instance
    private ScoreDisplay scoreDisplay;

    public Game(ScoreDisplay scoreDisplay) {
        this.scoreDisplay = scoreDisplay;
        this.score = new Score(); // Initialize Score instance
        board = new Board(GRID_WIDTH, GRID_HEIGHT);
        initPieces();
        reset();
    }

    private void initPieces() {
        PIECES[0] = new Tetromino(new int[][] { { 1, 1, 1, 1 } }, Color.CYAN, this); // I-piece
        PIECES[1] = new Tetromino(new int[][] { { 1, 1 }, { 1, 1 } }, Color.YELLOW, this); // O-piece
        PIECES[2] = new Tetromino(new int[][] { { 0, 1, 0 }, { 1, 1, 1 } }, Color.PURPLE, this); // T-piece
        PIECES[3] = new Tetromino(new int[][] { { 0, 1, 1 }, { 1, 1, 0 } }, Color.GREEN, this); // S-piece
        PIECES[4] = new Tetromino(new int[][] { { 1, 1, 0 }, { 0, 1, 1 } }, Color.RED, this); // Z-piece
        PIECES[5] = new Tetromino(new int[][] { { 1, 1, 1 }, { 0, 0, 1 } }, Color.BLUE, this); // J-piece
        PIECES[6] = new Tetromino(new int[][] { { 1, 1, 1 }, { 1, 0, 0 } }, Color.ORANGE, this); // L-piece
    }

    public void reset() {
        board.reset();
        spawnPiece();
        isRunning = true;
        score.reset(); // Reset score
        updateScoreDisplay(); // Update score display after reset
    }

    public void update() {
        if (isRunning) {
            if (canMoveDown()) {
                movePieceDown();
            } else {
                board.placePiece(currentPiece, currentPiece.getRow(), currentPiece.getCol());
                int linesCleared = board.clearFullLines().size();
                score.addScore((linesCleared + 1) * 10); // Increase score for each cleared line
                updateScoreDisplay(); // Update score display after clearing lines
                spawnPiece();
                if (!canMoveDown()) {
                    gameOver();
                }
            }
        }
    }

    private void spawnPiece() {
        Random random = new Random();
        int index = random.nextInt(PIECE_COUNT);
        currentPiece = new Tetromino(PIECES[index]);
        currentPiece.setRow(0);
        currentPiece.setCol(GRID_WIDTH / 2 - currentPiece.getShape()[0].length / 2);
        if (!canMoveDown()) {
            gameOver();
        }
    }

    public boolean canMoveDown() {
        return currentPiece.canMoveDown(board.getGrid());
    }

    public void movePieceDown() {
        currentPiece.moveDown();
        score.addScore(1); // Increase score for moving piece down
        updateScoreDisplay(); // Update score display after moving piece
    }

    public boolean canMoveLeft() {
        return currentPiece.canMoveLeft(board.getGrid());
    }

    public void movePieceLeft() {
        currentPiece.moveLeft();
    }

    public boolean canMoveRight() {
        return currentPiece.canMoveRight(board.getGrid());
    }

    public void movePieceRight() {
        currentPiece.moveRight();
    }

    public void rotatePiece() {
        currentPiece.rotate();
    }

    private void gameOver() {
        isRunning = false;
        // Handle game over logic, e.g., showing a message or resetting the game
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int[][] getGrid() {
        return board.getGrid();
    }

    public Tetromino getCurrentPiece() {
        return currentPiece;
    }

    public int getScore() {
        return score.getScore();
    }

    public static int getGridWidth() {
        return GRID_WIDTH;
    }

    public static int getGridHeight() {
        return GRID_HEIGHT;
    }

    // Method to update the score display
    private void updateScoreDisplay() {
        scoreDisplay.updateScore(score.getScore());
    }
}
