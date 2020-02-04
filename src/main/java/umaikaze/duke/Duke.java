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
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Duke extends Application{
    Ui ui;
    Storage st;
    TaskList tl;

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

    /**
     * Initialize the Duke object
     * Sets the save directory for the Storage during initialization
     */
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

    @Override
    public void start(Stage stage) {
        Label helloWorld = new Label("Hewwo Wowwd (・`ω´・) ");
        helloWorld.setFont(new Font("Comic Sans MS", 22));
        Scene scene = new Scene(helloWorld);

        stage.setScene(scene);
        stage.show();
    }
}
