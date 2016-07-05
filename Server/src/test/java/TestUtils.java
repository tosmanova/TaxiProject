import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.taxi.dao.sql.*;

import java.io.IOException;

/**
 * Created by andrii on 05.07.16.
 */
public class TestUtils {


    private  static  ApplicationContext context;
    static {
         context = new ClassPathXmlApplicationContext("app-context.xml");
    }

    public static void sriptRun(String sqlCreateTestScript) {

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

    public static UserDaoSqlImpl getUserDao(){
        return (UserDaoSqlImpl) context.getBean("userDaoSqlImpl");
    }
    public static DriverDao getDriverDao(){
        return (DriverDao) context.getBean("driverDao");
    }
    public static PassengerDao getPassengerDao(){
        return (PassengerDao) context.getBean("passengerDao");
    }
    public static CarDao getCarDao(){
        return (CarDao) context.getBean("carDao");
    }
    public static AddressDao getAddressDao(){
        return (AddressDao) context.getBean("addressDao");
    }
    public static OrderStatusDao getOrderStatusDao(){
        return (OrderStatusDao) context.getBean("orderStatusDao");
    }
    public static OrderDaoSQLImpl getOrderDaoSQLImpl(){
        return (OrderDaoSQLImpl) context.getBean("orderDaoSQLImpl");
    }

}
