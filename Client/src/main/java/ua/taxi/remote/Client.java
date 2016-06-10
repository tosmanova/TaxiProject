package ua.taxi.remote;

import ua.taxi.model.Remote.RemoteOrderObject;
import ua.taxi.model.Remote.RemoteUserObject;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Created by andrii on 06.06.16.
 */
public class Client {

    private final String IP = "127.0.0.1";
    private final int PORT = 8080;
    private int count;
    private Socket socket;
    private ObjectOutput out;
    private ObjectInput in;

    public Client() {
        try {
            socket = new Socket(IP, PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + IP);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    IP);
            System.exit(1);
        }
    }


    public Object send(Object object) throws IOException, ClassNotFoundException {

        out.writeObject(object);
        System.out.println("Client: Object send to server");
        System.out.println(printMessage(object));
        printMessage(object);
        return in.readObject();
    }

    private String printMessage(Object object) {

        if (object instanceof RemoteOrderObject) {
            RemoteOrderObject remoteOrderObject = (RemoteOrderObject) object;
            return remoteOrderObject.getOrderMethods().toString();
        } else if (object instanceof RemoteUserObject) {
            RemoteUserObject remoteUserObject = (RemoteUserObject) object;
            return remoteUserObject.getUserServiceMethods().toString();
        } else {
            return "Unknown object";
        }

    }

}
