package ua.taxi.remote;

import ua.taxi.model.Address;
import ua.taxi.model.Car;
import ua.taxi.model.UserValidateMessage;
import ua.taxi.service.UserService;

import ua.taxi.to.Server;

/**
 * Created by andrii on 06.06.16.
 */
public class RemoteUserService implements UserService{


    Server server;

    public RemoteUserService(Server server) {
        this.server = server;
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
