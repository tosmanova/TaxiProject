package ua.taxi.model;

/**
 * Created by serhii on 23.04.16.
 */
public class UserValidateMessage {

    private User user;
    private String title;
    private String body;
    private boolean state;

    public UserValidateMessage() {
    }

    public UserValidateMessage(boolean state, String title, String body, User user) {
        this.state = state;
        this.title = title;
        this.body = body;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public boolean getState() {
        return state;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "UserValidateMessage{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
