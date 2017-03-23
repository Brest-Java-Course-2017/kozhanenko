package by.eventcat;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * TimePeriodTest
 */
public class TimePeriodTest {


    @Test
    public void getTimePeriodId() throws Exception {
        TimePeriod timePeriod = new TimePeriod();
        timePeriod.setTimePeriodId(12);
        assertEquals(timePeriod.getTimePeriodId(), 12);
    }

    @Test
    public void getEvent() throws Exception {
        TimePeriod timePeriod = new TimePeriod();
        timePeriod.setEvent(new Event(1, new Category("Театр"), "Представление", "Где-то"));
        assertEquals(timePeriod.getEvent(), new Event(1, new Category("Театр"), "Представление", "Где-то"));
    }

    @Test
    public void getBeginning() throws Exception {
        TimePeriod timePeriod = new TimePeriod();
        timePeriod.setBeginning(1232423234423L);
        assertEquals(timePeriod.getBeginning(), 1232423234423L);
    }

    @Test
    public void getEnd() throws Exception {
        TimePeriod timePeriod = new TimePeriod();
        timePeriod.setEnd(1232423234423L);
        assertEquals(timePeriod.getEnd(), 1232423234423L);
    }

}