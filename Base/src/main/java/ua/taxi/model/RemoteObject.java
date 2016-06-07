package ua.taxi.model;

import java.io.Serializable;

/**
 * Created by andrii on 06.06.16.
 */
public class RemoteObject implements Serializable {

    private OrderMethods orderMethods;
    private int count;
    private String userPhone;
    private String userName;
    private Address from;
    private Address to;

    public RemoteObject() {
    }

    public RemoteObject(OrderMethods orderMethods) {
        this.orderMethods = orderMethods;

    }

    public OrderMethods getOrderMethods() {
        return orderMethods;
    }

    public void setOrderMethods(OrderMethods orderMethods) {
        this.orderMethods = orderMethods;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
}
