package ua.taxi.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: Роман
 * Date: 23.04.16
 * Time: 17:03
 * To change this template use File | Settings | File Templates.
 */

@XmlRootElement
public class Car {

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
        return  color + " " + model + " " + number;
    }
}
