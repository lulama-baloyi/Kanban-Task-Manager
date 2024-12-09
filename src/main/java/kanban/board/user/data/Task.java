package kanban.board.user.data;

import kanban.board.login.Login;

import java.util.ArrayList;
import java.util.List;

public class Task {

    List<TaskData> taskList = new ArrayList<>();

    public boolean checkTaskDescription(String description) {

        if(description.length() > 50)
            return false;
        return true;
    }

    public String createTaskID(String taskName, int taskNumber, String devName) {
        String taskID = taskName.substring(0, 2).toUpperCase();
        taskID += ":" + taskNumber + ":";
        taskID += devName.substring(0, 3).toUpperCase();
        return taskID;
    }

    public String printTaskDetails(String taskID) {
        TaskData newTaskData = new TaskData();
        for (TaskData taskData : taskList) {
            if (taskData.getTaskID().equals(taskID))
                newTaskData = taskData;
        }
        return "Task Status: To Do\n"
                + "Developer Details: " + newTaskData.getDeveloperDetails().getFirstName() + " " + newTaskData.getDeveloperDetails().getLastName() +"\n"
                + "Task Number: " + newTaskData.getTaskNumber() + "\n"
                + "Task Name: " + newTaskData.getTaskName() + "\n"
                + "Task Description: " + newTaskData.getTaskDescription() + "\n"
                + "Task ID: "+ newTaskData.getTaskID() +"\n"
                + "Duration: " + newTaskData.getTaskDuration();
    }

    public int returnTotalHours() {
        int hours = 0;
        for (TaskData task : taskList) {
            hours += task.getTaskDuration();
        }
        return hours;
    }

    public String createTask(String taskName, int taskNumber, String description, DeveloperDetails developerDetails, int duration, String taskStatus) {
        if (!checkTaskDescription(description))
            return "Please enter a task description of less than 50 characters";

        String taskId = createTaskID(taskName, taskNumber, developerDetails.getFirstName());

        TaskData taskData = new TaskData();
        taskData.setTaskName(taskName);
        taskData.setTaskDescription(description);
        taskData.setTaskNumber(taskNumber);
        taskData.setTaskID(taskId);
        taskData.setDeveloperDetails(developerDetails);
        taskData.setTaskDuration(duration);
        if (taskStatus == null)
            taskData.setTaskStatus("To Do");
        else
            taskData.setTaskStatus(taskStatus);

        taskList.add(taskData);
        return "Task successfully captured";
    }

    public List<TaskData> getTaskList() {
        return taskList;
    }
}
