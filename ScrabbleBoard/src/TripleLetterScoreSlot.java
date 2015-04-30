import javafx.scene.paint.Color;

/**
 * Created by benra_000 on 4/23/2015.
 */
public class TripleLetterScoreSlot extends Slot {
    public TripleLetterScoreSlot() {
        super("TLS");
        super.setTextFill(Color.BLUE);
    }

    @Override
    int scoreChange(int currentScore) {
        return currentScore + (contents.getValue() * 2);
    }
}
