package ua.taxi.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ua.taxi.StartApp;
import ua.taxi.base.exception.RemoteConnectionError;
import ua.taxi.base.model.order.OrderStatus;
import ua.taxi.base.model.order.OrderValidateMessage;
import ua.taxi.base.model.user.Driver;
import ua.taxi.base.model.user.Passenger;
import ua.taxi.base.model.user.UserValidateMessage;
import ua.taxi.base.utils.Utils;

/**
 * Created by Andrii on 4/28/2016.
 */
public class EnterWindowCntrl implements Controller {

    private boolean isPassanger;
    private StartApp startApp;
    private CreateOrderFormCntrl createOrderFormCntrl;
    private ChooseOrderCntrl chooseOrderCntrl;
    private ChooseOrderInfoCntrl chooseOrderInfoCntrl;
    private OrderStatusCntrl orderStatusCntrl;
    private MainWindowCntrl mainWindowCntrl;
    private final ToggleGroup togleGroup = new ToggleGroup();

    @FXML
    private Label anotation;
    @FXML
    private TextField phoneNumber;
    @FXML
    private PasswordField password;
    @FXML
    private RadioButton passangerRadioButton;
    @FXML
    private RadioButton driverRadioButton;


    @FXML
    private void initialize() {
        driverRadioButton.setToggleGroup(togleGroup);
        driverRadioButton.setSelected(true);
        isPassanger = false;
        passangerRadioButton.setToggleGroup(togleGroup);
        anotation.setText("It`s an imitation of online taxi Program.\n" +
                "You can enter as Passenger and create order to go\n" +
                "from street A to street B. If you enter as a Driver\n" +
                "you can see the orderList and choose order.\n" +
                " Program use GoogleAPI for calculate distance");

        phoneNumber.focusedProperty().addListener(
                (observable, oldValue, newValue) -> phoneFieldValidate(oldValue));
    }

    public void clear() {
        password.clear();
        phoneNumber.clear();
    }

    private boolean phoneFieldValidate(boolean oldValue) {
        String validatedPhone = Utils.phoneValidate(phoneNumber.getText());
        if (oldValue) {
            if (validatedPhone != null) {
                phoneNumber.setText(validatedPhone);
                return true;
            }
        }
        return false;
    }

    private boolean textFieldsValidate() {

        String errorContent = "";
        if (!phoneFieldValidate(true)) {
            errorContent = "Phone number not valid \n";
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
    private void passangerRadioButtonPress() {
        isPassanger = true;
    }

    @FXML
    private void driverRadioButtonPress() {
        isPassanger = false;
    }

    @FXML
    private void goNow() {

        startApp.showCreateOrder();
    }

    @FXML
    private void register() {
        if (isPassanger) {
            startApp.showPassangerRegisterForm();
            clear();
        } else {
            startApp.showDriverRegisterForm();
            clear();
        }
    }

    @FXML
    private void login() throws RemoteConnectionError {
        if (textFieldsValidate()) {

            UserValidateMessage validateMessage = startApp.getUserService().login(phoneNumber.getText(), password.getText());

            if (validateMessage.getState()) {
                if (validateMessage.getUser() instanceof Driver) {
                    OrderValidateMessage orderInProgresValidate = startApp.getOrderService()
                            .getOrderInProgresByDriverPhone(validateMessage.getUser().getPhone());
                    if (orderInProgresValidate.isState()) {
                        chooseOrderInfoCntrl.setActiveOrder(orderInProgresValidate.getOrder());
                        startApp.showChooseOrderInfo();
                        mainWindowCntrl.showGoogleMapRoute(orderInProgresValidate.getOrder().getFrom(), orderInProgresValidate.getOrder().getTo());
                    } else {
                        chooseOrderCntrl.setLogedDriver((Driver) validateMessage.getUser());
                        startApp.showChooseOrder();
                        clear();
                    }
                } else {

                    //If we have order with phone of logged user, we go to OrderStatus
                    OrderValidateMessage orderValidateMessage = startApp.getOrderService().getOrder(phoneNumber.getText());
                    if (orderValidateMessage.isState() && (orderValidateMessage.getOrder().getOrderStatus() != OrderStatus.DONE)) {
                        orderStatusCntrl.setActiveOrder(orderValidateMessage.getOrder());
                        startApp.showOrderStatus();
                        mainWindowCntrl.showGoogleMapRoute(orderValidateMessage.getOrder().getFrom(), orderValidateMessage.getOrder().getTo());
                        clear();
                    } else {
                        createOrderFormCntrl.setLogedPassenger((Passenger) validateMessage.getUser());
                        startApp.showCreateOrder();
                        clear();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(startApp.getPrimaryStage());
                alert.setTitle(validateMessage.getTitle());
                alert.setContentText(validateMessage.getBody());
                alert.showAndWait();
            }
        }
    }

    @Override
    public void setStartApp(StartApp startApp) {
        this.startApp = startApp;
    }

    public void setCreateOrderFormCntrl(CreateOrderFormCntrl createOrderFormCntrl) {
        this.createOrderFormCntrl = createOrderFormCntrl;
    }

    public void setChooseOrderCntrl(ChooseOrderCntrl chooseOrderCntrl) {
        this.chooseOrderCntrl = chooseOrderCntrl;
    }

    public void setOrderStatusCntrl(OrderStatusCntrl orderStatusCntrl) {
        this.orderStatusCntrl = orderStatusCntrl;
    }

    public void setChooseOrderInfoCntrl(ChooseOrderInfoCntrl chooseOrderInfoCntrl) {
        this.chooseOrderInfoCntrl = chooseOrderInfoCntrl;
    }

    public void setMainWindowCntrl(MainWindowCntrl mainWindowCntrl) {
        this.mainWindowCntrl = mainWindowCntrl;
    }
}
