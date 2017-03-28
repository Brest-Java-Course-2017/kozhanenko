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
    private static final long BEGIN1 = convertTimeFromStringToMilliseconds("2017-03-15 22:50:00");
    private static final long END1 = convertTimeFromStringToMilliseconds("2017-03-15 22:55:10");
    private static final TimePeriod TIME_PERIOD = new TimePeriod(new Event(1), BEGIN, END);
    private static final TimePeriod TIME_PERIOD1 = new TimePeriod(new Event(1), BEGIN, END);
    private static final List<TimePeriod> TIME_PERIODS = new ArrayList<>();
    static {
        TIME_PERIODS.add(TIME_PERIOD);
        TIME_PERIODS.add(TIME_PERIOD1);
    }

    @Test
    public void getAllTimePeriodsByEvent() throws Exception {
        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriodsByEvent(new Event(2));
        assertTrue(timePeriods.size() == 2);
    }

    @Test
    public void getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime() throws Exception {
        List<TimePeriod> timePeriods = timePeriodDao.
                getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime(BEGIN, END);
        assertTrue(timePeriods.size() == 2);
    }

    @Test
    public void addTimePeriod() throws Exception {
        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriodsByEvent(new Event(1));
        Integer quantityBefore = timePeriods.size();//1

        Integer timePeriodId = timePeriodDao.addTimePeriod(TIME_PERIOD);
        assertNotNull(timePeriodId);

        timePeriods = timePeriodDao.getAllTimePeriodsByEvent(new Event(1));
        assertEquals(quantityBefore + 1, timePeriods.size());

        TimePeriod newTimePeriod = timePeriods.get(timePeriods.size() - 1);
        assertNotNull(newTimePeriod);
        assertEquals(newTimePeriod.getBeginning(), TIME_PERIOD.getBeginning());
    }

    @Test
    public void addTimePeriodList() throws Exception {
        List<TimePeriod> timePeriods = timePeriodDao.getAllTimePeriodsByEvent(new Event(1));
        Integer quantityBefore = timePeriods.size();//1

        int rowsAffected = timePeriodDao.addTimePeriodList(TIME_PERIODS);
        assertEquals(2, rowsAffected);

        timePeriods = timePeriodDao.getAllTimePeriodsByEvent(new Event(1));
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
        List <TimePeriod> timePeriods = timePeriodDao.getAllTimePeriodsByEvent(new Event(4));
        TimePeriod timePeriod;
        if (timePeriods !=  null){
            timePeriod = timePeriods.get(0);
            timePeriod.setBeginning(BEGIN1);
            timePeriod.setEnd(END1);

            int count = timePeriodDao.updateTimePeriod(timePeriod);
            assertEquals(1, count);
            TimePeriod updatedTimePeriod = timePeriodDao.getAllTimePeriodsByEvent(new Event(4)).get(0);
            assertEquals(timePeriod.getBeginning(), updatedTimePeriod.getBeginning());
            assertEquals(timePeriod.getEnd(), updatedTimePeriod.getEnd());
        }
    }

    @Test
    public void deleteTimePeriod() throws Exception {
        Integer timePeriodId = timePeriodDao.addTimePeriod(TIME_PERIOD);
        assertNotNull(timePeriodId);

        List<Category> categories = categoryDao.getAllCategories();
        Integer quantityBefore = categories.size();

        int count = categoryDao.deleteCategory(categoryId);
        assertEquals(1, count);

        categories = categoryDao.getAllCategories();
        assertEquals(quantityBefore - 1, categories.size());
    }

    @Test
    public void deleteTimePeriodsByEvent() throws Exception {
        
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