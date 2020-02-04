package umaikaze.duke;

import umaikaze.duke.task.Task;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Ui {
    PrintStream out;

    public Ui() throws UnsupportedEncodingException {
        out = new PrintStream(System.out, true, "UTF-8");
        showHeading();
    }

    public void showHeading() {
        showReply("hewwo fwom\n\t _____     _\n\t"
                + "|     |___| |_ ___ ___ ___ ___ ___ ___\n\t"
                + "|   --| .'|  _| . | -_|  _|_ -| . |   |\n\t"
                + "|_____|__,|_| |  _|___|_| |___|___|_|_|\n\t"
                + "              |_|");
    }

    public void showReply(String line) {
        StringBuilder sb = new StringBuilder("\t");
        sb.append("*".repeat(60)).append("\n\t");
        sb.append(line).append("\n\t");
        sb.append("*".repeat(60)).append("\n");
        out.print(sb.toString());
    }

    public void showError(String error) {
        StringBuilder sb = new StringBuilder("\t");
        sb.append("x".repeat(60)).append("\n\t");
        sb.append(error).append("\n\t");
        sb.append("x".repeat(60)).append("\n");
        out.print(sb.toString());
    }
}
