import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by benra_000 on 4/23/2015.
 */
public abstract class Slot extends Button {
    protected Tile contents;
    protected boolean locked;

    public Slot(final String text) {
        super(text);
        super.setStyle("-fx-font: 20 arial");

        this.locked = true;

        super.setMaxWidth(Double.MAX_VALUE);
        super.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (isFilled()) {
                    contents.setDisable(false);
                    contents.setInSlot(false);
                    ScrabbleGame.selectedTile = contents;
                    contents = null;
                    locked = true;
                    setGraphic(text);
                } else {
                    if (ScrabbleGame.selectedTile != null)
                        setContents(ScrabbleGame.selectedTile);
                    ScrabbleGame.selectedTile = null;
                }

                ScrabbleGame.board.enableLegalSlots();
            }
        });

        setGraphic(text);
    }

    public void setGraphic(String pictureName) {
        try {
            super.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/BoardSlotGraphics/" + pictureName + ".png"))));
            super.setStyle("-fx-focus-color: transparent;-fx-faint-focus-color: transparent;");
            super.setBackground(null);
            super.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Tile getContents() {
        return contents;
    }

    public void setContents(Tile contents) {
        this.contents = contents;
        this.contents.setInSlot(true);
        this.setText(contents.getLetter());
        this.locked = false;
        this.setGraphic(contents.getLetter());
    }

    public boolean isFilled() {
        return contents != null;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    abstract int scoreChange(int currentScore);
}
