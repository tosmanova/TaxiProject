import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.taxi.server.dao.UserDao;

import ua.taxi.server.dao.jpa.UserDaoJPAImpl;

import java.io.IOException;

/**
 * Created by andrii on 05.07.16.
 */
public class TestUtils {


     static void scriptRun(String createScript) {

        ProcessBuilder pb = new ProcessBuilder(createScript);
        try {
            Process process = pb.start();
            process.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

     static UserDao getUserDao() {
         ApplicationContext context = new ClassPathXmlApplicationContext("/test-app-context.xml");
         return context.getBean(UserDaoJPAImpl.class);
    }

    /* static OrderDao getOrderDaoSQLImpl() {
         return context.getBean(OrderDaoJPAImpl.class);
    }*/
}
