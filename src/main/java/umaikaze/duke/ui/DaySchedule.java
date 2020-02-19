package umaikaze.duke.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import umaikaze.duke.task.Event;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class DaySchedule extends VBox {
    @FXML
    private Label date;
    PriorityQueue<Event> q = new PriorityQueue<>();

    public DaySchedule(LocalDate date, Stream<Event> events) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DaySchedule.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        events.forEach(event -> {
            q.add(event);
        });
        this.date.setText(date.format(DateTimeFormatter.ofPattern("EEEE, d MMM yyyy")));
        while (q.peek() != null) {
            getChildren().add(new EventUi(q.poll()));
        }
    }
}
