package ua.taxi.dao;

import javafx.scene.control.Alert;
import ua.taxi.exception.TaxiAppException;
import ua.taxi.model.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.*;

// operations in application
public class AppDB implements OrderDao, UserDao {

    private final String PATH_NAME = "C:\\Java\\TaxiProject\\DataXML\\";
    private File orderData = new File(PATH_NAME + "orderData.xml");
    private File userData = new File(PATH_NAME + "userData.xml");
    private Map<String, User> users = new HashMap<>();
    private List<String> phoneBlackList = new ArrayList<>();

    private Map<String, Order> orders = new HashMap<>();

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
        saveUsers();
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
        saveOrders();
        return orders.values();
    }

    @Override
    public Order changeStatus(String phone, OrderStatus newStatus) {
        Order order = orders.get(phone);
        order.setOrderStatus(newStatus);
        return orders.replace(phone, order);
    }

    /**
     * ******************* XML Save-Load *************************************************
     */
    private void saveOrders() {
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(OrderMapWriter.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            OrderMapWriter wrapper = new OrderMapWriter();
            wrapper.setOrders(orders);

            jaxbMarshaller.marshal(wrapper, orderData);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + orderData.getPath());

            alert.showAndWait();
        }
    }

    private void loadOrders() {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(OrderMapWriter.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            OrderMapWriter wrapper = (OrderMapWriter) jaxbUnmarshaller.unmarshal(orderData);
            orders.clear();
            orders.putAll(wrapper.getOrders());

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + orderData.getPath());

            alert.showAndWait();
        }
    }

    private void saveUsers() {
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(UserMapWriter.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            UserMapWriter wrapper = new UserMapWriter();
            wrapper.setUsers(users);

            jaxbMarshaller.marshal(wrapper, userData);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + userData.getPath());

            alert.showAndWait();
        }
    }

    private void loadUsers() {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(UserMapWriter.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            UserMapWriter wrapper = (UserMapWriter) jaxbUnmarshaller.unmarshal(userData);
            users.clear();
            users.putAll(wrapper.getUsers());

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data to file:\n" + userData.getPath());

            alert.showAndWait();
        }
    }
}
