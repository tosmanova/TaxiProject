package ua.taxi.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ua.taxi.StartApp;
import ua.taxi.exception.RemoteConnectionError;
import ua.taxi.model.Order.Address;
import ua.taxi.model.Order.OrderStatus;
import ua.taxi.model.geolocation.Location;
import ua.taxi.model.geolocation.GoogleMapsAPI;
import ua.taxi.model.geolocation.GoogleMapsAPIImpl;

import java.util.Map;

/**
 * Created by Andrii on 4/28/2016.
 */
public class MainWindowCntrl implements Controller {

    private StartApp startApp;

    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private AnchorPane leftAnchorPane;
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
        AnchorPane initPane2 = new AnchorPane();
        mainAnchorPane.getChildren().add(initPane);
        leftAnchorPane.getChildren().add(initPane2);
    }

    public void setMainAnchorPane(AnchorPane anchorPane) {
        mainAnchorPane.getChildren().set(0, anchorPane);
        mainAnchorPane.setBottomAnchor(anchorPane, 0.0);
        mainAnchorPane.setLeftAnchor(anchorPane, 0.0);
        mainAnchorPane.setRightAnchor(anchorPane, 0.0);
        mainAnchorPane.setTopAnchor(anchorPane, 0.0);
    }

    public void setLeftAnchorPane(AnchorPane anchorPane) {
        leftAnchorPane.getChildren().set(0, anchorPane);
        leftAnchorPane.setBottomAnchor(anchorPane, 0.0);
        leftAnchorPane.setLeftAnchor(anchorPane, 0.0);
        leftAnchorPane.setRightAnchor(anchorPane, 0.0);
        leftAnchorPane.setTopAnchor(anchorPane, 0.0);
    }

    public void showGoogleMapRoute(Address from, Address to) {

        GoogleMapCntrl googleMapCntrl = new GoogleMapCntrl();

        GoogleMapsAPI googleMapsAPI = new GoogleMapsAPIImpl();
        Location fromLocation = googleMapsAPI.findLocation("Ukraine", "Kiev", from.getStreet(), from.getHouseNum());
        Location toLocation2 = googleMapsAPI.findLocation("Ukraine", "Kiev", to.getStreet(), to.getHouseNum());

        googleMapCntrl.displayRoute(fromLocation.getLat(), fromLocation.getLng(),
                toLocation2.getLat(), toLocation2.getLng());

        AnchorPane googlePan = new AnchorPane();
        googlePan.getChildren().add(googleMapCntrl);
        googlePan.setPrefWidth(600);
        setLeftAnchorPane(googlePan);
        startApp.getPrimaryStage().setWidth(1360);
        startApp.getPrimaryStage().setHeight(650);
        startApp.getPrimaryStage().show();
    }

    public void hideGoogleMap() {

        AnchorPane clearPan = new AnchorPane();
        clearPan.setPrefWidth(20);
        setLeftAnchorPane(clearPan);
        startApp.getPrimaryStage().setWidth(670);
        startApp.getPrimaryStage().setHeight(450);
        startApp.getPrimaryStage().show();
    }


    public void updateOrderCounters() throws RemoteConnectionError {
        Map<OrderStatus, Integer> counterMap = startApp.getOrderService().getStatusCounterMap();
        ordersLabel.setText(String.valueOf(startApp.getOrderService().ordersRegisteredQuantity()));
        newLabel.setText(String.valueOf(counterMap.get(OrderStatus.NEW)));
        inProgresLabel.setText(String.valueOf(counterMap.get(OrderStatus.IN_PROGRESS)));
        doneLabel.setText(String.valueOf(counterMap.get(OrderStatus.DONE)));
    }

    public void updateUserCounters() throws RemoteConnectionError {

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
