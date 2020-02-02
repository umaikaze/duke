package umaikaze.duke.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime at;
    protected boolean hasTime;

    public Event(String description, LocalDateTime at, boolean hasTime) {
        super(description);
        this.at = at;
        this.hasTime = hasTime;
    }

    @Override
    public String getSaveString() {
        return "E|" + (isDone ? 1 : 0) + "|" + description + "|"
                + at.format(DateTimeFormatter.ofPattern("d/M/yyyy" + (hasTime ? " H:mm" : "")));
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + at.format(DateTimeFormatter.ofPattern("EEEE, d MMM yyyy"
                + (hasTime ? " H:mm" : ""))) + ")";
    }
}
