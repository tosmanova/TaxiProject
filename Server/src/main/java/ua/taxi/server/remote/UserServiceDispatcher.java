package ua.taxi.server.remote;

import ua.taxi.base.model.remote.RemoteUserObject;
import ua.taxi.base.model.remote.UserServiceMethods;
import ua.taxi.server.service.UserServiceImpl;

/**
 * Created by andrii on 07.06.16.
 */
public class UserServiceDispatcher {

    private UserServiceImpl service;

    public UserServiceDispatcher(UserServiceImpl service) {
        this.service = service;
    }

    public Object handler(Object object) {

        RemoteUserObject remote = (RemoteUserObject) object;

        if (remote.getUserServiceMethods() == UserServiceMethods.REGISTER_PASSANGER) {
            return service.register(remote.getPhone(), remote.getPass(), remote.getName(), remote.getHomeAdress());

        } else if (remote.getUserServiceMethods() == UserServiceMethods.REGISTER_DRIVER) {

            return service.register(remote.getPhone(), remote.getPass(), remote.getName(), remote.getCar());

        } else if (remote.getUserServiceMethods() == UserServiceMethods.CHANGE_PASSANGER) {

            return service.changePassanger(remote.getPhone(), remote.getPass(), remote.getName(), remote.getHomeAdress());

        } else if (remote.getUserServiceMethods() == UserServiceMethods.CHANGE_DRIVER) {

            return service.changeDriver(remote.getPhone(), remote.getPass(), remote.getName(), remote.getCar());

        } else if (remote.getUserServiceMethods() == UserServiceMethods.GET_USER) {
            return service.getUser(remote.getPhone());

        } else if (remote.getUserServiceMethods() == UserServiceMethods.DRIVER_REGISTERED_QUANTITY) {

            return service.driverRegisteredQuantity();

        } else if (remote.getUserServiceMethods() == UserServiceMethods.PASSANGER_REGISTERED_QUANTITY) {
            return service.passangerRegisteredQuantity();

        } else if (remote.getUserServiceMethods() == UserServiceMethods.LOGIN) {
            return service.login(remote.getPhone(), remote.getPass());

        } else {
            return "UNKNOWN Method";
        }
    }
}
