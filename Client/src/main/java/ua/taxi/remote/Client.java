package ua.taxi.remote;

import org.apache.log4j.Logger;
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
    private static final Logger LOGGER = Logger.getLogger(Client.class);

    public Client() {
        try {
            socket = new Socket(IP, PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (UnknownHostException e) {
            LOGGER.error("Don't know about host " + IP + "\n", e);
            System.exit(1);
        } catch (IOException e) {
            LOGGER.error("Couldn't get I/O for the connection to " + IP + "\n", e);
            System.exit(1);
        }
    }


    public Object send(Object object) throws IOException, ClassNotFoundException {

        out.writeObject(object);
        LOGGER.trace("Client: " + count++ + ".Object send to server - " + printMessage(object));
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
