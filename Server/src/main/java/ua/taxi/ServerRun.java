package ua.taxi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by andrii on 05.07.16.
 */
public class ServerRun {

    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("app-context.xml");
        context.getBean("db");
    }

}
