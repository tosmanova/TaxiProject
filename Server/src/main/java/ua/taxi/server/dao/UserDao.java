package ua.taxi.server.dao;

import org.springframework.transaction.annotation.Transactional;
import ua.taxi.base.exception.NoUserWithPhoneException;
import ua.taxi.base.exception.TaxiAppException;
import ua.taxi.base.exception.UserPresentException;
import ua.taxi.base.model.user.User;

import java.util.List;

public interface UserDao {
    /**
    * @param user element is added to database
    * @return copy of user
    * @throws UserPresentException if user is present in database
    */

    User createUser(User user)  throws TaxiAppException;

    /**
     * @param phone - unique User phone
     * @return User with phone
     * @throws NoUserWithPhoneException if user with this phone don`t present in database
     */

    User getUser(String phone) throws TaxiAppException;

    List<String> setToBlackList(String phone) throws TaxiAppException;

    User delete(String phone) throws TaxiAppException;

    //return OldUser

    User update(User newUser) throws TaxiAppException;

    int driverRegisteredQuantity();

    int passengerRegisteredQuantity();

}
