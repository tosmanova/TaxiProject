package ua.taxi.dao;

import ua.taxi.model.*;

import java.util.Collection;
import java.util.List;

/**
 * Created by serhii on 23.04.16.
 */
public class UserDaoInnerDbImpl implements UserDao {

    private AppDB appDB;

    public UserDaoInnerDbImpl(AppDB appDB) {
        this.appDB = appDB;
    }

    @Override
    public Collection<User> addUser(User user){
        return appDB.addUser(user);
    }

    @Override
    public User getUser(String phone) {
        return appDB.getUser(phone);
    }

    @Override
    public List<String> setToBlackList(String phone) {
        return appDB.setToBlackList(phone);
    }

    @Override
    public User delete(String phone) {
        return appDB.delete(phone);
    }

    @Override
    public User update(User newUser) {
        return appDB.update(newUser);
    }

    @Override
    public Collection<User> getAllUsers() {
        return appDB.getAllUsers();
    }
}
