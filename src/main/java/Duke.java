import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Duke {
    static List<Task> list = new ArrayList<>(100);

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String logo = " _____     _\n\t"
                + "|     |___| |_ ___ ___ ___ ___ ___ ___\n\t"
                + "|   --| .'|  _| . | -_|  _|_ -| . |   |\n\t"
                + "|_____|__,|_| |  _|___|_| |___|___|_|_|\n\t"
                + "              |_|";
        System.out.print(formatReply("hewwo fwom\n\t" + logo));
        loadFile();
        System.out.print(formatReply("how may i sewve u today nya?"));
        String[] line = br.readLine().split(" ");
        String cmd = line[0];
        while (!cmd.equals("bye")) {
            try {
                switch (cmd) {
                    case "list":
                        System.out.print(formatList());
                        break;
                    case "done":
                        int index = Integer.parseInt(line[1]) - 1;
                        if (index >= list.size() || index < 0) {
                            throw new DukeException(formatReply("Tasks out of bounds cannyot be donye ^>w<^"));
                        }
                        Task task = list.get(index);
                        task.markDone();
                        System.out.print(formatReply("Nyice ^;;w;;^  I've mawked this task as donye: \n\t"
                                + task));
                        break;
                    case "delete":
                        index = Integer.parseInt(line[1]) - 1;
                        if (index >= list.size() || index < 0) {
                            throw new DukeException(formatReply("Nyooooo ^;;w;;^ You cannyot dewete beyond the wist size!"));
                        }
                        task = list.get(index);
                        list.remove(task);
                        System.out.print(formatReply("Nyoted (^・`ω´・^)  I've wemuvd this task: \n\t"
                                + task + "\n\t" + countList()));
                        break;
                    default:
                        StringBuilder description = new StringBuilder("");
                        Task newTask = null;
                        boolean option = false;
                        switch (cmd) {
                            case "deadline":
                                StringBuilder by = new StringBuilder("");
                                for (int i = 1; i < line.length; i++) {
                                    if (option) {
                                        if (i != line.length - 1) {
                                            by.append(line[i]).append(" ");
                                        } else {
                                            by.append(line[i]);
                                        }
                                    } else if (line[i].equals("/by")) {
                                        option = true;
                                    } else {
                                        description.append(line[i]).append(" ");
                                    }
                                }
                                if (description.toString().equals("")) {
                                    throw new DukeException(formatReply("OOPS ^;;w;;^  The descwiption of a deadwinye cannyot be empty."));
                                }
                                if (by.toString().equals("")) {
                                    throw new DukeException(formatReply("OOPS ^;;w;;^  The deadwinye of a deadwinye cannyot be empty, did you use /by to state the deadwinye?"));
                                }
                                newTask = new Deadline(description.toString(), by.toString());
                                break;
                            case "event":
                                StringBuilder at = new StringBuilder("");
                                for (int i = 1; i < line.length; i++) {
                                    if (option) {
                                        if (i != line.length - 1) {
                                            at.append(line[i]).append(" ");
                                        } else {
                                            at.append(line[i]);
                                        }
                                    } else if (line[i].equals("/at")) {
                                        option = true;
                                    } else {
                                        description.append(line[i]).append(" ");
                                    }
                                }
                                if (description.toString().equals("")) {
                                    throw new DukeException(formatReply("OOPS ^owo^  The descwiption of a event cannyot be empty."));
                                }
                                if (at.toString().equals("")) {
                                    throw new DukeException(formatReply("OOPS ^;;w;;^  The timing fow an event cannyot be empty, did you use /at to state the timing?"));
                                }
                                newTask = new Event(description.toString(), at.toString());
                                break;
                            case "todo":
                                if (line.length == 1) {
                                    throw new DukeException(formatReply("OOPS (^・`ω´・^)  The descwiption of a todo cannyot be empty."));
                                }
                                for (int i = 1; i < line.length; i++) {
                                    description.append(line[i]).append(" ");
                                }
                                newTask = new Todo(description.toString());
                                break;
                            default:
                                throw new DukeException(formatReply("OOPS ^owo^  I'm sowwy, but I don't knyow what that means :-("));
                        }
                        list.add(newTask);
                        System.out.print(formatReply("Got it ^UwU^ I've added this task: \n\t"
                                + newTask + "\n\t" + countList()));
                        break;
                }
                if (!cmd.equals("list")) {
                    saveFile();
                }
            } catch (DukeException e) {
                System.out.print(e);
            }
            line = br.readLine().split(" ");
            cmd = line[0];
        }
        System.out.print(formatReply("Bye. Hope to see you again soon ^>w<^"));
    }

    private static String formatReply(String line) {
        StringBuilder sb = new StringBuilder("\t");
        sb.append("*".repeat(60)).append("\n\t");
        sb.append(line).append("\n\t");
        sb.append("*".repeat(60)).append("\n");
        return sb.toString();
    }

    private static String formatList() {
        StringBuilder sb = new StringBuilder("\t");
        sb.append("*".repeat(60)).append("\n\tHewe awe the tasks in youw wist:\n\t");
        if (list.size() == 0) {
            sb.append("list is empty ^qwq^\n\t");
        } else {
            for (int i = 1; i <= list.size(); i++) {
                sb.append(i).append(". ").append(list.get(i - 1)).append("\n\t");
            }
        }
        sb.append("*".repeat(60)).append("\n");
        return sb.toString();
    }

    private static String countList() {
        return "Nyow you have " + list.size() + " tasks in the wist.";
    }

    private static void saveFile() {
        try {
            PrintWriter out = new PrintWriter("../src/data/data.txt");
            StringBuilder sb = new StringBuilder("");
            for (int i = 1; i <= list.size(); i++) {
                Task task = list.get(i - 1);
                sb.append(task.getSaveString()).append("\n");
            }
            out.print(sb.toString());
            out.close();
        } catch (IOException e) {
            System.out.print(formatReply("Oh nyo ^;;w;;^  I was unyabwe to save because:\n\t" + e.getMessage()));
        } catch (Exception e) {
            System.out.print(formatReply("I am sowwy ^;;w;;^ i twied to save a bawebonye task fow some weason"));
        }
    }

    private static void loadFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("../src/data/data.txt"));
            String nextLine = br.readLine();
            String[] line;
            if (nextLine != null) {
                line = nextLine.split("\\|");
                for (int i = 0; i < line.length; i++) {
                    line[i] = line[i].trim();
                }
                while (nextLine != null) {
                    Task task;
                    switch (line[0]) {
                        case "T":
                            task = new Todo(line[2]);
                            if (line[1].equals("1")) {
                                task.markDone();
                            }
                            System.out.println("Loaded a todo");
                            break;
                        case "D":
                            task = new Deadline(line[2], line[3]);
                            if (line[1].equals("1")) {
                                task.markDone();
                            }
                            System.out.println("Loaded a deadline");
                            break;
                        default:
                            task = new Event(line[2], line[3]);
                            if (line[1].equals("1")) {
                                task.markDone();
                            }
                            System.out.println("Loaded an event");
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
                System.out.print(formatReply("Save data is empty, we wiww stawt fwesh (・`ω´・) "));
            }
        } catch (IOException e) {
            System.out.print(formatReply("Oops, unyabwe to woad saved data ;;w;;:\n\t" + e.getMessage()));
        }
    }
}
