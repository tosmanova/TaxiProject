package ua.taxi.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ua.taxi.StartApp;
import ua.taxi.model.Order;
import ua.taxi.model.OrderStatus;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrii on 4/28/2016.
 */
public class MainWindowCntrl implements Controller {

    private StartApp startApp;

    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private Label allUsersLabel;
    @FXML
    private Label passangersLabel;
    @FXML
    private Label driversLabel;
    @FXML
    private Label ordersLabel;
    @FXML
    private Label newLabel;
    @FXML
    private Label inProgresLabel;
    @FXML
    private Label doneLabel;


    @FXML
    private void initialize() {
        AnchorPane initPane = new AnchorPane();
        mainAnchorPane.getChildren().add(initPane);
    }

    public void setMainAnchorPane(AnchorPane anchorPane) {
        mainAnchorPane.getChildren().set(0, anchorPane);
        mainAnchorPane.setBottomAnchor(anchorPane, 0.0);
        mainAnchorPane.setLeftAnchor(anchorPane, 0.0);
        mainAnchorPane.setRightAnchor(anchorPane, 0.0);
        mainAnchorPane.setTopAnchor(anchorPane, 0.0);
    }

    public void updateOrderCounters() {
        Map<OrderStatus, Integer> counterMap = startApp.getOrderService().getStatusCounterMap();
        ordersLabel.setText(String.valueOf(startApp.getOrderService().ordersRegisteredQuantity()));
        newLabel.setText(String.valueOf(counterMap.get(OrderStatus.NEW)));
        inProgresLabel.setText(String.valueOf(counterMap.get(OrderStatus.IN_PROGRESS)));
        doneLabel.setText(String.valueOf(counterMap.get(OrderStatus.DONE)));
    }

    public void updateUserCounters() {

        allUsersLabel.setText(String.valueOf(startApp.getUserService().driverRegisteredQuantity()
                + startApp.getUserService().passangerRegisteredQuantity()));
        passangersLabel.setText(String.valueOf(startApp.getUserService().passangerRegisteredQuantity()));
        driversLabel.setText(String.valueOf(startApp.getUserService().driverRegisteredQuantity()));
    }

    @Override
    public void setStartApp(StartApp startApp) {
        this.startApp = startApp;
    }
}
