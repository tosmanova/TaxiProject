package ua.taxi.model.Order;

import javafx.beans.property.SimpleStringProperty;
import ua.taxi.utils.DateUtils;
import ua.taxi.utils.Utils;

/**
 * Created by andrii on 08.06.16.
 */
public class TableViewOrder {

    private Order order;
    private SimpleStringProperty from;
    private SimpleStringProperty to;
    private SimpleStringProperty price;
    private SimpleStringProperty distance;
    private SimpleStringProperty createTime;

    public TableViewOrder(Order order) {
        this.order = order;
        from = new SimpleStringProperty(order.getFrom().toString());
        to = new SimpleStringProperty(order.getTo().toString());
        price = new SimpleStringProperty(Utils.priceFormat(order.getPrice()));
        distance = new SimpleStringProperty(Utils.distanceFormat(order.getDistance()));
        createTime = new SimpleStringProperty(DateUtils.HHmm(order.getCreateTime()));
    }

    public String getFrom() {
        return from.get();
    }

    public SimpleStringProperty fromProperty() {
        return from;
    }

    public void setFrom(String from) {
        this.from.set(from);
    }

    public String getTo() {
        return to.get();
    }

    public SimpleStringProperty toProperty() {
        return to;
    }

    public void setTo(String to) {
        this.to.set(to);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getDistance() {
        return distance.get();
    }

    public SimpleStringProperty distanceProperty() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance.set(distance);
    }

    public String getCreateTime() {
        return createTime.get();
    }

    public SimpleStringProperty createTimeProperty() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime.set(createTime);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
