import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by benra_000 on 4/28/2015.
 */
public class Turn extends HashMap<Position, Tile> {
    private boolean gameOver;
    private SetOFTiles updatedBag;
    private int pointsAwarded;

    public Turn(SetOFTiles tiles) {
        super();

        updatedBag = tiles;
        gameOver = false;
    }

    public Tile get(Position p) {
        for (Position k: super.keySet())
            if (k.equals(p))
                return super.get(k);

        return null;
    }

    public SetOFTiles getUpdatedBag() {
        return updatedBag;
    }

    public void setUpdatedBag(SetOFTiles updatedBag) {
        this.updatedBag = updatedBag;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getPointsAwarded() {
        return pointsAwarded;
    }

    public void setPointsAwarded(int pointsAwarded) {
        this.pointsAwarded = pointsAwarded;
    }
}
