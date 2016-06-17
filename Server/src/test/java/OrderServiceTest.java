import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.taxi.dao.AppDB;
import ua.taxi.dao.OrderDaoInnerDbImpl;
import ua.taxi.dao.serialize.JsonSaveLoad;
import ua.taxi.model.Order.Address;
import ua.taxi.model.Order.Order;
import ua.taxi.model.Order.OrderStatus;
import ua.taxi.model.Order.OrderValidateMessage;
import ua.taxi.service.OrderService;
import ua.taxi.service.OrderServiceImpl;

/**
 * Created by Andrii on 6/17/2016.
 */
public class OrderServiceTest extends Assert {

    private OrderService orderService = new OrderServiceImpl(new OrderDaoInnerDbImpl(new AppDB(new JsonSaveLoad())));
    private Order order1;
    private Order order2;

    @BeforeClass
    private void init() {
        AppDB appDB = new AppDB(new JsonSaveLoad());
        orderService = new OrderServiceImpl(new OrderDaoInnerDbImpl(appDB));
        appDB.loadOrders();
        order1 = appDB.getOrder("(093)306-56-89");
        order2 = appDB.getOrder("(097)506-61-89");
    }

    @Test
    public void ordersRegisteredQuantity() {
        assertEquals(orderService.ordersRegisteredQuantity(), 3);
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
        OrderValidateMessage messageNegative = orderService.changeOrderStatus("(093)306-56-89", OrderStatus.DONE);
        assertNotEquals(messageNegative.isState(), true);
    }

    @Test
    public void getOrder() {
//
    }

    @Test
    public void getOrderInProgresByDriverPhone() {

    }

    @Test
    public void getAllOrders() {

    }

    @Test
    public void getNewOrders() {

    }

    @Test
    public void changeOrder4() {

    }

    @Test
    public void changeOrder2() {

    }

    @Test
    public void cancelOrder() {

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


/**
 *
 * orderService.createOrder("(093)306-01-13", "Andrey", new Address("ул. Ентузиастов", "27"), new Address("ул. Российская", "82"));
 orderService.createOrder("(093)306-01-14", "Andrii", new Address("ул. Хрещатик", "1"), new Address("ул. Бориспольская", "4"));
 orderService.createOrder("(093)306-01-15", "Andrii", new Address("ул. Ентузиастов", "27"), new Address("ул. Княжий Затон", "3"));
 *
 * int ordersRegisteredQuantity();

 OrderValidateMessage createOrder(String userPhone, String userName, Address from, Address to);

 OrderValidateMessage changeOrderStatus(String phone, OrderStatus newStatus);

 OrderValidateMessage getOrder(String phone);

 OrderValidateMessage getOrderInProgresByDriverPhone(String driverPhone);

 List<Order> getAllOrders() throws RemoteConnectionError;

 List<Order> getNewOrders() throws RemoteConnectionError;

 Map<OrderStatus, Integer> getStatusCounterMap() throws RemoteConnectionError;

 OrderValidateMessage changeOrder(String phone, String name, Address from, Address to);

 OrderValidateMessage changeOrder(String phone, Order newOrder);

 OrderValidateMessage cancelOrder(String phone);

 Double getDistance(Address from, Address to) throws RemoteConnectionError;

 Double getPrice(Double distance) throws RemoteConnectionError;

 Double getPrice(Address from, Address to) throws RemoteConnectionError;
 * */

}
