package ua.taxi.constants;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by andrii on 26.06.16.
 */
public class WindowsSize {

    private static Properties props;

    static {
        props = new Properties();
        try {
            props.load(WindowsSize.class.getResourceAsStream("/windowSize.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final int ENTER_HEIGHT = Integer.parseInt(props.getProperty("enter_height"));
    public static final int ENTER_WIDTH =  Integer.parseInt(props.getProperty("enter_width"));

    public static final int CHOOSE_ORDER_HEIGHT = Integer.parseInt(props.getProperty("choose_order_height"));
    public static final int CHOOSE_ORDER_WIDTH = Integer.parseInt(props.getProperty("choose_order_width"));

    public static final int GOOGLE_MAP_HEIGHT = Integer.parseInt(props.getProperty("google_map_height"));
    public static final int GOOGLE_MAP_WIDTH = Integer.parseInt(props.getProperty("google_map_width"));
    public static final int GOOGLE_PANE_WIDTH = Integer.parseInt(props.getProperty("google_pane_width"));

}
