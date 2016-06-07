package ua.taxi.test;

import ua.taxi.model.Address;
import ua.taxi.model.OrderValidateMessage;
import ua.taxi.remote.RemoteOrderService;
import ua.taxi.to.Client;

/**
 * Created by andrii on 07.06.16.
 */
public class ClientTest {

    public static void main(String[] args) {

        RemoteOrderService service = new RemoteOrderService(new Client());

        OrderValidateMessage message = service.createOrder("(093)306-01-13", "Andrey", new Address("ул. Ентузиастов", "27"), new Address("ул. Российская", "82"));

        System.out.println(message);

        int count = service.ordersRegisteredQuantity();
        System.out.println(count);

    }

}
