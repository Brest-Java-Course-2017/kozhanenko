package by.eventcat;

import by.eventcat.custom.exceptions.ServiceException;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * UserService interface
 */
public interface UserService {

    /**
     * Get all users
     *
     * @return list of all users from DB
     * @throws DataAccessException
     */
    List<User> getAllUsers() throws DataAccessException, ServiceException;

    /**
     * Get User object by identifier
     *
     * @param userId - user identifier
     * @return User object
     * @throws DataAccessException
     */
    User getUserById (long userId) throws DataAccessException, ServiceException;

    /**
     * Get User object by email that used as its main identifier
     *
     * @param userEmail user email
     * @return User object
     * @throws DataAccessException
     */
    User getUserByUserEmail (long userEmail) throws DataAccessException, ServiceException;

    /**
     * Add new user to database
     *
     * @param user User object to add
     * @return new User identifier
     * @throws DataAccessException
     */
    Integer addNewUser (User user) throws DataAccessException, ServiceException;

    /**
     * Update User information
     *
     * @param user - User object with updated data
     * @return number of rows affected
     * @throws DataAccessException
     */
    int updateUser(User user) throws DataAccessException, ServiceException;

    /**
     * Delete User by identifier
     *
     * @param userId
     * @return
     * @throws DataAccessException
     */
    int deleteUserById(Integer userId) throws DataAccessException, ServiceException;

    /**
     * Authenticate User by login (email) and password
     *
     * returns User object if user with userEmail exists and authentication is successful
     * returns null if no such User in DB or login-password are incorrect
     *
     * @param userEmail User email - used as unique identifier
     * @param userPassword - User's encrypted password
     * @return User object (authentication is successful) or null
     * @throws DataAccessException
     */
    User authenticateUser(String userEmail, String userPassword) throws DataAccessException, ServiceException;


}
