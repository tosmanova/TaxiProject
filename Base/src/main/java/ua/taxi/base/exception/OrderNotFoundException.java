package ua.taxi.base.exception;

/**
 * Created by serhii on 23.04.16.
 */
public class OrderNotFoundException extends TaxiAppException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
