package by.eventcat;

import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * User class dao interface
 */
public interface UserDao {

    /**
     * Get all users
     *
     * @return list of all users from DB
     * @throws DataAccessException
     */
    List<User> getAllUsers() throws DataAccessException;

    /**
     * Get User object by identifier
     *
     * @param userId - user identifier
     * @return User object
     * @throws DataAccessException
     */
    User getUserById (long userId) throws DataAccessException;

    /**
     * Get User object by email that used as its main identifier
     *
     * @param userEmail user email
     * @return User object
     * @throws DataAccessException
     */
    User getUserByUserEmail (long userEmail) throws DataAccessException;

    /**
     * Add new user to database
     *
     * @param user User object to add
     * @return new User identifier
     * @throws DataAccessException
     */
    Integer addNewUser (User user) throws DataAccessException;

    /**
     * Update User information
     *
     * @param user - User object with updated data
     * @return number of rows affected
     * @throws DataAccessException
     */
    int updateUser(User user) throws DataAccessException;

    /**
     * Delete User by identifier
     *
     * @param userId
     * @return
     * @throws DataAccessException
     */
    int deleteUserById(Integer userId) throws DataAccessException;
}
