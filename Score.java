public class Score {
    private int score;

    public Score() {
        this.score = 0;
    }

    public void addScore(int points) {
        score += points;
    }

    public void reset() {
        score = 0;
    }

    public int getScore() {
        return score;
    }
}
