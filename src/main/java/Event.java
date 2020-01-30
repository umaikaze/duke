import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime at;
    protected boolean hasTime = false;

    public Event(String description, String at) {
        super(description);
        if (at.length() <= 10) {
            at = at + " 23:59";
        } else {
            hasTime = true;
        }
        this.at = LocalDateTime.parse(at, DateTimeFormatter.ofPattern("d/M/yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + at.format(DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy"
                + (hasTime ? " HH:mm" : ""))) + ")";
    }
}
