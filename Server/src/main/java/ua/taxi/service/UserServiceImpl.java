package ua.taxi.service;

import org.apache.log4j.Logger;
import ua.taxi.dao.*;
import ua.taxi.model.Order.Address;
import ua.taxi.model.User.*;

import java.sql.SQLException;

/**
 * Created by serhii on 23.04.16.
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    public static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    public UserServiceImpl() {
    }

    @Override
    public UserValidateMessage register(String phone, String pass, String name, Address homeAdress) {
        User user = null;
        try {
            user = userDao.getUser(phone);
        } catch (SQLException e) {

        }
        if (user == null) {
            User passanger = new Passenger(phone, pass, name, homeAdress);
            try {
                userDao.createUser(passanger);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            LOGGER.trace("register: " + passanger);
            return new UserValidateMessage(true, "Create UserValidateMessage", "Create new Passenger\n" + passanger, passanger);
        } else {
            LOGGER.warn(("register: User with this phone is already registered"));
            return new UserValidateMessage(false, "Create UserValidateMessage", "User with this phone\n" +
                    " is already registered", null);
        }
    }

    @Override
    public UserValidateMessage register(String phone, String pass, String name, Car car) {
        User user = null;
        try {
            user = userDao.getUser(phone);
        } catch (SQLException e) {

        }
        if (user == null) {
            User driver = new Driver(phone, pass, name, car);
            try {
                userDao.createUser(driver);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            LOGGER.trace("register: " + driver);
            return new UserValidateMessage(true, "Create UserValidateMessage", "Create new driver\n" + driver, driver);
        } else {
            LOGGER.warn(("register: User with this phone is already registered"));
            return new UserValidateMessage(false, "Create UserValidateMessage", "User with this phone\n" +
                    " is already registered ", null);
        }
    }

    @Override
    public int driverRegisteredQuantity() {
       // Collection<User> users = userDao.getAllUsers();
        int count = 0;
        try {
            count = userDao.driverRegisteredQuantity();
        } catch (SQLException e) {
            e.printStackTrace();
        }
       /* for (User user : users) {
            if (user instanceof Driver) {
                count++;
            }
        }*/
        LOGGER.trace("driverRegisteredQuantity: " + count);
        return count;
    }

    @Override
    public int passangerRegisteredQuantity() {
        //Collection<User> users = userDao.getAllUsers();
        int count = 0;
        try {
            count = userDao.passangerRegisteredQuantity();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*for (User user : users) {
            if (user instanceof Passenger) {
                count++;
            }
        }*/
        LOGGER.trace("passangerRegisteredQuantity: " + count);
        return count;
    }

    @Override
    public UserValidateMessage login(String phone, String pass) {
        User user = null;
        try {
            user = userDao.getUser(phone);
        } catch (SQLException e) {

        }
        if (user != null) {
            if (user.getPass().equals(pass)) {
                LOGGER.trace("login: " + user);
                return new UserValidateMessage(true, "login UserValidateMessage", "Pass is Ok", user);
            }
        }
        LOGGER.warn(("login Warning. Input is incorrect"));
        return new UserValidateMessage(false, "login Warning", "Input is incorrect", null);

    }

    @Override
    public UserValidateMessage changePassanger(String phone, String pass, String name, Address homeAdress) {

        User user = null;
        try {
            user = userDao.getUser(phone);
        } catch (SQLException e) {

        }

        if (user != null) {
            User passanger = new Passenger(phone, pass, name, homeAdress);
            User oldPassanger = null;
            try {
                oldPassanger = userDao.update(passanger);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            LOGGER.trace("changePassanger: new" + passanger + "; old: " + oldPassanger);
            return new UserValidateMessage(true, "Change passenger", "Old user:\n" + oldPassanger, passanger);
        } else {
            LOGGER.warn(("Change passenger Warning, User with this phone not present"));
            return new UserValidateMessage(false, "Change passenger error", "User with this phone\n" +
                    " is not present", null);
        }
    }

    @Override
    public UserValidateMessage changeDriver(String phone, String pass, String name, Car car) {

        User user = null;
        try {
            user = userDao.getUser(phone);
        } catch (SQLException e) {

        }

        if (user != null) {
            User driver = new Driver(phone, pass, name, car);
            User oldDriver = null;
            try {
                oldDriver = userDao.update(driver);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            LOGGER.trace("changeDriver: new" + driver + "; old: " + oldDriver);
            return new UserValidateMessage(true, "Change driver", "Old user:\n" + oldDriver, driver);
        } else {
            LOGGER.warn(("Change driver Warning, User with this phone not present"));
            return new UserValidateMessage(false, "Change driver error", "User with this phone\n" +
                    " is not present", null);
        }
    }

    @Override
    public UserValidateMessage getUser(String phone) {

        User user = null;
        try {
            user = userDao.getUser(phone);
        } catch (SQLException e) {

        }

        if (user != null) {
            LOGGER.trace("getUser: " + user);
            return new UserValidateMessage(true, "Change passenger", "Old user:\n" + user, user);
        } else {
            LOGGER.warn(("getUser Warning, User with this phone not present"));
            return new UserValidateMessage(false, "Change passenger error", "User with this phone\n" +
                    " is not present", null);
        }
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
