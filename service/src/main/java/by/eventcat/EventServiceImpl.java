package by.eventcat;


import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * EventService Implementation
 */
@Service
@Transactional
public class EventServiceImpl implements EventService{

    private static final Logger LOGGER = LogManager.getLogger();

    private EventDao eventDao;

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public List<Event> getAllEvents() throws DataAccessException, ServiceException {
        LOGGER.debug("getAllEvents()");

        List<Event> events = eventDao.getAllEvents();
        if (events == null || events.size() == 0) throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        return events;
    }

    @Override
    public List<Event> getAllEventsByEventPlaceName(String eventPlaceName) throws DataAccessException, ServiceException {
        LOGGER.debug("getAllEventsByEventPlaceName({})", eventPlaceName);

        if(eventPlaceName == null || eventPlaceName.length() < 2){
            throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        }
        List<Event> events = eventDao.getAllEventsByEventPlaceName(eventPlaceName);
        if(events == null || events.size() == 0) throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        return events;
    }

    @Override
    public List<Event> getAllEventsByCategoryId(Category category) throws DataAccessException, ServiceException {
        LOGGER.debug("getAllEventsByCategoryId({})", category.getCategoryId());

        if (category.getCategoryId() <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        List<Event> events = eventDao.getAllEventsByCategoryId(category);
        if(events == null || events.size() == 0) throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        return events;
    }

    @Override
    public Event getEventById(Integer eventId) throws DataAccessException, ServiceException {
        LOGGER.debug("getEventById({})", eventId);

        if (eventId <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        Event event;
        try {
            event = eventDao.getEventById(eventId);
        } catch (EmptyResultDataAccessException ex){
            throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        }
        return event;
    }

    @Override
    public Integer addEvent(Event event) throws DataAccessException, ServiceException {
        LOGGER.debug("addEvent(event): name = {}", event.getEventName());

        if (event.getCategory().getCategoryId() <= 0 ||
                event.getEventName() == null ||
                event.getEventName().length() < 2 ||
                event.getEventPlace() == null ||
                event.getEventPlace().length() < 2){
            throw new ServiceException(CustomErrorCodes.INCORRECT_INPUT_DATA);
        }
        Integer eventId;
        try{
            eventId = eventDao.addEvent(event);
        } catch (DataIntegrityViolationException ex){
            throw new ServiceException(CustomErrorCodes.INPUT_INDEX_OF_NON_EXISTING_CATEGORY);
        }
        return eventId;
    }

    @Override
    public int updateEvent(Event event) throws DataAccessException, ServiceException {
        LOGGER.debug("updateEvent {}", event);

        if (event.getEventId() <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        if (event.getCategory().getCategoryId() <= 0 ||
                event.getEventName() == null ||
                event.getEventName().length() < 2 ||
                event.getEventPlace() == null ||
                event.getEventPlace().length() < 2){
            throw new ServiceException(CustomErrorCodes.INCORRECT_INPUT_DATA);
        }
        int rowsAffected = eventDao.updateEvent(event);
        if (rowsAffected == 0) throw new ServiceException(CustomErrorCodes.NO_ACTIONS_MADE);
        if (rowsAffected > 1) throw new ServiceException(CustomErrorCodes.ACTIONS_ERROR);
        return rowsAffected;
    }

    @Override
    public int deleteEvent(Integer eventId) throws DataAccessException, ServiceException {
        LOGGER.debug("delete event with eventId = {}", eventId);

        if (eventId <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        int rowsAffected;
        try{
            rowsAffected = eventDao.deleteEvent(eventId);
        } catch (DataIntegrityViolationException ex){
            throw new ServiceException(CustomErrorCodes.DELETING_DATA_IS_IN_USE);
        }
        if (rowsAffected == 0) throw new ServiceException(CustomErrorCodes.NO_ACTIONS_MADE);
        if (rowsAffected > 1) throw new ServiceException(CustomErrorCodes.ACTIONS_ERROR);
        return rowsAffected;
    }
}
