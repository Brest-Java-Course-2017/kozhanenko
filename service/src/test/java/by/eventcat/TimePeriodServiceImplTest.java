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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static by.eventcat.jpa.TimeConverter.*;

/**
 * Time Period Service Implementation Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:test-spring-service.xml"})
@ContextConfiguration(locations = {"classpath*:test-spring-service-for-jpa-dao-impl.xml"})
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
    private static final String END3 = "2017-12-31 00:00:00";
    private static final String WRONG_BEGINNING = "2017-03-13 22:49";
    private static final String WRONG_BEGINNING1 = "2017-99-13 22:49:00";
    private static final TimePeriod TIME_PERIOD_CONST = new TimePeriod(new Event(1),
            convertTimeFromStringToSeconds(BEGINNING), convertTimeFromStringToSeconds(END));
    private static final TimePeriod TIME_PERIOD = new TimePeriod(new Event(1),
            convertTimeFromStringToSeconds(BEGINNING), convertTimeFromStringToSeconds(END));
    private static final TimePeriod TIME_PERIOD1 = new TimePeriod(new Event(2),
            convertTimeFromStringToSeconds(BEGINNING1), convertTimeFromStringToSeconds(END1));
    private static final List<TimePeriod> TIME_PERIODS = new ArrayList<>();
    static {
        TIME_PERIODS.add(TIME_PERIOD);
        TIME_PERIODS.add(TIME_PERIOD1);
    }


    @Autowired
    private TimePeriodService timePeriodService;

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
    public void getTimePeriodListOfCertainEventByEventId() throws Exception{
        LOGGER.debug("test: getTimePeriodListOfCertainEventByEventId()");
        List<TimePeriod> timePeriods = timePeriodService.getTimePeriodListOfCertainEventByEventId(new Event(4));
        assertTrue(timePeriods.size() > 0);
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getTimePeriodListOfCertainEventByEventIdIncorrectIndex() throws Exception{
        LOGGER.debug("test: getTimePeriodListOfCertainEventByEventIdIncorrectIndex()");
        try{
            timePeriodService.getTimePeriodListOfCertainEventByEventId(new Event(-5));
        } catch(ServiceException ex) {
            assertEquals(CustomErrorCodes.INCORRECT_INDEX, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getTimePeriodListOfCertainEventByEventIdNoDataFound() throws Exception{
        LOGGER.debug("test: getTimePeriodListOfCertainEventByEventIdNoDataFound()");
        try{
           timePeriodService.getTimePeriodListOfCertainEventByEventId(new Event(99));
        } catch(ServiceException ex) {
            assertEquals(CustomErrorCodes.NO_CALLING_DATA_FOUND, ex.getCustomErrorCode());
            throw ex;
        }
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
    public  void  getAllTimePeriodsOfCertainCategoryInTimeInterval() throws Exception{
        LOGGER.debug("test: getAllTimePeriodsOfCertainCategoryInTimeInterval()");
        List<TimePeriod> timePeriods = timePeriodService.getAllTimePeriodsOfCertainCategoryInTimeInterval(
                new Category(2),
                convertTimeFromStringToSeconds(BEGINNING2),
                convertTimeFromStringToSeconds(END3)
        );
        assertTrue(timePeriods.size() > 0);
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public  void  getAllTimePeriodsOfCertainCategoryInTimeIntervalWrongInputData() throws Exception{
        LOGGER.debug("test: getAllTimePeriodsOfCertainCategoryInTimeIntervalWrongInputData()");
        try{
            timePeriodService.getAllTimePeriodsOfCertainCategoryInTimeInterval(
                    new Category(2),
                    0,
                    convertTimeFromStringToSeconds(END3)
            );
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_INPUT_DATA, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public  void  getAllTimePeriodsOfCertainCategoryInTimeIntervalNoDataFound() throws Exception{
        LOGGER.debug("test: getAllTimePeriodsOfCertainCategoryInTimeIntervalNoDataFound()");
        try{
            timePeriodService.getAllTimePeriodsOfCertainCategoryInTimeInterval(
                    new Category(999),
                    convertTimeFromStringToSeconds(BEGINNING2),
                    convertTimeFromStringToSeconds(END3)
            );
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.NO_CALLING_DATA_FOUND, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test
    public void addTimePeriod() throws Exception {
        LOGGER.debug("test: addTimePeriod()");

        List<TimePeriod> timePeriods = timePeriodService.getAllTimePeriods();
        Integer quantityBefore = timePeriods.size();

        Integer timePeriodId = timePeriodService.addTimePeriod(TIME_PERIOD_CONST);
        assertNotNull(timePeriodId);
        timePeriods = timePeriodService.getAllTimePeriods();
        assertEquals(quantityBefore + 1, timePeriods.size());

        TimePeriod newTimePeriod = timePeriodService.getTimePeriodById(timePeriodId);
        assertNotNull(newTimePeriod);
        assertEquals(TIME_PERIOD_CONST.getEvent().getEventId(), newTimePeriod.getEvent().getEventId());
        assertEquals(TIME_PERIOD_CONST.getBeginning(), newTimePeriod.getBeginning());
        assertEquals(TIME_PERIOD_CONST.getEnd(), newTimePeriod.getEnd());
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void addTimePeriodWrongInputData() throws Exception {
        LOGGER.debug("test: addTimePeriodWrongInputData()");
        TIME_PERIOD.setEvent(new Event(-1));
        try{
            timePeriodService.addTimePeriod(TIME_PERIOD);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_INPUT_DATA, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void addTimePeriodWithNonExistingEvent() throws Exception {
        LOGGER.debug("test: addTimePeriodWrongInputData()");
        TIME_PERIOD.getEvent().setEventId(999);
        try{
            timePeriodService.addTimePeriod(TIME_PERIOD);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INPUT_INDEX_OF_NON_EXISTING_EVENT, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test
    public void addTimePeriodList() throws Exception {
        LOGGER.debug("test: addTimePeriodList()");

        List<TimePeriod> timePeriods = timePeriodService.getAllTimePeriods();
        Integer quantityBefore = timePeriods.size();

        int [] res = timePeriodService.addTimePeriodList(TIME_PERIODS);
        for (int val: res) assertEquals(1, val);
        timePeriods = timePeriodService.getAllTimePeriods();
        assertEquals(quantityBefore + 2, timePeriods.size());
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void addTimePeriodListIncorrectInputData() throws Exception {
        LOGGER.debug("test: addTimePeriodListIncorrectInputData()");
        TIME_PERIODS.get(0).getEvent().setEventId(-1);
        try{
            timePeriodService.addTimePeriodList(TIME_PERIODS);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_INPUT_DATA, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void addTimePeriodListWithNonExistingEvent() throws Exception {
        LOGGER.debug("test: ddTimePeriodListWithNonExistingEvent()");
        TIME_PERIODS.get(0).getEvent().setEventId(999);
        try{
            timePeriodService.addTimePeriodList(TIME_PERIODS);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INPUT_INDEX_OF_NON_EXISTING_EVENT, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test
    public void updateTimePeriod() throws Exception {
        LOGGER.debug("test: updateTimePeriod()");

        TimePeriod timePeriod = timePeriodService.getTimePeriodById(1);
        timePeriod.setBeginning(convertTimeFromStringToSeconds(BEGINNING1));

        int count = timePeriodService.updateTimePeriod(timePeriod);
        assertEquals(1, count);

        TimePeriod updatedTimePeriod = timePeriodService.getTimePeriodById(1);
        assertEquals(timePeriod.getBeginning(), (updatedTimePeriod.getBeginning()));
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void updateTimePeriodIncorrectInputData() throws Exception {
        LOGGER.debug("test: updateTimePeriodIncorrectInputData()");

        TimePeriod timePeriod = timePeriodService.getTimePeriodById(1);
        timePeriod.setBeginning(-10000000);
        try{
            timePeriodService.updateTimePeriod(timePeriod);
        }catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_INPUT_DATA, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test
    public void deleteTimePeriodById() throws Exception {
        LOGGER.debug("test: deleteTimePeriodById()");

        Integer addedCategoryId = timePeriodService.addTimePeriod(TIME_PERIOD);
        assertNotNull(addedCategoryId);
        List<TimePeriod> timePeriods = timePeriodService.getAllTimePeriods();
        Integer quantityBefore = timePeriods.size();
        int count = timePeriodService.deleteTimePeriodById(addedCategoryId);
        assertEquals(1, count);
        timePeriods = timePeriodService.getAllTimePeriods();
        assertEquals(quantityBefore - 1, timePeriods.size());
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void deleteTimePeriodByIdIncorrectIndex() throws Exception {
        LOGGER.debug("test: deleteTimePeriodByIdIncorrectIndex() ");

        try{
            timePeriodService.deleteTimePeriodById(0);
        }catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_INDEX, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void deleteTimePeriodByIdNonExistingIndex() throws Exception {
        LOGGER.debug("test: deleteTimePeriodByIdIncorrectIndex() ");

        try{
            timePeriodService.deleteTimePeriodById(999);
        }catch (ServiceException ex){
            assertEquals(CustomErrorCodes.NO_ACTIONS_MADE, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test
    public void deleteTimePeriodsByEventId() throws Exception {
        LOGGER.debug("test: deleteTimePeriodsByEventId()");

        TIME_PERIOD.getEvent().setEventId(3);
        Integer addedCategoryId = timePeriodService.addTimePeriod(TIME_PERIOD);
        assertNotNull(addedCategoryId);

        int totalBefore = timePeriodService.getAllTimePeriods().size();

        List<TimePeriod> timePeriods1 = timePeriodService.getAllTimePeriodsByEventId(new Event(3));
        int totalToDelete = timePeriods1.size();
        int count = timePeriodService.deleteTimePeriodsByEventId(new Event(3));
        assertEquals(totalToDelete, count);

        int totalAfter = timePeriodService.getAllTimePeriods().size();
        assertEquals(totalBefore - totalToDelete, totalAfter);
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void deleteTimePeriodsByEventIdIncorrectIndex() throws Exception {
        LOGGER.debug("test: deleteTimePeriodsByEventIdIncorrectIndex()");

        try{
            timePeriodService.deleteTimePeriodsByEventId(new Event(-3));
        }catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_INDEX, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void deleteTimePeriodsByEventIdNonExistingIndex() throws Exception {
        LOGGER.debug("test: deleteTimePeriodsByEventIdNonExistingIndex()");

        try{
            timePeriodService.deleteTimePeriodsByEventId(new Event(999));
        }catch (ServiceException ex){
            assertEquals(CustomErrorCodes.NO_ACTIONS_MADE, ex.getCustomErrorCode());
            throw ex;
        }
    }



}