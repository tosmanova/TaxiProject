package ua.taxi.model.remote;

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
