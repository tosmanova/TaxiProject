package ua.taxi.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import ua.taxi.StartApp;
import ua.taxi.exception.RemoteConnectionError;
import ua.taxi.model.Order.Address;
import ua.taxi.model.User.Passanger;
import ua.taxi.model.User.UserValidateMessage;
import ua.taxi.utils.Utils;

/**
 * Created by Andrii on 4/28/2016.
 */
public class PassangerRegisterFormCntrl implements Controller {

    private StartApp startApp;
    private CreateOrderFormCntrl createOrderFormCntrl;
    private MainWindowCntrl mainWindowCntrl;
    private Address homeAddress;
    private boolean isOpenForEdit;

    @FXML
    private TextField phoneNumberField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField homeAddressField;
    @FXML
    private TextField birthdayField;
    @FXML
    private TextField emailField;
    @FXML
    private ImageView phoneValidateImage;
    @FXML
    private ImageView passwordValidateImage;
    @FXML
    private ImageView nameValidateImage;
    @FXML
    private ImageView addressValidateImage;
    @FXML
    private Button createButton;

    @FXML
    private void initialize() {
        clear();
        phoneNumberField.focusedProperty().addListener(
                (observable, oldValue, newValue) -> phoneValidate(oldValue));
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

    public void clear() {
        phoneNumberField.clear();
        passwordField.clear();
        nameField.clear();
        homeAddressField.clear();
        phoneNumberField.clear();
        birthdayField.clear();
        isOpenForEdit = false;
        createButton.setText("Create");
    }

    public void setOpenForEdit(Passanger passanger) {
        isOpenForEdit = true;
        createButton.setText("Change");
        phoneNumberField.setText(passanger.getPhone());
        phoneNumberField.editableProperty().set(false);
        passwordField.setText(passanger.getPass());
        nameField.setText(passanger.getName());
        homeAddressField.setText(passanger.getHomeAdress().toString());
    }

    private boolean textFieldsValidate() {

        String errorContent = "";
        if (!phoneValidate(true)) {
            errorContent = "Phone number not valid \n";
        }

        homeAddress = Utils.addressValidate(homeAddressField.getText());
        if (homeAddress == null) {
            errorContent += "You entered not valid homeAddress \n";
        }

        if (nameField.getText().length() < 2) {
            errorContent += "Please enter your Name\n";
        }

        if (passwordField.getText().length() < 5) {
            errorContent += "You password length is not enough\n";
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
    private void create() throws RemoteConnectionError {

        if (textFieldsValidate()) {
            UserValidateMessage userValidate;
            if (isOpenForEdit) {
                userValidate = startApp.getUserService().changePassanger(phoneNumberField.getText()
                        , passwordField.getText()
                        , nameField.getText()
                        , homeAddress);
            } else {

                userValidate = startApp.getUserService().register(
                        phoneNumberField.getText()
                        , passwordField.getText()
                        , nameField.getText()
                        , homeAddress);
            }
            if (userValidate.getState()) {
                createOrderFormCntrl.setLogedPassenger((Passanger) userValidate.getUser());
                if(!isOpenForEdit) mainWindowCntrl.updateUserCounters();
                startApp.showCreateOrder();
                clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(startApp.getPrimaryStage());
                alert.setTitle(userValidate.getTitle());
                alert.setContentText(userValidate.getBody());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void back() {
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
