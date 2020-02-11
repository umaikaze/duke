/**
 * Main class for the Duke project
 */

package umaikaze.duke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.time.format.DateTimeParseException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Duke extends Application{
    Ui ui;
    Storage st;
    TaskList tl;

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;
    private Image user = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image duke = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    public Duke() throws UnsupportedEncodingException {
        ui = new Ui();
        st = new Storage("../src/data/data.txt");
        try {
            tl = new TaskList(st.loadFile());
        } catch (IOException e) {
            ui.showError(e.getMessage());
            tl = new TaskList();
        }
    }

    /**
     * Handles the 3 main types of commands, passes the handling to TaskList for adding tasks
     */
    /*
    void run(BufferedReader br) throws IOException {
        ui.showReply("how may i sewve u today nya?");
        String[] line = br.readLine().split(" ");
        String cmd = line[0];
        while (!cmd.equals("bye")) {
            try {
                switch (cmd) {
                case "list":
                    ui.showReply(tl.toString());
                    break;
                case "done":
                    ui.showReply(tl.markDone(Integer.parseInt(line[1]) - 1));
                    break;
                case "delete":
                    ui.showReply(tl.delete(Integer.parseInt(line[1]) - 1));
                    break;
                case "find":
                    ui.showReply(tl.getFindString(line));
                    break;
                default:
                    ui.showReply(tl.addTask(line));
                    break;
                }
                if (!cmd.equals("list")) {
                    tl.saveFile(st);
                }
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            } catch (DateTimeParseException e) {
                ui.showError(e.getMessage() + "\n\tYouw date and time fowmat is invawid ^;;w;;^ "
                        + "Make suwe to follow d/M/yyyy fowmat followed by optionyal 24 hour time H:mm (^・`ω´・^)");
            } catch (IOException e) {
                ui.showError("Oh nyo ^;;w;;^  I was unyabwe to save / load because:\n\t" + e.getMessage());
            }
            line = br.readLine().split(" ");
            cmd = line[0];
        }
        ui.showReply("Bye. Hope to see you again soon ^>w<^");
    }
     */

    /**
     * Initialize the Duke object
     * Sets the save directory for the Storage during initialization
     */
    /*
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            Duke d = new Duke();
            d.run(br);
        } catch (UnsupportedEncodingException e) {
            System.out.println("^;;w;;^ The programmer was clumsy and set an unsupported output encoding: "
                    + e.getMessage());
        } catch (IOException e) {
            System.out.println("^;;w;;^ a fatal input / output error has occurred: " + e.getMessage());
        }
    }
     */

    @Override
    public void start(Stage stage) {
        Label helloWorld = new Label("Hewwo Wowwd (・`ω´・) ");
        helloWorld.setFont(new Font("Comic Sans MS", 22));
        Scene scene = new Scene(helloWorld);

        setUiElements();

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.show();

        setUiFormatting(stage, mainLayout);

        setAction();
    }

    private void setUiElements() {
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");
    }

    private void setUiFormatting(Stage stage, AnchorPane mainLayout) {
        stage.setTitle("Cat Person");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput , 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);
    }

    private void setAction() {
        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });

        userInput.setOnAction((event) -> {
            handleUserInput();
        });

        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
    }

    /**
     * Iteration 1:
     * Creates a label with the specified text and adds it to the dialog container.
     * @param text String containing text to add
     * @return a label with the specified text that has word wrap enabled.
     */
    private Label getDialogLabel(String text) {
        Label textToAdd = new Label(text);
        textToAdd.setWrapText(true);

        return textToAdd;
    }

    /**
     * Iteration 2:
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    private void handleUserInput() {
        Label userText = new Label(userInput.getText());
        String response = getResponse(getResponse(userInput.getText()));
        Label dukeText = new Label(response);
        dialogContainer.getChildren().addAll(
                new DialogBox(userText, new ImageView(user)),
                new DialogBox(dukeText, new ImageView(duke))
        );
        userInput.clear();
    }

    private String getResponse(String line) {
        String response;
        try {
            String[] words = line.split(" ");
            switch (words[0]) {
            case "bye":
                response = "Bye. Hope to see you again soon ^>w<^";
                break;
            case "list":
                response = tl.toString();
                break;
            case "done":
                response = tl.markDone(Integer.parseInt(words[1]) - 1);
                break;
            case "delete":
                response = tl.delete(Integer.parseInt(words[1]) - 1);
                break;
            case "find":
                response = tl.getFindString(words);
                break;
            default:
                response = tl.addTask(words);
                break;
            }
            if (!(words[0].equals("list") || words[0].equals("bye"))) {
                tl.saveFile(st);
            }
        } catch (DukeException e) {
            ui.showError(e.getMessage());
            return e.getMessage();
        } catch (DateTimeParseException e) {
            String error = e.getMessage() + "\n\tYouw date and time fowmat is invawid ^;;w;;^ "
                    + "Make suwe to follow d/M/yyyy fowmat followed by optionyal 24 hour time H:mm (^・`ω´・^)";
            ui.showError(error);
            return error;
        } catch (IOException e) {
            String error ="Oh nyo ^;;w;;^  I was unyabwe to save / load because:\n\t" + e.getMessage();
            ui.showError(error);
            return error;
        }
        return response;
    }
}
