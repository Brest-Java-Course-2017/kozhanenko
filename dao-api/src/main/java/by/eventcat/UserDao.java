package by.eventcat;

import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * public interface UserDao
 */
public interface UserDao {

    /**
     * Get all users list.
     *
     * @return all users list
     * @throws DataAccessException
     */
    List<User> getAllUsers() throws DataAccessException;

    /**
     * Get user by Id.
     *
     * @param userId user identifier.
     * @return user object.
     * @throws DataAccessException
     */
    User getUserById(Integer userId) throws DataAccessException;

    /**
     * Get user by login
     *
     * @param login user login (really email uses as login)
     * @return user object
     * @throws DataAccessException
     */
    User getUserByLogin(String login) throws DataAccessException;

    /**
     * Create new user.
     *
     * @param user user object.
     * @return new user Id.
     * @throws DataAccessException
     */
    Integer addUser(User user) throws DataAccessException;

    /**
     * Update user.
     *
     * @param user user object.
     * @return new user Id.
     * @throws DataAccessException
     */
    int updateUser(User user) throws DataAccessException;

    /**
     * Delete user.
     *
     * @param userId user identifier
     * @return
     * @throws DataAccessException
     */
    int deleteUser(Integer userId) throws DataAccessException;
    //TODO: realise if there is needless to return something - REST do not permit any return when delete
    //TODO: DataAccessException description in javadoc???
}
