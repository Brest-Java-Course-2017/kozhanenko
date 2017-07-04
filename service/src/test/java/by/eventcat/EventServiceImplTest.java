package by.eventcat;

import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
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
 * Event Service Implementation Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:test-spring-service.xml"})
@ContextConfiguration(locations = {"classpath*:test-spring-service-for-jpa-dao-impl.xml"})
@Transactional
public class EventServiceImplTest {

    private Logger LOGGER = LogManager.getLogger();

    private static final String EVENT_PLACE_NAME = "«БРЕСТСКИЙ АКАДЕМИЧЕСКИЙ ТЕАТР ДРАМЫ»";
    private static final String EVENT_NAME = "Премьера \"Весёлый вдовец\"";
    private static final String EVENT_NAME2 = "Радостный человек";
    private static final Event EVENT_CONST = new Event(new Category(4), EVENT_NAME, EVENT_PLACE_NAME);
    private static final Event EVENT = new Event(new Category(4), EVENT_NAME, EVENT_PLACE_NAME);
    private static final Event EVENT1 = new Event(new Category(4), EVENT_NAME, EVENT_PLACE_NAME);

    @Autowired
    private EventService eventService;

    @Test
    public void getAllEvents() throws Exception {
        LOGGER.debug("test: getAllEvents()");

        List<Event> events = eventService.getAllEvents();
        assertTrue(events.size() > 0);
    }

