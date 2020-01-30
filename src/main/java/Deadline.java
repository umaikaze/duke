import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime by;
    protected boolean hasTime = false;

    public Deadline(String description, String by) {
        super(description);
        if (by.length() <= 10) {
            by = by + " 23:59";
        } else {
            hasTime = true;
        }
        this.by = LocalDateTime.parse(by, DateTimeFormatter.ofPattern("d/M/yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy"
                + (hasTime ? " HH:mm" : ""))) + ")";
    }
}