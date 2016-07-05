package ua.taxi.dao.sql;

import ua.taxi.dao.UserDao;
import ua.taxi.model.User.Car;
import ua.taxi.model.User.Driver;
import ua.taxi.model.User.User;
import ua.taxi.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by andrii on 27.06.16.
 */
public class DriverDao implements GenericDao<Driver> {

    private CarDao carDao;
    private UserDaoSqlImpl userDao;

    public DriverDao() {    }

    @Override
    public int create(Driver driver) throws SQLException {

        int car_id = carDao.create(driver.getCar());

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT u.user_id FROM Users u WHERE u.phone='%s'",
                    driver.getPhone()));
            resultSet.next();
            int user_id = resultSet.getInt(1);

            statement.execute(String.format(
                    "INSERT INTO Drivers (user_id, car_id) VALUES ('%d','%d')",
                    user_id, car_id));
            return user_id;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean delete(Driver el) {
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT COUNT(*) FROM Drivers WHERE Drivers.user_id=%d",
                    id));
            resultSet.next();
            if (resultSet.getInt(1) == 0) {
                return false;
            }

            statement.execute(String.format(
                    "DELETE FROM Drivers WHERE Drivers.user_id=%d",
                    id));
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Driver findById(int id) throws SQLException {

        User user = userDao.findById(id);
        if (user instanceof Driver) {
            return (Driver) user;
        } else {
            throw new SQLException("No Driver id");
        }
    }

    @Override
    public List<Driver> getAll(int offset, int length) {
        return null;
    }

    @Override
    public Driver update(int id, Driver el) throws SQLException {

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT d.car_id FROM Drivers d WHERE d.user_id=%d",
                    id));
            resultSet.next();
            int car_id = resultSet.getInt(1);
            carDao.update(car_id, el.getCar());

            return (Driver) userDao.findById(id);

        } catch (SQLException e) {
            throw e;
        }
    }

    public CarDao getCarDao() {
        return carDao;
    }

    public void setCarDao(CarDao carDao) {
        this.carDao = carDao;
    }

    public UserDaoSqlImpl getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDaoSqlImpl userDao) {
        this.userDao = userDao;
    }
}
