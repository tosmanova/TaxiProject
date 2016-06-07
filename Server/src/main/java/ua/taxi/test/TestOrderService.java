package ua.taxi.test;

import ua.taxi.dao.AppDB;
import ua.taxi.dao.OrderDaoInnerDbImpl;
import ua.taxi.model.Address;
import ua.taxi.model.Order;
import ua.taxi.model.OrderStatus;
import ua.taxi.model.OrderValidateMessage;
import ua.taxi.service.OrderServiceImpl;

/**
 * Created by Andrii on 5/10/2016.
 */
public class TestOrderService {

    static OrderService orderService = new OrderServiceImpl(new OrderDaoInnerDbImpl(new AppDB()));

    public static void main(String[] args) {

        String phone = "(093)306-01-13";
        Order order;

        OrderValidateMessage orderValidateMessage = orderService.createOrder(phone, "Andrey", new Address("ул. Ентузиастов", "27"), new Address("ул. Российская", "82"));

        order = orderValidateMessage.getOrder();

        System.out.println(order.toString());
        System.out.println();

        orderService.changeOrderStatus(phone, OrderStatus.IN_PROGRESS);

        System.out.println(orderService.getOrder(phone).getOrder().toString());
        System.out.println();

        orderValidateMessage = orderService.getOrder(phone);
        order = orderValidateMessage.getOrder();
        order.setDriverPhone("(093)306-11-13");
        orderService.changeOrder(phone, order);

        System.out.println(orderService.getOrder(phone).getOrder().toString());
        System.out.println();

        orderValidateMessage = orderService.getOrderInProgresByDriverPhone("(093)306-11-13");

        System.out.println(orderValidateMessage);
        System.out.println();
        System.out.println(orderValidateMessage.getOrder().toString());

    }


}
