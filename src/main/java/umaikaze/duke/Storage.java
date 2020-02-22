/**
 * Storage class saves List<Task> in the directory providing upon initialization
 * Loads from file, parse it using StorageParser and returns a new List<Task>
 */

package umaikaze.duke;

import umaikaze.duke.task.Deadline;
import umaikaze.duke.task.Event;
import umaikaze.duke.task.Task;
import umaikaze.duke.task.Todo;

import java.io.*;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private File saveFile;

    public Storage(String savePath) throws IOException {
        saveFile = new File(savePath);
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
            saveFile.createNewFile();
            System.out.println("saveFile and path created @: " + saveFile.getAbsolutePath());
        } else {
            System.out.println("saveFile found @: " + saveFile.getAbsolutePath());
        }
        if (!saveFile.exists()) {
            saveFile.createNewFile();
            System.out.println("saveFile created @: " + saveFile.getAbsolutePath());
        }
    }

    /**
     * Adds Tasks from list to the data file as specified by saveFile
     * WARNING: In the case where the file is already populated, this method would not delete the original content
     * but simply append new Tasks to the file
     */
    public void saveFile(List<Task> list) throws IOException, DukeException {
        PrintWriter out = new PrintWriter(saveFile, "UTF-8");
        StringBuilder sb = new StringBuilder("");
        for (int i = 1; i <= list.size(); i++) {
            Task task = list.get(i - 1);
            sb.append(task.getSaveString()).append("\n");
        }
        out.print(sb.toString());
        out.close();
    }

    /**
     * Returns a List<Task> that is parsed from the directory as specified by saveFile
     */
    public List<Task> loadFile() throws DukeException, IOException {
        List<Task> list = new ArrayList<>(100);
        BufferedReader br = new BufferedReader(new FileReader(saveFile));
        String nextLine = br.readLine();
        String[] line;
        if (nextLine != null) {
            while (nextLine != null) {
                list.add(StorageParser.parse(nextLine.split("\\|")));
                nextLine = br.readLine();
            }
        }
        return list;
    }
}
