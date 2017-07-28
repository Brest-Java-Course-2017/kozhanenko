package by.eventcat;

import by.eventcat.custom.exceptions.ServiceException;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * UserService implementation
 */
public class UserServiceImpl implements UserService{

    @Override
    public List<User> getAllUsers() throws DataAccessException, ServiceException {
        return null;
    }

    @Override
    public User getUserById(long userId) throws DataAccessException, ServiceException {
        return null;
    }

    @Override
    public User getUserByUserEmail(long userEmail) throws DataAccessException, ServiceException {
        return null;
    }

    @Override
    public Integer addNewUser(User user) throws DataAccessException, ServiceException {
        return null;
    }

    @Override
    public int updateUser(User user) throws DataAccessException, ServiceException {
        return 0;
    }

    @Override
    public int deleteUserById(Integer userId) throws DataAccessException, ServiceException {
        return 0;
    }

    @Override
    public User authenticateUser(String userEmail, String userPassword) throws DataAccessException, ServiceException {
        return null;
    }
}
