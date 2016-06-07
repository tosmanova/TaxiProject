package ua.taxi.remote;

import ua.taxi.model.*;
import ua.taxi.service.OrderService;
import ua.taxi.to.Server;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by andrii on 06.06.16.
 */
public class RemoteOrderService implements OrderService {
    Server server;

    public RemoteOrderService(Server server) {
        this.server = server;
    }

    @Override
    public int ordersRegisteredQuantity() throws IOException, ClassNotFoundException {

        return (int) recive("ordersRegisteredQuantity", null);
    }

    @Override
    public OrderValidateMessage createOrder(String userPhone, String userName, Address from, Address to) throws IOException, ClassNotFoundException {

        Order order = new Order();
        order.setUserPhone(userPhone);
        order.setUserName(userName);
        order.setFrom(from.toString());
        order.setTo(to.toString());

        return (OrderValidateMessage) recive("createOrder", order);
    }

    @Override
    public OrderValidateMessage changeOrderStatus(String phone, OrderStatus newStatus) {
        return null;
    }

    @Override
    public OrderValidateMessage getOrder(String phone) {
        return null;
    }

    @Override
    public OrderValidateMessage getOrderInProgresByDriverPhone(String driverPhone) {
        return null;
    }

    @Override
    public Collection<Order> getAllOrders() {
        return null;
    }

    @Override
    public Collection<Order> getNewOrders() {
        return null;
    }

    @Override
    public Map<OrderStatus, Integer> getStatusCounterMap() {
        return null;
    }

    @Override
    public OrderValidateMessage changeOrder(String phone, String name, Address from, Address to) {
        return null;
    }

    @Override
    public OrderValidateMessage changeOrder(String phone, Order newOrder) {
        return null;
    }

    @Override
    public OrderValidateMessage cancelOrder(String phone) {
        return null;
    }

    @Override
    public Double getDistance(Address from, Address to) {
        return null;
    }

    @Override
    public Double getPrice(Double distance) {
        return null;
    }

    @Override
    public Double getPrice(Address from, Address to) {
        return null;
    }

    private Object recive(String methodName, Object object) throws IOException, ClassNotFoundException {
        server.send(new RemoteObject(methodName, object));
        return server.recive();
    }
}
