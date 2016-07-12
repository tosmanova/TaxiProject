package ua.taxi.base.exception;

/**
 * Created by Andrii on 4/25/2016.
 */
public class UserIdIsSettedException extends TaxiAppException {
    public UserIdIsSettedException(String message) {
        super(message);
    }
}
