package ua.taxi.base.model.order;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by serhii on 23.04.16.
 */

@Entity
@Table(name = "Orders")
@NamedQuery(name="findOrderByPhone", query = "SELECT u from Order u where u.userPhone = ?1")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "address_to_id")
    private Address from;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "address_from_id")
    private Address to;

    @Column(nullable = false, length = 14, unique = true)
    private String userPhone;

    @Column(nullable = false, length = 30)
    private String userName;

    @Column(length = 14)
    private String driverPhone;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Double distance;

    @Column
    private LocalDateTime createTime;


    @Enumerated(EnumType.STRING)
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
        return "order{" +
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return id == order.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
