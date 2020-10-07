package com.epam.lab.magazineStore.dao;

import com.epam.lab.magazineStore.common.DBUtil;
import com.epam.lab.magazineStore.domain.Order;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents DAO model for {@code Order}
 *
 * @author Daniil_Gorbach on 5/20/2019
 */
public class OrderDao {
    private Connection connection;
    private static final String INSERT_SQL = "INSERT INTO `order` (USER_ID,PRICE,CREATED) VALUES (?, ?, ?)";
    private static final String SELECT_SQL = "SELECT * FROM `order` WHERE ID=?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM `order`";
    private static final String UPDATE_SQL = "UPDATE `order` SET USER_ID=?, PRICE=? WHERE ID=?";
    private static final String DELETE_SQL = "DELETE FROM `order` WHERE ID=?";
    private static final String INSERT_ORDER_MAGAZINE_SQL = "INSERT INTO order_magazine (ORDER_ID, MAGAZINE_ID, AMOUNT) values (?,?,?)";
    private static final String GET_ORDER_BY_USER_ID = "SELECT * FROM `order` WHERE USER_ID=? ORDER BY CREATED ASC";
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDao.class);


    public OrderDao() {
        connection = DBUtil.getConnection();
    }

    /**
     * Adding ability to add new order to DB.
     *
     * @param order represent order object, that will be added to DB
     * @return generated order id
     */
    public Long addOrder(Order order) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            int attrIndex = 0;
            preparedStatement.setLong(++attrIndex, order.getUserId());
            preparedStatement.setBigDecimal(++attrIndex, order.getPrice());
            preparedStatement.setTimestamp(++attrIndex, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Error during adding order to DB", e);
        }
        return null;
    }

    /**
     * Returns list of all added orders.
     *
     * @return list of all added orders
     */
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SELECT_ALL_SQL);
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("ID"));
                order.setUserId(rs.getLong("USER_ID"));
                order.setPrice(rs.getBigDecimal("PRICE"));
                order.setCreated(rs.getTimestamp("CREATED").toLocalDateTime());
                orders.add(order);
            }
        } catch (SQLException e) {
            LOGGER.error("Error during getting all orders from DB", e);
        }
        return orders;
    }

    /**
     * Returns an order, specified by it's id.
     *
     * @param orderId identification of order, that must be returned
     * @return an order, specified by it's id
     */
    public Order getOrderById(Long orderId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL)) {
            preparedStatement.setLong(1, orderId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("ID"));
                order.setUserId(rs.getLong("USER_ID"));
                order.setPrice(rs.getBigDecimal("PRICE"));
                order.setCreated(rs.getTimestamp("CREATED").toLocalDateTime());
                return order;
            }
        } catch (SQLException e) {
            LOGGER.error("Error during getting order from DB by id:" + orderId, e);
        }
        return null;
    }

    /**
     * Adding ability to add new constraint between order and magazine to DB.
     *
     * @param orderId    identification of order
     * @param magazineId identification of order
     * @param count      count of magazines
     */
    public void addOrderMagazine(Long orderId, Long magazineId, int count) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_MAGAZINE_SQL)) {
            int attrIndex = 0;
            preparedStatement.setLong(++attrIndex, orderId);
            preparedStatement.setLong(++attrIndex, magazineId);
            preparedStatement.setInt(++attrIndex, count);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error during adding record to order_magazine table", e);
        }
    }

    /**
     * Updates an order via it's id.
     *
     * @param order represents order with new values
     */
    public void updateOrder(Order order) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            int attrIndex = 0;
            preparedStatement.setLong(++attrIndex, order.getUserId());
            preparedStatement.setBigDecimal(++attrIndex, order.getPrice());
            preparedStatement.setLong(++attrIndex, order.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error during updating order in DB", e);
        }
    }

    /**
     * Deletes an order, specified by it's id.
     *
     * @param orderId id of order, that must be deleted
     */
    public void deleteOrder(int orderId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error during deleting order from DB by id:" + orderId, e);
        }
    }

    public List<Order> getOrdersByUserId(Long userId) {
        List<Order> orderList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("ID"));
                order.setPrice(rs.getBigDecimal("PRICE"));
                order.setPrice(rs.getBigDecimal("PRICE"));
                order.setUserId(rs.getLong("USER_ID"));
                order.setCreated(rs.getTimestamp("CREATED").toLocalDateTime());
                orderList.add(order);
            }
        } catch (SQLException e) {
            LOGGER.error("Error during getting order from DB by user id:" + userId, e);
        }
        return orderList;
    }
}
