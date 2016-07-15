package ua.taxi.server.dao.jpa;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.taxi.server.dao.OrderDao;
import ua.taxi.base.model.order.Order;
import ua.taxi.base.model.order.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

/**
 * Created by andrii on 10.07.16.
 */
@Component(value = "orderDao")
public class OrderDaoJPAImpl implements OrderDao {

    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public Order createOrder(Order order) {
        manager.persist(order);
        return order;
    }

    @Override
    public Order getOrder(String phone) {

        return manager.createNamedQuery("findOrderByPhone", Order.class)
                .setParameter(1, phone).getSingleResult();
    }

    @Override
    @Transactional
    public Order updateOrder(String phone, Order newOrder) {

        Order order = manager.createNamedQuery("findOrderByPhone", Order.class)
                .setParameter(1, phone).getSingleResult();
        Order oldOrder = order;
        order.setDistance(newOrder.getDistance());
        order.setDriverPhone(newOrder.getDriverPhone());

        if(!order.getFrom().equals(newOrder.getFrom())) {
            order.setFrom(newOrder.getFrom());
        }

        if(!order.getTo().equals(newOrder.getTo())) {
            order.setTo(newOrder.getTo());
        }
        order.setUserName(newOrder.getUserName());
        order.setPrice(newOrder.getPrice());
        order.setOrderStatus(newOrder.getOrderStatus());
        manager.flush();

        return oldOrder;
    }

    @Override
    @Transactional
    public Order deleteOrder(String phone) {

        Order order = manager.createNamedQuery("findOrderByPhone", Order.class)
                .setParameter(1, phone).getSingleResult();
        manager.remove(order);
        manager.flush();
        return order;
    }

    @Override
    public List<Order> getOrderList(long firstId, int offset) {
        return manager.createQuery("SELECT u from Order u ", Order.class)
                .setFirstResult((int) (firstId - 1))
                .setMaxResults(offset)
                .getResultList();
    }

    @Override
    public List<Order> getOrderWithStatus(OrderStatus orderStatus, long firstId, int offset) {

        return manager.createQuery("SELECT o from Order o where o.orderStatus=?1 ", Order.class)
                .setParameter(1, orderStatus)
                .setFirstResult((int) (firstId - 1))
                .setMaxResults(offset)
                .getResultList();
    }

    @Override
    public int getOrdersRegisteredQuantity() {
        return ((Long) manager.createQuery("select count(o.id) from Order o")
                .getSingleResult()).intValue();
    }

    @Override
    public int getOrdersNewCount() {
        return ((Long) manager.createQuery("select count(o.id) from Order o where o.orderStatus = ?1")
                .setParameter(1, OrderStatus.NEW)
                .getSingleResult()).intValue();
    }

    @Override
    public int getOrdersDoneCount() {
        return ((Long) manager.createQuery("select count(o.id) from Order o where o.orderStatus = ?1")
                .setParameter(1, OrderStatus.DONE)
                .getSingleResult()).intValue();
    }

    @Override
    public int getOrdersInProgressCount() {
        return ((Long) manager.createQuery("select count(o.id) from Order o where o.orderStatus = ?1")
                .setParameter(1, OrderStatus.IN_PROGRESS)
                .getSingleResult()).intValue();
    }
}
