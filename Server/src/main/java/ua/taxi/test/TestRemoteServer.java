package ua.taxi.test;

import ua.taxi.dao.AppDB;
import ua.taxi.dao.OrderDaoInnerDbImpl;
import ua.taxi.dao.UserDaoInnerDbImpl;
import ua.taxi.remote.RemoteOrder;
import ua.taxi.remote.RemoteUser;
import ua.taxi.service.OrderServiceImpl;
import ua.taxi.service.UserServiceImpl;
import ua.taxi.to.Server;

import java.io.IOException;

/**
 * Created by andrii on 07.06.16.
 */
public class TestRemoteServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        AppDB appDB = new AppDB();
        Server server = new Server(
                new RemoteOrder(new OrderServiceImpl(new OrderDaoInnerDbImpl(appDB))),
                new RemoteUser(new UserServiceImpl(new UserDaoInnerDbImpl(appDB))));
    }
}
