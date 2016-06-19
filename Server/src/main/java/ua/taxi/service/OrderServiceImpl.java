package ua.taxi.service;

import org.apache.log4j.Logger;
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
    public static final Logger LOGGER = Logger.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
        LOGGER.info("init OrderServiceImpl");
    }


    @Override
    public OrderValidateMessage createOrder(String phone, String name, Address from, Address to) {
        double price = getPrice(from, to);
        double distance = getDistance(from, to);
        if (orderDao.getOrder(phone) == null) {
            Order newOrder = new Order(from, to, phone, name, price, distance);
            orderDao.addOrder(newOrder);
            LOGGER.trace("createOrder: " + newOrder);
            return new OrderValidateMessage(newOrder, "Order Creation", newOrder.toString(), true);
        }
        LOGGER.warn("Order Creation: You already have active orders");
        return new OrderValidateMessage(null, "Order Creation", "You already have active orders", false);
    }

    @Override
    public OrderValidateMessage getOrder(String phone) {

        Order order = orderDao.getOrder(phone);
        if (order != null) {
            LOGGER.trace("getOrder" + order);
            return new OrderValidateMessage(order, "Get Order", order.toString(), true);
        }
        LOGGER.warn("Get Order. You don`t have any orders");
        return new OrderValidateMessage(null, "Get Order", "You don`t have any orders", false);
    }

    @Override
    public OrderValidateMessage changeOrder(String phone, String name, Address from, Address to) {
        double price = getPrice(from, to);
        double distance = getDistance(from, to);
        if (orderDao.getOrder(phone) != null) {
            Order newOrder = new Order(from, to, phone, name, price, distance);
            Order oldOrder = orderDao.updateOrder(phone, newOrder);
            LOGGER.trace("Change Order4 new: " + newOrder + ";  old:" + oldOrder);
            return new OrderValidateMessage(newOrder, "Change Order", oldOrder.toString(), true);
        }
        LOGGER.warn("Change Order4 error, You don`t have order with this phone");
        return new OrderValidateMessage(null, "Change Order error", "You don`t have order with this phone", false);
    }

    @Override
    public OrderValidateMessage changeOrder(String phone, Order newOrder) {

        if (orderDao.getOrder(phone) != null) {
            Order oldOrder = orderDao.updateOrder(phone, newOrder);
            LOGGER.trace("Change Order2 new: " + newOrder + "; old" + oldOrder);
            return new OrderValidateMessage(newOrder, "Change Order", oldOrder.toString(), true);
        }
        LOGGER.warn("Change Order2 error, You don`t have order with this phone");
        return new OrderValidateMessage(null, "Change Order error", "You don`t have order with this phone", false);
    }

    @Override
    public OrderValidateMessage cancelOrder(String phone) {
        if (orderDao.getOrder(phone) != null) {
            Order oldOrder = orderDao.deleteOrder(phone);
            LOGGER.trace("Cancel Order:" + oldOrder);
            return new OrderValidateMessage(oldOrder, "Cancel Order", oldOrder.toString(), true);
        }
        LOGGER.warn("Cancel Order You don`t have any orders");
        return new OrderValidateMessage(null, "Cancel Order", "You don`t have any orders", false);

    }

    @Override
    public int ordersRegisteredQuantity() {
        Collection<Order> orders = orderDao.getOrderList();
        LOGGER.trace("ordersRegisteredQuantity: " + orders.size());
        return orders.size();
    }

    @Override
    public OrderValidateMessage changeOrderStatus(String phone, OrderStatus newStatus) {
        if (orderDao.getOrder(phone) != null) {
            Order oldOrder = orderDao.changeStatus(phone, newStatus);
            LOGGER.trace("changeOrderStatus new: " + newStatus + " Old Order: " + oldOrder);
            return new OrderValidateMessage(oldOrder, "Change Order Status", oldOrder.toString(), true);
        }
        LOGGER.warn("Change Order Status, You don`t have any orders");
        return new OrderValidateMessage(null, "Change Order Status", "You don`t have any orders", false);
    }

    @Override
    public List<Order> getAllOrders() {

        LOGGER.trace("getAllOrders size:" + orderDao.getOrderList().size());
        return new ArrayList<>(orderDao.getOrderList());
    }

    @Override
    public List<Order> getNewOrders() {
        Collection<Order> allOrders = orderDao.getOrderList();
        List<Order> newOrders = new ArrayList<>();

        for (Order order : allOrders) {
            if (order.getOrderStatus() == OrderStatus.NEW){
                newOrders.add(order);
            }
        }
        LOGGER.trace("getNewOrders size:" + newOrders.size());
        return new ArrayList<>(newOrders);
    }

    @Override
    public OrderValidateMessage getOrderInProgresByDriverPhone(String driverPhone) {
        System.out.println(driverPhone);
        System.out.println(orderDao.getOrderList().size());
        for (Order order : orderDao.getOrderList()) {
            if (order.getDriverPhone().equals(driverPhone)) {
                if (order.getOrderStatus() == OrderStatus.IN_PROGRESS) {
                    LOGGER.trace("getOrderInProgresByDriverPhone: " + order);
                    return new OrderValidateMessage(order, "Active driver Order", "Active driver Order", true);
                }
            }
        }
        LOGGER.warn("IN_PROGRESS driver Order not found");
        return new OrderValidateMessage(null, "Active driver Order", "IN_PROGRESS driver Order not found", false);
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
        LOGGER.trace("getStatusCounterMap: " + " newOrder:" + newOrder + "; done: " + done + "; inProgress: " + inProgress);
        return counterMap;
    }

    @Override
    public Double getDistance(Address from, Address to) {

        Location locationFrom = googleMapsAPI.findLocation("Ukraine", "Kiev", from.getStreet(), from.getHouseNum());
        Location locationTo = googleMapsAPI.findLocation("Ukraine", "Kiev", to.getStreet(), to.getHouseNum());

        double distance = googleMapsAPI.getDistance(locationFrom, locationTo);
        LOGGER.trace(String.format("getDistance: from %s  to %s = %f", from, to, distance));
        return distance;
    }

    @Override
    public Double getPrice(Double distance) {
        double price = MIN_PRICE + (distance / 1000) * KILOMETRE_PRICE;
        LOGGER.trace(String.format("getPrice: for distance %f = %f", distance, price));
        return price;
    }

    public Double getPrice(Address from, Address to) {
        double price = getPrice(getDistance(from, to));
        LOGGER.trace(String.format("getDistance: from %s  to %s = %f", from, to, price));
        return price;
    }
}
