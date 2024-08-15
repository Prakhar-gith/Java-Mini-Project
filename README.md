# Smart Task Manager

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Class Structure](#class-structure)
- [UI Components](#ui-components)
- [Task Class](#task-class)
- [Event Handling](#event-handling)
- [Summary and Search Functionality](#summary-and-search-functionality)

## Introduction
The Smart Task Manager is a Java-based GUI application designed to help users manage their tasks efficiently. It allows users to add, remove, mark tasks as completed, and search through tasks. It also provides a summary of tasks based on their priority and status.

## Features
- Add new tasks with a description, priority, and deadline.
- Remove tasks from the list.
- Mark tasks as completed.
- Search tasks based on description.
- Display a summary of tasks, including counts of high, medium, and low priority tasks, completed tasks, and overdue tasks.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) installed on your machine.
- An Integrated Development Environment (IDE) like IntelliJ IDEA, Eclipse, or VS Code.

### Running the Application
1. Clone the repository or download the source code.
2. Open the project in your IDE.
3. Compile and run the `SmartTaskManager` class.

## Usage

### Adding a Task
1. Enter the task description in the "Task" field.
2. Select the priority from the "Priority" dropdown.
3. Enter the deadline in the "Deadline (DD-MM-YYYY)" field.
4. Click the "Add Task" button to add the task to the list.

### Removing a Task
1. Select the task from the list.
2. Click the "Remove Task" button to remove it from the list.

### Marking a Task as Completed
1. Select the task from the list.
2. Click the "Mark Completed" button to mark it as completed.

### Searching Tasks
1. Enter the search term in the "Search" field.
2. Click the "Search" button to search tasks containing the term.

### Viewing Task Summary
Click the "Show Summary" button to view the summary of tasks, including counts of high, medium, and low priority tasks, completed tasks, and overdue tasks.

## Class Structure

### SmartTaskManager
**Description**: Main class for the Smart Task Manager application. It sets up the UI and handles user interactions.

**Attributes**:
- `List<Task> tasks`: List to store tasks.
- `DefaultListModel<String> taskListModel`: Model for the JList.
- `JList<String> taskList`: List to display tasks.
- `JTextField taskField, deadlineField, searchField`: Text fields for input.
- `JComboBox<String> priorityBox`: ComboBox for priority selection.
- `JButton addButton, removeButton, markButton, summaryButton, searchButton`: Buttons for various actions.

**Methods**:
- `setupUI()`: Sets up the UI components.
- `setupEventHandlers()`: Sets up event handlers for buttons.
- `addTask()`: Adds a new task.
- `removeTask()`: Removes the selected task.
- `markTaskCompleted()`: Marks the selected task as completed.
- `showSummary()`: Displays a summary of tasks.
- `searchTasks()`: Searches tasks based on the input term.

### Task
**Description**: Represents a task with a description, priority, deadline, and completion status.

**Attributes**:
- `String description`: Task description.
- `String priority`: Task priority.
- `String deadline`: Task deadline.
- `boolean isCompleted`: Task completion status.

**Methods**:
- `markCompleted()`: Marks the task as completed.
- `isOverdue()`: Checks if the task is overdue.
- `toString()`: Returns the task details as a string.

## UI Components
- **Task Field**: Text field to enter the task description.
- **Priority Dropdown**: Dropdown to select task priority (High, Medium, Low).
- **Deadline Field**: Text field to enter the task deadline.
- **Search Field**: Text field to enter the search term.
- **Task List**: JList to display tasks.
- **Buttons**: Buttons for adding, removing, marking tasks, showing summary, and searching tasks.

## Event Handling
The `SmartTaskManager` class sets up event handlers for the following actions:
- **Adding a Task**: Adds a new task to the list when the "Add Task" button is clicked.
- **Removing a Task**: Removes the selected task from the list when the "Remove Task" button is clicked.
- **Marking a Task as Completed**: Marks the selected task as completed when the "Mark Completed" button is clicked.
- **Showing Task Summary**: Displays a summary of tasks when the "Show Summary" button is clicked.
- **Searching Tasks**: Searches tasks based on the input term when the "Search" button is clicked.

## Summary and Search Functionality

### Summary
The `showSummary()` method displays a summary of tasks, including:
- High priority tasks count.
- Medium priority tasks count.
- Low priority tasks count.
- Completed tasks count.
- Overdue tasks count.
- List of overdue tasks.

### Search
The `searchTasks()` method filters and displays tasks that contain the search term in their description.
