package ua.taxi.dao.serialize;

import ua.taxi.model.User.Car;
import ua.taxi.model.User.Driver;
import ua.taxi.model.User.Passanger;
import ua.taxi.model.User.User;
import ua.taxi.utils.Utils;

/**
 * Created by andrii on 17.06.16.
 */
public class UserSerializeAdapter {

    private String phone;
    private String pass;
    private String name;
    private String car;
    private String homeAdress;

    public UserSerializeAdapter(User user) {
        phone = user.getPhone();
        pass = user.getPass();
        name = user.getName();
        if (user instanceof Passanger) {
            Passanger passanger = (Passanger) user;
            homeAdress = passanger.getHomeAdress().toString();
            car = "null";
        } else {
            Driver driver = (Driver) user;
            car = driver.getCar().toString();
            homeAdress = "null";
        }
    }

    public UserSerializeAdapter() {

    }

    User createUser() {
        if (car.equals("null")) {
            return new Passanger(phone, pass, name, Utils.addressValidate(homeAdress));
        } else {
            String carA[] = car.split(" ");
            return new Driver(phone, pass, name, new Car(carA[0], carA[1], carA[2]));
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {

        this.car = car;
    }

    public String getHomeAdress() {
        return homeAdress;
    }

    public void setHomeAdress(String homeAdress) {
        this.homeAdress = homeAdress;
    }
}
