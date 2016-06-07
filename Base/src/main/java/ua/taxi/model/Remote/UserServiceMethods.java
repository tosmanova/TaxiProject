package ua.taxi.model.Remote;

import ua.taxi.model.Order.Address;
import ua.taxi.model.User.Car;
import ua.taxi.model.User.UserValidateMessage;

import java.io.Serializable;

/**
 * Created by andrii on 07.06.16.
 */
public enum UserServiceMethods implements Serializable{

    REGISTER_PASSANGER,
    REGISTER_DRIVER,
    CHANGE_PASSANGER,
    CHANGE_DRIVER,
    GET_USER,
    DRIVER_REGISTERED_QUANTITY,
    PASSANGER_REGISTERED_QUANTITY,
    LOGIN

}
