package umaikaze.duke.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import umaikaze.duke.task.Event;

import java.io.IOException;

public class EventUi extends VBox {
    @FXML
    private Label time;
    @FXML
    private Label eventName;
    public EventUi(Event event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/EventUi.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(time);
        time.setText(event.getUiString());
        eventName.setText(event.getDescription());
    }
}
