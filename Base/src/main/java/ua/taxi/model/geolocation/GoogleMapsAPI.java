package ua.taxi.model.geolocation;

import ua.taxi.model.geolocation.Location;

public interface GoogleMapsAPI {

    Location findLocation(String unformatted);

    Location findLocation(String country, String city, String street, String houseNum);

    double getDistance(Location pointA, Location pointB);

}