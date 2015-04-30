import javafx.concurrent.Service;

/**
 * Created by benra_000 on 4/29/2015.
 */
public abstract class TurnServiceShell extends Service<Turn> {
    abstract void sendTurn(Turn turn);
}
