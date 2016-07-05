import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.runners.MethodSorters;
import ua.taxi.constants.DaoConstants;
import ua.taxi.dao.sql.OrderDaoSQLImpl;
import ua.taxi.model.Order.Address;
import ua.taxi.model.Order.Order;
import ua.taxi.service.OrderServiceImpl;
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
public class SqlOrderTest extends Assert {


    private OrderDaoSQLImpl orderDao;
    public static final Logger LOGGER = Logger.getLogger(OrderServiceImpl.class);

    @BeforeClass
    public static void initTestSQL() {
        TestUtils.sriptRun(DaoConstants.SQL_CREATE_TEST_SCRIPT);
    }

    @Before
    public void initUserDao() {
        orderDao = TestUtils.getOrderDaoSQLImpl();
    }

    @Test
    public void _01_createOrder() throws SQLException {

        Order order1 = new Order(new Address("Entuziastov", "29a"), new Address("Bulvar Perova", "1"), "(093)306-01-13", "Vasia", 123.21, 12312.1);
        Order order2 = new Order(new Address("tuziastov", "23a"), new Address("ulvar Perova", "4"), "(053)306-01-13", "Fasia", 23.21, 1212.1);
        List<Order> list1 = new ArrayList<>(orderDao.createOrder(order1));
        Order rtOrder1 = list1.get(0);
        List<Order> list2 = new ArrayList<>(orderDao.createOrder(order2));
        Order rtOrder2 = list2.get(0);
        LOGGER.trace("Create order test: Order:  " + order1.toString());
        LOGGER.trace("Create order test: Return: " + rtOrder1.toString());
        assertTrue(rtOrder1.equals(order1));
        assertTrue(rtOrder2.equals(order2));
    }

    @Test
    public void _02_deleteOrder() throws SQLException {
        Order order1 = new Order(new Address("Entuziastov", "29a"), new Address("Bulvar Perova", "1"), "(093)306-01-13", "Vasia", 123.21, 12312.1);
        Order order2 = new Order(new Address("tuziastov", "23a"), new Address("ulvar Perova", "4"), "(053)306-01-13", "Fasia", 23.21, 1212.1);
        orderDao.createOrder(order1);
        orderDao.createOrder(order2);

        assertEquals(orderDao.deleteOrder(order1.getUserPhone()), order1);
        assertEquals(orderDao.deleteOrder(order2.getUserPhone()), order2);
        assertEquals(null, orderDao.getOrder(order1.getUserPhone()));
    }

    @Test
    public void _03_updateOrder() throws SQLException {
        Order order1 = new Order(new Address("Entuziastov", "29a"), new Address("Bulvar Perova", "1"), "(093)306-01-13", "Vasia", 123.21, 12312.1);
        Order order2 = new Order(new Address("tuziastov", "23a"), new Address("ulvar Perova", "4"), "(053)306-01-13", "Fasia", 23.21, 1212.1);

        orderDao.createOrder(order1);
        assertEquals(orderDao.updateOrder(order1.getUserPhone(), order2), order1);
    }

    @Test
    public void _03_getAll() throws SQLException {
        Order order1 = new Order(new Address("Entuziastov", "29a"), new Address("Bulvar Perova", "1"), "(093)306-01-13", "Vasia", 123.21, 12312.1);
        Order order2 = new Order(new Address("tuziastov", "23a"), new Address("ulvar Perova", "4"), "(053)306-01-13", "Fasia", 23.21, 1212.1);
        Order order3 = new Order(new Address("Khreschatyk", "1"), new Address("Knyazhyi Zaton", "1"), "(083)306-01-13", "Leva", 29.21, 6582.1);

        orderDao.createOrder(order1);
        orderDao.createOrder(order2);
        orderDao.createOrder(order3);
        List<Order> list = new ArrayList<>();
        list.add(order1);
        list.add(order2);
        list.add(order3);

        List<Order> getList = new ArrayList<>(orderDao.getOrderList());

        assertEquals(list.get(0), getList.get(0));
        assertEquals(list.get(1), getList.get(1));
        assertEquals(list.get(2), getList.get(2));
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

        TestUtils.sriptRun(DaoConstants.SQL_REMOVE_TEST_SCRIPT);
    }

}
