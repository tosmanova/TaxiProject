package ua.taxi.server.dao;

import ua.taxi.base.model.order.Order;
import ua.taxi.base.model.order.OrderStatus;

import java.util.Collection;
import java.util.List;


public interface OrderDao {

    // add order return order iD
    Order createOrder(Order order);

    Order getOrder(String phone);

    //return oldOrder
    Order updateOrder(String phone, Order newOrder);

    //return deleted order
    Order deleteOrder(String phone);

    List<Order> getOrderList(long firstId, int offset);

    List<Order> getOrderWithStatus(OrderStatus orderStatus, long firstId, int offset);

    int getOrdersRegisteredQuantity();

    int getOrdersNewCount();

    int getOrdersDoneCount();

    int getOrdersInProgressCount();



}
