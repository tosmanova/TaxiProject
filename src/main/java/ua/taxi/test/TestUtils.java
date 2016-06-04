package ua.taxi.test;

import ua.taxi.model.Address;
import ua.taxi.utils.Utils;

/**
 * Created by Andrii on 4/30/2016.
 */
public class TestUtils {

    public static void main(String[] args) {
        //System.out.println(phoneValidateTest("+380933060113", "(093)306-01-13"));
        System.out.println(addressValidateTest("Kiev 45", new Address("Kiev","45")));
        System.out.println(addressValidateTest("Kiev 45a", new Address("Kiev","45a")));
        System.out.println(addressValidateTest("Kiev 45/2", new Address("Kiev","45/2")));
        System.out.println(addressValidateTest("str 40ages Kiev 45", new Address("str 40ages Kiev","45")));
        System.out.println(addressValidateTest("str 40ages Kiev45", new Address("str 40ages Kiev","45")));

    }

    public static String phoneValidateTest(String phone, String validPhone) {
        if (Utils.phoneValidate(phone) == null) {
            return "phone is Invalid";
        } else if (Utils.phoneValidate(phone).equals(validPhone)) {
            return "Phone Validation is Ok";
        } else return "Phone Valdation false :" + Utils.phoneValidate(phone);
    }

    public static String addressValidateTest(String stringAdress, Address adress){


        if(Utils.addressValidate(stringAdress) == null){
            return "adress is invalid";
        }else if (Utils.addressValidate(stringAdress).equals(adress)){
            return "adress Validation is Ok";
        }else return "address Valdation false : " + Utils.addressValidate(stringAdress).toString();
    }


}
