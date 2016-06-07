package ua.taxi.remote;

import ua.taxi.exception.RemoteConnectionError;
import ua.taxi.model.Order.Address;
import ua.taxi.model.Order.Order;
import ua.taxi.model.Order.OrderStatus;
import ua.taxi.model.Order.OrderValidateMessage;
import ua.taxi.model.Remote.OrderServiceMethods;
import ua.taxi.model.Remote.RemoteOrderObject;
import ua.taxi.service.OrderService;
import ua.taxi.to.Client;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
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
            return (int) send(new RemoteOrderObject(OrderServiceMethods.ORDERS_REGISTERED_QUANTITY));
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

        RemoteOrderObject remoteOrderObject = new RemoteOrderObject(OrderServiceMethods.CREATE_ORDER);
        remoteOrderObject.setUserPhone(userPhone);
        remoteOrderObject.setUserName(userName);
        remoteOrderObject.setFrom(from);
        remoteOrderObject.setTo(to);

        try {
            return (OrderValidateMessage) send(remoteOrderObject);
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

        RemoteOrderObject remoteOrderObject = new RemoteOrderObject(OrderServiceMethods.CHANGE_ORDER_2);
        remoteOrderObject.setUserPhone(phone);
        remoteOrderObject.setOrderStatus(newStatus);

        try {
            return (OrderValidateMessage) send(remoteOrderObject);
        } catch (IOException e) {
            e.printStackTrace();
            return new OrderValidateMessage(null, "Remote error", e.getMessage(), false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new OrderValidateMessage(null, "Remote error", e.getMessage(), false);
        }
    }

    @Override
    public OrderValidateMessage getOrder(String phone) {

        RemoteOrderObject remoteOrderObject = new RemoteOrderObject(OrderServiceMethods.GET_ORDER);
        remoteOrderObject.setUserPhone(phone);

        try {
            return (OrderValidateMessage) send(remoteOrderObject);
        } catch (IOException e) {
            e.printStackTrace();
            return new OrderValidateMessage(null, "Remote error", e.getMessage(), false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new OrderValidateMessage(null, "Remote error", e.getMessage(), false);
        }
    }

    @Override
    public OrderValidateMessage getOrderInProgresByDriverPhone(String driverPhone)
    {
        RemoteOrderObject remoteOrderObject = new RemoteOrderObject(OrderServiceMethods.GET_ORDER_IN_PROGRES_BY_DRIVERPHONE);
        remoteOrderObject.setDriverPhone(driverPhone);

        try {
            return (OrderValidateMessage) send(remoteOrderObject);
        } catch (IOException e) {
            e.printStackTrace();
            return new OrderValidateMessage(null, "Remote error", e.getMessage(), false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new OrderValidateMessage(null, "Remote error", e.getMessage(), false);
        }
    }

    @Override
    public List<Order> getAllOrders() throws RemoteConnectionError {

        try {
            return (List<Order>) send(new RemoteOrderObject(OrderServiceMethods.GET_ALL_ORDERS));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RemoteConnectionError("getAllOrders");
    }

    @Override
    public List<Order> getNewOrders() throws RemoteConnectionError {

        try {
            return (List<Order>) send(new RemoteOrderObject(OrderServiceMethods.GET_ALL_ORDERS));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RemoteConnectionError("getNewOrders");
    }

    @Override
    public Map<OrderStatus, Integer> getStatusCounterMap() throws RemoteConnectionError {

        try {
            return (Map<OrderStatus, Integer>) send(new RemoteOrderObject(OrderServiceMethods.GET_STATUS_COUNTERMAP));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RemoteConnectionError("getStatusCounterMap");

    }

    @Override
    public OrderValidateMessage changeOrder(String phone, String name, Address from, Address to) {

        RemoteOrderObject remoteOrderObject = new RemoteOrderObject(OrderServiceMethods.CHANGE_ORDER_4);
        remoteOrderObject.setUserPhone(phone);
        remoteOrderObject.setUserName(name);
        remoteOrderObject.setFrom(from);
        remoteOrderObject.setTo(to);

        try {
            return (OrderValidateMessage) send(remoteOrderObject);
        } catch (IOException e) {
            e.printStackTrace();
            return new OrderValidateMessage(null, "Remote error", e.getMessage(), false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new OrderValidateMessage(null, "Remote error", e.getMessage(), false);
        }
    }

    @Override
    public OrderValidateMessage changeOrder(String phone, Order newOrder) {

        RemoteOrderObject remoteOrderObject = new RemoteOrderObject(OrderServiceMethods.CHANGE_ORDER_2);
        remoteOrderObject.setUserPhone(phone);
        remoteOrderObject.setOrder(newOrder);

        try {
            return (OrderValidateMessage) send(remoteOrderObject);
        } catch (IOException e) {
            e.printStackTrace();
            return new OrderValidateMessage(null, "Remote error", e.getMessage(), false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new OrderValidateMessage(null, "Remote error", e.getMessage(), false);
        }
    }

    @Override
    public OrderValidateMessage cancelOrder(String phone) {

        RemoteOrderObject remoteOrderObject = new RemoteOrderObject(OrderServiceMethods.CANCEL_ORDER);
        remoteOrderObject.setUserPhone(phone);

        try {
            return (OrderValidateMessage) send(remoteOrderObject);
        } catch (IOException e) {
            e.printStackTrace();
            return new OrderValidateMessage(null, "Remote error", e.getMessage(), false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new OrderValidateMessage(null, "Remote error", e.getMessage(), false);
        }
    }

    @Override
    public Double getDistance(Address from, Address to) throws RemoteConnectionError {

        RemoteOrderObject remoteOrderObject = new RemoteOrderObject(OrderServiceMethods.GET_DISTANCE);
        remoteOrderObject.setFrom(from);
        remoteOrderObject.setTo(to);

        try {
            return (Double) send(remoteOrderObject);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RemoteConnectionError("getDistance");
    }

    @Override
    public Double getPrice(Double distance) throws RemoteConnectionError {

        RemoteOrderObject remoteOrderObject = new RemoteOrderObject(OrderServiceMethods.GET_PRICE);
        remoteOrderObject.setDistance(distance);

        try {
            return (Double) send(remoteOrderObject);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RemoteConnectionError("getPrice");
    }

    @Override
    public Double getPrice(Address from, Address to) throws RemoteConnectionError {

        RemoteOrderObject remoteOrderObject = new RemoteOrderObject(OrderServiceMethods.GET_PRICE2);
        remoteOrderObject.setFrom(from);
        remoteOrderObject.setTo(to);

        try {
            return (Double) send(remoteOrderObject);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RemoteConnectionError("getPrice");
    }

    private Object send(RemoteOrderObject remoteOrderObject) throws IOException, ClassNotFoundException {
        client.send(remoteOrderObject);
        return client.receive();
    }
}
