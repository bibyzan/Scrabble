import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

/**
 * Created by benra_000 on 4/23/2015.
 */
public class Tile extends Button implements Serializable {
    private String letter;
    private int value;
    private boolean inSlot;

    public Tile(String letter, int value) {
        super(letter + value);

        this.letter = letter;
        this.value = value;
        this.inSlot = false;

        final Tile temp = this;
        super.setStyle("-fx-font: 15 arial");
        super.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ScrabbleGame.selectedTile = temp;
                for (Tile h: ScrabbleGame.localPlayer)
                    if (!h.isInSlot())
                        h.setDisable(false);

                setDisable(true);
            }
        });

        this.setGraphic();
    }

    public Tile(Tile copy) {
        this(copy.getLetter(), copy.getValue());
    }

    public void setGraphic() {
        try {
            super.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("\\TileGraphics\\" + letter + ".png"))));
            super.setStyle("-fx-focus-color: transparent;-fx-faint-focus-color: transparent;");
            super.setBackground(null);
            super.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isInSlot() {
        return inSlot;
    }

    public void setInSlot(boolean inSlot) {
        this.inSlot = inSlot;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "letter='" + letter + '\'' +
                ", value=" + value +
                '}';
    }
}
