import javafx.scene.control.Label;

public class ScoreDisplay {
  private Label scoreLabel;

  public ScoreDisplay(Label scoreLabel) {
    this.scoreLabel = scoreLabel;
  }

  public void updateScore(int score) {
    scoreLabel.setText("Score: " + score);
  }
}
