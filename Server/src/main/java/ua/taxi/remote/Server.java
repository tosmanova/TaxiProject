package ua.taxi.remote;

import org.apache.log4j.Logger;
import ua.taxi.dao.appdb.AppDB;
import ua.taxi.dao.appdb.OrderDaoInnerDbImpl;
import ua.taxi.dao.appdb.UserDaoInnerDbImpl;
import ua.taxi.dao.serialize.JsonSaveLoad;
import ua.taxi.dao.sql.OrderDaoSQLImpl;
import ua.taxi.dao.sql.UserDaoSqlImpl;
import ua.taxi.model.Remote.RemoteOrderObject;
import ua.taxi.model.Remote.RemoteUserObject;
import ua.taxi.service.OrderServiceImpl;
import ua.taxi.service.UserServiceImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by andrii on 06.06.16.
 */
public class Server {


    private OrderServiceDispatcher orderServiceDispatcher;
    private UserServiceDispatcher userServiceDispatcher;
    private Object inObject;
    private Object outObject;
    private long count;
    private final int PORT = 8080;
    private static final Logger LOGGER = Logger.getLogger(Server.class);


    public Server(OrderServiceDispatcher orderServiceDispatcher,
                  UserServiceDispatcher userServiceDispatcher) throws ClassNotFoundException {
        this.orderServiceDispatcher = orderServiceDispatcher;
        this.userServiceDispatcher = userServiceDispatcher;
        LOGGER.info("Start TaxiApp Server \n Port: " + PORT);
        run();
    }

    private void run() throws ClassNotFoundException {
        try (ServerSocket serverSocket = new ServerSocket(PORT);
             Socket client = serverSocket.accept();
             ObjectInput reader = new ObjectInputStream(client.getInputStream());
             ObjectOutput writer = new ObjectOutputStream(client.getOutputStream());
        ) {

            while ((inObject = reader.readObject()) != null) {
                outObject = objectHandler(inObject);
                writer.writeObject(outObject);
                count++;
                LOGGER.trace("Send object N: " + count);
            }
        } catch (IOException e) {
            LOGGER.error("Exception caught when trying to listen on port "
                    + PORT + " or listening for a connection", e);
        }
    }

    private Object objectHandler(Object object) throws ClassNotFoundException {

        if (object instanceof RemoteOrderObject) {
            return orderServiceDispatcher.handler(object);
        } else if (object instanceof RemoteUserObject) {
            return userServiceDispatcher.handler(object);
        } else {
            throw new ClassNotFoundException("Server handler error");
        }
    }

    public static void main(String[] args) {
        //AppDB appDB = new AppDB(new JsonSaveLoad());
        //appDB.loadAllData();
        try {
            new Server(
                    new OrderServiceDispatcher(new OrderServiceImpl(new OrderDaoSQLImpl())),
                    new UserServiceDispatcher(new UserServiceImpl(new UserDaoSqlImpl())));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //appDB.saveAllData();
    }

}
