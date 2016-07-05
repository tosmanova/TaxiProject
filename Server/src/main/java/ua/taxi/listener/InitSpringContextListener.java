package ua.taxi.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by andrii on 05.07.16.
 */

@WebListener
public class InitSpringContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("app-context.xml");
        servletContextEvent.getServletContext().setAttribute("spring-context", context);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }


}
