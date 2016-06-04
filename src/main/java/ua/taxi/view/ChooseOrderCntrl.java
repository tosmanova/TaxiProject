package ua.taxi.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ua.taxi.StartApp;
import ua.taxi.model.Driver;
import ua.taxi.model.Order;
import ua.taxi.model.OrderStatus;


/**
 * Created by Andrii on 4/28/2016.
 */
public class ChooseOrderCntrl implements Controller {

    private StartApp startApp;
    private DriverRegisterFormCntrl driverRegisterFormCntrl;
    private ChooseOrderInfoCntrl chooseOrderInfoCntrl;
    private MainWindowCntrl mainWindowCntrl;

    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, String> fromColumn;
    @FXML
    private TableColumn<Order, String> toColumn;
    @FXML
    private TableColumn<Order, String> priceColumn;
    @FXML
    private TableColumn<Order, String> createdColumn;
    @FXML
    private TableColumn<Order, String> distanceColumn;

    @FXML
    private Label driverNameField;
    @FXML
    private Label carLabel;

    private Driver driver;

    @FXML
    private void initialize() {

        fromColumn.setCellValueFactory(cellData -> cellData.getValue().fromProperty());
        toColumn.setCellValueFactory(cellData -> cellData.getValue().toProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        createdColumn.setCellValueFactory(cellData -> cellData.getValue().createTimeAsStringProperty());
        distanceColumn.setCellValueFactory(cellData -> cellData.getValue().distanceProperty());

    }

    public void clear() {
    }

    public void setLogedDriver(Driver driver) {
        this.driver = driver;
        driverNameField.setText(driver.getName());
        carLabel.setText(driver.getCar().toString());
    }

    @FXML
    private void getOrder() {


        Order order = orderTable.getSelectionModel().getSelectedItem();
        order.setDriverPhone(driver.getPhone());
        chooseOrderInfoCntrl.setActiveOrder(order);
        startApp.getOrderService().changeOrderStatus(order.getUserPhone(), OrderStatus.IN_PROGRESS);
        mainWindowCntrl.updateOrderCounters();
        startApp.getOrderService().changeOrder(order.getUserPhone(), order);
        startApp.showChooseOrderInfo();
    }

    @FXML
    private void addToBlackList() {

    }

    @FXML
    private void Cancel() {
        startApp.showEnterWindow();
    }

    @FXML
    private void editAccount() {
        driverRegisterFormCntrl.setOpenForEdit(driver);
        startApp.showDriverRegisterForm();
    }

    @Override
    public void setStartApp(StartApp startApp) {
        this.startApp = startApp;
        orderTable.setItems(startApp.getOrderList());
    }

    public void setDriverRegisterFormCntrl(DriverRegisterFormCntrl driverRegisterFormCntrl) {
        this.driverRegisterFormCntrl = driverRegisterFormCntrl;
    }

    public void setChooseOrderInfoCntrl(ChooseOrderInfoCntrl chooseOrderInfoCntrl) {
        this.chooseOrderInfoCntrl = chooseOrderInfoCntrl;
    }

    public void setMainWindowCntrl(MainWindowCntrl mainWindowCntrl) {
        this.mainWindowCntrl = mainWindowCntrl;
    }
}
