/**
 * This class is used by Storage.loadFile to read file following the saveString format strictly
 */

package umaikaze.duke;

import umaikaze.duke.task.Deadline;
import umaikaze.duke.task.Event;
import umaikaze.duke.task.Task;
import umaikaze.duke.task.Todo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StorageParser {
    String description;
    LocalDate date;
    LocalTime time;
    Duration duration;

    private StorageParser(String description, String timeStr) {
        this.description = description;
        parseTime(timeStr);
    }

    public static Task parse(String[] words) throws DukeException {
        Task task = null;
        StorageParser p;
        try {
            switch (words[0]) {
            case "T":
                task = new Todo(words[2]);
                System.out.println("Loaded a todo:" + task.getDescription());
                break;
            case "D":
                p = new StorageParser(words[2], words[3]);
                task = new Deadline(p.description, p.date, p.time);
                System.out.println("Loaded a deadline:" + task.getDescription());
                break;
            default:
                p = new StorageParser(words[2], words[3]);
                task = new Event(p.description, p.date, p.time, p.duration);
                System.out.println("Loaded an event:" + task.getDescription());
                break;
            }
            if (words[1].equals("1")) {
                task.markDone();
            }
        } catch (DateTimeParseException | NullPointerException e) {
            System.out.println("Parsing failed, skipping line");
        }

        return task;
    }

    private void parseTime(String str) throws DateTimeParseException {
        String[] dateTime = str.split(" ");
        if (dateTime.length == 1) {
            date = LocalDate.parse(dateTime[0], DateTimeFormatter.ofPattern("d/M/yyyy"));
            time = null;
        } else if (dateTime.length == 2) {
            date = LocalDate.parse(dateTime[0], DateTimeFormatter.ofPattern("d/M/yyyy"));
            time = LocalTime.parse(dateTime[1], DateTimeFormatter.ofPattern("H:mm"));
        } else {
            date = LocalDate.parse(dateTime[0], DateTimeFormatter.ofPattern("d/M/yyyy"));
            time = LocalTime.parse(dateTime[1], DateTimeFormatter.ofPattern("H:mm"));
            duration = Duration.ofHours(Long.parseLong(dateTime[2]));
        }
    }
}
