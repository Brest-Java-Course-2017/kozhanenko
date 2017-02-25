package com.epam.test.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dao implementation.
 */
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LogManager.getLogger();

    static final String USER_ID = "user_id";
    static final String LOGIN = "login";
    static final String PASSWORD = "password";
    static final String DESCRIPTION = "description";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${user.select}")
    String getAllUsersSql;

    @Value("${user.selectByLogin}")
    String getUserByLoginSql;

    @Value("${user.selectById}")
    String getUserByIdSql;

    @Value("${user.insert}")
    String insertUserSql;

    @Value("${user.update}")
    String updateUserSql;

    @Value("${user.delete}")
    String deleteUserSql;

    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAllUsers() throws DataAccessException {
        LOGGER.debug("getAllUsers()");
        return jdbcTemplate.query(getAllUsersSql, new UserRowMapper());
    }

    @Override
    public User getUserById(Integer userId) throws DataAccessException {
        LOGGER.debug("getUserById({})", userId);
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_user_id", userId);
        User user = namedParameterJdbcTemplate.queryForObject(
                getUserByIdSql, namedParameters, new UserRowMapper());
        return user;
    }

    @Override
    public User getUserByLogin(String login) throws DataAccessException {
        LOGGER.debug("getUserByLogin({})", login);
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_login", login);
        User user = namedParameterJdbcTemplate.queryForObject(
                getUserByLoginSql, namedParameters, new UserRowMapper());
        return user;
    }

    @Override
    public Integer addUser(User user) throws DataAccessException {
        LOGGER.debug("addUser(user): login = {}", user.getLogin());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(USER_ID, user.getUserId());
        parameterSource.addValue(LOGIN, user.getLogin());
        parameterSource.addValue(PASSWORD, user.getPassword());
        parameterSource.addValue(DESCRIPTION, user.getDescription());
        namedParameterJdbcTemplate.update(insertUserSql, parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int updateUser(User user) throws DataAccessException {

        LOGGER.debug("update user {}", user);
        Map<String, Object> params = new HashMap<>();
        params.put(USER_ID, user.getUserId());
        params.put(LOGIN, user.getLogin());
        params.put(PASSWORD, user.getPassword());
        params.put(DESCRIPTION, user.getDescription());
        return namedParameterJdbcTemplate.update(updateUserSql, params);
    }

    @Override
    public int deleteUser(Integer userId) throws DataAccessException {

        LOGGER.debug("delete user with id = {}", userId);
        Map<String, Object> params = new HashMap<>();
        params.put(USER_ID, userId);
        return namedParameterJdbcTemplate.update(deleteUserSql, params);
    }

    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User(
                    resultSet.getInt(USER_ID),
                    resultSet.getString(LOGIN),
                    resultSet.getString(PASSWORD),
                    resultSet.getString(DESCRIPTION));
            return user;
        }
    }
}
