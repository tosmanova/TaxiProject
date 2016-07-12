package ua.taxi.base.exception;

/**
 * Created by andrii on 11.07.16.
 */
public class NoUserWithPhoneException extends TaxiAppException {
    public NoUserWithPhoneException(String message) {
        super(message);
    }
}
