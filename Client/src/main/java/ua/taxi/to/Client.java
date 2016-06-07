package ua.taxi.to;

import java.io.*;
import java.net.Socket;

/**
 * Created by andrii on 06.06.16.
 */
public class Client {


    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private Socket socket;


    public void send(Object object) throws IOException {
        write(object);
    }

    public Object recive() throws IOException, ClassNotFoundException {
        return read();
    }

    private void write(Object object) throws IOException {
        socket = new Socket("127.0.0.1", 8080);
        writer = new ObjectOutputStream(socket.getOutputStream());
        writer.writeObject(object);
        writer.flush();
        writer.close();
        socket.close();
    }

    private Object read() throws IOException, ClassNotFoundException {
        socket = new Socket("127.0.0.1", 8080);
        System.out.println("Wait for server");
        reader = new ObjectInputStream(socket.getInputStream());
        Object object = reader.readObject();
        reader.close();
        socket.close();
        return object;

    }

}
