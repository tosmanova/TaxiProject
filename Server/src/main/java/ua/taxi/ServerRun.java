package ua.taxi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.taxi.remote.OrderServiceDispatcher;
import ua.taxi.remote.Server;
import ua.taxi.remote.UserServiceDispatcher;
import ua.taxi.service.OrderServiceImpl;
import ua.taxi.service.UserServiceImpl;

/**
 * Created by andrii on 05.07.16.
 */
public class ServerRun {

    public static void main(String[] args) throws ClassNotFoundException {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("app-context.xml");
        new Server(
                new OrderServiceDispatcher((OrderServiceImpl) context.getBean("orderServiceImpl")),
                new UserServiceDispatcher((UserServiceImpl) context.getBean("userServiceImpl")));
    }
}
