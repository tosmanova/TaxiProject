package ua.taxi.model.order;

import java.io.Serializable;

/**
 * Created by Andrii on 5/4/2016.
 */
public class OrderValidateMessage implements Serializable {

    private Order order;
    private String title;
    private String body;
    private boolean state;

    public OrderValidateMessage(Order order, String title, String body, boolean state) {
        this.order = order;
        this.title = title;
        this.body = body;
        this.state = state;
    }

    public Order getOrder() {
        return order;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public boolean isState() {
        return state;
    }

    @Override
    public String toString() {
        return "OrderValidateMessage{" +
                "order=" + order +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", state=" + state +
                '}';
    }
}
