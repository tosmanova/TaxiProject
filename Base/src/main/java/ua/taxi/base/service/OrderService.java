package ua.taxi.base.service;

import ua.taxi.base.exception.RemoteConnectionError;
import ua.taxi.base.model.order.Order;
import ua.taxi.base.model.order.OrderStatus;
import ua.taxi.base.model.order.OrderValidateMessage;
import ua.taxi.base.model.order.Address;

import java.util.List;
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

    List<Order> getAllOrders() throws RemoteConnectionError;

    List<Order> getNewOrders() throws RemoteConnectionError;

    Map<OrderStatus, Integer> getStatusCounterMap() throws RemoteConnectionError;

    OrderValidateMessage changeOrder(String phone, String name, Address from, Address to);

    OrderValidateMessage changeOrder(String phone, Order newOrder);

    OrderValidateMessage cancelOrder(String phone);

    Double getDistance(Address from, Address to) throws RemoteConnectionError;

    Double getPrice(Double distance) throws RemoteConnectionError;

    Double getPrice(Address from, Address to) throws RemoteConnectionError;

}
