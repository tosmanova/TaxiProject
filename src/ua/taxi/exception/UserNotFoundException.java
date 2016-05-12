package ua.taxi.exception;

public class UserNotFoundException extends TaxiAppException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
