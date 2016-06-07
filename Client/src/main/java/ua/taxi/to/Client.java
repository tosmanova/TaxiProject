package ua.taxi.to;

import ua.taxi.model.Remote.RemoteOrderObject;
import ua.taxi.model.Remote.RemoteUserObject;

import java.io.*;
import java.net.Socket;


/**
 * Created by andrii on 06.06.16.
 */
public class Client {


    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private Socket socket;
    private final String IP = "127.0.0.1";
    private final int PORT = 8080;
    private int count;


    public void send(Object object) throws IOException {
        write(object);
    }

    public Object receive() throws IOException, ClassNotFoundException {
        return read();
    }

    private void write(Object object) throws IOException {
        socket = new Socket(IP, PORT);
        writer = new ObjectOutputStream(socket.getOutputStream());
        writer.writeObject(object);
        System.out.println("Client: Object send to server");
        System.out.println(printMessage(object));
        writer.flush();
        writer.close();
        socket.close();
    }

    private Object read() throws IOException, ClassNotFoundException {
        socket = new Socket(IP, PORT);
        System.out.println("Client: Wait for server");
        reader = new ObjectInputStream(socket.getInputStream());
        Object object = reader.readObject();
        reader.close();
        socket.close();
        return object;
    }

    private String printMessage(Object object) {

        if (object instanceof RemoteOrderObject){
            RemoteOrderObject remoteOrderObject = (RemoteOrderObject) object;
            return remoteOrderObject.getOrderMethods().toString();
        }else if(object instanceof RemoteUserObject){
            RemoteUserObject remoteUserObject = (RemoteUserObject) object;
            return remoteUserObject.getUserServiceMethods().toString();
        }else {
            return "Unknown object";
        }

    }

}
