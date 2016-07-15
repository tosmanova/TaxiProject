package ua.taxi.server.dao;

import org.springframework.transaction.annotation.Transactional;
import ua.taxi.base.exception.NoUserWithPhoneException;
import ua.taxi.base.exception.TaxiAppException;
import ua.taxi.base.exception.UserPresentException;
import ua.taxi.base.model.user.User;

import javax.persistence.PersistenceException;
import java.util.List;

public interface UserDao {
    /**
     * @param user element is added to database
     * @return copy of user
     * @throws PersistenceException if user is present in database
     */

    User createUser(User user);

    /**
     * @param phone - unique User phone
     * @return User with phone
     * @throws PersistenceException if user with this phone don`t present in database
     */

    User getUser(String phone);

    List<String> setToBlackList(String phone);

    User delete(String phone);

    User update(User newUser);

    int driverRegisteredQuantity();

    int passengerRegisteredQuantity();

}
