package ua.taxi.dao.jpa;

import ua.taxi.dao.UserDao;
import ua.taxi.model.user.Car;
import ua.taxi.model.user.Driver;
import ua.taxi.model.user.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Created by andrii on 7/10/16.
 */
public class UserDaoJPAImpl implements UserDao{


    @Override
    public Collection<User> createUser(User user) throws SQLException {

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("taxiProjectJPA");
        EntityManager manager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = manager.getTransaction();

        try {

            transaction.begin();
            manager.persist(user);


            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            manager.close();
            entityManagerFactory.close();
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
        User driver = new Driver("(063)306-01-13", "0633060113", "Vasia", new Car("AA2222", "Vaz", "Baklazhan"));
        UserDaoJPAImpl userDaoJPA = new UserDaoJPAImpl();
        
        userDaoJPA.createUser(driver);

    }

    @Override
    public User getUser(String phone) throws SQLException {
        return null;
    }

    @Override
    public List<String> setToBlackList(String phone) {
        return null;
    }

    @Override
    public User delete(String phone) throws SQLException {
        return null;
    }

    @Override
    public User update(User newUser) throws SQLException {
        return null;
    }

    @Override
    public int driverRegisteredQuantity() throws SQLException {
        return 0;
    }

    @Override
    public int passangerRegisteredQuantity() throws SQLException {
        return 0;
    }
}
