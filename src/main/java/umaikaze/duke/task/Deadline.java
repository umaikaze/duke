package umaikaze.duke.task;

import umaikaze.duke.DukeException;
import umaikaze.duke.Message;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDate date;
    protected LocalTime time;

    public Deadline(String description, LocalDate date, LocalTime time) throws DukeException{
        super(description);

        if (date == null) {
            throw new DukeException(Message.EXCEPTION_TIMING_NOT_FOUND);
        }

        this.date = date;
        this.time = time;
    }

    @Override
    public String getSaveString() {
        return "D|" + (isDone ? 1 : 0) + "|" + description + "|"
                + date.format(DateTimeFormatter.ofPattern("d/M/yyyy"))
                + (time != null ? time.format(DateTimeFormatter.ofPattern(" H:mm")) : "");
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + date.format(DateTimeFormatter.ofPattern("EEEE, d MMM yyyy"))
                + (time != null ? time.format(DateTimeFormatter.ofPattern(" H:mm)")) : ")");
    }
}