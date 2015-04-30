import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Created by benra_000 on 4/28/2015.
 */
public class ClientConnection extends TurnServiceShell {
    private Socket connection;

    private ObjectOutputStream outToClient;
    private ObjectInputStream inFromClient;

    public ClientConnection(String ip) {
        try {
            connection = new Socket(ip, 2021);

            outToClient = new ObjectOutputStream(connection.getOutputStream());
            inFromClient = new ObjectInputStream(connection.getInputStream());

            ScrabbleGame.opposingPlayer = (Player)inFromClient.readObject();
            outToClient.writeObject(ScrabbleGame.localPlayer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    void sendTurn(Turn turn) {
        try {
            outToClient.writeObject(turn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Task<Turn> createTask() {
        return new Task<Turn>() {
            protected Turn call() throws Exception {
                return (Turn)inFromClient.readObject();
            }
        };
    }
}
