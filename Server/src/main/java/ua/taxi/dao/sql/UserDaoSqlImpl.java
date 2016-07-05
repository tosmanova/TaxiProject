package ua.taxi.dao.sql;

import ua.taxi.dao.UserDao;
import ua.taxi.model.Order.Address;
import ua.taxi.model.User.Car;
import ua.taxi.model.User.Driver;
import ua.taxi.model.User.Passenger;
import ua.taxi.model.User.User;
import ua.taxi.utils.ConnectionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created
 * by АндрейГ on 26.06.2016.
 */
public class UserDaoSqlImpl implements UserDao {

    private static final String DRIVER = "Driver";
    private static final String PASSENGER = "Passenger";

    private DriverDao driverDao;
    private PassengerDao passengerDao;
    private AddressDao addressDao;
    private CarDao carDao;

    public UserDaoSqlImpl(){    }

    @Override
    public Collection<User> createUser(User user) throws SQLException {

        List<User> list = new ArrayList<>();

        String userType;

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            if (user instanceof Driver) {
                userType = DRIVER;
            } else {
                userType = PASSENGER;
            }

            statement.execute(String.format(
                    "INSERT INTO Users (phone, pass, name, type) VALUES ('%s','%s','%s','%s')",
                    user.getPhone(), user.getPass(), user.getName(), userType)
            );

            if (userType.equals(DRIVER)) {
                driverDao.create((Driver) user);
            } else if ((userType.equals(PASSENGER))) {
                passengerDao.create((Passenger) user);
            } else {
                throw new SQLException("Undefined user");
            }

            ResultSet resultSet = statement.executeQuery(
                    "SELECT MAX(user_id) FROM Users"
            );
            resultSet.next();
            list.add(findById(resultSet.getInt(1)));
            return list;

        } catch (SQLException e) {
            throw e;
        }
    }

    public User findById(int id) throws SQLException {

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT u.type FROM Users u WHERE u.user_id=%d", id
            ));
            resultSet.next();
            String userType = resultSet.getString(1);

            if (userType.equals(DRIVER)) {

                resultSet = statement.executeQuery(String.format(
                        "SELECT u.phone, u.pass, u.name, c.number, c.model, c.color FROM Users u " +
                                "INNER JOIN Drivers d " +
                                "ON u.user_id=d.user_id " +
                                "INNER JOIN Cars c " +
                                "ON d.car_id=c.car_id " +
                                "WHERE u.user_id=%d", id
                ));

                resultSet.next();
                int i = 0;

                return new Driver(
                        resultSet.getString(++i),
                        resultSet.getString(++i),
                        resultSet.getString(++i),
                        new Car(
                                resultSet.getString(++i),
                                resultSet.getString(++i),
                                resultSet.getString(++i)
                        ));
            } else if (userType.equals(PASSENGER)) {

                resultSet = statement.executeQuery(String.format(
                        "SELECT u.phone, u.pass, u.name, a.street, a.num FROM Users u " +
                                "INNER JOIN Passengers p " +
                                "ON u.user_id=p.user_id " +
                                "INNER JOIN Addresses a " +
                                "ON p.address_id=a.address_id " +
                                "WHERE u.user_id=%d", id
                ));

                resultSet.next();
                int i = 0;
                return new Passenger(
                        resultSet.getString(++i),
                        resultSet.getString(++i),
                        resultSet.getString(++i),
                        new Address(
                                resultSet.getString(++i),
                                resultSet.getString(++i)
                        ));
            } else {
                throw new SQLException("Undefined user");
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public User getUser(String phone) throws SQLException {

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT COUNT(*) FROM Users WHERE Users.phone='%s'",
                    phone));
            resultSet.next();
            if (resultSet.getInt(1) == 0) {
                throw new SQLException("No user with phone " + phone);
            }

            resultSet = statement.executeQuery(String.format(
                    "SELECT Users.user_id FROM Users WHERE Users.phone='%s'",
                    phone));
            resultSet.next();
            return findById(resultSet.getInt(1));

        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<String> setToBlackList(String phone) {
        return null;
    }

    @Override
    public User delete(String phone) throws SQLException {

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT u.user_id, u.type FROM Users u WHERE u.phone='%s'",
                    phone));
            resultSet.next();
            int user_id = resultSet.getInt(1);
            String userType = resultSet.getString(2);
            User user = findById(user_id);

            boolean state1;
            boolean state2;
            if (userType.equals(DRIVER)) {
                resultSet = statement.executeQuery(String.format(
                        "SELECT d.car_id FROM Drivers d WHERE d.user_id=%d",
                        user_id));
                resultSet.next();
                int car_id = resultSet.getInt(1);
                state1 = driverDao.delete(user_id);
                state2 = carDao.delete(car_id);

            } else if (userType.equals(PASSENGER)) {
                resultSet = statement.executeQuery(String.format(
                        "SELECT p.address_id FROM Passengers p WHERE p.user_id=%d",
                        user_id));
                resultSet.next();
                int address_id = resultSet.getInt(1);
                state1 = passengerDao.delete(user_id);
                state2 = addressDao.delete(address_id);

            } else {
                throw new SQLException("Undefined user");
            }

            if (!(state1 && state2)) {
                throw new SQLException("User delete error");
            }

            statement.execute(String.format(
                    "DELETE FROM Users WHERE Users.phone='%s'", phone
            ));

            return user;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public User update(User user) throws SQLException {

        String userType;

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT Users.user_id FROM Users WHERE Users.phone='%s'",
                    user.getPhone()));
            resultSet.next();
            int user_id = resultSet.getInt(1);

            User oldUser = findById(user_id);

            statement.execute(String.format(
                    "UPDATE Users SET pass='%s', name='%s'  WHERE Users.phone='%s'",
                    user.getPass(), user.getName(), user.getPhone()));

            if (user instanceof Driver) {
                driverDao.update(user_id, (Driver) user);
            } else if (user instanceof Passenger) {
                passengerDao.update(user_id, (Passenger) user);
            } else {
                throw new SQLException("Undefined user");
            }

            return oldUser;

        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public int driverRegisteredQuantity() throws SQLException {

        return getCount(String.format(
                "SELECT COUNT(*) FROM Users WHERE Users.type='%s'",
                DRIVER));
    }

    @Override
    public int passangerRegisteredQuantity() throws SQLException {
        return getCount(String.format(
                "SELECT COUNT(*) FROM Users WHERE Users.type='%s'",
                PASSENGER));
    }

    private int getCount(String format) throws SQLException {

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(format);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw e;
        }
    }

    public DriverDao getDriverDao() {
        return driverDao;
    }

    public void setDriverDao(DriverDao driverDao) {
        this.driverDao = driverDao;
    }

    public PassengerDao getPassengerDao() {
        return passengerDao;
    }

    public void setPassengerDao(PassengerDao passengerDao) {
        this.passengerDao = passengerDao;
    }

    public AddressDao getAddressDao() {
        return addressDao;
    }

    public void setAddressDao(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    public CarDao getCarDao() {
        return carDao;
    }

    public void setCarDao(CarDao carDao) {
        this.carDao = carDao;
    }
}
