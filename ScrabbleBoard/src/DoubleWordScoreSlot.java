import javafx.scene.paint.Color;

/**
 * Created by benra_000 on 4/23/2015.
 */
public class DoubleWordScoreSlot extends Slot {
    public DoubleWordScoreSlot() {
        super("DWS");
        super.setTextFill(Color.PINK);
    }

    @Override
    int scoreChange(int currentScore) {
        return currentScore + contents.getValue();
    }
}
