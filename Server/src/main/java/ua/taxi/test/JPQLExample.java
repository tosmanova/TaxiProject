package ua.taxi.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by andrii on 09.07.16.
 */
public class JPQLExample {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("myunit");
        EntityManager manager = entityManagerFactory.createEntityManager();

        // jpql, hql -> sql + OOP
        Query query = manager.createQuery(
                "SELECT u FROM Student u WHERE u.name = :inName"
        );

        List list = query.setParameter("inName", "Gavril").
                setFirstResult(1).
                setMaxResults(2).
                getResultList();

        list.stream().forEach(System.out::println);


    }




}
