import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by benra_000 on 4/28/2015.
 */
public class Launcher extends BorderPane {
    private TextField enterName, enterIp;

    public Launcher(final Stage parentStage) throws Exception {
        super();
        enterName = new TextField(""); enterIp = new TextField("localhost");

        Button hostLauncher = new Button("Host");
        hostLauncher.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    parentStage.setScene(new Scene(new ScrabbleGame(enterName.getText())));
                    parentStage.centerOnScreen();
                } catch (Exception e) { e.printStackTrace(); }
            }
        });

        Button connectLauncher = new Button("Connect");
        connectLauncher.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    parentStage.setScene(new Scene(new ScrabbleGame(enterName.getText(), enterIp.getText())));
                    parentStage.centerOnScreen();
                } catch (Exception e) { e.printStackTrace(); }
            }
        });


        GridPane center = new GridPane();
        center.add(new Text("Enter name: "), 0, 0);
        center.add(enterName, 1, 0);

        center.add(new Text("Host IP: "), 0, 1);
        center.add(enterIp, 1, 1);

        center.add(hostLauncher, 0, 2);
        center.add(connectLauncher, 1 , 2);

        super.setCenter(center);
    }

}
