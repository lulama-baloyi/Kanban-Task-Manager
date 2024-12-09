package kanban.board.user.data;

import java.util.ArrayList;
import java.util.List;

public class DataHandler {

    private List<User> UserList;
    private List<TaskData> tasks;
    private static DataHandler instance;

    private DataHandler() {
        UserList = new ArrayList<>();
    }

    public static DataHandler getInstance() {
        if(instance == null)
            instance = new DataHandler();
        return instance;
    }

    public User getUser(String username) {
        for (User user : UserList) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public List<TaskData> getTasks() {
        return tasks;
    }

    public void setUser(User user) {
        this.UserList.add(user);
    }

    public void setTask(TaskData task) {
        this.tasks.add(task);
    }
}
