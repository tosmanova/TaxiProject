package ua.taxi.server.constants;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by andrii on 27.06.16.
 */
public class Constants {

    private static Properties props;
    private static final Logger LOGGER = Logger.getLogger(Constants.class);

    static {
        props = new Properties();
        try {
            props.load(Constants.class.getResourceAsStream("/constants.properties"));
            LOGGER.info(String.format("Load Constants: SQL_URL=%s, SQL_USER=%s, SQL_PASSWORD=%s",
                    props.getProperty("sql_url"),
                    props.getProperty("sql_user"),
                    props.getProperty("sql_password")));
        } catch (IOException e) {
            LOGGER.error("Constants load error");
            e.printStackTrace();
        }
    }

    public static final String SQL_URL = props.getProperty("sql_url");
    public static final String SQL_USER = props.getProperty("sql_user");
    public static final String SQL_PASSWORD = props.getProperty("sql_password");
    public static final String SQL_CREATE_TEST_SCRIPT = props.getProperty("sql_create_test_script");
    public static final String SQL_REMOVE_TEST_SCRIPT = props.getProperty("sql_remove_test_script");
    public static final double MIN_PRICE = Double.valueOf(props.getProperty("min_price"));
    public static final double KILOMETRE_PRICE = Double.valueOf(props.getProperty("kilometre_price"));
    public static final String TAXI_PROJECT_JPA = props.getProperty("persistence_unit");


}
