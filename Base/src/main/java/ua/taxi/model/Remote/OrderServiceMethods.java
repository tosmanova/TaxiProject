package ua.taxi.model.Remote;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * Created by andrii on 07.06.16.
 */
public enum OrderServiceMethods implements Serializable{

    ORDERS_REGISTERED_QUANTITY,
    CREATE_ORDER,
    CHANGE_ORDER_STATUS,
    GET_ORDER,
    GET_ORDER_IN_PROGRES_BY_DRIVERPHONE,
    GET_ALL_ORDERS,
    GET_NEW_ORDERS,
    GET_STATUS_COUNTERMAP,
    CHANGE_ORDER_4,
    CHANGE_ORDER_2,
    CANCEL_ORDER,
    GET_DISTANCE,
    GET_PRICE,
    GET_PRICE2,

}
