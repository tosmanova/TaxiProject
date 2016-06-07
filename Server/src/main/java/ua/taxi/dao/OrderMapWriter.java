package ua.taxi.dao;

import ua.taxi.model.Order.Order;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * Created by Andrii on 5/12/2016.
 */

@XmlRootElement(name = "orders")
public class OrderMapWriter {

    private Map<String,Order> orders;

    @XmlElement(name = "order")
    public Map<String, Order> getOrders() {
        return orders;
    }

    public void setOrders(Map<String, Order> orders) {
        this.orders = orders;
    }
}
