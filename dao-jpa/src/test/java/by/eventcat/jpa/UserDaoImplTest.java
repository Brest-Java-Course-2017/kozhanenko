package by.eventcat.jpa;

import by.eventcat.User;
import by.eventcat.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * User Dao JPA Implementation Tests
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-jpa-dao.xml"})
@Transactional
public class UserDaoImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserDao userDao;

    @Test
    public void getAllUsers() throws Exception {
        LOGGER.debug("test: getAllUsers()");
        List<User> users = userDao.getAllUsers();
        assertTrue(users.size() > 0);
    }

    @Test
    public void getUserById() throws Exception {
        LOGGER.debug("test: getUserById()");
        List<User> users = userDao.getAllUsers();
        if (users.size() > 0){
            User realUser = users.get(0);
            long userId = realUser.getUserId();
            User user = userDao.getUserById(userId);
            assertNotNull(user);
            assertEquals(realUser, user);
        }
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserByIdNoDataFound() throws Exception {
        LOGGER.debug("test: getUserByIdNoDataFound()");
        userDao.getUserById(999);
    }

    @Test
    public void getUserByUserEmail() throws Exception {
        LOGGER.debug("test: getUserByUserEmail()");
        List<User> users = userDao.getAllUsers();
        if (users.size() > 0){
            User realUser = users.get(0);
            String userEmail = realUser.getUserEmail();
            User user = userDao.getUserByUserEmail(userEmail);
            assertNotNull(user);
            assertEquals(realUser, user);
        }
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserByUserEmailNoDataFound() throws Exception {
        LOGGER.debug("test: getUserByUserEmailNoDataFound()");
        userDao.getUserByUserEmail("someNotExistingEmail@gmail.com");
    }

    @Test
    public void addNewUser() throws Exception {
        LOGGER.debug("test: addNewUser()");

        List<User> users = userDao.getAllUsers();
        Integer quantityBefore = users.size();

        if (quantityBefore != 0){
            User userToAdd = users.get(0);
            userToAdd.setUserEmail("newUserName@gmail.com");

            Long userId = userDao.addNewUser(userToAdd);
            assertNotNull(userId);

            users = userDao.getAllUsers();
            assertEquals(quantityBefore + 1, users.size());

            User newUser = userDao.getUserById(userId);
            assertNotNull(newUser);
            assertEquals("newUserName@gmail.com", newUser.getUserEmail());
        }
    }

    @Test(expected = DuplicateKeyException.class)
    public void addNewUserDuplicateEmail() throws Exception {
        LOGGER.debug("test: addNewUserDuplicateEmail()");
        List<User> users = userDao.getAllUsers();
        if (users.size() > 0){
            User userToAdd = users.get(0);
            userDao.addNewUser(userToAdd);
        }
    }

    @Test
    public void updateUser() throws Exception {
        LOGGER.debug("test: updateUser()");

        List<User> users = userDao.getAllUsers();
        if (users.size() > 0){
            User userToUpdate = users.get(0);
            userToUpdate.setUserName("John");

            int count = userDao.updateUser(userToUpdate);
            assertEquals(1, count);

            User updatedUser = userDao.getUserById(userToUpdate.getUserId());
            assertEquals("John", updatedUser.getUserName());
        }
    }

    @Test(expected = DuplicateKeyException.class)
    public void updateUserTryingToPutDuplicateEmail() throws Exception {
        LOGGER.debug("test: updateUserTryingToPutDuplicateEmail()");

        List<User> users = userDao.getAllUsers();
        if (users.size() > 1){
            User userToUpdate = users.get(0);
            User otherUser = users.get(1);

            userToUpdate.setUserEmail(otherUser.getUserEmail());

            userDao.updateUser(userToUpdate);
        }
    }

    @Test
    public void updateUserNonExistingUser() throws Exception {
        LOGGER.debug("test: updateUserNonExistingUser()");

        List<User> users = userDao.getAllUsers();
        if (users.size() > 0){
            User userToUpdate = users.get(0);
            userToUpdate.setUserId(999);
            userToUpdate.setUserName("John");

            int rowsAffected = userDao.updateUser(userToUpdate);
            assertEquals(0, rowsAffected);
        }
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteUserById() throws Exception {
        LOGGER.debug("test: deleteUserById()");

        List<User> users = userDao.getAllUsers();

        if (users.size() > 0){
            User user = users.get(0);
            user.setUserEmail("userToDelete@gmail.com");

            Long addedUserId = userDao.addNewUser(user);
            assertEquals(users.size() + 1, userDao.getAllUsers().size());

            int rowsAffected = userDao.deleteUserById(addedUserId);
            assertEquals(1, rowsAffected);
            assertEquals(users.size(), userDao.getAllUsers().size());

            userDao.getUserByUserEmail("userToDelete@gmail.com");
        }
    }

    @Test
    public void deleteUserByIdNotingToDelete() throws Exception {
        LOGGER.debug("test: deleteUserByIdNotingToDelete()");

        List<User> users = userDao.getAllUsers();

        if (users.size() > 0){
            int rowsAffected = userDao.deleteUserById(999L);
            assertEquals(0, rowsAffected);
        }
    }

    @Test
    public void deleteUserByIdDataIsInUse() throws Exception {
        LOGGER.debug("test: deleteUserByIdDataIsInUse()");
        userDao.deleteUserById(1L);
    }




}
