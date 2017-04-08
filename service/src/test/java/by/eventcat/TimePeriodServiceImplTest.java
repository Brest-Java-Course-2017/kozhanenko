package by.eventcat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Time Period Service Implementation Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-service.xml"})
@Transactional
public class TimePeriodServiceImplTest {
    @Test
    public void getTimePeriodById() throws Exception {

    }

    @Test
    public void getAllTimePeriods() throws Exception {

    }

    @Test
    public void getAllTimePeriodsByEventId() throws Exception {

    }

    @Test
    public void getAllTimePeriodsThatBeginOrLastFromNowTillSelectedTime() throws Exception {

    }

    @Test
    public void addTimePeriod() throws Exception {

    }

    @Test
    public void addTimePeriodList() throws Exception {

    }

    @Test
    public void updateTimePeriod() throws Exception {

    }

    @Test
    public void deleteTimePeriod() throws Exception {

    }

    @Test
    public void deleteTimePeriodsByEventId() throws Exception {

    }

}