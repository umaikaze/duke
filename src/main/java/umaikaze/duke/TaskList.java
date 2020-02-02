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

    public String countList() {
        return "Nyow you have " + list.size() + " tasks in the wist.";
    }

    private Task getTask(String[] line) throws DukeException{
        Task newTask;
        String cmd = line[0];
        Parser p = new Parser(line);
        switch (cmd) {
            case "deadline":
                if (p.getDescription().equals("")) {
                    throw new DukeException("OOPS ;;w;;  The descwiption of a deadwinye cannyot be empty.");
                }
                if (p.time == null) {
                    throw new DukeException("OOPS ;;w;;  The deadwinye of a deadwinye cannyot be empty" +
                            ", did you use /by to state the deadwinye?");
                }
                newTask = new Deadline(p.getDescription(), p.time, p.hasTime);
                break;
            case "event":
                if (p.getDescription().equals("")) {
                    throw new DukeException("OOPS owo  The descwiption of a event cannyot be empty.");
                }
                if (p.time == null) {
                    throw new DukeException("OOPS ;;w;;  The timing fow an event cannyot be empty," +
                            " did you use /at to state the timing?");
                }
                newTask = new Event(p.getDescription(), p.time, p.hasTime);
                break;
            case "todo":
                if (p.getDescription().equals("")) {
                    throw new DukeException("OOPS (・`ω´・)  The descwiption of a todo cannyot be empty.");
                }
                newTask = new Todo(p.getDescription());
                break;
            default:
                throw new DukeException("OOPS owo  I'm sowwy, but I don't knyow what that means ^;;w;;^");
        }
        return newTask;
    }

    public String addTask(String[] line) throws DukeException{
        Task newTask = getTask(line);
        list.add(newTask);
        return "Got it ^UwU^ I've added this task: \n\t"
                + newTask + "\n\t" + countList();
    }

    public String markDone(int index) throws DukeException{
        if (index >= list.size() || index < 0) {
            throw new DukeException("Tasks out of bounds cannyot be donye >w<");
        }
        Task task = list.get(index);
        task.markDone();
        return "Nyice ^;;w;;^  I've mawked this task as donye: \n\t" + task;
    }

    public String delete(int index) throws DukeException{
        if (index >= list.size() || index < 0) {
            throw new DukeException("Nyooooo ;;w;; You cannyot delete beyond the list size!");
        }
        Task task = list.get(index);
        list.remove(task);
        return "Nyoted (^・`ω´・^)  I've wemuvd this task: \n\t"
                + task + "\n\t" + countList();
    }

    public void printList(Ui ui) {
        ui.printList(list);
    }

    public void saveFile(Storage st) throws IOException, DukeException{
        st.saveFile(list);
    }
}
