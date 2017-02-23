package com.epam.test.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class UserDaoImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String USER_LOGIN_1 = "userLogin1";

    // sample user object for tests.
    private static final User user = new User("userLogin3", "userPassword3");

    @Autowired
    UserDao userDao;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        LOGGER.error("execute: setUpBeforeClass()");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        LOGGER.error("execute: tearDownAfterClass()");
    }


    @Before
    public void beforeTest() {
        LOGGER.error("execute: beforeTest()");
    }

    @After
    public void afterTest() {
        LOGGER.error("execute: afterTest()");
    }

    @Test
    public void getAllUsersTest() throws Exception {

        LOGGER.debug("test: getAllUsers()");
        List<User> users = userDao.getAllUsers();
        assertTrue(users.size() > 0);
    }

    //@Ignore
    @Test
    public void getUserByIdTest() throws Exception {

        LOGGER.debug("test: getUserById()");
        User user = userDao.getUserById(1);
        assertNotNull(user);
        assertEquals(USER_LOGIN_1, user.getLogin());
    }

    @Test
    public void getUserByLoginTest() throws Exception {

        LOGGER.debug("test: getUserByLogin()");
        User user = userDao.getUserByLogin(USER_LOGIN_1);
        assertNotNull(user);
        assertEquals(USER_LOGIN_1, user.getLogin());
    }

    @Test
    public void testAddUser() throws Exception {

        LOGGER.debug("test: addUser()");

        List<User> users = userDao.getAllUsers();
        Integer quantityBefore = users.size();

        Integer userId = userDao.addUser(user);
        assertNotNull(userId);

        User newUser = userDao.getUserById(userId);
        assertNotNull(newUser);
        assertTrue(user.getLogin().equals(newUser.getLogin()));
        assertTrue(user.getPassword().equals(newUser.getPassword()));
        assertNull(user.getDescription());

        users = userDao.getAllUsers();
        assertEquals(quantityBefore + 1, users.size());
    }

    @Test(expected = org.springframework.dao.DuplicateKeyException.class)
    public void testAddDuplicateUser() throws Exception {
        LOGGER.debug("test: testAddDuplicateUser()");
        User xUser = new User("userLogin3", "userPassword3");
        xUser.setUserId(1);
        userDao.addUser(xUser);
    }

    @Test
    public void testUpdateUser() throws Exception {
        LOGGER.debug("test: updateUser()");
        User user = userDao.getUserById(1);
        user.setPassword("updated password");
        user.setDescription("updated description");

        int count = userDao.updateUser(user);
        assertEquals(1, count);

        User updatedUser = userDao.getUserById(user.getUserId());
        assertTrue(user.getLogin().equals(updatedUser.getLogin()));
        assertTrue(user.getPassword().equals(updatedUser.getPassword()));
        assertTrue(user.getDescription().equals(updatedUser.getDescription()));
    }

    @Test
    public void testDeleteUser() throws Exception {

        LOGGER.debug("test: deleteUser()");

        Integer userId = userDao.addUser(user);
        assertNotNull(userId);

        List<User> users = userDao.getAllUsers();
        Integer quantityBefore = users.size();

        int count = userDao.deleteUser(userId);
        assertEquals(1, count);


        users = userDao.getAllUsers();
        assertEquals(quantityBefore - 1, users.size());
    }
}