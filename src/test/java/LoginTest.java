import kanban.board.login.Login;
import kanban.board.user.data.DataHandler;
import kanban.board.user.data.DeveloperDetails;
import kanban.board.user.data.Task;
import kanban.board.user.data.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @BeforeAll
    public static void init() {
        DataHandler.getInstance().setUser(new User("kyl_1", "P@ssword1", "Test_name", "Test_surname"));
    }

    @Test
    public void checkUsernameTest() {
        Login login = new Login();
        String message = login.returnLoginStatus("kyl_1", "P@ssword1");
        assertEquals(message, "Welcome Test_name,Test_surname it is great to see you again.");
    }

    @Test
    public void checkBadUsernameTest() {
        Login login = new Login();
        String message = login.registerUser("kyle!!!!!!!", "P@ssword1", "Test_name", "Test_surname");
        assertEquals(message, "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.");
    }

    @Test
    public void checkCorrectlyFormattedPasswordTest() {
        Login login = new Login();
        String message = login.registerUser("ky_le", "Ch&&sec@ke99!", "Test_name", "Test_surname");
        assertEquals(message, "Password successfully captured");
    }

    @Test
    public void checkIncorrectlyFormattedPasswordTest() {
        Login login = new Login();
        String message = login.registerUser("k_yle", "password", "Test_name", "Test_surname");
        assertEquals(message, "Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number and a special character.");
    }

    @Test
    public void loginSuccessfulTest() {
        Login login = new Login();
        assertTrue(login.loginUser("kyl_1","P@ssword1"));
    }

    @Test
    public void loginFailTest() {
        Login login = new Login();
        assertFalse(login.loginUser("kyl_1","P@ssword2"));
    }

    @Test
    public void usernameCorrectlyFormattedTest() {
        Login login = new Login();
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void usernameInCorrectlyFormattedTest() {
        Login login = new Login();
        assertFalse(login.checkUserName("kyl!!!!!"));
    }

    @Test
    public void passwordCorrectlyFormattedTest() {
        Login login = new Login();
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void passwordInCorrectlyFormattedTest() {
        Login login = new Login();
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void taskCreateTest() {
        Login login = new Login();
        Task task = new Task();
        assertTrue(login.loginUser("kyl_1","P@ssword1"));

        DeveloperDetails developerDetails = new DeveloperDetails("Robyn", "Harrison");
        String response = task.createTask("Login Feature", 0, "Create Login to authenticate users", developerDetails,8,null);
        assertEquals("Task successfully captured", response);

        DeveloperDetails developerDetails2 = new DeveloperDetails("Mike" ,"Smith");
        String response2 = task.createTask("Add Task Feature”", 0, "Create Add Task feature to add task users", developerDetails2,10,"Doing");
        assertEquals("Task successfully captured", response2);

        assertEquals(18, task.returnTotalHours());
    }

    @Test
    public void failTaskCreateTest() {
        Login login = new Login();
        Task task = new Task();
        assertTrue(login.loginUser("kyl_1","P@ssword1"));

        DeveloperDetails developerDetails = new DeveloperDetails("Robyn", "Harrison");
        String response = task.createTask("Login Feature", 0, "Create Login to authenticate users Create Login to authenticate users", developerDetails,8,null);
        assertEquals("Please enter a task description of less than 50 characters", response);
    }
}
