package kanban.board;

import kanban.board.login.Login;
import kanban.board.user.data.DeveloperDetails;
import kanban.board.user.data.Task;
import kanban.board.user.data.TaskData;

import javax.swing.*;
import java.util.Scanner;

public class Main {

    public static Scanner sn = new Scanner(System.in);
    public static Login login = new Login();
    public static Task task = new Task();
    public static final String errorMessage = "Incorrect username or password. Please try again.";
    public static final String signupErrorMessage = "Your account has been successfully created.";

    public static void main(String[] args) {
        programLoop();
    }

    public static boolean tryToLogin() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);

        panel.add(new JLabel("Enter your username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Enter your password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            String message = login.returnLoginStatus(username, password);
            if (!message.equals(errorMessage)) {
                return true; // Indicate successful login
            } else {
                JOptionPane.showMessageDialog(null, message, "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Login canceled.", "Login", JOptionPane.WARNING_MESSAGE);
        }
        return false; // Login failed or canceled
    }

    public static void loggedInUser() {
        boolean stayLoggedIn = true;

        while (stayLoggedIn) {
            try {
                String menuOptions = "Welcome to EasyKanban!\n\n" +
                        "Please select an option:\n" +
                        "1. Add Task\n" +
                        "2. Show Report\n" +
                        "3. Logout";

                String selectedOption = JOptionPane.showInputDialog(null, menuOptions, "Main Menu", JOptionPane.QUESTION_MESSAGE);

                if (selectedOption == null) {
                    JOptionPane.showMessageDialog(null, "You have been logged out.", "Logout", JOptionPane.INFORMATION_MESSAGE);
                    stayLoggedIn = false;
                } else {
                    int selected = Integer.parseInt(selectedOption);
                    switch (selected) {
                        case 1:
                            addTask();
                            break;
                        case 2:
                            showReport();
                            break;
                        case 3:
                            stayLoggedIn = false;
                            JOptionPane.showMessageDialog(null, "You have successfully logged out.", "Logout", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Please select a valid option (1-3).", "Invalid Option", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter numbers only.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void addTask() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField taskNameField = new JTextField(15);
        JTextField taskDescriptionField = new JTextField(15);
        JTextField developerFirstNameField = new JTextField(15);
        JTextField developerLastNameField = new JTextField(15);
        JTextField taskDurationField = new JTextField(15);

        panel.add(new JLabel("Enter the task name:"));
        panel.add(taskNameField);
        panel.add(new JLabel("Enter the task description (max 50 characters):"));
        panel.add(taskDescriptionField);
        panel.add(new JLabel("Enter the developer's first name:"));
        panel.add(developerFirstNameField);
        panel.add(new JLabel("Enter the developer's last name:"));
        panel.add(developerLastNameField);
        panel.add(new JLabel("Enter the task duration (in hours):"));
        panel.add(taskDurationField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Task", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String taskName = taskNameField.getText();
                String taskDescription = taskDescriptionField.getText();
                String firstName = developerFirstNameField.getText();
                String lastName = developerLastNameField.getText();
                int duration = Integer.parseInt(taskDurationField.getText());

                if (taskDescription.length() > 50) {
                    JOptionPane.showMessageDialog(null, "Task description must be 50 characters or less.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                DeveloperDetails developerDetails = new DeveloperDetails(firstName, lastName);
                String response = task.createTask(taskName, 0, taskDescription, developerDetails, duration, "To Do");

                if (response.equals("Task successfully captured")) {
                    JOptionPane.showMessageDialog(null, response, "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, response, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for task duration.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Task creation canceled.", "Add Task", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void showReport() {
        if (task.getTaskList().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tasks have been created yet.", "Task Report", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));

        JTextField developerNameField = new JTextField(15);
        filterPanel.add(new JLabel("Enter developer's name to filter tasks (leave blank to view all):"));
        filterPanel.add(developerNameField);

        int filterResult = JOptionPane.showConfirmDialog(null, filterPanel, "Filter Tasks", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (filterResult == JOptionPane.OK_OPTION) {
            String filterName = developerNameField.getText().trim();

            StringBuilder reportBuilder = new StringBuilder();
            reportBuilder.append("Task Report:\n");

            for (TaskData t : task.getTaskList()) {
                if (filterName.isEmpty() || t.getDeveloperDetails().getFirstName().equalsIgnoreCase(filterName)) {
                    reportBuilder.append(formatTaskDetails(t)).append("\n");
                }
            }

            if (reportBuilder.toString().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No tasks found matching the specified filter.", "Task Report", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, reportBuilder.toString(), "Task Report", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Task report canceled.", "Task Report", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static String formatTaskDetails(TaskData task) {
        return String.format("Task ID: %s\nTask Name: %s\nDescription: %s\nDeveloper: %s %s\nDuration: %d hrs\n",
                task.getTaskID(),
                task.getTaskName(),
                task.getTaskDescription(),
                task.getDeveloperDetails().getFirstName(),
                task.getDeveloperDetails().getLastName(),
                task.getTaskDuration());
    }

      public static void tryToSignup() {
        // Create a panel to hold the input fields
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create text fields for user input
        JTextField usernameField = new JTextField(15);
        JTextField passwordField = new JPasswordField(15);
        JTextField firstNameField = new JTextField(15);
        JTextField lastNameField = new JTextField(15);

        // Add labels and fields to the panel
        panel.add(new JLabel("Enter your username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Enter your password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Enter your first name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Enter your last name:"));
        panel.add(lastNameField);

        // Show the input dialog with the panel
        int result = JOptionPane.showConfirmDialog(null, panel, "Sign Up", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Get user input from the fields
            String username = usernameField.getText();
            String password = passwordField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();

            // Register the user
            String message = login.registerUser(username, password, firstName, lastName);
            JOptionPane.showMessageDialog(null, message, "Sign-Up Status", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Sign-up canceled.", "Sign-Up", JOptionPane.WARNING_MESSAGE);
        }
    }
    public static void programLoop() {
        boolean loop = true;
        while (loop) {
            try {
                int selected = Integer.parseInt(getInput("Welcome to EasyKanban! Please select an option:\n1. Login\n2. Sign Up\n3. Exit"));
                switch (selected) {
                    case 1:
                        if (tryToLogin()) {
                            loggedInUser();
                        }
                        break;
                    case 2:
                        tryToSignup();
                        break;
                    case 3:
                        loop = false;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Please select a valid option (1-3).", "Invalid Option", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter numbers only.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static String getInput(String input) {
        return JOptionPane.showInputDialog(null, input, "Input Required", JOptionPane.QUESTION_MESSAGE);
    }
}
