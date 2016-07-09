package ua.taxi.test;

import ua.taxi.test.model.Course;
import ua.taxi.test.model.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by andrii on 09.07.16.
 */
public class TestFetchType {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("myunit");
        EntityManager manager = entityManagerFactory.createEntityManager();

        Teacher teacher1 = new Teacher(5, "Bloch");
        for (int i = 0; i < 1000; i++) {
            teacher1.getCourseList().add(new Course(String.valueOf(i), "desc", teacher1));
        }

        manager.getTransaction().begin();
        manager.persist(teacher1);
        manager.getTransaction().commit();

        manager.clear();

        Teacher teacher = manager.createQuery("SELECT t FROM Teacher t WHERE t.name = :tName", Teacher.class)
                .setParameter("tName", "Bloch").getSingleResult();

        int level = teacher.getLevel();

        Course course = teacher.getCourseList().get(0);

    }

}
