package ua.taxi.model;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * Created by Andrii on 4/25/2016.
 */
public class Passanger extends User implements Serializable {

    private Address homeAdress;

    public Passanger(String phone, String pass, String name, Address homeAdress) {
        super(phone, pass, name);
        this.homeAdress = homeAdress;
    }

    public Address getHomeAdress() {
        return homeAdress;
    }

    @Override
    public String toString() {
        return "Passanger{" +
                "homeAdress=" + homeAdress +
                super.toString() +
                '}';
    }
}
