package ua.taxi.server.dao;

import ua.taxi.base.model.order.Order;
import ua.taxi.base.model.order.OrderStatus;

import java.util.Collection;


public interface OrderDao {

    // add order return order iD
    Collection<Order> createOrder(Order order);

    Order getOrder(String phone);

    //return oldOrder
    Order updateOrder(String phone, Order newOrder);

    //return deleted order
    Order deleteOrder(String phone);

    // return order with old Status
    Order changeStatus(String phone, OrderStatus newStatus);

    Collection<Order> getOrderList();
}
