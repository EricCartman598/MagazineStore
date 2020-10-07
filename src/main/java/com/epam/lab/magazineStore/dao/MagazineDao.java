package com.epam.lab.magazineStore.dao;

import com.epam.lab.magazineStore.common.DBUtil;
import com.epam.lab.magazineStore.domain.Magazine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents DAO model for {@code Magazine}
 *
 * @author Aleksandr_Samsonov
 */
public class MagazineDao {
    private static final String INSERT_SQL = "INSERT INTO magazine(NAME,DESCRIPTION,PERIOD_DAYS,PRICE) VALUES(?, ?, ?, ?);";
    private static final String SELECT_SQL = "SELECT * FROM magazine WHERE ID=?;";
    private static final String SELECT_ALL_SQL = "SELECT * FROM magazine;";
    private static final String UPDATE_SQL = "UPDATE magazine SET NAME=?,DESCRIPTION=?,PERIOD_DAYS=?,PRICE=? WHERE ID=?;";
    private static final String DELETE_SQL = "DELETE FROM magazine WHERE ID=?;";
    private static final String SELECT_MAGAZINES_BY_ORDER_SQL =
            "SELECT m.*,om.AMOUNT FROM  magazine m LEFT JOIN order_magazine om ON m.ID = om.MAGAZINE_ID WHERE ORDER_ID=?;";
    private static final Logger LOGGER = LoggerFactory.getLogger(MagazineDao.class);
    private Connection connection;

    public MagazineDao() {
        connection = DBUtil.getConnection();
    }

    /**
     * Adds magazine to the database.
     *
     * @param magazine the magazine to be added.
     */
    public void addMagazine(Magazine magazine) {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_SQL);
            int attributeIndex = 0;
            statement.setString(++attributeIndex, magazine.getName());
            statement.setString(++attributeIndex, magazine.getDescription());
            statement.setInt(++attributeIndex, magazine.getPeriodDays());
            statement.setBigDecimal(++attributeIndex, magazine.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("error while trying to add new magazine", e);
        }
    }

    /**
     * Gets magazine from database by it's id.
     *
     * @param magazineId id of the magazine.
     * @return magazine  which corresponds to magazineId.
     */
    public Magazine getMagazineById(Long magazineId) {
        Magazine magazine = new Magazine();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_SQL);
            statement.setLong(1, magazineId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                magazine.setId(result.getLong("ID"));
                magazine.setName(result.getString("NAME"));
                magazine.setDescription(result.getString("DESCRIPTION"));
                magazine.setPeriodDays(result.getInt("PERIOD_DAYS"));
                magazine.setPrice(result.getBigDecimal("PRICE"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            LOGGER.error("error while trying to get magazine by id ", e);
        }
        return magazine;
    }

    /**
     * Gets all magazines from database.
     *
     * @return list of all magazines
     */
    public List<Magazine> getAll() {
        List<Magazine> magazines = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Magazine magazine = new Magazine();
                magazine.setId(result.getLong("ID"));
                magazine.setName(result.getString("NAME"));
                magazine.setDescription(result.getString("DESCRIPTION"));
                magazine.setPeriodDays(result.getInt("PERIOD_DAYS"));
                magazine.setPrice(result.getBigDecimal("PRICE"));
                magazines.add(magazine);
            }
        } catch (SQLException e) {
            LOGGER.error("error while trying to get all magazines", e);
        }
        return magazines;
    }

    /**
     * Updates magazine in database.
     *
     * @param magazine the magazine to be updated
     */
    public void update(Magazine magazine) {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);
            int attributeIndex = 0;
            statement.setString(++attributeIndex, magazine.getName());
            statement.setString(++attributeIndex, magazine.getDescription());
            statement.setInt(++attributeIndex, magazine.getPeriodDays());
            statement.setBigDecimal(++attributeIndex, magazine.getPrice());
            statement.setLong(++attributeIndex, magazine.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("error while trying to update magazine", e);
        }
    }

    /**
     * Deletes magazine from database by it's id.
     *
     * @param magazineId the magazine to be deleted
     */
    public void deleteMagazineById(Long magazineId) {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_SQL);
            statement.setLong(1, magazineId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("error while trying to delete magazine by id", e);
        }
    }

    /**
     * Gets map of magazines and it's amount by order id.
     *
     * @param orderId id of the order
     * @return map of magazines to it's amount in order
     */
    public Map<Magazine,Integer> getMagazinesByOrderId(Long orderId) {
        Map<Magazine,Integer> magazines = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_MAGAZINES_BY_ORDER_SQL);
            statement.setLong(1, orderId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Magazine magazine = new Magazine();
                magazine.setId(result.getLong("ID"));
                magazine.setName(result.getString("NAME"));
                magazine.setDescription(result.getString("DESCRIPTION"));
                magazine.setPeriodDays(result.getInt("PERIOD_DAYS"));
                magazine.setPrice(result.getBigDecimal("PRICE"));
                magazines.put(magazine,result.getInt("AMOUNT"));
            }
        } catch (SQLException e) {
            LOGGER.error("error while trying to get magazines by order id ", e);
        }
        return magazines;
    }

}
