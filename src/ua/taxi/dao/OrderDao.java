package ua.taxi.dao;

import ua.taxi.model.*;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Роман
 * Date: 23.04.16
 * Time: 23:52
 * To change this template use File | Settings | File Templates.
 */
public interface OrderDao {

    // add order return order iD
    Collection<Order> addOrder(Order order);

    Order getOrder(String phone);

    //return oldOrder
    Order updateOrder(String phone, Order newOrder);

    //return deleted order
    Order deleteOrder(String phone);

    // return order with old Status
    Order changeStatus(String phone, OrderStatus newStatus);

    Collection<Order> getOrderList();
}
