package ua.taxi.dao.appdb;

import ua.taxi.dao.OrderDao;
import ua.taxi.model.Order.Order;
import ua.taxi.model.Order.OrderStatus;

import java.util.Collection;

/**
 * Created by Andrii on 4/25/2016.
 */
public class OrderDaoInnerDbImpl implements OrderDao {

    private AppDB appDB;

    public OrderDaoInnerDbImpl(AppDB appDB) {
        this.appDB = appDB;
    }

    @Override
    public Collection<Order> createOrder(Order order) {
        return appDB.createOrder(order);
    }

    @Override
    public Order getOrder(String phone) {
        return appDB.getOrder(phone);
    }

    @Override
    public Order updateOrder(String phone, Order newOrder) {
        return appDB.updateOrder(phone, newOrder);
    }

    @Override
    public Order deleteOrder(String phone) {
        return appDB.deleteOrder(phone);
    }

    @Override
    public Collection<Order> getOrderList() {
        return appDB.getOrderList();
    }

    @Override
    public Order changeStatus(String phone, OrderStatus newStatus) {
        return appDB.changeStatus(phone, newStatus);
    }
}
