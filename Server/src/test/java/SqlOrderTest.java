import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.runners.MethodSorters;
import ua.taxi.base.model.order.OrderStatus;
import ua.taxi.server.constants.Constants;
import ua.taxi.server.dao.OrderDao;
import ua.taxi.base.model.order.Address;
import ua.taxi.base.model.order.Order;
import ua.taxi.server.utils.ConnectionFactory;

import javax.persistence.PersistenceException;
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


    private OrderDao orderDao;

    private static final Logger LOGGER = Logger.getLogger(SqlOrderTest.class);

    private Order order1 = new Order(new Address("Entuziastov", "29a"), new Address("Bulvar Perova", "1"), "(093)306-01-13", "Vasia", 123.21, 12312.1);
    private Order order2 = new Order(new Address("tuziastov", "23a"), new Address("ulvar Perova", "4"), "(053)306-01-13", "Fasia", 23.21, 1212.1);
    private Order order4 = new Order(new Address("tuziastov", "23a"), new Address("ulvar Perova", "4"), "(093)306-01-13", "Fasia", 23.21, 1212.1);
    private Order order3 = new Order(new Address("Khreschatyk", "1"), new Address("Knyazhyi Zaton", "1"), "(083)306-01-13", "Leva", 29.21, 6582.1);
    private Order order5 = new Order(new Address("Khreschatyk", "1"), new Address("Knyazhyi Zaton", "1"), "(044)306-01-13", "TTTTT", 29.21, 6582.1);

    @BeforeClass
    public static void initTestSQL() {
        TestUtils.scriptRun(Constants.SQL_CREATE_TEST_SCRIPT);
        TestUtils.init();
    }

    @Before
    public void initUserDao() {
        orderDao = TestUtils.getOrderDaoSQLImpl();
    }

    @Test
    public void _01_createOrder() {

        orderDao.createOrder(order1);
        Order rOrder = orderDao.getOrder("(093)306-01-13");
        assertEquals(rOrder, order1);
    }


    @Test
    public void _02_deleteOrder() {
        orderDao.createOrder(order1);
        assertEquals(order1, orderDao.deleteOrder(order1.getUserPhone()));
    }

    @Test(expected = PersistenceException.class)
    public void _02_deleteOrderNeg() {

        orderDao.createOrder(order1);
        orderDao.deleteOrder(order1.getUserPhone());
        orderDao.getOrder(order1.getUserPhone());
    }


    @Test
    public void _03_updateOrder() {

        orderDao.createOrder(order1);
        orderDao.updateOrder(order4.getUserPhone(), order4);
        Order uOrder = orderDao.getOrder(order4.getUserPhone());

        assertEquals(order4.getUserName(), uOrder.getUserName());
    }


    @Test
    public void _04_getAll() {

        orderDao.createOrder(order1);
        orderDao.createOrder(order2);
        orderDao.createOrder(order3);
        List<Order> list = new ArrayList<>();
        list.add(order1);
        list.add(order2);
        list.add(order3);

        List<Order> rList = orderDao.getOrderList(1, 5);
        assertEquals(list.size(), rList.size());

        rList = orderDao.getOrderList(1, 2);
        assertEquals(2, rList.size());
    }

    @Test
    public void _04_getAllNeg() {
        List<Order> rList = orderDao.getOrderList(1, 5);
        assertEquals(0, rList.size());
    }


    @Test
    public void _04_getOrdersWithStatus() {

        orderDao.createOrder(order1);
        order2.setOrderStatus(OrderStatus.IN_PROGRESS);
        orderDao.createOrder(order2);
        order3.setOrderStatus(OrderStatus.DONE);
        orderDao.createOrder(order3);
        order5.setOrderStatus(OrderStatus.DONE);
        orderDao.createOrder(order5);

        List<Order> rList = orderDao.getOrderWithStatus(OrderStatus.NEW, 1, 5);
        assertEquals(1, rList.size());
        rList = orderDao.getOrderWithStatus(OrderStatus.DONE, 1, 5);
        assertEquals(2, rList.size());
        rList = orderDao.getOrderWithStatus(OrderStatus.IN_PROGRESS, 1, 5);
        assertEquals(1, rList.size());
    }

    @Test
    public void _04_getCount() {

        orderDao.createOrder(order1);
        order2.setOrderStatus(OrderStatus.IN_PROGRESS);
        orderDao.createOrder(order2);
        order3.setOrderStatus(OrderStatus.DONE);
        orderDao.createOrder(order3);
        order5.setOrderStatus(OrderStatus.DONE);
        orderDao.createOrder(order5);

        assertEquals(4, orderDao.getOrdersRegisteredQuantity());
        assertEquals(1, orderDao.getOrdersNewCount());
        assertEquals(1, orderDao.getOrdersInProgressCount());
        assertEquals(2, orderDao.getOrdersDoneCount());

    }

    @After
    public void clearBase() {
        TestUtils.clearOrders();
    }

    @AfterClass
    public static void removeTestSQL() {
        TestUtils.scriptRun(Constants.SQL_REMOVE_TEST_SCRIPT);
    }

}
