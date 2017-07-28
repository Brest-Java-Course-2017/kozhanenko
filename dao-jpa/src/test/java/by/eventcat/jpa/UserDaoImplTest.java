package by.eventcat.jpa;

import by.eventcat.User;
import by.eventcat.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


}
