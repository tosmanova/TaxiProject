package ua.taxi.dao.sql;

import ua.taxi.model.User.Car;
import ua.taxi.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by andrii on 27.06.16.
 */
public class CarDao implements GenericDao<Car> {



    @Override
    public int create(Car car) throws SQLException {

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(String.format(
                    "INSERT INTO Cars (number, model, color) VALUES ('%s','%s','%s')",
                    car.getNumber(), car.getModel(), car.getColor()));

            ResultSet resultSet = statement.executeQuery(
                    "SELECT MAX(car_id) FROM Cars"
            );
            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean delete(Car car) throws SQLException {
        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT COUNT(*) FROM Cars WHERE Cars.number='%s'",
                    car.getNumber()));
            resultSet.next();

            if (resultSet.getInt(1) == 0) {
                return false;
            }
            statement.execute(String.format(
                    "DELETE FROM Cars WHERE Cars.number='%s'",
                    car.getNumber()));
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT COUNT(*) FROM Cars WHERE Cars.car_id=%d",
                    id));
            resultSet.next();
            if (resultSet.getInt(1) == 0) {
                return false;
            }
            statement.execute(String.format(
                    "DELETE FROM Cars WHERE Cars.car_id=%d",
                    id));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Car findById(int id) throws SQLException {
        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT * FROM Cars WHERE Cars.car_id=%d",
                    id));

            resultSet.next();
            return new Car(
                    resultSet.getString("number"),
                    resultSet.getString("model"),
                    resultSet.getString("color"));

        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<Car> getAll(int offset, int length) throws SQLException {
        List<Car> list = new LinkedList<>();
        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT * FROM Cars LIMIT %d OFFSET %d",
                    length, offset));

            while (resultSet.next()) {
                list.add(new Car(
                        resultSet.getString("number"),
                        resultSet.getString("model"),
                        resultSet.getString("color")));
            }
            return list;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Car update(int id, Car car) throws SQLException {

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(String.format(
                    "UPDATE Cars SET number='%s', model='%s', color='%s' WHERE car_id=%d",
                    car.getNumber(), car.getModel(), car.getColor(), id));

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT * FROM Cars WHERE Cars.number='%s'",
                    car.getNumber()));

            resultSet.next();
            return new Car(
                    resultSet.getString("number"),
                    resultSet.getString("model"),
                    resultSet.getString("color"));

        } catch (SQLException e) {
            throw e;
        }
    }


}
