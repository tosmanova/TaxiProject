package ua.taxi.model.Order;

import java.io.Serializable;

/**
 * Created by serhii on 23.04.16.
 */
public class Address implements Serializable{

    private String street;
    private String houseNum;

    // google api
    private double lat;
    private double lon;



    public Address(String street, String houseNum) {
        this.street = street;
        this.houseNum = houseNum;
    }

    public Address(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Address() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return street + " " + houseNum;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        return !(houseNum != null ? !houseNum.equals(address.houseNum) : address.houseNum != null);
    }


}
