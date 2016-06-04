package ua.taxi.service;

import ua.taxi.model.*;

/**
 * Created by serhii on 23.04.16.
 */
public interface UserService {

    UserValidateMessage register(String phone, String pass, String name, Address homeAdress);
    UserValidateMessage register(String phone, String pass, String name, Car car);

    UserValidateMessage changePassanger(String phone, String pass, String name, Address homeAdress);
    UserValidateMessage changeDriver(String phone, String pass, String name, Car car);

    UserValidateMessage getUser(String phone);

    int driverRegisteredQuantity ();
    int passangerRegisteredQuantity ();

    UserValidateMessage login(String phone, String pass);

}
