package umaikaze.duke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeParseException;

public class Duke {
    Ui ui;
    Storage st;
    TaskList tl;

    public Duke(String savePath) {
        ui = new Ui();
        ui.showHeading();
        st = new Storage(savePath);
        try {
            tl = new TaskList(st.loadFile());
        } catch (IOException e) {
            ui.showError(e.getMessage());
            tl = new TaskList();
        }
    }

    void run(BufferedReader br) throws IOException {
        ui.showReply("how may i sewve u today nya?");
        String[] line = br.readLine().split(" ");
        String cmd = line[0];
        while (!cmd.equals("bye")) {
            try {
                switch (cmd) {
                    case "list":
                        tl.printList(ui);
                        break;
                    case "done":
                        ui.showReply(tl.markDone(Integer.parseInt(line[1]) - 1));
                        break;
                    case "delete":
                        ui.showReply(tl.delete(Integer.parseInt(line[1]) - 1));
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

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Duke d = new Duke("../src/data/data.txt");
        try {
            d.run(br);
        } catch (IOException e) {
            d.ui.showError(e.getMessage());
        }
    }
}
