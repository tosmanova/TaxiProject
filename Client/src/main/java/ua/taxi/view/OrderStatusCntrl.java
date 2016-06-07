package ua.taxi.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ua.taxi.StartApp;
import ua.taxi.model.Driver;
import ua.taxi.model.Order;
import ua.taxi.model.OrderStatus;
import ua.taxi.model.Passanger;

import java.time.format.DateTimeFormatter;

/**
 * Created by Andrii on 4/28/2016.
 */
public class OrderStatusCntrl implements Controller {
    private StartApp startApp;
    private Order activeOrder;
    private CreateOrderFormCntrl createOrderFormCntrl;
    private MainWindowCntrl mainWindowCntrl;


    @FXML
    private Label goFromLabel;
    @FXML
    private Label goToLabel;
    @FXML
    private Label distanceLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label yourDriverLabel;
    @FXML
    private Label yourCarLabel;
    @FXML
    private Label orderCreated;
    @FXML
    private TextField addToPriceField;


    public void clear() {
        addToPriceField.clear();
    }

    public void setActiveOrder(Order activeOrder) {

        this.activeOrder = activeOrder;
        goFromLabel.setText(activeOrder.getFrom().toString());
        goToLabel.setText(activeOrder.getTo().toString());
     //   distanceLabel.setText(activeOrder.getDistance());
      //  priceLabel.setText(activeOrder.getPrice());

        if (activeOrder.getOrderStatus() == OrderStatus.IN_PROGRESS) {
            String driverPhone = activeOrder.getDriverPhone();
            Driver driver = (Driver) startApp.getUserService().getUser(driverPhone).getUser();
            yourDriverLabel.setText(driver.getName() + " тел: " + driver.getPhone());
            yourCarLabel.setText("Your car: " + driver.getCar().toString());
        } else {
            yourDriverLabel.setText("Waiting for Driver");
            yourCarLabel.setText("Waiting for Driver");
        }
        orderCreated.setText(activeOrder.getCreateTime().format(DateTimeFormatter.ofPattern("HH.mm.ss a")));
    }

    @FXML
    private void addToPrice() {
/*
        String addPriceField = addToPriceField.getText();
        if (addPriceField.matches("^\\d+$")) {
            activeOrder.setPrice(Double.parseDouble(activeOrder.getPrice().replaceAll("[\\D]+", "")) + Double.parseDouble(addPriceField));
            priceLabel.setText(activeOrder.getPrice());
            startApp.getOrderService().changeOrder(activeOrder.getUserPhone(), activeOrder);
        }
        startApp.initOrderList();*/
    }

    @FXML
    private void changeOrder() {
        createOrderFormCntrl.setOpenForEdit(activeOrder);
        startApp.showCreateOrder();
        clear();
    }

    @FXML
    private void cancelOrder() {
        startApp.getOrderService().cancelOrder(activeOrder.getUserPhone());
        mainWindowCntrl.updateOrderCounters();
        startApp.showEnterWindow();
        startApp.initOrderList();
        clear();
    }

    @FXML
    private void exit() {
        startApp.showEnterWindow();
        clear();
    }

    @Override
    public void setStartApp(StartApp startApp) {
        this.startApp = startApp;
    }

    public void setCreateOrderFormCntrl(CreateOrderFormCntrl createOrderFormCntrl) {
        this.createOrderFormCntrl = createOrderFormCntrl;
    }

    public void setMainWindowCntrl(MainWindowCntrl mainWindowCntrl) {
        this.mainWindowCntrl = mainWindowCntrl;
    }
}
