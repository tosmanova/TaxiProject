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

    List<Order> getAllOrders(long firstId, int offset);

    List<Order> getOrdersWithStatus(OrderStatus orderStatus, long firstId, int offset);

    Map<OrderStatus, Integer> getStatusCounterMap();

    OrderValidateMessage changeOrder(String phone, String name, Address from, Address to);

    OrderValidateMessage changeOrder(String phone, Order newOrder);

    OrderValidateMessage cancelOrder(String phone);

    Double getDistance(Address from, Address to);

    Double getPrice(Double distance);

    Double getPrice(Address from, Address to);

}
