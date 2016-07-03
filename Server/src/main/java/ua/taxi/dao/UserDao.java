package ua.taxi.dao;

import ua.taxi.model.User.User;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Created by serhii on 23.04.16.
 */

// CRUD, Create, Read, Update, Delete
public interface UserDao {

    Collection<User> createUser(User user) throws SQLException;

    User getUser(String phone) throws SQLException;

    List<String> setToBlackList(String phone);

    User delete(String phone) throws SQLException;

    //return OldUser
    User update(User newUser) throws SQLException;

    //Collection<User> getAllUsers() throws SQLException;

    int driverRegisteredQuantity() throws SQLException;

    int passangerRegisteredQuantity() throws SQLException;

}
