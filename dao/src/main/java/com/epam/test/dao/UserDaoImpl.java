package com.epam.test.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Dao implementation.
 */
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // FIXME move SQL scripts to properties or external files
    String getAllUsersSql = "select user_id, login, password, description from app_user";
    String getUserByIdSql = "select user_id, login, password, description from app_user where user_id = :p_user_id";

    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAllUsers() {
        LOGGER.debug("Hello from getAllUsers");
        return jdbcTemplate.query(getAllUsersSql, new UserRowMapper());
    }

    @Override
    public User getUserById(Integer userId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_user_id", userId);
        User user = namedParameterJdbcTemplate.queryForObject(
                getUserByIdSql, namedParameters, new UserRowMapper());
        return user;
    }

    @Override
    public Integer addUser(User user) {
        return null;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(Integer userId) {

    }

    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User(
                    resultSet.getInt("user_id"),
                    resultSet.getString("login"),
                    resultSet.getString("password"),
                    resultSet.getString("description"));
            return user;
        }
    }
}