package ua.taxi.model;

import javafx.beans.property.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Created by serhii on 23.04.16.
 */
public class Order {

    // private long id;

    private StringProperty from;
    private StringProperty to;
    private StringProperty userPhone;
    private StringProperty userName;
    private StringProperty driverPhone;
    private StringProperty price;
    private StringProperty distance;
    private ObjectProperty<LocalDateTime> createTime;
    private StringProperty createTimeAsString;
    private ObjectProperty<OrderStatus> orderStatus;

    public Order() {
    }

    public Order(Address from, Address to, String userPhone, String userName, Double price, Double distance) {
        this.from = new SimpleStringProperty(from.toString());
        this.to = new SimpleStringProperty(to.toString());
        this.userPhone = new SimpleStringProperty(userPhone);
        this.price = new SimpleStringProperty(String.format("%3.0f грн.", price));
        this.userName = new SimpleStringProperty(userName);
        orderStatus = new SimpleObjectProperty<>(OrderStatus.NEW);
        createTime = new SimpleObjectProperty<>(LocalDateTime.now());

        createTimeAsString = new SimpleStringProperty(createTime.get()
                .format(DateTimeFormatter.ofPattern("HH.mm.ss a")));
        this.distance = new SimpleStringProperty(String.format("%3.2f km", distance / 1000));
        driverPhone = new SimpleStringProperty(" ");
    }

    public Address getFrom() {
        return new Address(from.get());
    }

    public Address getTo() {
        return new Address(to.get());
    }

    public String getUserPhone() {
        return userPhone.get();
    }

    public String getUserName() {
        return userName.get();
    }

    public String getDriverPhone() {
        return driverPhone.get();
    }

    public OrderStatus getOrderStatus() {
        return orderStatus.get();
    }

    public LocalDateTime getCreateTime() {
        return createTime.get();
    }

    public StringProperty fromProperty() {
        return from;
    }

    public StringProperty toProperty() {
        return to;
    }

    public StringProperty userPhoneProperty() {
        return userPhone;
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public StringProperty driverPhoneProperty() {
        return driverPhone;
    }

    public ObjectProperty<LocalDateTime> createTimeProperty() {
        return createTime;
    }

    public ObjectProperty<OrderStatus> orderStatusProperty() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus.set(orderStatus);
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone.set(driverPhone);
    }

    public String getCreateTimeAsString() {
        return createTimeAsString.get();
    }

    public StringProperty createTimeAsStringProperty() {
        return createTimeAsString;
    }

    public String getPrice() {
        return price.get();
    }

    public StringProperty priceProperty() {
        return price;
    }

    public String getDistance() {
        return distance.get();
    }

    public StringProperty distanceProperty() {
        return distance;
    }

    public void setPrice(Double price) {
        this.price.set(String.format("%3.0f грн.", price));
    }

    public void setFrom(String from) {
        this.from.set(from);
    }

    public void setTo(String to) {
        this.to.set(to);
    }

    public void setUserPhone(String userPhone) {
        this.userPhone.set(userPhone);
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public void setDistance(String distance) {
        this.distance.set(distance);
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime.set(createTime);
    }

    public void setCreateTimeAsString(String createTimeAsString) {
        this.createTimeAsString.set(createTimeAsString);
    }

    @Override
    public String toString() {
        return "Order{" +
                "from=" + from +
                ", to=" + to +
                ", userPhone=" + userPhone +
                ", userName=" + userName +
                ", driverPhone=" + driverPhone +
                ", price=" + price +
                ", distance=" + distance +
                ", createTime=" + createTime +
                ", createTimeAsString=" + createTimeAsString +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
