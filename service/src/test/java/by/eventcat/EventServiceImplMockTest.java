package by.eventcat;

import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;

/**
 * EventServiceImplMockTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-mock-spring-service.xml"})
public class EventServiceImplMockTest {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Event EVENT = new Event(1, new Category(4), "Name1", "Place1");
    private static final Event EVENT1 = new Event(2, new Category(4), "Name2", "Place2");
    private static final List<Event> EVENTS_LIST = new ArrayList<>();

    static {
        EVENTS_LIST.add(EVENT);
        EVENTS_LIST.add(EVENT1);
    }

    @Autowired
    private EventService eventService;

    @Autowired
    private EventDao mockEventDao;

    @After
    public void clean() {
        verify(mockEventDao);
        reset(mockEventDao);
    }

    @Test
    public void getAllEvents() throws Exception {
        LOGGER.debug("mockTest: getAllEvents()");
        expect(mockEventDao.getAllEvents()).andReturn(EVENTS_LIST);
        replay(mockEventDao);
        List<Event> events= eventService.getAllEvents();
        Assert.assertEquals(2, events.size());
    }

    @Test(expected = ServiceException.class)
    public void getAllEventsNoCallingDataFound() throws Exception {
        LOGGER.debug("mockTest: getAllEventsNoCallingDataFound()");
        expect(mockEventDao.getAllEvents()).andReturn(null);
        replay(mockEventDao);
        try{
            eventService.getAllEvents();
        } catch (ServiceException ex){
            Assert.assertTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.NO_CALLING_DATA_FOUND));
            throw ex;
        }
    }

    @Test
    public void addEvent() throws Exception {
        LOGGER.debug("mockTest: addEvent()");
        expect(mockEventDao.addEvent(EVENT)).andReturn(5);
        replay(mockEventDao);
        int newEventId = eventService.addEvent(EVENT);
        Assert.assertEquals(5,  newEventId);
    }

    @Test(expected = ServiceException.class)
    public void addEventIntegrityViolation() throws Exception {
        LOGGER.debug("mockTest: addEventIntegrityViolation()");
        expect(mockEventDao.addEvent(EVENT)).andThrow(new DataIntegrityViolationException(""));
        replay(mockEventDao);
        try{
            eventService.addEvent(EVENT);
        } catch (ServiceException ex){
            Assert.assertTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.INPUT_INDEX_OF_NON_EXISTING_CATEGORY));
            throw ex;
        }
    }

    @Test
    public void updateEvent() throws Exception {
        LOGGER.debug("mockTest: updateEvent()");
        expect(mockEventDao.updateEvent(EVENT1)).andReturn(1);
        replay(mockEventDao);
        int rowsAffected = eventService.updateEvent(EVENT1);
        Assert.assertEquals(1,  rowsAffected);
    }

    @Test(expected = ServiceException.class)
    public void updateEventNoActionsMade() throws Exception {
        LOGGER.debug("mockTest: updateEventNoActionsMade()");
        expect(mockEventDao.updateEvent(EVENT1)).andReturn(0);
        replay(mockEventDao);
        try{
            eventService.updateEvent(EVENT1);
        } catch (ServiceException ex){
            Assert.assertTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.NO_ACTIONS_MADE));
            throw ex;
        }
    }

    @Test(expected = ServiceException.class)
    public void updateEventProgramError() throws Exception {
        LOGGER.debug("mockTest: updateEventProgramError()");
        expect(mockEventDao.updateEvent(EVENT1)).andReturn(5);
        replay(mockEventDao);
        try{
            eventService.updateEvent(EVENT1);
        } catch (ServiceException ex){
            Assert.assertTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.ACTIONS_ERROR));
            throw ex;
        }
    }

    @Test
    public void deleteEvent() throws Exception {
        LOGGER.debug("mockTest: deleteEvent()");
        expect(mockEventDao.deleteEvent(1)).andReturn(1);
        replay(mockEventDao);
        int rowsAffected = eventService.deleteEvent(1);
        Assert.assertEquals(1,  rowsAffected);
    }

    @Test(expected = ServiceException.class)
    public void deleteEventIntegrityViolation() throws Exception {
        LOGGER.debug("mockTest: deleteEventIntegrityViolation()");
        expect(mockEventDao.deleteEvent(1)).andThrow(new DataIntegrityViolationException(""));
        replay(mockEventDao);
        try {
            eventService.deleteEvent(1);
        } catch (ServiceException ex) {
            Assert.assertTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.DELETING_DATA_IS_IN_USE));
            throw ex;
        }
    }

    @Test(expected = ServiceException.class)
    public void deleteEventNoActionsMade() throws Exception {
        LOGGER.debug("mockTest: deleteEventNoActionsMade()");
        expect(mockEventDao.deleteEvent(1)).andReturn(0);
        replay(mockEventDao);
        try {
            eventService.deleteEvent(1);
        } catch (ServiceException ex) {
            Assert.assertTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.NO_ACTIONS_MADE));
            throw ex;
        }
    }

    @Test(expected = ServiceException.class)
    public void deleteEventProgramError() throws Exception {
        LOGGER.debug("mockTest: deleteEventProgramError()");
        expect(mockEventDao.deleteEvent(1)).andReturn(5);
        replay(mockEventDao);
        try {
            eventService.deleteEvent(1);
        } catch (ServiceException ex) {
            Assert.assertTrue(ex.getCustomErrorCode().equals(CustomErrorCodes.ACTIONS_ERROR));
            throw ex;
        }
    }




}
