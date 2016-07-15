package ua.taxi.base.model.user;

import ua.taxi.base.model.order.Address;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Andrii on 4/25/2016.
 */

@Entity
@DiscriminatorValue("passenger")
@Table(name = "Passengers")
public class Passenger extends User implements Serializable {

    @OneToOne(fetch=FetchType.EAGER,  cascade = {CascadeType.PERSIST})
    @JoinColumn(name="address_id")
    private Address homeAdress;

    public Passenger() {

    }

    public Passenger(String phone, String pass, String name, Address homeAdress) {
        super(phone, pass, name);
        this.homeAdress = homeAdress;
    }

    public Address getHomeAdress() {

        return homeAdress;
    }

    public void setHomeAdress(Address homeAdress) {
        this.homeAdress = homeAdress;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "homeAdress=" + homeAdress +
                super.toString() +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Passenger)) return false;
        if (!super.equals(object)) return false;

        Passenger passenger = (Passenger) object;
        return homeAdress.equals(passenger.homeAdress);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + homeAdress.hashCode();
        return result;
    }
}
