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
 * UserTotals Service Implementation Mock Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-mock-spring-service-for-jpa-dao.xml"})
public class UserTotalsServiceImplMockTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserTotalsService userTotalsService;

    @Autowired
    private UserTotalsDao mockUserTotalsDao;

    private static final List<UserTotals> USER_TOTALS_EMPTY_LIST = new ArrayList<>();

    @After
    public void clean() {
        verify(mockUserTotalsDao);
        reset(mockUserTotalsDao);
    }

    @Test(expected = ServiceException.class)
    public void getUserTotalsEmptyResult() throws Exception {
        LOGGER.debug("mockTest: getUserTotalsEmptyResult()");
        expect(mockUserTotalsDao.getUserTotals()).andReturn(USER_TOTALS_EMPTY_LIST);
        replay(mockUserTotalsDao);
        try{
            userTotalsService.getUserTotals();
        } catch (ServiceException ex){
            assertEquals(ex.getCustomErrorCode(), CustomErrorCodes.NO_CALLING_DATA_FOUND);
            throw ex;
        }
    }

}
