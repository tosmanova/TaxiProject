package ua.taxi.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by andrii on 05.07.16.
 */


public class InitSpringContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        String springLocation = servletContextEvent.getServletContext().getInitParameter("springLocation");
        ApplicationContext context = new ClassPathXmlApplicationContext(springLocation);
        servletContextEvent.getServletContext().setAttribute("spring-context", context);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }


}
