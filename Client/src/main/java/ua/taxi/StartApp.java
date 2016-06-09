package ua.taxi;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ua.taxi.exception.RemoteConnectionError;
import ua.taxi.model.Order.Address;
import ua.taxi.model.Order.Order;
import ua.taxi.model.Order.TableViewOrder;
import ua.taxi.model.User.Car;
import ua.taxi.remote.RemoteOrderService;
import ua.taxi.remote.RemoteUserService;
import ua.taxi.service.*;
import ua.taxi.to.Client;
import ua.taxi.view.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class StartApp extends Application {

    private UserService userService;
    private OrderService orderService;

    private Stage primaryStage;
    private BorderPane rootLayout;
    private AnchorPane chooseOrderLayout;
    private AnchorPane createOrderFormLayout;
    private AnchorPane driverRegisterFormLayout;
    private AnchorPane orderStatusLayout;
    private AnchorPane passangerRegisterFormLayout;
    private AnchorPane enterWindowLayout;
    private AnchorPane chooseOrderInfoLayout;

    private EnterWindowCntrl enterWindowController;
    private MainWindowCntrl mainWindowController;
    private CreateOrderFormCntrl createOrderFormCntrl;
    private PassangerRegisterFormCntrl passangerRegisterFormCntrl;
    private DriverRegisterFormCntrl driverRegisterFormCntrl;
    private OrderStatusCntrl orderStatusCntrl;
    private ChooseOrderCntrl chooseOrderCntrl;
    private ChooseOrderInfoCntrl chooseOrderInfoCntrl;

    private ObservableList<TableViewOrder> orderList = FXCollections.observableArrayList();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Client client = new Client();
        userService = new RemoteUserService(client);
        orderService = new RemoteOrderService(client);

        this.primaryStage = primaryStage;

        this.primaryStage.setTitle("TaxiApp");

        initDaoWithSomeUsers();
        initDaoWithSomeOrders();

        initOrderList();

        initRootLayout();
        initEnterWindow();
        initDriverRegisterFormLayout();
        initPassangerRegisterFormLayout();
        initCreateOrderFormLayout();
        initChooseOrderLayout();
        initOrderStatusLayout();
        initChooseOrderInfo();

        initCrossLinks();

        showEnterWindow();
    }

    public void initOrderList()  {
        List<Order> list = null;
        try {
            list = orderService.getNewOrders();
        } catch (RemoteConnectionError remoteConnectionError) {
            remoteConnectionError.printStackTrace();
        }
        orderList.clear();
        for (Order order : list) {
            orderList.add(new TableViewOrder(order));
        }
    }

    private void initCrossLinks() {

        enterWindowController.setChooseOrderCntrl(chooseOrderCntrl);
        enterWindowController.setCreateOrderFormCntrl(createOrderFormCntrl);
        enterWindowController.setOrderStatusCntrl(orderStatusCntrl);
        enterWindowController.setChooseOrderInfoCntrl(chooseOrderInfoCntrl);

        createOrderFormCntrl.setMainWindowCntrl(mainWindowController);
        createOrderFormCntrl.setOrderStatusCntrl(orderStatusCntrl);
        createOrderFormCntrl.setPassangerRegisterFormCntrl(passangerRegisterFormCntrl);

        driverRegisterFormCntrl.setChooseOrderCntrl(chooseOrderCntrl);
        driverRegisterFormCntrl.setMainWindowCntrl(mainWindowController);

        passangerRegisterFormCntrl.setCreateOrderFormCntrl(createOrderFormCntrl);
        passangerRegisterFormCntrl.setMainWindowCntrl(mainWindowController);

        orderStatusCntrl.setCreateOrderFormCntrl(createOrderFormCntrl);
        orderStatusCntrl.setMainWindowCntrl(mainWindowController);

        chooseOrderCntrl.setDriverRegisterFormCntrl(driverRegisterFormCntrl);
        chooseOrderCntrl.setChooseOrderInfoCntrl(chooseOrderInfoCntrl);
        chooseOrderCntrl.setMainWindowCntrl(mainWindowController);

        chooseOrderInfoCntrl.setMainWindowCntrl(mainWindowController);
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartApp.class.getResource("/MainWindow.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            mainWindowController = loader.getController();
            mainWindowController.setStartApp(this);
            mainWindowController.updateOrderCounters();
            mainWindowController.updateUserCounters();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (RemoteConnectionError remoteConnectionError) {
            remoteConnectionError.printStackTrace();
        }
    }

    public void initEnterWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartApp.class.getResource("/EnterWindow.fxml"));
            enterWindowLayout = (AnchorPane) loader.load();

            enterWindowController = loader.getController();
            enterWindowController.setStartApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void initChooseOrderLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartApp.class.getResource("/ChooseOrder.fxml"));
            chooseOrderLayout = (AnchorPane) loader.load();

            chooseOrderCntrl = loader.getController();
            chooseOrderCntrl.setStartApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initCreateOrderFormLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartApp.class.getResource("/CreateOrderForm.fxml"));
            createOrderFormLayout = (AnchorPane) loader.load();

            createOrderFormCntrl = loader.getController();
            createOrderFormCntrl.setStartApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initDriverRegisterFormLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartApp.class.getResource("/DriverRegisterForm.fxml"));
            driverRegisterFormLayout = (AnchorPane) loader.load();

            driverRegisterFormCntrl = loader.getController();
            driverRegisterFormCntrl.setStartApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initOrderStatusLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartApp.class.getResource("/OrderStatus.fxml"));
            orderStatusLayout = (AnchorPane) loader.load();

            orderStatusCntrl = loader.getController();
            orderStatusCntrl.setStartApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initPassangerRegisterFormLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartApp.class.getResource("/PassangerRegisterForm.fxml"));
            passangerRegisterFormLayout = (AnchorPane) loader.load();

            passangerRegisterFormCntrl = loader.getController();
            passangerRegisterFormCntrl.setStartApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initChooseOrderInfo() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartApp.class.getResource("/ChooseOrderInfo.fxml"));
            chooseOrderInfoLayout = (AnchorPane) loader.load();

            chooseOrderInfoCntrl = loader.getController();
            chooseOrderInfoCntrl.setStartApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initDaoWithSomeUsers() {
        //default registered passengers
        userService.register("(093)306-01-13", "0933060113", "Andrey", new Address("ул. Ентузиастов", "27"));
        userService.register("(093)306-56-89", "0933065689", "Vasia", new Address("пр. Харьковский", "103"));
        userService.register("(097)506-61-89", "0975066189", "Kolya", new Address("пр. Победы", "103"));

        //default registered drivers
        userService.register("(063)306-01-13", "0633060113", "Lihach", new Car("AA3254AE", "Daewoo Lanos", "Gray"));
        userService.register("(066)506-61-89", "0665066189", "Borodach", new Car("AA7777AE", "Toyota Camry", "Black"));
        userService.register("(073)636-65-89", "0736366589", "Macho", new Car("AA5555AE", "Porshe Cayenne", "Gold"));
    }

    private void initDaoWithSomeOrders() {

        orderService.createOrder("(093)306-01-13", "Andrey", new Address("ул. Ентузиастов", "27"), new Address("ул. Российская", "82"));
        orderService.createOrder("(093)306-01-14", "Andrii", new Address("ул. Хрещатик", "1"), new Address("ул. Бориспольская", "4"));
        orderService.createOrder("(093)306-01-15", "Andrii", new Address("ул. Ентузиастов", "27"), new Address("ул. Княжий Затон", "3"));
    }

    public void showChooseOrder() throws RemoteConnectionError {

        initOrderList();
        mainWindowController.setMainAnchorPane(chooseOrderLayout);
    }

    public void showChooseOrderInfo() {

        mainWindowController.setMainAnchorPane(chooseOrderInfoLayout);
    }

    public void showCreateOrder() {
        mainWindowController.setMainAnchorPane(createOrderFormLayout);
    }

    public void showDriverRegisterForm() {
        mainWindowController.setMainAnchorPane(driverRegisterFormLayout);
    }

    public void showOrderStatus() {
        mainWindowController.setMainAnchorPane(orderStatusLayout);
    }

    public void showPassangerRegisterForm() {
        mainWindowController.setMainAnchorPane(passangerRegisterFormLayout);
    }

    public void showEnterWindow() {
        mainWindowController.setMainAnchorPane(enterWindowLayout);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public UserService getUserService() {
        return userService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public ObservableList<TableViewOrder> getOrderList() {
        return orderList;
    }
}
