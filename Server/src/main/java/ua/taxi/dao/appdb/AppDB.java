package ua.taxi.dao.appdb;

import org.apache.log4j.Logger;
import ua.taxi.dao.OrderDao;
import ua.taxi.dao.UserDao;
import ua.taxi.dao.serialize.SaveLoad;
import ua.taxi.model.Order.Order;
import ua.taxi.model.Order.OrderStatus;
import ua.taxi.model.User.User;

import java.io.IOException;
import java.util.*;

// operations in application
public class AppDB implements OrderDao, UserDao {

    private Map<String, User> users = new HashMap<>();
    private List<String> phoneBlackList = new ArrayList<>();
    private Map<String, Order> orders = new HashMap<>();
    private SaveLoad saveLoad;
    private static final Logger LOGGER = Logger.getLogger(AppDB.class);

    public AppDB(SaveLoad saveLoad) {

        this.saveLoad = saveLoad;
        LOGGER.info("init AppDB");
    }

    @Override
    public User update(User newUser) {
        return users.replace(newUser.getPhone(), newUser);
    }

    @Override
    public User delete(String phone) {
        return users.remove(phone);
    }

    @Override
    public List<String> setToBlackList(String phone) {
        users.remove(phone);
        phoneBlackList.add(phone);
        return phoneBlackList;
    }

    @Override
    public User getUser(String phone) {
        return users.get(phone);
    }

    @Override
    public Collection<User> createUser(User user) {
        users.put(user.getPhone(), user);
        return users.values();
    }

    @Override
    public int driverRegisteredQuantity() {
        return 0;
    }

    @Override
    public int passangerRegisteredQuantity() {
        return 0;
    }

    //@Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    /**
     * ******************* Orders *************************************************
     */
    @Override
    public Collection<Order> getOrderList() {
        return orders.values();
    }

    @Override
    public Order deleteOrder(String phone) {
        return orders.remove(phone);
    }

    @Override
    public Order updateOrder(String phone, Order newOrder) {
        return orders.replace(phone, newOrder);
    }

    @Override
    public Order getOrder(String phone) {
        return orders.get(phone);
    }

    @Override
    public Collection<Order> createOrder(Order order) {
        orders.put(order.getUserPhone(), order);
        return orders.values();
    }

    @Override
    public Order changeStatus(String phone, OrderStatus newStatus) {
        Order order = orders.get(phone);
        order.setOrderStatus(newStatus);
        return orders.replace(phone, order);
    }

    /**
     * ******************* Save-Load *************************************************
     */

    public void saveUsers() {
        if (users != null) {
            saveLoad.saveUserMap(users);
            LOGGER.info("saved all Users");
        } else {
            LOGGER.error("saveUsers error. No users");
        }
    }

    public void loadUsers() {
        try {
            users = saveLoad.loadUserMap();
            LOGGER.info("all Users loaded");
        } catch (IOException e) {
            LOGGER.error("loadUserMap error", e);
        }
    }

    public void saveOrders() {
        if (orders != null) {
            saveLoad.saveOrderMap(orders);
            LOGGER.info("saved all Orders");
        } else {
            LOGGER.error("saveOrders error. No orders");
        }
    }

    public void loadOrders() {
        try {
            orders = saveLoad.loadOrderMap();
            LOGGER.info("all Orders loaded");
        } catch (IOException e) {
            LOGGER.error("loadOrderMap error", e);
        }
    }

    public void saveBlackList() {
        if (phoneBlackList != null) {
            saveLoad.saveBlackList(phoneBlackList);
            LOGGER.info("saved BlackList");
        } else {
            LOGGER.error("saveBlackList error. No phones");
        }
    }

    public void loadBlackList() {
        try {
            phoneBlackList = saveLoad.loadBlackList();
            LOGGER.info("BlackList Loaded");
        } catch (IOException e) {
            LOGGER.error("loadBlackList error", e);

        }
    }

    public void saveAllData() {
        saveUsers();
        saveOrders();
        saveBlackList();
    }

    public void loadAllData() {
        loadOrders();
        loadUsers();
        loadBlackList();
    }
}