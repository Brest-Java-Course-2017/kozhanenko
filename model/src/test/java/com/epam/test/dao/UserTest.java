package com.epam.test.dao;


import org.junit.Assert;

/**
 * Created by andrei on 13.2.17.
 *
 * this is our first class
 */
public class UserTest {

    private static final int USER_ID = 11;
    private static final String LOGIN = "petya";
    private static final String PASSWORD = "pass";
    private static final String DESCRIPTION = "some descr";


    @org.junit.Test
    public void getUserID() throws Exception {
        User user = new User();
        user.setUserId(11);
        Assert.assertEquals("User id", (Integer) USER_ID, user.getUserId());

    }

    @org.junit.Test
    public void getLogin() throws Exception {
        User user = new User();
        user.setLogin("petya");
        Assert.assertEquals("Login is", LOGIN, user.getLogin());
    }

    @org.junit.Test
    public void getPassword() throws Exception {
        User user = new User();
        user.setPassword("pass");
        Assert.assertEquals("Password is", PASSWORD, user.getPassword());
    }

    @org.junit.Test
    public void getDescription() throws Exception {
        User user = new User();
        user.setDescription("some descr");
        Assert.assertEquals("Description is", DESCRIPTION, user.getDescription());
    }
}