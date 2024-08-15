import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// SmartTaskManagerCLI class hai jo task manage karti hai
public class SmartTaskManagerCLI {
    private List<Task> tasks; // Tasks ko store karne ke liye list

    // Constructor to initialize the tasks list
    public SmartTaskManagerCLI() {
        tasks = new ArrayList<>(); // Initializing the list
    }

    // Main method jo program run karne ke liye entry point hai
    public static void main(String[] args) {
        SmartTaskManagerCLI manager = new SmartTaskManagerCLI();
        manager.run(); // Program start hota hai
    }

    // Yeh method program run karta hai
    private void run() {
        Scanner scanner = new Scanner(System.in);

        // Infinite loop to keep the program running until user exits
        while (true) {
            // Menu display kar raha hai
            System.out.println("\nSmart Task Manager CLI");
            System.out.println("1. Add Task");
            System.out.println("2. Remove Task");
            System.out.println("3. Mark Task Completed");
            System.out.println("4. Show Summary");
            System.out.println("5. Search Tasks");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = 0; // User choice input ke liye variable
            try {
                choice = scanner.nextInt(); // User se choice le rahe hain
                scanner.nextLine(); // Consume newline character
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                scanner.nextLine(); // Consume the invalid input
                continue;
            }

            // Switch case to handle different user choices
            switch (choice) {
                case 1:
                    addTask(scanner); // Task add karne ka method
                    break;
                case 2:
                    removeTask(scanner); // Task remove karne ka method
                    break;
                case 3:
                    markTaskCompleted(scanner); // Task complete mark karne ka method
                    break;
                case 4:
                    showSummary(); // Summary dikhane ka method
                    break;
                case 5:
                    searchTasks(scanner); // Tasks search karne ka method
                    break;
                case 6:
                    System.out.println("Exiting..."); // Exit message
                    return; // Program terminate
                default:
                    System.out.println("Invalid choice. Please try again."); // Invalid choice handling
            }
        }
    }

    // Yeh method ek naya task add karta hai
    private void addTask(Scanner scanner) {
        System.out.print("Enter task description: ");
        String description = scanner.nextLine(); // Task description input
        System.out.print("Enter priority (High/Medium/Low): ");
        String priority = scanner.nextLine().toLowerCase(); // Priority input
        System.out.print("Enter deadline (DD-MM-YYYY): ");
        String deadline = scanner.nextLine(); // Deadline input

        try {
            Task task = new Task(description, priority, deadline); // Naya task create karte hain
            tasks.add(task); // Task list mein add karte hain
            System.out.println("Task added successfully."); // Success message
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage()); // Error handling
        }
    }

    // Yeh method ek task remove karta hai
    private void removeTask(Scanner scanner) {
        System.out.print("Enter the index of the task to remove: ");
        int index = scanner.nextInt();
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index); // Task list se remove karte hain
            System.out.println("Task removed successfully."); // Success message
        } else {
            System.out.println("Invalid index."); // Invalid index handling
        }
    }

    // Yeh method ek task ko complete mark karta hai
    private void markTaskCompleted(Scanner scanner) {
        System.out.print("Enter the index of the task to mark as completed: ");
        int index = scanner.nextInt();
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index); // Task list se get karte hain
            task.markCompleted(); // Task ko complete mark karte hain
            System.out.println("Task marked as completed."); // Success message
        } else {
            System.out.println("Invalid index."); // Invalid index handling
        }
    }

    // Yeh method summary dikhata hai
    private void showSummary() {
        int highPriorityCount = 0, mediumPriorityCount = 0, lowPriorityCount = 0;
        int completedCount = 0, overdueCount = 0;

        StringBuilder overdueTasks = new StringBuilder(); // Overdue tasks store karne ke liye

        // Iterate through tasks to count and identify overdue tasks
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            switch (task.getPriority()) {
                case "high":
                    highPriorityCount++;
                    break;
                case "medium":
                    mediumPriorityCount++;
                    break;
                case "low":
                    lowPriorityCount++;
                    break;
            }
            if (task.isCompleted()) {
                completedCount++;
            }
            if (task.isOverdue()) {
                overdueCount++;
                overdueTasks.append(i).append(": ").append(task.toString()).append("\n");
            }
        }

        // Summary print karna
        System.out.println("\nSummary:");
        System.out.printf("High Priority: %d\n", highPriorityCount);
        System.out.printf("Medium Priority: %d\n", mediumPriorityCount);
        System.out.printf("Low Priority: %d\n", lowPriorityCount);
        System.out.printf("Completed: %d\n", completedCount);
        System.out.printf("Overdue: %d\n", overdueCount);
        System.out.printf("Overdue Tasks:\n%s", overdueTasks.toString());

        // All tasks list print karna
        System.out.println("\nAll Tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d: %s\n", i, tasks.get(i).toString());
        }
    }

    // Yeh method tasks ko search karta hai
    private void searchTasks(Scanner scanner) {
        System.out.print("Enter search query: ");
        String query = scanner.nextLine().toLowerCase();
        List<String> results = tasks.stream()
                .filter(task -> task.getDescription().toLowerCase().contains(query)) // Query match karne wale tasks filter karte hain
                .map(Task::toString)
                .collect(Collectors.toList());

        // Search results print karna
        if (results.isEmpty()) {
            System.out.println("No tasks found matching the search query.");
        } else {
            results.forEach(System.out::println);
        }
    }

    // Task class jo ek task define karti hai
    private static class Task {
        private String description;
        private String priority;
        private String deadline;
        private boolean completed;

        // Constructor to initialize a new task
        public Task(String description, String priority, String deadline) {
            priority = priority.toLowerCase();
            if (!priority.equals("high") && !priority.equals("medium") && !priority.equals("low")) {
                throw new IllegalArgumentException("Invalid priority. Use High, Medium, or Low.");
            }
            if (!deadline.matches("\\d{2}-\\d{2}-\\d{4}")) {
                throw new IllegalArgumentException("Invalid deadline format. Use DD-MM-YYYY.");
            }
            this.description = description;
            this.priority = priority;
            this.deadline = deadline;
            this.completed = false;
        }

        // Getter for description
        public String getDescription() {
            return description;
        }

        // Getter for priority
        public String getPriority() {
            return priority;
        }

        // Check if task is completed
        public boolean isCompleted() {
            return completed;
        }

        // Mark task as completed
        public void markCompleted() {
            completed = true;
        }

        // Check if task is overdue
        public boolean isOverdue() {
            // Simple check: for demonstration purposes, just comparing with a fixed date
            return !completed && deadline.compareTo("15-08-2024") < 0;
        }

        // Task details string representation
        @Override
        public String toString() {
            return (completed ? "[Completed] " : "") + description + " (Priority: " + priority + ", Deadline: " + deadline + ")";
        }
    }
}
