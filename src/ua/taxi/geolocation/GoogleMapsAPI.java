package ua.taxi.geolocation;

public interface GoogleMapsAPI {

    Location findLocation(String unformatted);

    Location findLocation(String country, String city, String street, String houseNum);

    double getDistance(Location pointA, Location pointB);

}