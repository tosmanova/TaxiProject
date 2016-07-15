package ua.taxi.server.dao.jpa;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.taxi.base.model.user.Driver;
import ua.taxi.base.model.user.Passenger;
import ua.taxi.server.dao.UserDao;
import ua.taxi.base.model.user.User;

import javax.persistence.*;
import java.util.List;

/**
 * Created by andrii on 7/10/16.
 */

@Component
public class UserDaoJPAImpl implements UserDao {

    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public User createUser(User user) {
        manager.persist(user);
        return user;
    }

    @Override
    public User getUser(String phone) {
        return manager.createNamedQuery("findUserByPhone", User.class)
                .setParameter(1, phone).getSingleResult();
    }


    @Override
    @Transactional
    public User delete(String phone) {

        User user = manager.createNamedQuery("findUserByPhone", User.class)
                .setParameter(1, phone).getSingleResult();
        manager.remove(user);
        manager.flush();
        return user;
    }

    @Override
    @Transactional
    public User update(User newUser) {

        User user = manager.createNamedQuery("findUserByPhone", User.class)
                .setParameter(1, newUser.getPhone()).getSingleResult();

        if(newUser instanceof Driver){
            ((Driver) user).setCar(((Driver) newUser).getCar());
        }else if (newUser instanceof Passenger){
            ((Passenger) user).setHomeAdress(((Passenger) newUser).getHomeAdress());
        }else {
            throw new PersistenceException("Unidentified newUser");
        }
        user.setPass(newUser.getPass());
        user.setName(newUser.getName());
        manager.flush();
        return user;
    }

    @Override
    @Transactional
    public int driverRegisteredQuantity() {
        return ((Long) manager.createQuery("select count(d.id) from Driver d ")
                .getSingleResult()).intValue();
    }

    @Override
    @Transactional
    public int passengerRegisteredQuantity() {
        return ((Long) manager.createQuery("select count(p.id) from Passenger p ")
                .getSingleResult()).intValue();
    }

    @Override
    public List<String> setToBlackList(String phone) {
        return null;
    }

}
