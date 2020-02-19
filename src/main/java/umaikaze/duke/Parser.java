/**
 * Paser class locates the description String and parse String into LocalDate and LocalTime
 * from String provided by TaskList class
 */

package umaikaze.duke;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Parser {
    String description;
    char separator;
    LocalDate date = null;
    LocalTime time = null;
    Duration duration = null;

    /**
     * This constructor is used by TaskList.getTask
     */
    public Parser(String[] words) {
        setDateTime(words);
        setDescriptionString(words);
    }

    public static LocalDate toDate(String string) {
        return LocalDate.parse(string, string.contains("-") ? DateTimeFormatter.ofPattern("d-M-yyyy")
                : DateTimeFormatter.ofPattern("d/M/yyyy"));
    }

    private void setDateTime(String[] words) {
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals("/by") || words[i].equals("/at")) {
                System.out.println("time command recognized");
                if (words.length > i + 1 && (words[i + 1].contains("/") || words[i + 1].contains("-"))) {
                    separator = words[i + 1].contains("/") ? '/' : '-';
                    date = LocalDate.parse(words[i + 1], DateTimeFormatter.ofPattern("d" + separator + "M"
                            + separator + "yyyy"));
                }
                if (words.length > i + 2 && words[i + 2].contains(":")) {
                    time = LocalTime.parse(words[i + 2], DateTimeFormatter.ofPattern("H:mm"));
                }
            }
            if (words[i].equals("/for")) {
                System.out.println("duration command recognized");
                if (words.length > i + 1) {
                    duration = Duration.ofHours(Long.parseLong(words[i + 1]));
                }
            }
        }
    }

    private void setDescriptionString(String[] words) {
        StringBuilder description = new StringBuilder("");
        for (int i = 1; i < words.length; i++) {
            if (words[i].charAt(0) == '/') {
                if (i + 2 < words.length && words[i + 2].contains(":")) {
                    i += 2;
                } else {
                    i += 1;
                }
                continue;
            }
            description.append(words[i]).append(" ");
        }
        this.description = description.toString();
    }
}
