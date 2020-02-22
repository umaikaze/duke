package umaikaze.duke.ui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import umaikaze.duke.Duke;
import umaikaze.duke.DukeException;
import umaikaze.duke.Message;
import umaikaze.duke.Parser;
import umaikaze.duke.task.Event;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

// Solution adapted from https://github.com/nus-cs2103-AY1920S2/duke/blob/master/tutorials/javaFxTutorialPart4.md
// Color scheme used in this project is generated from https://coolors.co/3b1f2b-dbdfac-5f758e-cc0000-ffebcd
/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    private Duke duke;
    private Stage mainStage;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));
    private Image systemImage = new Image(this.getClass().getResourceAsStream("/images/DaSystem.png"));
    private Image meowImage = new Image(this.getClass().getResourceAsStream("/images/DaMeow.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.prefWidthProperty().bind(scrollPane.widthProperty());
    }

    public void setDuke(Duke d) {
        duke = d;
        if (duke.tl.size() == 0) {
            showDukeDialog(Message.GREETING_EMPTY_SAVE, dukeImage);
        } else {
            showDukeDialog(Message.GREETING_LOADED, dukeImage);
        }
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String[] words = input.split(" ");
        userInput.clear();
        if (input.equals("")) {
            return;
        }
        showUserDialog(input);
        if (input.toLowerCase().contains("no you don't") || input.toLowerCase().contains("no you dont")) {
            showDukeDialog(Message.BONUS, meowImage);
            return;
        }
        if (input.toLowerCase().equals("bye")) {
            hideUi();
            return;
        }
        if (words[0].equals("schedule")) {
            try {
                showSchedule(Parser.toDate(words[1]));
            } catch (DateTimeParseException | NullPointerException | ArrayIndexOutOfBoundsException e) {
                showSchedule(LocalDate.now());
            }
            return;
        }
        try {
            String response = duke.getResponse(input);
            showDukeDialog(response, dukeImage);
        }  catch (DukeException e) {
            System.out.println("Duke exception caught in handleUserInput");
            showDukeDialog(e.getMessage(), systemImage);
        } catch (DateTimeParseException e) {
            System.out.println("DateTimeParseException caught in handleUserInput");
            showDukeDialog(e.getMessage() + "\n" + Message.EXCEPTION_BAD_TIME_FORMAT, systemImage);
        } catch (IOException e) {
            System.out.println("IOException caught in handleUserInput");
            showDukeDialog(Message.EXCEPTION_UNABLE_TO_SAVE_LOAD + "\n" + e.getMessage(), systemImage);
        }
    }

    private void showUserDialog(String userText) {
        DialogBox db = DialogBox.getUserDialog(userText, userImage);
        db.bindWidthProperty(dialogContainer.widthProperty());
        dialogContainer.getChildren().add(db);
    }

    /**
     * Prints given string and image
     * @param text Only supports single line, when there are multiple lines, add your own \n\t at the end of every
     *             line (Except the last line)
     */
    private void showDukeDialog(String text, Image image) {
        if (text != null && !text.equals("")) {
            DialogBox db = DialogBox.getDukeDialog(text, image);
            db.bindWidthProperty(dialogContainer.widthProperty());
            dialogContainer.getChildren().add(db);
        }
    }

    private void hideUi() {
        showDukeDialog(Message.GREETING_GOODBYE, dukeImage);
        PauseTransition delay = new PauseTransition(Duration.millis(1000));
        delay.setOnFinished(event -> mainStage.close());
        delay.play();
    }

    private void showSchedule(LocalDate date) {
        dialogContainer.getChildren().add(
                new DaySchedule(date, duke.tl.getListAsStream()
                        .filter(task -> task instanceof Event)
                        .map(task -> (Event) task)
                        .filter(event -> event.getDate().isEqual(date))));
    }
}
