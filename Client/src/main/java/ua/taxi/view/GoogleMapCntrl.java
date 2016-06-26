package ua.taxi.view;

import javafx.beans.property.ReadOnlyDoubleProperty;

import javafx.concurrent.Worker;
import javafx.scene.Parent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

/**
 * Created by andrii on 20.06.16.
 */


public class GoogleMapCntrl extends Parent {

    private JSObject doc;

    private WebView webView;
    private WebEngine webEngine;
    private boolean ready;

    public GoogleMapCntrl() {
        initMap();
        initCommunication();
        getChildren().add(webView);
        // setMarkerPosition(0, 0);
        setMapCenter(0, 0);
        switchTerrain();
    }

    private void initMap() {
        webView = new WebView();
        webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("/googleMap.html").toExternalForm());
        ready = false;
        webEngine.getLoadWorker().stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                ready = true;
            }
        });
    }

    private void initCommunication() {
        webEngine.getLoadWorker().stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                doc = (JSObject) webEngine.executeScript("window");
                doc.setMember("app", GoogleMapCntrl.this);
            }
        });
    }

    private void invokeJS(final String str) {
        if (ready) {
            doc.eval(str);
        } else {
            webEngine.getLoadWorker().stateProperty().addListener((observableValue, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    doc.eval(str);
                }
            });
        }
    }

    public void setMarkerPosition(double lat, double lng) {
        String sLat = Double.toString(lat);
        String sLng = Double.toString(lng);
        invokeJS("setMarkerPosition(" + sLat + ", " + sLng + ")");
    }

    public void setMapCenter(double lat, double lng) {
        String sLat = Double.toString(lat);
        String sLng = Double.toString(lng);
        invokeJS("setMapCenter(" + sLat + ", " + sLng + ")");
    }

    public void displayRoute(double startLat, double startLng, double finishLat, double finishLng) {

        String sLat = Double.toString(startLat);
        String sLng = Double.toString(startLng);
        String fLat = Double.toString(finishLat);
        String fLng = Double.toString(finishLng);

        invokeJS("displayRoute(" + sLat + "," + sLng + "," + fLat + "," + fLng + ")");

    }

    public void switchSatellite() {
        invokeJS("switchSatellite()");
    }

    public void switchRoadmap() {
        invokeJS("switchRoadmap()");
    }

    public void switchHybrid() {
        invokeJS("switchHybrid()");
    }

    public void switchTerrain() {
        invokeJS("switchTerrain()");
    }

    public void startJumping() {
        invokeJS("startJumping()");
    }

    public void stopJumping() {
        invokeJS("stopJumping()");
    }

    public void setHeight(double h) {
        webView.setPrefHeight(h);
    }

    public void setWidth(double w) {
        webView.setPrefWidth(w);
    }

    public ReadOnlyDoubleProperty widthProperty() {
        return webView.widthProperty();
    }


}


