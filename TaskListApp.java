import java.util.*;
import java.time.LocalDate;

enum Priority {
    LOW, MEDIUM, HIGH
}

class Task {
    private String description;
    private LocalDate dueDate;
    private Priority priority;

    public Task(String description, LocalDate dueDate, Priority priority) {
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public void setDescription(String description) { this.description = description; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public String getDescription() { return description; }
    public LocalDate getDueDate() { return dueDate; }
    public Priority getPriority() { return priority; }

    @Override
    public String toString() {
        return description + " | Due: " + dueDate + " | Priority: " + priority;
    }
}

public class TaskListApp {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n--- Task List Menu ---");
            System.out.println("1. Add Task");
            System.out.println("2. Edit Task");
            System.out.println("3. Remove Task");
            System.out.println("4. View All Tasks");
            System.out.println("5. Sort/Filter Tasks");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: addTask(); break;
                case 2: editTask(); break;
                case 3: removeTask(); break;
                case 4: viewTasks(); break;
                case 5: sortFilter(); break;
                case 6: System.out.println("Exiting application."); break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 6);
    }

    private static void addTask() {
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        System.out.print("Due date (YYYY-MM-DD): ");
        LocalDate due = LocalDate.parse(scanner.nextLine());
        System.out.print("Priority (LOW, MEDIUM, HIGH): ");
        Priority pr = Priority.valueOf(scanner.nextLine().toUpperCase());
        tasks.add(new Task(desc, due, pr));
        System.out.println("Task added.");
    }

    private static void editTask() {
        viewTasks();
        System.out.print("Enter task number to edit: ");
        int idx = Integer.parseInt(scanner.nextLine()) - 1;
        if (idx >= 0 && idx < tasks.size()) {
            System.out.print("New description (blank to keep): ");
            String desc = scanner.nextLine();
            if (!desc.isEmpty()) tasks.get(idx).setDescription(desc);
            System.out.print("New due date (YYYY-MM-DD, blank to keep): ");
            String due = scanner.nextLine();
            if (!due.isEmpty()) tasks.get(idx).setDueDate(LocalDate.parse(due));
            System.out.print("New priority (LOW, MEDIUM, HIGH, blank to keep): ");
            String pr = scanner.nextLine();
            if (!pr.isEmpty()) tasks.get(idx).setPriority(Priority.valueOf(pr.toUpperCase()));
            System.out.println("Task updated.");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    private static void removeTask() {
        viewTasks();
        System.out.print("Enter task number to remove: ");
        int idx = Integer.parseInt(scanner.nextLine()) - 1;
        if (idx >= 0 && idx < tasks.size()) {
            tasks.remove(idx);
            System.out.println("Task removed.");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to show.");
        } else {
            int i = 1;
            for (Task task : tasks) {
                System.out.println((i++) + ". " + task);
            }
        }
    }

    private static void sortFilter() {
        System.out.println("1. Sort by Due Date");
        System.out.println("2. Sort by Priority");
        System.out.println("3. Filter by Priority");
        System.out.println("4. Back");
        System.out.print("Choose: ");
        int option = Integer.parseInt(scanner.nextLine());
        switch (option) {
            case 1:
                tasks.sort(Comparator.comparing(Task::getDueDate));
                viewTasks();
                break;
            case 2:
                tasks.sort(Comparator.comparing(Task::getPriority));
                viewTasks();
                break;
            case 3:
                System.out.print("Enter priority to filter (LOW, MEDIUM, HIGH): ");
                Priority pr = Priority.valueOf(scanner.nextLine().toUpperCase());
                int i = 1;
                for (Task task : tasks) {
                    if (task.getPriority() == pr) {
                        System.out.println((i) + ". " + task);
                    }
                    i++;
                }
                break;
            default:
                break;
        }
    }
}
