/**
 * Main Ui which the user interact with, the Application launched by Launcher
 */



package umaikaze.duke;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import umaikaze.duke.ui.MainWindow;

// Solution adapted from https://github.com/nus-cs2103-AY1920S2/duke/blob/master/tutorials/javaFxTutorialPart4.md
public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try {
            Duke duke = new Duke();
            System.out.println("Duke class initialized " + duke);

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setDuke(duke);
            fxmlLoader.<MainWindow>getController().setMainStage(stage);
            stage.setTitle("Cat Person");
            stage.show();
        } catch (IOException | DukeException e) {
            e.printStackTrace();
        }
    }
}
