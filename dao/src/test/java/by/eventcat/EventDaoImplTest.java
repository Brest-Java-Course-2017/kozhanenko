package by.eventcat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Event Dao Implementation Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class EventDaoImplTest {

    @Autowired
    private EventDao eventDao;

    @Test
    public void getAllEvents() throws Exception {
        List<Event> events = eventDao.getAllEvents();
        assertTrue(events.size() > 0);
    }

    @Test
    public void getAllEventsByEventPlaceName() throws Exception {
        List<Event> events = eventDao.getAllEventsByEventPlaceName("«БРЕСТСКИЙ АКАДЕМИЧЕСКИЙ ТЕАТР ДРАМЫ»");
        assertTrue(events.size() == 2);
    }

    @Test
    public void getAllEventsByCategory() throws Exception {

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