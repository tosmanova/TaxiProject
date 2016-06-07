package ua.taxi.remote;


import ua.taxi.model.OrderMethods;
import ua.taxi.model.RemoteObject;
import ua.taxi.service.OrderServiceImpl;

/**
 * Created by andrii on 07.06.16.
 */
public class RemoteOrder {

    private OrderServiceImpl service;

    public RemoteOrder(OrderServiceImpl service) {
        this.service = service;
    }

    public Object handler(Object object) {

        RemoteObject remote = (RemoteObject) object;

        if (remote.getOrderMethods() == OrderMethods.ORDERS_REGISTERED_QUANTITY) {
            return service.ordersRegisteredQuantity();

        } else if (remote.getOrderMethods() == OrderMethods.CREATE_ORDER) {
            return service.createOrder(
                    remote.getUserPhone(),
                    remote.getUserName(),
                    remote.getFrom(),
                    remote.getTo());
        }

        return "UNKNOWN Method";
    }

}
