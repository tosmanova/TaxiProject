package ua.taxi.server.dao.jpa;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.taxi.server.dao.UserDao;
import ua.taxi.base.exception.NoUserWithPhoneException;
import ua.taxi.base.exception.TaxiAppException;
import ua.taxi.base.model.order.Address;
import ua.taxi.base.model.user.Passenger;
import ua.taxi.base.model.user.User;

import javax.persistence.*;
import java.util.List;

/**
 * Created by andrii on 7/10/16.
 */

@Component
public class UserDaoJPAImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoJPAImpl.class);

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @Override
    public User createUser(User user) {

        //EntityManager manager = entityManagerFactory.createEntityManager();
        //manager.getTransaction().begin();
        manager.persist(user);
        //manager.getTransaction().commit();
        return user;

    }

    public static void main(String[] args) throws TaxiAppException {
        ApplicationContext context = new ClassPathXmlApplicationContext("/app-context.xml");
        TempClass tempClass = context.getBean(TempClass.class);
        System.out.println(tempClass.tset1());

        UserDao userDao = context.getBean(UserDaoJPAImpl.class);
       /* Passenger pass = new Passenger("(093)306-01-13", "0933060113", "Andrii", new Address("Entuziastiv", "27"));
        User user = userDao.createUser(pass);*/

    }

    @Override

    public User getUser(String phone) {
        //  EntityManager manager = entityManagerFactory.createEntityManager();
        User user = manager.createQuery(
                "SELECT u from User u where u.phone = :tPhone",
                User.class
        ).setParameter("tPhone", phone).getSingleResult();

        manager.getTransaction().commit();
        return user;
    }

    @Override
    public List<String> setToBlackList(String phone) {
        return null;
    }

    @Override

    public User delete(String phone) throws NoUserWithPhoneException {
        // EntityManager manager = entityManagerFactory.createEntityManager();
        User user = manager.createQuery(
                "SELECT u from User u where u.phone = :tPhone",
                User.class
        ).setParameter("tPhone", phone).getSingleResult();
        manager.remove(user);

        return user;
    }

    @Override
    public User update(User newUser) {
        return null;
    }

    @Override
    public int driverRegisteredQuantity() {
        return 0;
    }

    @Override
    public int passengerRegisteredQuantity() {
        return 0;
    }

  /*  public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }*/
}
