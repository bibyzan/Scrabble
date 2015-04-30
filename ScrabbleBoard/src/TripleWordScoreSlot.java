import javafx.scene.paint.Color;

/**
 * Created by benra_000 on 4/23/2015.
 */
public class TripleWordScoreSlot extends Slot {
    public TripleWordScoreSlot() {
        super("TWS");
        super.setTextFill(Color.RED);
    }

    @Override
    int scoreChange(int currentScore) {
        return currentScore * 3;
    }
}
