package ua.taxi.dao.sql;

import ua.taxi.dao.OrderDao;
import ua.taxi.model.Order.Order;
import ua.taxi.model.Order.OrderStatus;

import java.util.Collection;

/**
 * Created by АндрейГ on 26.06.2016.
 */
public class OrderDaoSqlImpl implements OrderDao {

    @Override
    public Collection<Order> addOrder(Order order) {
        return null;
    }

    @Override
    public Order getOrder(String phone) {
        return null;
    }

    @Override
    public Order updateOrder(String phone, Order newOrder) {
        return null;
    }

    @Override
    public Order deleteOrder(String phone) {
        return null;
    }

    @Override
    public Order changeStatus(String phone, OrderStatus newStatus) {
        return null;
    }

    @Override
    public Collection<Order> getOrderList() {
        return null;
    }
}
