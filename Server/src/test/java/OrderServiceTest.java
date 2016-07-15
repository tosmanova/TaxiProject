import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.runners.MethodSorters;
import ua.taxi.base.model.order.Address;
import ua.taxi.base.model.order.Order;
import ua.taxi.base.model.order.OrderStatus;
import ua.taxi.base.model.order.OrderValidateMessage;
import ua.taxi.base.service.OrderService;
import ua.taxi.server.constants.Constants;
import ua.taxi.server.dao.OrderDao;

import java.util.List;

/**
 * Created by andrii on 14.07.16.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderServiceTest extends Assert {

    private OrderDao orderDao;
    private OrderService orderService;

    private static final Logger LOGGER = Logger.getLogger(SqlOrderTest.class);

    private Order order1 = new Order(new Address("Entuziastov", "29a"), new Address("Bulvar Perova", "1"), "(093)306-01-13", "Vasia", 123.21, 12312.1);
    private Order order2 = new Order(new Address("tuziastov", "23a"), new Address("ulvar Perova", "4"), "(053)306-01-13", "Kolia", 23.21, 1212.1);
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
        orderService = TestUtils.getOrderService();
    }

    @Test
    public void _01_createOrder() {

        OrderValidateMessage message = orderService.createOrder(order1.getUserPhone(), order1.getUserName(), order1.getFrom(), order1.getTo());
        Order rOrder = orderDao.getOrder(order1.getUserPhone());
        assertEquals(rOrder, message.getOrder());
    }

    @Test
    public void _01_createOrderNeg() {

        orderService.createOrder(order1.getUserPhone(), order1.getUserName(), order1.getFrom(), order1.getTo());
        OrderValidateMessage message2 = orderService.createOrder(order1.getUserPhone(), order1.getUserName(), order1.getFrom(), order1.getTo());
        assertFalse(message2.isState());
    }


    @Test
    public void _02_deleteOrder() {
        OrderValidateMessage message = orderService.createOrder(order1.getUserPhone(), order1.getUserName(), order1.getFrom(), order1.getTo());
        OrderValidateMessage delMess = orderService.cancelOrder(order1.getUserPhone());
        assertTrue(delMess.isState());
    }

    @Test
    public void _03_getOrder() {
        OrderValidateMessage message = orderService.createOrder(order1.getUserPhone(), order1.getUserName(), order1.getFrom(), order1.getTo());
        OrderValidateMessage message2 = orderService.getOrder(order1.getUserPhone());
        assertEquals(message.getOrder(), message2.getOrder());
    }

    @Test
    public void _04_changeOrder() {
        OrderValidateMessage message = orderService.createOrder(order1.getUserPhone(), order1.getUserName(), order1.getFrom(), order1.getTo());
        OrderValidateMessage message2 = orderService.changeOrder(order1.getUserPhone(), order2.getUserName(), order1.getFrom(), order1.getTo());
        assertEquals(message2.getOrder().getUserName(), order2.getUserName());
    }

    @Test
    public void _05_getOrderInProgresByDriverPhone() {

        OrderValidateMessage message = orderService.createOrder(order1.getUserPhone(), order1.getUserName(), order1.getFrom(), order1.getTo());
        OrderValidateMessage message2 = orderService.changeOrderStatus(order1.getUserPhone(), OrderStatus.IN_PROGRESS);
        OrderValidateMessage message3 = orderService.getOrderInProgresByDriverPhone(order1.getUserPhone());
        assertTrue(message2.isState());
        assertTrue(message3.isState());
    }

    @Test
    public void _06_getAllOrders() {

        orderService.createOrder(order1.getUserPhone(), order1.getUserName(), order1.getFrom(), order1.getTo());
        orderService.createOrder(order2.getUserPhone(), order2.getUserName(), order2.getFrom(), order2.getTo());
        orderService.createOrder(order3.getUserPhone(), order3.getUserName(), order3.getFrom(), order3.getTo());

        List<Order> list = orderService.getAllOrders(1, 5);

        assertEquals(list.size(), 3);

    }

    @Test
    public void _07_getAllOrdersWithStatus() {

        orderService.createOrder(order1.getUserPhone(), order1.getUserName(), order1.getFrom(), order1.getTo());
        orderService.createOrder(order2.getUserPhone(), order2.getUserName(), order2.getFrom(), order2.getTo());

        List<Order> list = orderService.getOrdersWithStatus(OrderStatus.NEW, 1, 5);

        assertEquals(list.size(), 2);

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
