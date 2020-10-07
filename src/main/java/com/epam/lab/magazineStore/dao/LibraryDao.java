package com.epam.lab.magazineStore.dao;

import com.epam.lab.magazineStore.common.DBUtil;
import com.epam.lab.magazineStore.domain.Library;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryDao {
    private static final String INSERT_SQL = "INSERT INTO library(USER_ID,MAGAZINE_ID,EXPIRATION) VALUES(?,?,?);";
    private static final String UPDATE_BY_USER_AND_MAGAZINE_ID_SQL = "UPDATE library SET EXPIRATION=? WHERE USER_ID=? AND MAGAZINE_ID=?;";
    private static final String DELETE_SQL = "DELETE FROM library WHERE MAGAZINE_ID=?;";
    private static final String SELECT_ALL_BY_USER_ID_SQL = "SELECT * FROM library JOIN (magazine,user) ON (magazine.ID=library.MAGAZINE_ID AND user.ID=library.USER_ID)";
    private static final Logger LOGGER = LoggerFactory.getLogger(MagazineDao.class);
    private Connection connection;

    public LibraryDao() {
        connection = DBUtil.getConnection();
    }

    public void addMagazineToLibrary(Library library) {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_SQL);
            int attributeIndex = 0;
            statement.setLong(++attributeIndex, library.getUserId());
            statement.setLong(++attributeIndex, library.getMagazineId());
            statement.setTimestamp(++attributeIndex, Timestamp.valueOf(library.getExpiration()));
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("error while trying to add to Library", e);
        }
    }

    public void updateLibrary(Library library) {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_BY_USER_AND_MAGAZINE_ID_SQL);
            int attributeIndex = 0;
            statement.setTimestamp(++attributeIndex, Timestamp.valueOf(library.getExpiration()));
            statement.setLong(++attributeIndex, library.getUserId());
            statement.setLong(++attributeIndex, library.getMagazineId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("error while updating Library", e);
        }
    }

    public List<Library> getAll() {
        List<Library> subscribeList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_USER_ID_SQL);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Library subscribe = new Library();
                subscribe.setUserId(result.getLong("USER_ID"));
                subscribe.setMagazineId(result.getLong("MAGAZINE_ID"));
                subscribe.setExpiration(result.getTimestamp("EXPIRATION").toLocalDateTime());
                subscribeList.add(subscribe);
            }
        } catch (SQLException e) {
            LOGGER.error("error while trying to get all magazines from library", e);
        }
        return subscribeList;
    }
}
