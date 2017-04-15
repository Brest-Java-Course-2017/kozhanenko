package by.eventcat;

import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;
import static by.eventcat.TimeConverter.*;

/**
 * Time Period Service Implementation Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-service.xml"})
@Transactional
public class TimePeriodServiceImplTest {

    private Logger LOGGER = LogManager.getLogger();

    private static final int EVENT_ID = 4;
    private static final String BEGINNING = "2017-03-13 22:49:49";
    private static final String END = "2017-03-13 23:55:00";
    private static final String BEGINNING1 = "2017-03-14 00:00:00";
    private static final String END1 = "2017-03-14 18:16:00";
    private static final String BEGINNING2 = "2016-03-14 00:00:00";
    private static final String END2 = "2016-03-14 18:16:00";
    private static final String WRONG_BEGINNING = "2017-03-13 22:49";
    private static final String WRONG_BEGINNING1 = "2017-99-13 22:49:00";

    @Autowired
    private TimePeriodServiceImpl timePeriodService;

    @Test
    public void getTimePeriodById() throws Exception {
        LOGGER.debug("test: getTimePeriodById()");

        TimePeriod timePeriod = timePeriodService.getTimePeriodById(1);
        assertNotNull(timePeriod);
        assertEquals(EVENT_ID, timePeriod.getEvent().getEventId());
        assertEquals(BEGINNING, convertTimeFromSecondsToString(timePeriod.getBeginning()));
        assertEquals(END, convertTimeFromSecondsToString(timePeriod.getEnd()));
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getTimePeriodByIdWrongIndex() throws Exception {
        LOGGER.debug("test: getTimePeriodByIdWrongIndex()");
        try {
            timePeriodService.getTimePeriodById(-1);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_INDEX, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getTimePeriodByIdNoDataFound() throws Exception {
        LOGGER.debug("test: getTimePeriodByIdNoDataFound()");
        try {
            timePeriodService.getTimePeriodById(999);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.NO_CALLING_DATA_FOUND, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test
    public void getAllTimePeriods() throws Exception {
        LOGGER.debug("test: getAllTimePeriods()");

        List<TimePeriod> timePeriods = timePeriodService.getAllTimePeriods();
        assertTrue(timePeriods.size() > 0);
    }

    @Test
    public void getAllTimePeriodsByEventId() throws Exception {
        LOGGER.debug("test: getAllTimePeriodsByEventId()");

        List<TimePeriod> timePeriods = timePeriodService.getAllTimePeriodsByEventId(new Event(4));
        assertTrue(timePeriods.size() == 1);
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getAllTimePeriodsByEventIdEmptyResult() throws Exception {
        LOGGER.debug("test: getAllTimePeriodsByEventIdEmptyResult()");
        timePeriodService.getAllTimePeriodsByEventId(new Event(99));
    }

    @Test
    public void getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime() throws Exception {
        LOGGER.debug("test: getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime()");
        List<TimePeriod> timePeriods = timePeriodService.getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(BEGINNING1, END1);
        assertTrue(timePeriods.size() > 0);
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTimeWrongBeginning() throws Exception {
        LOGGER.debug("test: getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTimeWrongBeginning()");
        try{
            timePeriodService.getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(WRONG_BEGINNING, END1);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_DATE_FORMAT, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTimeWrongBeginning1() throws Exception {
        LOGGER.debug("test: getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTimeWrongBeginning()");
        try{
            timePeriodService.getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(WRONG_BEGINNING1, END1);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_DATE, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTimeWrongParameters() throws Exception {
        LOGGER.debug("test: getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTimeWrongBeginning()");
        try{
            timePeriodService.getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(END, BEGINNING);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_REQUEST_PARAMETERS, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTimeNoDataFound() throws Exception {
        LOGGER.debug("test: getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTimeWrongBeginning()");
        try{
            timePeriodService.getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(BEGINNING2, END2);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.NO_CALLING_DATA_FOUND, ex.getCustomErrorCode());
            throw ex;
        }
    }



    @Test
    public void addTimePeriod() throws Exception {
        LOGGER.debug("test: addTimePeriod()");
    }

    @Test
    public void addTimePeriodList() throws Exception {
        LOGGER.debug("test: addTimePeriodList()");
    }

    @Test
    public void updateTimePeriod() throws Exception {
        LOGGER.debug("test: updateTimePeriod()");
    }

    @Test
    public void deleteTimePeriod() throws Exception {
        LOGGER.debug("test: deleteTimePeriod()");
    }

    @Test
    public void deleteTimePeriodsByEventId() throws Exception {
        LOGGER.debug("test: deleteTimePeriodsByEventId())");
    }

}