
package com.epam.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
public class UserDaoImplTest {

    @Autowired
    UserDao userDao;

    @Test
    public void getAllUsersTest() throws Exception {

        List<User> users = userDao.getAllUsers();
        assertTrue(users.size() >= 2);
    }

    @Test
    public void getUserByIdTest() throws Exception {

        User user = userDao.getUserById(1);
        assertNotNull(user);
        assertEquals("userLogin1", user.getLogin());
    }

    @Test
    public void addUserTest () throws Exception{
        User user = new User("Some login", "Some password", "some description");
        Integer res = userDao.addUser(user);
        assertEquals(res, new Integer(1));
    }

    @Test
    public void updateUserTest () throws Exception{
        User user = new User(1, "Some login", "Some password", "some description");
        userDao.updateUser(user);
        User userUpdated = userDao.getUserById(1);
        assertEquals(user.getPassword(), userUpdated.getPassword());
    }

    @Test
    public void deleteUserTest () throws Exception{
        userDao.deleteUser(1);
        User user = userDao.getUserById(1);
        assertNull(user);
    }


}