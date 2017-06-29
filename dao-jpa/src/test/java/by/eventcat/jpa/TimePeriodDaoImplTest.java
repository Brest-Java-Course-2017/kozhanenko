package by.eventcat.jpa;

import by.eventcat.Event;
import by.eventcat.TimePeriod;
import by.eventcat.TimePeriodDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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
    private static final TimePeriod TIME_PERIOD = new TimePeriod(
            new Event(1), convertTimeFromStringToSeconds(TIME_IN_STRING),
            convertTimeFromStringToSeconds(TIME_IN_STRING1));
    private static final TimePeriod WRONG_TIME_PERIOD = new TimePeriod(
            new Event(999), convertTimeFromStringToSeconds(TIME_IN_STRING),
            convertTimeFromStringToSeconds(TIME_IN_STRING1));

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



//    @Test
//    public void getTimePeriodListOfCertainEventByEventIdWithNoTimePeriodData() throws Exception{
//        LOGGER.debug("test: getTimePeriodListOfCertainEventByEventIdFail()");
//        List<TimePeriod> timePeriods = timePeriodDao.getTimePeriodListOfCertainEventByEventId(new Event(6));
//        assertTrue(timePeriods.size() > 0);
//    }
//
//    @Test
//    public void getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime() throws Exception {
//        LOGGER.debug("test: getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime() ");
//        List<TimePeriod> timePeriods = timePeriodDao.
//                getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(BEGIN_IN_STRING, END_IN_STRING);
//        assertTrue(timePeriods.size() > 0);
//    }
//
//    @Test
//    public void getAllTimePeriodsOfCertainCategoryInTimeInterval() throws Exception{
//        LOGGER.debug("test: getAllTimePeriodsOfCertainCategoryInTimeInterval() ");
//        List<TimePeriod> timePeriods = timePeriodDao.
//                getAllTimePeriodsOfCertainCategoryInTimeInterval(new Category(2),
//                        convertTimeFromStringToSeconds(BEGIN_IN_STRING1), convertTimeFromStringToSeconds(END_IN_STRING1));
//        assertTrue(timePeriods.size() > 0);
//    }
//
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

//    @Test
//    public void addTimePeriodList() throws Exception {
//        LOGGER.debug("test: addTimePeriodList()");
//
//        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriods();
//        int quantityBefore = timePeriods.size();
//
//        int [] rowsAffectedArr = timePeriodDao.addTimePeriodList(TIME_PERIODS);
//        assertEquals(2, rowsAffectedArr.length);
//
//        timePeriods = timePeriodDao.getAllTimePeriods();
//        assertEquals(quantityBefore + 2, timePeriods.size());
//
//        TimePeriod newTimePeriod = timePeriods.get(timePeriods.size() - 2);
//        assertNotNull(newTimePeriod);
//        assertEquals(newTimePeriod.getBeginning(), TIME_PERIOD.getBeginning());
//
//        TimePeriod newTimePeriod1 = timePeriods.get(timePeriods.size() - 1);
//        assertNotNull(newTimePeriod1);
//        assertEquals(newTimePeriod1.getBeginning(), TIME_PERIOD1.getBeginning());
//    }
//
//    @Test
//    public void updateTimePeriod() throws Exception {
//        LOGGER.debug("test: updateTimePeriod()");
//
//        TimePeriod timePeriod = timePeriodDao.getTimePeriodById(1);
//        timePeriod.setBeginning(BEGIN1);
//
//        int count = timePeriodDao.updateTimePeriod(timePeriod);
//        assertEquals(1, count);
//
//        TimePeriod updatedTimePeriod = timePeriodDao.getTimePeriodById(timePeriod.getTimePeriodId());
//        assertEquals(BEGIN1, updatedTimePeriod.getBeginning());
//    }
//
//    @Test
//    public void deleteTimePeriod() throws Exception {
//        LOGGER.debug("test: updateTimePeriod()");
//
//        Integer timePeriodId = timePeriodDao.addTimePeriod(TIME_PERIOD);
//        assertNotNull(timePeriodId);
//
//        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriods();
//        int quantityBefore = timePeriods.size();
//
//        int count = timePeriodDao.deleteTimePeriod(timePeriodId);
//        assertEquals(1, count);
//
//        timePeriods = timePeriodDao.getAllTimePeriods();
//        assertEquals(quantityBefore - 1, timePeriods.size());
//    }
//
//    @Test
//    public void deleteTimePeriodsByEventId() throws Exception {
//        LOGGER.debug("test: deleteTimePeriodsByEventId()");
//
//        int[] count = timePeriodDao.addTimePeriodList(TIME_PERIODS1);
//        assertEquals(2, count.length);
//
//        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriods();
//        int quantityBefore = timePeriods.size();
//
//        int count1 = timePeriodDao.deleteTimePeriodsByEventId(new Event(6));
//        assertEquals(2, count1);
//
//        timePeriods = timePeriodDao.getAllTimePeriods();
//        assertEquals(quantityBefore - 2, timePeriods.size());
//    }


}
