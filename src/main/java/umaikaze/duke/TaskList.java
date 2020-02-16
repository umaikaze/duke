/**
 * Where List<Task> is handled
 * Adds, delete, markdone and prints the list
 */

package umaikaze.duke;

import umaikaze.duke.task.Deadline;
import umaikaze.duke.task.Event;
import umaikaze.duke.task.Task;
import umaikaze.duke.task.Todo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    List<Task> list;

    public TaskList() {
        list = new ArrayList<>(100);
    }

    public TaskList(List<Task> loaded) {
        list = loaded;
    }

    public String getSizeMessage() {
        return "Nyow you have " + list.size() + " tasks in the wist.";
    }

    private Task getTask(String[] line) throws DukeException {
        Task newTask;
        String cmd = line[0];
        Parser p = new Parser(line);
        switch (cmd) {
        case "deadline":
            newTask = new Deadline(p.getDescription(), p.time, p.hasTime);
            break;
        case "event":
            newTask = new Event(p.getDescription(), p.time, p.hasTime);
            break;
        case "todo":
            newTask = new Todo(p.getDescription());
            break;
        default:
            throw new DukeException("OOPS oωo  I'm sowwy, but I don't knyow what that means ^;;ω;;^");
        }
        if (p.getDescription().equals("")) {
            throw new DukeException("descwiption must nyot be empty >w<");
        }
        if (!cmd.equals("todo")) {
            throw new DukeException("i couwdn't find the timing in youw instwuctions, make suwe to specify timing " +
                    "for deadwinye or event with /by and /at");
        }
        return newTask;
    }

    /**
     * Adds a new Task object to list, as specified by
     * @param line, a String array already split
     * Returns the Task description in String when successful
     */
    public String addTask(String[] line) throws DukeException{
        Task newTask = getTask(line);
        assert !newTask.getDescription().equals("");
        list.add(newTask);
        return "Got it ^UωU^ I've added this task: \n\t"
                + newTask + "\n\t" + getSizeMessage();
    }

    /**
     * @param index starts from 0
     */
    public String markDone(int index) throws DukeException{
        if (index >= list.size() || index < 0) {
            throw new DukeException("Tasks out of bounds cannyot be donye >ω<");
        }
        Task task = list.get(index);
        task.markDone();
        return "Nyice ^;;ω;;^  I've mawked this task as donye: \n\t" + task;
    }

    public String delete(int index) throws DukeException{
        if (index >= list.size() || index < 0) {
            throw new DukeException("Nyooooo ;;ω;; You cannyot delete beyond the list size!");
        }
        Task task = list.get(index);
        list.remove(task);
        return "Nyoted (^・`ω´・^)  I've wemuvd this task: \n\t"
                + task + "\n\t" + getSizeMessage();
    }

    private List<Task> find(String[] line) {
        StringBuilder keyString = new StringBuilder("");
        for (int i = 1; i < line.length; i++) {
            if (i != line.length - 1) {
                keyString.append(line[i]).append(" ");
            } else {
                keyString.append(line[i]);
            }
        }
        List<Task> matches = new ArrayList<>(100);
        for (Task task : list) {
            if (task.getDescription().contains(keyString.toString())) {
                matches.add(task);
            }
        }
        return matches;
    }

    private String getBasicListString(List<Task> list) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 1; i <= list.size(); i++) {
            sb.append(i).append(". ").append(list.get(i - 1));
            if (i != list.size()) {
                sb.append("\n\t");
            }
        }
        return sb.toString();
    }

    public String getFindString(String[] line) throws DukeException{
        if (line.length == 1) {
            throw new DukeException("Keyoword(s) cannyot be empty (^・`ω´・^)");
        }
        StringBuilder sb = new StringBuilder("Hewe awe the matching tasks in youw list:\n\t");
        List<Task> findResults = find(line);
        if (findResults.size() == 0) {
            sb.append("no matches ^qwq^");
        } else {
            sb.append(getBasicListString(findResults));
        }
        return sb.toString();
    }

    public void saveFile(Storage st) throws IOException, DukeException{
        st.saveFile(list);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Hewe awe the tasks in youw wist:\n\t");
        if (list.size() == 0) {
            sb.append("list is empty ^qwq^");
        } else {
            sb.append(getBasicListString(list));
        }
        return sb.toString();
    }
}
