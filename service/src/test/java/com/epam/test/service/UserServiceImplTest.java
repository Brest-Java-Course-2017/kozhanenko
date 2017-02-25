package com.epam.test.service;

import com.epam.test.dao.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mentor on 20.2.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-test.xml"})
@Transactional
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    private static final String USER_LOGIN_1 = "userLogin1";

    @Test
    public void getAllUsers() throws Exception {
        List<User> users = userService.getAllUsers();
        Assert.assertEquals("", 2, users.size());
    }

    @Test
    public void getUserById() throws Exception {
        // FIXME implement test
    }

    @Test
    public void getUserByLogin() throws Exception {
        User user = userService.getUserByLogin(USER_LOGIN_1);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getLogin());
        Assert.assertEquals(USER_LOGIN_1, user.getLogin());
    }

    @Test
    public void addUser() throws Exception {
        // FIXME implement test
    }

    @Test
    public void updateUser() throws Exception {
        // FIXME implement test
    }

    @Test
    public void deleteUser() throws Exception {
        // FIXME implement test
    }

}