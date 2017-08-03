package by.eventcat;

import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
                userService.addNewUser(userToAdd);
            } catch(ServiceException ex) {
                assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INPUT_DATA);
            }
            userToAdd.setUserEmail("rr@dd.com");

            userToAdd.setUserPassword("123456");
            try{
                userService.addNewUser(userToAdd);
            } catch(ServiceException ex) {
                assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INPUT_DATA);
            }
            userToAdd.setUserPassword("12345678");

            userToAdd.setRole(null);
            try{
                userService.addNewUser(userToAdd);
            } catch(ServiceException ex) {
                assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INPUT_DATA);
            }
            userToAdd.setRole(UserRole.CITY_ADMIN);

            userToAdd.setLocalities(null);
            try{
                userService.addNewUser(userToAdd);
            } catch(ServiceException ex) {
                assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INPUT_DATA);
            }
            userToAdd.setLocalities(new ArrayList<Locality>());
        }
    }

    @Test
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
            }
        }
    }

    @Test
    public void updateUser() throws Exception {
        LOGGER.debug("test: updateUser()");

        List<User> users = userService.getAllUsers();
        if (users.size() > 0){
            User userToUpdate = users.get(0);
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
        }
    }

    @Test
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
                userService.updateUser(userToUpdate);
            } catch (ServiceException ex){
                assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INPUT_DATA);
            }
            userToUpdate.setUserEmail("normalMail@gmail.com");

            userToUpdate.setRole(null);
            try{
                userService.updateUser(userToUpdate);
            } catch (ServiceException ex){
                assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INPUT_DATA);
            }

            userToUpdate.setRole(UserRole.CITY_ADMIN);
            userToUpdate.setLocalities(new ArrayList<>());
            try{
                userService.updateUser(userToUpdate);
            } catch (ServiceException ex){
                assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INPUT_DATA);
            }
            userToUpdate.setLocalities(null);
            try{
                userService.updateUser(userToUpdate);
            } catch (ServiceException ex){
                assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INPUT_DATA);
            }

        }
    }

    @Test
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
            }

        }
    }

    @Test
    public void changeUserPassword() throws Exception {
        LOGGER.debug("test: changeUserPassword()");

        List<User> users = userService.getAllUsers();
        if (users.size() > 0) {
            User userToUpdate = users.get(0);
            String passwordBefore = userToUpdate.getUserPassword();

            int rowsAffected = userService.changeUserPassword(userToUpdate.getUserId(), "newPassword");
            assertEquals(1, rowsAffected);

            User updatedUser = userService.getUserById(userToUpdate.getUserId());

            assertNotEquals(passwordBefore, updatedUser.getUserPassword());
        }
    }

    @Test
    public void changeUserPasswordIncorrectInputData() throws Exception {
        LOGGER.debug("test: changeUserPasswordIncorrectInputData()");

        try{
            userService.changeUserPassword(0L, "12345678");
        } catch(ServiceException ex){
            assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INPUT_DATA);
        }

        try{
            userService.changeUserPassword(1L, "12345");
        } catch(ServiceException ex){
            assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.INCORRECT_INPUT_DATA);
        }
    }

    @Test
    public void changeUserPasswordNoSuchUser() throws Exception {
        LOGGER.debug("test: changeUserPasswordNoSuchUser()");

        try{
            userService.changeUserPassword(999L, "12345678");
        } catch(ServiceException ex){
            assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.NO_ACTIONS_MADE);
        }
    }








    }
