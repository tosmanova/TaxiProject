package ua.taxi.dao.jdbcsql;

import ua.taxi.model.user.Passenger;
import ua.taxi.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by andrii on 27.06.16.
 */
public class PassengerDao implements GenericDao<Passenger> {

    private AddressDao addressDao;
    private UserDaoSqlImpl userDao;

    public PassengerDao(){}

    @Override
    public int create(Passenger el) throws SQLException {

        int address_id = addressDao.create(el.getHomeAdress());

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT u.user_id FROM Users u WHERE u.phone='%s'",
                    el.getPhone()));
            resultSet.next();
            int user_id = resultSet.getInt(1);

            statement.execute(String.format(
                    "INSERT INTO Passengers (user_id, address_id) VALUES ('%d','%d')",
                    user_id, address_id));
            return user_id;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean delete(Passenger el) {
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT COUNT(*) FROM Passengers WHERE Passengers.user_id=%d",
                    id));
            resultSet.next();
            if (resultSet.getInt(1) == 0) {
                return false;
            }

            statement.execute(String.format(
                    "DELETE FROM Passengers WHERE Passengers.user_id=%d",
                    id));
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Passenger findById(int id) {
        return null;
    }

    @Override
    public List<Passenger> getAll(int offset, int length) {
        return null;
    }

    @Override
    public Passenger update(int id, Passenger el) throws SQLException {

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT p.address_id FROM Passengers p WHERE p.user_id=%d",
                    id));
            resultSet.next();
            int address_id = resultSet.getInt(1);
            addressDao.update(address_id, el.getHomeAdress());

            return (Passenger) userDao.findById(id);

        } catch (SQLException e) {
            throw e;
        }
    }

    public AddressDao getAddressDao() {
        return addressDao;
    }

    public void setAddressDao(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    public UserDaoSqlImpl getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDaoSqlImpl userDao) {
        this.userDao = userDao;
    }
}
