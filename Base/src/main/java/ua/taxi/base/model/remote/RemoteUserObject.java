package ua.taxi.base.model.remote;

import ua.taxi.base.model.order.Address;
import ua.taxi.base.model.user.Car;
import ua.taxi.base.model.user.User;

import java.io.Serializable;

/**
 * Created by andrii on 07.06.16.
 */
public class RemoteUserObject extends User implements Serializable {

    private Car car;
    private Address homeAdress;
    private User user;
    private UserServiceMethods userServiceMethods;

    public RemoteUserObject(UserServiceMethods userServiceMethods) {
        this.userServiceMethods = userServiceMethods;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Address getHomeAdress() {
        return homeAdress;
    }

    public void setHomeAdress(Address homeAdress) {
        this.homeAdress = homeAdress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserServiceMethods getUserServiceMethods() {
        return userServiceMethods;
    }

    public void setUserServiceMethods(UserServiceMethods userServiceMethods) {
        this.userServiceMethods = userServiceMethods;
    }
}
