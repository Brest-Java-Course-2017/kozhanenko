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
    public void getEventId() throws Exception {
        TimePeriod timePeriod = new TimePeriod();
        timePeriod.setEventId(12);
        assertEquals(timePeriod.getEventId(), 12);
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