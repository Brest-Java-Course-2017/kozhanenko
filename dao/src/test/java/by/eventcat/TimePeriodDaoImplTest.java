package by.eventcat;

import static by.eventcat.TimeConverter.*;

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

/**
 * Time Period Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class TimePeriodDaoImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private TimePeriodDao timePeriodDao;

    private static final String BEGIN_IN_STRING = "2017-03-14 10:55:00";
    private static final String BEGIN_IN_STRING1 = "2017-01-01 00:00:00";
    private static final long BEGIN = convertTimeFromStringToSeconds(BEGIN_IN_STRING);
    private static final String END_IN_STRING = "2017-03-14 22:30:00";
    private static final String END_IN_STRING1 = "2017-12-31 00:00:00";
    private static final long END = convertTimeFromStringToSeconds(END_IN_STRING);
    private static final long BEGIN1 = convertTimeFromStringToSeconds("2017-03-13 22:52:00");
    private static final TimePeriod TIME_PERIOD = new TimePeriod(new Event(1), BEGIN, END);
    private static final TimePeriod TIME_PERIOD1 = new TimePeriod(new Event(1), BEGIN, END);
    private static final TimePeriod TIME_PERIOD2 = new TimePeriod(new Event(6), BEGIN, END);
    private static final TimePeriod TIME_PERIOD3 = new TimePeriod(new Event(6), BEGIN, END);
    private static final List<TimePeriod> TIME_PERIODS = new ArrayList<>();
    private static final List<TimePeriod> TIME_PERIODS1 = new ArrayList<>();
    static {
        TIME_PERIODS.add(TIME_PERIOD);
        TIME_PERIODS.add(TIME_PERIOD1);
        TIME_PERIODS1.add(TIME_PERIOD2);
        TIME_PERIODS1.add(TIME_PERIOD3);
    }

    @Test
    public void getTimePeriodById() throws Exception {
        LOGGER.debug("test: getTimePeriodById()");
        TimePeriod timePeriod = timePeriodDao.getTimePeriodById(1);
        assertNotNull(timePeriod);
        assertEquals(1, timePeriod.getTimePeriodId());
        assertEquals(4, timePeriod.getEvent().getEventId());
        assertEquals(convertTimeFromStringToSeconds("2017-03-13 22:49:49"), timePeriod.getBeginning());
        assertEquals(convertTimeFromStringToSeconds("2017-03-13 23:55:00"), timePeriod.getEnd());
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
        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriodsByEventId(new Event(4));
        assertTrue(timePeriods.size() > 0);
    }

    @Test
    public void getTimePeriodListOfCertainEventByEventId() throws Exception{
        LOGGER.debug("test: getTimePeriodListOfCertainEventByEventId()");
        List<TimePeriod> timePeriods = timePeriodDao.getTimePeriodListOfCertainEventByEventId(new Event(1));
        assertTrue(timePeriods.size() > 0);
    }

    @Test
    public void getTimePeriodListOfCertainEventByEventIdWithNoTimePeriodData() throws Exception{
        LOGGER.debug("test: getTimePeriodListOfCertainEventByEventIdFail()");
        List<TimePeriod> timePeriods = timePeriodDao.getTimePeriodListOfCertainEventByEventId(new Event(6));
        assertTrue(timePeriods.size() > 0);
    }

    @Test
    public void getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime() throws Exception {
        LOGGER.debug("test: getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime() ");
        List<TimePeriod> timePeriods = timePeriodDao.
                getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(BEGIN_IN_STRING, END_IN_STRING);
        assertTrue(timePeriods.size() > 0);
    }

    @Test
    public void getAllTimePeriodsOfCertainCategoryInTimeInterval() throws Exception{
        LOGGER.debug("test: getAllTimePeriodsOfCertainCategoryInTimeInterval() ");
        List<TimePeriod> timePeriods = timePeriodDao.
                getAllTimePeriodsOfCertainCategoryInTimeInterval(new Category(2),
                        convertTimeFromStringToSeconds(BEGIN_IN_STRING1), convertTimeFromStringToSeconds(END_IN_STRING1));
        assertTrue(timePeriods.size() > 0);
    }

    @Test
    public void addTimePeriod() throws Exception {
        LOGGER.debug("test: addTimePeriod()");

        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriods();
        int quantityBefore = timePeriods.size();

        int timePeriodId = timePeriodDao.addTimePeriod(TIME_PERIOD);
        assertNotNull(timePeriodId);

        timePeriods = timePeriodDao.getAllTimePeriods();
        assertEquals(quantityBefore + 1, timePeriods.size());

        TimePeriod newTimePeriod = timePeriodDao.getTimePeriodById(timePeriodId);
        assertNotNull(newTimePeriod);
        assertEquals(newTimePeriod.getBeginning(), TIME_PERIOD.getBeginning());
        assertEquals(newTimePeriod.getEnd(), TIME_PERIOD.getEnd());
    }

    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void addTimePeriodWithNotExistingEvent() throws Exception{
        LOGGER.debug("test: addTimePeriodWithNotExistingEvent()");
        TimePeriod timePeriod = timePeriodDao.getTimePeriodById(1);
        assertNotNull(timePeriod);
        timePeriod.setEvent(new Event(999));
        Integer timePeriodId = timePeriodDao.addTimePeriod(timePeriod);
        assertNotNull(timePeriodId);
    }

    @Test
    public void addTimePeriodList() throws Exception {
        LOGGER.debug("test: addTimePeriodList()");

        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriods();
        int quantityBefore = timePeriods.size();

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

    @Test
    public void updateTimePeriod() throws Exception {
        LOGGER.debug("test: updateTimePeriod()");

        TimePeriod timePeriod = timePeriodDao.getTimePeriodById(1);
        timePeriod.setBeginning(BEGIN1);

        int count = timePeriodDao.updateTimePeriod(timePeriod);
        assertEquals(1, count);

        TimePeriod updatedTimePeriod = timePeriodDao.getTimePeriodById(timePeriod.getTimePeriodId());
        assertEquals(BEGIN1, updatedTimePeriod.getBeginning());
    }

    @Test
    public void deleteTimePeriod() throws Exception {
        LOGGER.debug("test: updateTimePeriod()");

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
    public void deleteTimePeriodsByEventId() throws Exception {
        LOGGER.debug("test: deleteTimePeriodsByEventId()");

        int[] count = timePeriodDao.addTimePeriodList(TIME_PERIODS1);
        assertEquals(2, count.length);

        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriods();
        int quantityBefore = timePeriods.size();

        int count1 = timePeriodDao.deleteTimePeriodsByEventId(new Event(6));
        assertEquals(2, count1);

        timePeriods = timePeriodDao.getAllTimePeriods();
        assertEquals(quantityBefore - 2, timePeriods.size());
    }
}