/**
 * Paser class locates the description String and parse String into LocalDateTime
 * from String provided by TaskList class and Storage class
 */

package umaikaze.duke;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser {
    String description;
    char seperator;
    LocalDateTime time;
    boolean hasTime;

    /**
     * This constructor is used by Storage.loadFile
     */
    public Parser(String description, String timeStr) {
        this.description = description;
        seperator = '/';
        parseTime(timeStr);
    }

    /**
     * This constructor is used by TaskList.getTask
     */
    public Parser(String[] line) {
        setTimeString(line);
        setDescriptionString(line);
    }

    private void setTimeString(String[] words) {
        StringBuilder dateTimeSb = new StringBuilder("");
        String dateStr = "";
        String timeStr = "";
        for (int i = 1; i < words.length; i++) {
            if (words[i].charAt(0) == '/') {
                for (int j = i + 1; j < words.length && j < i + 2; j++) {
                    if (words[j].contains("/") || words[j].contains("-")) {
                        seperator = words[j].contains("/") ? '/' : '-';
                        dateStr = words[j];
                        timeStr = j + 1 >= words.length ? "" : words[j + 1];
                    }
                }
            }
        }
        if (!dateStr.equals("")) {
            dateTimeSb.append(dateStr).append(" ").append(timeStr);
            parseTime(dateTimeSb.toString());
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

    /**
     * Only used for loading file, when the time string already follow the format d/M/yyyy H:mm but H:mm may be
     * obmitted
     */
    private void parseTime(String str) throws AssertionError {
        String[] dateTime = str.split(" ");
        if (dateTime.length == 1) {
            hasTime = false;
            time = LocalDateTime.parse(dateTime[0] + " 23:29", DateTimeFormatter.ofPattern("d" + seperator
                    + "M" + seperator + "yyyy H:mm"));
        } else {
            hasTime = true;
            time = LocalDateTime.parse(dateTime[0] + " " + dateTime[1],
                    DateTimeFormatter.ofPattern("d" + seperator + "M" + seperator + "yyyy H:mm"));
        }
        assert time.isAfter(LocalDateTime.now());
    }

    public String getDescription() {
        return description;
    }
}
