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
public class EntityManagerTest {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("myunit");
        EntityManager manager = entityManagerFactory.createEntityManager();

        Student student = new Student("Gavril", 3,
                StudentType.MIDDLE, new Date(), "");

        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            manager.persist(student);
            transaction.commit();

            Student st1 = manager.find(Student.class, 7);
            System.out.println("Find student = " + st1);



        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            manager.close();
            entityManagerFactory.close();
        }



    }

}
