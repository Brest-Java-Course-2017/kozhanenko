package com.epam.test.service;

import com.epam.test.dao.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-test.xml"})
@Transactional
public class UserServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

    private static final String USER_LOGIN_1 = "userLogin1";
    private static final User TEST_USER = new User("userLogin3", "userPassword3");

    @Test
    public void getAllUsers() throws Exception {
        LOGGER.debug("UserServiceImplTest: getAllUsers()");
        List<User> users = userService.getAllUsers();
        Assert.assertEquals("", 2, users.size());
    }

    @Test
    public void getUserById() throws Exception {
        LOGGER.debug("UserServiceImplTest: getUserById()");
        User user = userService.getUserById(1);
        Assert.assertNotNull(user);
        Assert.assertEquals(USER_LOGIN_1, user.getLogin());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserByIdWhenNoSuchUser(){
        LOGGER.debug("UserServiceImplTest: getUserByIdWhenNoSuchUser()");
        User user = userService.getUserById(100);
    }

    @Test
    public void getUserByLogin() throws Exception {
        LOGGER.debug("UserServiceImplTest: getUserByLogin()");
        User user = userService.getUserByLogin(USER_LOGIN_1);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getLogin());
        Assert.assertEquals(USER_LOGIN_1, user.getLogin());
    }

    @Test
    public void addUser() throws Exception {
        LOGGER.debug("UserServiceImplTest: addUser()");
        List<User> users = userService.getAllUsers();
        Integer quantityBefore = users.size();

        Integer userId = userService.addUser(TEST_USER);
        assertNotNull(userId);

        User newUser = userService.getUserById(userId);
        assertNotNull(newUser);
        assertTrue(TEST_USER.getLogin().equals(newUser.getLogin()));
        assertTrue(TEST_USER.getPassword().equals(newUser.getPassword()));
        assertNull(TEST_USER.getDescription());

        users = userService.getAllUsers();
        assertEquals(quantityBefore + 1, users.size());
    }

    @Test
    public void updateUser() throws Exception {
        LOGGER.debug("UserServiceImplTest: updateUser()");
        User user = userService.getUserById(1);
        user.setPassword("updated password");
        user.setDescription("updated description");

        int count = userService.updateUser(user);
        assertEquals(1, count);

        User updatedUser = userService.getUserById(user.getUserId());
        assertTrue(user.getLogin().equals(updatedUser.getLogin()));
        assertTrue(user.getPassword().equals(updatedUser.getPassword()));
        assertTrue(user.getDescription().equals(updatedUser.getDescription()));
    }

    @Test
    public void deleteUser() throws Exception {
        LOGGER.debug("UserServiceImplTest: deleteUser()");
        Integer userId = userService.addUser(TEST_USER);
        assertNotNull(userId);

        List<User> users = userService.getAllUsers();
        Integer quantityBefore = users.size();

        int count = userService.deleteUser(userId);
        assertEquals(1, count);


        users = userService.getAllUsers();
        assertEquals(quantityBefore - 1, users.size());
    }

}