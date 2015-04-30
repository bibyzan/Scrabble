import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by benra_000 on 4/23/2015.
 */
public class ScrabbleApplicationFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(new Launcher(primaryStage)));
        primaryStage.show();
    }
}
