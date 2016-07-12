package ua.taxi.server.dao.jpa;

import org.springframework.stereotype.Component;
import ua.taxi.server.dao.OrderDao;
import ua.taxi.base.model.order.Order;
import ua.taxi.base.model.order.OrderStatus;

import java.util.Collection;

/**
 * Created by andrii on 10.07.16.
 */
@Component(value = "orderDao")
public class OrderDaoJPAImpl implements OrderDao {

    @Override
    public Collection<Order> createOrder(Order order) {
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
