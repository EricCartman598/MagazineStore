package com.epam.lab.magazineStore.service;

import com.epam.lab.magazineStore.dao.OrderDao;
import com.epam.lab.magazineStore.domain.Magazine;
import com.epam.lab.magazineStore.domain.Order;

import java.util.List;
import java.util.Map;

/**
 * Represents functionality, that can be made with {@code OrderDao}
 *
 * @author Daniil_Gorbach on 5/21/2019
 */
public class OrderService {
    private OrderDao orderDao;

    public OrderService() {
        orderDao = new OrderDao();
    }

    /**
     * Adding new order to DB.
     *
     * @param order represent order object, that will be added to DB
     * @return generated order id, that was added to DB
     */
    public Long add(Order order) {

        if (order != null) {
            return orderDao.addOrder(order);
        }

        return null;
    }

    /**
     * Adds record to order_magazine table by order id and it's cart.
     *
     * @param orderId      identification of created order
     * @param magazineCart cart with magazines
     */
    public void add(Long orderId, Map<Magazine, Integer> magazineCart) {
        if (orderId != null && magazineCart != null) {
            for (Magazine magazine : magazineCart.keySet()) {
                orderDao.addOrderMagazine(orderId, magazine.getId(), magazineCart.get(magazine));
            }
        }
    }

    /**
     * Returns an order, specified by it's id.
     *
     * @param id identification of order, that must be returned
     * @return an order, specified by it's id
     */
    public Order getById(Long id) {

        if (id != null) {
            return orderDao.getOrderById(id);
        }

        return null;
    }

    /**
     * Returns list of all added orders.
     *
     * @return list of all added orders
     */
    public List<Order> getAll() {
        return orderDao.getAllOrders();
    }
}
