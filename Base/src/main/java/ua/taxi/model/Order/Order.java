package ua.taxi.model.Order;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

   public Order() {}

    public Order(Address from, Address to, String userPhone, String userName, Double price, Double distance) {

        this.from = from;
        this.to = to;
        this.userPhone = userPhone;
        this.userName = userName;
        this.price = price;
        this.distance = distance;

        driverPhone = "";
        createTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
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


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Order)) return false;

        Order order = (Order) object;

        if (from != null ? !from.equals(order.from) : order.from != null) return false;
        if (to != null ? !to.equals(order.to) : order.to != null) return false;
        if (userPhone != null ? !userPhone.equals(order.userPhone) : order.userPhone != null) return false;
        if (userName != null ? !userName.equals(order.userName) : order.userName != null) return false;
        if (driverPhone != null ? !driverPhone.equals(order.driverPhone) : order.driverPhone != null) return false;
        if (price != null ? !price.equals(order.price) : order.price != null) return false;
        if (distance != null ? !distance.equals(order.distance) : order.distance != null) return false;
        if (createTime != null ? !createTime.equals(order.createTime) : order.createTime != null) return false;
        return orderStatus == order.orderStatus;

    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (userPhone != null ? userPhone.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (driverPhone != null ? driverPhone.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        return result;
    }
}
