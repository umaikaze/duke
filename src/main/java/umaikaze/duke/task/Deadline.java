package umaikaze.duke.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime by;
    protected boolean hasTime;

    public Deadline(String description, LocalDateTime by, boolean hasTime) {
        super(description);
        this.by = by;
        this.hasTime = hasTime;
    }

    @Override
    public String getSaveString() {
        return "D|" + (isDone ? 1 : 0) + "|" + description + "|"
                + by.format(DateTimeFormatter.ofPattern("d/M/yyyy" + (hasTime ? " H:mm" : "")));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("EEEE, d MMM yyyy"
                + (hasTime ? " H:mm" : ""))) + ")";
    }
}