    @Test
    public void getAllEventsByEventPlaceName() throws Exception {
        LOGGER.debug("test: getAllEventsByEventPlaceName()");

        List<Event> events = eventService.getAllEventsByEventPlaceName(EVENT_PLACE_NAME);
        assertTrue(events.size() == 2);
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getAllEventsByEventPlaceNameWrongName() throws Exception {
        LOGGER.debug("test: getAllEventsByEventPlaceNameWrongName()");

        try{
            eventService.getAllEventsByEventPlaceName("A");
        } catch (ServiceException ex ){
            assertEquals(CustomErrorCodes.INCORRECT_INDEX, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getAllEventsByEventPlaceNameNoCallingDataFound() throws Exception {
        LOGGER.debug("test: getAllEventsByEventPlaceNameNoCallingDataFound()");

        try{
            eventService.getAllEventsByEventPlaceName("Площадь Ленина");
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.NO_CALLING_DATA_FOUND, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test
    public void getAllEventsByCategoryId() throws Exception {
        LOGGER.debug("test: getAllEventsByCategoryId()");
        List<Event> allEvents = eventService.getAllEvents();
        if (allEvents.size() > 0){
            int categoryId = allEvents.get(0).getCategory().getCategoryId();
            List<Event> events = eventService.getAllEventsByCategoryId(new Category(categoryId));
            assertTrue(events.size() > 0);
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getAllEventsByCategoryIdIncorrectIndex() throws Exception {
        LOGGER.debug("test: getAllEventsByCategoryId()");

        try{
            eventService.getAllEventsByCategoryId(new Category(-1));
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_INDEX, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getAllEventsByCategoryIdNoDataFound() throws Exception {
        LOGGER.debug("test: getAllEventsByCategoryId()");

        try{
            eventService.getAllEventsByCategoryId(new Category(999));
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.NO_CALLING_DATA_FOUND, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test
    public void getEventById() throws Exception {
        LOGGER.debug("test: getEventById()");
        List<Event> events = eventService.getAllEvents();
        if (events.size() > 0){
            Event existingEvent = events.get(0);
            Event event = eventService.getEventById(existingEvent.getEventId());
            assertNotNull(event);
            assertEquals(existingEvent.getCategory().getCategoryId(), event.getCategory().getCategoryId());
            assertEquals(existingEvent.getEventName(), event.getEventName());
            assertEquals(existingEvent.getEventPlace(), event.getEventPlace());
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getEventByIdIncorrectIndex() throws Exception {
        LOGGER.debug("test: getEventByIdIncorrectIndex()");

        try{
            eventService.getEventById(-1);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_INDEX, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void getEventByIdNoDataFound() throws Exception {
        LOGGER.debug("test: getEventByIdNoDataFound()");

        try{
            eventService.getEventById(999);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.NO_CALLING_DATA_FOUND, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test
    public void addEvent() throws Exception {
        LOGGER.debug("test: addEvent()");

        Integer quantityBefore = eventService.getAllEvents().size();

        Integer eventId = eventService.addEvent(EVENT_CONST);
        assertNotNull(eventId);
        assertEquals(quantityBefore + 1, eventService.getAllEvents().size());

        Event newEvent = eventService.getEventById(eventId);
        assertNotNull(newEvent);
        assertEquals(EVENT_CONST.getCategory().getCategoryId(), newEvent.getCategory().getCategoryId());
        assertEquals(EVENT_CONST.getEventName(), newEvent.getEventName());
        assertEquals(EVENT_CONST.getEventPlace(), newEvent.getEventPlace());
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void addEventIncorrectInputData() throws Exception {
        LOGGER.debug("test: addEventIncorrectInputData()");
        EVENT.getCategory().setCategoryId(-1);
        try{
            eventService.addEvent(EVENT);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_INPUT_DATA, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void addEventNonExistingCategory() throws Exception {
        LOGGER.debug("test: addEventNonExistingCategory()");
        EVENT.getCategory().setCategoryId(999);
        try{
            eventService.addEvent(EVENT);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INPUT_INDEX_OF_NON_EXISTING_CATEGORY, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test
    public void updateEvent() throws Exception {
        LOGGER.debug("test: updateEvent()");

        Event event = eventService.getEventById(1);
        event.setEventName(EVENT_NAME2);

        int count = eventService.updateEvent(event);
        assertEquals(1, count);

        Event updatedEvent = eventService.getEventById(1);
        assertEquals(event.getEventName(), updatedEvent.getEventName());
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void updateEventIncorrectIndex() throws Exception {
        LOGGER.debug("test: updateEventIncorrectIndex()");

        Event event = eventService.getEventById(1);
        event.setEventId(-1);
        try{
            eventService.updateEvent(event);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_INDEX, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void updateEventIncorrectInputData() throws Exception {
        LOGGER.debug("test: updateEventIncorrectInputData()");

        Event event = eventService.getEventById(1);
        event.setEventName("G");
        try{
            eventService.updateEvent(event);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_INPUT_DATA, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void updateEventNoActionsMade() throws Exception {
        LOGGER.debug("test: updateEventNoActionsMade()");

        Event event = eventService.getEventById(1);
        event.setEventId(999);
        try{
            eventService.updateEvent(event);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.NO_ACTIONS_MADE, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test
    public void deleteEvent() throws Exception {
        LOGGER.debug("test: deleteEvent()");

        Integer addedEventId = eventService.addEvent(EVENT1);
        assertNotNull(addedEventId);
        int quantityBefore = eventService.getAllEvents().size();
        int count = eventService.deleteEvent(addedEventId);
        assertEquals(1, count);
        int quantityAfter = eventService.getAllEvents().size();
        assertEquals(quantityBefore - 1, quantityAfter);
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void deleteEventIncorrectIndex() throws Exception {
        LOGGER.debug("test: deleteEventIncorrectIndex()");

        try{
            eventService.deleteEvent(-5);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.INCORRECT_INDEX, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void deleteEventNoActionsMade() throws Exception {
        LOGGER.debug("test: deleteEventNoActionsMade()");

        try{
            eventService.deleteEvent(999);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.NO_ACTIONS_MADE, ex.getCustomErrorCode());
            throw ex;
        }
    }

    @Test(expected = by.eventcat.custom.exceptions.ServiceException.class)
    public void deleteEventDataIntegrityException() throws Exception {
        LOGGER.debug("test: deleteEventDataIntegrityException()");

        try{
            eventService.deleteEvent(1);
        } catch (ServiceException ex){
            assertEquals(CustomErrorCodes.DELETING_DATA_IS_IN_USE, ex.getCustomErrorCode());
            throw ex;
        }
    }
}