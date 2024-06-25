import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private Timer timer;
    private Game game;
    private long interval;

    public GameTimer(Game game, long interval) {
        this.game = game;
        this.interval = interval;
        this.timer = new Timer(true);
    }

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                game.update();
            }
        }, 0, interval);
    }

    public void stop() {
        timer.cancel();
    }

    public void setInterval(long interval) {
        this.interval = interval;
        stop();
        timer = new Timer(true);
        start();
    }
}
