package by.eventcat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Event Service Implementation Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-service.xml"})
@Transactional
public class EventServiceImplTest {
    @Test
    public void getAllEvents() throws Exception {

    }

    @Test
    public void getAllEventsByEventPlaceName() throws Exception {

    }

    @Test
    public void getAllEventsByCategoryId() throws Exception {

    }

    @Test
    public void getEventById() throws Exception {

    }

    @Test
    public void addEvent() throws Exception {

    }

    @Test
    public void updateEvent() throws Exception {

    }

    @Test
    public void deleteEvent() throws Exception {

    }

}