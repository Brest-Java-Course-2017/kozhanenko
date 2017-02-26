package com.epam.test.service;

import com.epam.test.dao.User;
import com.epam.test.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-test-mock.xml"})
public class UserServiceImplMockTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final User user = new User("userLogin3", "userPassword3");
    private static final User user1 = new User(4, "userLogin4", "userPassword4", "userDescription4");
    private static final List<User> users;

    static {
        users = new ArrayList<>();
        users.add(user);
        users.add(user1);
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao mockUserDao;

    @After
    public void clean() {
        verify(mockUserDao);
        reset(mockUserDao);
    }

    @Test
    public void testGetAllUsers(){
        LOGGER.debug("UserServiceImplMockTest: testGetAllUsers()");
        expect(mockUserDao.getAllUsers()).andReturn(users);
        replay(mockUserDao);
        List<User> receivedUsers = userService.getAllUsers();
        Assert.isTrue(receivedUsers.size() == 2);
    }

    @Test
    public void testGetUserById(){
        LOGGER.debug("UserServiceImplMockTest: testGetUserById()");
        expect(mockUserDao.getUserById(4)).andReturn(user1);
        replay(mockUserDao);
        User receivedUser = userService.getUserById(4);
        Assert.notNull(receivedUser);
        Assert.isTrue(4 == receivedUser.getUserId());
    }

    @Test
    public void testGetUserByLogin(){
        LOGGER.debug("UserServiceImplMockTest: testGetUserByLogin()");
        expect(mockUserDao.getUserByLogin("userLogin4")).andReturn(user1);
        replay(mockUserDao);
        User receivedUser = userService.getUserByLogin("userLogin4");
        Assert.notNull(receivedUser);
        Assert.isTrue("userLogin4".equals(receivedUser.getLogin()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetUserByLoginException() {
        LOGGER.debug("UserServiceImplMockTest: testGetUserByLoginException()");
        expect(mockUserDao.getUserByLogin(user.getLogin())).andThrow(new UnsupportedOperationException());
        replay(mockUserDao);
        userService.getUserByLogin(user.getLogin());
    }

    @Test
    public void testAddUser() throws Exception {
        LOGGER.debug("UserServiceImplMockTest: testAddUser()");
        expect(mockUserDao.addUser(new User("userLogin3", "userPassword3"))).andReturn(5);
        replay(mockUserDao);
        Integer id = userService.addUser(user);
        Assert.isTrue(id == 5);
    }

    @Test
    public void testUpdateUser(){
        LOGGER.debug("UserServiceImplMockTest: testUpdateUser()");
        expect(mockUserDao.updateUser(new User(4, "userLogin4", "userPassword4", "userDescription4"))).andReturn(4);
        replay(mockUserDao);
        Integer id = userService.updateUser(user1);
        Assert.isTrue(id == 4);
    }

    @Test
    public void deleteUser(){
        LOGGER.debug("UserServiceImplMockTest: deleteUser()");
        expect(mockUserDao.deleteUser(3)).andReturn(3);
        replay(mockUserDao);
        Integer id = userService.deleteUser(3);
        Assert.isTrue(id == 3);
    }





}
