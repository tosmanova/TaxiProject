package ua.taxi.remote;

import ua.taxi.model.*;
import ua.taxi.model.RemoteObject;
import ua.taxi.service.OrderService;
import ua.taxi.to.Client;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by andrii on 06.06.16.
 */
public class RemoteOrderService implements OrderService {
    Client client;

    public RemoteOrderService(Client client) {
        this.client = client;
    }

    @Override
    public int ordersRegisteredQuantity() {

        try {
            return (int) send(new RemoteObject(OrderMethods.ORDERS_REGISTERED_QUANTITY));
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public OrderValidateMessage createOrder(String userPhone, String userName, Address from, Address to) {

        RemoteObject remoteObject = new RemoteObject(OrderMethods.CREATE_ORDER);
        remoteObject.setUserPhone(userPhone);
        remoteObject.setUserName(userName);
        remoteObject.setFrom(from);
        remoteObject.setTo(to);

        try {
            return (OrderValidateMessage) send(remoteObject);
        } catch (IOException e) {
            e.printStackTrace();
            return new OrderValidateMessage(null, "Remote error", e.getMessage(), false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new OrderValidateMessage(null, "Remote error", e.getMessage(), false);
        }

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

    private Object send(RemoteObject remoteObject) throws IOException, ClassNotFoundException {
        client.send(remoteObject);
        return client.receive();
    }
}
