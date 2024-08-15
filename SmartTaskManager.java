import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SmartTaskManager extends JFrame {
    private List<Task> tasks; // List to hold tasks
    private DefaultListModel<String> taskListModel; // Model for the JList
    private JList<String> taskList; // List to display tasks
    private JTextField taskField, deadlineField, searchField; // Text fields for input
    private JComboBox<String> priorityBox; // ComboBox for priority selection
    private JButton addButton, removeButton, markButton, summaryButton, searchButton; // Buttons for various actions

    // Constructor for setting up the UI and event handlers
    public SmartTaskManager() {
        tasks = new ArrayList<>(); // Initializing the task list
        taskListModel = new DefaultListModel<>(); // Initializing the list model
        taskList = new JList<>(taskListModel); // Initializing the JList
        taskField = new JTextField(15); // Text field for task description
        deadlineField = new JTextField(10); // Text field for deadline
        searchField = new JTextField(15); // Text field for search
        priorityBox = new JComboBox<>(new String[]{"High", "Medium", "Low"}); // ComboBox for priority
        addButton = new JButton("Add Task"); // Button to add a task
        removeButton = new JButton("Remove Task"); // Button to remove a task
        markButton = new JButton("Mark Completed"); // Button to mark a task as completed
        summaryButton = new JButton("Show Summary"); // Button to show task summary
        searchButton = new JButton("Search"); // Button to search tasks

        setupUI(); // Setting up the UI components
        setupEventHandlers(); // Setting up event handlers for buttons
    }

    // Method to setup the UI components
    private void setupUI() {
        setTitle("Smart Task Manager"); // Setting the title of the window
        setSize(600, 400); // Setting the size of the window
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Close operation
        setLayout(new BorderLayout()); // Using BorderLayout

        // Input Panel for adding tasks
        JPanel inputPanel = new JPanel(new GridBagLayout()); // Panel with GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints(); // Constraints for layout
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.anchor = GridBagConstraints.WEST; // Anchor to the west

        // Adding components to the input panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        inputPanel.add(new JLabel("Task:"), gbc); // Label for task

        gbc.gridx = 1;
        inputPanel.add(taskField, gbc); // Text field for task

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Priority:"), gbc); // Label for priority

        gbc.gridx = 1;
        inputPanel.add(priorityBox, gbc); // ComboBox for priority

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Deadline (DD-MM-YYYY):"), gbc); // Label for deadline

        gbc.gridx = 1;
        inputPanel.add(deadlineField, gbc); // Text field for deadline

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Search:"), gbc); // Label for search

        gbc.gridx = 1;
        inputPanel.add(searchField, gbc); // Text field for search

        gbc.gridx = 2;
        inputPanel.add(searchButton, gbc); // Button for search

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        inputPanel.add(addButton, gbc); // Button to add task

        // Panel for other buttons
        JPanel buttonPanel = new JPanel(new FlowLayout()); // Panel with FlowLayout
        buttonPanel.add(removeButton); // Button to remove task
        buttonPanel.add(markButton); // Button to mark task completed
        buttonPanel.add(summaryButton); // Button to show summary

        // Adding panels to the frame
        add(inputPanel, BorderLayout.NORTH); // Adding input panel to the north
        add(new JScrollPane(taskList), BorderLayout.CENTER); // Adding task list to the center
        add(buttonPanel, BorderLayout.SOUTH); // Adding button panel to the south

        // Customizing component fonts and colors
        Font font = new Font("Arial", Font.PLAIN, 14); // Font for components
        taskField.setFont(font);
        deadlineField.setFont(font);
        searchField.setFont(font);
        priorityBox.setFont(font);
        addButton.setFont(font);
        removeButton.setFont(font);
        markButton.setFont(font);
        summaryButton.setFont(font);
        searchButton.setFont(font);
        taskList.setFont(font);

        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Single selection mode for list
        taskList.setBackground(new Color(245, 245, 245)); // Background color for list
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Border for button panel
    }

    // Method to setup event handlers for buttons
    private void setupEventHandlers() {
        addButton.addActionListener(e -> addTask()); // Event handler for add button
        removeButton.addActionListener(e -> removeTask()); // Event handler for remove button
        markButton.addActionListener(e -> markTaskCompleted()); // Event handler for mark button
        summaryButton.addActionListener(e -> showSummary()); // Event handler for summary button
        searchButton.addActionListener(e -> searchTasks()); // Event handler for search button
    }

    // Method to add a task
    private void addTask() {
        String taskDescription = taskField.getText(); // Get task description
        String priority = (String) priorityBox.getSelectedItem(); // Get priority
        String deadline = deadlineField.getText(); // Get deadline
        
        if (taskDescription.isEmpty()) { // Check if description is empty
            JOptionPane.showMessageDialog(this, "Task description cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE); // Show error message
            return;
        }
        
        Task newTask;
        try {
            newTask = new Task(taskDescription, priority, deadline); // Create new task
        } catch (IllegalArgumentException ex) { // Catch invalid input exception
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // Show error message
            return;
        }
        
        tasks.add(newTask); // Add task to list
        taskListModel.addElement(newTask.toString()); // Add task to list model
        taskField.setText(""); // Clear task field
        deadlineField.setText(""); // Clear deadline field
    }

    // Method to remove a task
    private void removeTask() {
        int selectedIndex = taskList.getSelectedIndex(); // Get selected index
        if (selectedIndex >= 0) { // Check if a task is selected
            tasks.remove(selectedIndex); // Remove task from list
            taskListModel.remove(selectedIndex); // Remove task from list model
        }
    }

    // Method to mark a task as completed
    private void markTaskCompleted() {
        int selectedIndex = taskList.getSelectedIndex(); // Get selected index
        if (selectedIndex >= 0) { // Check if a task is selected
            Task task = tasks.get(selectedIndex); // Get selected task
            task.markCompleted(); // Mark task as completed
            taskListModel.set(selectedIndex, task.toString()); // Update task in list model
        }
    }

    // Method to show summary of tasks
    private void showSummary() {
        int highPriorityCount = 0, mediumPriorityCount = 0, lowPriorityCount = 0; // Counters for priorities
        int completedCount = 0, overdueCount = 0; // Counters for completed and overdue tasks

        StringBuilder overdueTasks = new StringBuilder(); // StringBuilder for overdue tasks

        for (Task task : tasks) { // Iterate through tasks
            switch (task.getPriority()) { // Check task priority
                case "High":
                    highPriorityCount++; // Increment high priority counter
                    break;
                case "Medium":
                    mediumPriorityCount++; // Increment medium priority counter
                    break;
                case "Low":
                    lowPriorityCount++; // Increment low priority counter
                    break;
            }
            if (task.isCompleted()) { // Check if task is completed
                completedCount++; // Increment completed counter
            }
            if (task.isOverdue()) { // Check if task is overdue
                overdueCount++; // Increment overdue counter
                overdueTasks.append(task.toString()).append("\n"); // Add overdue task to StringBuilder
            }
        }

        // Show summary dialog with task counts and overdue tasks
        JOptionPane.showMessageDialog(this, String.format("Summary:\n" +
                "High Priority: %d\n" +
                "Medium Priority: %d\n" +
                "Low Priority: %d\n" +
                "Completed: %d\n" +
                "Overdue: %d\n" +
                "Overdue Tasks:\n%s", highPriorityCount, mediumPriorityCount, lowPriorityCount, completedCount, overdueCount, overdueTasks), "Task Summary", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to search tasks
    private void searchTasks() {
        String searchTerm = searchField.getText().toLowerCase(); // Get search term in lower case

        // Filter tasks that match the search term
        List<String> searchResults = tasks.stream()
                .filter(task -> task.getDescription().toLowerCase().contains(searchTerm))
                .map(Task::toString)
                .collect(Collectors.toList());

        if (searchResults.isEmpty()) { // Check if no tasks match the search term
            JOptionPane.showMessageDialog(this, "No tasks found.", "Search Results", JOptionPane.INFORMATION_MESSAGE); // Show no tasks found message
        } else {
            String resultMessage = String.join("\n", searchResults); // Join search results into a single string
            JOptionPane.showMessageDialog(this, resultMessage, "Search Results", JOptionPane.INFORMATION_MESSAGE); // Show search results
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SmartTaskManager manager = new SmartTaskManager(); // Creating instance of SmartTaskManager
            manager.setVisible(true); // Setting the frame visible
        });
    }

    // Task class to represent a task
    static class Task {
        private String description; // Task description
        private String priority; // Task priority
        private String deadline; // Task deadline
        private boolean isCompleted; // Task completion status

        // Constructor for creating a task
        public Task(String description, String priority, String deadline) {
            if (description.isEmpty() || priority.isEmpty() || deadline.isEmpty()) { // Check if any input is empty
                throw new IllegalArgumentException("All fields must be filled."); // Throw exception for empty input
            }
            this.description = description; // Set description
            this.priority = priority; // Set priority
            this.deadline = deadline; // Set deadline
            this.isCompleted = false; // Set completion status to false
        }

        // Method to mark task as completed
        public void markCompleted() {
            isCompleted = true; // Set completion status to true
        }

        // Method to get task description
        public String getDescription() {
            return description; // Return description
        }

        // Method to get task priority
        public String getPriority() {
            return priority; // Return priority
        }

        // Method to check if task is completed
        public boolean isCompleted() {
            return isCompleted; // Return completion status
        }

        // Method to check if task is overdue
        public boolean isOverdue() {
            // Placeholder logic for checking overdue status, should be replaced with actual date comparison
            return !isCompleted && deadline.compareTo("12-12-2023") < 0; // Check if task is overdue based on placeholder date
        }

        // Override toString method to return task details
        @Override
        public String toString() {
            return String.format("Task: %s | Priority: %s | Deadline: %s | %s", description, priority, deadline, isCompleted ? "Completed" : "Pending"); // Return task details
        }
    }
}
