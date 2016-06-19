package ua.taxi.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ua.taxi.StartApp;
import ua.taxi.exception.RemoteConnectionError;
import ua.taxi.model.Order.Address;
import ua.taxi.model.Order.Order;
import ua.taxi.model.Order.OrderValidateMessage;
import ua.taxi.model.User.Passanger;
import ua.taxi.utils.Utils;

/**
 * Created by Andrii on 4/28/2016.
 */
public class CreateOrderFormCntrl implements Controller {

    private StartApp startApp;
    private MainWindowCntrl mainWindowCntrl;
    private OrderStatusCntrl orderStatusCntrl;
    private PassangerRegisterFormCntrl passangerRegisterFormCntrl;

    private Passanger passenger;
    private Address goFromAddress;
    private Address goToAddress;
    private boolean isOpenForEdit;

    @FXML
    private Label passangerNameLabel;
    @FXML
    private Label distanceLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField goFromField;
    @FXML
    private TextField goToField;
    @FXML
    private Button createButton;

    @FXML
    private void initialize() {
        phoneNumberField.focusedProperty().addListener(
                (observable, oldValue, newValue) -> phoneValidate(oldValue));
    }

    public void clear() {
        passangerNameLabel.setText(" ");
        distanceLabel.setText(" ");
        priceLabel.setText(" ");
        phoneNumberField.clear();
        nameField.clear();
        goFromField.clear();
        goToField.clear();
    }

    public void setOpenForEdit(Order order) {
        isOpenForEdit = true;
        createButton.setText("Change");
        setLogedPassenger(passenger);
        goFromField.setText(order.getFrom().toString());
        goToField.setText(order.getTo().toString());
        priceLabel.setText(String.valueOf(order.getPrice()));
    }

    public void setLogedPassenger(Passanger passenger) {

        this.passenger = passenger;
        phoneNumberField.setText(passenger.getPhone());
        phoneNumberField.editableProperty().set(false);

        nameField.setText(passenger.getName());
        nameField.editableProperty().set(false);

        goFromField.setText(passenger.getHomeAdress().toString());
        passangerNameLabel.setText(passenger.getName());
    }

    private boolean phoneValidate(boolean state) {
        if (state) {
            if (Utils.phoneValidate(phoneNumberField.getText()) != null) {
                phoneNumberField.setText(Utils.phoneValidate(phoneNumberField.getText()));
                return true;
            }
        }
        return false;
    }

    private boolean textFieldsValidate() {

        String errorContent = "";
        if (!phoneValidate(true)) {
            errorContent = "Phone number not valid \n";
        }

        goFromAddress = Utils.addressValidate(goFromField.getText());
        goToAddress = Utils.addressValidate(goToField.getText());
        if (goFromAddress == null || goToAddress == null) {
            errorContent += "You entered not valid address \n";
        }

        if (nameField.getText().length() < 2) {
            errorContent += "Please enter your Name\n";
        }

        if (!errorContent.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(startApp.getPrimaryStage());
            alert.setTitle("Input error");
            alert.setContentText(errorContent);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    private void calculateOrder() throws RemoteConnectionError {

        if (textFieldsValidate()) {
            distanceLabel.setText(Utils.distanceFormat(startApp.getOrderService().getDistance(goFromAddress, goToAddress)));
            priceLabel.setText(Utils.priceFormat(startApp.getOrderService().getPrice(goFromAddress, goToAddress)));
        }
    }

    @FXML
    private void create() throws RemoteConnectionError {

        if (textFieldsValidate()) {
            OrderValidateMessage orderValidateMessage;
            if (isOpenForEdit) {
                orderValidateMessage = startApp.getOrderService().changeOrder(
                        phoneNumberField.getText(), nameField.getText(), goFromAddress, goToAddress);
            } else {
                orderValidateMessage = startApp.getOrderService().createOrder(
                        phoneNumberField.getText(), nameField.getText(), goFromAddress, goToAddress);
            }

            if (orderValidateMessage.isState()) {
                orderStatusCntrl.setActiveOrder(orderValidateMessage.getOrder());
                if (!isOpenForEdit) mainWindowCntrl.updateOrderCounters();
                startApp.initOrderList();
                startApp.showOrderStatus();
                clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(startApp.getPrimaryStage());
                alert.setTitle(orderValidateMessage.getTitle());
                alert.setContentText(orderValidateMessage.getBody());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void cancel() {
        startApp.showEnterWindow();
        clear();
    }

    @FXML
    private void replace() {
        String temp = goFromField.getText();
        goFromField.setText(goToField.getText());
        goToField.setText(temp);
    }

    @FXML
    private void editAccount() {
        passangerRegisterFormCntrl.setOpenForEdit(passenger);
        startApp.showPassangerRegisterForm();
        clear();
    }

    @Override
    public void setStartApp(StartApp startApp) {
        this.startApp = startApp;
    }

    public void setMainWindowCntrl(MainWindowCntrl mainWindowCntrl) {
        this.mainWindowCntrl = mainWindowCntrl;
    }

    public void setOrderStatusCntrl(OrderStatusCntrl orderStatusCntrl) {
        this.orderStatusCntrl = orderStatusCntrl;
    }

    public void setPassangerRegisterFormCntrl(PassangerRegisterFormCntrl passangerRegisterFormCntrl) {
        this.passangerRegisterFormCntrl = passangerRegisterFormCntrl;
    }
}
