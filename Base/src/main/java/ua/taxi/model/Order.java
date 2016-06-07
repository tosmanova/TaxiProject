package ua.taxi.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by serhii on 23.04.16.
 */
public class Order implements Serializable {

    // private long id;

    private Address from;
    private Address to;
    private String userPhone;
    private String userName;
    private String driverPhone;
    private Double price;
    private Double distance;
    private LocalDateTime createTime;
    private OrderStatus orderStatus;

    public Order() {
    }

    public Order(Address from, Address to, String userPhone, String userName, Double price, Double distance) {

        this.from = from;
        this.to = to;
        this.userPhone = userPhone;
        this.userName = userName;
        this.price = price;
        this.distance = distance;

        createTime = LocalDateTime.now();
        orderStatus = OrderStatus.NEW;

    }


    public Address getFrom() {
        return from;
    }

    public void setFrom(Address from) {
        this.from = from;
    }

    public Address getTo() {
        return to;
    }

    public void setTo(Address to) {
        this.to = to;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "from=" + from +
                ", to=" + to +
                ", userPhone='" + userPhone + '\'' +
                ", userName='" + userName + '\'' +
                ", driverPhone='" + driverPhone + '\'' +
                ", price=" + price +
                ", distance=" + distance +
                ", createTime=" + createTime +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
