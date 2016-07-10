package ua.taxi.dao.jdbcsql;


import ua.taxi.model.order.Address;

import ua.taxi.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by serhii on 26.06.16.
 */
public class AddressDao implements GenericDao<Address> {


    @Override
    public int create(Address el) throws SQLException {

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(String.format(
                    "INSERT INTO Addresses (street, num) VALUES ('%s','%s')",
                    el.getStreet(), el.getHouseNum()));

            ResultSet resultSet = statement.executeQuery(
                    "SELECT MAX(address_id) FROM Addresses"
            );
            resultSet.next();

            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw e;
        }
    }

    @Deprecated
    @Override
    public boolean delete(Address el) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(int id) {

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT COUNT(*) FROM Addresses  WHERE Addresses.address_id=%d",
                    id));
            resultSet.next();
            if (resultSet.getInt(1) == 0) {
                return false;
            }

            statement.execute(String.format(
                    "DELETE FROM Addresses WHERE Addresses.address_id=%d",
                    id));

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Address findById(int id) throws SQLException {
        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT a.street, a.num FROM Addresses a WHERE a.address_id=%d",
                    id));

            resultSet.next();
            return new Address(
                    resultSet.getString(1),
                    resultSet.getString(2));

        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<Address> getAll(int offset, int length) throws SQLException {

        List<Address> list = new LinkedList<>();
        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT a.street, a.num FROM Addresses a LIMIT %d OFFSET %d",
                    length, offset));

            while (resultSet.next()) {
                list.add(new Address(
                        resultSet.getString(1),
                        resultSet.getString(2)));
            }
            return list;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Address update(int id, Address el) throws SQLException {
        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(String.format(
                    "UPDATE Addresses SET street='%s', num='%s' WHERE address_id=%d",
                    el.getStreet(), el.getHouseNum(), id));

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT a.street, a.num FROM Addresses a WHERE a.address_id=%d",
                    id));

            resultSet.next();
            return new Address(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );

        } catch (SQLException e) {
            throw e;
        }
    }
}
