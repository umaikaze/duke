package umaikaze.duke.task;

import umaikaze.duke.DukeException;
import umaikaze.duke.Message;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task implements Comparable<Event> {
    protected LocalDate date;
    protected LocalTime time;
    protected Duration duration;

    public Event(String description, LocalDate date, LocalTime time, Duration duration) throws DukeException {
        super(description);

        if (date == null) {
            throw new DukeException(Message.EXCEPTION_TIMING_NOT_FOUND);
        }
        if (duration != null && time == null) {
            throw new DukeException(Message.EXCEPTION_DURATION_BUT_NO_TIMING);
        }

        this.date = date;
        this.time = time;
        this.duration = duration;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() { return time; }

    public LocalTime getEndTIme() {
        return time.plus(duration);
    }

    public String getUiString() {
        return time.format(DateTimeFormatter.ofPattern("H:mm"))
                + " ~ " + time.plus(duration).format(DateTimeFormatter.ofPattern("H:mm"));
    }

    @Override
    public String getSaveString() {
        return "E|" + (isDone ? 1 : 0) + "|" + description + "|"
                + date.format(DateTimeFormatter.ofPattern("d/M/yyyy"))
                + (time != null ? time.format(DateTimeFormatter.ofPattern(" H:mm ")) : "")
                + (duration != null ? duration.toString().replaceAll("[^\\d.]", "") : "");
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: "
                + date.format(DateTimeFormatter.ofPattern("EEEE, d MMM yyyy"))
                + (time != null ? time.format(DateTimeFormatter.ofPattern(" H:mm")) : "")
                + (duration != null ? " for " + duration.toString() : ")");
    }

    @Override
    public int compareTo(Event other) {
        return time.compareTo(other.time);
    }
}
