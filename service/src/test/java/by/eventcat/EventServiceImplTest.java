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
@ContextConfiguration(locations = {"classpath*:test-spring-service.xml"})
@Transactional
public class EventServiceImplTest {

    private Logger LOGGER = LogManager.getLogger();

    private static final String EVENT_PLACE_NAME = "«БРЕСТСКИЙ АКАДЕМИЧЕСКИЙ ТЕАТР ДРАМЫ»";
    private static final int CATEGORY_ID = 1;
    private static final String EVENT_NAME = "Премьера \"Весёлый вдовец\"";
    private static final Event EVENT_CONST = new Event(new Category(4), EVENT_NAME, EVENT_PLACE_NAME);
    private static final Event EVENT = new Event(new Category(4), EVENT_NAME, EVENT_PLACE_NAME);

    @Autowired
    private EventServiceImpl eventService;

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

        List<Event> events = eventService.getAllEventsByCategoryId(new Category(1));
        assertTrue(events.size() == 2);
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

        Event event = eventService.getEventById(1);
        assertNotNull(event);
        assertEquals(CATEGORY_ID, event.getCategory().getCategoryId());
        assertEquals(EVENT_NAME, event.getEventName());
        assertEquals(EVENT_PLACE_NAME, event.getEventPlace());
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
    }

    @Test
    public void deleteEvent() throws Exception {
        LOGGER.debug("test: deleteEvent()");
    }

}