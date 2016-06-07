package ua.taxi.remote;

import ua.taxi.model.Address;
import ua.taxi.model.Car;
import ua.taxi.model.UserValidateMessage;
import ua.taxi.service.UserService;
import ua.taxi.to.Client;

/**
 * Created by andrii on 06.06.16.
 */
public class RemoteUserService implements UserService{


    Client client;

    public RemoteUserService(Client client) {
        this.client = client;
    }

    @Override
    public UserValidateMessage register(String phone, String pass, String name, Address homeAdress) {
        return null;
    }

    @Override
    public UserValidateMessage register(String phone, String pass, String name, Car car) {
        return null;
    }

    @Override
    public UserValidateMessage changePassanger(String phone, String pass, String name, Address homeAdress) {
        return null;
    }

    @Override
    public UserValidateMessage changeDriver(String phone, String pass, String name, Car car) {
        return null;
    }

    @Override
    public UserValidateMessage getUser(String phone) {
        return null;
    }

    @Override
    public int driverRegisteredQuantity() {
        return 0;
    }

    @Override
    public int passangerRegisteredQuantity() {
        return 0;
    }

    @Override
    public UserValidateMessage login(String phone, String pass) {
        return null;
    }
}
