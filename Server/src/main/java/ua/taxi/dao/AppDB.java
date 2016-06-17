package ua.taxi.dao;

import org.apache.log4j.Logger;
import ua.taxi.dao.serialize.JsonSaveLoad;
import ua.taxi.dao.serialize.SaveLoad;
import ua.taxi.model.Order.Order;
import ua.taxi.model.Order.OrderStatus;
import ua.taxi.model.User.User;

import java.io.File;
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
    public Collection<User> addUser(User user) {
        users.put(user.getPhone(), user);
        return users.values();
    }

    @Override
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
    public Collection<Order> addOrder(Order order) {
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

    private void saveUsers() {
        if(users != null) {
            saveLoad.saveUserMap(users);
        }else {
            LOGGER.error("saveUsers error. No users" );
        }
    }

    private void loadUsers() {
        try {
            users = saveLoad.loadUserMap();
        } catch (IOException e) {
            LOGGER.error("loadUserMap error", e);
        }
    }

    private void saveOrders() {
        if(orders != null) {
            saveLoad.saveOrderMap(orders);
        }else {
            LOGGER.error("saveOrders error. No orders" );
        }
    }

    private void loadOrders() {
        try {
            orders = saveLoad.loadOrderMap();
        } catch (IOException e) {
            LOGGER.error("loadOrderMap error", e);
        }
    }

    private void saveBlackList() {
        if(phoneBlackList != null){
        saveLoad.saveBlackList(phoneBlackList);
        }else {
            LOGGER.error("saveBlackList error. No phones" );
        }
    }

    private void loadBlackList() {
        try {
            phoneBlackList = saveLoad.loadBlackList();
        } catch (IOException e) {
            LOGGER.error("loadBlackList error", e);

        }
    }

    public void saveAllData(){
        saveUsers();
        saveOrders();
        saveBlackList();
    }

    public void loadAllData(){
        loadOrders();
        loadUsers();
        loadBlackList();
    }
}
