package ua.taxi.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ua.taxi.StartApp;
import ua.taxi.model.Order;
import ua.taxi.model.OrderStatus;

import java.util.Collection;

/**
 * Created by Andrii on 5/9/2016.
 */
public class ChooseOrderInfoCntrl implements Controller{

    private StartApp startApp;
    private Order activeOrder;
    private MainWindowCntrl mainWindowCntrl;

    @FXML
    private Label goFromLabel;
    @FXML
    private Label goToLabel;
    @FXML
    private Label distanceLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label priceLabel;

    public void setActiveOrder(Order activeOrder) {

        this.activeOrder = activeOrder;
        goFromLabel.setText(activeOrder.getFrom().toString());
        goToLabel.setText(activeOrder.getTo().toString());
      //  distanceLabel.setText(activeOrder.getDistance());
        phoneLabel.setText(activeOrder.getUserPhone());
        nameLabel.setText(activeOrder.getUserName());
      //  timeLabel.setText(activeOrder.getCreateTimeAsString());
       // priceLabel.setText(activeOrder.getPrice());
    }

    @FXML
    private void exit(){
        startApp.showEnterWindow();
    }

    @FXML
    private void complete(){
        startApp.getOrderService().changeOrderStatus(activeOrder.getUserPhone(), OrderStatus.DONE);
        mainWindowCntrl.updateOrderCounters();
        startApp.showEnterWindow();
    }



    @Override
    public void setStartApp(StartApp startApp) {
        this.startApp = startApp;
    }

    public void setMainWindowCntrl(MainWindowCntrl mainWindowCntrl) {
        this.mainWindowCntrl = mainWindowCntrl;
    }

}
