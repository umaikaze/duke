package umaikaze.duke.task;

import umaikaze.duke.DukeException;

public class Todo extends Task {
    public Todo(String description) throws DukeException {
        super(description);
    }

    @Override
    public String getSaveString() {
        return "T|" + (isDone ? 1 : 0) + "|" + description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
