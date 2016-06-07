package ua.taxi.service;

import ua.taxi.dao.OrderDao;
import ua.taxi.geolocation.GoogleMapsAPI;
import ua.taxi.geolocation.GoogleMapsAPIImpl;
import ua.taxi.geolocation.Location;
import ua.taxi.model.Order.Address;
import ua.taxi.model.Order.Order;
import ua.taxi.model.Order.OrderStatus;
import ua.taxi.model.Order.OrderValidateMessage;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Created by Andrii on 4/29/2016.
 */
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private GoogleMapsAPI googleMapsAPI = new GoogleMapsAPIImpl();
    private final double MIN_PRICE = 40;
    private final double KILOMETRE_PRICE = 5;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }


    @Override
    public OrderValidateMessage createOrder(String phone, String name, Address from, Address to) {
        double price = getPrice(from, to);
        double distance = getDistance(from, to);
        if (orderDao.getOrder(phone) == null) {
            Order newOrder = new Order(from, to, phone, name, price, distance);
            orderDao.addOrder(newOrder);
            return new OrderValidateMessage(newOrder, "Order Creation", newOrder.toString(), true);
        }
        return new OrderValidateMessage(null, "Order Creation", "You already have active orders", false);
    }

    @Override
    public OrderValidateMessage getOrder(String phone) {

        Order order = orderDao.getOrder(phone);
        if (order != null) {
            return new OrderValidateMessage(order, "Get Order", order.toString(), true);
        }
        return new OrderValidateMessage(null, "Get Order", "You don`t have any orders", false);
    }

    @Override
    public OrderValidateMessage changeOrder(String phone, String name, Address from, Address to) {
        double price = getPrice(from, to);
        double distance = getDistance(from, to);
        if (orderDao.getOrder(phone) != null) {
            Order newOrder = new Order(from, to, phone, name, price, distance);
            Order oldOrder = orderDao.updateOrder(phone, newOrder);
            return new OrderValidateMessage(newOrder, "Change Order", oldOrder.toString(), true);
        }
        return new OrderValidateMessage(null, "Change Order error", "You don`t have order with this phone", false);
    }

    @Override
    public OrderValidateMessage changeOrder(String phone, Order newOrder) {

        if (orderDao.getOrder(phone) != null) {
            Order oldOrder = orderDao.updateOrder(phone, newOrder);
            return new OrderValidateMessage(newOrder, "Change Order", oldOrder.toString(), true);
        }
        return new OrderValidateMessage(null, "Change Order error", "You don`t have order with this phone", false);
    }

    @Override
    public OrderValidateMessage cancelOrder(String phone) {
        if (orderDao.getOrder(phone) != null) {
            Order oldOrder = orderDao.deleteOrder(phone);
            return new OrderValidateMessage(oldOrder, "Cancel Order", oldOrder.toString(), true);
        }
        return new OrderValidateMessage(null, "Cancel Order", "You don`t have any orders", false);

    }

    @Override
    public int ordersRegisteredQuantity() {
        Collection<Order> orders = orderDao.getOrderList();
        return orders.size();
    }

    @Override
    public OrderValidateMessage changeOrderStatus(String phone, OrderStatus newStatus) {
        if (orderDao.getOrder(phone) != null) {
            Order oldOrder = orderDao.changeStatus(phone, newStatus);
            return new OrderValidateMessage(oldOrder, "Change Order Status", oldOrder.toString(), true);
        }
        return new OrderValidateMessage(null, "Change Order Status", "You don`t have any orders", false);
    }

    @Override
    public List<Order> getAllOrders() {
        return new ArrayList<>(orderDao.getOrderList());
    }

    @Override
    public List<Order> getNewOrders() {
        Collection<Order> allOrders = orderDao.getOrderList();
        Collection<Order> newOrders = allOrders.stream().filter(order -> order.getOrderStatus() == OrderStatus.NEW)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        return new ArrayList<>(newOrders);
    }

    @Override
    public OrderValidateMessage getOrderInProgresByDriverPhone(String driverPhone) {

        for (Order order : orderDao.getOrderList()) {
            if (order.getDriverPhone().equals(driverPhone)) {
                if (order.getOrderStatus() == OrderStatus.IN_PROGRESS) {
                    return new OrderValidateMessage(order, "Active driver Order", "Active driver Order", true);
                }
            }
        }
        return new OrderValidateMessage(null, "Active driver Order", "Active driver Order not found", false);
    }

    @Override
    public Map<OrderStatus, Integer> getStatusCounterMap() {

        Collection<Order> orders = orderDao.getOrderList();
        Map<OrderStatus, Integer> counterMap = new HashMap<>();
        int newOrder = 0;
        int inProgress = 0;
        int done = 0;

        for (Order order : orders) {
            if (order.getOrderStatus() == OrderStatus.NEW) {
                newOrder++;
            } else if (order.getOrderStatus() == OrderStatus.IN_PROGRESS) {
                inProgress++;
            } else if (order.getOrderStatus() == OrderStatus.DONE) {
                done++;
            }
        }
        counterMap.put(OrderStatus.NEW, newOrder);
        counterMap.put(OrderStatus.DONE, done);
        counterMap.put(OrderStatus.IN_PROGRESS, inProgress);
        return counterMap;
    }

    @Override
    public Double getDistance(Address from, Address to) {

        Location locationFrom = googleMapsAPI.findLocation("Ukraine", "Kiev", from.getStreet(), from.getHouseNum());
        Location locationTo = googleMapsAPI.findLocation("Ukraine", "Kiev", to.getStreet(), to.getHouseNum());

        double distance = googleMapsAPI.getDistance(locationFrom, locationTo);
        //   System.out.println(distance);
        return distance;
    }

    @Override
    public Double getPrice(Double distance) {
        double price = MIN_PRICE + (distance / 1000) * KILOMETRE_PRICE;
        //   System.out.println(price);
        return price;
    }

    public Double getPrice(Address from, Address to) {

        return getPrice(getDistance(from, to));
    }
}
