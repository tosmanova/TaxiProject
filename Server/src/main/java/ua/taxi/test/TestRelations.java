package ua.taxi.test;

import ua.taxi.test.model.Course;
import ua.taxi.test.model.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by andrii on 09.07.16.
 */
public class TestRelations {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("myunit");
        EntityManager manager = entityManagerFactory.createEntityManager();



        Course course1 = new Course("1", "desc");
        Course course2 = new Course("2", "desc");
        Course course3 = new Course("3", "desc");
        Course course4 = new Course("4", "desc");

        Teacher teacher1 = new Teacher( 5, "Ivan");
        Teacher teacher2 = new Teacher( 5, "Serhii");
        Teacher teacher3 = new Teacher( 5, "Valera");

        course1.setOwner(teacher1);
        course2.setOwner(teacher1);

        teacher1.getCourseList().add(course1);
        teacher1.getCourseList().add(course2);

        course3.setOwner(teacher2);
        teacher2.getCourseList().add(course3);

        course4.setOwner(teacher3);
        teacher3.getCourseList().add(course4);

        manager.getTransaction().begin();


        manager.persist(course1);
        manager.persist(course2);
        manager.persist(course3);
        manager.persist(course4);

        manager.persist(teacher1);
        manager.persist(teacher2);
        manager.persist(teacher3);

        manager.getTransaction().commit();

    }


}
