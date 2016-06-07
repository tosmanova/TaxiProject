package ua.taxi.service;

import ua.taxi.dao.*;
import ua.taxi.model.*;

import java.util.Collection;

/**
 * Created by serhii on 23.04.16.
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserValidateMessage register(String phone, String pass, String name, Address homeAdress) {
        if (userDao.getUser(phone) == null) {
            User passanger = new Passanger(phone, pass, name, homeAdress);
            userDao.addUser(passanger);
            return new UserValidateMessage(true, "Create UserValidateMessage", "Create new Passanger\n" + passanger, passanger);
        } else {
            return new UserValidateMessage(false, "Create UserValidateMessage", "User with this phone\n" +
                    " is already registered", null);
        }
    }

    @Override
    public UserValidateMessage register(String phone, String pass, String name, Car car) {
        User user = userDao.getUser(phone);
        if (user == null) {
            User driver = new Driver(phone, pass, name, car);
            userDao.addUser(driver);
            return new UserValidateMessage(true, "Create UserValidateMessage", "Create new driver\n" + driver, driver);
        } else {
            return new UserValidateMessage(false, "Create UserValidateMessage", "User with this phone\n" +
                    " is already registered ", null);
        }
    }

    @Override
    public int driverRegisteredQuantity() {
        Collection<User> users = userDao.getAllUsers();
        int count = 0;
        for (User user : users) {
            if (user instanceof Driver) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int passangerRegisteredQuantity() {
        Collection<User> users = userDao.getAllUsers();
        int count = 0;
        for (User user : users) {
            if (user instanceof Passanger) {
                count++;
            }
        }
        return count;
    }

    @Override
    public UserValidateMessage login(String phone, String pass) {
        User user = userDao.getUser(phone);
        if (user != null) {
            if (user.getPass().equals(pass)) {
                return new UserValidateMessage(true, "login UserValidateMessage", "Pass is Ok", user);
            }
        }
        return new UserValidateMessage(false, "login Warning", "Input is incorrect", null);

    }

    @Override
    public UserValidateMessage changePassanger(String phone, String pass, String name, Address homeAdress) {

        if (userDao.getUser(phone) != null) {
            User passanger = new Passanger(phone, pass, name, homeAdress);
            User oldPassanger = userDao.update(passanger);
            return new UserValidateMessage(true, "Change passenger", "Old user:\n" + oldPassanger, passanger);
        } else {
            return new UserValidateMessage(false, "Change passenger error", "User with this phone\n" +
                    " is not present", null);
        }
    }

    @Override
    public UserValidateMessage changeDriver(String phone, String pass, String name, Car car) {
        if (userDao.getUser(phone) != null) {
            User driver = new Driver(phone, pass, name, car);
            User oldDriver = userDao.update(driver);
            return new UserValidateMessage(true, "Change driver", "Old user:\n" + oldDriver, driver);
        } else {
            return new UserValidateMessage(false, "Change driver error", "User with this phone\n" +
                    " is not present", null);
        }
    }

    @Override
    public UserValidateMessage getUser(String phone) {

        User user = userDao.getUser(phone);
        if (user != null) {
            return new UserValidateMessage(true, "Change passenger", "Old user:\n" + user, user);
        } else {
            return new UserValidateMessage(false, "Change passenger error", "User with this phone\n" +
                    " is not present", null);
        }
    }
}
