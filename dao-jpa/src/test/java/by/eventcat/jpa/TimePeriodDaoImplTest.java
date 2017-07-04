package by.eventcat.jpa;

import by.eventcat.Category;
import by.eventcat.Event;
import by.eventcat.TimePeriod;
import by.eventcat.TimePeriodDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static by.eventcat.jpa.TimeConverter.*;
import static org.junit.Assert.assertTrue;

/**
 * TimePeriod JPA Dao Implementation Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-jpa-dao.xml"})
@Transactional
public class TimePeriodDaoImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private TimePeriodDao timePeriodDao;

    private static final String TIME_IN_STRING = "2017-03-13 22:49:49";
    private static final String TIME_IN_STRING1 = "2017-03-13 23:55:00";
    private static final String TIME_IN_STRING2 = "2017-06-13 23:55:00";
    private static final String TIME_IN_STRING3 = "2017-07-13 23:55:00";
    private static final String BEGINNING_IN_STRING = "2016-01-01 23:55:00";
    private static final String END_IN_STRING = "2017-12-31 23:55:00";
    private static final String END_IN_STRING1 = "2016-01-02 23:55:00";
    private static final TimePeriod TIME_PERIOD = new TimePeriod(
            new Event(1), convertTimeFromStringToSeconds(TIME_IN_STRING),
            convertTimeFromStringToSeconds(TIME_IN_STRING1));
    private static final TimePeriod TIME_PERIOD1 = new TimePeriod(4,
            new Event(1), convertTimeFromStringToSeconds(TIME_IN_STRING2),
            convertTimeFromStringToSeconds(TIME_IN_STRING3));
    private static final TimePeriod WRONG_TIME_PERIOD = new TimePeriod(1,
            new Event(999), convertTimeFromStringToSeconds(TIME_IN_STRING),
            convertTimeFromStringToSeconds(TIME_IN_STRING1));
    private static final TimePeriod WRONG_TIME_PERIOD1 = new TimePeriod(999,
            new Event(999), convertTimeFromStringToSeconds(TIME_IN_STRING),
            convertTimeFromStringToSeconds(TIME_IN_STRING1));
    private static final List<TimePeriod> TIME_PERIODS = new ArrayList<>();
    private static final List<TimePeriod> WRONG_TIME_PERIODS = new ArrayList<>();

    static {
        TIME_PERIODS.add(TIME_PERIOD);
        TIME_PERIODS.add(TIME_PERIOD1);
        WRONG_TIME_PERIODS.add(WRONG_TIME_PERIOD);
        WRONG_TIME_PERIODS.add(WRONG_TIME_PERIOD1);

    }

    @Test
    public void getTimePeriodById() throws Exception {
        LOGGER.debug("test: getTimePeriodById()");
        TimePeriod timePeriod = timePeriodDao.getTimePeriodById(1);
        assertNotNull(timePeriod);
        assertEquals(1, timePeriod.getTimePeriodId());
        assertEquals(4, timePeriod.getEvent().getEventId());
        assertEquals(convertTimeFromStringToSeconds(TIME_IN_STRING), timePeriod.getBeginning());
        assertEquals(convertTimeFromStringToSeconds(TIME_IN_STRING1), timePeriod.getEnd());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getTimePeriodByIdNoDataFound() throws Exception {
        LOGGER.debug("test: getTimePeriodByIdNoDataFound()");
        timePeriodDao.getTimePeriodById(999);
    }

    @Test
    public void getTimePeriodListOfCertainEventByEventId() throws Exception{
        LOGGER.debug("test: getTimePeriodListOfCertainEventByEventId()");
        List<TimePeriod> timePeriods = timePeriodDao.getTimePeriodListOfCertainEventByEventId(new Event(1));
        assertTrue(timePeriods.size() > 0);
    }

    @Test
    public void getTimePeriodListOfCertainEventByEventIdNoDateFound() throws Exception{
        LOGGER.debug("test: getTimePeriodListOfCertainEventByEventIdNoDateFound()");
        List<TimePeriod> timePeriods = timePeriodDao.getTimePeriodListOfCertainEventByEventId(new Event(999));
        assertTrue(timePeriods.size() == 0);
    }

    @Test
    public void getAllTimePeriods() throws Exception{
        LOGGER.debug("test: getAllTimePeriods()");
        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriods();
        assertTrue(timePeriods.size() > 0);
    }

    @Test
    public void getAllTimePeriodsByEventId() throws Exception {
        LOGGER.debug("test: getAllTimePeriodsByEventId()");
        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriodsByEventId(new Event(1));
        assertTrue(timePeriods.size() > 0);
    }

    @Test
    public void getAllTimePeriodsByEventIdNoDataFound() throws Exception {
        LOGGER.debug("test: getAllTimePeriodsByEventId()");
        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriodsByEventId(new Event(999));
        assertTrue(timePeriods.size() == 0);
    }

    @Test
    public void getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime() throws Exception {
        LOGGER.debug("test: getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime() ");
        List<TimePeriod> timePeriods = timePeriodDao.
                getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(BEGINNING_IN_STRING, END_IN_STRING);
        assertTrue(timePeriods.size() > 0);
    }

    @Test
    public void getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTimeNoDataFound() throws Exception {
        LOGGER.debug("test: getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime() ");
        List<TimePeriod> timePeriods = timePeriodDao.
                getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(BEGINNING_IN_STRING, END_IN_STRING1);
        assertTrue(timePeriods.size() == 0);
    }


    @Test
    public void getAllTimePeriodsOfCertainCategoryInTimeInterval() throws Exception{
        LOGGER.debug("test: getAllTimePeriodsOfCertainCategoryInTimeInterval() ");
        List<TimePeriod> timePeriods = timePeriodDao.
                getAllTimePeriodsOfCertainCategoryInTimeInterval(new Category(2),
                        convertTimeFromStringToSeconds(BEGINNING_IN_STRING), convertTimeFromStringToSeconds(END_IN_STRING));
        assertTrue(timePeriods.size() > 0);
    }

    @Test
    public void getAllTimePeriodsOfCertainCategoryInTimeIntervalNoDataFound() throws Exception{
        LOGGER.debug("test: getAllTimePeriodsOfCertainCategoryInTimeInterval() ");
        List<TimePeriod> timePeriods = timePeriodDao.
                getAllTimePeriodsOfCertainCategoryInTimeInterval(new Category(2),
                        convertTimeFromStringToSeconds(BEGINNING_IN_STRING), convertTimeFromStringToSeconds(END_IN_STRING1));
        assertTrue(timePeriods.size() == 0);
    }

    @Test
    public void addTimePeriod() throws Exception {
        LOGGER.debug("test: addTimePeriod()");

        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriods();
        int quantityBefore = timePeriods.size();

        int timePeriodId = timePeriodDao.addTimePeriod(TIME_PERIOD);
        assertNotNull(timePeriodId);
        assertTrue(timePeriodId > 0);

        timePeriods = timePeriodDao.getAllTimePeriods();
        assertEquals(quantityBefore + 1, timePeriods.size());

        TimePeriod newTimePeriod = timePeriodDao.getTimePeriodById(timePeriodId);
        assertNotNull(newTimePeriod);
        assertEquals(TIME_PERIOD.getBeginning(), newTimePeriod.getBeginning());
        assertEquals(TIME_PERIOD.getEnd(), newTimePeriod.getEnd());
    }

    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void addTimePeriodWithNotExistingEvent() throws Exception{
        LOGGER.debug("test: addTimePeriodWithNotExistingEvent()");
        Integer timePeriodId = timePeriodDao.addTimePeriod(WRONG_TIME_PERIOD);
        assertNotNull(timePeriodId);
    }

    @Test
    public void addTimePeriodList() throws Exception {
        LOGGER.debug("test: addTimePeriodList()");

        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriods();
        int quantityBefore = timePeriods.size();
        assertTrue(quantityBefore >= 0);

        int [] rowsAffectedArr = timePeriodDao.addTimePeriodList(TIME_PERIODS);
        assertEquals(2, rowsAffectedArr.length);

        timePeriods = timePeriodDao.getAllTimePeriods();
        assertEquals(quantityBefore + 2, timePeriods.size());

        TimePeriod newTimePeriod = timePeriods.get(timePeriods.size() - 2);
        assertNotNull(newTimePeriod);
        assertEquals(newTimePeriod.getBeginning(), TIME_PERIOD.getBeginning());

        TimePeriod newTimePeriod1 = timePeriods.get(timePeriods.size() - 1);
        assertNotNull(newTimePeriod1);
        assertEquals(newTimePeriod1.getBeginning(), TIME_PERIOD1.getBeginning());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void addTimePeriodListWrongEvents() throws Exception {
        LOGGER.debug("test: addTimePeriodListWrongEvents()");

        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriods();
        int quantityBefore = timePeriods.size();
        try{
            timePeriodDao.addTimePeriodList(WRONG_TIME_PERIODS);
        } catch(DataIntegrityViolationException ex){
            List<TimePeriod> timePeriods1 = timePeriodDao.getAllTimePeriods();
            int quantityAfter = timePeriods1.size();
            assertEquals(quantityBefore, quantityAfter);
            throw ex;
        }
    }
    
    @Test
    public void updateTimePeriod() throws Exception {
        LOGGER.debug("test: updateTimePeriod()");

        int count = timePeriodDao.updateTimePeriod(TIME_PERIOD1);
        assertEquals(1, count);

        TimePeriod updatedTimePeriod = timePeriodDao.getTimePeriodById(TIME_PERIOD1.getTimePeriodId());
        assertEquals(TIME_PERIOD1.getEvent().getEventId(), updatedTimePeriod.getEvent().getEventId());
        assertEquals(convertTimeFromStringToSeconds(TIME_IN_STRING2), updatedTimePeriod.getBeginning());
        assertEquals(convertTimeFromStringToSeconds(TIME_IN_STRING3), updatedTimePeriod.getEnd());
    }

    @Test
    public void updateTimePeriodWrongEvent() throws Exception {
        LOGGER.debug("test: updateTimePeriodWrongEvent()");

        int count = timePeriodDao.updateTimePeriod(WRONG_TIME_PERIOD);
        assertEquals(0, count);
    }

    @Test
    public void updateTimePeriodWrongTimePeriodId() throws Exception {
        LOGGER.debug("test: updateTimePeriodWrongTimePeriodId()");

        int count = timePeriodDao.updateTimePeriod(WRONG_TIME_PERIOD1);
        assertEquals(0, count);
    }

    @Test
    public void deleteTimePeriod() throws Exception {
        LOGGER.debug("test: deleteTimePeriod()");

        Integer timePeriodId = timePeriodDao.addTimePeriod(TIME_PERIOD);
        assertNotNull(timePeriodId);

        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriods();
        int quantityBefore = timePeriods.size();

        int count = timePeriodDao.deleteTimePeriod(timePeriodId);
        assertEquals(1, count);

        timePeriods = timePeriodDao.getAllTimePeriods();
        assertEquals(quantityBefore - 1, timePeriods.size());
    }

    @Test
    public void deleteTimePeriodNonExistingTimePeriod() throws Exception {
        LOGGER.debug("test: deleteTimePeriodNonExistingTimePeriod()");

        int count = timePeriodDao.deleteTimePeriod(999);
        assertEquals(0, count);
    }

    @Test
    public void deleteTimePeriodsByEventId() throws Exception {
        LOGGER.debug("test: deleteTimePeriodsByEventId()");

        int[] count = timePeriodDao.addTimePeriodList(TIME_PERIODS);
        assertEquals(2, count.length);

        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriods();
        int quantityBefore = timePeriods.size();

        int count1 = timePeriodDao.deleteTimePeriodsByEventId(new Event(1));
        assertTrue(count1 > 0);

        List<TimePeriod> timePeriods1 = timePeriodDao.getAllTimePeriods();
        int quantityAfter = timePeriods1.size();

        assertEquals(quantityBefore - count1, quantityAfter);
    }

    @Test
    public void deleteTimePeriodsByEventIdNoDataToDelete() throws Exception {
        LOGGER.debug("test: deleteTimePeriodsByEventIdNoDataToDelete()");

        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriods();
        int quantityBefore = timePeriods.size();

        int count1 = timePeriodDao.deleteTimePeriodsByEventId(new Event(999));
        assertTrue(count1 == 0);

        List<TimePeriod> timePeriods1 = timePeriodDao.getAllTimePeriods();
        int quantityAfter = timePeriods1.size();

        assertEquals(quantityBefore, quantityAfter);
    }

}
