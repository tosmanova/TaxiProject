package ua.taxi.utils;

import ua.taxi.model.Address;

/**
 * Created by Andrii on 4/30/2016.
 */
public class Utils {

    static public String phoneValidate(String phone) {
        String onlyNumbersPhone = phone.replaceAll("[\\D]+", "");
        if (onlyNumbersPhone.startsWith("38")) {
            onlyNumbersPhone = onlyNumbersPhone.substring(2);
        } else if (onlyNumbersPhone.startsWith("8")) {
            onlyNumbersPhone = onlyNumbersPhone.substring(1);
        }
        if (onlyNumbersPhone.length() != 10 || !onlyNumbersPhone.startsWith("0")) {
            return null;
        }
        return "(" + onlyNumbersPhone.substring(0, 3) +
                ")" + onlyNumbersPhone.substring(3, 6) +
                "-" + onlyNumbersPhone.substring(6, 8) +
                "-" + onlyNumbersPhone.substring(8, 10);
    }

    static public Address addressValidate(String address) {

        String[] arr = address.split("\\s(?=([\\d]{1,2}(\\b|\\w\\b)))");
        if (arr.length != 2) {
            return null;
        } else {
            return new Address(arr[0], arr[1]);
        }
    }

}
