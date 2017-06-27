package by.eventcat.jpa;

import by.eventcat.TimePeriod;
import by.eventcat.TimePeriodDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static by.eventcat.jpa.TimeConverter.*;

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


}
