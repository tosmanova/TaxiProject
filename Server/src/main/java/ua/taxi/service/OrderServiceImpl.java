package ua.taxi.service;

import org.apache.log4j.Logger;
import ua.taxi.constants.Constants;
import ua.taxi.dao.OrderDao;

import ua.taxi.model.geolocation.GoogleMapsAPI;
import ua.taxi.model.geolocation.GoogleMapsAPIImpl;
import ua.taxi.model.geolocation.Location;
import ua.taxi.model.order.Address;
import ua.taxi.model.order.Order;
import ua.taxi.model.order.OrderStatus;
import ua.taxi.model.order.OrderValidateMessage;

import java.util.*;

/**
 * Created by Andrii on 4/29/2016.
 */
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private GoogleMapsAPI googleMapsAPI= new GoogleMapsAPIImpl();
    private static final Logger LOGGER = Logger.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl() {
    }

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public OrderValidateMessage createOrder(String phone, String name, Address from, Address to) {
        double price = getPrice(from, to);
        double distance = getDistance(from, to);
        if (orderDao.getOrder(phone) == null) {
            Order newOrder = new Order(from, to, phone, name, price, distance);
            orderDao.createOrder(newOrder);
            LOGGER.trace("createOrder: " + newOrder);
            return new OrderValidateMessage(newOrder, "order Creation", newOrder.toString(), true);
        } else if (orderDao.getOrder(phone).getOrderStatus() == OrderStatus.DONE) {
            orderDao.deleteOrder(phone);
            Order newOrder = new Order(from, to, phone, name, price, distance);
            orderDao.createOrder(newOrder);
            LOGGER.trace("createOrder: " + newOrder);
            return new OrderValidateMessage(newOrder, "order Creation", newOrder.toString(), true);
        }
        LOGGER.warn("order Creation: You already have active orders");
        return new OrderValidateMessage(null, "order Creation", "You already have active orders", false);
    }

    @Override
    public OrderValidateMessage getOrder(String phone) {

        Order order = orderDao.getOrder(phone);
        if (order != null) {
            LOGGER.trace("getOrder" + order);
            return new OrderValidateMessage(order, "Get order", order.toString(), true);
        }
        LOGGER.warn("Get order. You don`t have any orders");
        return new OrderValidateMessage(null, "Get order", "You don`t have any orders", false);
    }

    @Override
    public OrderValidateMessage changeOrder(String phone, String name, Address from, Address to) {
        double price = getPrice(from, to);
        double distance = getDistance(from, to);
        if (orderDao.getOrder(phone) != null) {
            Order newOrder = new Order(from, to, phone, name, price, distance);
            Order oldOrder = orderDao.updateOrder(phone, newOrder);
            LOGGER.trace("Change Order4 new: " + newOrder + ";  old:" + oldOrder);
            return new OrderValidateMessage(newOrder, "Change order", oldOrder.toString(), true);
        }
        LOGGER.warn("Change Order4 error, You don`t have order with this phone");
        return new OrderValidateMessage(null, "Change order error", "You don`t have order with this phone", false);
    }

    @Override
    public OrderValidateMessage changeOrder(String phone, Order newOrder) {

        if (orderDao.getOrder(phone) != null) {
            Order oldOrder = orderDao.updateOrder(phone, newOrder);
            LOGGER.trace("Change Order2 new: " + newOrder + "; old" + oldOrder);
            return new OrderValidateMessage(newOrder, "Change order", oldOrder.toString(), true);
        }
        LOGGER.warn("Change Order2 error, You don`t have order with this phone");
        return new OrderValidateMessage(null, "Change order error", "You don`t have order with this phone", false);
    }

    @Override
    public OrderValidateMessage cancelOrder(String phone) {
        if (orderDao.getOrder(phone) != null) {
            Order oldOrder = orderDao.deleteOrder(phone);
            LOGGER.trace("Cancel order:" + oldOrder);
            return new OrderValidateMessage(oldOrder, "Cancel order", oldOrder.toString(), true);
        }
        LOGGER.warn("Cancel order You don`t have any orders");
        return new OrderValidateMessage(null, "Cancel order", "You don`t have any orders", false);

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
            LOGGER.trace("changeOrderStatus new: " + newStatus + " Old order: " + oldOrder);
            return new OrderValidateMessage(oldOrder, "Change order Status", oldOrder.toString(), true);
        }
        LOGGER.warn("Change order Status, You don`t have any orders");
        return new OrderValidateMessage(null, "Change order Status", "You don`t have any orders", false);
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
            if (order.getOrderStatus() == OrderStatus.NEW) {
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
                    return new OrderValidateMessage(order, "Active driver order", "Active driver order", true);
                }
            }
        }
        LOGGER.warn("IN_PROGRESS driver order not found");
        return new OrderValidateMessage(null, "Active driver order", "IN_PROGRESS driver order not found", false);
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
        double price = Constants.MIN_PRICE + (distance / 1000) * Constants.KILOMETRE_PRICE;
        LOGGER.trace(String.format("getPrice: for distance %f = %f", distance, price));
        return price;
    }

    public Double getPrice(Address from, Address to) {
        double price = getPrice(getDistance(from, to));
        LOGGER.trace(String.format("getDistance: from %s  to %s = %f", from, to, price));
        return price;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }
}
