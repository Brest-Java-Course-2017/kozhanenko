package by.eventcat;

import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;

/**
 * TimePeriodServiceImplMockTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-mock-spring-service.xml"})
public class TimePeriodServiceImplMockTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private TimePeriodService timePeriodService;

    @Autowired
    private TimePeriodDao mockTimePeriodDao;

    private static final Category CATEGORY = new Category(1);
    private static final Event EVENT = new Event(1);
    private static final long BEGINNING = 123123123L;
    private static final long END = 123654848L;
    private static final TimePeriod TIME_PERIOD = new TimePeriod(1, new Event(1), BEGINNING, END);
    private static final TimePeriod TIME_PERIOD1 = new TimePeriod(2, new Event(1), BEGINNING, END);
    private static final List<TimePeriod> TIME_PERIODS = new ArrayList<>();
    private static final List<TimePeriod> TIME_PERIODS1 = new ArrayList<>();
    private static final int [] ROWS_AFFECTED = new int[]{1, 1};

    static {
        TIME_PERIODS.add(TIME_PERIOD);
        TIME_PERIODS.add(TIME_PERIOD1);
    }

    @After
    public void clean() {
        verify(mockTimePeriodDao);
        reset(mockTimePeriodDao);
    }

    @Test
    public void getAllTimePeriodsOfCertainCategoryInTimeInterval() throws Exception {
        LOGGER.debug("mockTest: getAllTimePeriodsOfCertainCategoryInTimeInterval()");
        expect(mockTimePeriodDao.getAllTimePeriodsOfCertainCategoryInTimeInterval(CATEGORY, BEGINNING, END))
                .andReturn(TIME_PERIODS);
        replay(mockTimePeriodDao);
        List<TimePeriod> timePeriods = timePeriodService
                .getAllTimePeriodsOfCertainCategoryInTimeInterval(CATEGORY, BEGINNING, END);
        Assert.assertTrue(timePeriods.size() > 0);
    }

    @Test(expected = ServiceException.class)
    public void getAllTimePeriodsOfCertainCategoryInTimeIntervalNoDataFound() throws Exception{
        LOGGER.debug("mockTest: getAllTimePeriodsOfCertainCategoryInTimeIntervalNoDataFound()");
        expect(mockTimePeriodDao.getAllTimePeriodsOfCertainCategoryInTimeInterval(CATEGORY, BEGINNING, END))
                .andReturn(TIME_PERIODS1);
        replay(mockTimePeriodDao);
        try{
            timePeriodService.getAllTimePeriodsOfCertainCategoryInTimeInterval(CATEGORY, BEGINNING, END);
        } catch(ServiceException ex){
            Assert.assertTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.NO_CALLING_DATA_FOUND));
            throw ex;
        }
    }

    @Test
    public void getTimePeriodListOfCertainEventByEventId() throws Exception {
        LOGGER.debug("mockTest: getTimePeriodListOfCertainEventByEventId()");
        expect(mockTimePeriodDao.getTimePeriodListOfCertainEventByEventId(EVENT)).andReturn(TIME_PERIODS);
        replay(mockTimePeriodDao);
        List<TimePeriod> timePeriods = timePeriodService.getTimePeriodListOfCertainEventByEventId(EVENT);
        Assert.assertTrue(timePeriods.size() > 0);
    }

    @Test(expected = ServiceException.class)
    public void getTimePeriodListOfCertainEventByEventIdNoDataFound() throws Exception{
        LOGGER.debug("mockTest: getTimePeriodListOfCertainEventByEventIdNoDataFound()");
        expect(mockTimePeriodDao.getTimePeriodListOfCertainEventByEventId(EVENT)).andReturn(TIME_PERIODS1);
        replay(mockTimePeriodDao);
        try{
            timePeriodService.getTimePeriodListOfCertainEventByEventId(EVENT);
        } catch(ServiceException ex){
            Assert.assertTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.NO_CALLING_DATA_FOUND));
            throw ex;
        }
    }

    @Test
    public void addTimePeriodList() throws Exception {
        LOGGER.debug("mockTest: addTimePeriodList()");
        expect(mockTimePeriodDao.addTimePeriodList(TIME_PERIODS)).andReturn(ROWS_AFFECTED);
        replay(mockTimePeriodDao);
        int[] rowsAffected = timePeriodService.addTimePeriodList(TIME_PERIODS);
        Assert.assertTrue(rowsAffected.length == TIME_PERIODS.size());
    }

    @Test(expected = ServiceException.class)
    public void addTimePeriodListIntegrityViolation() throws Exception {
        LOGGER.debug("mockTest: addTimePeriodListIntegrityViolation()");
        expect(mockTimePeriodDao.addTimePeriodList(TIME_PERIODS)).andThrow(new DataIntegrityViolationException(""));
        replay(mockTimePeriodDao);
        try{
            timePeriodService.addTimePeriodList(TIME_PERIODS);
        } catch(ServiceException ex){
            Assert.assertTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.INPUT_INDEX_OF_NON_EXISTING_EVENT));
            throw ex;
        }
    }

    @Test
    public void  deleteTimePeriodsByEventId() throws Exception {
        LOGGER.debug("mockTest:  deleteTimePeriodsByEventId()");
        expect(mockTimePeriodDao.deleteTimePeriodsByEventId(EVENT)).andReturn(2);
        replay(mockTimePeriodDao);
        int rowsAffected = timePeriodService.deleteTimePeriodsByEventId(EVENT);
        Assert.assertTrue(rowsAffected > 0);
    }

    @Test(expected = ServiceException.class)
    public void  deleteTimePeriodsByEventIdNoDataDeleted() throws Exception {
        LOGGER.debug("mockTest:  deleteTimePeriodsByEventIdNoDataDeleted()");
        expect(mockTimePeriodDao.deleteTimePeriodsByEventId(EVENT)).andReturn(0);
        replay(mockTimePeriodDao);
        try{
            timePeriodService.deleteTimePeriodsByEventId(EVENT);
        } catch(ServiceException ex){
            Assert.assertTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.NO_ACTIONS_MADE));
            throw ex;
        }
    }

}
