import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.taxi.dao.jdbcsql.*;

import java.io.IOException;

/**
 * Created by andrii on 05.07.16.
 */
public class TestUtils {


    private static ApplicationContext context;

    static {
        context = new ClassPathXmlApplicationContext("app-context.xml");
    }

     static void sriptRun(String sqlCreateTestScript) {

        ProcessBuilder pb = new ProcessBuilder(sqlCreateTestScript);
        try {
            Process process = pb.start();
            process.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

     static UserDaoSqlImpl getUserDao() {
        return (UserDaoSqlImpl) context.getBean("userDaoSqlImpl");
    }

     static DriverDao getDriverDao() {
        return (DriverDao) context.getBean("driverDao");
    }

     static PassengerDao getPassengerDao() {
        return (PassengerDao) context.getBean("passengerDao");
    }

     static CarDao getCarDao() {
        return (CarDao) context.getBean("carDao");
    }

     static AddressDao getAddressDao() {
        return (AddressDao) context.getBean("addressDao");
    }

     static OrderStatusDao getOrderStatusDao() {
        return (OrderStatusDao) context.getBean("orderStatusDao");
    }

     static OrderDaoSQLImpl getOrderDaoSQLImpl() {
        return (OrderDaoSQLImpl) context.getBean("orderDaoSQLImpl");
    }

}
