package by.eventcat;

import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * UserService implementation
 */
public class UserServiceImpl implements UserService{

    private static final Logger LOGGER = LogManager.getLogger();

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private UserTotalsDao userTotalsDao;

    public void setUserTotalsDao(UserTotalsDao userTotalsDao) {
        this.userTotalsDao = userTotalsDao;
    }

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() throws DataAccessException, ServiceException {
        LOGGER.debug("getAllUsers()");

        List<User> users = userDao.getAllUsers();
        if (users.size() == 0) throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        return users;
    }

    @Override
    public List<User> getAllUsersByLocationPermission(String cityName) throws DataAccessException, ServiceException {
        LOGGER.debug("getAllUsersByLocationPermission() where cityName={}", cityName);

        List<User> users = userDao.getAllUsersByLocationPermission(cityName);
        if (users.size() == 0) throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        return users;
    }

    @Override
    public User getUserById(long userId) throws DataAccessException, ServiceException {
        LOGGER.debug("getUserById where id=({})", userId);

        User user;
        if (userId <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        try{
            user = userDao.getUserById(userId);
        } catch (EmptyResultDataAccessException ex){
            throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        }
        return user;
    }

    @Override
    public User getUserByUserEmail(String userEmail) throws DataAccessException, ServiceException {
        LOGGER.debug("getUserByUserEmail where userEmail = {}", userEmail);

        if(userEmail == null || userEmail.length() < 3){
            throw new ServiceException(CustomErrorCodes.INCORRECT_EMAIL);
        }
        User user;
        try{
            user = userDao.getUserByUserEmail(userEmail);
        } catch (EmptyResultDataAccessException ex){
            throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        }
        return user;
    }

    @Override
    public Long addNewUser(User user) throws DataAccessException, ServiceException {
        LOGGER.debug("addNewUser with email = {}", user.getUserEmail());

        if(user.getUserEmail() == null || user.getUserEmail().length() < 3 ||
                user.getUserPassword() == null || user.getUserPassword().length() < 8 ||
                user.getRole() == null
                ){
            throw new ServiceException(CustomErrorCodes.INCORRECT_INPUT_DATA);
        }
        if ( ! user.getRole().equals(UserRole.SUPER_ADMIN) &&
                (user.getLocalities() == null || user.getLocalities().size() == 0)){
            throw new ServiceException(CustomErrorCodes.INCORRECT_INPUT_DATA);
        }
        //encode password
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));

        long userId;
        try {
            userId = userDao.addNewUser(user);
        } catch (DuplicateKeyException ex){
            throw new ServiceException(CustomErrorCodes.NO_DUPLICATE_DATA_PERMITTED);
        }
        //add data to totals
        Locality locality = null;
        if (user.getLocalities().size() > 0){
            locality = user.getLocalities().get(0);
        }
        int resultCode = userTotalsDao.setValue(user.getRole(), locality, UserTotalsSetValueOperation.INCREASE);
        if (resultCode != 1) {
            throw new ServiceException(CustomErrorCodes.ACTIONS_ERROR);
        }
        return userId;
    }

    @Override
    public int updateUser(User user) throws DataAccessException, ServiceException {
        LOGGER.debug("updateUser where userEmail={}", user.getUserEmail());

        User userBeforeUpdate;
        try{
            userBeforeUpdate = userDao.getUserById(user.getUserId());
        } catch (EmptyResultDataAccessException ex){
            throw new ServiceException(CustomErrorCodes.NO_ACTIONS_MADE);
        }

        if(user.getUserEmail() == null || user.getUserEmail().length() < 3 ||
                user.getRole() == null
                ){
            throw new ServiceException(CustomErrorCodes.INCORRECT_INPUT_DATA);
        }
        if ( ! user.getRole().equals(UserRole.SUPER_ADMIN) &&
                (user.getLocalities() == null || user.getLocalities().size() == 0)){
            throw new ServiceException(CustomErrorCodes.INCORRECT_INPUT_DATA);
        }
        //for password changing - function changeUserPassword()
        user.setUserPassword(userBeforeUpdate.getUserPassword());

        int rowsAffected;
        try{
            rowsAffected = userDao.updateUser(user);
        } catch (DuplicateKeyException ex){
            throw new ServiceException(CustomErrorCodes.NO_DUPLICATE_DATA_PERMITTED);
        }
        if (rowsAffected > 1) throw new ServiceException(CustomErrorCodes.ACTIONS_ERROR);


        if (rowsAffected == 1){

            Locality localityForBefore = null;
            if (userBeforeUpdate.getLocalities() != null && userBeforeUpdate.getLocalities().size() > 0 ){
                localityForBefore = userBeforeUpdate.getLocalities().get(0);
            }
            Locality localityForAfter = null;
            if (user.getLocalities() != null && user.getLocalities().size() > 0 ){
                localityForAfter = user.getLocalities().get(0);
            }

            boolean equalsLocalities = true;
            if (! (localityForBefore == null && localityForAfter == null)){
                if (localityForBefore == null) {
                    equalsLocalities = false;
                } else if ( ! localityForBefore.equals(localityForAfter)){
                    equalsLocalities = false;
                }
            }

            if ( ! userBeforeUpdate.getRole().equals(user.getRole()) || ! equalsLocalities){
                changeUserTotalsForUserUpdate(userBeforeUpdate, user);
            }
        }
        return rowsAffected;
    }

    @Override
    public int changeUserPassword(Long userId, String newPassword) throws DataAccessException, ServiceException {
        LOGGER.debug("changeUserPassword to={}", newPassword);

        if(userId == null || userId < 1 || newPassword == null || newPassword.length() < 8){
            throw new ServiceException(CustomErrorCodes.INCORRECT_INPUT_DATA);
        }

        User userBeforeUpdate;
        try{
            userBeforeUpdate = userDao.getUserById(userId);
        } catch (EmptyResultDataAccessException ex){
            throw new ServiceException(CustomErrorCodes.NO_ACTIONS_MADE);
        }
        //encode password
        userBeforeUpdate.setUserPassword(passwordEncoder.encode(newPassword));

        int rowsAffected = userDao.updateUser(userBeforeUpdate);
        if (rowsAffected > 1) throw new ServiceException(CustomErrorCodes.ACTIONS_ERROR);
        return rowsAffected;
    }

    @Override
    public int deleteUserById(Integer userId) throws DataAccessException, ServiceException {
        return 0;
    }

    @Override
    public User authenticateUser(String userEmail, String userPassword) throws DataAccessException, ServiceException {
        return null;
    }

    private void changeUserTotalsForUserUpdate(User userBeforeUpdate, User user) throws ServiceException{
        int resultCode, resultCode1;

        Locality localityBefore = null;
        if (userBeforeUpdate.getLocalities() != null && userBeforeUpdate.getLocalities().size() > 0){
            localityBefore = userBeforeUpdate.getLocalities().get(0);
        }
        resultCode = userTotalsDao.setValue(userBeforeUpdate.getRole(), localityBefore,
                UserTotalsSetValueOperation.DECREASE);
        Locality localityAfter = null;
        if (user.getLocalities() != null && user.getLocalities().size() > 0){
            localityAfter = user.getLocalities().get(0);
        }
        resultCode1 =userTotalsDao.setValue(user.getRole(), localityAfter, UserTotalsSetValueOperation.INCREASE);

        if (resultCode != 1 || resultCode1 != 1) throw new ServiceException(CustomErrorCodes.ACTIONS_ERROR);
    }
}
