package ua.taxi.dao.serialize;

import ua.taxi.model.Order.Order;
import ua.taxi.model.User.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by andrii on 10.06.16.
 */
public interface SaveLoad {

    void saveUserMap(Map<String, User> userMap);

    Map<String, User> loadUserMap() throws IOException;

    void saveOrderMap(Map<String, Order> orderMap);

    Map<String, Order> loadOrderMap() throws IOException;

    void saveBlackList(List<String> blackList);

    List<String> loadBlackList() throws IOException;

}
