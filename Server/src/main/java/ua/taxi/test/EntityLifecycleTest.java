package ua.taxi.test;

import ua.taxi.test.model.Student;
import ua.taxi.test.model.StudentType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

/**
 * Created by andrii on 09.07.16.
 */
public class EntityLifecycleTest {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("myunit");
        EntityManager manager = entityManagerFactory.createEntityManager();

        Student student = new Student("Gavril", 3,
                StudentType.MIDDLE, new Date(), "");

        EntityTransaction transaction = manager.getTransaction();

        try {
            manager.getTransaction().begin();
            Student st1 = manager.find(Student.class, 9);
            manager.remove(st1);
            manager.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            manager.close();
            entityManagerFactory.close();
        }


    }

}
