package ua.taxi.model;

import java.io.Serializable;

/**
 * Created by serhii on 23.04.16.
 */
public enum OrderStatus implements Serializable{

    NEW(), IN_PROGRESS(), CANCELLED(), DONE();

    OrderStatus() {
    }
}
