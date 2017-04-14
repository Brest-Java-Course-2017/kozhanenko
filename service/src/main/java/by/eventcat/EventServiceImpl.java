package by.eventcat;


import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import org.springframework.dao.DataAccessException;
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

    //@Autowired
    private EventDao eventDao;

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public List<Event> getAllEvents() throws DataAccessException, ServiceException {
        List<Event> events = eventDao.getAllEvents();
        if (events == null) throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        return events;
    }

    @Override
    public List<Event> getAllEventsByEventPlaceName(String eventPlaceName) throws DataAccessException, ServiceException {
        if(eventPlaceName == null || eventPlaceName.length() < 2){
            throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        }
        List<Event> events;
        try{
            events = eventDao.getAllEventsByEventPlaceName(eventPlaceName);
        } catch (EmptyResultDataAccessException ex){
            throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        }
        return events;
    }

    @Override
    public List<Event> getAllEventsByCategoryId(Category category) throws DataAccessException, ServiceException {
        if (category.getCategoryId() <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        List<Event> events;
        try{
            events = eventDao.getAllEventsByCategoryId(category);
        } catch (EmptyResultDataAccessException ex){
            throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        }
        return events;
    }

    @Override
    public Event getEventById(Integer eventId) throws DataAccessException, ServiceException {
        if (eventId <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        Event event = eventDao.getEventById(eventId);
        if (event == null)  throw new ServiceException(CustomErrorCodes.NO_CALLING_DATA_FOUND);
        return event;
    }

    @Override
    public Integer addEvent(Event event) throws DataAccessException, ServiceException {
        if (event.getCategory().getCategoryId() <= 0 ||
                event.getEventName() == null ||
                event.getEventName().length() < 2 ||
                event.getEventPlace() == null ||
                event.getEventPlace().length() < 2){
            throw new ServiceException(CustomErrorCodes.INCORRECT_INPUT_DATA);
        }
        return eventDao.addEvent(event);
    }

    @Override
    public int updateEvent(Event event) throws DataAccessException, ServiceException {
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
        if (eventId <= 0) throw new ServiceException(CustomErrorCodes.INCORRECT_INDEX);
        int rowsAffected = eventDao.deleteEvent(eventId);
        if (rowsAffected == 0) throw new ServiceException(CustomErrorCodes.NO_ACTIONS_MADE);
        if (rowsAffected > 1) throw new ServiceException(CustomErrorCodes.ACTIONS_ERROR);
        return rowsAffected;
    }
}
