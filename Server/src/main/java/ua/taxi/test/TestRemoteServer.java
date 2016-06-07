package ua.taxi.test;

import ua.taxi.dao.AppDB;
import ua.taxi.dao.OrderDaoInnerDbImpl;
import ua.taxi.remote.RemoteOrder;
import ua.taxi.service.OrderServiceImpl;
import ua.taxi.to.Server;

import java.io.IOException;

/**
 * Created by andrii on 07.06.16.
 */
public class TestRemoteServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Server server = new Server(new RemoteOrder(new OrderServiceImpl(new OrderDaoInnerDbImpl(new AppDB()))));
    }
}
