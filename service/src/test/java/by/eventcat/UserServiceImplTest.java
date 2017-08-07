package by.eventcat;

import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * UserService implementation tests
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-service-for-jpa-dao-impl.xml"})
@Transactional
public class UserServiceImplTest {

    private Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @Autowired
    private UserTotalsDao userTotalsDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void getAllUsers() throws Exception {
        LOGGER.debug("test: getAllUsers()");
        List<User> users = userService.getAllUsers();
        assertTrue(users.size() > 0);
    }

    @Test
    public void getAllUsersByLocationPermission() throws Exception {
        LOGGER.debug("test: getAllUsersByLocationPermission()");

        List<User> users = userService.getAllUsers();
        if (users.size() > 0){
            User user = users.get(0);
            List<Locality> localities = user.getLocalities();
            if (localities.size() > 0){
                String cityName = localities.get(0).getCityName();
                users = userService.getAllUsersByLocationPermission(cityName);
                assertTrue(users.size() > 0);
            }
        }
    }

    @Test(expected = ServiceException.class)
    public void getAllUsersByLocationPermissionNoDataFound() throws Exception {
        LOGGER.debug("test: getAllUsersByLocationPermissionNoDataFound() ");
        try{
            userService.getAllUsersByLocationPermission("Not existing place name");
        } catch (ServiceException ex){
            assertTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.NO_CALLING_DATA_FOUND));
            throw ex;
        }
    }

    @Test
    public void getUserById() throws Exception {
        LOGGER.debug("test: getUserById()");
        List<User> users = userService.getAllUsers();
        if (users.size() > 0){
            User realUser = users.get(0);
            long userId = realUser.getUserId();
            User user = userService.getUserById(userId);
            assertEquals(realUser, user);
            assertNotNull(user);
        }
    }

    @Test(expected = ServiceException.class)
    public void getUserByIdIncorrectIndex() throws Exception {
        LOGGER.debug("test: getUserByIdIncorrectIndex()");
        try{
            userService.getUserById(0);
        } catch (ServiceException ex){
            assertTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.INCORRECT_INDEX));
            throw ex;
        }
    }

    @Test(expected = ServiceException.class)
    public void getUserByIdNoDataFound() throws Exception {
        LOGGER.debug("test: getUserByIdNoDataFound()");
        try{
            userService.getUserById(999);
        } catch (ServiceException ex){
            assertTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.NO_CALLING_DATA_FOUND));
            throw ex;
        }
    }

    @Test
    public void getUserByUserEmail() throws Exception {
        LOGGER.debug("test: getUserByUserEmail()");
        List<User> users = userService.getAllUsers();
        if (users.size() > 0){
            String userEmail = users.get(0).getUserEmail();
            User user = userService.getUserByUserEmail(userEmail);
            assertNotNull(user);
            assertEquals(users.get(0), user);
        }
    }

    @Test(expected = ServiceException.class)
    public void getUserByUserEmailIncorrectEmail() throws Exception {
        LOGGER.debug("test: getUserByUserEmailIncorrectEmail() ");
        try{
            userService.getUserByUserEmail("ss");
        } catch (ServiceException ex){
            assertTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.INCORRECT_EMAIL));
            throw ex;
        }
    }

    @Test(expected = ServiceException.class)
    public void getUserByUserEmailNoCallingDataFound() throws Exception {
        LOGGER.debug("test: getUserByUserEmailNoCallingDataFound() ");
        try{
            userService.getUserByUserEmail("nonExistingEmail@mail.ru");
        } catch (ServiceException ex){
            assertTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.NO_CALLING_DATA_FOUND));
            throw ex;
        }
    }

    @Test
    public void addNewUser() throws Exception {
        LOGGER.debug("test: addNewUser()");

        List<User> users = userService.getAllUsers();
        int quantityBefore = users.size();

        if (quantityBefore != 0){
            User userToAdd = users.get(0);
            userToAdd.setUserEmail("newUserName@gmail.com");
            userToAdd.setUserPassword("12345678");

            Locality city = null;
            if (userToAdd.getLocalities().size() > 0){
                city = userToAdd.getLocalities().get(0);
            }

            UserTotals totalUsersOfNewUserKind = userTotalsDao.getValue(userToAdd.getRole(), city);

            Long userId = userService.addNewUser(userToAdd);
            assertNotNull(userId);

            users = userService.getAllUsers();
            assertEquals(quantityBefore + 1, users.size());

            User newUser = userService.getUserById(userId);
            assertNotNull(newUser);
            assertEquals("newUserName@gmail.com", newUser.getUserEmail());

            if (totalUsersOfNewUserKind == null){
                assertEquals(1, userTotalsDao.getValue(userToAdd.getRole(), city).getCount());
            } else {
                assertEquals(totalUsersOfNewUserKind.getCount() + 1,
                        userTotalsDao.getValue(userToAdd.getRole(), city).getCount());
            }

            int rowsAffected = userService.deleteUserById(userId);
            assertEquals(1, rowsAffected);
        }
    }

    @Test
    public void addNewUserIncorrectInputData() throws Exception {
        LOGGER.debug("test: addNewUserIncorrectInputData()");

        List<User> users = userService.getAllUsers();
        int quantityBefore = users.size();

        if (quantityBefore != 0){
            User userToAdd = users.get(0);
            userToAdd.setUserEmail("rr");
            try{
                Long newUserId = userService.addNewUser(userToAdd);
                assertEquals(new Long(-1), newUserId);
            } catch(ServiceException ex) {
                assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INPUT_DATA);
            }
            userToAdd.setUserEmail("rr@dd.com");

            userToAdd.setUserPassword("123456");
            try{
                Long newUserId = userService.addNewUser(userToAdd);
                assertEquals(new Long(-1), newUserId);
            } catch(ServiceException ex) {
                assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INPUT_DATA);
            }
            userToAdd.setUserPassword("12345678");

            userToAdd.setRole(null);
            try{
                Long newUserId =  userService.addNewUser(userToAdd);
                assertEquals(new Long(-1), newUserId);
            } catch(ServiceException ex) {
                assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INPUT_DATA);
            }
            userToAdd.setRole(UserRole.CITY_ADMIN);

            userToAdd.setLocalities(null);
            try{
                Long newUserId = userService.addNewUser(userToAdd);
                assertEquals(new Long(-1), newUserId);
            } catch(ServiceException ex) {
                assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INPUT_DATA);
            }
        }
    }

    @Test(expected = ServiceException.class)
    public void addNewUserDuplicateData() throws Exception {
        LOGGER.debug("test: addNewUserDuplicateData()");

        List<User> users = userService.getAllUsers();
        int quantityBefore = users.size();

        if (quantityBefore != 0){
            User userToAdd = users.get(0);
            userToAdd.setUserPassword("12345678");
            try{
                userService.addNewUser(userToAdd);
            } catch(ServiceException ex) {
                assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.NO_DUPLICATE_DATA_PERMITTED);
                throw ex;
            }
        }
    }

    @Test
    public void updateUser() throws Exception {
        LOGGER.debug("test: updateUser()");

        List<User> users = userService.getAllUsers();
        if (users.size() > 0){
            User userToUpdate = users.get(0);
            String userNameBefore = userToUpdate.getUserName();
            userToUpdate.setUserName("John");

            UserTotals userTotalsBeforeUpdate = userTotalsDao.getValue(userToUpdate.getRole(),
                    (userToUpdate.getLocalities() != null && userToUpdate.getLocalities().size() > 0) ?
                            userToUpdate.getLocalities().get(0) : null);

            int count = userService.updateUser(userToUpdate);
            assertEquals(1, count);

            User updatedUser = userService.getUserById(userToUpdate.getUserId());
            assertEquals("John", updatedUser.getUserName());

            UserTotals userTotalsAfterUpdate = userTotalsDao.getValue(userToUpdate.getRole(),
                    (userToUpdate.getLocalities() != null && userToUpdate.getLocalities().size() > 0) ?
                            userToUpdate.getLocalities().get(0) : null);
            assertEquals(userTotalsBeforeUpdate, userTotalsAfterUpdate);

            //set data to initial
            updatedUser.setUserName(userNameBefore);
            updatedUser = userService.getUserById(userToUpdate.getUserId());
            assertEquals("John", updatedUser.getUserName());
        }
    }

    @Test(expected = ServiceException.class)
    public void updateNonExistingUser() throws Exception {
        LOGGER.debug("test: updateNonExistingUser()");

        List<User> users = userService.getAllUsers();
        if (users.size() > 0){
            User userToUpdate = users.get(0);
            userToUpdate.setUserId(999);
            try{
                userService.updateUser(userToUpdate);
            } catch (ServiceException ex){
                assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.NO_ACTIONS_MADE);
                throw ex;
            }
        }
    }

    @Test
    public void updateUserIncorrectInputData() throws Exception {
        LOGGER.debug("test: updateUserIncorrectInputData()");

        List<User> users = userService.getAllUsers();
        if (users.size() > 0){
            User userToUpdate = users.get(0);
            userToUpdate.setUserEmail("rr");
            try{
                int rowsAffected = userService.updateUser(userToUpdate);
                assertEquals(-1, rowsAffected);
            } catch (ServiceException ex){
                assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INPUT_DATA);
            }
            userToUpdate.setUserEmail("normalMail@gmail.com");

            userToUpdate.setRole(null);
            try{
                int rowsAffected = userService.updateUser(userToUpdate);
                assertEquals(-1, rowsAffected);
            } catch (ServiceException ex){
                assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INPUT_DATA);
            }

            userToUpdate.setRole(UserRole.CITY_ADMIN);
            userToUpdate.setLocalities(new ArrayList<>());
            try{
                int rowsAffected = userService.updateUser(userToUpdate);
                assertEquals(-1, rowsAffected);
            } catch (ServiceException ex){
                assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INPUT_DATA);
            }
            userToUpdate.setLocalities(null);
            try{
                int rowsAffected = userService.updateUser(userToUpdate);
                assertEquals(-1, rowsAffected);
            } catch (ServiceException ex){
                assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INPUT_DATA);
            }
        }
    }

    @Test(expected = ServiceException.class)
    public void updateUserDuplicateData() throws Exception {
        LOGGER.debug("test: updateUserDuplicateData()");

        List<User> users = userService.getAllUsers();
        if (users.size() > 1){
            User userToUpdate = users.get(0);
            String duplicatedEmail = users.get(1).getUserEmail();
            userToUpdate.setUserEmail(duplicatedEmail);
            try{
                userService.updateUser(userToUpdate);
            } catch (ServiceException ex){
                assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.NO_DUPLICATE_DATA_PERMITTED);
                throw ex;
            }
        }
    }

    @Test
    public void updateUserUpdateRoles() throws Exception {
        LOGGER.debug("test: updateUserUpdateRoles() ");

        List<User> users = userService.getAllUsers();
        if (users.size() > 0){
            User userToUpdate = users.get(0);
            if (  ! userToUpdate.getRole().equals(UserRole.CITY_ADMIN)){

                UserRole userRoleBeforeUpdate = userToUpdate.getRole();
                Locality localityBeforeUpdate = null;
                if (userToUpdate.getLocalities() != null && userToUpdate.getLocalities().size() > 0){
                    localityBeforeUpdate = userToUpdate.getLocalities().get(0);
                }

                Locality localityAfterUpdate = new Locality(1, "Брест");

                UserTotals userTotalsBeforeUpdate = userTotalsDao.getValue(userToUpdate.getRole(),
                        localityBeforeUpdate);

                UserTotals userTotalsAfterUpdateBefore = userTotalsDao.getValue(UserRole.CITY_ADMIN,
                        localityAfterUpdate);

                userToUpdate.setRole(UserRole.CITY_ADMIN);
                List<Locality> localities = new ArrayList<>();
                localities.add(0, localityAfterUpdate);

                userToUpdate.setLocalities(localities);

                int count = userService.updateUser(userToUpdate);
                assertEquals(1, count);

                User updatedUser = userService.getUserById(userToUpdate.getUserId());
                assertEquals(UserRole.CITY_ADMIN, updatedUser.getRole());
                assertEquals(localityAfterUpdate, updatedUser.getLocalities().get(0));


                UserTotals userTotalsBeforeUpdateNew = userTotalsDao.getValue(userRoleBeforeUpdate,
                        localityBeforeUpdate);

                if (userTotalsBeforeUpdate.getCount() == 1){
                    assertEquals(null, userTotalsBeforeUpdateNew);
                } else{
                    assertEquals(userTotalsBeforeUpdate.getCount() -1 , userTotalsBeforeUpdateNew.getCount());
                }

                UserTotals userTotalsAfterUpdate = userTotalsDao.getValue(userToUpdate.getRole(),
                        localityAfterUpdate);

                if (userTotalsAfterUpdateBefore == null){
                    assertEquals(1, userTotalsAfterUpdate.getCount());
                } else{
                    assertEquals(userTotalsAfterUpdateBefore.getCount() + 1, userTotalsAfterUpdate.getCount());
                }

                //set data to initial
                updatedUser.setRole(userRoleBeforeUpdate);
                List<Locality> localities1 = new ArrayList<>();
                localities1.add(0, localityBeforeUpdate);
                updatedUser.setLocalities(localities1);
                count = userService.updateUser(updatedUser);
                assertEquals(1, count);
            }
        }
    }

    @Test
    public void changeUserPassword() throws Exception {
        LOGGER.debug("test: changeUserPassword()");

        List<User> users = userService.getAllUsers();
        if (users.size() > 0) {
            User user = users.get(0);
            user.setUserEmail("someDifferentEmail@gmail.com");
            user.setUserPassword("12345678");

            Long newUserId = userService.addNewUser(user);
            assertNotNull(newUserId);

            User userToUpdate = userService.getUserById(newUserId);
            assertTrue(passwordEncoder.matches("12345678", userToUpdate.getUserPassword()));

            int rowsAffected = userService.changeUserPassword(userToUpdate.getUserId(), "newPassword");
            assertEquals(1, rowsAffected);

            User updatedUser = userService.getUserById(newUserId);
            assertTrue(passwordEncoder.matches("newPassword",updatedUser.getUserPassword()));

            //set data to initial
            rowsAffected = userService.deleteUserById(newUserId);
            assertEquals(1, rowsAffected);
        }
    }

    @Test
    public void changeUserPasswordIncorrectInputData() throws Exception {
        LOGGER.debug("test: changeUserPasswordIncorrectInputData()");

        try{
            int rowsAffected = userService.changeUserPassword(0L, "12345678");
            assertEquals(-1, rowsAffected);
        } catch(ServiceException ex){
            assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INPUT_DATA);
        }

        try{
            int rowsAffected = userService.changeUserPassword(1L, "12345");
            assertEquals(-1, rowsAffected);
        } catch(ServiceException ex){
            assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INPUT_DATA);
        }
    }

    @Test(expected = ServiceException.class)
    public void changeUserPasswordNoSuchUser() throws Exception {
        LOGGER.debug("test: changeUserPasswordNoSuchUser()");

        try{
            userService.changeUserPassword(999L, "12345678");
        } catch(ServiceException ex){
            assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.NO_ACTIONS_MADE);
            throw ex;
        }
    }

    @Test
    public void deleteUserById() throws Exception {
        LOGGER.debug("test: deleteUserById()");

        List<User> users = userService.getAllUsers();

        if (users.size() > 0) {
            User user = users.get(0);
            user.setUserEmail("newUser@gmail.com");

            Long addedUserId = userService.addNewUser(user);
            assertEquals(users.size() + 1, userService.getAllUsers().size());

            UserTotals totalUsersOfThisKindBefore = userTotalsDao.getValue(user.getRole(),
                    (user.getLocalities() != null && user.getLocalities().size() > 0) ?
                            user.getLocalities().get(0) : null);

            int rowsAffected = userService.deleteUserById(addedUserId);
            assertEquals(1, rowsAffected);
            assertEquals(users.size(), userService.getAllUsers().size());

            UserTotals totalUsersOfThisKindAfter = userTotalsDao.getValue(user.getRole(),
                    (user.getLocalities() != null && user.getLocalities().size() > 0) ?
                            user.getLocalities().get(0) : null);

            try{
                userService.getUserByUserEmail("newUser@gmail.com");
            } catch (ServiceException ex){
                assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.NO_CALLING_DATA_FOUND);
            }

            if (totalUsersOfThisKindBefore.getCount() == 1){
                assertEquals(null, totalUsersOfThisKindAfter);
            } else if (totalUsersOfThisKindBefore.getCount() > 1){
                assertEquals(totalUsersOfThisKindBefore.getCount() - 1, totalUsersOfThisKindAfter.getCount());
            }
        }
    }

    @Test(expected = ServiceException.class)
    public void deleteUserByIdNoActionsMade() throws Exception {
        LOGGER.debug("test: deleteUserByIdNoActionsMade()");

        try {
            userService.deleteUserById(999L);
        } catch (ServiceException ex){
            assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.NO_ACTIONS_MADE);
            throw ex;
        }
    }

    @Test(expected = ServiceException.class)
    public void deleteUserIncorrectIndex() throws Exception {
        LOGGER.debug("test: deleteUserByIdNoActionsMade()");

        try {
            userService.deleteUserById(0L);
        } catch (ServiceException ex){
            assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INDEX);
            throw ex;
        }
    }

    @Test
    public void authenticateUser() throws Exception {
        LOGGER.debug("test: authenticateUser()");

        List<User> users = userService.getAllUsers();
        if (users.size() > 0) {
            User user = users.get(0);

            user.setUserEmail("someMagicEmail@gmail.com");
            user.setUserPassword("12345678");

            Long newUserId = userService.addNewUser(user);
            assertNotNull(newUserId);

            User resultUser = userService.authenticateUser("someMagicEmail@gmail.com", "12345678");
            assertNotNull(resultUser);

            //set data to initial
            int rowsAffected = userService.deleteUserById(newUserId);
            assertEquals(1, rowsAffected);
        }
    }

    @Test
    public void authenticateUserWrongEmail() throws Exception {
        LOGGER.debug("test: authenticateUserWrongEmail()");

        User user = userService.authenticateUser("someNonExistingUserEmail@gmail.com", "12345678");
        assertEquals(null, user);

        User user1 = userService.authenticateUser("om", "12345678");
        assertEquals(null, user1);
    }

    @Test
    public void authenticateUserWrongPasswordLength() throws Exception {
        LOGGER.debug("test: authenticateUserWrongPasswordLength()");

        User user = userService.authenticateUser("someNonExistingUserEmail@gmail.com", "12345");
        assertEquals(null, user);
    }

    @Test
    public void authenticateUserPasswordNotMatches() throws Exception {
        LOGGER.debug("test: authenticateUserPasswordNotMatches()");

        List<User> users = userService.getAllUsers();
        if (users.size() > 0) {
            User user = users.get(0);

            user.setUserEmail("someMagicEmail@gmail.com");
            user.setUserPassword("12345678");

            Long newUserId = userService.addNewUser(user);
            assertNotNull(newUserId);

            User resultUser = userService.authenticateUser("someMagicEmail@gmail.com", "87654321");
            assertNull(resultUser);

            //set data to initial
            int rowsAffected = userService.deleteUserById(newUserId);
            assertEquals(1, rowsAffected);
        }
    }

}
