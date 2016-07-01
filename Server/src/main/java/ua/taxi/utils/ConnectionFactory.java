package ua.taxi.utils;

import ua.taxi.constants.DaoConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by andrii on 27.06.16.
 */
public class ConnectionFactory {
   /* static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/

    public static Connection createConnection() throws SQLException {

        return DriverManager.getConnection(
                DaoConstants.SQL_URL,
                DaoConstants.SQL_USER,
                DaoConstants.SQL_PASSWORD);
    }


}
