/**
 * Storage class saves List<Task> in the directory providing upon initialization
 * Loads from file, parse it using Parser and returns a List<Task>
 */

package umaikaze.duke;

import umaikaze.duke.task.Deadline;
import umaikaze.duke.task.Event;
import umaikaze.duke.task.Task;
import umaikaze.duke.task.Todo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private File saveFile;

    /**
     * @param savePath This is the only way to set save directory for each Storage object
     */
    public Storage(String savePath) {
        this.saveFile = new File(savePath);
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
    public List<Task> loadFile() throws IOException {
        List<Task> list = new ArrayList<>(100);
        BufferedReader br = new BufferedReader(new FileReader(saveFile));
        String nextLine = br.readLine();
        String[] line;
        if (nextLine != null) {
            line = nextLine.split("\\|");
            for (int i = 0; i < line.length; i++) {
                line[i] = line[i].trim();
            }
            while (nextLine != null) {
                Task task;
                Parser p;
                switch (line[0]) {
                case "T":
                    task = new Todo(line[2]);
                    if (line[1].equals("1")) {
                        task.markDone();
                    }
                    System.out.println("\tLoaded a todo");
                    break;
                case "D":
                    p = new Parser(line[2], line[3]);
                    task = new Deadline(p.getDescription(), p.time, p.hasTime);
                    if (line[1].equals("1")) {
                        task.markDone();
                    }
                    System.out.println("\tLoaded a deadwinye");
                    break;
                default:
                    p = new Parser(line[2], line[3]);
                    task = new Event(p.getDescription(), p.time, p.hasTime);
                    if (line[1].equals("1")) {
                        task.markDone();
                    }
                    System.out.println("\tLoaded an event");
                    break;
                }
                list.add(task);
                nextLine = br.readLine();
                if (nextLine != null) {
                    line = nextLine.split("\\|");
                }
            }
        }
        if (list.size() == 0) {
            throw new IOException("Save data is empty, we will stawt fwesh (・`ω´・)");
        }
        return list;
    }
}
