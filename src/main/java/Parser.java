import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser {
    String description;
    LocalDateTime time;
    boolean hasTime;

    // Meant for file loading
    public Parser(String description, String timeStr) {
        this.description = description;
        parseTime(timeStr);
    }

    // Meant for user input
    public Parser(String[] line) {
        findTimeString(line);
        findDescriptionString(line);
    }

    private void findTimeString(String[] words) {
        StringBuilder dateTimeSb = new StringBuilder("");
        String dateStr = "";
        String timeStr = "";
        for (int i = 1; i < words.length; i++) {
            if (words[i].charAt(0) == '/') {
                for (int j = i + 1; j < words.length && j < i + 2; j++) {
                    if (words[j].contains("/")) {
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

    private void findDescriptionString(String[] words) {
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

    private void parseTime(String str) {
        String[] dateTime = str.split(" ");
        if (dateTime.length == 1) {
            hasTime = false;
            time = LocalDateTime.parse(dateTime[0] + " 23:29", DateTimeFormatter.ofPattern("d/M/yyyy H:mm"));
        } else {
            hasTime = true;
            time = LocalDateTime.parse(dateTime[0] + " " + dateTime[1], DateTimeFormatter.ofPattern("d/M/yyyy H:mm"));
        }
    }

    public String getDescription() {
        return description;
    }
}
