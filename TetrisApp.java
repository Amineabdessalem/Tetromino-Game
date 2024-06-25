import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TetrisApp extends Application {
    private static final int BLOCK_SIZE = 30;
    private static final int WINDOW_WIDTH = Game.getGridWidth() * BLOCK_SIZE;
    private static final int WINDOW_HEIGHT = Game.getGridHeight() * BLOCK_SIZE;

    private Game game;
    private GameTimer gameTimer;
    private InputHandler inputHandler;

    public static void main(String[] args) {
        launch(args);
    }

    @Override

    public void start(Stage primaryStage) {
        // Create a Label for score display
        Label scoreLabel = new Label("Score: 0");

        // Create ScoreDisplay instance with the scoreLabel
        ScoreDisplay scoreDisplay = new ScoreDisplay(scoreLabel);

        // Instantiate Game with ScoreDisplay instance
        game = new Game(scoreDisplay);

        gameTimer = new GameTimer(game, 500);
        inputHandler = new InputHandler(game);

        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        BorderPane root = new BorderPane();
        root.setCenter(canvas); // Place canvas in the center
        root.setTop(scoreLabel); // Place scoreLabel at the top

        // Adjusting alignment of scoreLabel
        BorderPane.setAlignment(scoreLabel, Pos.CENTER);
        BorderPane.setMargin(scoreLabel, new Insets(10));

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setOnKeyPressed(this::handleInput);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tetris");
        primaryStage.show();

        gameTimer.start();
        render(gc);
    }

    private void handleInput(KeyEvent event) {
        inputHandler.handle(event);
    }

    private void render(GraphicsContext gc) {
        new javafx.animation.AnimationTimer() {
            @Override
            public void handle(long now) {
                drawBoard(gc);
                drawCurrentPiece(gc);
            }
        }.start();
    }

    private void drawBoard(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        int[][] grid = game.getGrid();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] != 0) {
                    gc.setFill(getColor(grid[row][col]));
                    gc.fillRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
    }

    private void drawCurrentPiece(GraphicsContext gc) {
        Tetromino piece = game.getCurrentPiece();
        int[][] shape = piece.getShape();
        int row = piece.getRow();
        int col = piece.getCol();
        gc.setFill(getColor(piece.getColor().ordinal() + 1));
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] != 0) {
                    gc.fillRect((col + c) * BLOCK_SIZE, (row + r) * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
    }

    private javafx.scene.paint.Color getColor(int colorValue) {
        switch (colorValue) {
            case 1:
                return javafx.scene.paint.Color.CYAN;
            case 2:
                return javafx.scene.paint.Color.YELLOW;
            case 3:
                return javafx.scene.paint.Color.PURPLE;
            case 4:
                return javafx.scene.paint.Color.GREEN;
            case 5:
                return javafx.scene.paint.Color.RED;
            case 6:
                return javafx.scene.paint.Color.BLUE;
            case 7:
                return javafx.scene.paint.Color.ORANGE;
            default:
                return javafx.scene.paint.Color.BLACK;
        }
    }
}
