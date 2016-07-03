package ua.taxi.dao.sql;


import ua.taxi.model.Order.OrderStatus;
import ua.taxi.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by andrii on 30.06.16.
 */
public class OrderStatusDao implements GenericDao<OrderStatus>{
    
    public int create(OrderStatus status) throws SQLException {


        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(String.format(
                    "INSERT INTO Statuses (name) VALUES ('%s')",
                    status.toString()));

            ResultSet resultSet = statement.executeQuery(
                    "SELECT MAX(status_id) FROM Statuses"
            );
            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw e;
        }
    }

    public OrderStatus update(int id, OrderStatus status) throws SQLException {

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(String.format(
                    "UPDATE Statuses SET name='%s' WHERE status_id=%d",
                    status.toString(), id));
            return findById(id);

        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean delete(OrderStatus el) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT COUNT(*) FROM Statuses WHERE Statuses.status_id=%d",
                    id));
            resultSet.next();
            if (resultSet.getInt(1) == 0) {
                return false;
            }
            statement.execute(String.format(
                    "DELETE FROM Statuses WHERE Statuses.status_id=%d",
                    id));
            return true;
        } catch (SQLException e) {

            return false;
        }
    }

    @Override
    public OrderStatus findById(int id) throws SQLException {
        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT s.name FROM Statuses s WHERE s.status_id=%d",
                    id));
            resultSet.next();
            String status = resultSet.getString(1);

            if(status.equals("DONE")){
                return OrderStatus.DONE;
            }else if(status.equals("IN_PROGRESS")){
                return OrderStatus.IN_PROGRESS;
            }else if(status.equals("CANCELLED")) {
                return OrderStatus.CANCELLED;
            }else if(status.equals("NEW")) {
                return OrderStatus.NEW;
            }else {
                throw new SQLException("Cant identify Order Status");
            }

        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<OrderStatus> getAll(int offset, int length) throws SQLException {
        return null;
    }
}
