/**
 * Main class for the Duke project
 */

package umaikaze.duke;

import java.io.IOException;
import java.time.format.DateTimeParseException;

public class Duke{
    Storage st;
    TaskList tl;

    public Duke() throws IOException, DukeException {
        System.out.println("Working Directory = " +
                System.getProperty("user.dir") + "\n");
        st = new Storage("./src/main/resources/data/data.txt");
        tl = new TaskList(st.loadFile());
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

    /**
     *
     */
    public String getResponse(String line) throws DukeException, DateTimeParseException, IOException{
        String response;
        System.out.println("Input received: " + line);
        String[] words = line.split(" ");
        switch (words[0]) {
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
        if (!(words[0].equals("list"))) {
            tl.saveFile(st);
        }
        return response;
    }
}
