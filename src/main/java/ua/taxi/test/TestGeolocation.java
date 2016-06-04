package ua.taxi.test;

import ua.taxi.geolocation.GoogleMapsAPI;
import ua.taxi.geolocation.GoogleMapsAPIImpl;
import ua.taxi.geolocation.Location;

/**
 * Created by Andrii on 5/11/2016.
 */
public class TestGeolocation {

    public static void main(String[] args) {

        GoogleMapsAPI googleMapsAPI = new GoogleMapsAPIImpl();
        Location location1 = googleMapsAPI.findLocation("Україна", "Київ", "Khreschatyk", "3");
        Location location2 = googleMapsAPI.findLocation("Україна", "Київ", "Entuziastiv", "27");

        Location location3 = googleMapsAPI.findLocation("Khreschatyk 3");
        Location location4 = googleMapsAPI.findLocation("Entuziastiv 27");

        System.out.println(location1.toString());
        System.out.println(location2.toString());
        System.out.println();
        System.out.println(googleMapsAPI.getDistance(location1, location2));

        System.out.println();

        System.out.println(location3.toString());
        System.out.println(location4.toString());
        System.out.println();
        System.out.println(googleMapsAPI.getDistance(location3, location4));


    }

}
