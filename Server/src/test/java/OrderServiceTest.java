import org.junit.*;
import ua.taxi.dao.appdb.AppDB;
import ua.taxi.dao.appdb.OrderDaoInnerDbImpl;
import ua.taxi.dao.serialize.JsonSaveLoad;
import ua.taxi.exception.RemoteConnectionError;
import ua.taxi.model.Order.Address;
import ua.taxi.model.Order.Order;
import ua.taxi.model.Order.OrderStatus;
import ua.taxi.model.Order.OrderValidateMessage;
import ua.taxi.service.OrderService;
import ua.taxi.service.OrderServiceImpl;
import ua.taxi.utils.Utils;

import java.util.List;

/**
 * Created by Andrii on 6/17/2016.
 */
public class OrderServiceTest extends Assert {

    private OrderService orderService;
    private Order order1;
    private Order order2;

    @Before
    public void init() {
        AppDB appDB = new AppDB(new JsonSaveLoad());
        appDB.loadOrders();
        orderService = new OrderServiceImpl(new OrderDaoInnerDbImpl(appDB));
        order1 = appDB.getOrder("(093)306-56-89");
        order2 = appDB.getOrder("(097)506-61-89");
    }

    @Test
    public void ordersRegisteredQuantity() {
        assertEquals(orderService.ordersRegisteredQuantity(), 4);
        assertNotEquals(orderService.ordersRegisteredQuantity(), 0);
    }

    @Test
    public void createOrder() {

        OrderValidateMessage message = orderService.createOrder(
                "(093)306-11-11", "Andrey", new Address("ул. Ентузиастов", "27"), new Address("ул. Российская", "82"));
        assertEquals(message.isState(), true);
        assertEquals(message.getOrder().getUserPhone(), "(093)306-11-11");
        assertEquals(message.getOrder().getUserName(), "Andrey");
        assertEquals(message.getOrder().getFrom().toString(), "ул. Ентузиастов 27");
        assertEquals(message.getOrder().getTo().toString(), "ул. Российская 82");
        OrderValidateMessage messageNegative = orderService.createOrder(
                "(093)306-11-11", "Andrey", new Address("ул. Ентузиастов", "27"), new Address("ул. Российская", "82"));
        assertNotEquals(messageNegative.isState(), true);

    }

    @Test
    public void changeOrderStatus() {
        OrderValidateMessage message = orderService.changeOrderStatus("(093)306-56-89", OrderStatus.DONE);
        assertEquals(message.isState(), true);
        assertEquals(message.getOrder().getOrderStatus(), OrderStatus.DONE);
        OrderValidateMessage messageNegative = orderService.changeOrderStatus("(111)306-56-89", OrderStatus.DONE);
        assertNotEquals(messageNegative.isState(), true);
    }

    @Test

    public void getOrder() {
        OrderValidateMessage message = orderService.getOrder("(093)306-56-89");
        assertEquals(message.isState(), true);
        assertEquals(message.getOrder().getUserPhone(), "(093)306-56-89");
        assertEquals(message.getOrder().getUserName(), "Vasia");
        assertEquals(message.getOrder().getDriverPhone(), "");
        assertEquals(message.getOrder().getFrom().toString(), "пр. Харьковский 103");
        assertEquals(Utils.priceFormat(message.getOrder().getPrice()), "132 грн.");
        assertEquals(Utils.distanceFormat(message.getOrder().getDistance()), "18,32 km");

        assertEquals(message.getOrder().getOrderStatus().toString(), "NEW");

        OrderValidateMessage messageNegative = orderService.getOrder("(111)306-56-89");
        assertNotEquals(messageNegative.isState(), true);

    }

    @Test
    public void getOrderInProgresByDriverPhone() {
        OrderValidateMessage message = orderService.getOrderInProgresByDriverPhone("(073)306-01-13");
        assertEquals(message.isState(), true);
        assertEquals(message.getOrder().getDriverPhone(), "(073)306-01-13");
        assertEquals(message.getOrder().getOrderStatus().toString(), OrderStatus.IN_PROGRESS.toString());

        OrderValidateMessage messageNegative = orderService.getOrder("(063)306-01-13");
        assertNotEquals(messageNegative.isState(), true);
    }

    @Test
    public void getAllOrders() {

        List<Order> orderList;
        try {
            orderList = orderService.getAllOrders();
            assertEquals(orderList.size(), 4);
        } catch (RemoteConnectionError remoteConnectionError) {
            remoteConnectionError.printStackTrace();
        }

    }

    @Test
    public void getNewOrders() {
        List<Order> orderList;
        boolean isNewOrder = true;
        try {
            orderList = orderService.getNewOrders();
            assertEquals(orderList.size(), 2);
            for (Order order : orderList) {
                if (order.getOrderStatus() != OrderStatus.NEW) {
                    isNewOrder = false;
                }
            }
            assertEquals(isNewOrder, true);

        } catch (RemoteConnectionError remoteConnectionError) {
            remoteConnectionError.printStackTrace();
        }


    }

    @Test
    public void changeOrder4() {

        OrderValidateMessage message = orderService.changeOrder(
                "(093)306-56-89", "Vasia", new Address("ул. Ентузиастов", "35"), new Address("ул. Российская", "76"));
        assertEquals(message.isState(), true);
        assertEquals(message.getOrder().getUserPhone(), "(093)306-56-89");
        assertEquals(message.getOrder().getUserName(), "Vasia");
        assertEquals(message.getOrder().getFrom().toString(), "ул. Ентузиастов 35");
        assertEquals(message.getOrder().getTo().toString(), "ул. Российская 76");
        OrderValidateMessage messageNegative = orderService.changeOrder(
                "(011)306-11-11", "Andrey", new Address("ул. Ентузиастов", "27"), new Address("ул. Российская", "82"));
        assertNotEquals(messageNegative.isState(), true);
    }

    @Test
    public void changeOrder2() {

        OrderValidateMessage message = orderService.changeOrder("(093)306-56-89", order2);
        assertEquals(message.isState(), true);
        assertEquals(message.getOrder().getUserPhone(), order2.getUserPhone());
        assertEquals(message.getOrder().getUserName(), order2.getUserName());
        assertEquals(message.getOrder().getFrom().toString(), order2.getFrom().toString());
        assertEquals(message.getOrder().getTo().toString(), order2.getTo().toString());
        OrderValidateMessage messageNegative = orderService.changeOrder(
                "(011)306-11-11", "Andrey", new Address("ул. Ентузиастов", "27"), new Address("ул. Российская", "82"));
        assertNotEquals(messageNegative.isState(), true);


    }

    @Test
    public void cancelOrder() throws RemoteConnectionError {

        OrderValidateMessage message = orderService.cancelOrder("(093)306-56-89");
        List<Order> orderList = orderService.getAllOrders();
        assertEquals(message.isState(), true);
        assertEquals(orderList.size(), 3);

    }

    @Test
    public void getDistance() {

    }

    @Test
    public void getPrice() {

    }

    @Test
    public void getPrice2() {

    }


}
