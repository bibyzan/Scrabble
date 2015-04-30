import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Created by benra_000 on 4/23/2015.
 */
public class Player extends ArrayList<Tile> {
    private String name;
    private int score, mostRecentScoreAddition;
    private boolean playing;

    public Player(String name) {
        super();
        this.name = name;
        this.score = 0;
        this.playing = false;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
        for (Tile element: this)
            element.setDisable(!playing);
    }

    public Text createTextLabel() {
        Text temp = new Text(toString());
        temp.setFont(new Font("Monospaced", 20));

        return temp;
    }

    public int getMostRecentScoreAddition() {
        return mostRecentScoreAddition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addScore(int addition) {
        this.score += addition;
        this.mostRecentScoreAddition = addition;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return name + ": " + score + " points";
    }
}
