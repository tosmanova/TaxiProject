package ua.taxi.to;
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


    public Server() throws IOException {
        serverSocket = new ServerSocket(8080);
    }

    public void send(Object object) throws IOException {
        write(object);
    }

    public Object recive() throws IOException, ClassNotFoundException {
        return read();
    }

    private void write(Object object) throws IOException {
        client = serverSocket.accept();
        writer = new ObjectOutputStream(client.getOutputStream());
        writer.writeObject(object);
        writer.flush();
        writer.close();
    }

    private Object read() throws IOException, ClassNotFoundException {
        client = serverSocket.accept();
        System.out.println("Wait for client");
        reader = new ObjectInputStream(client.getInputStream());
        Object object = reader.readObject();
        reader.close();
        client.close();
        return object;
    }
}
