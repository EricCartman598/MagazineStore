package com.epam.lab.magazineStore.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Util class to get connection.
 */
public class DBUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBUtil.class);
    private static Connection connection;

    /**
     * Returns a connection to db.
     * Using properties file.
     *
     * @return Connection to db via properties
     */
    public static Connection getConnection() {
        if (connection != null)
            return connection;
        else {
            try {
                Properties prop = new Properties();
                InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("com.epam.lab.magazineStore/db.properties");
                prop.load(inputStream);
                String driver = prop.getProperty("driver");
                String url = prop.getProperty("url");
                String user = prop.getProperty("user");
                String password = prop.getProperty("password");
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException | SQLException | IOException e) {
                LOGGER.error("error during connection", e);
            }
            return connection;
        }
    }
}
