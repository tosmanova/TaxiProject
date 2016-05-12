package ua.taxi.service;

import ua.taxi.dao.*;
import ua.taxi.model.*;
import ua.taxi.service.*;

/**
 * Created with IntelliJ IDEA.
 * User: Роман
 * Date: 24.04.16
 * Time: 0:15
 * To change this template use File | Settings | File Templates.
 */
public class Calculator {
    Order order;
    int priceForOneKm = 6;

    public Calculator(Order order) {
        this.order = order;
    }

    public static int calculateThePrice(Order order) {
        return 1;
    }

}
