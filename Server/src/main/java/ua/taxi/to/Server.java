package ua.taxi.to;

import ua.taxi.dao.AppDB;
import ua.taxi.dao.OrderDaoInnerDbImpl;
import ua.taxi.dao.UserDaoInnerDbImpl;
import ua.taxi.model.Remote.RemoteOrderObject;
import ua.taxi.model.Remote.RemoteUserObject;
import ua.taxi.remote.RemoteOrder;
import ua.taxi.remote.RemoteUser;
import ua.taxi.service.OrderServiceImpl;
import ua.taxi.service.UserServiceImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by andrii on 06.06.16.
 */
public class Server {

    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private ServerSocket serverSocket;
    private Socket client;
    private RemoteOrder remoteOrder;
    private RemoteUser remoteUser;
    private long count;
    private final int PORT = 8080;


    public Server(RemoteOrder remoteOrder, RemoteUser remoteUser) throws IOException, ClassNotFoundException {
        this.remoteOrder = remoteOrder;
        this.remoteUser = remoteUser;
        serverSocket = new ServerSocket(PORT);
        System.out.printf("Start TaxiApp Server\nPort: %d; InetAdress: %s; \n", PORT, serverSocket.getInetAddress());
        run();
    }

    private void run() throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println("Server: Wait for client");
            client = serverSocket.accept();
            reader = new ObjectInputStream(client.getInputStream());
            Object object = reader.readObject();
            object = objectHandler(object);
            reader.close();
            client.close();

            client = serverSocket.accept();
            writer = new ObjectOutputStream(client.getOutputStream());
            writer.writeObject(object);
            count++;
            System.out.println("Server: Object -" + count + " - send to client");
            writer.flush();
            writer.close();
        }
    }

    private Object objectHandler(Object object) throws ClassNotFoundException {

        if (object instanceof RemoteOrderObject) {
            return remoteOrder.handler(object);
        } else if (object instanceof RemoteUserObject) {
            return remoteUser.handler(object);
        } else {
            throw new ClassNotFoundException("Server handler error");
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        AppDB appDB = new AppDB();
        new Server(
                new RemoteOrder(new OrderServiceImpl(new OrderDaoInnerDbImpl(appDB))),
                new RemoteUser(new UserServiceImpl(new UserDaoInnerDbImpl(appDB))));
    }

}
