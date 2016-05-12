package ua.taxi.dao;

import ua.taxi.model.Order;
import ua.taxi.model.OrderStatus;

import java.util.Collection;
import java.util.List;

/**
 * Created by Andrii on 4/25/2016.
 */
public class OrderDaoInnerDbImpl implements OrderDao {

    private AppDB appDB;

    public OrderDaoInnerDbImpl(AppDB appDB) {
        this.appDB = appDB;
    }

    @Override
    public Collection<Order> addOrder(Order order) {
        return appDB.addOrder(order);
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
