/**
 * This class is used by Storage.loadFile to read file following the saveString format strictly
 */

package umaikaze.duke;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StorageParser {
    String description;
    LocalDate date;
    LocalTime time;
    Duration duration;

    public StorageParser(String description, String timeStr) {
        this.description = description;
        parseTime(timeStr);
    }

    private void parseTime(String str) {
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
