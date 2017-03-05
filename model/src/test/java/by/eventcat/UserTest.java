package by.eventcat;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for User model (only getters testing)
 */
public class UserTest {
    @Test
    public void getUserId() throws Exception {
        User user = new User();
        user.setUserId(11);
        assertEquals(user.getUserId(), 11);
    }

    @Test
    public void getUserName() throws Exception {
        User user = new User();
        user.setUserName("Василий");
        assertEquals(user.getUserName(), "Василий");
    }

    @Test
    public void getLoginEmail() throws Exception {
        User user = new User();
        user.setLoginEmail("email@mail.ru");
        assertEquals(user.getLoginEmail(), "email@mail.ru");
    }

    @Test
    public void getPassword() throws Exception {
        User user = new User();
        user.setPassword("pass");
        assertEquals(user.getPassword(), "pass");
    }

}