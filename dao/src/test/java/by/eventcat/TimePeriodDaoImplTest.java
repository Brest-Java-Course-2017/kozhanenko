package by.eventcat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Time Period Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class TimePeriodDaoImplTest {

    @Autowired
    private TimePeriodDao timePeriodDao;

    private static final long BEGIN = convertTimeFromStringToMilliseconds("2017-03-14 10:55:00");
    private static final long END = convertTimeFromStringToMilliseconds("2017-03-14 22:30:00");
    private static final long BEGIN1 = convertTimeFromStringToMilliseconds("2017-03-13 22:52:00");
    private static final TimePeriod TIME_PERIOD = new TimePeriod(new Event(1), BEGIN, END);
    private static final TimePeriod TIME_PERIOD1 = new TimePeriod(new Event(1), BEGIN, END);
    private static final TimePeriod TIME_PERIOD2 = new TimePeriod(new Event(11), BEGIN, END);
    private static final TimePeriod TIME_PERIOD3 = new TimePeriod(new Event(11), BEGIN, END);
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
        TimePeriod timePeriod = timePeriodDao.getTimePeriodById(1);
        assertNotNull(timePeriod);
        assertEquals(1, timePeriod.getTimePeriodId());
        assertEquals(4, timePeriod.getEvent().getEventId());
        assertEquals(convertTimeFromStringToMilliseconds("2017-03-13 22:49:49"), timePeriod.getBeginning());
        assertEquals(convertTimeFromStringToMilliseconds("2017-03-13 23:55:00"), timePeriod.getEnd());
    }

    @Test
    public void getAllTimePeriods() throws Exception{
        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriods();
        assertTrue(timePeriods.size() > 0);
    }

    @Test
    public void getAllTimePeriodsByEventId() throws Exception {
        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriodsByEventId(new Event(4));
        assertTrue(timePeriods.size() > 0);
    }

    @Test
    public void getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime() throws Exception {
        List<TimePeriod> timePeriods = timePeriodDao.
                getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(BEGIN, END);
        assertTrue(timePeriods.size() > 0);
    }

    @Test
    public void addTimePeriod() throws Exception {
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

    @Test
    public void addTimePeriodList() throws Exception {
        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriods();
        int quantityBefore = timePeriods.size();

        int rowsAffected = timePeriodDao.addTimePeriodList(TIME_PERIODS);
        assertEquals(2, rowsAffected);

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
        TimePeriod timePeriod = timePeriodDao.getTimePeriodById(1);
        timePeriod.setBeginning(BEGIN1);

        int count = timePeriodDao.updateTimePeriod(timePeriod);
        assertEquals(1, count);

        TimePeriod updatedTimePeriod = timePeriodDao.getTimePeriodById(timePeriod.getTimePeriodId());
        assertEquals(BEGIN1, updatedTimePeriod.getBeginning());
    }

    @Test
    public void deleteTimePeriod() throws Exception {
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
        int count = timePeriodDao.addTimePeriodList(TIME_PERIODS1);
        assertEquals(2, count);

        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriods();
        int quantityBefore = timePeriods.size();

        count = timePeriodDao.deleteTimePeriodsByEventId(new Event(11));
        assertEquals(2, count);

        timePeriods = timePeriodDao.getAllTimePeriods();
        assertEquals(quantityBefore - 2, timePeriods.size());
    }

    /**
     * Convert time in String format to timestamp (milliseconds)
     * @param timeInString time in format "yyyy-MM-dd HH:mm:ss"
     * @return time in timestamp format
     */
    private static long convertTimeFromStringToMilliseconds(String timeInString){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try{
            date = simpleDateFormat.parse(timeInString);
        }catch (ParseException ex){
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

}