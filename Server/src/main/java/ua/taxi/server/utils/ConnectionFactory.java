package ua.taxi.server.utils;

import ua.taxi.server.constants.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by andrii on 27.06.16.
 */
public class ConnectionFactory {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection createConnection() throws SQLException {

        return DriverManager.getConnection(
                Constants.SQL_URL,
                Constants.SQL_USER,
                Constants.SQL_PASSWORD);
    }


}
