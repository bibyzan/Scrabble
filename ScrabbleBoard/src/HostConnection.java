import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Created by benra_000 on 4/28/2015.
 */
public class HostConnection extends TurnServiceShell {
    private ServerSocket host;
    private Socket connection;

    private ObjectOutputStream outToClient;
    private ObjectInputStream inFromClient;

    public HostConnection(Player player) {
        try {
            host = new ServerSocket(2021);

            JOptionPane.showMessageDialog(null, "waiting for connection on: " +
                    InetAddress.getLocalHost().toString().split("/")[1]);

            connection = host.accept();

            outToClient = new ObjectOutputStream(connection.getOutputStream());
            inFromClient = new ObjectInputStream(connection.getInputStream());

            outToClient.writeObject(player);
            ScrabbleGame.opposingPlayer = (Player)inFromClient.readObject();
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
