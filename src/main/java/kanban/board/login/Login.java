package kanban.board.login;

import kanban.board.user.data.DataHandler;
import kanban.board.user.data.User;

public class Login {

    public boolean checkUserName(String username) {

        if (username.length() >= 5 && username.contains("_"))
            return true;
        return false;
    }

    public boolean checkPasswordComplexity(String password) {

        if (!(password.length() >= 8))
            return false;
        if (!checkSpecialChar(password))
            return false;
        if (!checkDigit(password))
            return false;
        if (!checkLowerCase(password))
            return false;
        if (!checkCaps(password))
            return false;
        return true;
    }

    public String registerUser(String username, String password, String name, String surname) {
        if (!checkUserName(username))
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.";
        if (!checkPasswordComplexity(password))
            return "Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number and a special character.";
        DataHandler.getInstance().setUser(new User(username, password, name, surname));
        return "Password successfully captured";
    }

    public boolean loginUser(String username, String password) {
        User user = DataHandler.getInstance().getUser(username);
        if (user != null && user.getPassword().equals(password))
            return true;
        return false;
    }

    public String returnLoginStatus(String username, String password) {
        if(loginUser(username, password)) {
            User user = DataHandler.getInstance().getUser(username);
            return "Welcome " + user.getName() + "," + user.getSurname() + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again";
    }

    private boolean checkSpecialChar(String password) {
        char[] specialChar = {' ', '!', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',',
                '-', '.', '/', ':',';', '<', '=', '>', '?', '@','[', ']', '\\', '^', '_', '`','{', '|', '}', '~'};

        for (char c : specialChar) {
            if (password.contains(""+c))
                return true;
        }
        return false;
    }

    private boolean checkCaps(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i)))
                return true;
        }
        return false;
    }

    private boolean checkLowerCase(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i)))
                return true;
        }
        return false;
    }

    private boolean checkDigit(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i)))
                return true;
        }
        return false;
    }

    public User getUser(String username) {
        return DataHandler.getInstance().getUser(username);
    }
}
