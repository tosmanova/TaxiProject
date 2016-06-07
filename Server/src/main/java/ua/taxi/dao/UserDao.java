package ua.taxi.dao;

import ua.taxi.model.User.User;

import java.util.Collection;
import java.util.List;

/**
 * Created by serhii on 23.04.16.
 */

// CRUD, Create, Read, Update, Delete
public interface UserDao {

    Collection<User> addUser(User user);

    User getUser(String phone);

    List<String> setToBlackList(String phone);

    User delete(String phone);

    //return OldUser
    User update(User newUser);

    Collection<User> getAllUsers();

}
