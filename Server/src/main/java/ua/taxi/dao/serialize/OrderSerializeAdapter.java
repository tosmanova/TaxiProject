package ua.taxi.dao.serialize;

import ua.taxi.model.Order.Order;
import ua.taxi.model.Order.OrderStatus;
import ua.taxi.utils.Utils;

import java.time.LocalDateTime;


/**
 * Created by andrii on 11.06.16.
 */
public class OrderSerializeAdapter {

    private String from;
    private String to;
    private String userPhone;
    private String userName;
    private String driverPhone;
    private String price;
    private String distance;
    private String createTime;
    private String orderStatus;


    public OrderSerializeAdapter(Order order) {

        from = order.getFrom().toString();
        to = order.getTo().toString();
        userPhone = order.getUserPhone();
        userName = order.getUserName();
        driverPhone = order.getDriverPhone();
        price = order.getPrice().toString();
        distance = order.getDistance().toString();
        createTime = order.getCreateTime().toString();
        orderStatus = order.getOrderStatus().toString();

    }

    public Order createOrder() {

        Order order = new Order();

        order.setFrom(Utils.addressValidate(from));
        order.setTo(Utils.addressValidate(to));
        order.setUserPhone(userPhone);
        order.setUserName(userName);
        order.setDriverPhone(driverPhone);
        order.setPrice(Double.parseDouble(price));
        order.setDistance(Double.parseDouble(distance));
        order.setCreateTime(LocalDateTime.parse(createTime));
        order.setOrderStatus(OrderStatus.valueOf(orderStatus));

        return order;
    }

    public OrderSerializeAdapter() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
