import javafx.concurrent.Service;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by benra_000 on 4/23/2015.
 */
public class ScrabbleGame extends BorderPane {
    public static ArrayList<String> dictionary;
    public static Tile selectedTile;
    public static Player localPlayer, opposingPlayer;
    public static ScrabbleBoard board;
    public static Turn turnData;

    private SetOFTiles tiles;
    private HBox handPane;
    private VBox playerPane;
    private Text infoText;
    private Button endTurn;
    private TurnServiceShell opponent;

    public ScrabbleGame() throws Exception {
        super();

        tiles = new SetOFTiles();
        board = new ScrabbleBoard();

        infoText = new Text("<Info Pane>");
        infoText.setStyle("-fx-font: 30 arial");
        infoText.setTextAlignment(TextAlignment.CENTER);

        handPane = new HBox(5);
        playerPane = new VBox(5);
        playerPane.setAlignment(Pos.CENTER);

        super.setCenter(board.getSlotGridPane());
        super.setBottom(handPane);
        super.setLeft(playerPane);
        super.setTop(infoText);
    }

    public ScrabbleGame(String playerName) throws Exception {
        this();

        localPlayer = new Player(playerName);
        localPlayer.setPlaying(true);
        opponent = new HostConnection(localPlayer);

        finishInitializing();
    }

    public ScrabbleGame(String playerName, String ipAddress) throws Exception {
        this();

        localPlayer = new Player(playerName);
        localPlayer.setPlaying(false);
        opponent = new ClientConnection(ipAddress);

        finishInitializing();
    }

    private void finishInitializing() throws Exception {
        opponent.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                playTurn((Turn) t.getSource().getValue());
            }
        });

        turnData = new Turn(tiles);

        if (localPlayer.isPlaying()) {
            localPlayer.addAll(tiles.drawHand());
            infoText.setText("It's your turn");
        } else {
            infoText.setText("waiting for " + opposingPlayer.getName() + " to play! ");
            opponent.start();
        }


        fillInfoPanes();
        fillDictionary();
    }

    public void playTurn(Turn turn) {
        turnData = new Turn(tiles);

        for (Position k: turn.keySet()) {
            board.get(k).setContents(turn.get(k));
            board.get(k).setLocked(true);
        }

        tiles = new SetOFTiles(turn.getUpdatedBag());
        for (Tile incoming: tiles)
            incoming.setGraphic();

        endTurn.setDisable(false);
        infoText.setText("it's your turn");
        localPlayer.setPlaying(true);
        opposingPlayer.addScore(turn.getPointsAwarded());
        board.enableLegalSlots();
        updateHand();
        updatePlayerStats();
    }

    private void fillDictionary() throws Exception {
        dictionary = new ArrayList<String>();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("dictionary.txt").getFile());
        Scanner resourceReader = new Scanner(file);

        while (resourceReader.hasNext())
            dictionary.add(resourceReader.nextLine());
    }

    public void fillInfoPanes() {
        endTurn = new Button("End Turn");
        handPane.setAlignment(Pos.CENTER);

        endTurn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!board.endTurn(obtainThis()))
                    JOptionPane.showMessageDialog(null, "not a real word!");
                else {
                    board.disable();
                    endTurn.setDisable(true);
                    infoText.setText("it's " + opposingPlayer.getName() + "'s turn");
                    localPlayer.setPlaying(false);
                    turnData.setUpdatedBag(tiles);
                    turnData.setPointsAwarded(localPlayer.getMostRecentScoreAddition());
                    opponent.sendTurn(turnData);
                    if (opponent.getState().equals(Worker.State.READY))
                        opponent.start();
                    else
                        opponent.restart();
                }
            }
        });

        if (!localPlayer.isPlaying())
            endTurn.setDisable(true);

        handPane.getChildren().add(endTurn);
        handPane.getChildren().addAll(localPlayer);

        playerPane.setAlignment(Pos.TOP_LEFT);
        updatePlayerStats();
    }

    public ScrabbleGame obtainThis() {
        return this;
    }

    public void updateHand() {
        handPane.getChildren().removeAll(localPlayer);

        for (int i = 0; i < 8; i++) {
            try {
                if (localPlayer.get(i).isInSlot())
                    localPlayer.set(i, tiles.drawTile());
            } catch (Exception e) {
                localPlayer.add(tiles.drawTile());
            }
        }

        handPane.getChildren().addAll(localPlayer);
    }

    public void updatePlayerStats() {
        playerPane.getChildren().clear();

        playerPane.getChildren().addAll(new Text(tiles.size() + " tiles left!") ,
                localPlayer.createTextLabel(),
                opposingPlayer.createTextLabel());
    }
}
