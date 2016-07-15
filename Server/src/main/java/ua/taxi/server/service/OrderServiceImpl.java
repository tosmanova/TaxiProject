package ua.taxi.server.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.taxi.base.service.OrderService;
import ua.taxi.server.constants.Constants;
import ua.taxi.server.dao.OrderDao;

import ua.taxi.base.model.geolocation.GoogleMapsAPI;
import ua.taxi.base.model.geolocation.GoogleMapsAPIImpl;
import ua.taxi.base.model.geolocation.Location;
import ua.taxi.base.model.order.Address;
import ua.taxi.base.model.order.Order;
import ua.taxi.base.model.order.OrderStatus;
import ua.taxi.base.model.order.OrderValidateMessage;

import javax.persistence.PersistenceException;
import java.util.*;

/**
 * Created by Andrii on 4/29/2016.
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    private GoogleMapsAPI googleMapsAPI = new GoogleMapsAPIImpl();
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

        try {
            Order order = orderDao.getOrder(phone);
            if (order.getOrderStatus() == OrderStatus.DONE) {
                orderDao.deleteOrder(phone);
            } else {
                LOGGER.warn("order Creation: You already have active orders");
                return new OrderValidateMessage(null, "order Creation", "You already have active orders", false);
            }
        } catch (PersistenceException e) {
        }
        Order newOrder = new Order(from, to, phone, name, price, distance);
        orderDao.createOrder(newOrder);
        LOGGER.trace("createOrder: " + newOrder);
        return new OrderValidateMessage(newOrder, "order Creation", newOrder.toString(), true);
    }


    @Override
    public OrderValidateMessage getOrder(String phone) {

        try {
            Order order = orderDao.getOrder(phone);
            LOGGER.trace("getOrder" + order);
            return new OrderValidateMessage(order, "Get order", order.toString(), true);
        } catch (PersistenceException e) {
            LOGGER.warn("Get order. You don`t have any orders");
            return new OrderValidateMessage(null, "Get order", "You don`t have any orders", false);
        }

    }

    @Override
    public OrderValidateMessage changeOrder(String phone, String name, Address from, Address to) {
        double price = getPrice(from, to);
        double distance = getDistance(from, to);
        try {
            Order newOrder = new Order(from, to, phone, name, price, distance);
            Order oldOrder = orderDao.updateOrder(phone, newOrder);
            LOGGER.trace("Change Order new: " + newOrder + ";  old:" + oldOrder);
            return new OrderValidateMessage(newOrder, "Change order", oldOrder.toString(), true);
        } catch (PersistenceException e) {
            LOGGER.warn("Change Order error, You don`t have order with this phone \n" + e);
            return new OrderValidateMessage(null, "Change order error", "You don`t have order with this phone", false);
        }
    }

    @Override
    public OrderValidateMessage changeOrder(String phone, Order newOrder) {

        try {
            Order oldOrder = orderDao.updateOrder(phone, newOrder);
            LOGGER.trace("Change Order2 new: " + newOrder + "; old" + oldOrder);
            return new OrderValidateMessage(newOrder, "Change order", oldOrder.toString(), true);
        } catch (PersistenceException e) {
            LOGGER.warn("Change Order2 error, You don`t have order with this phone");
            return new OrderValidateMessage(null, "Change order error", "You don`t have order with this phone", false);
        }
    }

    @Override
    public OrderValidateMessage cancelOrder(String phone) {
        try {
            Order oldOrder = orderDao.deleteOrder(phone);
            LOGGER.trace("Cancel order:" + oldOrder);
            return new OrderValidateMessage(oldOrder, "Cancel order", oldOrder.toString(), true);
        } catch (PersistenceException e) {
            LOGGER.warn("Cancel order You don`t have any orders");
            return new OrderValidateMessage(null, "Cancel order", "You don`t have any orders", false);
        }
    }

    @Override
    public int ordersRegisteredQuantity() {
        int regOrders = orderDao.getOrdersRegisteredQuantity();
        LOGGER.trace("getOrdersRegisteredQuantity: " + regOrders);
        return regOrders;
    }

    @Override
    public OrderValidateMessage changeOrderStatus(String phone, OrderStatus newStatus) {

        try {
            Order order = orderDao.getOrder(phone);
            order.setOrderStatus(newStatus);
            Order oldOrder = orderDao.updateOrder(phone, order);
            LOGGER.trace("changeOrderStatus new: " + newStatus + " Old order: " + oldOrder);
            return new OrderValidateMessage(oldOrder, "Change order Status", oldOrder.toString(), true);
        } catch (PersistenceException e) {
            LOGGER.warn("Change order Status, You don`t have any orders");
            return new OrderValidateMessage(null, "Change order Status", "You don`t have any orders", false);
        }
    }

    @Override
    public List<Order> getAllOrders(long firstId, int offset) {

        List<Order> orderList = orderDao.getOrderList(firstId, offset);
        LOGGER.trace("getAllOrders size:" + orderList.size());
        return orderList;
    }

    @Override
    public List<Order> getOrdersWithStatus(OrderStatus orderStatus, long firstId, int offset) {

        List<Order> orderList = orderDao.getOrderWithStatus(orderStatus, firstId, offset);
        LOGGER.trace("getOrders with status" + orderStatus + " size:" + orderList.size());
        return orderList;
    }

    @Override
    public OrderValidateMessage getOrderInProgresByDriverPhone(String driverPhone) {
        try {
            Order order = orderDao.getOrder(driverPhone);
            if (order.getOrderStatus() != OrderStatus.IN_PROGRESS) {
                throw new PersistenceException();
            }
            return new OrderValidateMessage(order, "Active driver order", "IN_PROGRESS driver order", true);
        } catch (PersistenceException e) {
            LOGGER.warn("IN_PROGRESS driver order not found for phone: " + driverPhone);
            return new OrderValidateMessage(null, "Active driver order", "IN_PROGRESS driver order not found", false);
        }
    }

    @Override
    public Map<OrderStatus, Integer> getStatusCounterMap() {


        Map<OrderStatus, Integer> counterMap = new HashMap<>();

        int newOrder = orderDao.getOrdersNewCount();
        int done = orderDao.getOrdersDoneCount();
        int inProgress = orderDao.getOrdersInProgressCount();

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
}
