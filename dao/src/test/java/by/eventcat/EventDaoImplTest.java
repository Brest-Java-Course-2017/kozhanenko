package by.eventcat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger LOGGER = LogManager.getLogger();

    private static final Event EVENT = new Event(new Category(3), " The best from the west",
            "Matrix club");

    @Autowired
    private EventDao eventDao;

    @Test
    public void getAllEvents() throws Exception {
        LOGGER.debug("test: getAllEvents()");
        List<Event> events = eventDao.getAllEvents();
        assertTrue(events.size() > 0);
    }

    @Test
    public void getAllEventsByEventPlaceName() throws Exception {
        LOGGER.debug("test: getAllEventsByEventPlaceName()");
        List<Event> events = eventDao.getAllEventsByEventPlaceName("«БРЕСТСКИЙ АКАДЕМИЧЕСКИЙ ТЕАТР ДРАМЫ»");
        assertTrue(events.size() > 0);
    }

    @Test
    public void getAllEventsByCategoryId() throws Exception {
        LOGGER.debug("test: getAllEventsByCategoryId()");
        List<Event> events = eventDao.getAllEventsByCategoryId(new Category(1));
        assertTrue(events.size() > 0);
    }

    @Test
    public void getEventById() throws Exception {
        LOGGER.debug("test: getEventById()");
        Event event = eventDao.getEventById(1);
        assertNotNull(event);
        assertEquals(1, event.getEventId());
        assertEquals(1, event.getCategory().getCategoryId());
        assertEquals("Премьера \"Весёлый вдовец\"", event.getEventName());
        assertEquals("«БРЕСТСКИЙ АКАДЕМИЧЕСКИЙ ТЕАТР ДРАМЫ»", event.getEventPlace());
    }

    @Test
    public void addEvent() throws Exception {
        LOGGER.debug("test: addEvent()");

        List<Event> events = eventDao.getAllEvents();
        int quantityBefore = events.size();

        int eventId = eventDao.addEvent(EVENT);
        assertNotNull(eventId);

        events = eventDao.getAllEvents();
        assertEquals(quantityBefore + 1, events.size());

        Event newEvent = eventDao.getEventById(eventId);
        assertNotNull(newEvent);
        assertEquals(EVENT.getCategory().getCategoryId(), newEvent.getCategory().getCategoryId());
        assertEquals(EVENT.getEventName(), newEvent.getEventName());
        assertEquals(EVENT.getEventPlace(), newEvent.getEventPlace());
    }

    @Test
    public void updateEvent() throws Exception {
        LOGGER.debug("test: updateEvent()");

        Event event = eventDao.getEventById(3);
        event.setCategory(new Category(4));
        event.setEventName("Уличный Баскетбол");
        event.setEventPlace("Площадь Ленина");

        int count = eventDao.updateEvent(event);
        assertEquals(1, count);

        Event updatedEvent = eventDao.getEventById(event.getEventId());
        assertEquals(4, updatedEvent.getCategory().getCategoryId());
        assertEquals("Уличный Баскетбол", updatedEvent.getEventName());
        assertEquals("Площадь Ленина", updatedEvent.getEventPlace());
    }

    @Test
    public void deleteEvent() throws Exception {
        LOGGER.debug("test: deleteEvent()");

        Integer eventId = eventDao.addEvent(EVENT);
        assertNotNull(eventId);

        List<Event> events = eventDao.getAllEvents();
        int quantityBefore = events.size();

        int count = eventDao.deleteEvent(eventId);
        assertEquals(1, count);

        events = eventDao.getAllEvents();
        assertEquals(quantityBefore - 1, events.size());
    }
}