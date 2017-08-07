package by.eventcat;

import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * UserService Implementation Mock Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-mock-spring-service-for-jpa-dao.xml"})
public class UserServiceImplMockTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao mockUserDao;

    private static final List<User> USER_EMPTY_LIST = new ArrayList<>();

    @After
    public void clean() {
        verify(mockUserDao);
        reset(mockUserDao);
    }

    @Test(expected = ServiceException.class)
    public void getAllUsersEmptyResult() throws Exception {
        LOGGER.debug("mockTest: getAllUsersEmptyResult()");
        expect(mockUserDao.getAllUsers()).andReturn(USER_EMPTY_LIST);
        replay(mockUserDao);
        try{
            userService.getAllUsers();
        } catch (ServiceException ex){
            assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.NO_CALLING_DATA_FOUND);
            throw ex;
        }
    }


}
