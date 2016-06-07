package ua.taxi.remote;


import ua.taxi.model.Remote.OrderServiceMethods;
import ua.taxi.model.Remote.RemoteOrderObject;
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

        RemoteOrderObject remote = (RemoteOrderObject) object;

        if (remote.getOrderMethods() == OrderServiceMethods.ORDERS_REGISTERED_QUANTITY) {
            return service.ordersRegisteredQuantity();

        } else if (remote.getOrderMethods() == OrderServiceMethods.CREATE_ORDER) {
            return service.createOrder(
                    remote.getUserPhone(),
                    remote.getUserName(),
                    remote.getFrom(),
                    remote.getTo());
        } else if (remote.getOrderMethods() == OrderServiceMethods.GET_ORDER) {
            return service.getOrder(remote.getUserPhone());

        } else if (remote.getOrderMethods() == OrderServiceMethods.GET_ORDER_IN_PROGRES_BY_DRIVERPHONE) {
            return service.getOrderInProgresByDriverPhone(
                    remote.getDriverPhone());

        } else if (remote.getOrderMethods() == OrderServiceMethods.GET_ALL_ORDERS) {
            return service.getAllOrders();

        } else if (remote.getOrderMethods() == OrderServiceMethods.GET_NEW_ORDERS) {
            return service.getNewOrders();

        } else if (remote.getOrderMethods() == OrderServiceMethods.GET_STATUS_COUNTERMAP) {
            return service.getStatusCounterMap();

        } else if (remote.getOrderMethods() == OrderServiceMethods.CHANGE_ORDER_4) {
            return service.changeOrder(
                    remote.getUserPhone(),
                    remote.getUserName(),
                    remote.getFrom(),
                    remote.getTo());

        } else if (remote.getOrderMethods() == OrderServiceMethods.CHANGE_ORDER_2) {
            return service.changeOrder(
                    remote.getUserPhone(),
                    remote.getOrder());

        } else if (remote.getOrderMethods() == OrderServiceMethods.CANCEL_ORDER) {
            return service.cancelOrder(
                    remote.getUserPhone());

        } else if (remote.getOrderMethods() == OrderServiceMethods.GET_DISTANCE) {
            return service.getDistance(
                    remote.getFrom(),
                    remote.getTo());

        } else if (remote.getOrderMethods() == OrderServiceMethods.GET_PRICE) {
            return service.getPrice(
                    remote.getDistance());

        } else if (remote.getOrderMethods() == OrderServiceMethods.GET_PRICE2) {
            return service.getPrice(
                    remote.getFrom(),
                    remote.getTo());
        } else {
            return "UNKNOWN Method";
        }

    }
}
