/**
 * Output String message to console
 * Formats them with dividers
 */

package umaikaze.duke;

import umaikaze.duke.task.Task;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Ui {
    PrintStream out;

    /**
     * While instantiating the Ui, header is printed automatically
     */
    public Ui() throws UnsupportedEncodingException {
        out = new PrintStream(System.out, true, "UTF-8");
        showHeading();
    }

    /**
     * TBD: Use a static final constant
     */
    public void showHeading() {
        showReply("hewwo fwom\n\t _____     _\n\t"
                + "|     |___| |_ ___ ___ ___ ___ ___ ___\n\t"
                + "|   --| .'|  _| . | -_|  _|_ -| . |   |\n\t"
                + "|_____|__,|_| |  _|___|_| |___|___|_|_|\n\t"
                + "              |_|");
    }

    /**
     * Prints given String to console
     * @param line Only supports single line, when there are multiple lines, add your own \n\t at the end of every
     *             line (Except the last line)
     */
    public void showReply(String line) {
        StringBuilder sb = new StringBuilder("\t");
        sb.append("*".repeat(60)).append("\n\t");
        sb.append(line).append("\n\t");
        sb.append("*".repeat(60)).append("\n");
        out.print(sb.toString());
    }

    /**
     * Same as showReply but different divider
     */
    public void showError(String error) {
        StringBuilder sb = new StringBuilder("\t");
        sb.append("x".repeat(60)).append("\n\t");
        sb.append(error).append("\n\t");
        sb.append("x".repeat(60)).append("\n");
        out.print(sb.toString());
    }

    /**
     * Prints the given List<Task>
     * @param list can be empty or popularized
     * TBD: To be moved under TaskList class itself
     */
    public void printList(List<Task> list) {
        StringBuilder sb = new StringBuilder("\t");
        sb.append("*".repeat(60)).append("\n\tHewe awe the tasks in youw wist:\n\t");
        if (list.size() == 0) {
            sb.append("list is empty ^qwq^\n\t");
        } else {
            for (int i = 1; i <= list.size(); i++) {
                sb.append(i).append(". ").append(list.get(i - 1)).append("\n\t");
            }
        }
        sb.append("*".repeat(60)).append("\n");
        out.print(sb.toString());
    }
}
