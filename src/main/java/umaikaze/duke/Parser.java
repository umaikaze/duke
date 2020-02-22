/**
 * Paser class locates the description String and parse String into LocalDate and LocalTime
 * from String provided by TaskList class
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

public class Parser {
    /**
     * @param words: Split command
     * @return Task
     * @throws DukeException: If command does not follow standards
     * @throws DateTimeParseException: If date or time does not follow standards
     */
    public static Task parse(String[] words) throws DukeException, DateTimeParseException{
        Task newTask;
        String cmd = words[0];
        String description = findDescriptionString(words);
        LocalDate date = toDate(findDateString(words));
        LocalTime time = toTime(findTimeString(words));
        Duration duration;
        switch (cmd) {
        case "deadline":
            newTask = new Deadline(description, date, time);
            break;
        case "event":
            duration = toDuration(findDurationString(words));
            newTask = new Event(description, date, time, duration);
            break;
        case "todo":
            newTask = new Todo(description);
            break;
        default:
            throw new DukeException(Message.EXCEPTION_UNKNOWN_COMMAND);
        }
        return newTask;
    }

    public static LocalDate toDate(String string) {
        return string == null ? null : LocalDate.parse(string, string.contains("-") ? DateTimeFormatter.ofPattern("d-M-yyyy")
                : DateTimeFormatter.ofPattern("d/M/yyyy"));
    }

    public static LocalTime toTime(String string) {
        return string == null ? null : LocalTime.parse(string, DateTimeFormatter.ofPattern("H:mm"));
    }

    public static Duration toDuration(String string) throws DukeException {
        try {
            return string == null ? null : Duration.ofHours(Long.parseLong(string));
        } catch (NumberFormatException e) {
            throw new DukeException(Message.EXCEPTION_DURATION_NOT_INTEGER);
        }
    }

    private static String findDateString(String[] words) {
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals("/by") || words[i].equals("/at")) {
                System.out.println("date command recognized");
                if (words.length > i + 1 && (words[i + 1].contains("/") || words[i + 1].contains("-"))) {
                    return words[i + 1];
                }
            }
        }

        return null;
    }

    private static String findTimeString(String[] words) {
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals("/by") || words[i].equals("/at")) {
                System.out.println("time command recognized");
                if (words.length > i + 2 && words[i + 2].contains(":")) {
                    return words[i + 2];
                }
            }
        }

        return null;
    }

    private static String findDurationString(String[] words) {
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals("/for")) {
                System.out.println("duration command recognized");
                if (words.length > i + 1) {
                    return words[i + 1];
                }
            }
        }

        return null;
    }

    private static String findDescriptionString(String[] words) {
        StringBuilder description = new StringBuilder("");
        for (int i = 1; i < words.length; i++) {
            if (words[i].charAt(0) == '/') {
                continue;
            }
            description.append(words[i]).append(" ");
        }

        return description.toString();
    }
}
