package ua.taxi.dao.sql;

import ua.taxi.dao.OrderDao;
import ua.taxi.model.Order.Order;
import ua.taxi.model.Order.OrderStatus;
import ua.taxi.utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by andrii on 30.06.16.
 * <p>
 * order_id INT AUTO_INCREMENT,
 * from_address_id INT NOT NULL,
 * to_address_id INT NOT NULL,
 * userPhone VARCHAR(14),
 * userName VARCHAR(30),
 * driverPhone VARCHAR(14),
 * price DECIMAL(5,2),
 * distance DECIMAL(7,1),
 * createTime TIMESTAMP,
 * status_id INT NOT NULL,
 */
public class OrderDaoSQLImpl implements OrderDao {

    AddressDao addressDao = new AddressDao();
    OrderStatusDao orderStatusDao = new OrderStatusDao();

    public int findOrderId(Order order) {

        return findOrderId(order.getUserPhone());
    }

    public int findOrderId(String phone) {

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT o.order_id FROM Orders o WHERE o.userPhone='%s'", phone
            ));
            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException e) {
           // e.printStackTrace();
            return 0;
        }
    }

    public Order getOrderById(int id) {

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT o.from_address_id, " +
                            "o.to_address_id, " +
                            "o.userPhone, " +
                            "o.userName, " +
                            "o.driverPhone, " +
                            "o.price, " +
                            "o.distance, " +
                            "o.createTime, " +
                            "o.status_id " +
                            "FROM Orders o WHERE o.order_id='%d'", id
            ));
            resultSet.next();
            int i = 0;
            Order order = new Order();
            order.setFrom(addressDao.findById(resultSet.getInt(++i)));
            order.setTo(addressDao.findById(resultSet.getInt(++i)));
            order.setUserPhone(resultSet.getString(++i));
            order.setUserName(resultSet.getString(++i));
            order.setDriverPhone(resultSet.getString(++i));
            order.setPrice(resultSet.getDouble(++i));
            order.setDistance(resultSet.getDouble(++i));
            order.setCreateTime(resultSet.getTimestamp(++i).toLocalDateTime().withNano(0));
            order.setOrderStatus(orderStatusDao.findById(resultSet.getInt(++i)));

            return order;
        } catch (SQLException e) {
          //  e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Order> createOrder(Order order) {

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            int from_address_id = addressDao.create(order.getFrom());
            int to_address_id = addressDao.create(order.getTo());
            int status_id = orderStatusDao.create(order.getOrderStatus());

            PreparedStatement pst= connection.prepareStatement(
                    "INSERT INTO Orders (" +
                            "from_address_id, " +
                            "to_address_id, " +
                            "userPhone, " +
                            "userName, " +
                            "driverPhone, " +
                            "price, " +
                            "distance, " +
                            "createTime, " +
                            "status_id )" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            int i = 0;
            pst.setInt(++i, from_address_id);
            pst.setInt(++i, to_address_id);
            pst.setString(++i, order.getUserPhone());
            pst.setString(++i, order.getUserName());
            pst.setString(++i, order.getDriverPhone());
            pst.setDouble(++i, order.getPrice());
            pst.setDouble(++i, order.getDistance());
            pst.setTimestamp(++i, Timestamp.valueOf(order.getCreateTime()));
            pst.setInt(++i, status_id);
            pst.execute();

            List<Order> list = new ArrayList<>();
            list.add(getOrder(order.getUserPhone()));
            return list;

        } catch (SQLException e) {
           // e.printStackTrace();
            return null;
        }
    }

    @Override
    public Order getOrder(String phone) {

        return getOrderById(findOrderId(phone));
    }

    @Override
    public Order updateOrder(String phone, Order newOrder) {

        Order oldOrder = getOrder(phone);

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT o.from_address_id, o.to_address_id, o.status_id " +
                            "FROM Orders o WHERE o.userPhone='%s'", phone
            ));
            resultSet.next();
            int from_address_id = resultSet.getInt(1);
            int to_address_id = resultSet.getInt(2);
            int status_id = resultSet.getInt(3);
            addressDao.update(from_address_id, newOrder.getFrom());
            addressDao.update(to_address_id, newOrder.getTo());
            orderStatusDao.update(status_id, newOrder.getOrderStatus());

            PreparedStatement pst = connection.prepareStatement(
                    "UPDATE Orders SET " +
                            "userName=?, " +
                            "driverPhone=?, " +
                            "price=?, " +
                            "distance=? " +
                            "WHERE userPhone=?"
            );

            pst.setString(1, newOrder.getUserName());
            pst.setString(2, newOrder.getDriverPhone());
            pst.setDouble(3, newOrder.getPrice());
            pst.setDouble(4, newOrder.getDistance());
            pst.setString(5, phone);
            pst.execute();

            return oldOrder;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Order deleteOrder(String phone) {

        Order oldOrder = getOrder(phone);

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT o.from_address_id, o.to_address_id, o.status_id " +
                            "FROM Orders o WHERE o.userPhone='%s'", phone
            ));
            resultSet.next();
            int from_address_id = resultSet.getInt(1);
            int to_address_id = resultSet.getInt(2);
            int status_id = resultSet.getInt(3);

            orderStatusDao.delete(status_id);

            statement.execute(String.format(
                    "DELETE FROM Orders WHERE Orders.userPhone='%s'", phone
            ));

            addressDao.delete(from_address_id);
            addressDao.delete(to_address_id);

            return oldOrder;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Order changeStatus(String phone, OrderStatus newStatus) {

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format(
                    "SELECT o.status_id " +
                            "FROM Orders o WHERE o.userPhone='%s'", phone
            ));
            resultSet.next();

            int status_id = resultSet.getInt(1);
            orderStatusDao.update(status_id, newStatus);

            statement.execute(String.format(
                    "DELETE FROM Orders WHERE Orders.userPhone='%s'", phone
            ));

            return getOrder(phone);

        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Collection<Order> getOrderList() {

        List<Order> list = new ArrayList<>();

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(
                    "SELECT o.from_address_id, " +
                            "o.to_address_id, " +
                            "o.userPhone, " +
                            "o.userName, " +
                            "o.driverPhone, " +
                            "o.price, " +
                            "o.distance, " +
                            "o.createTime, " +
                            "o.status_id " +
                            "FROM Orders o "
            );
            while (resultSet.next()) {
                int i = 0;
                Order order = new Order();
                order.setFrom(addressDao.findById(resultSet.getInt(++i)));
                order.setTo(addressDao.findById(resultSet.getInt(++i)));
                order.setUserPhone(resultSet.getString(++i));
                order.setUserName(resultSet.getString(++i));
                order.setDriverPhone(resultSet.getString(++i));
                order.setPrice(resultSet.getDouble(++i));
                order.setDistance(resultSet.getDouble(++i));
                order.setCreateTime(resultSet.getTimestamp(++i).toLocalDateTime().withNano(0));
                order.setOrderStatus(orderStatusDao.findById(resultSet.getInt(++i)));
                list.add(order);
            }

            return list;
        } catch (SQLException e) {
              e.printStackTrace();
            return null;
        }
    }
}
