import org.junit.*;
import org.junit.runners.MethodSorters;
import ua.taxi.constants.DaoConstants;
import ua.taxi.dao.sql.OrderDaoSQLImpl;
import ua.taxi.model.Order.Address;
import ua.taxi.model.Order.Order;
import ua.taxi.utils.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by andrii on 01.07.16.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SQL_OrderTest extends Assert {


    private OrderDaoSQLImpl orderDao;

    @BeforeClass
    public static void initTestSQL() {

        ProcessBuilder pb = new ProcessBuilder(DaoConstants.SQL_CREATE_TEST_SCRIPT);
        try {
            Process process = pb.start();
            process.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Before
    public void initUserDao() {
        orderDao = new OrderDaoSQLImpl();
    }


    @Test
    public void _01_createOrder() throws SQLException {

        Order order1 = new Order(new Address("Entuziastov", "29a"), new Address("Bulvar Perova", "1"), "(093)306-01-13", "Vasia", 123.21, 12312.1);
        Order order2 = new Order(new Address("tuziastov", "23a"), new Address("ulvar Perova", "4"), "(053)306-01-13", "Fasia", 23.21, 1212.1);
        List<Order> list1 = new ArrayList<>(orderDao.createOrder(order1));
        Order rtOrder1 = list1.get(0);
        List<Order> list2 = new ArrayList<>(orderDao.createOrder(order2));
        Order rtOrder2 = list2.get(0);
        assertEquals(rtOrder1, order1);
        assertEquals(rtOrder2, order2);

    }

    @After
    public void clearBase() {

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            statement.executeUpdate("TRUNCATE Orders");
            statement.executeUpdate("TRUNCATE Statuses");
            statement.executeUpdate("TRUNCATE Addresses");
            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void removeTestSQL() {

        ProcessBuilder pb = new ProcessBuilder(DaoConstants.SQL_REMOVE_TEST_SCRIPT);
        try {
            Process process = pb.start();
            process.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
