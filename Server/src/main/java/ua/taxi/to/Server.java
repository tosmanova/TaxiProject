package ua.taxi.to;

import ua.taxi.remote.RemoteOrder;

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
    private long count;
    private final int PORT = 8080;


    public Server(RemoteOrder remoteOrder) throws IOException, ClassNotFoundException {
        this.remoteOrder = remoteOrder;
        serverSocket = new ServerSocket(PORT);
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
            System.out.printf("Server: Object -" + count + "- send to client");
            writer.flush();
            writer.close();
        }

    }

    private Object objectHandler(Object object) {
        return remoteOrder.handler(object);
    }

}
