import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.taxi.base.service.OrderService;
import ua.taxi.server.dao.OrderDao;
import ua.taxi.server.dao.UserDao;

import ua.taxi.server.dao.jpa.UserDaoJPAImpl;
import ua.taxi.server.utils.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by andrii on 05.07.16.
 */
public class TestUtils {

    private static ApplicationContext context;

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

    static void init() {
        context = new ClassPathXmlApplicationContext("/test-app-context.xml");
    }

    static UserDao getUserDao() {
        return context.getBean(UserDao.class);
    }

    static OrderDao getOrderDaoSQLImpl() {
        return context.getBean(OrderDao.class);
    }

    static OrderService getOrderService() {
        return context.getBean(OrderService.class);
    }

    static void clearOrders(){

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            statement.executeUpdate("TRUNCATE Orders");
            statement.executeUpdate("TRUNCATE Addresses");
            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
