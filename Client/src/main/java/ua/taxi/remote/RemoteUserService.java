package ua.taxi.remote;

import ua.taxi.exception.RemoteConnectionError;
import ua.taxi.model.Order.Address;
import ua.taxi.model.Remote.OrderServiceMethods;
import ua.taxi.model.Remote.RemoteOrderObject;
import ua.taxi.model.Remote.RemoteUserObject;
import ua.taxi.model.Remote.UserServiceMethods;
import ua.taxi.model.User.Car;
import ua.taxi.model.User.UserValidateMessage;
import ua.taxi.service.UserService;
import ua.taxi.to.Client;

import java.io.IOException;

/**
 * Created by andrii on 06.06.16.
 */
public class RemoteUserService implements UserService {


    Client client;

    public RemoteUserService(Client client) {
        this.client = client;
    }

    @Override
    public UserValidateMessage register(String phone, String pass, String name, Address homeAdress) {
        RemoteUserObject remoteUserObject = new RemoteUserObject(UserServiceMethods.REGISTER_PASSANGER);
        remoteUserObject.setPhone(phone);
        remoteUserObject.setPass(pass);
        remoteUserObject.setName(name);
        remoteUserObject.setHomeAdress(homeAdress);

        try {
            return (UserValidateMessage) send(remoteUserObject);
        } catch (IOException e) {
            e.printStackTrace();
            return new UserValidateMessage(false, "Remote error", e.getMessage(), null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new UserValidateMessage(false, "Remote error", e.getMessage(), null);
        }
    }

    @Override
    public UserValidateMessage register(String phone, String pass, String name, Car car) {
        RemoteUserObject remoteUserObject = new RemoteUserObject(UserServiceMethods.REGISTER_DRIVER);
        remoteUserObject.setPhone(phone);
        remoteUserObject.setPass(pass);
        remoteUserObject.setName(name);
        remoteUserObject.setCar(car);

        try {
            return (UserValidateMessage) send(remoteUserObject);
        } catch (IOException e) {
            e.printStackTrace();
            return new UserValidateMessage(false, "Remote error", e.getMessage(), null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new UserValidateMessage(false, "Remote error", e.getMessage(), null);
        }
    }

    @Override
    public UserValidateMessage changePassanger(String phone, String pass, String name, Address homeAdress) {

        RemoteUserObject remoteUserObject = new RemoteUserObject(UserServiceMethods.CHANGE_PASSANGER);
        remoteUserObject.setPhone(phone);
        remoteUserObject.setPass(pass);
        remoteUserObject.setName(name);
        remoteUserObject.setHomeAdress(homeAdress);

        try {
            return (UserValidateMessage) send(remoteUserObject);
        } catch (IOException e) {
            e.printStackTrace();
            return new UserValidateMessage(false, "Remote error", e.getMessage(), null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new UserValidateMessage(false, "Remote error", e.getMessage(), null);
        }
    }

    @Override
    public UserValidateMessage changeDriver(String phone, String pass, String name, Car car) {

        RemoteUserObject remoteUserObject = new RemoteUserObject(UserServiceMethods.CHANGE_DRIVER);
        remoteUserObject.setPhone(phone);
        remoteUserObject.setPass(pass);
        remoteUserObject.setName(name);
        remoteUserObject.setCar(car);

        try {
            return (UserValidateMessage) send(remoteUserObject);
        } catch (IOException e) {
            e.printStackTrace();
            return new UserValidateMessage(false, "Remote error", e.getMessage(), null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new UserValidateMessage(false, "Remote error", e.getMessage(), null);
        }
    }

    @Override
    public UserValidateMessage getUser(String phone) {

        RemoteUserObject remoteUserObject = new RemoteUserObject(UserServiceMethods.GET_USER);
        remoteUserObject.setPhone(phone);

        try {
            return (UserValidateMessage) send(remoteUserObject);
        } catch (IOException e) {
            e.printStackTrace();
            return new UserValidateMessage(false, "Remote error", e.getMessage(), null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new UserValidateMessage(false, "Remote error", e.getMessage(), null);
        }
    }

    @Override
    public int driverRegisteredQuantity() throws RemoteConnectionError {

        RemoteUserObject remoteUserObject = new RemoteUserObject(UserServiceMethods.DRIVER_REGISTERED_QUANTITY);

        try {
            return (int) send(remoteUserObject);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RemoteConnectionError("driverRegisteredQuantity");
    }

    @Override
    public int passangerRegisteredQuantity() throws RemoteConnectionError {
        RemoteUserObject remoteUserObject = new RemoteUserObject(UserServiceMethods.PASSANGER_REGISTERED_QUANTITY);

        try {
            return (int) send(remoteUserObject);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RemoteConnectionError("passangerRegisteredQuantity");

    }

    @Override
    public UserValidateMessage login(String phone, String pass) {
        RemoteUserObject remoteUserObject = new RemoteUserObject(UserServiceMethods.REGISTER_PASSANGER);
        remoteUserObject.setPhone(phone);
        remoteUserObject.setPass(pass);

        try {
            return (UserValidateMessage) send(remoteUserObject);
        } catch (IOException e) {
            e.printStackTrace();
            return new UserValidateMessage(false, "Remote error", e.getMessage(), null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new UserValidateMessage(false, "Remote error", e.getMessage(), null);
        }
    }

    private Object send(RemoteUserObject remoteUserObject) throws IOException, ClassNotFoundException {
        client.send(remoteUserObject);
        return client.receive();
    }
}
