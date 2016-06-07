package ua.taxi.dao;

import ua.taxi.model.User.User;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * Created by Andrii on 5/12/2016.
 */

@XmlRootElement(name = "users")
public class UserMapWriter {

    private Map<String,User> users;

    @XmlElement(name = "user")
    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }
}
