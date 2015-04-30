/**
 * Created by benra_000 on 4/23/2015.
 */
public class BlankSlot extends Slot {
    public BlankSlot() {
        super("blank");
    }

    @Override
    int scoreChange(int currentScore) {
        return currentScore;
    }
}
