package ua.taxi.model.User;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Роман
 * Date: 23.04.16
 * Time: 17:03
 * To change this test use File | Settings | File Templates.
 */

@XmlRootElement
public class Car implements Serializable {

    String number;
    String model;
    String color;

    public Car(){}

    public Car(String number, String model, String color) {
        this.number = number;
        this.model = model;
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    @XmlAttribute
    public void setNumber(String number) {
        this.number = number;
    }
    @XmlElement
    public void setModel(String model) {
        this.model = model;
    }
    @XmlElement
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return  number + " " + model + " " + color;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Car car = (Car) object;

        if (!number.equals(car.number)) return false;
        if (!model.equals(car.model)) return false;
        return color.equals(car.color);

    }
}
