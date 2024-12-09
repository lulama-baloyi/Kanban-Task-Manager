package kanban.board.user.data;

public class DeveloperDetails {

    private String firstName;
    private String lastName;

    public DeveloperDetails(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
