import javafx.scene.paint.Color;

/**
 * Created by benra_000 on 4/23/2015.
 */
public class DoubleLetterScoreSlot extends Slot {
    public DoubleLetterScoreSlot() {
        super("DLS");
        super.setTextFill(Color.LIGHTBLUE);
    }

    @Override
    int scoreChange(int currentScore) {
        return currentScore + contents.getValue();
    }
}
