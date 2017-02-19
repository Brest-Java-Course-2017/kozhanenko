package com.epam.test.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String getAllUsersSql = "select user_id, login, password, description from app_user";
    private String getUserByIdSql = "select user_id, login, password, description from app_user where user_id = :p_user_id";
    private String addUserSql = "insert into app_user (login, password, description) values (:p_login, :p_password, :p_description)";
    private String updateUserSql = "update app_user set login = :p_login, password = :p_password, description = :p_description where user_id = :p_user_id";
    private String deleteUserSql = "delete from app_user where user_id = :p_user_id";


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
        Map <String, String> parameters = new HashMap<>();
        parameters.put("p_login", user.getLogin());
        parameters.put("p_password", user.getPassword());
        parameters.put("p_description", user.getDescription());
        return namedParameterJdbcTemplate.update(addUserSql, parameters);
    }

    @Override
    public void updateUser(User user) {
        Map <String, Object> parameters = new HashMap<>();
        parameters.put("p_user_id", user.getUserId());
        parameters.put("p_login", user.getLogin());
        parameters.put("p_password", user.getPassword());
        parameters.put("p_description", user.getDescription());
        namedParameterJdbcTemplate.update(updateUserSql, parameters);
    }

    @Override
    public void deleteUser(Integer userId) {
        SqlParameterSource parameters = new MapSqlParameterSource("p_user_id", userId);
        namedParameterJdbcTemplate.update(deleteUserSql, parameters);
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