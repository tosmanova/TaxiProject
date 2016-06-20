package ua.taxi.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ua.taxi.StartApp;
import ua.taxi.exception.RemoteConnectionError;
import ua.taxi.model.Order.Order;
import ua.taxi.model.Order.OrderStatus;
import ua.taxi.utils.DateUtils;
import ua.taxi.utils.Utils;

/**
 * Created by Andrii on 5/9/2016.
 */
public class ChooseOrderInfoCntrl implements Controller{

    private StartApp startApp;
    private Order activeOrder;
    private MainWindowCntrl mainWindowCntrl;
    private EnterWindowCntrl enterWindowCntrl;

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
        distanceLabel.setText(Utils.distanceFormat(activeOrder.getDistance()));
        phoneLabel.setText(activeOrder.getUserPhone());
        nameLabel.setText(activeOrder.getUserName());
        timeLabel.setText(DateUtils.HHmm(activeOrder.getCreateTime()));
        priceLabel.setText(Utils.priceFormat(activeOrder.getPrice()));
    }

    @FXML
    private void exit(){
        enterWindowCntrl.clear();
        startApp.showEnterWindow();
    }

    @FXML
    private void complete() throws RemoteConnectionError {
        startApp.getOrderService().changeOrderStatus(activeOrder.getUserPhone(), OrderStatus.DONE);
        mainWindowCntrl.updateOrderCounters();
        enterWindowCntrl.clear();
        startApp.showEnterWindow();
    }

    @Override
    public void setStartApp(StartApp startApp) {
        this.startApp = startApp;
    }

    public void setMainWindowCntrl(MainWindowCntrl mainWindowCntrl) {
        this.mainWindowCntrl = mainWindowCntrl;
    }

    public void setEnterWindowCntrl(EnterWindowCntrl enterWindowCntrl) {
        this.enterWindowCntrl = enterWindowCntrl;
    }
}
