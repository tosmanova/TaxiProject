package ua.taxi.base.model.user;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@DiscriminatorValue("driver")
@Table(name = "Drivers")
public class Driver extends User implements Serializable {

    @OneToOne(fetch=FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(name="car_id")
    private Car car;

    public Driver() {
    }

    public Driver(String phone, String pass, String name, Car car) {
        super(phone, pass, name);
        this.car = car;
    }

    public Driver(String phone, String pass, String name) {
        super(phone, pass, name);
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Driver{ " +
                " car= " + car +
                super.toString() +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Driver)) return false;
        if (!super.equals(object)) return false;
        Driver driver = (Driver) object;
        return car.equals(driver.car);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + car.hashCode();
        return result;
    }
}
