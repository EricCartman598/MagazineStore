package com.epam.lab.magazineStore.dao;

import com.epam.lab.magazineStore.common.DBUtil;
import com.epam.lab.magazineStore.domain.Magazine;
import com.epam.lab.magazineStore.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

import static com.epam.lab.magazineStore.domain.Role.ADMIN;
import static com.epam.lab.magazineStore.domain.Role.CUSTOMER;

public class UserDao {
    private static final String ADD_USER_QUERY = "INSERT INTO user (ROLE, " +
            "FIRST_NAME, " +
            "LAST_NAME, " +
            "EMAIL, " +
            "BIRTH_DATE, " +
            "BALANCE, " +
            "PASS_HASH, " +
            "CREATED) VALUES(?,?,?,?,?,?,?,?);";
    private static final String FIND_USER_BY_ID_QUERY = "SELECT * FROM user WHERE ID=?;";
    private static final String DELETE_USER_BY_ID_QUERY = "DELETE FROM user WHERE ID=?;";
    private static final String GET_ALL_USERS_QUERY = "SELECT * FROM user";
    private static final String UPDATE_USER_QUERY = "UPDATE user SET " +
            "ROLE=?, FIRST_NAME=?, LAST_NAME=?, EMAIL=?, " +
            "BIRTH_DATE=?, BALANCE=?, PASS_HASH=? " +
            "WHERE ID=?;";
    private static final String GET_USER_BY_EMAIL_QUERY = "SELECT * FROM user WHERE EMAIL=?";
    private static final String GET_USER_BY_PASSWORD_QUERY = "SELECT * FROM user WHERE PASS_HASH=?";
    private  static final String GET_USER_LIBRARY_QUERY = "SELECT * FROM magazine\n" +
            "    INNER JOIN library ON magazine.ID = library.MAGAZINE_ID\n" +
            "    WHERE library.USER_ID=? ORDER BY EXPIRATION ASC;";

    private Connection connection;
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    public UserDao() {
        connection = DBUtil.getConnection();
    }

    private void setUserDaoFields(User data, PreparedStatement statement) throws SQLException {
        int indexAttr = 0;
        statement.setString(++indexAttr, data.getRole().toString());
        statement.setString(++indexAttr, data.getFirstName());
        statement.setString(++indexAttr, data.getLastName());
        statement.setString(++indexAttr, data.getEmail());
        statement.setTimestamp(++indexAttr, data.getBirthDate());
        statement.setBigDecimal(++indexAttr, data.getBalance());
        statement.setInt(++indexAttr, data.getPassword());
    }

    public void add(User data) {
        try (PreparedStatement statement = connection.prepareStatement(ADD_USER_QUERY)) {
            setUserDaoFields(data, statement);
            statement.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            statement.execute();
        } catch (Exception e) {
            logger.error("error while adding user", e);
        }
    }

    public User findById(Long id) {
        User user = new User();

        try (PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                fillUserFields(rs, user);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("error while searching user by ID", e);
        }

        return user;
    }

    public List<User> getAll() {
        List<User> userList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS_QUERY)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();
                fillUserFields(rs, user);
                userList.add(user);
            }
        } catch (Exception e) {
            logger.error("error while getting all users", e);
        }

        return userList;
    }

    private void fillUserFields(ResultSet rs, User user) throws SQLException {
        user.setId(rs.getLong("ID"));
        user.setFirstName(rs.getString("FIRST_NAME"));
        user.setBirthDate(rs.getTimestamp("BIRTH_DATE"));
        user.setEmail(rs.getString("EMAIL"));
        user.setLastName(rs.getString("LAST_NAME"));
        user.setBalance(rs.getBigDecimal("BALANCE"));
        user.setPassword(rs.getInt("PASS_HASH"));
        user.setCreatedDate(rs.getTimestamp("CREATED"));

        String role = rs.getString("ROLE");
        if (role.equals(CUSTOMER.toString())) {
            user.setRole(CUSTOMER);
        } else {
            user.setRole(ADMIN);
        }
    }


    public void update(User data) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_QUERY)) {
            setUserDaoFields(data, statement);
            statement.setLong(8, data.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            logger.error("error while updating user", e);
        }
    }

    public User getUserByEmail(String email) {
        try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL_QUERY)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User();
                fillUserFields(rs, user);
                return user;
            }
        } catch (Exception e) {
            logger.error("error while getting user by email", e);
        }

        return null;
    }

    public Map<Magazine, Timestamp> getUserLibrary(Long userId) {
        Map<Magazine, Timestamp> userLibrary = new LinkedHashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_USER_LIBRARY_QUERY)) {
            statement.setLong(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Magazine magazine = new Magazine();
                magazine.setId(rs.getLong("ID"));
                magazine.setName(rs.getString("NAME"));
                magazine.setPrice(rs.getBigDecimal("PRICE"));
                magazine.setPeriodDays(rs.getInt("PERIOD_DAYS"));
                Timestamp expiration = rs.getTimestamp("EXPIRATION");
                if(expiration.compareTo(new Timestamp(System.currentTimeMillis())) >= 0) {
                    userLibrary.put(magazine, expiration);
                }
            }
        } catch (Exception e) {
            logger.error("error while getting user by password", e);
        }

        return userLibrary;
    }
}
