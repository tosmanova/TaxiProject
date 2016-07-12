package ua.taxi.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ua.taxi.StartApp;
import ua.taxi.base.exception.RemoteConnectionError;
import ua.taxi.base.model.user.Driver;
import ua.taxi.base.model.order.Order;
import ua.taxi.base.model.order.OrderStatus;
import ua.taxi.base.utils.DateUtils;
import ua.taxi.base.utils.Utils;

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
        distanceLabel.setText(Utils.distanceFormat(activeOrder.getDistance()));
        priceLabel.setText(Utils.priceFormat(activeOrder.getPrice()));

        if (activeOrder.getOrderStatus() == OrderStatus.IN_PROGRESS) {
            String driverPhone = activeOrder.getDriverPhone();
            Driver driver = (Driver) startApp.getUserService().getUser(driverPhone).getUser();
            yourDriverLabel.setText(driver.getName() + " тел: " + driver.getPhone());
            yourCarLabel.setText("Your car: " + driver.getCar().toString());
        } else {
            yourDriverLabel.setText("Waiting for Driver");
            yourCarLabel.setText("Waiting for Driver");
        }
        orderCreated.setText(DateUtils.HHmm(activeOrder.getCreateTime()));
    }

    @FXML
    private void addToPrice() {

        String addPriceField = addToPriceField.getText();
        if (addPriceField.matches("^\\d+$")) {
            activeOrder.setPrice(activeOrder.getPrice() + Double.parseDouble(addPriceField));
            priceLabel.setText(Utils.priceFormat(activeOrder.getPrice()));
            startApp.getOrderService().changeOrder(activeOrder.getUserPhone(), activeOrder);
        }
        startApp.initOrderList();
    }

    @FXML
    private void changeOrder() {
        createOrderFormCntrl.setOpenForEdit(activeOrder);
        startApp.showCreateOrder();
        clear();
    }

    @FXML
    private void cancelOrder() throws RemoteConnectionError {
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
