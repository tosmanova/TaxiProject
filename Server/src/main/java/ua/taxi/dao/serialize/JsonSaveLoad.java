package ua.taxi.dao.serialize;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import ua.taxi.model.Order.Order;
import ua.taxi.model.User.User;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andrii on 10.06.16.
 */
public class JsonSaveLoad implements SaveLoad {

    private static final String RESOURCES_PATH = "Server/src/main/resources/";
    private static final String USER_MAP_FILE = "userMap.json";
    private static final String ORDER_MAP_FILE = "orderMap.json";
    private static final String BLACK_LIST_FILE = "blackList.json";
    private static final Logger LOGGER = Logger.getLogger(JsonSaveLoad.class);

    @Override
    public void saveUserMap(Map<String, User> userMap) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(RESOURCES_PATH + USER_MAP_FILE), userToString(userMap));
        } catch (IOException e) {
            LOGGER.error("saveUserMap error", e);
        }
    }

    @Override
    public Map<String, User> loadUserMap() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return userFromString(objectMapper.readValue(
                JsonSaveLoad.class.getClassLoader().getResourceAsStream(USER_MAP_FILE),
                new TypeReference<Map<String, UserSerializeAdapter>>() {
                }));
    }

    @Override
    public void saveOrderMap(Map<String, Order> orderMap) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(RESOURCES_PATH + ORDER_MAP_FILE), orderToString(orderMap));
        } catch (IOException e) {
            LOGGER.error("saveOrderMap error", e);
        }
    }

    @Override
    public Map<String, Order> loadOrderMap() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return orderFromString(objectMapper.readValue(
                JsonSaveLoad.class.getClassLoader().getResourceAsStream(ORDER_MAP_FILE),
                new TypeReference<Map<String, OrderSerializeAdapter>>() {
                }));
    }

    @Override
    public void saveBlackList(List<String> blackList) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(RESOURCES_PATH + BLACK_LIST_FILE), blackList);
        } catch (IOException e) {
            LOGGER.error("saveOrderMap error", e);
        }
    }

    @Override
    public List<String> loadBlackList() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(
                JsonSaveLoad.class.getClassLoader().getResourceAsStream(BLACK_LIST_FILE),
                new TypeReference<List<String>>() {
                });
    }

    private Map<String, OrderSerializeAdapter> orderToString(Map<String, Order> orderMap) {

        Map<String, OrderSerializeAdapter> serializeMap = new HashMap<>();
        for (Map.Entry<String, Order> pair : orderMap.entrySet()) {
            serializeMap.put(pair.getKey(), new OrderSerializeAdapter(pair.getValue()));
        }
        return serializeMap;
    }

    private Map<String, Order> orderFromString(Map<String, OrderSerializeAdapter> serializeMap) {

        Map<String, Order> orderMap = new HashMap<>();
        for (Map.Entry<String, OrderSerializeAdapter> pair : serializeMap.entrySet()) {
            orderMap.put(pair.getKey(), pair.getValue().createOrder());
        }
        return orderMap;
    }

    private Map<String, UserSerializeAdapter> userToString(Map<String, User> userMap) {

        Map<String, UserSerializeAdapter> serializeMap = new HashMap<>();
        for (Map.Entry<String, User> pair : userMap.entrySet()) {
            serializeMap.put(pair.getKey(), new UserSerializeAdapter(pair.getValue()));
        }
        return serializeMap;
    }

    private Map<String, User> userFromString(Map<String, UserSerializeAdapter> serializeMap) {

        Map<String, User> userMap = new HashMap<>();
        for (Map.Entry<String, UserSerializeAdapter> pair : serializeMap.entrySet()) {
            userMap.put(pair.getKey(), pair.getValue().createUser());
        }
        return userMap;
    }


}
