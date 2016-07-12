package ua.taxi.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.taxi.server.remote.OrderServiceDispatcher;
import ua.taxi.server.remote.Server;
import ua.taxi.server.remote.UserServiceDispatcher;
import ua.taxi.server.service.OrderServiceImpl;
import ua.taxi.server.service.UserServiceImpl;

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
