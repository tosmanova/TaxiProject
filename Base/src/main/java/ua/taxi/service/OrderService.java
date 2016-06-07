package ua.taxi.service;

import ua.taxi.model.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Andrii on 4/29/2016.
 */
public interface OrderService {

    int ordersRegisteredQuantity();

    OrderValidateMessage createOrder(String userPhone, String userName, Address from, Address to);

    OrderValidateMessage changeOrderStatus(String phone, OrderStatus newStatus);

    OrderValidateMessage getOrder(String phone);

    OrderValidateMessage getOrderInProgresByDriverPhone(String driverPhone);

    Collection<Order> getAllOrders();

    Collection<Order> getNewOrders();

    Map<OrderStatus,Integer> getStatusCounterMap();

    OrderValidateMessage changeOrder(String phone, String name, Address from, Address to);

    OrderValidateMessage changeOrder(String phone, Order newOrder);

    OrderValidateMessage cancelOrder(String phone);

    Double getDistance(Address from, Address to);

    Double getPrice(Double distance);

    Double getPrice(Address from, Address to);

}
