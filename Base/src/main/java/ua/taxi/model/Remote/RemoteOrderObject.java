package ua.taxi.model.Remote;

import ua.taxi.model.Order.Order;

import java.io.Serializable;

/**
 * Created by andrii on 06.06.16.
 */
public class RemoteOrderObject extends Order implements Serializable {

    private OrderServiceMethods orderMethods;
    private Order order;

    public RemoteOrderObject() {
    }

    public RemoteOrderObject(OrderServiceMethods orderMethods) {
        this.orderMethods = orderMethods;
    }

    public OrderServiceMethods getOrderMethods() {
        return orderMethods;
    }

    public void setOrderMethods(OrderServiceMethods orderMethods) {
        this.orderMethods = orderMethods;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
