package ua.taxi.dao.appdb;

import ua.taxi.dao.UserDao;
import ua.taxi.model.User.User;

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
    public Collection<User> createUser(User user){
        return appDB.createUser(user);
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

    //@Override
    public Collection<User> getAllUsers() {
        return appDB.getAllUsers();
    }

    @Override
    public int driverRegisteredQuantity() {
        return 0;
    }

    @Override
    public int passangerRegisteredQuantity() {
        return 0;
    }
}
