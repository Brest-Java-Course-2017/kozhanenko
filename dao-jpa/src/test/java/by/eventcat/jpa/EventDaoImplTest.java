package by.eventcat.jpa;

import by.eventcat.Category;
import by.eventcat.Event;
import by.eventcat.EventDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Event JPA Dao Implementation Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-jpa-dao.xml"})
@Transactional
public class EventDaoImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private EventDao eventDao;

    private static final String EVENT_PLACE_NAME = "«БРЕСТСКИЙ АКАДЕМИЧЕСКИЙ ТЕАТР ДРАМЫ»";
    private static final String EVENT_PLACE_NAME1 = "Some name";
    private static final String EVENT_PLACE_NAME2 = "Площадь Ленина";
    private static final String EVENT_NAME = "Премьера \"Весёлый вдовец\"";
    private static final String EVENT_NAME1 = "Уличный Баскетбол";
    private static final Event EVENT = new Event(new Category(1), EVENT_NAME, EVENT_PLACE_NAME);
    private static final Event WRONG_EVENT = new Event(new Category(999), EVENT_NAME, EVENT_PLACE_NAME);
    private static final Event NON_EXISTING_EVENT =
            new Event(999, new Category(999), EVENT_NAME, EVENT_PLACE_NAME);

    @Test
    public void getAllEvents() throws Exception {
        LOGGER.debug("test: getAllEvents()");
        List<Event> events = eventDao.getAllEvents();
        assertTrue(events.size() > 0);
    }

    @Test
    public void getAllEventsByEventPlaceName() throws Exception {
        LOGGER.debug("test: getAllEventsByEventPlaceName()");
        List<Event> events = eventDao.getAllEventsByEventPlaceName(EVENT_PLACE_NAME);
        assertTrue(events.size() > 0);
    }

    @Test
    public void getAllEventsByEventPlaceNameNoDataFound() throws Exception {
        LOGGER.debug("test: getAllEventsByEventPlaceNameNoDataFound()");
        List<Event> events = eventDao.getAllEventsByEventPlaceName(EVENT_PLACE_NAME1);
        assertTrue(events.size() == 0);
    }

    @Test
    public void getAllEventsByCategoryId() throws Exception {
        LOGGER.debug("test: getAllEventsByCategoryId()");
        List<Event> events = eventDao.getAllEventsByCategoryId(new Category(1));
        assertTrue(events.size() > 0);
    }

    @Test
    public void getAllEventsByCategoryIdNoDataFound() throws Exception {
        LOGGER.debug("test: getAllEventsByCategoryIdNoDataFound()");
        List<Event> events = eventDao.getAllEventsByCategoryId(new Category(999));
        assertTrue(events.size() == 0);
    }

    @Test
    public void getEventById() throws Exception {
        LOGGER.debug("test: getEventById()");
        Event event = eventDao.getEventById(1);
        assertNotNull(event);
        assertEquals(1, event.getEventId());
        assertEquals(1, event.getCategory().getCategoryId());
        assertEquals(EVENT_NAME, event.getEventName());
        assertEquals(EVENT_PLACE_NAME, event.getEventPlace());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getEventByIdNoDataFound() throws Exception {
        LOGGER.debug("test: getEventByIdNoDataFound()");
        eventDao.getEventById(999);
    }

    @Test
    public void addEvent() throws Exception {
        LOGGER.debug("test: addEvent()");

        List<Event> events = eventDao.getAllEvents();
        int quantityBefore = events.size();
        assertTrue(quantityBefore >= 0);

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

    @Test(expected = DataIntegrityViolationException.class)
    public void addEventWithNonExistingCategory() throws Exception {
        LOGGER.debug("test: addEventWithNonExistingCategory()");
        eventDao.addEvent(WRONG_EVENT);
    }

    @Test
    public void updateEvent() throws Exception {
        LOGGER.debug("test: updateEvent()");

        Event event = eventDao.getEventById(3);
        event.setCategory(new Category(4));
        event.setEventName(EVENT_NAME1);
        event.setEventPlace(EVENT_PLACE_NAME2);

        int count = eventDao.updateEvent(event);
        assertEquals(1, count);

        Event updatedEvent = eventDao.getEventById(event.getEventId());
        assertEquals(4, updatedEvent.getCategory().getCategoryId());
        assertEquals(EVENT_NAME1, updatedEvent.getEventName());
        assertEquals(EVENT_PLACE_NAME2, updatedEvent.getEventPlace());
    }

    @Test
    public void updateEventIndexOfNonExistingEvent() throws Exception {
        LOGGER.debug("test: updateEventIndexOfNonExistingEvent()");
        int rowsAffected = eventDao.updateEvent(NON_EXISTING_EVENT);
        assertTrue(rowsAffected == 0);
    }

    @Test
    public void deleteEvent() throws Exception {
        LOGGER.debug("test: deleteEvent()");

        Integer eventId = eventDao.addEvent(EVENT);
        assertNotNull(eventId);
        assertTrue(eventId > 0);

        List<Event> events = eventDao.getAllEvents();
        int quantityBefore = events.size();

        int count = eventDao.deleteEvent(eventId);
        assertEquals(1, count);

        events = eventDao.getAllEvents();
        assertEquals(quantityBefore - 1, events.size());
    }

    @Test
    public void deleteNonExistingEvent() throws Exception {
        LOGGER.debug("test: deleteNonExistingEvent()");
        int rowsAffected = eventDao.deleteEvent(999);
        assertTrue(rowsAffected == 0);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void deleteEventDataIntegrityViolation() throws Exception {
        LOGGER.debug("test: deleteEventDataIntegrityViolation()");
        eventDao.deleteEvent(1);
    }



}
