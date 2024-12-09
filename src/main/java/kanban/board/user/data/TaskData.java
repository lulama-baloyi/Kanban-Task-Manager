package kanban.board.user.data;

public class TaskData {
    private String taskName;
    private int taskNumber;
    private String taskDescription;
    private DeveloperDetails developerDetails;
    private int taskDuration;
    private String taskID;
    private String taskStatus;

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskNumber(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setDeveloperDetails(DeveloperDetails developerDetails) {
        this.developerDetails = developerDetails;
    }

    public void setTaskDuration(int taskDuration) {
        this.taskDuration = taskDuration;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public DeveloperDetails getDeveloperDetails() {
        return developerDetails;
    }

    public int getTaskDuration() {
        return taskDuration;
    }

    public String getTaskID() {
        return taskID;
    }

    public String getTaskStatus() {
        return taskStatus;
    }
}
