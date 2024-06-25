import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputHandler {
    private Game game;

    public InputHandler(Game game) {
        this.game = game;
    }

    public void handle(KeyEvent event) {
        if (game.isRunning()) {
            KeyCode code = event.getCode();
            switch (code) {
                case LEFT:
                    if (game.canMoveLeft())
                        game.movePieceLeft();
                    break;
                case RIGHT:
                    if (game.canMoveRight())
                        game.movePieceRight();
                    break;
                case DOWN:
                    if (game.canMoveDown())
                        game.movePieceDown();
                    break;
                case UP:
                    game.rotatePiece();
                    break;
                case SPACE:
                    while (game.canMoveDown())
                        game.movePieceDown();
                    break;
                default:
                    break;
            }
        }
    }
}
