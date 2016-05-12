package ua.taxi.model;

/**
 * Created with IntelliJ IDEA.
 * User: Роман
 * Date: 23.04.16
 * Time: 17:02
 * <p>
 * To change this template use File | Settings | File Templates.
 */
public class Driver extends User {

    private Car car;

    public Driver(String phone, String pass, String name, Car car) {
        super(phone, pass, name);
        this.car = car;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "car=" + car +
                super.toString() +
                '}';
    }
}
