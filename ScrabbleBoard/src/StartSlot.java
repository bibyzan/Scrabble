import javafx.scene.paint.Color;

/**
 * Created by benra_000 on 4/23/2015.
 */
public class StartSlot extends Slot {
    public StartSlot() {
        super("start");
        super.setTextFill(Color.ORANGE);
    }

    @Override
    int scoreChange(int currentScore) {
        return currentScore * 2;
    }
}
