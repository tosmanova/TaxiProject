package ua.taxi.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ua.taxi.StartApp;
import ua.taxi.base.exception.RemoteConnectionError;
import ua.taxi.base.model.user.Car;
import ua.taxi.base.model.user.Driver;
import ua.taxi.base.model.user.UserValidateMessage;
import ua.taxi.base.utils.Utils;

/**
 * Created by Andrii on 4/28/2016.
 */
public class DriverRegisterFormCntrl implements Controller {

    private StartApp startApp;
    private ChooseOrderCntrl chooseOrderCntrl;
    private MainWindowCntrl mainWindowCntrl;

    @FXML
    private TextField phoneNumberField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField carModelField;
    @FXML
    private TextField carNumberField;
    @FXML
    private TextField carColorField;
    @FXML
    private TextField birthdayField;
    @FXML
    private TextField emailField;
    @FXML
    private Button createButton;

    private boolean isOpenForEdit;

    @FXML
    private void initialize() {
        clear();
        phoneNumberField.focusedProperty().addListener(
                (observable, oldValue, newValue) -> phoneValidate(oldValue));
    }

    public void clear() {
        phoneNumberField.clear();
        passwordField.clear();
        nameField.clear();
        birthdayField.clear();
        emailField.clear();
        carColorField.clear();
        carModelField.clear();
        carNumberField.clear();
        isOpenForEdit = false;
        createButton.setText("Create");
    }

    public void setOpenForEdit(Driver driver) {

        isOpenForEdit = true;
        createButton.setText("Change");
        phoneNumberField.setText(driver.getPhone());
        phoneNumberField.editableProperty().set(false);
        passwordField.setText(driver.getPass());
        nameField.setText(driver.getName());
        carNumberField.setText(driver.getCar().getNumber());
        carModelField.setText(driver.getCar().getModel());
        carColorField.setText(driver.getCar().getColor());

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

        if (nameField.getText().length() < 2) {
            errorContent += "Please enter your Name\n";
        }

        if (passwordField.getText().length() < 5) {
            errorContent += "You password length is not enough\n";
        }

        if (carColorField.getText().length() < 2) {
            errorContent += "Please enter correct Car color\n";
        }

        if (carModelField.getText().length() < 2) {
            errorContent += "Please enter correct Car Model\n";
        }

        if (carNumberField.getText().length() < 4) {
            errorContent += "Please enter correct CarNumber\n";
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

                userValidate = startApp.getUserService().changeDriver(
                        phoneNumberField.getText()
                        , passwordField.getText()
                        , nameField.getText()
                        , new Car(carNumberField.getText(), carModelField.getText(), carColorField.getText()));

            } else {

                userValidate = startApp.getUserService().register(
                        phoneNumberField.getText()
                        , passwordField.getText()
                        , nameField.getText()
                        , new Car(carNumberField.getText(), carModelField.getText(), carColorField.getText()));
            }

            if (userValidate.getState()) {
                chooseOrderCntrl.setLogedDriver((Driver) userValidate.getUser());
                if (!isOpenForEdit) mainWindowCntrl.updateUserCounters();
                startApp.showChooseOrder();
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

    public void setChooseOrderCntrl(ChooseOrderCntrl chooseOrderCntrl) {
        this.chooseOrderCntrl = chooseOrderCntrl;
    }

    public void setMainWindowCntrl(MainWindowCntrl mainWindowCntrl) {
        this.mainWindowCntrl = mainWindowCntrl;
    }


}
