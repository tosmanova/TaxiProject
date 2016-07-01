import org.junit.*;
import ua.taxi.constants.DaoConstants;
import ua.taxi.dao.sql.OrderStatusDao;

import ua.taxi.model.Order.OrderStatus;
import ua.taxi.utils.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by andrii on 01.07.16.
 */
public class SQL_OrderStatus extends Assert {

    private OrderStatusDao statusDao;

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
    public void initStatusDao() {
        statusDao = new OrderStatusDao();
    }

    @Test
    public void _1_findById() throws SQLException {

        int id1 = statusDao.create(OrderStatus.NEW);
        int id2 = statusDao.create(OrderStatus.IN_PROGRESS);
        int id3 = statusDao.create(OrderStatus.DONE);

        assertEquals(statusDao.findById(id1), OrderStatus.NEW);
        assertEquals(statusDao.findById(id2), OrderStatus.IN_PROGRESS);
        assertEquals(statusDao.findById(id3), OrderStatus.DONE);
    }

    @Test
    public void deleteOrderStatus() throws SQLException {

        int id1 = statusDao.create(OrderStatus.NEW);
        int id2 = statusDao.create(OrderStatus.IN_PROGRESS);
        int id3 = statusDao.create(OrderStatus.DONE);

        assertTrue(statusDao.delete(id1));
        assertTrue(statusDao.delete(id2));
        assertTrue(statusDao.delete(id3));
    }

    @Test
    public void deleteOrderStatusNegative() throws SQLException {

        assertFalse(statusDao.delete(2));
    }

    @Test
    public void update() throws SQLException {

        int id1 = statusDao.create(OrderStatus.NEW);
        int id2 = statusDao.create(OrderStatus.IN_PROGRESS);
        int id3 = statusDao.create(OrderStatus.DONE);

        assertEquals(statusDao.update(id1, OrderStatus.IN_PROGRESS), OrderStatus.IN_PROGRESS);
        assertEquals(statusDao.update(id2, OrderStatus.DONE), OrderStatus.DONE);
        assertEquals(statusDao.update(id3, OrderStatus.NEW), OrderStatus.NEW);
    }

    @After
    public void clearBase() {

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            statement.executeUpdate("TRUNCATE Statuses");
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
